"use strict";

Function.prototype.extends = function(superclass) {
	this.prototype = Object.create(superclass.prototype);
	this.prototype.constructor = this;
}

var lockerManager = null;
var glb_isSimulator = false;
var lockerList = [];
var lockers = null;
var cmdConflictCount = 0;
var totalResponseCount = 0;
var scope = null;

var LOCKER_STATE = {
	DOOR : {
		CLOSED : "CLOSED",
		OPEN : "OPEN",
		FORCE_OPEN : "FORCE_OPEN",
		ERROR : "ERROR"
	},
	PACKAGE : {
		OUT : "OUT",
		IN : "IN",
		ERROR : "ERROR"
	},
	ALARM : {
		OFF : "OFF",
		ON : "ON",
		ERROR : "ERROR"
	}
}


var app = angular.module('LockerSimApp', []);

app.controller('lockerCtrl', function($scope) {
    $scope.package = "";
    $scope.kpiEvent = "";
    $scope.auditEvent = "";
    $scope.status = "";
    
    $scope.lockerList = [];
    main($scope.lockerList);
    scope = $scope;

    console.log("$scope.lockerList Size => " + $scope.lockerList.length);
    
    $scope.fireStaged = function() {
    	console.log("fireStaged()....");
		console.log("Updating pacakge :: " + JSON.stringify($scope.package));
		DSAManager.processPackageInfoChange($scope.package);	
    }

    $scope.fireKpi = function() {
    	console.log("fireKpi()....");
		console.log("Updating KPI :: " + JSON.stringify($scope.kpiEvent));
		DSAManager.updateKpiPayload($scope.kpiEvent);
    }

    $scope.fireAudit = function() {
    	console.log("fireAudit()....");
		console.log("Updating Audit :: " + JSON.stringify($scope.auditEvent));
		DSAManager.updateAuditPayload($scope.auditEvent);	

    }

    $scope.print = function() {
    	console.log("print()....");
        var _this = {
            scanData : {
                packageId : "12345667",
                logicalId : "logical-id"
            }
        };

        var win = {
            AppSettings : {
                site : "site-id",
                bank : "bank-id"
            }               
        }
       	
		try{
			var printingMessage = PrinterMessageBuilder.createInstance()
				.appendMessage("Keep this receipt for your records")
				.addLineFeed()
				.appendMessage("The following order was returned by you:")
				.appendMessage(_this.scanData.packageId)
				.addLineFeed()
				.appendMessage("At the following location")
				.appendMessage("Site Id: " + win.AppSettings.site + ", Bank Id: " + win.AppSettings.bank + ", Locker Id" + _this.scanData.logicalId)
				.build();

		}catch(e){
			console.log("ERROR :: " + e);
		}

		PrinterManager.print(printingMessage);
    }

    $scope.openAll = function() {
    	console.log("openAll()....");
		// var logical_id_List = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"];
		// var logical_id_List = ["1","2","3","4","5","6","7","8","9","10","11","12"]; //You can open 12 door at maximum.
		var logical_id_List = ["1","2","3","4","5","6"];
		// var logical_id_List = ["1"];
		// lockerManager.updateLockerStateForBulkOpen(logical_id_List);
		
		lockerManager.open(logical_id_List);
    }

    $scope.openDoor = function(logical_id) {
    	console.log("openDoor() logical_id => " + logical_id);
		var logical_id_List = [logical_id];
		lockerManager.open(logical_id_List);
    }

    $scope.closeAll = function() {
    	console.log("closeAll()....");
		// var logical_id_List = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"];
		
		var logical_id_List = ["1","2","3","4","5","6"];
		// lockerManager.updateLockerStateForBulkOpen(logical_id_List);
		for( var idx in logical_id_List)
			lockerManager.getConnection().onResponseSimulator("#CL", lockers.getLockerById(logical_id_List[idx]));
    }

    $scope.closeDoor = function(locker) {
		lockerManager.getConnection().onResponseSimulator("#CL", locker);
    } 

    $scope.getStatusAll = function() {
    	console.log("getStatusAll()....");
		var logical_id_List = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"];
		// lockerManager.getStatus(logical_id_List );
		lockerManager.getStatus();
    }

    $scope.pushConfig = function() {
    	console.log("pushConfig()....");
        $scope.status = "pushConfig()....";
    }


    $scope.togglePackageStatus = function(locker) {
    	if(locker.occupancyStatus === LOCKER_STATE.PACKAGE.OUT)
    		locker.occupancyStatus = LOCKER_STATE.PACKAGE.IN;
    	else
    		locker.occupancyStatus = LOCKER_STATE.PACKAGE.OUT;
    	
		lockerManager.getConnection().onResponseSimulator("#EB", locker);
    } 

    $scope.toggleAlarmStatus = function(locker) {
    	if(locker.alarmState === LOCKER_STATE.ALARM.OFF)
    		locker.alarmState = LOCKER_STATE.ALARM.ON;
    	else
    		locker.alarmState = LOCKER_STATE.ALARM.OFF;
    	
    	
		lockerManager.getConnection().onResponseSimulator("#EB", locker);
    } 

    $scope.$on('lockerStatusChanged', function(event, lockerEvent){
    	$scope.$apply(function () {
	    	$scope.lockerList.forEach(function(locker){
	    		if(locker.physicalId === lockerEvent.physicalId){
	    			locker.doorState = lockerEvent.doorState;
	    			locker.occupancyStatus = lockerEvent.occupancyStatus;
	    			locker.alarmState = lockerEvent.alarmState;
	    		}
	    	});

    	});
    	DSAManager.processLockerStateChange(lockerEvent);
    });

});


