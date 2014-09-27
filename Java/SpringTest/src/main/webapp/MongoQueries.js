
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

db.test.find({
	loc : {
		$geoNear : {
			$geometry : {
				type : "Point",
				coordinates : [ 175, 35.5 ]
			},
			$maxDistance : 80000
		}
	}
}).pretty();

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
