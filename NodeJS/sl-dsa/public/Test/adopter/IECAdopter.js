
"use strict";

Function.prototype.extends = function(superclass) {
	this.prototype = Object.create(superclass.prototype);
	this.prototype.constructor = this;
}

var glb_serialPortDevice;
var glb_serialPortListener;
// var glb_serialResponses = [];
// var glb_serialCmd_queue = [];
var glb_serialConnection;

var LOCKER_STATE = {
	DOOR : {
		CLOSED : 0,
		OPEN : 1,
		FORCE_OPEN : 2,
		ERROR : 9
	},
	PACKAGE : {
		OUT : 0,
		IN : 1,
		ERROR : 9
	},
	ALARM : {
		OFF : 0,
		ON : 1,
		ERROR : 9
	}
}

var Connection = function(){
}


var SerialConnection = function() {
	Connection.call(this);
//	this.device = {};
	this.deviceName = 'USB0';
	this.baudRate = 115200;
	this.dataBits = 8;
	this.flowControl = 0;
	this.parity = 3;
	this.stopBits = 1;
	this.response_Q = [];
	this.listener = {};

	//POLLER properties
	this.pollerMode = "INACTIVE";
	this.command_Q = [];
	this.waiting_Q = [];
	this.doorOpeninterval = 1000;
	this.interCommandInterval = 100;
	// this.theadCheck_Q = [];
	this.theadCheck_Q = {};
	this.cmd_error_Q = [];
	this.lastResponse = null;
	this.maxCmdStuckCount = 9;
	
	//TestOnly
	this.stuckedThreadCount = 0;
};

SerialConnection.extends(Connection);

SerialConnection.prototype.setConnection = function(callback) {

	initMetaDataDefinition();
	intiMetaPropertyDefinition();
	
//	try{
		global.serialPorts.deleteAll();
		var devices = global.serialPorts.availableDevices;

		console.log("global.serialPorts.contains :: " +global.serialPorts.contains(this.deviceName));
		
        for (var i = 0; i < devices.length; ++i)
        {
            if(devices[i] == 'USB0')
            	console.log("device :: [" + i + "] - " +devices[i] );
        }
        
		var alreadyExists = global.serialPorts.contains(this.deviceName);

//		this.device = global.serialPorts.port(this.deviceName);
//		this.device.baudRate = this.baudRate
//		this.device.dataBits = this.dataBits;
//		this.device.flowControl = this.flowControl;
//		this.device.parity = this.parity;
//		this.device.stopBits = this.stopBits;
		
		console.log("this.baudRate:: " + this.baudRate);
		console.log("this.dataBits:: " + this.dataBits);
		console.log("this.flowControl:: " + this.flowControl);
		console.log("this.parity:: " + this.parity);
		console.log("this.stopBits:: " + this.stopBits);
		console.log("this.baudRate:: " + this.baudRate);
		glb_serialPortDevice = global.serialPorts.port(this.deviceName);
		glb_serialPortDevice.baudRate = this.baudRate;
		glb_serialPortDevice.dataBits = this.dataBits;
		glb_serialPortDevice.flowControl = this.flowControl;
		glb_serialPortDevice.parity = this.parity;
		glb_serialPortDevice.stopBits = this.stopBits;

		
		// TODO Find better place to put
//		this.device.iecId = global.device.serialNumber;
//		console.log("Locker S/N ::: " + device.iecId);
		
		if (!alreadyExists){
//			this.device.readyRead.connect(this.onData);
			// SerialConnection.prototype.onData.bind(SerialConnection.prototype);
			glb_serialPortDevice.readyRead.connect(this.onData);

		}

//	}catch(e){
//		//TODO device connection error handling
//		console.log("ERROR :: " + e)
//	}

//	this.listener = callback;
		glb_serialPortListener = callback;
};