var main = function(list){

	// var brokerHost = "http://localhost:8080";
	var brokerHost = "http://10.106.8.159:8080";
	// var brokerHost = "http://10.106.9.143:8080";
	// var brokerHost = "dglux.directcode.org/conn";

	// var brok/erHost = "https://hostssl.dglux.com"
	// var brokerHost = "http://rnd.iot-dsa.org:8081";
	
	var lockerMode = "PanasonicLockerSimulator";
	// var lockerMode = "PanasonicLockerManager";
	lockerList = getLockerList();


	if(lockerMode === "PanasonicLockerSimulator"){
		console.log("PanasonicLockerSimulator Intialied.....");
		// lockerManager = new PanasonicLockerSimulator({isSensorEnabled:true}, lockerList, true);
		lockerManager = new PanasonicLockerSimulator({isSensorEnabled:true}, lockerList, false);
		lockerManager.setConnection(new SerialConnectionSimulator(lockerManager.getEventHandler()));
		glb_isSimulator = true;			
	}else{
		console.log("PanasonicLockerManager Intialied.....");
		lockerManager = LockerManager.getInstance({isSensorEnabled:true}, lockerList);
	}


	
	// var serverDSLinkName = "SmartLocker-Server";
	var deviceId = "testSite-testBank";
	var serverDSLinkName = "server";
	// var deviceId = "nodeResponder-1";


	// Should be within initialize method
	DSAManager.init(brokerHost, deviceId, lockerList, lockerManager, serverDSLinkName);

	lockers = new Lockers();

	for(var idx in lockerList){
		var locker = new Locker(lockerList[idx].logicalId, lockerList[idx].physicalId);
		locker.doorState = "CLOSE"
		locker.occupancyStatus = "OUT"
		locker.alarmState = "OFF"
		lockers.add(locker.logicalId, locker);
		list.push(locker);
	}
	
	// PrinterManager.init();


	// console.log(JSON.stringify(lockers));
//TODO Locker bank - door 
};

var Lockers = function() {
	this.lockers = {};
};

Lockers.prototype.add = function(id, locker){
	this.lockers[id] = locker;
};

Lockers.prototype.addAll = function(lockers){
	lockers.forEach(function(locker){
		this.lockers.add(locker.phsycalId, locker);
	});
};

Lockers.prototype.getLockerById = function(id){
	return this.lockers[id];
};

