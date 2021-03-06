
var paths = new Array();
paths[0] = "/US";
paths[1] = "/US/California";
paths[2] = "/US/California/San Jose";
paths[3] = "/US/California/San Jose/Building J";
paths[4] = "/US/California/San Jose/Building O";
paths[5] = "/US/California/San Jose/Building O/floor1";
paths[6] = "/US/California/San Jose/Building O/floor2";
paths[7] = "/US/California/San Jose/Building J/floor1";
paths[8] = "/US/California/San Jose/Building J/floor2";

for (var i = 1; i < 35; i++) {
	paths.push("/US/California/San Jose/Building " + i);
	paths.push("/US/California/San Jose/Building " + i + "/floor1");
	paths.push("/US/California/San Jose/Building " + i + "/floor2");
	paths.push("/US/California/San Jose/Building " + i + "/floor3");
	paths.push("/US/California/San Jose/Building " + i + "/floor4");
	paths.push("/US/California/San Jose/Building " + i + "/floor5");
	paths.push("/US/California/San Jose/Building " + i + "/floor6");
	paths.push("/US/California/San Jose/Building " + i + "/floor7");
	paths.push("/US/California/San Jose/Building " + i + "/floor8");
}

var catalogList = new Array();

catalogList.push([ "Electric" ]);
catalogList.push([ "Electric", "TV" ]);
catalogList.push([ "Electric", "TV", "LCD" ]);
catalogList.push([ "Electric", "TV", "LCD", "Samsung" ]);
catalogList.push([ "Electric", "TV", "LCD", "LG" ]);
catalogList.push([ "Electric", "TV", "LCD", "Sony" ]);
catalogList.push([ "Electric" ]);
catalogList.push([ "Electric", "Phone" ]);
catalogList.push([ "Electric", "Phone", "Smart Phone" ]);
catalogList.push([ "Electric", "Phone", "Smart Phone", "iPhone" ]);
catalogList.push([ "Electric", "Phone", "Smart Phone", "Samsung" ]);
catalogList.push([ "Electric", "Phone", "Smart Phone", "LG" ]);
catalogList.push([ "Electric", "Phone", "Smart Phone", "Sony" ]);
catalogList.push([ "Electric", "Phone", "Smart Phone", "Shaomi" ]);
catalogList.push([ "Automotive" ]);
catalogList.push([ "Automotive", "Standard" ]);
catalogList.push([ "Automotive", "Standard", "Hyundai" ]);
catalogList.push([ "Automotive", "Standard", "Kia" ]);
catalogList.push([ "Automotive", "Standard", "Honda" ]);
catalogList.push([ "Automotive", "Standard", "Toyota" ]);
catalogList.push([ "Automotive", "Standard", "GM" ]);
catalogList.push([ "Automotive", "Standard", "Nissan" ]);
catalogList.push([ "Automotive", "Standard", "Lexus" ]);

var users = new Array();
var emails = new Array();
var comments = new Array();
var movies = new Array();
var movieTitles = new Array();
var movieDescription = new Array();
var movieLength = new Array();
var movieRatings = new Array();
var movieRatingList = [ "G", "PG", "PG-13", "R", "NC-17" ];
var movieImages = new Array();
var movieTrailers = new Array();
var stars = new Array();

for (var i = 1; i < 101; i++) {
	users.push("user" + i);
	emails.push("user" + i + "@exmaple.com");
	comments.push("user" + i + " commented....");
}

for (var i = 1; i < 101; i++) {
	stars.push("star" + i);
}

for (var i = 1; i < 101; i++) {
	movies.push("movie_id-" + i);
	movieTitles.push("movie_title-" + i);
	movieDescription.push("movie_discription-" + i);
	movieLength.push(getRandomInRange(95, 180, 0));
	movieRatings.push(movieRatingList[Math.floor(Math.random()
			* movieRatingList.length)]);
	movieImages.push("movie_image-" + i);
	movieTrailers.push("movie_trailer-" + i);
}

