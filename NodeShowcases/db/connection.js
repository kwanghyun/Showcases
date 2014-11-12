var mongojs = require('mongojs');

/*
 * databaseUrl = "username:password@example.com/mydb"
 */

var getDB = function getDB(databaseUrl, collectionName) {
	var db = require("mongojs").connect(databaseUrl, [ collectionName ]);
	return db;
};

exports.getDB = getDB;
