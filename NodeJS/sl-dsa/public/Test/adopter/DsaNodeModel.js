/**
 * 
 */


provider.load({
	defs : {
		profile : {
			openLocker : {},
	        bulkOpenLocker : {},
			closeLocker : {},
	        configure : {}
		}
	},
	configure : {
	    '$invokable' : 'read',
	    '$is' : 'pushLockerConfig',
	    '$params' : {
	      'payload' : {
	        'type':'map'
	      }
	    }
	},

	bulkopen:{
		'$invokable' : 'read',
		'$is' : 'bulkOpenLocker',
		'$params' : {
			'lockerIds' : {
				'type' : 'array'
			}
		}
	},

	locker1 : {
		open : {
			'$invokable' : 'read',
			'$is' : 'openLocker'
		},
		close : {
			'$invokable' : 'read',
			'$is' : 'closeLocker'
		},
		doorState : {
			'$type' : 'enum[CLOSE,OPEN,ERROR]',
			'?value' : 'CLOSE'
		},
		occupancyStatus : {
			'$type' : 'enum[OCCUPIED,VACANT,UNKNOWN,ERROR]',
			'?value' : 'CLOSE'
		}
	},
	locker2 : {
		open : {
			'$invokable' : 'read',
			'$is' : 'openLocker'
		},
		close : {
			'$invokable' : 'read',
			'$is' : 'closeLocker'
		},
		doorState : {
			'$type' : 'enum[CLOSE,OPEN,ERROR]',
			'?value' : 'CLOSE'
		},
		occupancyStatus : {
			'$type' : 'enum[OCCUPIED,VACANT,UNKNOWN,ERROR]',
			'?value' : 'CLOSE'
		}
	},
});

var lockerId = provider.getNode('/')._config[lockerIdStr];
var physical_id = provider.getNode('/' + lockerId)._config["lockerId"];
lockerManager.sendCommand("OPEN", physical_id);


