/**
 * New node file
 */
var console = require('vertx/console');
var eventBus = require('vertx/event_bus');
var col_name = 'geoData';

console.log('main.js deployed!!!!!!!!');

function sendPersistorEvent(command, callback) {
	eventBus.send('mongo.persistor', command, function(reply) {
		if (reply.status === "ok") {
			callback(reply);
		} else {
			console.log(reply.message);
		}
	});
};

eventBus.registerHandler('geo.update', function(args, responder) {
	sendPersistorEvent({
		action : "update",
		collection : col_name,
		criteria : args.criteria,
		objNew : args.objNew,
		upsert : args.upsert,
		multi : args.multi
	}, function(reply) {
		responder({
			data : reply
		});
	});
});

eventBus.registerHandler('geo.find', function(data, responder) {
	sendPersistorEvent({
		action : "find",
		collection : col_name,
		matcher : { path : new RegExp(data) }
	}, function(reply) {
		responder({
			data : reply.results
		});
	});
});

eventBus.registerHandler('geo.save', function(data, responder) {
	sendPersistorEvent({
		action : "save",
		collection : col_name,
		document : data
	},function(reply) {
		responder(data);
	});
});

eventBus.registerHandler('geo.geoindex', function(data, responder) {
	sendPersistorEvent({
		action : "command",
		collection : col_name,
		document : {"loc" : "2dsphere"}
	}, function(reply) {
		responder(data);
	});
});
eventBus.registerHandler('geo.delete', function(args, responder) {
	sendPersistorEvent({
		action : "delete",
		collection : col_name,
		matcher : {
			_id : args.id
		}
	}, function(reply) {
		responder({});
	});
//	console.log("$$$$$$$DELETE$$$$$$$$$$$");
});
