var BridgePointLockerManager = function(lockerConfig, lockers) {
	//var lcuURL = "http://10.158.6.137:5000";
    //var lcuURL = "http://10.106.9.212:5000";
    var lcuURL = "http://" + lockerConfig.host + ":" + lockerConfig.port;
    var isSensorEnabled = lockerConfig.isSensorEnabled;
    
	// Long polling...
	var getLockerStatePollerInterval = 30000;
	
	var lockerMap;
	var lockerIdMap = {};

	var lockerStateMap = {
		"Open": "OPEN",
		"Closed": "CLOSE",
        "Offline": "OFFLINE",
        "InUse": "INUSE"
	};
    
    var LCU_STATUS = {
        ONLINE: "ONLINE",
        OFFLINE: "OFFLINE"
    }  
    var lcuStatus = "ONLINE";

    function initailizeLockerModel(lockers) {
        lockerMap = {};
        lockers.map(function(locker) {
            lockerMap[locker.physicalId] = locker;
            lockerIdMap[locker.logicalId] = locker.physicalId;    
	   });
    }
    
    function onErrorResponse(res) {
        console.log("Error res - status:" + res.status + " statusText:" + res.statusText);
        
        if(res.status == 400 || res.status == 500) {
            console.log(" responseText:" + res.responseText);
            return;
        }
        
        lcuStatus = LCU_STATUS.OFFLINE;
        jQuery(document).trigger("LCUStatusChanged", LCU_STATUS.OFFLINE);
    }
    
    function getLCUConfigPayload(lockerMap) {
        var payload = 	'<Configuration>';
        
        if(isSensorEnabled) {
            payload = payload + '<Sensors>Installed</Sensors>';
        }
                            
        payload = payload + '<Lockers>';
            
        lockerIds = Object.keys(lockerMap); 
		lockerIds.forEach(function(lockerId){
			payload = payload + 
							'<Locker>' +
								'<Id>' + lockerId + '</Id>'+ 
                                '<Number>' + lockerMap[lockerId].logicalId + '</Number>'+
							'</Locker>';
		});

		payload = payload + '</Lockers>' +
                            '</Configuration>';	

		return payload;
	};
    
    function pushConfigToLCU(lockerMap) {
        console.log("BridgePointLockerManager sending Configuration to BridgePoint LCU for Lockers - " + lockerMap);
        var payload = getLCUConfigPayload(lockerMap);
		
        $.ajax({
		    url: lcuURL,
		    data: payload,
		    type: 'POST',
		    success: function(res){
                console.log("Successfully sent locker configuration command");
                if (lcuStatus == LCU_STATUS.OFFLINE) {
                    lcuStatus = LCU_STATUS.ONLINE;
                    jQuery(document).trigger("LCUStatusChanged", LCU_STATUS.ONLINE);
                }
            },
		    error: function(res){
                console.log("Error sending Configuration command");
                onErrorResponse(res);
            }  
		});
    }
	
    function getLockerStatePayload(lockerIds) {
		var payload = 	'<GetLockerState>';

		lockerIds.forEach(function(lockerId){
			payload = payload + 
							'<Locker>' +
								'<Id>' + lockerId + '</Id>'+   
							'</Locker>';
		});

		payload = payload + '</GetLockerState>';	

		return payload;
	};
    
    
    function onPollLockerStateSuccess(res) {
		var responseStr = (new XMLSerializer()).serializeToString(res);
		console.log(responseStr);

		// Poll state for all lockers
		// Compare with state in lockerList
		// Publish events like below if any locker state if different
		var $lockers = $(res).find("Locker");
		$lockers.each(function(index, locker){
			var $locker = $(locker);
			var lockerId = $locker.find("Id").text();
			var newLockerState = lockerStateMap[$locker.find("State").text()];
			var currentLockerState = lockerMap[lockerId].doorState;
			if(currentLockerState !== newLockerState) {	
				
                if(newLockerState === "INUSE") {
                    if (lockerMap[lockerId].doorState === "CLOSE" && lockerMap[lockerId].occupancyStatus === "OCCUPIED") {
                        return;
                    }
                    lockerMap[lockerId].doorState = "CLOSE";
                    lockerMap[lockerId].occupancyStatus = "OCCUPIED";
                } else if(newLockerState === "CLOSE" && isSensorEnabled) {
                    lockerMap[lockerId].doorState = newLockerState;
                    lockerMap[lockerId].occupancyStatus = "VACANT";
                } else if(newLockerState === "OPEN" && isSensorEnabled) {
                    lockerMap[lockerId].doorState = newLockerState;
                    lockerMap[lockerId].occupancyStatus = "VACANT";
                } else {
                    lockerMap[lockerId].doorState = newLockerState;
                }
                
                jQuery(document).trigger("lockerStateChanged", [lockerMap[lockerId]]);
			}
		});		
	}
    
	function pollLockerState() {
        console.log("BridgePointLockerManager poller sending getLockerSate to BridgePoint LCU for Lockers - " + Object.keys(lockerMap));
        var payload = getLockerStatePayload(Object.keys(lockerMap));
		
        $.ajax({
		    url: lcuURL,
		    data: payload,
		    type: 'POST',
		    success: function(res){
                if (lcuStatus == LCU_STATUS.OFFLINE) {
                    lcuStatus = LCU_STATUS.ONLINE;
                    jQuery(document).trigger("LCUStatusChanged", LCU_STATUS.ONLINE);
                }
                
                onPollLockerStateSuccess(res);},
		    error: function(res){
                console.log("Error sending getLockerSate command");
                onErrorResponse(res);
            } 
		});
	}

	function getOpenLockerPayload (physicalIds) {
		var payload =	'<LockerCommand>';

		physicalIds.forEach(function(physicalId){
            payload = payload + 
							'<Locker>' +
							'<Id>' + physicalId + '</Id>'+ 
							'<Command>Open</Command>' +  
							'</Locker>';
		});

		payload = payload + '</LockerCommand>';	

		return payload;
	}

	function onOpenSuccess(openedPhysicalIds) {
		console.log("Successfully sent Open command");
        
		openedPhysicalIds.forEach(function(physicalId){
			lockerMap[physicalId].doorState = "OPEN";

			jQuery(document).trigger("lockerStateChanged", [lockerMap[physicalId]]);
		});
	}

	this.open = function(logicalIds) {
		console.log("BridgePointLockerManager sending OPEN to BridgePoint LCU for Lockers with logicalIds - " + logicalIds);
		var physicalIds = logicalIds.map(function(lockerId){
			return lockerIdMap[lockerId];
		});
		var payload = getOpenLockerPayload(physicalIds);

		$.ajax({
		    url: lcuURL,
		    data: payload,
		    type: 'POST',
		    success: function(res){
                if (lcuStatus == LCU_STATUS.OFFLINE) {
                    lcuStatus = LCU_STATUS.ONLINE;
                    jQuery(document).trigger("LCUStatusChanged", LCU_STATUS.ONLINE);
                }
                
                onOpenSuccess(physicalIds);},
		    error: function(res){
                console.log("Error sending Open command");
                onErrorResponse(res);
            }  
		});

	}

	this.close = function(logicalId) {
		console.log("BridgePointLockerManager sending CLOSE to BridgePoint LCU for Lockers with logicalId - " + logicalId);
		var physicalId = lockerIdMap[logicalId];
        lockerMap[physicalId].doorState = "CLOSE";

		jQuery(document).trigger("lockerStateChanged", [lockerMap[physicalId]]);
	};
    
    this.getStatus = function(logicalIds) {
		concole.log("BridgePointLockerManager getStatus() called");
		pollLockerState()
	};

    initailizeLockerModel(lockers);
    pushConfigToLCU(lockerMap);

	setInterval(pollLockerState, getLockerStatePollerInterval);
	pollLockerState();
};

