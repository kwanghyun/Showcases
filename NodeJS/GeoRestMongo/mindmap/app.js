/**
 * New node file
 */
var container = require("vertx/container");
container.deployModule("io.vertx~mod-web-server~2.0.0-final", {
	port : 8989,
	host : "localhost",
	bridge : true,
	inbound_permitted : [ {
		address : 'test'
	}, {
		address : 'mindMaps.list'
	}, {
		address : 'mindMaps.save'
	}, {
		address : 'mindMaps.delete'
	}, {
		address_re: 'mindMaps\\.editor\\..+' 
	} ],
	outbound_permitted : [ {
		address_re: 'mindMaps\\.events\\..+'
	} ]
});

container.deployModule("io.vertx~mod-mongo-persistor~2.1.0", {
	address : "mongo.persistor",
	db_name : "mindMaps"
});
container.deployVerticle('mindmaps.js');
container.deployVerticle('editor.js');

