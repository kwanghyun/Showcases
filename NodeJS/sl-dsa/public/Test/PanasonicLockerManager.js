'use strict'

var ADPTER_CORE_DEBUG = "OFF";
var debug = function(str){
	if(ADPTER_CORE_DEBUG=="ON")
		console.log(str);
};
var INITIAL_GET_LOCKER_STATUS_INTERVAL = 5000;

var PanasonicLockerManager = function(lockerConfig, lockers, initialStatusCheck) {
	var _lockerMap = {};
	var _lockerIdMap = {};
	var _conn;
	var isSensorEnabled = lockerConfig.isSensorEnabled;

	var doorStateMap = {
		0: "CLOSE",
		1: "OPEN",
        2: "FORCE_OPEN",
        3: "ERROR", //DOOR_ERROR1
        4: "ERROR", //DOOR_ERROR2
        9: "ERROR" //NOT_CONNECTED
	};

	var occupancyStatusMap = {
		0: "VACANT",
		1: "OCCUPIED",
        2: "UNKNOWN",
        9: "ERROR"
	};

	var alarmStateMap = {
		0: "OFF",
		1: "ON", //"CLOSE_FORGOT_ON",
		2: "ON", //"FORCE_OPEN_ON",
		3: "CLOSE_FORGOT_COUTING", 
		9: "ERROR"
	};

	init(lockers, initialStatusCheck);

	function init(lockers, initialStatusCheck) {
		lockers.map(function(locker) {
			_lockerMap[locker.physicalId] = locker;
			_lockerIdMap[locker.logicalId] = locker.physicalId;
		});

		_conn = new SerialConnection(eventHandler);	

		if(initialStatusCheck){
			setTimeout(function(){
				getInitialLockerStatus();
			}, INITIAL_GET_LOCKER_STATUS_INTERVAL)			
		}
	}

	this.getConnection = function(){
		return _conn;
	};

	this.setConnection = function(connection){
		_conn = connection;
	};

	this.getEventHandler = function(){
		return eventHandler;
	};

	function getInitialLockerStatus(){
        var lockerIds = Object.keys(_lockerMap); 
        console.log(lockerIds);
        _conn.sendCommands("GET_STATUS", lockerIds);
	};

	function eventHandler(response){
		
		console.log("[eventHandler()]::::response::::"+ response);
		var EVENT_NOTI_HEADER = "#EB";
		var GET_STATUS_HEADER = "$SB";
		var header = response.substring(0,3);
		var fireEvent = false; 
		var offset;
		
		if(header == EVENT_NOTI_HEADER){
				offset = 0;	
			}else if(header == GET_STATUS_HEADER){
				offset = 2;
			}else{
				return;
		}

		var physicalId = response.substring(3  + offset, 6 + offset);
		var logicalId = _lockerMap[physicalId].logicalId;
		
		var currentDoorState = _lockerMap[physicalId].doorState;
		var newDoorState = doorStateMap[response.substring(6 + offset, 7 + offset)];

		var currentOccupancyStatus = _lockerMap[physicalId].occupancyStatus;
		var newOccupancyStatus = occupancyStatusMap[response.substring(7 + offset, 8 + offset)];

		var alarmState = _lockerMap[physicalId].alarmState;
		var newAlarmState = alarmStateMap[response.substring(8 + offset, 9 + offset)];

		if(header == GET_STATUS_HEADER){
			fireEvent = true;
		}else if(currentDoorState !== newDoorState
			|| currentOccupancyStatus !== newOccupancyStatus
			|| alarmState !== newAlarmState) {

			if(currentDoorState === "OPEN" && newDoorState === "OPEN" && alarmState === newAlarmState){
				console.log("##[eventHandler] ==> Ignore ocuupancy event when door is open");					
				fireEvent = false;
				return;
			}
			fireEvent = true;
		}

		if(fireEvent){
			_lockerMap[physicalId].doorState = newDoorState;
			_lockerMap[physicalId].occupancyStatus = newOccupancyStatus;
			_lockerMap[physicalId].alarmState = newAlarmState;

			jQuery(document).trigger("lockerStateChanged", [_lockerMap[physicalId]]);
		}

		return;
	};

	this.invokeEventHanlder = function(response){
		return eventHandler(response);
	}

	this.open = function(logicalIds) {
		console.log("logicalIds :: " + logicalIds);
		var physicalIds = logicalIds.map(function(logicalId){
			return _lockerIdMap[logicalId];
		});
		
		_conn.sendCommands("OPEN", physicalIds);

		//Just in case door is already open
		physicalIds.forEach(function(physicalId){
			if(_lockerMap[physicalId] && _lockerMap[physicalId].doorState ==="OPEN"){
				console.log("##Door is alreday open, but still triggering event!!!!");
				jQuery(document).trigger("lockerStateChanged", [_lockerMap[physicalId]]);
			}			
		});
	};

	this.getStatus = function(logicalIds) {
		//Get all status when no lockerid is specified 
		if(logicalIds == null){
			_conn.sendCommands("GET_STATUS", Object.keys(_lockerMap));
		}else{
			var physicalIds = logicalIds.map(function(logicalId){
				return _lockerIdMap[logicalId];
			});

			_conn.sendCommands("GET_STATUS", physicalIds);
		}
	};

};
