var glb_serialPortDevice;
var glb_serialPortListener;
var glb_serialConnection;

var SerialConnection = function(eventHandler) {
	this.deviceName = "";
	this.baudRate = 115200;
	this.dataBits = 8;
	this.flowControl = 0;
	this.parity = 3;
	this.stopBits = 1;
	this.commandInterpreterManager = new CommandInterpreterManager();
	
	this.response_Q = [];
	this.listener = eventHandler;

	//POLLER properties
	this.pollerMode = "INACTIVE";
	this.command_Q = [];
	this.waiting_Q = [];
	this.doorOpeninterval = 1000;
	this.interCommandInterval = 100;
	this.theadCheck_Q = {};
	this.cmd_error_Q = [];
	this.stuck_thresholds_count = 2
	this.maxCmdStuckCount = 2;

	//TestOnly
	this.stuckedThreadCount = 0;

	try{
		this.initDeviceConnection();	
	}catch(e){
		console.log("Devince serical connection error : " + e);
	}
	
};


SerialConnection.prototype.initDeviceConnection = function(){
	global.serialPorts.deleteAll();
	var devices = global.serialPorts.availableDevices;
	var rs232connections = [];
	 
	try{
	
	    for (var i = 0; i < devices.length; ++i)
	    {		    	
	        if(devices[i].length > 3 && devices[i].substring(0,3) === "USB"){
	        	console.log("device :: [" + i + "] - " + devices[i] );
	        	rs232connections.push(devices[i]);
	        }		        	
	    }

	    if(rs232connections.length == 1){
	    	// throw new Exception;
	    	this.deviceName = rs232connections[0];
	    }else{
			this.deviceName = rs232connections[1];
	    }

		console.log("global.serialPorts.contains :: " + global.serialPorts.contains(this.deviceName));    
		var alreadyExists = global.serialPorts.contains(this.deviceName);

		console.log("baudRate:: " + this.baudRate);
		console.log("dataBits:: " + this.dataBits);
		console.log("flowControl:: " + this.flowControl);
		console.log("parity:: " + this.parity);
		console.log("stopBits:: " + this.stopBits);
		console.log("baudRate:: " + this.baudRate);
		glb_serialPortDevice = global.serialPorts.port(this.deviceName);
		console.log("### glb_serialPortDevice :: " + glb_serialPortDevice);
		glb_serialPortDevice.baudRate = this.baudRate;
		glb_serialPortDevice.dataBits = this.dataBits;
		glb_serialPortDevice.flowControl = this.flowControl;
		glb_serialPortDevice.parity = this.parity;
		glb_serialPortDevice.stopBits = this.stopBits;
		
		
		if (!alreadyExists){
			glb_serialPortDevice.readyRead.connect(this.onData);
		}

	}catch(e){
		console.log("[ERROR] Not able to connect to Locker");
		console.log(e);
	}

	glb_serialPortListener = this.listener;
	glb_serialConnection = this;
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

SerialConnection.prototype.setDoorOpenInterval = function(cmdCount){
	// console.log("######setDoorOpenInterval() - " + cmdCount);

	if(cmdCount > 1 && cmdCount <= 6){
		this.doorOpeninterval = 1000;
	}else if(cmdCount > 6 && cmdCount <= 12){
		this.doorOpeninterval = 2000;
	}else if(cmdCount > 13){
		this.doorOpeninterval = 3000;
	}
	// console.log("######setDoorOpenInterval() - doorOpeninterval - " + this.doorOpeninterval);	
};

SerialConnection.prototype.hex2a = function(hex){
	var str = '';
	for (var i = 0; i < hex.length; i += 2)
		str += String.fromCharCode(parseInt(hex.substr(i, 2), 16));
	return str;
};

SerialConnection.prototype.getBCC = function(str){
	var hexAdded = 0;
	var hexAdded2 = 0;
	for (var i = 0; i < str.length; i++) {
		hexAdded += str.charCodeAt(i);
	}
	var result = Number(hexAdded).toString(16);
	// debug("[BCC] result : " + result);
	// debug("[BCC] " + result.substring(result.length - 2));
	
	//Panasonic Locker Board return BCC error when bcc end with "F"
	return result.toUpperCase().substring(result.length - 2);
};

SerialConnection.prototype.cleanArray = function(actual) {
	var newArray = new Array();
	for (var i = 0; i < actual.length; i++) {
		if (actual[i]) {
			newArray.push(actual[i]);
		}
	}
	return newArray;
};

SerialConnection.prototype.convertStringToCommand = function(str){
	var command = '';
	str = str + this.getBCC(str);
	console.log("[convertStringToCommand() :: str] : " + str);
	
	for (var i = 0; i < str.length; i++) {
		var hex = str.charCodeAt(i).toString(16);
//		command += '\\x' + str.charCodeAt(i).toString(16);
		command += str.charCodeAt(i).toString(16);
	}
	command += '0d';
	// debug("[convertStringToCommand()] : " + command);

	return command;
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
	debug("#####onDate() for - responesArrs.length ::::" + responseArrs.length);

	for(var idx in responseArrs){
		var response = responseArrs[idx];
		var length = response.length;

		if(length < 1 ){
			debug("#####onDate() - invalid String, length is ::: " + length);
			return;
		}else if(HANDSHAKE_EVENT == response){
			debug("#####onDate() - HANDSHAKE_EVENT :::: sent ACKNOWLEDGE_NOTI");
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
			if(response.indexOf(EVENT_NITOFICATION) >= 0 || response.indexOf(GET_STATUS_HEADER) >= 0){
				console.log("#####onDate() - Calling callback with response :::: " + response);
				glb_serialPortListener.call(null, response);				
			}else{
				console.log("#####onDate() - Ignoring this event response :::: " + response);
			}
		}
	}
};

SerialConnection.prototype.serialPort_filterData = function(str) {

	if(!str) 
		return null;
	// debug("#input - str :: " + str);
	var str = str.replace(/\./g,"._");
	// debug("#input - replace :: " + str);
	
	var arrs = str.split("_");
	// debug("#splitted arrs :: " + arrs);
	
	var firstStr = arrs.splice(0,1).toString();
	var markerIndex = firstStr.indexOf(".");
	var returnValue = [];
	
	debug("#firstStr :: " + firstStr);
	// debug("#markerIndex :: " + markerIndex );
	
	this.response_Q = this.cleanArray(this.response_Q);
	
	if(markerIndex == -1){//'.' is not appear 
		debug("@@this.response_Q 1 - NONE BEFORE ::: " + this.response_Q.length);
		debug("@@arrs 1 - NONE BEFORE ::: " + arrs.length);
		if(this.response_Q.length > 0){
			this.response_Q[0] = this.response_Q[0] + firstStr;
		}else{
			this.response_Q = this.response_Q.concat(firstStr);	
		}
		debug("@@this.response_Q 1 - NONE :: " + this.response_Q);
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
		// debug("4. arrs ::: " + arrs);
		// debug("4. this.response_Q ::: " + this.response_Q);
		if (this.response_Q.length > 0){
			returnValue.push(this.response_Q.splice(0,1) + firstStr.replace(".",""));
		}else{
			returnValue.push(firstStr.replace(".",""));
		}

		debug("4. arrs ::: " + arrs);
		
		for(var idx in arrs){
			// debug("4. str ::: " + arrs[idx]);
			//found a finished response
			if(arrs[idx].indexOf(".") > 0){
				returnValue.push(arrs[idx].replace(".",""));	
				// debug("4. returnValue ::: " + returnValue);
			}else{
				//found a unfinished response
				this.response_Q = this.response_Q.concat(arrs[idx]);
				// debug("@@this.response_Q 4 - Multiple ::: " + this.response_Q);
			}
		}
		return returnValue;
	}

};

SerialConnection.prototype.isCmdReceivedNotification = function(response){
	if(response.indexOf("$OP") >= 0){
		// console.log("[isCmdReceivedNotification]::::This is a Cmd received notification - " + response);
		return true;
	}else if(response.indexOf("$SB") >= 0){
		// console.log("[isCmdReceivedNotification]::::This is a Cmd received notification - " + response);
		return true;		
	}
	return false;
};

SerialConnection.prototype.dequeueCompletedCmd = function(response) {
	var rceivedNotiLockerId = response.substring(5,8);
	var deleted = false;

	for(var index in this.waiting_Q){
		var queueLockerId = this.waiting_Q[index].substring(3,6);
		// console.log("[dequeueCompletedCmd - resend]:::rceivedNotiLockerId vs queueLockerId - " + rceivedNotiLockerId + " VS "+ queueLockerId);
		if(rceivedNotiLockerId === queueLockerId){
			var removedCmd = this.waiting_Q.splice(index, 1);
			// console.log("[dequeueCompletedCmd - resend]:::: command removed from :: waiting_Q" + removedCmd);
			deleted = true;
			break;
		}
	}

	if(!deleted){
		if(this.isCommandError(response)){
			// console.log("[dequeueCompletedCmd]:::: it's command error " + response);
			this.cmd_error_Q.push(this.waiting_Q.splice(0, 1)); 
		}else{
			// console.log("[dequeueCompletedCmd]:::: Something Wrong!! No commmand match to deque " + response);		
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

SerialConnection.prototype.isCommandStuck = function() {
	if(this.waiting_Q.length > 0){
		var waitingCmd = this.waiting_Q[0];

		if(this.theadCheck_Q[waitingCmd] && this.theadCheck_Q[waitingCmd] % this.stuck_thresholds_count == 0){
			// console.log("% isCommandStuck() - this thread stuck :: " + waitingCmd + " : "+ this.theadCheck_Q[waitingCmd]);
			this.theadCheck_Q[waitingCmd]++;
			return true;
		}else{
			
			if(this.theadCheck_Q[waitingCmd]){
				this.theadCheck_Q[waitingCmd]++;
			}else{
				this.theadCheck_Q[waitingCmd] = 1;
			}
			// console.log("#####:::waitingCmd ::: " + waitingCmd + ":::::" + this.theadCheck_Q[waitingCmd]);
			// console.log("% isCommandStuck() - this.theadCheck_Q[waitingCmd] :: " + this.theadCheck_Q[waitingCmd]);		
			return false;						
		}
	}
	// console.log("% isCommandStuck() - waiting_Q is empty :: " + this.theadCheck_Q);	
	return false;
};

SerialConnection.prototype.isWaiting_Q_Empty = function() {
	this.waiting_Q = this.cleanArray(this.waiting_Q);
	if(this.waiting_Q.length > 0 ){
		// console.log("% isWaiting_Q_Empty() - waiting_Q is not empty :: " + this.waiting_Q.length);		
		return false;
	}
	// console.log("% isWaiting_Q_Empty() - waiting_Q is empty :: " + this.waiting_Q);	
	return true;
};

SerialConnection.prototype.isCommand_Q_Empty = function() {
	this.command_Q = this.cleanArray(this.command_Q);
	if(this.command_Q.length > 0 && this.command_Q[0] != ""){
		// console.log("% isCommand_Q_Empty() - command_Q is not empty :: " + this.waiting_Q);		
		return false;
	}
	// console.log("% isCommand_Q_Empty() - command_Q is empty :: " + this.waiting_Q);
	return true;
};

SerialConnection.prototype.addCommand = function(action, lockerId) {
	console.log("[addCommand]::::START with - " + action + ", " + lockerId);
	var request = this.createRequest(action, lockerId);
	var interpreter = this.commandInterpreterManager.getCommandInterpreter("request", request.action);
    console.log("Obtained command interpreter " + interpreter);
	var strCommand = interpreter.interpretRequest(request);
	this.command_Q.push(strCommand);
	console.log("[addCommand]::::this.command_Q - " + this.command_Q);
};

SerialConnection.prototype.sendCommands = function(action, lockerIds){
	//Door open interval optimization.
	// if(action === "OPEN"){
	// 	console.log("[sendCommands()] lockerIds.length :: " + lockerIds.length);
	// 	this.setDoorOpenInterval(lockerIds.length);
	// }

	for(var idx in lockerIds){
		this.addCommand(action, lockerIds[idx]);
	}

	if(this.pollerMode == "INACTIVE"){
		
		this.sendSerialCommand(this.command_Q.splice(0,1).toString());
		// this.checkProcessCompeletedPoller(this.command_Q.length, true);	
		this.commandQ_Monitor(this.command_Q.length + 1);	
	}else{
		console.log("[sendCommands()]Poller is running!!!!!! commands are added to commnnd_Q");
	}
};

SerialConnection.prototype.sendCommand = function(action, lockerId) {
	console.log("[sendCommand]::::START with - " + action + ", " + lockerId);
	var request = this.createRequest(action, lockerId);
	var interpreter = this.commandInterpreterManager.getCommandInterpreter("request",
			request.action);
	console.log("Obtained command interpreter " + interpreter);

	var strCommand = interpreter.interpretRequest(request);
	this.sendSerialCommand(strCommand);
};

SerialConnection.prototype.sendSerialCommand = function(strCommand) {
	console.log("[sendSerialCommand()]::command => " + strCommand);
	var cmd = this.convertStringToCommand(strCommand);
	try{
		glb_serialPortDevice.write(this.hex2a(cmd));	
	}catch(e){
		throw "Can't send a serial command! Please check the Serial Connection"
	}
	
	
	this.waiting_Q.push(strCommand);
	console.log("[sendSerialCommand()]:: pushed into waiting_Q => " + this.waiting_Q);
};


SerialConnection.prototype.commandQ_Monitor = function(cmd_q_count) {
	this.pollerMode = "ACTIVE";
	var interval
	if(cmd_q_count == null){
		interval = this.interCommandInterval;
	}else{
		interval = this.doorOpeninterval + (this.interCommandInterval * cmd_q_count); //interval of opendoor process 100(should configurable)
	}
	console.log("[commandQ_Monitor]cmd_q_count :::: " + cmd_q_count);
	console.log("[commandQ_Monitor]TOTAL poller Interval is :::: " + interval);
	var self = this;
	setTimeout(function(cmd_q_count){
		
		// console.log("% checkProcessCompeletedPoller() - START with interval :: " + interval);
		if(self.isWaiting_Q_Empty() && self.isCommand_Q_Empty()){
			console.log("#################### POLLER END #################### ");
			if(self.cmd_error_Q.length > 0){
				console.log("#################### Failed command ::: " + self.cmd_error_Q);
			}
			//Initialize internal Qs
			self.cmd_error_Q = [];
			self.theadCheck_Q = {};
			self.pollerMode = "INACTIVE";
				
		}else{

			//INFINITE LOOP PROTECTION CODE START
			var waitingCmd = self.waiting_Q[0];
			console.log("% [commandQ_Monitor()] - waitingCmd ::: " + waitingCmd);

			if(self.theadCheck_Q[waitingCmd]){
				self.theadCheck_Q[waitingCmd]++;
			}else{
				self.theadCheck_Q[waitingCmd] = 1;
			}

			if(self.theadCheck_Q[waitingCmd] >= self.maxCmdStuckCount){
				console.log("% [commandQ_Monitor()] - self.theadCheck_Q[waitingCmd] ::: " + self.theadCheck_Q[waitingCmd]);
				//splice in here is not working, just make it "" array so array's length is 1.
				self.moveCmdWatingQtoErrorQ();
				self.theadCheck_Q[waitingCmd] = 0;
			}
			//INFINITE LOOP PROTECTION CODE END
			
			self.command_Q = [].concat(self.waiting_Q.splice(0).concat(self.command_Q.splice(0)));
			console.log("% commandQ_Monitor() - concat this.command_Q :: " + self.command_Q);
			
			self.sendSerialCommand(self.command_Q.splice(0,1).toString());
			self.commandQ_Monitor(self.command_Q.length + 1);
		}
		
	}, interval)
};


/*
 * use CommandMonitor() in IEC, too heavy for IEC but not web browsers.
 */
SerialConnection.prototype.checkProcessCompeletedPoller = function(cmd_q_count, sleepMode) {
	self.pollerMode = "INACTIVE";
	var interval
	if(cmd_q_count == null){
		interval = this.interCommandInterval;
	}else{
		interval = /*this.doorOpeninterval +*/ (this.interCommandInterval * cmd_q_count / this.interCommandInterval /100); //interval of opendoor process 100(should configurable)
	}
	
	console.log("TOTAL poller Interval is :::: " + interval);

	var self = this;
	setTimeout(function(cmd_q_count){
		console.log("$$$$$$$$$$$$$$$$$$$$ POLLER START - SleepMode :"+ sleepMode +" $$$$$$$$$$$$$$$$$$$$");
		self.pollerMode = "ACTIVE";
		// console.log("% checkProcessCompeletedPoller() - START with interval :: " + interval);
		if(self.isWaiting_Q_Empty() && self.isCommand_Q_Empty()){
			console.log("#################### POLLER END #################### ");
			if(self.cmd_error_Q.length > 0){
				console.log("#################### Failed command ::: " + self.cmd_error_Q);
				
			}
			//Initialize internal Qs
			self.cmd_error_Q = [];
			self.theadCheck_Q = {};
			
		}else if(sleepMode == true){
			console.log("% checkProcessCompeletedPoller() - with sleepMode :: " + sleepMode );
			//one of Qs are not empty.
			if(!self.isWaiting_Q_Empty()){
				if(self.isCommandStuck()){
					console.log("% checkProcessCompeletedPoller() - command stuck detected!!! " + self.waiting_Q);

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
				// console.log("% checkProcessCompeletedPoller() - self.theadCheck_Q[waitingCmd] ::: " + self.theadCheck_Q[waitingCmd]);
				//splice in here is not working, just make it "" array so array's length is 1.
				self.moveCmdWatingQtoErrorQ();
				self.theadCheck_Q[waitingCmd] = 0;	
			}
			//INFINITE LOOP PROTECTION CODE END

			self.command_Q = [].concat(self.waiting_Q.splice(0).concat(self.command_Q.splice(0)));
			// self.command_Q = [].concat(self.command_Q.splice(0).concat(self.waiting_Q.splice(0)));
			// console.log("% checkProcessCompeletedPoller() - concat this.command_Q :: " + self.command_Q);
			// self.theadCheck_Q = [];
			self.sendSerialCommand(self.command_Q.splice(0,1).toString());
			self.checkProcessCompeletedPoller(self.command_Q.length + 1, true);
		}
		
	}, interval)
};
