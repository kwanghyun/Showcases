/**
 * New node file
 */
var http = require('vertx/http');
var console = require('vertx/console');
var container = require("vertx/container");
var eventBus = require('vertx/event_bus');

var server = http.createHttpServer();

var routeMatcher = new http.RouteMatcher();


/*
 * Store data 
 * Example 
{
  "_id" : "parkingLot_id-3",
  "loc" : {
    "type" : "Point",
    "coordinates" : [79.123, 19.243]
  },
  "path" : "US/San Jose/Building B"
}
 */
routeMatcher.put('/test', function(req) {

	req.bodyHandler(function(data) {
		var content =  JSON.parse(data.toString());
		console.log("DATA : " + content);
		console.log("DATA : " + content.test);
		
		eventBus.send('geo.save', content,  function(result) {
			req.response.end('result : ' + JSON.stringify(result));
			
			//TODO ensureIndex for when create a new collection.
		});
	});
});


/*
 * Update
 * Example
{
  "criteria": {
  		"_id": "parkingLot_id-3"
    },
    "objNew" : {
        "$set": {
            "count": 30
        }
     },
    "upsert" : true,
    "multi" : false
}
{
  "criteria": {
  		"_id": "parkingLot_id-3"
    },
    "objNew" : {
        "$inc": {
            "count": 1
        }
     },
    "upsert" : true,
    "multi" : false
}

 */
routeMatcher.post('/test', function(req) {
	req.bodyHandler(function(data) {
		var content =  JSON.parse(data.toString());
		console.log("content.query : " + content);
		console.log("content.query.path : " + JSON.stringify(content));
		
		eventBus.send('geo.update', content,  function(result) {
			req.response.end('result : ' + JSON.stringify(result));
			
		});
	});
});


/*
 * Get data 
 * Example 
{
  "query": {
  "loc" : {  
  "$geoNear" : {  
    "$geometry" :  {  
      "type" : "Point",  
      "coordinates":[72, 15] },
      "$maxDistance" : 80000 }  
  	}  
  }
}
 */
routeMatcher.post('/test/_search', function(req) {
	
	req.bodyHandler(function(data) {
		var content =  JSON.parse(data.toString());
		console.log("DATA : " + content);
		console.log("Query : " + JSON.stringify(content.query));
		
		eventBus.send('geo.find', content.query,  function(result) {
			req.response.end('result : ' + JSON.stringify(result));
			
		});
	});
});


/*
 * Get data with Regx
 * Example 
{
  "query": {
    "path" : "/^US/San Jose"
  }
}
 */
routeMatcher.post('/test/_search/regex', function(req) {
	
	req.bodyHandler(function(data) {
		var content =  JSON.parse(data.toString());
		console.log("content.query : " + content.query);
		console.log("content.query.path : " + JSON.stringify(content.query.path));
		
		eventBus.send('geo.find', content.query,  function(result) {
			req.response.end('result : ' + JSON.stringify(result));
			
		});
	});

});

routeMatcher.delete('/test/:docId', function(req) {
	var docId = req.params().get('docId');
	console.log("docID : " + docId);
	var data = {id : docId};
	eventBus.send('geo.delete',data,  function(result) {
		req.response.end('result : ' + JSON.stringify(result));
	});
});



/*
 * Run command
 * Example : Real-time top 10 recommendations for parking lot near by you. Based on purchase history
		{ aggregate: "geoData",
		    pipeline: [
	                     {$geoNear: {
	                          near: { type: "Point", coordinates: [ 72 , 15 ] },
	                          distanceField: "dist.calculated",
	                          maxDistance: 800000,
	                          spherical: true
	                       }
	                     },
	                     { $group : { _id : "$_id" , count : { $sum : 1 } } },
	                     { $sort : { count : -1, _id : 1}},
	                     { $limit : 10 }
	                  ]
		}

 * Example : Most popular top 10 parking lot. Based on purchase history
{ aggregate: "geoData",
    pipeline: [
               { $group : { _id : "$_id" , count : { $sum : 1 } } },
               { $sort : { count : -1}},
               { $limit : 10 }
            ]
}

 * Example : Create an index 
{
    createIndexes: "geoData",
    indexes: [
        {
            key: {
                loc : "2dsphere",
            },
            name: "geoDataIndex",
            2dsphereIndexVersion : 2,
        }
    ]
  }

 */
routeMatcher.post('/test/command', function(req) {
	req.bodyHandler(function(data) {

		var content =  data.toString();
		console.log("content : " + content);
		eventBus.send('geo.command', content,  function(result) {
			req.response.end('result : ' + JSON.stringify(result));
			
		});
	});
});


server.requestHandler(routeMatcher).listen(8080, 'localhost');

container.deployModule("io.vertx~mod-mongo-persistor~2.1.0", {
	address : "mongo.persistor",
	db_name : "geoData"
});

container.deployVerticle('main.js');

console.log('Server is up now...');