SerialConnection.prototype.onData = function() {

	var HANDSHAKE_EVENT = '#ES1EC';
	var EVENT_NITOFICATION = '#EB';
	var GET_STATUS_HEADER = '$SB';
	var ACKNOWLEDGE_NOTI = '\x24\x45\x53\x30\x30\x2a\x2a\x0d'; //OK

	//	var data = this.device.readAll();
	var data = glb_serialPortDevice.readAll();
	
	var responseStr = "";
	for (var i = 0; i < data.length; ++i) {
		responseStr += data[i] < 32 ? '.' : String.fromCharCode(data[i]);
	}
	
	console.log("#####onDate() raw String ::::" + responseStr);

	if(responseStr.indexOf(EVENT_NITOFICATION) >= 0){
		console.log("#####onDate() - EVENT_NITOFICATION :::: sent ACKNOWLEDGE_NOTI");
		glb_serialPortDevice.write(ACKNOWLEDGE_NOTI);				
	}
	
//	if(str.startsWith(HANDSHAKE_EVENT)){//startwith() not working
	var responseArrs = [];
	responseArrs = responseArrs.concat(
		glb_serialConnection.serialPort_filterData(responseStr));
	console.log("#####onDate() filtered result arrs ::::" + responseArrs);
	console.log("#####onDate() for - responesArrs.length ::::" + responseArrs.length);

	for(var idx in responseArrs){
		var response = responseArrs[idx];
		var length = response.length;

		if(length < 1 ){
			console.log("#####onDate() - invalid String, length is ::: " + length);
			return;
		}else if(HANDSHAKE_EVENT == response){
			console.log("#####onDate() - HANDSHAKE_EVENT :::: sent ACKNOWLEDGE_NOTI");
			glb_serialPortDevice.write(ACKNOWLEDGE_NOTI);
		}else{

			//COMMAND NOTIFICATION 
			if(glb_serialConnection.isCmdReceivedNotification(response)){
				glb_serialConnection.dequeueCompletedCmd(response);
				glb_serialConnection.sendNextCommand(response);
			}else if(glb_serialConnection.isCmdErrorNotification(response)){
				console.log("#####onDate() - Command Error Notification :::: " + response);
				console.log("#####onDate() - Move the command [" + response + "] to Error_Q");
				glb_serialConnection.moveCmdWatingQtoErrorQ();
				glb_serialConnection.sendNextCommand(response);
			}

			//EVENT NOTIFICATION 
			if(response.indexOf(EVENT_NITOFICATION) >= 0){
				//In case of chuck of response - when fail to send ACKNOWLEDGE_NOTI against EVENT_NITOFICATION 
				if(glb_serialConnection.lastResponse != response){
					console.log("#####onDate() - Calling callback with response :::: " + response);
					glb_serialPortListener.call(null, response);
				}else{
					console.log("#####onDate() - Ignore duplicated response :::: " + response);
				}
				glb_serialConnection.lastResponse = response;
			}else if(response.indexOf(GET_STATUS_HEADER) >= 0){
				glb_serialPortListener.call(null, response);
			}else{
				console.log("#####onDate() - Ignoring this event response :::: " + response);
			}
		}
	}
};

SerialConnection.prototype.isCmdReceivedNotification = function(response){
	if(response.indexOf("$OP") >= 0){
		console.log("[isCmdReceivedNotification]::::This is a Cmd received notification - " + response);
		return true;
	}else if(response.indexOf("$SB") >= 0){
		console.log("[isCmdReceivedNotification]::::This is a Cmd received notification - " + response);
		return true;		
	}
	return false;
};

SerialConnection.prototype.dequeueCompletedCmd = function(response) {
	var rceivedNotiLockerId = response.substring(5,8);
	var deleted = false;

	for(var index in this.waiting_Q){
		var queueLockerId = this.waiting_Q[index].substring(3,6);
		console.log("[dequeueCompletedCmd - resend]:::rceivedNotiLockerId vs queueLockerId - " + rceivedNotiLockerId + " VS "+ queueLockerId);
		if(rceivedNotiLockerId === queueLockerId){
			var removedCmd = this.waiting_Q.splice(index, 1);
			console.log("[dequeueCompletedCmd - resend]:::: command removed from :: waiting_Q" + removedCmd);
			deleted = true;
			break;
		}
	}

	if(!deleted){
		if(this.isCommandError(response)){
			console.log("[dequeueCompletedCmd]:::: it's command error " + response);
			this.cmd_error_Q.push(this.waiting_Q.splice(0, 1)); 
		}else{
			console.log("[dequeueCompletedCmd]:::: Something Wrong!! No commmand match to deque " + response);		
		}
		
	}
};

