
var commandIntepreterManager = null;
var ADPTER_CORE_DEBUG = "OFF"


var debug = function(str){
	if(ADPTER_CORE_DEBUG=="ON")
		console.log(str);
}

var CommandIntepreterManager = function() {
	this.interpreters = new Object();
}

commandIntepreterManager = new CommandIntepreterManager();

CommandIntepreterManager.prototype.getCommandInterpreter = function(type, key) {
	if(type == "request"){
		for(var interpreter in this.interpreters){
			debug("##########interpreter" + interpreter);
			if(interpreter === key)
				return this.interpreters[key];
		}
	}else if (type == "response"){
		var id = '';
			for (cId in META.COMMAND) {
				if (META.COMMAND[cId].value === key) {
					id = META.COMMAND[cId].key;
					break;
				}
			}
			return this.interpreters[id];
	}
	return null;
}

CommandIntepreterManager.prototype.setCommadInterpreter = function(key, interpreter) {
	this.interpreters[key] = interpreter;
}



var CommandInterpreter = function (reqCmd, resCmd){
	this.reqCmd = reqCmd;
	this.resCmd = resCmd;
}

CommandInterpreter.prototype.interpreteRequest = function(reqeust) {
	debug("[requestInterperter()::##START##]  action : " + reqeust.action);
	
	var metaInfo = this.reqCmd.contents;

	if (metaInfo == null)
		throw "NO REQUEST META INFO FOUND : " + reqeust.action;

	var commandStr = '#';

	for ( var index in metaInfo) {
		var metaId = metaInfo[index].id;
		var value = eval("reqeust." + metaId);
		
		debug("[requestInterperter():metaId, requestValue] : " + metaId
				+ " , " + value);

		if (metaInfo[index].ref != null) {
			for ( var def in metaInfo[index].ref) {
				var found = false;
				debug("[meta property] :" + def);
				if (metaInfo[index].ref[def].key === value) {
					debug("[requestInterperter():FOUND] key : "
							+ metaInfo[index].ref[def].key + ", value : "
							+ metaInfo[index].ref[def].value + ", request : "
							+ value);
					commandStr += metaInfo[index].ref[def].value;
					found = true;
					break;
				}
			}
			if (!found)
				throw "BAD REQUEST - NO DEFINETION FOUND : " + value;
			
		} else {
			
			if (value.length < metaInfo[index].length){
				value = this.pad(value, metaInfo[index].length);
			}else if (value.length > metaInfo[index].length)
				throw "BAD REQUEST - INPUT VALUE OVERFLOW : " + value;

			commandStr += value;
		}

	}
	debug("requestInterperter()::##END##] commandStr : " + commandStr);
	return commandStr;
}

