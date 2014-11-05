/**
 * New node file
 */

var vertx = require('vertx')
var console = require('vertx/console')
var container = require("vertx/container");

var server = vertx.createHttpServer();

var routeMatcher = new vertx.RouteMatcher();

routeMatcher.get('/test', function(req) {
	var params = req.params();
	console.log(req.query());
});

routeMatcher.put('/*', function(req) {
	var params = req.params();
    req.response().end('put...');    
});

routeMatcher.post('/*', function(req) {
	var params = req.params();
    req.response().end('post...');    
});

routeMatcher.delete('/*', function(req) {
	var params = req.params();
    req.response().end('delete...');    
});

server.requestHandler(routeMatcher).listen(8989, 'localhost');


container.deployModule("io.vertx~mod-mongo-persistor~2.1.0", {
	address : "mongo.persistor",
	db_name : "geoData"
});
container.deployVerticle('main.js');