SerialConnection.prototype.moveCmdWatingQtoErrorQ = function() {
	this.cmd_error_Q.push(this.waiting_Q.splice(0, 1)); 
};

SerialConnection.prototype.isCmdErrorNotification = function(response){
	var BCC_ERR = "!0182";
	if(response.indexOf(BCC_ERR) >= 0)
		return true;
	return false;
};

SerialConnection.prototype.isCommandError = function(response){
	var rceivedNotiLockerId = response.substring(5,8);
	if(rceivedNotiLockerId == "000")
		return true;
	return false;
};

SerialConnection.prototype.sendNextCommand = function(response){
	console.log("#####sendNextCommand() - command_Q.length :: " + this.command_Q.length);
	if(this.command_Q.length > 0){
		var cmd = this.command_Q.splice(0,1).toString();
		console.log("#####sendNextCommand() - sending a next command with :: " + cmd);
		this.sendSerialCommand(cmd);
	}else{
		console.log("#####sendNextCommand() - no commmand left on this.command_Q");
	}
};

/*
1. Poller start with sleep mode with interval (doorOpenInterval + (LockerCount * interCommandInterval))
2. When the poller kicked in, check whether the process is stuck or not.
 - check thread is stuck or not by checking waiting_Q's count.
 2.1 If it is stucked, start the poller with non-sleep Mode; move command from wating_Q to command_Q and re-send command
 2.2 If not, start the poller with sleep mode with (doorOpenInterval + (LockerCount * interCommandInterval))
3. If no command are left, stop the poller
 */


SerialConnection.prototype.checkProcessCompeletedPoller = function(cmd_q_count, sleepMode) {
	var interval
	if(cmd_q_count == null){
		interval = this.interCommandInterval;
	}else{
		interval = /*this.doorOpeninterval +*/ (this.interCommandInterval * cmd_q_count); //interval of opendoor process 100(should configurable)
	}
	
	console.log("TOTAL poller Interval is :::: " + interval);

	var self = this;
	setTimeout(function(cmd_q_count){
		console.log("$$$$$$$$$$$$$$$$$$$$ POLLER START - SleepMode :"+ sleepMode +" $$$$$$$$$$$$$$$$$$$$");
		self.pollerMode = "ACTIVE";
		console.log("% checkProcessCompeletedPoller() - START with interval :: " + interval);
		if(self.isWaiting_Q_Empty() && self.isCommand_Q_Empty()){
			console.log("#################### POLLER END #################### ");
			if(self.cmd_error_Q.length > 0){
				console.log("#################### Failed command ::: " + self.cmd_error_Q);
				
			}
			//Initialize internal Qs
			self.cmd_error_Q = [];
			self.theadCheck_Q = {};
			self.pollerMode = "INACTIVE";
				
		}else if(sleepMode == true){
			console.log("% checkProcessCompeletedPoller() - with sleepMode :: " + sleepMode );
			//one of Qs are not empty.
			if(!self.isWaiting_Q_Empty()){
				if(self.isThreadStruck()){
					console.log("% checkProcessCompeletedPoller() - thread stuck detected!!! " + self.waiting_Q);

					//DELETE ME TEST ONLY 
					self.stuckedThreadCount ++ ;
					//DELETE ME TEST ONLY 

					self.checkProcessCompeletedPoller(null, false);
				}else{
					self.checkProcessCompeletedPoller(null, true);
				}
			}else{
				self.checkProcessCompeletedPoller(self.command_Q.length + 1, true);
			}
		}else{
			
			//INFINITE LOOP PROTECTION CODE START
			var waitingCmd = self.waiting_Q[0];
			if(self.theadCheck_Q[waitingCmd] > self.maxCmdStuckCount){
				console.log("% checkProcessCompeletedPoller() - self.theadCheck_Q[waitingCmd] ::: " + self.theadCheck_Q[waitingCmd]);
				//splice in here is not working, just make it "" array so array's length is 1.
				self.moveCmdWatingQtoErrorQ();
				self.theadCheck_Q[waitingCmd] = 0;	
			}
			//INFINITE LOOP PROTECTION CODE END

			self.command_Q = [].concat(self.waiting_Q.splice(0).concat(self.command_Q.splice(0)));
			// self.command_Q = [].concat(self.command_Q.splice(0).concat(self.waiting_Q.splice(0)));
			console.log("% checkProcessCompeletedPoller() - concat this.command_Q :: " + self.command_Q);
			// self.theadCheck_Q = [];
			self.sendSerialCommand(self.command_Q.splice(0,1).toString());
			self.checkProcessCompeletedPoller(self.command_Q.length + 1, true);
		}
		
	}, interval)
}