function getRandomComments(num) {

	var randomIteration = Math.floor(Math.random() * num);
	var comment = "[";
	for (var i = randomIteration; i > 0; i--) {
		var randomNum = Math.floor(Math.random() * users.length);
		comment += '{ "user" : "' + users[randomNum] + '" , ';
		comment += ' "email" : "' + emails[randomNum] + '" , ';
		comment += ' "score" : ' + Math.floor(Math.random() * 6) + ' , ';
		comment += ' "comment" : "' + comments[randomNum] + '"},';
	}
	if (randomIteration > 0)
		comment = comment.substring(0, comment.length - 1);
	comment += ']';
	return comment;
}

function getRandomKeyValuePairs(num, key, value, array) {

	var randomIteration = Math.floor(Math.random() * num);

	for (var i = randomIteration; i > 0; i--) {
		var randomNum = Math.floor(Math.random() * array.length);
		str += '{ "' + key + '" : "' + array[randomNum] + '" , ';
		str += ' "' + value + '" : "' + array[randomNum] + '"},';
	}
	if (randomIteration > 0)
		str = str.substring(0, str.length - 1);

	return str;
}

function getRandomArray(num, array) {
	var randomIteration = Math.floor(Math.random() * num);
	var str = "[";
	for (var i = randomIteration; i > 0; i--) {
		var randomNum = Math.floor(Math.random() * array.length);
		str += '"' + array[randomNum] + '" ,';
	}
	if (randomIteration > 0)
		str = str.substring(0, str.length - 1);
	str += ']';
	return str;
}

function getRandomCategories() {
	var str = "[";

	var randomNum = Math.floor(Math.random() * catalogList.length);
	var catagoryList = catalogList[randomNum];
	for (var i = 0; i < catalogList[randomNum].length; i++) {
		str += '{ "id-" : "' + 'id-' + catagoryList[i].toLowerCase() + '" , ';
		str += ' "name" : "' + catagoryList[i] + '"},';
	}

	str = str.substring(0, str.length - 1);
	str += ']';
	return str;
}

function getRandomDateArray(num) {
	var arr = new Array();
	var randomIteration = Math.floor(Math.random() * num);
	for (var i = randomIteration; i > 0; i--) {
		arr.push(JSON.stringify(getRandomDate(new Date(2014, 9, 17), new Date(
				2014, 10, 17))));
	}
	arr.sort();
	return arr;

}

function getRandomDate(start, end) {
	return new Date(start.getTime() + Math.random()
			* (end.getTime() - start.getTime()));
}

function getRandomInRange(from, to, fixed) {
	return (Math.random() * (to - from) + from).toFixed(fixed) * 1;
}

function getRandomMovies(num) {
	var randomIteration = Math.floor(Math.random() * num);
	var str = "[";
	for (var i = randomIteration; i > 0; i--) {
		var randomNum = Math.floor(Math.random() * users.length);
		str += '{ "id" : "' + movies[randomNum] + '" , ';
		str += ' "title" : "' + movieTitles[randomNum] + '" , ';
		str += ' "description" : "' + movieDescription[randomNum] + '" , ';
		str += ' "length" : ' + movieLength[randomNum] + ' , ';
		str += ' "star" : ' + getRandomArray(5, stars) + ' , ';
		str += ' "showTimes" : [' + getRandomDateArray(10) + '] , ';
		str += ' "image" : "' + movieImages[randomNum] + '" , ';
		str += ' "trailers" : "' + movieTrailers[randomNum] + '" },';
	}
	if (randomIteration > 0)
		str = str.substring(0, str.length - 1);
	str += ']';
	return str;
}