Lockers.prototype.getLockerByProperty = function(key, value){

	for(var id in this.lockers){
		var locker = this.lockers[id];

		if(locker[key] == value){
			return this.lockers[id];
		}
	}
};


var SerialConnectionSimulator = function(dataHandler){
	SerialConnection.call(this, dataHandler);
	this.eventInterval = 60;
	//Out of 100
	this.cmdFailPercentage = 0;
	// this.cmdFailPercentage = 100;
}
SerialConnectionSimulator.extends(SerialConnection);


SerialConnectionSimulator.prototype.sendSerialCommand = function(strCommand) {
	console.log("[Simulator - sendSerialCommand()]::command => " + strCommand);
	//	this.device.write(strCommand);

	var cmd = this.convertStringToCommand(strCommand);
	// glb_serialPortDevice.write(this.hex2a(cmd));
	console.log("[Simulator - sendSerialCommand()]::command => " + cmd);
	this.waiting_Q.push(strCommand);
	console.log("[Simulator - sendSerialCommand()]::this.waiting_Q => " + this.waiting_Q);
	var radValue = Math.floor((Math.random() * 100) + 1);
	

	if(radValue > this.cmdFailPercentage){
		//Simulate received event from LCU.

		var cmd = strCommand.substring(0, 3);
		var physical_id = strCommand.substring(3, 6);

		this.onResponseSimulator(cmd, lockers.getLockerByProperty("physicalId", physical_id));
	}else{
		cmdConflictCount ++;
		////Simulate command conflit, so no response from LCU
		console.log("#####################!!!!!! Oops some commands are conflicted (" + cmdConflictCount + ")!!!!!#####################");
	}
};

SerialConnectionSimulator.prototype.onResponseSimulator = function(cmd, locker) {
	console.log(JSON.stringify(locker));
	console.log("### onResponseSimulator() START with cmd :: " + cmd + ", lockerId :: " + locker.physicalId);


	totalResponseCount ++;
	var logical_id = locker.logicalId;
	var physical_id = locker.physicalId;
	var doorState = locker.doorState;
	var occupancyStatus = locker.occupancyStatus;
	var alarmState = locker.alarmState;
	var eventList = [];	

	console.log("##[onResponseSimulator1] logical_id :: [" + logical_id 
		+ "] - physical_id :: [" + physical_id
		+ "] - doorState :: [" + doorState 
		+ "] - occupancyStatus :: [" + occupancyStatus  
		+ "] - alarmState :: [" + alarmState + "]");

	switch (cmd) {
	case "#OP":
		if(lockerManager.countOpenDoor() >= 16){
			lockers.getLockerById(logical_id).doorState = "ERROR";
			doorState = "ERROR";
			eventList = ["$OP12000B6."];
		}else{
			lockers.getLockerById(logical_id).doorState = "OPEN";
			doorState = "OPEN";
			eventList = ["$OP00" + physical_id +"B5.", 
			"#EB" + physical_id + convertDoorStateToNumber(doorState) + convertPkgStateToNumber(occupancyStatus) + convertAlarmStateToNumber(alarmState) + "CD.", 
			"#EB" + physical_id + convertDoorStateToNumber(doorState) + convertPkgStateToNumber(occupancyStatus) + convertAlarmStateToNumber(alarmState) + "CD.", 
			"#EB" + physical_id + convertDoorStateToNumber(doorState) + convertPkgStateToNumber(occupancyStatus) + convertAlarmStateToNumber(alarmState) + "CD." ];
		}
		break;
	case "#SB":
		if(physical_id == "036"){
			//simulating BCC error, unexpected resonse handling Test
			eventList = ["!0182."];
		}else{
			eventList = [
			"$SB00" + physical_id + convertDoorStateToNumber(doorState) + convertPkgStateToNumber(occupancyStatus) + convertAlarmStateToNumber(alarmState) + "CD."];	
		}
		
		break;
	case "#CL":
		lockers.getLockerById(logical_id).doorState = "CLOSE";
		doorState = "CLOSE";
	case "#EB":
		eventList = [
			"#EB" + physical_id + convertDoorStateToNumber(doorState) + convertPkgStateToNumber(occupancyStatus) + convertAlarmStateToNumber(alarmState) + "CD.", 
			"#EB" + physical_id + convertDoorStateToNumber(doorState) + convertPkgStateToNumber(occupancyStatus) + convertAlarmStateToNumber(alarmState) + "CD.", 
			"#EB" + physical_id + convertDoorStateToNumber(doorState) + convertPkgStateToNumber(occupancyStatus) + convertAlarmStateToNumber(alarmState) + "CD." ];

		break;
	default:
		throw "onResponseSimulator():::No-Match found"
	}

	console.log("##[onResponseSimulator2] logical_id :: [" + logical_id 
		+ "] - doorState :: [" + doorState
		+ "] - occupancyStatus :: [" + occupancyStatus  
		+ "] - alarmState :: [" + alarmState + "]");

	console.log("eventList :: " + JSON.stringify(eventList));

	this.eventEmitter(eventList, this.eventInterval);
	// this.changeLockerStateTimer(lockerId, doorState, occupancyStatus, alarmState , this.eventInterval);
	this.changeLockerStateTimer(lockers.getLockerById(logical_id), this.eventInterval);
};