CommandInterpreter.prototype.interpreteResponse = function(responesStr) {
	//TODO when read() from serial, stop read when it found "0D". or filter it.
	debug("responseInterpreter()::@@START@@ responesStr.length : " + responesStr.length)
	responesStr = responesStr.substring(0 , responesStr.length -4);
	debug("responseInterpreter()::substring(1, 3)" + responesStr.substring(1, 3))
	
	var metaInfo = this.resCmd.contents;
	
	if (metaInfo == null)
		throw "NO RESPONSE META INFO FOUND : " + responesStr.substring(1, 3);

	var resultStr = '{';
	var startIndex = 1;
	var endIndex = 1;
	for ( var index in metaInfo) {	
		var id = metaInfo[index].id;
		
		endIndex = startIndex + metaInfo[index].length;
		var value = responesStr.substring(startIndex, endIndex);
		debug("[responseInterpreter():metaId, substring] : " + id + " , "
				+ value);
		
		if (metaInfo[index].ref != null) {
			value = this.convertStringToMetaDefinition(metaInfo[index].ref, value);
		}

		debug("[responseInterpreter():endIndex, responesStr.length] : " + endIndex + " , "
				+ responesStr.length);

		if (index == metaInfo.length - 1) {
			if( endIndex == responesStr.length){
				resultStr += '"' + id + '" : "' + value + '" }';	
			}else if(this.resCmd.repeatableContents != null){
				debug("[################:start, end] : " + endIndex + " , "
						+ endIndex);
				//handle Multi-responses 
				var idx = 1;
				var tempStr ='';
				var command = this.resCmd
				tempStr += '"' + id + '" : [ ';

				//TODO Refactor this algorithm 
				while(endIndex < responesStr.length){
					if(command.repeatableContents.length == 1){

						debug("[################:command.filter, keySet] : " + command.filter + " , "
								+ command.keySet[0]);
						if(command.filter != null){

							if(value != command.filter){
								tempStr += '{"';
								tempStr += command.keySet[0]
										+ '" : "' + idx + '" ,';
								tempStr += '"'
										+ command.repeatableContents[0].id
										+ '" : "' + value 
								tempStr += '" },';
							}
							
						}
						debug("[################:start, end] : " + startIndex + " , "
								+ endIndex);
						debug("#####################tempStr"+tempStr);

						startIndex = endIndex;
						endIndex = startIndex + command.repeatableContents[0].length;
						value = this.convertStringToMetaDefinition(command.repeatableContents[0].ref,
								responesStr.substring(startIndex, endIndex));
						idx ++;
						
					}
					else if (this.resCmd.repeatableContents.length > 1
							&& id == META.LOCKERS_STATUS.id) {
 						
						endIndex = startIndex + META.LOCKERS_STATUS.chunk;
						while(endIndex <= responesStr.length){

//							debug("[handleMultiResponse()]::endIndex: " + startIndex +", endIndex : " + endIndex);
							var strChunk = responesStr.substring(startIndex, endIndex); 
							debug("[handleMultiResponse()]::strChunk: " + strChunk);
							tempStr += '{"' + META.LOCKER_NUMBER.id + '" : "' + idx + '" ,';
							tempStr += '"'
									+ META.DOOR.id
									+ '" : "'
									+ this.convertStringToMetaDefinition(META.DOOR, strChunk
											.substring(0, 1)) + '" ,';
							tempStr += '"'
									+ META.PACKAGE.id
									+ '" : "'
									+ this.convertStringToMetaDefinition(META.PACKAGE, strChunk
											.substring(1, 2)) + '" ,';
							tempStr += '"'
									+ META.ALARM.STATUS.id
									+ '" : "'
									+ this.convertStringToMetaDefinition(META.ALARM.STATUS, strChunk
											.substring(2, 3)) + '" },';

							startIndex = endIndex;
							endIndex += META.LOCKERS_STATUS.chunk;
							idx ++;
						}
					}else{
						throw "THIS GONNA BE INFINTE LOOP : id => " + id;
					}

				}
				if(tempStr.lastIndexOf(',') == tempStr.length -1 ){
					tempStr = tempStr.substring(0, tempStr.length-1);
				}
				tempStr += ']}';
				
				resultStr += tempStr;
			}
		} else {
			resultStr += '"' + id + '" : "' + value + '" ,';
			startIndex += metaInfo[index].length;
		}
		
		// If errorintiMetaPropertyDefinitionust return error response.
		if(id == "response" && this.isError(value) == true){
			resultStr = resultStr.substring(0,resultStr.length - 1);
			resultStr += " }";
			return JSON.parse(resultStr);
		}
	}
	debug("[responseInterpreter():response] : " + resultStr);
	
	return JSON.parse(resultStr);
}

CommandInterpreter.prototype.isError = function(key) {
	if (key != META.RESPONSE.SUCCESS.key) {
		return true;
	}else
		return false;
}

CommandInterpreter.prototype.pad = function(n, width, z){
	z = z || '0';
	n = n + '';
	return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
}

CommandInterpreter.prototype.convertStringToMetaDefinition = function(metaInfoRef, value){
	for ( var def in metaInfoRef) {
		var found = false;
		debug("[responseInterpreter():Searching...]def : "+def+" : meta value : "
				+metaInfoRef[def].value + ", str value : "
				+ value);
		if (metaInfoRef[def].value === value) {
			debug("[responseInterpreter():FOUND] meta value : "
					+metaInfoRef[def].value + ", str value : "
					+ value);
			value = metaInfoRef[def].key;
			found = true;
			break;
		}
	}
	if (!found)
		throw "NO DEFINETION FOUND : value : " + value;
	
	return value;
}

var Command = function(contents, repeatableContents, repeatableKeySet, filter){
	this.contents = contents;
	this.repeatableContents = repeatableContents;
	this.keySet = repeatableKeySet;
	this.filter = filter
};

// CommandInterpreter.prototype.convertStringToCommand = function(str){
// 	var command = '';
// 	str = str + this.getBCC(str);
	
// 	for (var i = 0; i < str.length; i++) {
// 		var hex = str.charCodeAt(i).toString(16);
// //		command += '\\x' + str.charCodeAt(i).toString(16);
// 		command += str.charCodeAt(i).toString(16);
// 	}
// 	command += '0d';
// 	debug("[convertStringToCommand()] 3 : " + command);