function insertTheaterData(num) {
	db.itemLocation.insert({
		id : "theater_id" + num,
		name : "theater_name" + num,
		phoneNumber : Math.round((Math.random() * 1000000000)),
		movies : JSON.parse(getRandomMovies(10)),
		path : paths[Math.floor(Math.random() * paths.length)],
		loc : {
			type : "Point",
			coordinates : [ getRandomInRange(170, 179, 5),
					getRandomInRange(30, 40, 5) ]
		},
		tile : [ getRandomInRange(10, 20, 0), getRandomInRange(10, 20, 0) ]
	});
}

function insertDealData(num) {
	var start = getRandomDate(new Date(2013, 0, 1), new Date());
	var end = getRandomDate(start, new Date(2015, 5, 1));

	db.itemLocation.insert({
		name : "Deal Title-" + num,
		startTime :new Date(start),
		endTime : new Date(end),
		image : "deal_image-" + num,
		website : "deal_website-" + num,
		catagries : JSON.parse(getRandomCategories()),
		path : paths[Math.floor(Math.random() * paths.length)],
		location : {
			type : "Point",
			coordinates : [ getRandomInRange(170, 179, 5),
					getRandomInRange(30, 40, 5) ]
		},
		tile : [ getRandomInRange(10, 20, 0), getRandomInRange(10, 20, 0) ]
	});
}

function insertAdData(num) {
	db.itemLocation.insert({
		name : {
			first : "firstname" + num,
			last : "lastname" + num
		},
		title : "advertisement-" + num,
		path : paths[Math.floor(Math.random() * paths.length)],
		loc : {
			type : "Point",
			coordinates : [ getRandomInRange(170, 179, 5),
					getRandomInRange(30, 40, 5) ]
		},
		tile : [ getRandomInRange(10, 20, 0), getRandomInRange(10, 20, 0) ],
		startDate : getRandomDate(new Date(2012, 0, 1), new Date()),
		catalog : catalogList[Math.floor(Math.random() * catalogList.length)],
		price : Math.floor(Math.random() * 100 + 1) * 100,
		comments : JSON.parse(getRandomComments(5))
	});
}

function insertParkingLots(num) {
	db.testdb.insert({
		path : paths[Math.floor(Math.random() * paths.length)],
		loc : {
			type : "Point",
			coordinates : [ getRandomInRange(70, 74, 5),
							getRandomInRange(13, 16, 5) ]
		},
		parkinglotId : "parkinglot-id_" + (Math.floor(Math.random() * 100 + 1)),
		price : Math.floor(Math.random() * 100 + 1) * 100,
		buyer : users[Math.floor(Math.random() * users.length)],
		score : Math.floor(Math.random() * 6),
		comments : JSON.parse(getRandomComments(5))
	});
}

/*
 * Real-time top 10 recommendations for parking lot near by you. (Based on user's purchase history)
 */
db.testdb.aggregate([
                     {$geoNear: {
                          near: { type: "Point", coordinates: [ 72 , 15 ] },
                          distanceField: "dist.calculated",
                          maxDistance: 800000,
                          query: { buyer: "user11" },
                          spherical: true
                       }
                     },
                     { $group : { _id : "$parkinglotId" , count : { $sum : 1 } } },
                     { $sort : { count : -1, _id : 1}},
                     { $limit : 10 }
                  ]);



/*
 * GeoNear with Regular expression together (floor range)
 */
db.testdb.aggregate([
                     {$geoNear: {
                          near: { type: "Point", coordinates: [ 72 , 15 ] },
                          distanceField: "dist.calculated",
                          maxDistance: 800000,
                          query: { path : {$regex : "^/US/California/San[^\S]Jose/Building[^\S]4/floor[5-7]"} },
                          spherical: true
                       }
                     },
                     { $sort : { path: -1}}
                  ])

db.runCommand(
		{ aggregate: "testdb",
		    pipeline:[
	                     {$geoNear: {
	                          near: { type: "Point", coordinates: [ 72 , 15 ] },
	                          distanceField: "dist.calculated",
	                          maxDistance: 800000,
	                          query: { path : {$regex : "^/US/California/San[^\S]Jose/Building[^\S]4/floor[5-7]"} },
	                          spherical: true
	                       }
	                     },
	                     { $sort : { path: -1}}
	                  ]
		}
);