SerialConnectionSimulator.prototype.eventEmitter = function(evnetList, interval) {
	var self = this;
    if (evnetList.length>0)
        var aEvent = evnetList.splice(0,1)
    else 
        return;

    setTimeout(function () {
        // console.log("% eventEmitter() aEvent :: " + aEvent);
        self.onData(aEvent.toString());
        self.eventEmitter(evnetList, interval);
    }, interval);
};

SerialConnectionSimulator.prototype.changeLockerStateTimer = function(locker , interval) {
	setTimeout(function(){
		// console.log("% changeLockerStateTimer() - lockerId :: " + locker.id);
		// changeLokcerStates(locker.logicalId, locker.doorState, locker.occupancyStatus, locker.alarmState);
		changeLokcerStates(locker);
	}, interval)
};


SerialConnectionSimulator.prototype.onData = function(responseStr) {

	var HANDSHAKE_EVENT = '#ES1EC';
	var EVENT_NITOFICATION = '#EB';
	var GET_STATUS_HEADER = '$SB';
	var ACKNOWLEDGE_NOTI = '\x24\x45\x53\x30\x30\x2a\x2a\x0d'; //OK

	//	var data = this.device.readAll();
	// var data = glb_serialPortDevice.readAll();
	
	// var responseStr = "";
	// for (var i = 0; i < data.length; ++i) {
	// 	responseStr += data[i] < 32 ? '.' : String.fromCharCode(data[i]);
	// }

	// console.log("#####onDate() raw String ::::" + responseStr);

	if(responseStr.indexOf(EVENT_NITOFICATION) >= 0){
		// console.log("#####onDate() - EVENT_NITOFICATION :::: sent ACKNOWLEDGE_NOTI");
		// glb_serialPortDevice.write(ACKNOWLEDGE_NOTI);				
	}
	
//	if(str.startsWith(HANDSHAKE_EVENT)){//startwith() not working
	var responseArrs = [];
	responseArrs = responseArrs.concat(
		this.serialPort_filterData(responseStr));
	console.log("3.#####onDate() filtered result arrs ::::" + responseArrs);
	// console.log("4.#####onDate() for - responesArrs.length ::::" + responseArrs.length);	

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
			if(this.isCmdReceivedNotification(response)){
				this.dequeueCompletedCmd(response);
				this.sendNextCommand(response);
			}else if(this.isCmdErrorNotification(response)){
				console.log("#####onDate() - Command Error Notification :::: " + response);
				console.log("#####onDate() - Move the command [" + response + "] to Error_Q");
				this.moveCmdWatingQtoErrorQ();
				this.sendNextCommand(response);
			}

			//EVENT NOTIFICATION 
			if(response.indexOf(EVENT_NITOFICATION) >= 0 || response.indexOf(GET_STATUS_HEADER) >= 0){
				console.log("#####onDate() - Calling callback with response :::: " + response);
				this.listener.call(null, response);				
			}else{
				console.log("#####onDate() - Ignoring this event response :::: " + response);
			}
		}
	}
};