// 	return command;
// };

// CommandInterpreter.prototype.hex2a = function(hex){
// 	var str = '';
// 	for (var i = 0; i < hex.length; i += 2)
// 		str += String.fromCharCode(parseInt(hex.substr(i, 2), 16));
// 	return str;
// };


// CommandInterpreter.prototype.getBCC = function(str){
// 	var hexAdded = 0;
// 	var hexAdded2 = 0;
// 	for (var i = 0; i < str.length; i++) {
// 		hexAdded += str.charCodeAt(i);
// 	}
// 	var result = Number(hexAdded).toString(16);
// 	debug("[BCC] result : " + result);
// 	debug("[BCC] " + result.substring(result.length - 2));
// 	return result.substring(result.length - 2);
// }

CommandInterpreter.prototype.deviceWrite = function(str){
	str = str + this.getBCC(str) + 'CR';
	debug("[convertStringToCommand()]  : " + str);
	device.write(str.split ('').map (function (c) { return c.charCodeAt (0); }));
}


CommandInterpreter.prototype.convertStringToHex = function(str){
	var hexStr = '';
	for (var i = 0; i < str.length; i++) {
		hexStr += '' + str.charCodeAt(i).toString(16);
	}
	debug("[convertStringToHex] result : " + hexStr);
	return hexStr;
}


