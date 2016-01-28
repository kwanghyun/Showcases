/**
 * New node file
 */
var META = {};
function initMetaDataDefinition() {
	META = {
		COMMAND : {
			id : 'action',
			OPEN : {
				key : 'OPEN',
				value : 'OP'
			},
			CHANGE_LOCKER_CONDITION :{
				key : 'CHANGE_LOCKER_CONDITION',
				value : 'CL'
			},
			GET_STATUS :{
				key : 'GET_STATUS',
				value : 'SB'
			},
			GET_CONNECTED_LOCKER_GROUPS :{
				key : 'GET_CONNECTED_LOCKER_GROUPS',
				value : 'AP'
			},
			GET_LOCKERS_STATUS :{
				key : 'GET_LOCKERS_STATUS',
				value : 'AB'
			},
			GET_SYSTEM_STATUS :{
				key : 'GET_SYSTEM_STATUS',
				value : 'RS'
			},
			SET_CLOSE_FORGET :{
				key : 'SET_CLOSE_FORGET',
				value : 'CW'
			},
			GET_CLOSE_FORGET :{
				key : 'GET_CLOSE_FORGET',
				value : 'CR'
			},
			SET_FORCE_OPEN :{
				key : 'SET_FORCE_OPEN',
				value : 'FW'
			},
			GET_FORCE_OPEN :{
				key : 'GET_FORCE_OPEN',
				value : 'FR'
			},
			SYSTEM_REBOOT :{
				key : 'SYSTEM_REBOOT',
				value : 'RB'
			},
			SET_ALARM_CONTROL :{
				key : 'SET_ALARM_CONTROL',
				value : 'WA'
			},
			GET_DOOR_OPEN_COUNT :{
				key : 'GET_DOOR_OPEN_COUNT',
				value : 'NB'
			},
			SET_DOOR_OPEN_COUNT :{
				key : 'SET_DOOR_OPEN_COUNT',
				value : 'NC'
			},
			NOTI_LOCKER_STATUS :{
				key : 'NOTI_LOCKER_STATUS',
				value : 'EB'
			},
			NOTI_SYSTEM_STATUS :{
				key : 'NOTI_SYSTEM_STATUS',
				value : 'ES'
			}
		},

		LOCKER_GROUP_NUMBER :{
			id : 'lockerGroupNo'
		},
		LOCKER_NUMBER : {
			id : 'lockerNo'
		},
		INTERVAL : {
			id : 'interval'
		},
		DURATION : {
			id : 'duration'
		},
		LOCKER_CONDITION : {
			id : 'lockerCondition',
			NONE : {
				key : 'LOCKER_CONDITION.NONE',
				value : '0'
			},
			PACKAGE_EXIST : {
				key : 'LOCKER_CONDITION.PACKAGE_EXIST',
				value : '1'
			},
			PACKAGE_NOT_EXIST : {
				key : 'LOCKER_CONDITION.PACKAGE_NOT_EXIST',
				value : '2'
			}
		},

		ALARM : {
			CONTROL : {
				id : 'alarmControl',
				OFF : {
					key : 'ALARM.CONTROL.OFF',
					value : '0'
				},
				ON : {
					key : 'ALARM.CONTROL.ON',
					value : '1'
				}
			},
			STATUS : {
				id : 'alarmStatus',
				OFF : {
					key : 'ALARM.STATUS.OFF',
					value : '0'
				},
				ON_CLOSE_FORGET : {
					key : 'ALARM.STATUS.ON_CLOSE_FORGET',
					value : '1'
				},
				ON_FORCE_OPEN : {
					key : 'ALARM.STATUS.ON_FORCE_OPEN',
					value : '2'
				},
				ON_CLOSE_FORGET_TIMER : {
					key : 'ALARM.STATUS.ON_CLOSE_FORGET_TIMER',
					value : '3'
				},
				CONNECTION_ERROR : {
					key : 'ALARM.STATUS.CONNECTION_ERROR',
					value : '9'
				}
			}
		},

		DOOR : {
			id : 'door',
			CLOSED : {
				key : 'DOOR.CLOSED',
				value : '0'
			},
			OPEN : {
				key : 'DOOR.OPEN',
				value : '1'
			},
			FORCE_OPEN : {
				key : 'DOOR.FORCE_OPEN',
				value : '2'
			},
			DOOR_ERROR1 : {
				key : 'DOOR.DOOR_ERROR1',
				value : '3'
			},
			DOOR_ERROR2 : {
				key : 'DOOR.DOOR_ERROR2',
				value : '4'
			},
			CONNECTION_ERROR : {
				key : 'DOOR.CONNECTION_ERROR',
				value : '9'
			}			
		},
		PACKAGE : {
			id : 'package',
			NOT_EXIST : {
				key : 'PACKAGE.NOT_EXIST',
				value : '0'
			},
			EXIST : {
				key : 'PACKAGE.EXIST',
				value : '1'
			},	
			CONNECTION_ERROR : {
				key : 'PACKAGE.CONNECTION_ERROR',
				value : '9'
			}	
		},

		RESPONSE : {
			id : 'response',
			SUCCESS : {
				key : 'RESPONSE.SUCCESS',
				value : '00'
			},
			ERROR_SYNTAX : {
				key : 'RESPONSE.ERROR_SYNTAX',
				value : '11'
			},
			ERROR_OVERFLOW : {
				key : 'RESPONSE.ERROR_OVERFLOW',
				value : '12'
			},
			ERROR_NO_CONNECTIVITY : {
				key : 'RESPONSE.ERROR_NO_CONNECTIVITY',
				value : '13'
			}
		},
		
		SYSTEM_STATUS :{
			id : 'systemStatus',
			OK : {
				key : 'SYSTEM_STATUS.OK',
				value : '0'
			},
			ON_INITIALIZATION : {
				key : 'SYSTEM_STATUS.ON_INITIALIZATION',
				value : '1'
			},
			HARDWARE_ERROR : {
				key : 'SYSTEM_STATUS.HARDWARE_ERROR',
				value : '2'
			}
		},
		LOCKER_GROUP_STATUS : {
			id : 'lockerGroupStatus',
			NOT_CONNECTED : {
				key : 'LOCKER_GROUP_STATUS.NOT_CONNECTED',
				value : '0'
			},
			CONNECTED : {
				key : 'LOCKER_GROUP_STATUS.CONNECTED',
				value : '1'
			}
		},
		LOCKERS_STATUS : {
			id : 'lockersStatus',
			chunk : 3
		},
		DOOR_OPEN_COUNT : {
			id : 'doorOpenCount'
		}
	}
};
