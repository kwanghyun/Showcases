
insertAllTestData(100);
db.test.ensureIndex({
	"loc" : "2dsphere"
});
db.test.ensureIndex({
	"tile" : "2d"
}, {
	"min" : 0,
	"max" : 100
});

/**
 * 2DSphere Geo-spatial find Query Examples
 */

db.geoLocation.find({
	loc : {
		$geoNear : {
			$geometry : {
				type : "Point",
				coordinates : [ 37, -121 ]
			},
			$maxDistance : 80000
		}
	}
}).pretty();

db.testdb.find(
{
	  $near: {
	     $geometry: {
	        type: "Point" ,
	        coordinates: [ 72 , 15 ]
	     },
	     $minDistance: 80000
	  }
});

db.test.find({
	loc : {
		$geoWithin : {
			$box : [ [ 174.5, 34.5 ], [ 175.6, 35.6 ] ]
		}
	}
}).pretty();

db.test.find(
		{
			loc : {
				$geoWithin : {
					$geometry : {
						type : "Polygon",
						coordinates : [ [ [ 175.5, 35.5 ], [ 176.5, 35.5 ],
								[ 176.5, 36.5 ], [ 175.5, 36.5 ],
								[ 175.5, 35.5 ] ] ]
					},
				}
			}
		}).pretty();

db.test.find({
	loc : {
		$geoWithin : {
			$center : [ [ 175.5, 35.5 ], 1 ]
		}
	}
}).pretty();

/**
 * 2D Geo-spatial find Query Examples
 */

db.test.find({
	"tile" : {
		"$near" : [ 15, 15 ]
	}
}).limit(4).pretty();

db.test.find({
	"tile" : {
		"$within" : {
			"$box" : [ [ 14, 14 ], [ 15, 15 ] ]
		}
	}
}).pretty();

db.test.find({
	"tile" : {
		"$within" : {
			"$center" : [ [ 15, 15 ], 1 ]
		}
	}
}).pretty();

db.test.find({
	"tile" : {
		"$within" : {
			"$polygon" : [ [ 14, 14 ], [ 16, 14 ], [ 15, 15 ] ]
		}
	}
}).pretty();



db.testdb.find({
	loc : {
		$geoWithin : {
			$center : [ [ 72, 15 ], 0.1 ]
		}
	}
}).pretty();


db.testdb.find({
	$or : [ {
		loc : {
			$geoWithin : {
				$center : {
					type : "Point",
					coordinates : [ [ 72, 15 ], 1 ]
				},
			}
		}
	}, {
		path : {
			$regex : "^/US/California/San[^\S]Jose/Building[^\S]4/floor[5-7]"
		}
	} ]
});



db.testOne.find({
	loc : {
		$geoNear : {
			$geometry : {
				type : "Point",
				coordinates : [ 170, 30 ]
			},
			$maxDistance : 80000
		}
	}
}).explain();

db.testMany.find({
	loc : {
		$geoNear : {
			$geometry : {
				type : "Point",
				coordinates : [ 170, 30 ]
			},
			$maxDistance : 80000
		}
	}
});