/*SerialConnection.prototype.isThreadStruck = function() {
	if(this.waiting_Q.length > 0){
		if(this.theadCheck_Q.length >= 3){
			var stuckChk_Q_length = this.theadCheck_Q.length;

			//if all three value are the same.
			var isSameValues = this.theadCheck_Q[stuckChk_Q_length - 3] == this.theadCheck_Q[stuckChk_Q_length - 2] 
				&& this.theadCheck_Q[stuckChk_Q_length - 3] == this.theadCheck_Q[stuckChk_Q_length - 1]
				&& this.theadCheck_Q[stuckChk_Q_length - 3] != null;
	
			console.log("% isThreadStruck() - this thread stuck :: " + isSameValues + " : "+ this.theadCheck_Q);		
			return isSameValues ? true : false ;			
		}else{
			this.theadCheck_Q.push(this.waiting_Q.length);
			console.log("% isThreadStruck() - this.theadCheck_Q.length :: " + this.theadCheck_Q.length);		
			return false;						
		}
	}
	console.log("% isWaiting_Q_Empty() - waiting_Q is empty :: " + this.theadCheck_Q);	
	return false;
};
*/
SerialConnection.prototype.isThreadStruck = function() {

	if(this.waiting_Q.length > 0){
		var waitingCmd = this.waiting_Q[0];

		if(this.theadCheck_Q[waitingCmd] && this.theadCheck_Q[waitingCmd] % 3 == 0){
			console.log("% isThreadStruck() - this thread stuck :: " + waitingCmd + " : "+ this.theadCheck_Q[waitingCmd]);
			this.theadCheck_Q[waitingCmd]++;
			return true;
		}else{
			
			if(this.theadCheck_Q[waitingCmd]){
				this.theadCheck_Q[waitingCmd]++;
			}else{
				this.theadCheck_Q[waitingCmd] = 1;
			}
			console.log("#####:::waitingCmd ::: " + waitingCmd + ":::::" + this.theadCheck_Q[waitingCmd]);
			console.log("% isThreadStruck() - this.theadCheck_Q[waitingCmd] :: " + this.theadCheck_Q[waitingCmd]);		
			return false;						
		}
	}
	console.log("% isThreadStruck() - waiting_Q is empty :: " + this.theadCheck_Q);	
	return false;
};

SerialConnection.prototype.isWaiting_Q_Empty = function() {

	this.waiting_Q = this.cleanArray(this.waiting_Q);
	if(this.waiting_Q.length > 0 ){
		console.log("% isWaiting_Q_Empty() - waiting_Q is not empty :: " + this.waiting_Q.length);		
		return false;
	}
	console.log("% isWaiting_Q_Empty() - waiting_Q is empty :: " + this.waiting_Q);	
	return true;
};

SerialConnection.prototype.isCommand_Q_Empty = function() {
	this.command_Q = this.cleanArray(this.command_Q);
	if(this.command_Q.length > 0 && this.command_Q[0] != ""){
		console.log("% isCommand_Q_Empty() - command_Q is not empty :: " + this.waiting_Q);		
		return false;
	}
	console.log("% isCommand_Q_Empty() - command_Q is empty :: " + this.waiting_Q);
	return true;
};

SerialConnection.prototype.addCommand = function(action, lockerId) {
	console.log("[addCommand]::::START with - " + action + ", " + lockerId);
	var request = this.createRequest(action, lockerId);
	var interpreter = commandIntepreterManager.getCommandInterpreter("request",
			request.action);
	var strCommand = interpreter.interpreteRequest(request);
	this.command_Q.push(strCommand);
	console.log("[addCommand]::::this.command_Q - " + this.command_Q);
};