db.runCommand(
{
	geoNear : "testdb",
	near : {
		type : "Point",
		coordinates : [ 73.9667, 14.78 ]
	},
	spherical : true,
	query : {
		path : {
			$regex : "^/US/California/San[^\S]Jose/Building[^S]17/floor[1-3]"
		}
}
});


db.runCommand(
		{ aggregate: "testdb",
		    pipeline: [
	                     {$geoNear: {
	                          near: { type: "Point", coordinates: [ 72 , 15 ] },
	                          distanceField: "dist.calculated",
	                          maxDistance: 800000,
	                          query: { buyer: "user11" },
	                          spherical: true
	                       }
	                     },
	                     { $group : { _id : "$parkinglotId" , count : { $sum : 1 } } },
	                     { $sort : { count : -1, _id : 1}},
	                     { $limit : 10 }
	                  ]
		}
);


db.testdb.find({
	$or : [ {
		loc : {
			$geoNear : {
				$geometry : {
					type : "Point",
					coordinates : [ 72, 15 ]
				},
				$maxDistance : 80000
			}
		}
	}, {
		path : {
			$regex : "^/US/California/San[^\S]Jose/Building[^\S]4/floor[5-7]"
		}
	} ]
});

db.testdb.find({
	"loc" : {
		$within : {
			$center : [ [ 72, 15], 1]
		}
	}
}, {
	"price" : 1,
	"path" : 1
});


db.testdb.find({
	"loc" : {
		$within : {
			$center : [ [ 72, 15], 0.15 ]
		}
	}
})

db.testdb.find({
	"loc" : {
		$within : {
			$center : [ [ 72, 15], 0.15 ]
		}
	}
}, {
	"path" : {
		$regex : "^/US/California/San[^\S]Jose/Building[^\S]4/floor[5-7]"
	}
});

/*
 * Map Reduce Exmaple
 */

var mapFunc = function(){
	emit(this.parkinglotId, this.price);
};

var reduceFunc = function(keyParkingLotId, prices ){
	return Array.sum(prices);
};

db.testdb.mapReduce(
		mapFunc,
		reduceFunc,
		{out : {inline : 1}}
);

db.runCommand({
	'mapreduce' : 'testdb',
	'map' : function() {
		emit(this.parkinglotId, this.price);
	},
	'reduce' : function(key, values) {
		return Array.sum(values);
	},
	'out' : 'Income_byParkingLot'
});

/*
 * Map recude exmpale
 * Return parking lots avg score from the all user rating. 
 */
var mapFunc2 = function(){
	var key = this.parkinglotId;
	for( var i = 0; i < this.comments.length; i++){
		var value = {
				count : 1,
				score : this.comments[i].score
		};
		emit(key, value);
	}
};

var reduceFunc2 = function(keyParkingLotId, valObjs ){
	reduceVal = {count : 0, score :0};
	
	for(i = 0; i < valObjs.length; i++){
		reduceVal.count += valObjs[i].count;
		reduceVal.score += valObjs[i].score;
	}
	return reduceVal;
};

var finallizeFunc2 = function(key, reducedVal){
	reducedVal.avg = reducedVal.score/reducedVal.count;
	return reducedVal;
};

db.testdb.mapReduce(
		mapFunc2,
		reduceFunc2,
		{
			out : {inline : 1},
			finalize : finallizeFunc2
		}
);

db.runCommand({
	'mapreduce' : 'testdb',
	'map' : function() {
		emit(this.parkinglotId, this.price);
	},
	'reduce' : function(key, values) {
		return Array.sum(values);
	},
	'out' : 'Income_byParkingLot'
});
/*
 * Real-time most popluar parking lot top 10.
 */
db.testdb.aggregate([
                     { $group : { _id : "$parkinglotId" , count : { $sum : 1 } } },
                     { $sort : { count : -1}},
                     { $limit : 10 }
                  ]);