var PanasonicLockerSimulator = function(lockerConfig, lockers, initialStatusCheck) {
	PanasonicLockerManager.call(this, lockerConfig, lockers, initialStatusCheck);
};

PanasonicLockerSimulator.extends(PanasonicLockerManager);

PanasonicLockerSimulator.prototype.countOpenDoor = function() {
	var openDoorCount = 0;
	for(var id in this.lockers){
		if(this.getLockerById(id).doorState == LOCKER_STATE.DOOR.OPEN){
			openDoorCount++;
		}
			
	}
	return openDoorCount;
};


function changeLokcerStates(locker){
	scope.$emit('lockerStatusChanged', locker);
}



// function convertDoorStateToString(state){
// 	var state = parseInt(state);
// 	switch (state){
// 	case 0:
// 		return "CLOSE";
// 	case 1:
// 		return "OPEN";
// 	case 2:
// 		return "FORCE_OPEN";
// 	case 9:
// 		return "ERROR";
// 	default :
// 		return "__NO_MATCH__";	
// 	}
// };

// function convertPkgStateToString(state){
// 	var state = parseInt(state);
// 	switch (state){
// 	case 0:
// 		return "OUT";
// 	case 1:
// 		return "IN";
// 	case 9:
// 		return "ERROR";
// 	default :
// 		return "__NO_MATCH__";	
// 	}
// };

// function convertAlarmStateToString(state){
// 	var state = parseInt(state);
// 	switch (state){
// 	case 0:
// 		return "OFF";
// 	case 1:
// 		return "ON";
// 	case 9:
// 		return "ERROR";
// 	default :
// 		return "__NO_MATCH__";	
// 	}
// };

function convertDoorStateToNumber(state){
	switch (state){
	case "CLOSE":
		return 0;
	case "OPEN":
		return 1;
	case "FORCE_OPEN":
		return 2;
	case "ERROR":
		return 9;
	default :
		return "__NO_MATCH__";	
	}
};

function convertPkgStateToNumber(state){
	switch (state){
	case "OUT":
		return 0;
	case "IN":
		return 1;
	case "ERROR":
		return 9;
	default :
		return "__NO_MATCH__";	
	}
};

function convertAlarmStateToNumber(state){
	switch (state){
	case "OFF":
		return 0;
	case "ON":
		return 1;
	case "ERROR":
		return 9;
	default :
		return "__NO_MATCH__";	
	}
};

function getLockerList(){
	var lockerList = [
		{
			"logicalId" : "1",
			"physicalId" : "011"
		},
		{
			"logicalId" : "2",
			"physicalId" : "012"
		},
		{
			"logicalId" : "3",
			"physicalId" : "013"
		},
		{
			"logicalId" : "4",
			"physicalId" : "014"
		},
		{
			"logicalId" : "5",
			"physicalId" : "015"
		},
		{
			"logicalId" : "6",
			"physicalId" : "016"
		},
		{
			"logicalId" : "7",
			"physicalId" : "021"
		},
		{
			"logicalId" : "8",
			"physicalId" : "022"
		},
		{
			"logicalId" : "9",
			"physicalId" : "023"
		},
		{
			"logicalId" : "10",
			"physicalId" : "024"
		},
		{
			"logicalId" : "11",
			"physicalId" : "025"
		},
		{
			"logicalId" : "12",
			"physicalId" : "026"
		},
		{
			"logicalId" : "13",
			"physicalId" : "031"
		},
		{
			"logicalId" : "14",
			"physicalId" : "032"
		},
		{
			"logicalId" : "15",
			"physicalId" : "033"
		}
	];
	return lockerList;
}


