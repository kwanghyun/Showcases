"use strict";

var app = angular.module('BobMobilemApp', []);
var link;
var LOCAL_ACCESS_MODE = false;
var downstreamName = "downstream";	
var app_id = "bob-mobile"
var deviceId = "bob-device";
var cloud_broker_prefix = "/downstream/bob-broker";
var scope = null;

// var brokerURL = 'http://localhost:8080';
// var brokerURL = 'http://10.106.9.143:8080';
var brokerURL = "http://10.106.8.160:8080";

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

app.controller('lockerCtrl', function($scope, dsaService) {

    $scope.package = "";
    $scope.kpiEvent = "";
    $scope.auditEvent = "";
    $scope.status = ""
    scope = $scope;

    if(LOCAL_ACCESS_MODE){
    	cloud_broker_prefix = "";
    	brokerURL = 'http://10.106.9.143:8080';
    }

    $scope.lockerList = [];
    // main($scope.lockerList);
    dsaService.init($scope.lockerList);

    console.log("$scope.lockerList Size => " + $scope.lockerList.length);  

    $scope.dslinks = ["bob-son-mobile","alice-mobile"];
    $scope.permissions = [":bob:config",":bob:write",":bob:read"];

    $scope.openDoor = function(logical_id) {
    	console.log("openDoor() logical_id => " + logical_id);
		var nodePath = cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/command';
        link.requester.invoke(nodePath,{command:"/" + logical_id +"/doorState-OPEN"});
		return;
    }

    $scope.closeDoor = function(logical_id) {
    	console.log("openDoor() logical_id => " + logical_id);
		var nodePath = cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/command';
        console.log(nodePath);
        link.requester.invoke(nodePath,{command:"/" + logical_id +"/doorState-CLOSE"});
		return;
    }

    $scope.togglePackageStatus = function(locker) {
    	console.log("togglePackageStatus() logical_id => " + locker.logicalId);
		var nodePath = cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/command';
		var value;
    	if(locker.occupancyStatus === LOCKER_STATE.PACKAGE.OUT)
    		value = LOCKER_STATE.PACKAGE.IN;
    	else
    		value = LOCKER_STATE.PACKAGE.OUT;
    	
		link.requester.invoke(nodePath,{command:"/" + locker.logicalId +"/occupancyStatus-" + value});
    } 

    $scope.toggleAlarmStatus = function(locker) {
    	console.log("toggleAlarmStatus() logical_id => " + locker.logicalId);
		var nodePath = cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/command';
		var value;
    	if(locker.alarmState === LOCKER_STATE.ALARM.OFF)
    		value = LOCKER_STATE.ALARM.ON;
    	else
    		value = LOCKER_STATE.ALARM.OFF;
    	
		link.requester.invoke(nodePath,{command:"/" + locker.logicalId +"/alarmState-"+ value});
    } 

    $scope.updatePermssion = function() {
    	console.log("updatePermssion() started.... -> " + $scope.dsaName + " - " + $scope.permission);
		var nodePath = "/sys/updateGroup";
		if($scope.dsaName == undefined || $scope.permission == undefined){
			$scope.error = "Please check your input"
		}
		else{
			$scope.error = ""
			link.requester.invoke(nodePath,{"Name": $scope.dsaName , "Group": $scope.permission});
			// link.requester.invoke("/sys/updateGroup", {
			//   "Name": "son-mobile",
			//   "Group": ":write"
			// });
		}
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
    });

});

