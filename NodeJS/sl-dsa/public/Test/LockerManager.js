
/*TODO DSA wiki update 
 * 
 * TODO 
 * OOP Changes : A single kiosk app should able to support multiple locker vendors 
 * 		without code changes (or minimum code changes)
 * Factory Method Pattern - Dynamic creation of Locker vendors
 * Strategy Pattern - Inject dynamic behavior according to certain edge types   
 * 
 * 
 * 1. Server set configuration on IEC which adopter it need to load
 * 	 1.1 Site id, locker model
 *  1.2 Implement IEC device with OOP to maximize re-usability the code for other solutions
 *  1.3 Avoid polling (TODO Check all polling)
 *  	1.3.1 IEC version poll -> DSA updateSoftware API call (Server Push) to check the software version with Server  
 * 
 * 2. IEC Initialization  
 *  2.1 Get configuration info from server push or client poll 
 *  2.2 Based on Edge model (Panasonic, BridgePoint),
 *     2.2.1. Create Locker Vender Type (OOP Concept, properties) 
 *     2.2.2. Create connection type with Locker (Serial port or REST/XML)
 *     2.2.3. Create DSA nodes and initiate DSA connection.
 *     
 * 3. Bulk operation.
 *    
 * NOTE : Get configuration info from Server VS DSA Broker, Responder)
 *    	What benefit we could take advantage holding these info on DSA
 *  1. Data locality(Scalability ) : data retrieval is faster when it closed to where request the data, when system scale horizontally,
 *  		data need to be distributed then data can be in the local. 
 */

"use strict";

// Locker Manager initialization...
var LockerManager = (function() {
	var lockerManager;

	return {
		// Get the Singleton instance if one exists
		// or create one if it doesn't
		getInstance: function (lockerModel, lockerConfig, lockerList) {

		  if ( !lockerManager ) {
		  	if(lockerModel !== null || lockerModel !== undefined) {
		  		if(lockerModel === "BridgePoint") {
				    lockerManager = new BridgePointLockerManager(lockerConfig, lockerList);
		  		} else if(lockerModel === "Panasonic") {
		  	        lockerManager = new PanasonicLockerManager(lockerConfig, lockerList, true);
		  		}
		  	}
		  }
		  return lockerManager;
		}
	};
})();