SerialConnection.prototype.sendCommands = function(action, lockerIds){

	for(var idx in lockerIds){
		if(idx != 'undefined'){
			this.addCommand(action, lockerIds[idx]);
		}
	}

	if(this.pollerMode =="INACTIVE"){
		this.sendSerialCommand(this.command_Q.splice(0,1).toString());
		this.checkProcessCompeletedPoller(this.command_Q.length, true);		
	}else{
		console.log("Poller is running!!!!!! commands are added to commnnd_Q");
	}
};

SerialConnection.prototype.sendCommand = function(action, lockerId) {
	console.log("[sendCommand]::::START with - " + action + ", " + lockerId);
	var request = this.createRequest(action, lockerId);
	var interpreter = commandIntepreterManager.getCommandInterpreter("request",
			request.action);
	var strCommand = interpreter.interpreteRequest(request);
	// var cmd = interpreter.convertStringToCommand(interpreter.interpreteRequest(request));
	// this.sendSerialCommand(interpreter.hex2a(cmd));
	this.sendSerialCommand(strCommand);
};

SerialConnection.prototype.sendSerialCommand = function(strCommand) {
	console.log("[sendSerialCommand()]::command => " + strCommand);
//	this.device.write(strCommand);
	var cmd = this.convertStringToCommand(strCommand);
	glb_serialPortDevice.write(this.hex2a(cmd));
	
	this.waiting_Q.push(strCommand);
	console.log("[sendSerialCommand()]:: pushed into waiting_Q => " + this.waiting_Q);
};


SerialConnection.prototype.createRequest = function(actionType, lockerId) {

	var lockerGroupNum = lockerId.substring(0, 2);
	var lockerNum = lockerId.substring(2, 3);
	var data = {};

	console.log("[createRequest()]::lockerGroupNum , " + lockerGroupNum);
	console.log("[createRequest()]::lockerNum , " + lockerNum);

	switch(actionType){
		case 'OPEN' :
			data = {
				action : actionType,
				version : '1.0.0',
				lockerGroupNo : lockerGroupNum,
				lockerNo : lockerNum,
				interval : '0' + this.doorOpeninterval/1000,
				lockerCondition : 'LOCKER_CONDITION.NONE',
				alarmControl : 'ALARM.CONTROL.OFF'
			}

			break;
		case 'GET_STATUS':
			data = {
				action : actionType,
				version : '1.0.0',
				lockerGroupNo : lockerGroupNum,
				lockerNo : lockerNum
			}
			break;
		default:
			throw Error("No anctionType match : " + actionType);
	}

	// console.log("data::::" + JSON.stringify(data));
	return data;
};

/*
 * Handling split serial response from Panasonic locker.
 * 
 */
/*SerialConnection.prototype.serialPort_filterData = function (str){
	// ADPTER_CORE_DEBUG = "ON";
	if(!str) 
		return null;
	debug("#input - str :: " + str);
	var str = str.replace(/\./g,"._");
	debug("#input - replace :: " + str);
	
	var arrs = str.split("_");
	debug("#splitted arrs :: " + arrs);
	
	var firstStr = arrs.splice(0,1).toString();
	var markerIndex = firstStr.indexOf(".");
	var returnValue = [];
	
	debug("#firstStr :: " + firstStr);
	debug("#markerIndex :: " + markerIndex );
	
	glb_serialResponses = this.cleanArray(glb_serialResponses);
	
	if(markerIndex == -1){//'.' is not appear 
		debug("@@glb_serialResponses 1 - NONE BEFORE ::: " + glb_serialResponses.length);
		debug("@@arrs 1 - NONE BEFORE ::: " + arrs.length);
		if(glb_serialResponses.length > 0){
			glb_serialResponses[0] = glb_serialResponses[0] + firstStr;
		}else{
			glb_serialResponses = glb_serialResponses.concat(firstStr);	
		}
		debug("@@glb_serialResponses 1 - NONE ::: " + glb_serialResponses);
		return null;
	}else if(arrs.length == 1 && markerIndex >= 0){ //'.' is appear once
		//Leftover response found
		if (glb_serialResponses.length > 0){
			returnValue.push(glb_serialResponses.splice(0,1) + firstStr.replace(".",""));

			//handled all received array, join leftover arrs into glb_serialResponses 
			glb_serialResponses = glb_serialResponses.concat(arrs);
			debug("@@glb_serialResponses 2 - ONCE(No Leftover) ::: " + glb_serialResponses);

			return returnValue; 
		}else{//No leftover found
			returnValue.push(firstStr.replace(".",""));
			
			//handled all received array, join leftover arrs into glb_serialResponses 
			glb_serialResponses = glb_serialResponses.concat(arrs);

			debug("@@glb_serialResponses 3 - ONCE(With Leftover) ::: " + glb_serialResponses);

			return returnValue  
		}
	}else if(arrs.length > 1 ){//'.' is appear multiple times
		debug("4. arrs ::: " + arrs);
		debug("4. glb_serialResponses ::: " + glb_serialResponses);
		if (glb_serialResponses.length > 0){
			returnValue.push(glb_serialResponses.splice(0,1) + firstStr.replace(".",""));
		}else{
			returnValue.push(firstStr.replace(".",""));
		}

		debug("4. arrs ::: " + arrs);
		
		for(var idx in arrs){
			debug("4. str ::: " + arrs[idx]);
			//found a finished response
			if(arrs[idx].indexOf(".") > 0){
				returnValue.push(arrs[idx].replace(".",""));	
				debug("4. returnValue ::: " + returnValue);
			}else{
				//found a unfinished response
				glb_serialResponses = glb_serialResponses.concat(arrs[idx]);
				debug("@@glb_serialResponses 4 - Multiple ::: " + glb_serialResponses);
			}
		}
		return returnValue;
	}
	// ADPTER_CORE_DEBUG = "OFF";
};*/


