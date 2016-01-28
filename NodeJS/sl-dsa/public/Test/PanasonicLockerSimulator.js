"use strict";

$(document).ready(function(){

	Function.prototype.extends = function(superclass) {
		this.prototype = Object.create(superclass.prototype);
		this.prototype.constructor = this;
	}

	var client = null;
	var lockerManager = null;
	var provider = null;
	var lockerModel = '';
	var kiosk_app_version = 0.0;
	var totalResponseCount = 0;
	var cmdConflictCount = 0;
	var lockers = {};

	var glb_isSimulator = false;

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

	// var openDoorCount = 0;

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
			changeLokcerStates(locker.logicalId, locker.doorState, locker.occupancyStatus, locker.alarmState);
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

	function getDoorStateFromUI(lockerId){
		console.log("##DOOR :: " + $('#door_' + lockerId).is(":checked"));
		if($('#door_' + lockerId).is(":checked")){
			return LOCKER_STATE.DOOR.OPEN;
		}else{
			return LOCKER_STATE.DOOR.CLOSED;
		}
	};

	function getPkgStateFromUI(lockerId){
		console.log("##PKG :: " + $('#pkg_' + lockerId).is(":checked"));
		if($('#pkg_' + lockerId).is(":checked")){
			return LOCKER_STATE.PACKAGE.IN;
		}else{
			return LOCKER_STATE.PACKAGE.OUT;
		}
	};

	function getAlarmStateFromUI(lockerId){
		console.log("##ALARM :: " + $('#alarm_' + lockerId).is(":checked"));
		if($('#alarm_' + lockerId).is(":checked")){
			return LOCKER_STATE.ALARM.ON;
		}else{
			return LOCKER_STATE.ALARM.OFF;
		}
	}
	
	function changeLokcerStates(lockerId, doorState, occupancyStatus, alarmState){
		// console.log("doorState :: " + doorState);
		// console.log("occupancyStatus :: " + occupancyStatus);
		// console.log("alarmState :: " + alarmState);
		// var doorState = convertDoorStateToString(doorState);
		// var occupancyStatus = convertPkgStateToString(occupancyStatus);
		// var alarmState = convertAlarmStateToString(alarmState);

		var doorState = doorState;
		var occupancyStatus = occupancyStatus;
		var alarmState = alarmState;

		if(doorState == "OPEN"){
			$("#doorState" + lockerId).addClass("doorOpen");		
			$("#doorState" + lockerId).removeClass("doorClose");
			$("#doorState" + lockerId).removeClass("error");
			$('#door_' + lockerId).prop('checked', true);
		}else if(doorState == "ERROR"){
			$("#doorState" + lockerId).addClass("error");		
			$("#doorState" + lockerId).removeClass("doorClose");
			$("#doorState" + lockerId).removeClass("doorOpen");
			$('#door_' + lockerId).prop('checked', false);
		}else{
			$("#doorState" + lockerId).addClass("doorClose");		
			$("#doorState" + lockerId).removeClass("doorOpen");	
			$("#doorState" + lockerId).removeClass("error");	
			$('#door_' + lockerId).prop('checked', false);
		}
		$("#doorState" + lockerId).text(doorState);

		if(occupancyStatus == "IN"){
			$("#occupancyStatus" + lockerId).addClass("pkg_in");		
			$("#occupancyStatus" + lockerId).removeClass("pkg_out");
			$("#occupancyStatus" + lockerId).removeClass("error");	
		}else if(occupancyStatus == "ERROR"){
			$("#occupancyStatus" + lockerId).addClass("error");		
			$("#occupancyStatus" + lockerId).removeClass("pkg_in");
			$("#occupancyStatus" + lockerId).removeClass("pkg_out");
		}else{
			$("#occupancyStatus" + lockerId).addClass("pkg_out");		
			$("#occupancyStatus" + lockerId).removeClass("pkg_in");
			$("#occupancyStatus" + lockerId).removeClass("error");			
		}		
		$("#occupancyStatus" + lockerId).text(occupancyStatus);

		if(alarmState == "ON"){
			$("#alarmState" + lockerId).addClass("alarm_on");
			// $("#alarmState" + lockerId).animate("fast", function() {
			//     $(this).addClass("alarm_on");
			// });
			$("#alarmState" + lockerId).removeClass("alarm_off");
			$("#alarmState" + lockerId).removeClass("error");	
		}else if(alarmState == "ERROR"){
			$("#alarmState" + lockerId).addClass("error");		
			$("#alarmState" + lockerId).removeClass("alarm_on");
			$("#alarmState" + lockerId).removeClass("alarm_off");
		}else{
			$("#alarmState" + lockerId).addClass("alarm_off");		
			$("#alarmState" + lockerId).removeClass("alarm_on");	
			$("#alarmState" + lockerId).removeClass("error");			
		}				
		$("#alarmState" + lockerId).text(alarmState);
	};
	
	function createRequestTableRows(locker){
		var str = '<tr><td><div id="lockerId'+locker.logicalId+'">'+ locker.logicalId + ' ('+ locker.physicalId +')</div></td>';
		str += '<td ><div id="doorState'+locker.logicalId+'" class="locerk_state" >'+ locker.doorState +'</div></td>';
		str += '<td ><div id="occupancyStatus'+locker.logicalId+'" class="locerk_state">'+ locker.occupancyStatus +'</div></td>';
		str += '<td><div id="alarmState'+locker.logicalId+'" class="locerk_state">' + locker.alarmState +'</div></td>';
		str += '<td>'
		str += '<input type="button" id="open_'+locker.logicalId+'" value ="OPEN" class="openButtons" />';
		str += '<input type="button" id="close_'+locker.logicalId+'" value ="CLOSE" class="closeButtons" />';
		str += '<input type = "button" id="getStatus'+locker.logicalId+'" value ="GET_STATUS" class="getStatusButtons" />';
		str += '[ PKG <input type="checkbox" id="pkg_'+locker.logicalId+'" value ="Pkg" class="pkgCheckBox" /> ]';
		str += '[ ALARM <input type="checkbox" id="alarm_'+locker.logicalId+'" value ="Alarm" class="alarmCheckBox" /> ]';
		str += '</td></tr>';
		$('#lockerStateList tr:last').after(str);
	};
		
	function convertDoorStateToString(state){
		var state = parseInt(state);
		switch (state){
		case 0:
			return "CLOSE";
		case 1:
			return "OPEN";
		case 2:
			return "FORCE_OPEN";
		case 9:
			return "ERROR";
		default :
			return "__NO_MATCH__";	
		}
	};
	
	function convertPkgStateToString(state){
		var state = parseInt(state);
		switch (state){
		case 0:
			return "OUT";
		case 1:
			return "IN";
		case 9:
			return "ERROR";
		default :
			return "__NO_MATCH__";	
		}
	};

	function convertAlarmStateToString(state){
		var state = parseInt(state);
		switch (state){
		case 0:
			return "OFF";
		case 1:
			return "ON";
		case 9:
			return "ERROR";
		default :
			return "__NO_MATCH__";	
		}
	};

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

	function updateLockerStateFromUI(logical_id){
		// lockers.getLockerById(logical_id).doorState = getDoorStateFromUI(logical_id);
		lockers.getLockerById(logical_id).occupancyStatus = convertPkgStateToString(getPkgStateFromUI(logical_id));
		lockers.getLockerById(logical_id).alarmState = convertAlarmStateToString(getAlarmStateFromUI(logical_id));
	};
	
	function generateTables(lockerList){
		for(var idx in lockerList){
			var locker = lockerList[idx];
			createRequestTableRows(locker);
		}
	};
	

	$(document).on('click', '#bulkOpen', function(){
		var logical_id_List = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"];
		// var logical_id_List = ["1","2","3","4","5","6","7","8","9","10","11","12"]; //You can open 12 door at maximum.
		// var logical_id_List = ["1","2","3","4","5","6"];
		// var logical_id_List = ["1"];
		// lockerManager.updateLockerStateForBulkOpen(logical_id_List);
		
		lockerManager.open(logical_id_List);
	});

	$(document).on('click', '#bulkClose', function(){
		var logical_id_List = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"];
		
		// var logical_id_List = ["1","2","3","4","5","6"];
		// lockerManager.updateLockerStateForBulkOpen(logical_id_List);
		for( var idx in logical_id_List)
			lockerManager.getConnection().onResponseSimulator("#CL", lockers.getLockerById(logical_id_List[idx]));
	});


	$(document).on('click', '.openButtons', function(){
		var keyward = "open_";
		var logical_id = this.id.substring(keyward.length, this.id.length);

		if(glb_isSimulator)
			updateLockerStateFromUI(logical_id);
		lockerManager.open([logical_id]);
	});

	$(document).on('click', '.closeButtons', function(){
		var keyward = "close_";
		var logical_id = this.id.substring(keyward.length, this.id.length);

		lockerManager.getConnection().onResponseSimulator("#CL", lockers.getLockerById(logical_id));

	});


	$(document).on('click', '.pkgCheckBox', function(){
		var keyward = "pkg_";
		var logical_id = this.id.substring(keyward.length, this.id.length);
		var locker = lockers.getLockerById(logical_id);

		if(glb_isSimulator)
			updateLockerStateFromUI(logical_id);
		lockerManager.getConnection().onResponseSimulator("#EB", lockers.getLockerById(logical_id));
	});

	$(document).on('click', '.alarmCheckBox', function(){
		var keyward = "alarm_";
		var logical_id = this.id.substring(keyward.length, this.id.length);
		var locker = lockers.getLockerById(logical_id);
		
		if(glb_isSimulator)
			updateLockerStateFromUI(logical_id);
		lockerManager.getConnection().onResponseSimulator("#EB", lockers.getLockerById(logical_id));
	});

	$(document).on('click', '.configChangeButton', function(){
		console.log("[DSA - Configure onInvoke()]path:");
        jQuery(document).trigger("lockerConfigChanged");
	});

	$(document).on('click', '#getStatusALL', function(){
		var logical_id_List = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"];
		// lockerManager.getStatus(logical_id_List );
		lockerManager.getStatus();
	});

	$(document).on('click', '.getStatusButtons', function(){
		var keyward = "getStatus";
		var logical_id = this.id.substring(keyward.length, this.id.length);
		console.log(".getStatusButtons :: Logical locker id :: " + logical_id);
		lockerManager.getStatus([logical_id]);
	});

	$(document).on('click', '.updatePkgButton', function(){
		var data = document.getElementById("updatePkgInput").value
		console.log("Updating pacakge :: " + data);
		DSAManager.processPackageInfoChange(data);	
	});

	$(document).on('click', '.updateKpiButton', function(){
		var data = document.getElementById("updateKpiInput").value
		console.log("Updating KPI :: " + data);
		DSAManager.updateKpiPayload(data);	
	});

	$(document).on('click', '.updateAuditButton', function(){
		var data = document.getElementById("updateAuditInput").value
		console.log("Updating Audit :: " + data);
		DSAManager.updateAuditPayload(data);	
	});


	$(document).on('click', '.printButton', function(){

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

	});

	/* * * * * * * * * * * * * * * * * * * * * * * * * *
	* EVENT EMITTERS
	* * * * * * * * * * * * * * * * * * * * * * * * * */

	$(document).off("lockerStateChanged").on("lockerStateChanged", function(event, locker) {
		// Do UI animations
		// Update Node value via DSAManager API. 
		// This would result in value update and would notify the Java subscriber
		console.log("Received LockerStateChanged event - " + locker.doorState + 
			" for Locker - " + locker.logicalId + 
			" with occupancyStatus - " + locker.occupancyStatus + 
			" and alarmState:" + locker.alarmState);

		DSAManager.processLockerStateChange(locker);
	});
    
    $(document).off("lockerConfigChanged").on("lockerConfigChanged", function() {
		// Need to reinitailize lockerManager and DSAManager after pulling updated config from server...
		console.log("Received lockerConfigChanged event...");
        console.log("############ Reload page.............");
	});
    
    $(document).off("LCUStatusChanged").on("LCUStatusChanged", function(event, lcuStatus) {
        // So Kiosk temporarily not avaliable message if lcuStatus = OFFLINE?
        // Update Node value via DSAManager API. 
        // This would result in value update and would notify the Java subscriber
        console.log("Received LCUStatusChanged event - lcuStatus:" + lcuStatus);
        
        DSAManager.processLcuStatusChange(lcuStatus);
    });

    $(document).off("stagedPackagUpdate").on("stagedPackagUpdate", function(event, data) {  
        console.log("Received stagedPackagUpdate event - data:" + data);
        // var pkgInfo = JSON.parse(data);

        //TODO Update stage Package info
        //NOTE When dslink first handshake it receive the last value, 
        // the data already updated shoudn't be updated again.
    });

    $(document).off("canceledPackagUpdate").on("canceledPackagUpdate", function(event, data) {  
        console.log("Received canceledPackagUpdate event - data:" + data);
        // var pkgInfo = JSON.parse(data);

        //TODO Update stage Package info
        //NOTE When dslink first handshake it receive the last value, 
        // the data already updated shoudn't be updated again.
    });

    $(document).off("cancelOrderInvoked").on("cancelOrderInvoked", function(event, data) {  
        console.log("Received cancelOrderInvoked event, orderId => " + data);
        // var pkgInfo = JSON.parse(data);

        //TODO Update stage Package info
        //NOTE When dslink first handshake it receive the last value, 
        // the data already updated shoudn't be updated again.
    });


	var main = function(){

		// var brokerHost = "http://localhost:8080";
		// var brokerHost = "http://10.203.55.126:8080";
		// var brokerHost = "http://10.154.16.148:8080";
		// var brokerHost = "http://10.106.8.159:8080";
		var brokerHost = "http://10.106.9.143:8080";
		// var brokerHost = "http://10.203.55.126:8080";	
		// var brokerHost = "http://10.203.31.192:8080";
		// var brokerHost = "dglux.directcode.org/conn";

		// var brokerHost = "http://10.68.220.177:8080";		
		// var brok/erHost = "https://hostssl.dglux.com"
		// var brokerHost = "http://rnd.iot-dsa.org:8081";
		// var brokerHost = "http://10.70.235.3:8080"; 
		
		var lockerMode = "PanasonicLockerSimulator";
		// var lockerMode = "PanasonicLockerManager";
		var lockerList = getLockerList();


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
		}

		// PrinterManager.init();


		// console.log(JSON.stringify(lockers));
	//TODO Locker bank - door 
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

	main();
	generateTables(lockers.lockers);
});