db.runCommand(
{ aggregate: "testdb",
    pipeline: [
               { $group : { _id : "$parkinglotId" , count : { $sum : 1 } } },
               { $sort : { count : -1}},
               { $limit : 10 }
            ]
});


function insertAdData2(num) {
	db.testOne.insert({
		name : {
			first : "firstname" + num,
			last : "lastname" + num
		},
		title : "advertisement-" + num,
		path : paths[8],
		loc : {
			type : "Point",
			coordinates : [ 170, 30 ]
		},
		tile : [ 10, 20],
		startDate : getRandomDate(new Date(2012, 0, 1), new Date()),
		catalog : catalogList[Math.floor(Math.random() * catalogList.length)],
		price : Math.floor(Math.random() * 100 + 1) * 100,
		comments : JSON.parse(getRandomComments(5))
	});
}


function insertTestData(num, type) {
	if(type== null)
		return; 
	
	var _type = type.toString(2);
	var charArr = _type.split('');
	for (var i = 1; i < num + 1; i++) {
		if (charArr[0])
			insertDealData(i);
		if (charArr[1])
			insertTheaterData(i);
		if (charArr[2])
			insertAdData(i);

	}
}


function insert1(num) {	
	for (var i = 1; i < num + 1; i++) {
			insertDealData(i);
	}
}

function insert2(num) {	
	for (var i = 1; i < num + 1; i++) {
			insertTheaterData(i);
	}
}

function insert3(num) {	
	for (var i = 1; i < num + 1; i++) {
			insertAdData2(i);
	}
}

function insert4(num) {	
	for (var i = 1; i < num + 1; i++) {
		insertParkingLots(i);
	}
}

db.geoLocation.ensureIndex({
	"loc" : "2dsphere"
});

db.testOne.ensureIndex({
	"tile" : "2d"
}, {
	"min" : 0,
	"max" : 100
});


map = function() {
	for(var key in this){
		emit(key, {count: 1});
	}
};

reduce = function(key, emits){
	total = 0;
	for(var i in emits){
		total += emits[i].count;
	}
	return {"count" : total};
};

window.onload = function() {

	// for(var i=0; i<50; i++){
	// document.getElementById("debug1").innerHTML =
	// document.getElementById("debug1").innerHTML + getRandomDate(new
	// Date(2012, 0, 1), new Date()) +"<br/>";
	// }

	// document.getElementById("debug1").innerHTML = getRandomInRange(30, 40,
	// 5);
	// document.getElementById("debug2").innerHTML = getRandomInRange(170, 180,
	// 5);

	// function getLocation() {
	// if (navigator.geolocation) {
	// navigator.geolocation.getCurrentPosition(showPosition);
	// } else {
	// x.innerHTML = "Geolocation is not supported by this browser.";
	// }
	// }
	//
	// function showPosition(position) {
	// x.innerHTML="Latitude: " + position.coords.latitude +
	// "<br>Longitude: " + position.coords.longitude;
	// }
	 
	var num = 2;
	var str = "";
	
	var start = getRandomDate(new Date(2013, 0, 1), new Date());
	var end = getRandomDate(start, new Date(2015, 5, 1));
			
	
	for(var i =1; i<num; i++){
		str += JSON.stringify({

			
			id : "deal_id-" + num,
			title : "Deal Title-" + num,
			startTime : start,
			endTime : end,
			image : "deal_image-" + num,
			website : "deal_website-" + num,
			catagries : JSON.parse(getRandomCategories()),
			path : paths[Math.floor(Math.random() * paths.length)],
			loc : {
				type : "Point",
				coordinates : [ getRandomInRange(170, 179, 5),
						getRandomInRange(30, 40, 5) ]
			},
			tile : [ getRandomInRange(10, 20, 0), getRandomInRange(10, 20, 0) ]
			
			
		});
	}
		
		
	document.getElementById("debug1").innerHTML = str;

};