SerialConnection.prototype.serialPort_filterData = function (str){
	// ADPTER_CORE_DEBUG = "ON";
	if(!str) 
		return null;
	debug("#input - str :: " + str);
	var str = str.replace(/\./g,"._");
	debug("#input - replace :: " + str);
	
	var arrs = str.split("_");
	debug("#splitted arrs :: " + arrs);
	
	var firstStr = arrs.splice(0,1).toString();
	var markerIndex = firstStr.indexOf(".");
	var returnValue = [];
	
	debug("#firstStr :: " + firstStr);
	debug("#markerIndex :: " + markerIndex );
	
	this.response_Q = this.cleanArray(this.response_Q);
	
	if(markerIndex == -1){//'.' is not appear 
		debug("@@this.response_Q 1 - NONE BEFORE ::: " + this.response_Q.length);
		debug("@@arrs 1 - NONE BEFORE ::: " + arrs.length);
		if(this.response_Q.length > 0){
			this.response_Q[0] = this.response_Q[0] + firstStr;
		}else{
			this.response_Q = this.response_Q.concat(firstStr);	
		}
		debug("@@this.response_Q 1 - NONE ::: " + this.response_Q);
		return null;
	}else if(arrs.length == 1 && markerIndex >= 0){ //'.' is appear once
		//Leftover response found
		if (this.response_Q.length > 0){
			returnValue.push(this.response_Q.splice(0,1) + firstStr.replace(".",""));

			//handled all received array, join leftover arrs into this.response_Q 
			this.response_Q = this.response_Q.concat(arrs);
			debug("@@this.response_Q 2 - ONCE(No Leftover) ::: " + this.response_Q);

			return returnValue; 
		}else{//No leftover found
			returnValue.push(firstStr.replace(".",""));
			
			//handled all received array, join leftover arrs into this.response_Q 
			this.response_Q = this.response_Q.concat(arrs);

			debug("@@this.response_Q 3 - ONCE(With Leftover) ::: " + this.response_Q);

			return returnValue  
		}
	}else if(arrs.length > 1 ){//'.' is appear multiple times
		debug("4. arrs ::: " + arrs);
		debug("4. this.response_Q ::: " + this.response_Q);
		if (this.response_Q.length > 0){
			returnValue.push(this.response_Q.splice(0,1) + firstStr.replace(".",""));
		}else{
			returnValue.push(firstStr.replace(".",""));
		}

		debug("4. arrs ::: " + arrs);
		
		for(var idx in arrs){
			debug("4. str ::: " + arrs[idx]);
			//found a finished response
			if(arrs[idx].indexOf(".") > 0){
				returnValue.push(arrs[idx].replace(".",""));	
				debug("4. returnValue ::: " + returnValue);
			}else{
				//found a unfinished response
				this.response_Q = this.response_Q.concat(arrs[idx]);
				debug("@@this.response_Q 4 - Multiple ::: " + this.response_Q);
			}
		}
		return returnValue;
	}
	// ADPTER_CORE_DEBUG = "OFF";
};

