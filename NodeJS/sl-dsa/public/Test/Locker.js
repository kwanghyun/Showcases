// Common Locker Definition...
Locker = function(logicalId, physicalId) {

	var LOCKER_STATE = {
		DOOR : {
			CLOSE : "CLOSE",
			OPEN : "OPEN",
			OFFLINE : "OFFLINE"
		},
		OCCUPANCY : {
			OCCUPIED : "OCCUPIED",
			EMPTY : "VACANT"
		},
		ALARM : {
			OFF : "OFF",
			ON : "ON"
		}
	}

	this.logicalId = logicalId;
	this.physicalId = physicalId;
	this.doorState;
	this.occupancyStatus;
	this.alarmState;
};