function intiMetaPropertyDefinition() {
	//Content instance definition.
	var action = {
		id : META.COMMAND.id,
		ref : META.COMMAND,
		length : 2
	};
	var lockerGroupNo = {
			id : META.LOCKER_GROUP_NUMBER.id,
			ref : null,
			length : 2
		};
	var lockerNo = {
		id : META.LOCKER_NUMBER.id,
		ref : null,
		length : 1
	};
	var interval = {
		id : META.INTERVAL.id,
		ref : null,
		length : 2
	};
	var duration = {
			id : META.DURATION.id,
			ref : null,
			length : 2
		};
	var lockerCondition = {
		id : META.LOCKER_CONDITION.id,
		ref : META.LOCKER_CONDITION,
		length : 1
	};
	var alarmControl = {
		id : META.ALARM.CONTROL.id,
		ref : META.ALARM.CONTROL,
		length : 1
	}
	var alramStatus = {
			id :META.ALARM.STATUS.id,
			ref : META.ALARM.STATUS,
			length : 1
	}
	var doorStatus = {
			id : META.DOOR.id,
			ref : META.DOOR,
			length : 1
	}
	var pacakgeStatus = {
			id : META.PACKAGE.id,
			ref : META.PACKAGE,
			length : 1
	}
	var response = {
		id : META.RESPONSE.id,
		ref : META.RESPONSE,
		length : 2
	}
	var systemStatus = {
			id : META.SYSTEM_STATUS.id,
			ref : META.SYSTEM_STATUS,
			length : 1
	}
	var lockerGroupStatus = {
			id : META.LOCKER_GROUP_STATUS.id,
			ref : META.LOCKER_GROUP_STATUS,
			length : 1
	}
	var lockersStatus = {
			id : META.LOCKERS_STATUS.id,
			ref : null,
			length : 0
	}
	var doorOpenCount = {
			id : META.DOOR_OPEN_COUNT.id,
			ref : null,
			length : 5
	}
	
	/*
	 * OP - OPEN REQUEST & RESPONSE DEFINITION
	 */
	var OP_REQ = new Array();
	OP_REQ.push(action);
	OP_REQ.push(lockerGroupNo);
	OP_REQ.push(lockerNo);
	OP_REQ.push(interval);
	OP_REQ.push(lockerCondition);
	OP_REQ.push(alarmControl);

	var OP_RES = new Array();
	OP_RES.push(action);
	OP_RES.push(response);
	OP_RES.push(lockerGroupNo);
	OP_RES.push(lockerNo);

	commandIntepreterManager.setCommadInterpreter(META.COMMAND.OPEN.key,
			new CommandInterpreter(new Command(OP_REQ), new Command(OP_RES)));

	
	/*
	 * CL - CHANGE LOCKER CODITION REQUEST & RESPONSE DEFINITION
	 */
	var CL_REQ = new Array();
	CL_REQ.push(action);
	CL_REQ.push(lockerGroupNo);
	CL_REQ.push(lockerNo);
	CL_REQ.push(lockerCondition);
	CL_REQ.push(alarmControl);
	
	var CL_RES = new Array();
	CL_RES.push(action);
	CL_RES.push(response);
	CL_RES.push(lockerGroupNo);
	CL_RES.push(lockerNo);

	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.CHANGE_LOCKER_CONDITION.key, new CommandInterpreter(
					new Command(CL_REQ), new Command(CL_RES)));

	/*
	 * SB - GET STATUS REQUEST & RESPONSE DEFINITION
	 */
	var SB_REQ = new Array();
	SB_REQ.push(action);
	SB_REQ.push(lockerGroupNo);
	SB_REQ.push(lockerNo);
	
	var SB_RES = new Array();
	SB_RES.push(action);
	SB_RES.push(response);
	SB_RES.push(lockerGroupNo);
	SB_RES.push(lockerNo);
	SB_RES.push(doorStatus);
	SB_RES.push(pacakgeStatus);
	SB_RES.push(alramStatus);

	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.GET_STATUS.key, new CommandInterpreter(
					new Command(SB_REQ), new Command(SB_RES)));
	/*
	 * CW - SET CLOSE FORGET ALRAM REQUEST & RESPONSE DEFINITION
	 */
	var CW_REQ = new Array();
	CW_REQ.push(action);
	CW_REQ.push(alarmControl);
	CW_REQ.push(interval);
	CW_REQ.push(duration);
	
	var CW_RES = new Array();
	CW_RES.push(action);
	CW_RES.push(response);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.SET_CLOSE_FORGET.key, new CommandInterpreter(
					new Command(CW_REQ), new Command(CW_RES)));
	
	/*
	 * CR - GET CLOSE FORGET ALRAM STATUS REQUEST & RESPONSE DEFINITION
	 */
	var CR_REQ = new Array();
	CR_REQ.push(action);
	
	var CR_RES = new Array();
	CR_RES.push(action);
	CR_RES.push(response);
	CR_RES.push(alarmControl);
	CR_RES.push(interval);
	CR_RES.push(duration);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.GET_CLOSE_FORGET.key, new CommandInterpreter(
					new Command(CR_REQ), new Command(CR_RES)));

	/*
	 * FW - SET FORCE OPEN ALRAM REQUEST & RESPONSE DEFINITION
	 */
	var FW_REQ = new Array();
	FW_REQ.push(action);
	FW_REQ.push(alarmControl);
	FW_REQ.push(duration);
	
	var FW_RES = new Array();
	FW_RES.push(action);
	FW_RES.push(response);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.SET_FORCE_OPEN.key, new CommandInterpreter(
					new Command(FW_REQ), new Command(FW_RES)));
	
	/*
	 * FR - GET FORCE OPEN ALRAM REQUEST & RESPONSE DEFINITION
	 */
	var FR_REQ = new Array();
	FR_REQ.push(action);
	
	var FR_RES = new Array();
	FR_RES.push(action);
	FR_RES.push(response);
	FR_RES.push(alarmControl);
	FR_RES.push(duration);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.GET_FORCE_OPEN.key, new CommandInterpreter(
					new Command(FR_REQ), new Command(FR_RES)));
	
	/*
	 * RS - GET SYSTEM STATUS REQUEST & RESPONSE DEFINITION
	 */
	var RS_REQ = new Array();
	RS_REQ.push(action);
	
	var RS_RES = new Array();
	RS_RES.push(action);
	RS_RES.push(response);
	RS_RES.push(systemStatus);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.GET_SYSTEM_STATUS.key, new CommandInterpreter(
					new Command(RS_REQ), new Command(RS_RES)));
	
	/*
	 * RB - SYSTEM REBOOT REQUEST & RESPONSE DEFINITION
	 */
	var RB_REQ = new Array();
	RB_REQ.push(action);
	
	var RB_RES = new Array();
	RB_RES.push(action);
	RB_RES.push(response);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.SYSTEM_REBOOT.key, new CommandInterpreter(
					new Command(RB_REQ), new Command(RB_RES)));
	
	/*
	 * AP - GET LOCKER GROUP STATUS REQUEST & RESPONSE DEFINITION
	 */
	var AP_REQ = new Array();
	AP_REQ.push(action);
	
	var AP_RES = new Array();
	AP_RES.push(action);
	AP_RES.push(response);
	AP_RES.push(lockerGroupStatus);
	
	var AP_RES_REP = new Array();
	AP_RES_REP.push(lockerGroupStatus);
	
	var AP_RES_REP_KYES = new Array();
	AP_RES_REP_KYES.push(META.LOCKER_GROUP_NUMBER.id);
	
	commandIntepreterManager.setCommadInterpreter(
		META.COMMAND.GET_CONNECTED_LOCKER_GROUPS.key,
		new CommandInterpreter(new Command(AP_REQ), new Command(AP_RES,
				AP_RES_REP, AP_RES_REP_KYES,
				META.LOCKER_GROUP_STATUS.NOT_CONNECTED.key)));
	
	/*
	 * AB - GET THE STATUS OF THE LOCKERS OF A LOCKER GROUP REQUEST & RESPONSE DEFINITION
	 */
	var AB_REQ = new Array();
	AB_REQ.push(action);
	AB_REQ.push(lockerGroupNo);
	
	var AB_RES = new Array();
	AB_RES.push(action);
	AB_RES.push(response);
	AB_RES.push(lockerGroupNo);
	AB_RES.push(lockersStatus);
	
	var AB_RES_REP = new Array();
	AB_RES_REP.push(doorStatus);
	AB_RES_REP.push(pacakgeStatus);
	AB_RES_REP.push(alramStatus);
	
	var AB_RES_REP_KYES = new Array();
	AB_RES_REP_KYES.push(META.LOCKER_NUMBER.id);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.GET_LOCKERS_STATUS.key, new CommandInterpreter(
					new Command(AB_REQ), new Command(AB_RES,
							AB_RES_REP, AB_RES_REP_KYES)));
	
	/*
	 * WA - SET ALARM CONTROL REQUEST & RESPONSE DEFINITION
	 */
	var WA_REQ = new Array();
	WA_REQ.push(action);
	WA_REQ.push(lockerGroupNo);
	WA_REQ.push(lockerNo);
	WA_REQ.push(alarmControl);
	WA_REQ.push(duration);
	
	var WA_RES = new Array();
	WA_RES.push(action);
	WA_RES.push(response);
	WA_RES.push(lockerGroupNo);
	WA_RES.push(lockerNo);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.SET_ALARM_CONTROL.key, new CommandInterpreter(
					new Command(WA_REQ), new Command(WA_RES)));
	
	/*
	 * NB - GET DOOR OPEN COUNT REQUEST & RESPONSE DEFINITION
	 */
	var NB_REQ = new Array();
	NB_REQ.push(action);
	NB_REQ.push(lockerGroupNo);
	NB_REQ.push(lockerNo);
	
	var NB_RES = new Array();
	NB_RES.push(action);
	NB_RES.push(response);
	NB_RES.push(lockerGroupNo);
	NB_RES.push(lockerNo);
	NB_RES.push(doorOpenCount);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.GET_DOOR_OPEN_COUNT.key, new CommandInterpreter(
					new Command(NB_REQ), new Command(NB_RES)));
	
	/*
	 * NC - SET DOOR OPEN COUNT REQUEST & RESPONSE DEFINITION
	 */
	var NC_REQ = new Array();
	NC_REQ.push(action);
	NC_REQ.push(lockerGroupNo);
	NC_REQ.push(lockerNo);
	NC_REQ.push(doorOpenCount);
	
	var NC_RES = new Array();
	NC_RES.push(action);
	NC_RES.push(response);
	NC_RES.push(lockerGroupNo);
	NC_RES.push(lockerNo);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.SET_DOOR_OPEN_COUNT.key, new CommandInterpreter(
					new Command(NC_REQ), new Command(NC_RES)));
	
	/*
	 * EB - A LOCKER STATUS CHANGE NOTIFICATION REQUEST & RESPONSE DEFINITION
	 */
	var EB_REQ = new Array();
	EB_REQ.push(action);
	EB_REQ.push(response);
	
	var EB_RES = new Array();
	EB_RES.push(action);
	EB_RES.push(lockerGroupNo);
	EB_RES.push(lockerNo);
	EB_RES.push(doorStatus);
	EB_RES.push(pacakgeStatus);
	EB_RES.push(alramStatus);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.NOTI_LOCKER_STATUS.key, new CommandInterpreter(
					new Command(EB_REQ), new Command(EB_RES)));
	
	/*
	 * ES - A LOCKER GROUP CONNECTION CHANGE NOTIFICATION REQUEST & RESPONSE DEFINITION
	 */
	var ES_REQ = new Array();
	ES_REQ.push(action);
	ES_REQ.push(response);
	
	var ES_RES = new Array();
	ES_RES.push(action);
	ES_RES.push(systemStatus);
	
	commandIntepreterManager.setCommadInterpreter(
			META.COMMAND.NOTI_SYSTEM_STATUS.key, new CommandInterpreter(
					new Command(ES_REQ), new Command(ES_RES)));
}
