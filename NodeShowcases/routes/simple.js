/**
 * Created by HYUN on 01/06/14.
 */

var express = require('express');
var dbConn = require('../db/connection');
var db = dbConn.getDB("testdb", "testdb");

var router = express.Router();

function readFile(filename, enc) {

}

/*
Method : GET
http://localhost:4000/gls/parking
 */
router.route('/parking').get(function(req, res) {

	var collection = db.collection('testdb');
	collection.find({}, function(err, users) {
		if (err)
			res.json(err);
		console.log("[UsEr fOuNd]" + JSON.stringify(users));
		res.json(users);
	});

})

/*
 * 
Method : POST
http://localhost:4000/gls/parking
{
    "_id": "parkingLot_id-2",
    "loc": {
        "type": "Point",
        "coordinates": [
            79.123,
            19.243
        ]
    },
    "path": "US/San Jose/Building B"
} 
 */
.post(function(req, res) {
	var collection = db.collection('testdb');
	// collection.save();
	collection.save(req.body, function(err) {
		console.log("body : " + JSON.stringify(req.body));
		if (err)
			res.json(err);
		res.send({
			message : 'Item Added'
		});
	});
});

/*
Method : PUT
http://localhost:4000/gls/parking/parkingLot_id-1
{
    "path": "US/San Jose/Building A"
}
 */
router.route('/parking/:id').put(function(req, res) {
	
	var collection = db.collection('testdb');
	console.log(req.body);
	collection.update({
		_id : req.params.id
	}, {
		$set : req.body
	} , function(err, result) {
		if (users) {
			res.json(result);
		} else {
			res.json("No result found");
		}
	});

})

/*
Method : GET
http://localhost:4000/gls/parking/parkingLot_id-1
 */
.get(function(req, res) {

	var collection = db.collection('testdb');
	collection.findOne({
		_id : req.params.id
	}, function(err, result) {
		if (err)
			res.json(err);
		
		res.json(result);
	});

})

/*
Method : POST
http://localhost:4000/gls/parking/<COMMAND>
 */
.post(function(req,res){
	var command = req.params.id;
	var collection = db.collection('testdb');

	switch(command){
	
	case "ping":
		db.runCommand({ping:1}, function(err, result) {
			if (err)
				res.json(err);
			
			res.send(result);
		});
		break;
	case "geoNear":
		res.send("geoNear");
		break;
		
		default :
			res.json("No command matched");
	} 
})


/*
Method : DELETE
http://localhost:4000/gls/parking/parkingLot_id-1
 */

.delete(function(req,res){
	var collection = db.collection('testdb');
	collection.remove({
		_id : req.params.id
	}, function(err, result) {
		if (err)
			res.json("Data is not exist");
		
		res.json(result);
	});
});


module.exports = router;
