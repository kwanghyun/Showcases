"use strict";

var MyTV = (function($) {
	var uniqueId = "UUID-1234567890";
	var keys;
	var link;
	var dslinkName = "testTV"
	var serverDSLinkName = "server"
	// var brokerURL = "http://localhost:8080";
	// var brokerURL = "http://10.106.8.159:8080";
	// var brokerURL = "http://10.203.31.192:8080";
	var brokerURL = 'http://10.106.9.143:8080';
	// var brokerURL = "http://10.194.170.132:8080";
	// var brokerURL = "http://10.203.55.126:8080";
	
	function turnOnTV(param){
		//TODO 
		console.log("Calling turnOnTV(), sends downstream command to device");
		eventHandler("ON");
	};

	function turnOffTV(param){
		//TODO 
		console.log("Calling turnOffTV(), sends downstream command to device");
		eventHandler("OFF");
	};

	//Callback for the device status change.
	function eventHandler(response){
		//Propogate status change to the upstream
		var statusNode = "/deviceStatus";
		link.updateValue(statusNode, response);
		
		//Notify status change to the application layer
		jQuery(document).trigger("stateChanged", response);
	};


	/* * * * * * * * * * *
	* DSA Implementation
	* * * * * * * * * * */
	var TurnOn = DS.createNode({
		onInvoke : function() {
			var path = this.path;
			console.log("[DSA - TurnOn onInvoke()]path:" + path);
			turnOnTV(path);
		}
	});

	var TurnOff = DS.createNode({
		onInvoke : function() {
			var path = this.path;
			console.log("[DSA - TurnOff onInvoke()]path:" + path);
			turnOffTV(path);
		}
	});

	keys = new DS.PrivateKey.loadFromString(uniqueId);

	var load = {
		defs: {
			profile: {
				turnOn: {},
				turnOff: {}
			}
		},
		turnOn: {
			'$invokable' : 'read',
			'$is' : 'turnOn',
			'$params' : 'string'
		},	
		turnOff: {
			'$invokable' : 'read',
			'$is' : 'turnOff',
			'$params' : 'string'
		},			
		deviceStatus: {
			'$type' : 'enum[ON,OFF,UNKNOWN]',
			'?value' : 'UNKNOWN'
		}

	};
	
	link = new DS.LinkProvider(brokerURL + '/conn', 'link-' + dslinkName + '-', {
		profiles: {
	        turnOn: function(path) {
	          return new TurnOn(path);
	        },
	        turnOff: function(path) {
	          return new TurnOff(path);
	        }
      	},
      	defaultNodes: load,
      	token:"dQ3aj2WjvGO1NF9L6Kckwq5F4jHDisLsRT3vuFjZp8DEEONo"
	});

    link.privateKey = keys;
    
	link.connect().then(function() {
		return link.onRequesterReady;
    }).then(function(requester) {

		requester.subscribe('/conns/link-' + serverDSLinkName + '/sw_version', function(update) {
	        var data = update.value;	        
		    console.log("#requester.subscribtion - sw_version :: " + data);
	  		if(data !=null && data != undefined && data != ''){
	    		jQuery(document).trigger("serverValueUpdate", data);
			}
    		return;
		});

  	}).catch(function(reason) {
      	console.log(reason);
  	});

	return {
		turnOn : turnOnTV,
		turnOff : turnOffTV
	};
})(jQuery);