SerialConnection.prototype.convertStringToCommand = function(str){
	var command = '';
	str = str + this.getBCC(str);
	
	for (var i = 0; i < str.length; i++) {
		var hex = str.charCodeAt(i).toString(16);
//		command += '\\x' + str.charCodeAt(i).toString(16);
		command += str.charCodeAt(i).toString(16);
	}
	command += '0d';
	debug("[convertStringToCommand()] 3 : " + command);

	return command;
};

SerialConnection.prototype.hex2a = function(hex){
	var str = '';
	for (var i = 0; i < hex.length; i += 2)
		str += String.fromCharCode(parseInt(hex.substr(i, 2), 16));
	return str;
};


SerialConnection.prototype.getBCC = function(str){
	var hexAdded = 0;
	for (var i = 0; i < str.length; i++) {
		hexAdded += str.charCodeAt(i);
	}
	var result = Number(hexAdded).toString(16);
	debug("[BCC] result : " + result);
	debug("[BCC] " + result.substring(result.length - 2));
	// return result.substring(result.length - 2);
	return "**";
}


SerialConnection.prototype.cleanArray = function(actual) {
	var newArray = new Array();
	for (var i = 0; i < actual.length; i++) {
		if (actual[i]) {
			newArray.push(actual[i]);
		}
	}
	return newArray;
}


var Locker = function() {
	this.id = "";
	this.doorState = LOCKER_STATE.DOOR.CLOSED;
	this.packageState = LOCKER_STATE.PACKAGE.OUT;
};

var BridgePointLocker = function() {
	Locker.call(this)
};
BridgePointLocker.extends(Locker);


var PanasonicLocker = function() {
	Locker.call(this)
	this.alarmState = LOCKER_STATE.ALARM.OFF;
};
PanasonicLocker.extends(Locker);



var LockerManager = function(lockerCount, connection) {
	this.lockers = {};
	this.conn = connection;
	this.lockerCount = lockerCount;

};

LockerManager.prototype.setLockerById = function(lockerId, locker){
	this.lockers[lockerId] = locker;
};

LockerManager.prototype.getLockerById = function(lockerId, locker){
	return this.lockers[lockerId];
};

// LockerManager.prototype.setLockerPropertyById = function(lockerId, key, value){
// 	eval("this.lockers[lockerId]." + key + " = " + value);
// };

// LockerManager.prototype.getLockerPropertyById = function(lockerId, key, value){
// 	return eval("this.lockers[lockerId]." + key);
// };

LockerManager.prototype.sendCommand = function(str, lockerId){
	this.conn.sendCommand(str, lockerId);
};

LockerManager.prototype.addCommand = function(str, lockerId){
	this.conn.addCommand(str, lockerId);
};





var PanasonicLockerManager = function(lockerCount, connection) {
	LockerManager.call(this, lockerCount);
	this.init();
};
PanasonicLockerManager.extends(LockerManager);

PanasonicLockerManager.prototype.initConnection = function(handler){
	if(this.conn == null){
		this.conn = new SerialConnection();
		console.log("Create a new SerialConnection!!!!!!!!!");
		
		this.conn.setConnection(handler);
		//store it as global level because when Cobra browser native method doesn't know this.		
		glb_serialConnection = this.conn;
	}
	else{
		this.conn.setConnection(handler);	
	}
};

PanasonicLockerManager.prototype.init = function() {
	var lockerGroupId = 1;
	var lockerId = 1;

	for (var i = 0; i < this.lockerCount; i++) {
		var locker = new PanasonicLocker();
		locker.id = "0" + lockerGroupId + lockerId;
		this.setLockerById(locker.id, locker);
		
		if (lockerId == 6) {
			lockerGroupId++;
			lockerId = 1;
		} else {
			lockerId++;
		}
	}
	console.log("###TOTAL LOCKER COUNT::: " + this.lockerCount);
};




