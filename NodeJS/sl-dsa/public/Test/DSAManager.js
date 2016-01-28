"use strict";

var DSAManager = (function($) {
	var keys;
	var link;
	var _lockerManager;
	var _storageManager;

	/*
	 * DSA Node definition
	 */
	var OpenLocker = DS.createNode({
		onInvoke : function() {
			var path = this.path;
			var parentNodeName = path.substring(1, path.length -  "/open".length);
			console.log("[DSA - OpenLocker onInvoke()]path:" + path);
			console.log("[DSA - OpenLocker onInvoke()]parentNodeName::::: " + parentNodeName);
			var lockerIds = [];
			lockerIds[0] = parentNodeName;
			_lockerManager.open(lockerIds);
		}
	});

	var OpenLockers = DS.createNode({
		onInvoke : function(params) {
			var path = this.path;
			console.log("[DSA - Bulk Open onInvoke()] path:" + path + " params:" + params);
			console.log("[DSA - Bulk Open on Invoke()] lockerIds:" + params.lockerIds);
			_lockerManager.open(params.lockerIds);
		}
	});

	// DSA Close for Test only
	var CloseLocker = DS.createNode({
		onInvoke : function() {
			var path = this.path;
			var parentNodeName = path.substring(1, path.length -  "/close".length);
			console.log("[DSA - CloseLocker]path:" + path);
			console.log("[DSA - CloseLocker]parentNodeName::::: " + parentNodeName);
			_lockerManager.close([params.lockerIds]);
		}
	});

	var Configure = DS.createNode({
		onInvoke : function() {
			var path = this.path;
			console.log("[DSA - Configure onInvoke()]path:" + path);
			jQuery(document).trigger("lockerConfigChanged");
		}
  	});

  	var ClearOrders = DS.createNode({
		onInvoke : function() {
			var path = this.path;
			console.log("[DSA - ClearOrders onInvoke()]path:" + path);
			//storageManager.clearOrder(option); if success return success else fail;
			//option -> staged or deposit.
			return new DS.SimpleTableResult([
			  ["success"]
			], [
			  {
			    "name": "message",
			    "type": "string"
			  }
			]);
		}
  	});

  	var ResetLockers = DS.createNode({
		onInvoke : function(params) {
			var path = this.path;
			console.log("[DSA - ResetLockers onInvoke()] path:" + path);
			console.log("[DSA - ResetLockers onInvoke()] lockerIds :" + params.lockerIds);
			//storageManager.cancelOrder(); if success return success else fail;
			return new DS.SimpleTableResult([
			  ["success"]
			], [
			  {
			    "name": "message",
			    "type": "string"
			  }
			]);
		}
  	});

  	var CancelOrders = DS.createNode({
		onInvoke : function(params) {
			var path = this.path;
			console.log("[DSA - CancelOrder onInvoke()] path:" + path);
			console.log("[DSA - CancelOrder onInvoke()] orderIds :" + params.orderIds);
			//storageManager.cancelOrder(); if success return success else fail;
			return new DS.SimpleTableResult([
			  ["success"]
			], [
			  {
			    "name": "message",
			    "type": "string"
			  }
			]);
		}
  	});


  	var GetStatus = DS.createNode({
		onInvoke : function() {
			var path = this.path;
			console.log("[DSA - GetStatus onInvoke()] path:" + path);
			// _lockerManager.getStatus()

			return new DS.SimpleTableResult([
			  ["success"]
			], [
			  {
			    "name": "message",
			    "type": "string"
			  }
			]);
		}
  	});



	function initialize(brokerURL, deviceId, lockers, lockerManager, serverDSLinkName, storageManager) {
		console.log("Got brokerURL : " + brokerURL + ", deviceId : " + deviceId + ", locker count : " + lockers.length + ", serverDSLinkName : " + serverDSLinkName);

		keys = new DS.PrivateKey.loadFromString(deviceId);

		var openDef = {
			'$invokable' : 'read',
			'$is' : 'openLocker',
		};

		var closeDef = {
			'$invokable' : 'read',
			'$is' : 'closeLocker'
		};

		var doorStateDef = {
			'$type' : 'enum[CLOSE,OPEN,UNKNOWN,OFFLINE]',
			'?value' : 'UNKNOWN'
		};

		var occupancyStatusDef = {
			'$type' : 'enum[OCCUPIED,VACANT,UNKNOWN,ERROR]',
			'?value' : 'UNKNOWN'
		};

		var load = {
			defs: {
				profile: {
					openLocker: {},
					openLockers: {},
					closeLocker: {},
					configure: {},
					cancelOrder: {},
					clearOrders: {},
					resetLockers: {},
					getStatus: {}
				}
			},
			configure: {
				'$invokable' : 'read',
				'$is' : 'configure'
			},
			openLockers: {
				'$invokable' : 'read',
				'$is' : 'openLockers',
				'$params' : [
					{
						'name': 'lockerIds',
						'type' : 'array'
					}
				]
			},			
			lcuStatus: {
				'$type' : 'enum[ONLINE,OFFLINE]',
				'?value' : 'ONLINE'
			},
			packageUpdate: {
				'$type' : 'string',
				'?value' : ''
			},
			kpiPayload: {
				'$type' : 'string',
				'?value' : ''
			},
			auditPayload: {
				'$type' : 'string',
				'?value' : ''
			},
			cancelOrders: {
				'$invokable' : 'read',
				'$is' : 'cancelOrders',
				'$params' : [
					{
						'name': 'orderId',
						'type' : 'array'
					}
				]
			},
			clearOrders: {
				'$invokable' : 'read',
				'$is' : 'clearOrders',
			},
			resetLockers: {
				'$invokable' : 'read',
				'$is' : 'resetLockers',
				'$params' : [
					{
						'name': 'lockerIds',
						'type' : 'array'
					}
				]
			},
			getStatus: {
				'$invokable' : 'read',
				'$is' : 'getStatus',
			}
		};

		lockers.forEach(function(locker) {
			var logical_id = locker.logicalId;
			console.log("Logical locker id :: " + logical_id);

			load[logical_id] = {
				open: openDef,
				close: closeDef,
				doorState: doorStateDef,
				occupancyStatus: occupancyStatusDef,
				alarmState: doorStateDef
			};
		});
		
		link = new DS.LinkProvider(brokerURL + '/conn', 'link-' + deviceId + '-', {
			profiles: {
		        openLocker: function(path) {
		          return new OpenLocker(path);
		        },
		        openLockers: function(path) {
		          return new OpenLockers(path);
		        },
		        closeLocker: function(path) {
		          return new CloseLocker(path);
		        },
		        configure: function(path) {
		          return new Configure(path);
		    	},
		    	clearOrders: function(path) {
		          return new ClearOrders(path);
		    	},
		    	resetLockers: function(path) {
		          return new ResetLockers(path);
		    	},
		    	cancelOrders: function(path) {
		          return new CancelOrders(path);
		    	},
		    	getStatus: function(path) {
		          return new GetStatus(path);
		    	}
	      	},
	      	defaultNodes: load
   		});

	    link.privateKey = keys;

		_lockerManager = lockerManager;
		_storageManager = storageManager;


		var storage = new DS.WebResponderStorage('storage');
		var _storedNodes; 

		storage.load().then(function(storedNodes) {
		  	console.log("#### [DSA] loaded storage Nodes.....");
		  	_storedNodes = storedNodes
		  	return link.init();
		}).then(function() {
			console.log("#### [DSA] initializing storage.....");
		  	link.link.responder.initStorage(storage, _storedNodes);
		  	console.log("#### [DSA] initialization DONE");
		  	return link.connect();
		}).then(function() {
			console.log("#### [DSA] connected.....");
		  	return link.onRequesterReady;
		}).then(function(requester) {
		  	requester.subscribe('/conns/dual/link-' + serverDSLinkName + '/'+ deviceId + '/stagePackage', function(update) {
			    var data = update.value;            
			    console.log("#requester.subscribtion - stagePackage :: " + data);
			    if(data !=null && data != undefined && data != ''){
			      jQuery(document).trigger("stagedPackagUpdate", data);
			    }

			    return;
			  },3); // QoS option 3 durable & persist, responder/broker will backup the
					// whole cache queue

		/*		  requester.subscribe('/conns/dual/link-' + serverDSLinkName + '/'+ deviceId + '/cancelPackage', function(update) {
		    var data = update.value;            
		    console.log("#requester.subscribtion - cancelPackage :: " + data);
		    if(data !=null && data != undefined && data != ''){
		      jQuery(document).trigger("canceledPackagUpdate", data);
		    }

		    return;
		  },3); 
		*/		  
		}).catch(function(reason) {
		  console.log("#### [DSA] ERROR => " + reason);
		});

	
	}

	function processLcuStatusChange(lcuStatus) {
		console.log("processLcuStatusChange - " + lcuStatus);

		var lcuStatusNode = "/lcuStatus";
		link.updateValue(lcuStatusNode, lcuStatus);
	};

	function processLockerStateChange(locker) {
		console.log("processLockerStateChange - " + JSON.stringify(locker));

		if (locker.doorState != null || locker.doorState != undefined) {
			var doorValueNode = "/"+ locker.logicalId + "/doorState";
			link.updateValue(doorValueNode, locker.doorState);
		}

		if (locker.occupancyStatus != null || locker.occupancyStatus != undefined) {
			var occupancyValueNode = "/"+ locker.logicalId + "/occupancyStatus";
			link.updateValue(occupancyValueNode, locker.occupancyStatus);
		}

		if (locker.alarmState != null || locker.alarmState != undefined) {
			var alarmStateValueNode = "/"+ locker.logicalId + "/alarmState";
			link.updateValue(alarmStateValueNode, locker.alarmState);
		}
	};

	function processPackageInfoChange(pkgInfo) {
		console.log("processPackageInfoChange - " + pkgInfo);

		var updatedPkgNode = "/packageUpdate";
		link.updateValue(updatedPkgNode, pkgInfo);
	};

	function updateKpiPayload(param) {
		console.log("updateKpiPayload - " + param);

		var node = "/kpiPayload";
		link.updateValue(node, param);
	};

	function updateAuditPayload(param) {
		console.log("updateAuditPayload - " + param);

		var node = "/auditPayload";
		link.updateValue(node, param);
	};

	function shutdown() {
		link.close();
		link = null;
	};

	return {
		init: initialize,
		processLockerStateChange: processLockerStateChange,
		processLcuStatusChange: processLcuStatusChange,
		processPackageInfoChange: processPackageInfoChange,
		updateKpiPayload: updateKpiPayload,
		updateAuditPayload: updateAuditPayload,
		shutdown: shutdown
	};
})(jQuery);

