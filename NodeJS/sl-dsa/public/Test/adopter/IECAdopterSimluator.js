"use strict";

$(document).ready(function(){
	var client = null;
	var lockerManager = null;
	var provider = null;
	var lockerModel = '';
	var kiosk_app_version = 0.0;
	var totalResponseCount = 0;
	var cmdConflictCount = 0;
	// var openDoorCount = 0;

	var SerialConnectionSimulator = function(){
		SerialConnection.call(this);
		this.eventInterval = 60;
		//Out of 100
		this.cmdFailPercentage = 20;
		// this.cmdFailPercentage = 100;
	}
	SerialConnectionSimulator.extends(SerialConnection);

	// @override : SerialConnection method override
	SerialConnectionSimulator.prototype.setConnection = function(callback) {
		initMetaDataDefinition();
		intiMetaPropertyDefinition();
		
		this.listener = callback;
		// glb_serialPortListener = callback;
	}

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
			var physical_id = strCommand.substring(3, 6)
			this.onResponseSimulator(cmd, lockerManager.getLockerById(physical_id));
		}else{
			cmdConflictCount ++;
			////Simulate command conflit, so no response from LCU
			console.log("#####################!!!!!! Oops some commands are conflicted (" + cmdConflictCount + ")!!!!!#####################");
		}
	};

	SerialConnectionSimulator.prototype.onResponseSimulator = function(cmd, locker) {
		// console.log("### onResponseSimulator() START with cmd :: " + cmd + ", lockerId :: " + lockerId);
		console.log("### onResponseSimulator() START with cmd :: " + cmd + ", lockerId :: " + locker.id);
	

		totalResponseCount ++;
		var lockerId = locker.id;
		var doorState = locker.doorState;
		var packageState = locker.packageState;
		var alarmState = locker.alarmState;
		var eventList = [];	

		switch (cmd) {
		case "#OP":
			/*if(lockerId == "016"){
				//simulating BCC error, unexpected resonse handling Test
				eventList = ["!0182."];
			}else */if(lockerManager.countOpenDoor() >= 12){
				lockerManager.getLockerById(lockerId).doorState = LOCKER_STATE.DOOR.ERROR;
				doorState = LOCKER_STATE.DOOR.ERROR;
				eventList = ["$OP12000B6."];
			}else{
				lockerManager.getLockerById(lockerId).doorState = LOCKER_STATE.DOOR.OPEN;
				doorState = LOCKER_STATE.DOOR.OPEN;
				eventList = ["$OP00" + lockerId +"B5.", 
				"#EB" + lockerId + doorState + packageState + alarmState + "CD.", 
				"#EB" + lockerId + doorState + packageState + alarmState + "CD.", 
				"#EB" + lockerId + doorState + packageState + alarmState + "CD." ];
			}
			break;
		case "#SB":
			if(lockerId == "016"){
				//simulating BCC error, unexpected resonse handling Test
				eventList = ["!0182."];
			}else{
				eventList = [
				"$SB00" + lockerId + doorState + packageState + alarmState + "CD."];	
			}
			
			break;
		case "#CL":
			lockerManager.getLockerById(lockerId).doorState = LOCKER_STATE.DOOR.CLOSED;
			doorState = LOCKER_STATE.DOOR.CLOSED;
		case "#EB":
			eventList = [
				"#EB" + lockerId + doorState + packageState + alarmState + "CD.", 
				"#EB" + lockerId + doorState + packageState + alarmState + "CD.", 
				"#EB" + lockerId + doorState + packageState + alarmState + "CD." ];

			break;
		default:
			throw "onResponseSimulator():::No-Match found"
		}

		console.log("Logical locker id :: [" + lockerId 
			+ "] - doorState :: [" + convertDoorStateToString(doorState)
			+ "] - packageState :: [" + convertPkgStateToString(packageState)  
			+ "] - alarmState :: [" + convertAlarmStateToString(alarmState) + "]");

		this.eventEmitter(eventList, this.eventInterval);
		// this.changeLockerStateTimer(lockerId, doorState, packageState, alarmState , this.eventInterval);
		this.changeLockerStateTimer(lockerManager.getLockerById(lockerId), this.eventInterval);
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

	// SerialConnectionSimulator.prototype.changeLockerStateTimer = function(lockerId, doorState, packageState, alarmState , interval) {
	// 	setTimeout(function(){
	// 		// console.log("% changeLockerStateTimer() - lockerId :: " + locker.id);
	// 		changeLokcerStates(lockerId, doorState, packageState, alarmState);
	// 	}, interval)
	// };

	SerialConnectionSimulator.prototype.changeLockerStateTimer = function(locker , interval) {
		setTimeout(function(){
			// console.log("% changeLockerStateTimer() - lockerId :: " + locker.id);
			changeLokcerStates(locker.id, locker.doorState, locker.packageState, locker.alarmState);
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
		// console.log("3.#####onDate() filtered result arrs ::::" + responseArrs);
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
				if(response.indexOf(EVENT_NITOFICATION) >= 0){
					//In case of chuck of response - when fail to send ACKNOWLEDGE_NOTI against EVENT_NITOFICATION 
					if(this.lastResponse != response){
						console.log("#####onDate() - Calling callback with response :::: " + response);
						this.listener.call(null, response);
					}else{
						console.log("#####onDate() - Ignore duplicated response :::: " + response);
					}
					this.lastResponse = response;
				}else if(response.indexOf(GET_STATUS_HEADER) >= 0){
					this.listener.call(null, response);
				}else{
					console.log("#####onDate() - Ignoring this event response :::: " + response);
				}
			}
		}
	};

	var PanasonicLockerSimulator = function(lockerCount) {
		PanasonicLockerManager.call(this, lockerCount);
	}
	
	PanasonicLockerSimulator.extends(PanasonicLockerManager);
		
	// @override : LockerManager method override
	PanasonicLockerSimulator.prototype.initConnection = function(handler){
		if(this.conn == null){
			this.conn = new SerialConnectionSimulator();
			console.log("Create a new SerialConnection!!!!!!!!!");
			
			this.conn.setConnection(handler);
			//store it as global level because when Cobra browser native method doesn't know this.		
			glb_serialConnection = this.conn;
		}
		else{
			this.conn.setConnection(handler);	
		}
	};

	PanasonicLockerSimulator.prototype.updateLockerStateFromUI = function(physical_id){
		// this.getLockerById(physical_id).doorState = getDoorStateFromUI(physical_id);
		this.getLockerById(physical_id).packageState = getPkgStateFromUI(physical_id);
		this.getLockerById(physical_id).alarmState = getAlarmStateFromUI(physical_id);
	}

	PanasonicLockerSimulator.prototype.sendCommands = function(action, lockerIds){
		this.conn.sendCommands(action, lockerIds);
	}

	PanasonicLockerSimulator.prototype.countOpenDoor = function() {
		var openDoorCount = 0;
		for(var id in this.lockers){
			if(this.getLockerById(id).doorState == LOCKER_STATE.DOOR.OPEN){
				openDoorCount++;
			}
				
		}
		return openDoorCount;
	}

	var LockerFactory = function(){};

	LockerFactory.createInstance = function(lockerType, lockerCount){
		var lockerManager = null;
		try{
			if(lockerCount > 0 ){
				lockerManager = eval("new "+ lockerType + "(" + lockerCount + ");");	
			}else{
				lockerManager = eval("new "+ lockerType + "();");
			}
		}catch(e){
			throw "LockerManager::createInstance() - Invalid Input :: msg[ " +e + "] ," + lockerType +"," + lockerCount + ", ";
		}

		return lockerManager;
	};
	
	function getDoorStateFromUI(lockerId){
		console.log("##DOOR :: " + $('#door_' + lockerId).is(":checked"));
		if($('#door_' + lockerId).is(":checked")){
			return LOCKER_STATE.DOOR.OPEN;
		}else{
			return LOCKER_STATE.DOOR.CLOSED;
		}
	}

	function getPkgStateFromUI(lockerId){
		console.log("##PKG :: " + $('#pkg_' + lockerId).is(":checked"));
		if($('#pkg_' + lockerId).is(":checked")){
			return LOCKER_STATE.PACKAGE.IN;
		}else{
			return LOCKER_STATE.PACKAGE.OUT;
		}
	}

	function getAlarmStateFromUI(lockerId){
		console.log("##ALARM :: " + $('#alarm_' + lockerId).is(":checked"));
		if($('#alarm_' + lockerId).is(":checked")){
			return LOCKER_STATE.ALARM.ON;
		}else{
			return LOCKER_STATE.ALARM.OFF;
		}
	}
	
	function changeLokcerStates(lockerId, doorState, packageState, alarmState){
		var doorState = convertDoorStateToString(doorState);
		var packageState = convertPkgStateToString(packageState);
		var alarmState = convertAlarmStateToString(alarmState);

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

		if(packageState == "IN"){
			$("#packageState" + lockerId).addClass("pkg_in");		
			$("#packageState" + lockerId).removeClass("pkg_out");
			$("#packageState" + lockerId).removeClass("error");	
		}else if(packageState == "ERROR"){
			$("#packageState" + lockerId).addClass("error");		
			$("#packageState" + lockerId).removeClass("pkg_in");
			$("#packageState" + lockerId).removeClass("pkg_out");
		}else{
			$("#packageState" + lockerId).addClass("pkg_out");		
			$("#packageState" + lockerId).removeClass("pkg_in");
			$("#packageState" + lockerId).removeClass("error");			
		}		
		$("#packageState" + lockerId).text(packageState);

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
	}
	
	function createRequestTableRows(locker){
		var str = '<tr><td><div id="lockerId'+locker.id+'">'+ provider.getNode('/')._config[locker.id] + ' ('+ locker.id +')</div></td>';
		str += '<td ><div id="doorState'+locker.id+'" class="locerk_state" >'+ convertDoorStateToString(locker.doorState) +'</div></td>';
		str += '<td ><div id="packageState'+locker.id+'" class="locerk_state">'+ convertPkgStateToString(locker.packageState) +'</div></td>';
		str += '<td><div id="alarmState'+locker.id+'" class="locerk_state">' + convertAlarmStateToString(locker.alarmState) +'</div></td>';
		str += '<td>'
		str += '[ DOOR <input type="checkbox" id="door_'+locker.id+'" value ="Door" class="doorCheckBox" /> ]';
		str += '[ PKG <input type="checkbox" id="pkg_'+locker.id+'" value ="Pkg" class="pkgCheckBox" /> ]';
		str += '[ ALARM <input type="checkbox" id="alarm_'+locker.id+'" value ="Alarm" class="alarmCheckBox" /> ]';
		str += '<input type = "button" id="cfg-chg'+locker.id+'" value ="Cfg-chg" class="configChangeButtons" />';
		str += '<input type = "button" id="getStatus'+locker.id+'" value ="getStatus" class="getStatusButtons" /></td></tr>';
		$('#lockerStateList tr:last').after(str);
	}
		
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
	}
	
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
	}

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
	}

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
	}
	
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
	}

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
	}
	
	function generateTables(lockerManager){
		for(var lockerId in lockerManager.lockers){
			var locker = lockerManager.getLockerById(lockerId);

			createRequestTableRows(locker);
		}
	}
	




	$(document).on('click', '#bulkOpen', function(){
		var physical_id_List = ["011","012","013","014","015","016","021","022","023","024","025","026","031","032","033"];
		// var physical_id_List = ["011","012","013","014","015","016","021","022","023","024","025","026"]; //You can open 12 door at maximum.
		// var physical_id_List = ["011","012","013","014","015","016"];
		// lockerManager.updateLockerStateForBulkOpen(physical_id_List);
		lockerManager.sendCommands("OPEN", physical_id_List);
	});

	$(document).on('click', '.doorCheckBox', function(){
		var keyward = "door_";
		var physical_id = this.id.substring(keyward.length,keyward.length + 3);
		var logical_id = provider.getNode('/')._config[physical_id];
		
		if($('#' + this.id).is(":checked")){
			lockerManager.updateLockerStateFromUI(physical_id);
			lockerManager.sendCommands("OPEN", [physical_id]);
		}else{			
			// lockerManager.getLockerById(physical_id).doorState = LOCKER_STATE.DOOR.CLOSED; 
			lockerManager.conn.onResponseSimulator("#CL", lockerManager.getLockerById(physical_id));
		}
		
	});

	$(document).on('click', '.pkgCheckBox', function(){
		var keyward = "pkg_";
		var physical_id = this.id.substring(keyward.length,keyward.length + 3);
		var locker = lockerManager.getLockerById(physical_id);

		lockerManager.updateLockerStateFromUI(physical_id);
		lockerManager.conn.onResponseSimulator("#EB", lockerManager.getLockerById(physical_id));
	});

	$(document).on('click', '.alarmCheckBox', function(){
		var keyward = "alarm_";
		var physical_id = this.id.substring(keyward.length,keyward.length + 3);
		var locker = lockerManager.getLockerById(physical_id);
		
		lockerManager.updateLockerStateFromUI(physical_id);
		lockerManager.conn.onResponseSimulator("#EB", lockerManager.getLockerById(physical_id));
	});

	$(document).on('click', '.configChangeButtons', function(){
		var keyward = "cfg-chg";
		var physical_id = this.id.substring(keyward.length,keyward.length + 3);
		var logical_id = provider.getNode('/')._config[physical_id];
		console.log("Logical locker id :: " + logical_id);
		provider.getNode('/' + logical_id).config("row", 10);
		var row = provider.getNode('/' + logical_id)._config["row"];
		console.log("%%%%row" + row);
		// provider.addNode('/' + logical_id , {
		// 	'$lockerId' : phsical_id,
		// 	"$size" : lockerList[locker].size,
		// 	"$row" : lockerList[locker].row,
		// 	"$column" : lockerList[locker].column,
		// 	"$reservedForCustomer" : lockerList[locker].reservedForCustomer,
		// 	"$sensorInstalled" : lockerList[locker].sensorInstalled
		// });
	});

	$(document).on('click', '#getStatusALL', function(){
		var physical_id_List = ["011","012","013","014","015","016","021","022","023","024","025","026","031","032","033",];
		// var physical_id_List = ["011","012","013","014","015"];
		lockerManager.sendCommands("GET_STATUS",physical_id_List );
	});

	$(document).on('click', '.getStatusButtons', function(){
		var keyward = "getStatus";
		var physical_id = this.id.substring(keyward.length,keyward.length + 3);
		var logical_id = provider.getNode('/')._config[physical_id];
		console.log(".getStatusButtons :: Logical locker id :: " + logical_id);
		lockerManager.sendCommands("GET_STATUS",[physical_id] );
	});

	var main = function(){

		// Broker Host:port
		// var brokerHost = "http://localhost:8080";
		// var brokerHost = "http://10.106.8.159:8080";
		// var brokerHost = "http://10.139.5.29:8080";
		// var brokerHost = "https://10.106.9.143:8443";
		var brokerHost = "http://10.106.9.143:8080";
		// var brok/erHost = "https://hostssl.dglux.com"
		// var brokerHost = "http://rnd.iot-dsa.org:8081";
		
		// var brokerHost = location.host;
		
		console.log("main() :: brokerHost :: " + brokerHost);
		

		//TODO get it from server
		var lockerList = [
		    {
		    	"lockerModel" : "PanasonicLockerSimulator", //"PanasonicLockerManager","PanasonicLockerSimulator", "BridgePointLockerManager"
		    	"kiosk_app_version" : "1.1",
		    	"auth_token" : "ECDH-key-saldkfjslkdflskdjflksdjflkjdslkfjdskf"
		    },
			{
				"id" : "locker1",
				"lockerId" : "011"
			},
			{
				"id" : "locker2",
				"lockerId" : "012"
			},
			{
				"id" : "locker3",
				"lockerId" : "013"
			},
			{
				"id" : "locker4",
				"lockerId" : "014"
			},
			{
				"id" : "locker5",
				"lockerId" : "015"
			},
			{
				"id" : "locker6",
				"lockerId" : "016"
			},
			{
				"id" : "locker7",
				"lockerId" : "021"
			},
			{
				"id" : "locker8",
				"lockerId" : "022"
			},
			{
				"id" : "locker9",
				"lockerId" : "023"
			},
			{
				"id" : "locker10",
				"lockerId" : "024"
			},
			{
				"id" : "locker11",
				"lockerId" : "025"
			},
			{
				"id" : "locker12",
				"lockerId" : "026"
			},
			{
				"id" : "locker13",
				"lockerId" : "031"
			},
			{
				"id" : "locker14",
				"lockerId" : "032"
			},
			{
				"id" : "locker15",
				"lockerId" : "033"
			}
		];


		provider = new DS.NodeProvider();		
		
		 /*
		 * DSA Node definition
		 */
		var OpenLocker = DS.Node.createNode({
			onInvoke : function(params) {
				var path = this.path();
				var parentNodeName = path.substring(0, path.length -  "/open".length);
				var physical_id = provider.getNode(parentNodeName)._config["lockerId"];
				console.log("[$DSA - onInvoke()]path:" + path);
				console.log("[$DSA - onInvoke()]parentNodeName::::: " + parentNodeName);
				lockerManager.sendCommands("OPEN", [physical_id]);
			}
		});
		
		var BulkOpenLockers = DS.Node.createNode({
			onInvoke : function(params) {
				var path = this.path();
				console.log("[$DSA - Bulk Open]path:" + path);
				console.log("[$DSA - Bulk Open]params:" + params.lockerIds);

				lockerManager.sendCommands("OPEN", params.lockerIds);
			}
		}); 

		//DSA Close for Test only
		var CloseLocker = DS.Node.createNode({
			onInvoke : function(params) {
				var path = this.path();
				var parentNodeName = path.substring(0, path.length -  "/close".length);
				var physical_id = provider.getNode(parentNodeName)._config["lockerId"];
				console.log("[$DSA - CloseLocker]path:" + path);
				console.log("[$DSA - CloseLocker]parentNodeName::::: " + parentNodeName);
				lockerManager.conn.onResponseSimulator("#CL", lockerManager.getLockerById(physical_id));
			}
		});
		
		//PANASONIC LOCKER also could use this method
		//i.e. When admin want to map logical ID "14" to physical_id "033" because "032" port doesn't work
		var PushConfig = DS.Node.createNode({
			onInvoke : function(params) {
				var path = this.path();
				console.log("[$DSA - Push Config]path:" + path);
				console.log("[$DSA - Push Config]params:" + params.payload);
				console.log("[$DSA - Push Config]params:" + JSON.stringify(params.payload));
				// console.log("[$DSA - Push Config]params:" + params.payload.physical_id);
				// console.log("[$DSA - Push Config]params:" + params.payload.logical_id);
				
				//TODO let's expose a function that re-set all the profile values on DSAManager.js and call that function in here.
				/*
				var physical_id = params.payload.physical_id;
				var logical_id = params.payload.logical_id;
				provider.getNode('/' + logical_id).config("row", physical_id);
				provider.getNode('/' + logical_id).config("row", logical_id);
				*/
				return "{'status' : 'success'}";
			}
		}); 


		provider.is('openLocker', OpenLocker); 
		provider.is('bulkOpenLocker', BulkOpenLockers); 

		provider.is('closeLocker', CloseLocker); 
		provider.is('pushLockerConfig', PushConfig); 
		//DSA Close for Test only

		provider.load({
			defs : {
				profile : {
					openLocker : {},
					bulkOpenLocker : {},
					closeLocker : {},
					pushLockerConfig : {}
				}
			}
		});

		var openDef = {
			'$invokable' : 'read',
			'$is' : 'openLocker',
		};

		var bulkOpenDef = {
			'$invokable' : 'read',
			'$is' : 'bulkOpenLocker',
			'$params' : {
				'lockerIds' : {
					'type' : 'array'
				}
			}
		};

		// var pushConfigDef = {
		// 	'$invokable' : 'read',
		// 	'$is' : 'pushLockerConfig',
		// 	'$param' : {
		// 		'key' : 'string'
		// 	}
		// };

		var pushConfigDef = {
			'$invokable' : 'read',
			'$is' : 'pushLockerConfig',
			'$params' : {
				'payload' : {
					'type':'map'
				}
			}
		};

		var closeDef = {
			'$invokable' : 'read',
			'$is' : 'closeLocker'
		};

		var doorStateDef = {
			'$type' : 'enum[CLOSE,OPEN,ERROR]',
			'?value' : 'CLOSE'
		};

		var packageStateDef = {
			'$type' : 'enum[NONE,IN,OUT,ERROR]',
			'?value' : 'OUT'
		};

		var alarmStateDef = {
			'$type' : 'enum[ON,OFF,ERROR]',
			'?value' : 'OFF'
		};


		
		for(var locker in lockerList){
			if(locker == 0 ){
				
				lockerModel = lockerList[0].lockerModel;
				kiosk_app_version = lockerList[0].kiosk_app_version;
				// provider.addNode('/properties', {'$kiosk_app_version' : kiosk_app_version});
				console.log("lockerModel :: " + lockerModel);
				console.log("kiosk_app_version :: " + parseFloat(kiosk_app_version));
				provider.addNode('/bulkOpen', bulkOpenDef);	
				provider.addNode('/pushConfig', pushConfigDef);	
				continue;
			}
				 
			var logical_id = lockerList[locker].id;
			var phsical_id = lockerList[locker].lockerId;
			console.log("Logical locker id :: " + lockerList[locker].id);
			console.log("Pysical locker id  :: " + phsical_id);
			
			provider.getNode('/')._config[phsical_id] = logical_id;
			provider.addNode('/' + logical_id , {
				'$lockerId' : phsical_id
			});
			provider.addNode('/' + logical_id + '/open', openDef);
			provider.addNode('/' + logical_id + '/close', closeDef);
			provider.addNode('/' + logical_id + '/doorState', doorStateDef);
			provider.addNode('/' + logical_id + '/packageState', packageStateDef);
			provider.addNode('/' + logical_id + '/alarmState', alarmStateDef);
			// provider.addNode('/' + logical_id + '/pushConfig', pushConfigDef);
		};

		
		var dataHandler = function (response){
			
			console.log("dataHandler()::::response::::"+ response);
			var header = response.substring(0,3);
			var offset;
			
			if(header == "#EB"){
				offset = 0;	
			}else if(header == "$SB"){
				offset = 2;
			}else{
				return;
			}

			var lockerGroupId = response.substring(3 + offset, 5 + offset);
			var lockerId = response.substring(5 + offset, 6 + offset);
			var doorState = convertDoorStateToString(response.substring(6 + offset, 7 + offset));
			var pkgState = convertPkgStateToString(response.substring(7 + offset, 8 + offset));
			var alarmState = convertAlarmStateToString(response.substring(8 + offset, 9 + offset));

			var targetNodeName = provider.getNode('/')._config[lockerGroupId+lockerId];				
			var doorValueNode = "/"+ targetNodeName + "/doorState";
			var pkgValueNode = "/"+ targetNodeName + "/packageState";
			var alarmValueNode = "/"+ targetNodeName + "/alarmState";
			// console.log("[@@DSA Physical lockerId : "+lockerGroupId + lockerId);	
			// console.log("[@@DSA Logical LockerId : "+targetNodeName);

			
			// console.log("[@@DSA path ::::: " + doorValueNode);
			console.log("[@@DSA Updated " + doorValueNode + " ===> " + doorState);
			console.log("[@@DSA Updated " + pkgValueNode + " ===> " + pkgState);
			console.log("[@@DSA Updated " + alarmValueNode + " ===> " + alarmState);

			provider.getNode(doorValueNode).value = new DS.Value(doorState);
			provider.getNode(pkgValueNode).value = new DS.Value(pkgState);
			provider.getNode(alarmValueNode).value = new DS.Value(alarmState);
			
			//Bulk operation test result check
			if(totalResponseCount > 11){					
				console.log("#################### TOTAL Conflict Count (" + cmdConflictCount + ") #################### ");
				console.log("#################### TOTAL Stuck Detection Count ( " + lockerManager.conn.stuckedThreadCount + ") ####################");
				console.log("#################### TOTAL Response Count ( " + totalResponseCount + ") ####################");
			}

		};
		
		lockerManager = LockerFactory.createInstance(lockerModel, lockerList.length - 1);
		lockerManager.initConnection(dataHandler);

		//TODO Test Only
		var iec_sn = null;
		try{
			iec_sn = global.device.serialNumber;
		}catch(err){
			iec_sn = "USI1742S132";
		}

		var keys = DS.ECDH
				.importKey('t5YRKgaZyhXNNberpciIoYzz3S1isttspwc4QQhiaVkBJ403K-ND1Eau8UJA7stsYI2hdgiOKhNVDItwg7sS6MfG2iSRGqM2UodSF0mb8GbD8s2OAukQ03DFLULw72bklo');
		var requester = new DS.Requester();
		var responder = new DS.Responder(provider);
		client = new DS.WebSocketClient(iec_sn, responder, requester);

		client.connect({
					hostname : brokerHost +'/conn',
					keys : keys
				}).then(function() {

		    var stream = requester.subscribe([{
		       path: '/conns/link-SL-server/kiosk_version',
		       sid: 1
		    }]);

	      requester.streams[0].on('data', function(data) {
	        if(Array.isArray(data)) {
	          	if(data[0] != 1) return;	

				var version = data[1].toString();            
	          	if(kiosk_app_version < parseFloat(data[1].toString())){
	          		alert("#######on(Array) Reloading page with kiosk_app_version "+data[1].toString());	
	          		// location.reload();
	         	}
	          	return;
	        }

	        if(data.sid != 1) 
	        	return;

			var version = data.value.toString();
			console.log("current kiosk_app_version :: " + kiosk_app_version);
			console.log("subscribed kiosk version :: " + parseFloat(version));
	        if(kiosk_app_version < version){
	          	alert("#######on(VALUE) Reloading page with kiosk_app_version "+ parseFloat(version));
	          	// location.reload();
	        }

	      });

        }).catch(function(reason) {
          console.log(reason);
        });
					
		generateTables(lockerManager);
	};
	//TODO Locker bank - door 
	main();
});