app.service('dsaService',function(){
	this.init = function(list){
		console.log("init started......");

		var propertyNames = ["doorState", "occupancyStatus", "alarmState"];

	    var keys;
	    var load = {
	        app_version: {
	            '$type' : 'string',
	            '?value' : ''
	        }

	    };

		var lockerList = getLockerList();
	    for(var idx in lockerList){
			var locker = new Locker(lockerList[idx].logicalId, lockerList[idx].physicalId);
			locker.doorState = "....."
			locker.occupancyStatus = "....."
			locker.alarmState = "....."
			list.push(locker);
		}

	    link = new DS.LinkProvider(brokerURL + '/conn', app_id + '-', {
	        profiles: {},
	        defaultNodes: load
	    });

	    keys = new DS.PrivateKey.loadFromString('rbKXu9ADtvRtHFVNGdTpmeSddsfdddU7TNsxtgrsk+OM=');
	    link.privateKey = keys;

	    link.connect().then(function() {
	        return link.onRequesterReady;
	    }).then(function(requester) {
	       requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/1/doorState', function(update) {
	            var data = update.value;
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - doorStatus update :: " + data);
	                list[0][propertyNames[0]] = data;
	                changeLokcerStates(list[0]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/1/occupancyStatus', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - package status update :: " + data);
	                list[0][propertyNames[1]] = data;
	                changeLokcerStates(list[0]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/1/alarmState', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - alarm update :: " + data);
	                list[0][propertyNames[2]] = data;
	                changeLokcerStates(list[0]);
	            }
	            return;
	        });            
	                       

	       requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/2/doorState', function(update) {
	            var data = update.value;

	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - doorStatus  update :: " + data);
	                list[1][propertyNames[0]] = data;
	                changeLokcerStates(list[1]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/2/occupancyStatus', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - package status update :: " + data);
	                list[1][propertyNames[1]] = data;
	                changeLokcerStates(list[1]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/2/alarmState', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - alarm update :: " + data);
	                list[1][propertyNames[2]] = data;
	                changeLokcerStates(list[1]);
	            }
	            return;
	        });            


	       requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/3/doorState', function(update) {
	            var data = update.value;

	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - doorStatus  update :: " + data);
	                list[2][propertyNames[0]] = data;
	                changeLokcerStates(list[2]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/3/occupancyStatus', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - package status update :: " + data);
	                list[2][propertyNames[1]] = data;
	                changeLokcerStates(list[2]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/3/alarmState', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - alarm update :: " + data);
	                list[2][propertyNames[2]] = data;
	                changeLokcerStates(list[2]);
	            }
	            return;
	        });            


	       requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/4/doorState', function(update) {
	            var data = update.value;

	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - doorStatus  update :: " + data);
	                list[3][propertyNames[0]] = data;
	                changeLokcerStates(list[3]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/4/occupancyStatus', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - package status update :: " + data);
	                list[3][propertyNames[1]] = data;
	                changeLokcerStates(list[3]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/4/alarmState', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - alarm update :: " + data);
	                list[3][propertyNames[2]] = data;
	                changeLokcerStates(list[3]);
	            }
	            return;
	        });            


	       requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/5/doorState', function(update) {
	            var data = update.value;

	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - doorStatus  update :: " + data);
	                list[4][propertyNames[0]] = data;
	                changeLokcerStates(list[4]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/5/occupancyStatus', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - package status update :: " + data);
	                list[4][propertyNames[1]] = data;
	                changeLokcerStates(list[4]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/5/alarmState', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - alarm update :: " + data);
	                list[4][propertyNames[2]] = data;
	                changeLokcerStates(list[4]);
	            }
	            return;
	        });   
	         


	       requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/6/doorState', function(update) {
	            var data = update.value;

	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - doorStatus  update :: " + data);
	                list[5][propertyNames[0]] = data;
	                changeLokcerStates(list[5]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/6/occupancyStatus', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - package status update :: " + data);
	                list[5][propertyNames[1]] = data;
	                changeLokcerStates(list[5]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/6/alarmState', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - alarm update :: " + data);
	                list[5][propertyNames[2]] = data;
	                changeLokcerStates(list[5]);
	            }
	            return;
	        });   


	       requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/7/doorState', function(update) {
	            var data = update.value;

	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - doorStatus  update :: " + data);
	                list[6][propertyNames[0]] = data;
	                changeLokcerStates(list[6]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/7/occupancyStatus', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - package status update :: " + data);
	                list[6][propertyNames[1]] = data;
	                changeLokcerStates(list[6]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/7/alarmState', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - alarm update :: " + data);
	                list[6][propertyNames[2]] = data;
	                changeLokcerStates(list[6]);
	            }
	            return;
	        });   



	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/8/doorState', function(update) {
	            var data = update.value;

	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - doorStatus  update :: " + data);
	                list[7][propertyNames[0]] = data;
	                changeLokcerStates(list[7]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/'+ deviceId + '/8/occupancyStatus', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - package status update :: " + data);
	                list[7][propertyNames[1]] = data;
	                changeLokcerStates(list[7]);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/8/alarmState', function(update) {
	            var data = update.value;            
	            
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - alarm update :: " + data);
	                list[7][propertyNames[2]] = data;
	                changeLokcerStates(list[7]);
	            }
	            return;
	        });   
	       

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/packageUpdate', function(update) {
	            var data = update.value;            
	            console.log("#requester.subscribtion - packageUpdate :: " + data);
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - packageUpdate data :: " + data);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/kpiPayload', function(update) {
	            var data = update.value;            
	            console.log("#requester.subscribtion - kpiPayload :: " + data);
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - kpiPayload data :: " + data);
	            }
	            return;
	        });

	        requester.subscribe(cloud_broker_prefix + '/' + downstreamName +'/' + deviceId + '/auditPayload', function(update) {
	            var data = update.value;            
	            console.log("#requester.subscribtion - auditPayload :: " + data);
	            if(data !=null && data != undefined && data != ''){
	                console.log("##### Handle - auditPayload data :: " + data);
	            }
	            return;
	        });

	        return link;

	    }).catch(function(reason) {
	      console.log(reason);
	    });
	}

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
			}
		];
		return lockerList;
	}

	function changeLokcerStates(locker){
		scope.$emit('lockerStatusChanged', locker);
	}

});



