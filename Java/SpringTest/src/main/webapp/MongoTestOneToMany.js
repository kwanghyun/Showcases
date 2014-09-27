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

var dealIds = new Array();
var theaterIds = new Array();
var adIds = new Array();

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

for (var i = 1; i < 101; i++) {
	dealIds.push("item_id-" + i);
	theaterIds.push("theater_id-" + i);
	adIds.push("ad_id-" + i);
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

function getRandomTheaters(num) {
	var randomIteration = Math.floor(Math.random() * num);

	var str = "[";
	for (var i = randomIteration; i > 0; i--) {
		var theaterRandomNum = [ Math.floor(Math.random() * dealIds.length) ];
		var start = getRandomDate(new Date(2013, 0, 1), new Date());
		var end = getRandomDate(start, new Date(2015, 5, 1));

		str += '{ "id" : "theater_id-' + theaterRandomNum + '" , ';
		str += ' "name" : "theater_name-' + theaterRandomNum + '" , ';
		str += ' "phoneNumber" : ' + JSON.stringify(start) + ' , ';
		str += ' "movies" : ' + getRandomMovies(10) + ' },';

	}
	if (randomIteration > 0)
		str = str.substring(0, str.length - 1);
	str += ']';
	return str;
}

function getRadomDeals(num) {
	var randomIteration = Math.floor(Math.random() * num);

	var str = "[";
	for (var i = randomIteration; i > 0; i--) {
		var dealRandomNum = [ Math.floor(Math.random() * dealIds.length) ];
		var start = getRandomDate(new Date(2013, 0, 1), new Date());
		var end = getRandomDate(start, new Date(2015, 5, 1));

		str += '{ "id" : "deal_id-' + dealRandomNum + '" , ';
		str += ' "title" : "Deal Title-' + dealRandomNum + '" , ';
		str += ' "startTime" : new Date(' + JSON.stringify(start) + ') , ';
		str += ' "endTime" : ' + JSON.stringify(end) + ' , ';
		str += ' "image" : "deal_image' + dealRandomNum + '" , ';
		str += ' "website" : "deal_website' + dealRandomNum + '" , ';
		str += ' "catagries" : ' + getRandomCategories() + ' },';
	}
	if (randomIteration > 0)
		str = str.substring(0, str.length - 1);
	str += ']';
	return str;
}


function getRadomAds4Update(num) {
	
	randomIteration = num;
	for (var i = randomIteration; i > 0; i--) {
		var str = "";
		var adRandomNum = [ Math.floor(Math.random() * adIds.length) ];
		var start = getRandomDate(new Date(2013, 0, 1), new Date());
		var end = getRandomDate(start, new Date(2015, 5, 1));

		str += '{ "id" : "ad_id-' + adRandomNum + '" , ';
		str += ' "name" : "ad_name-' + adRandomNum + '" , ';
		str += ' "startTime" : ' + JSON.stringify(getRandomDate(new Date(2012, 0, 1), new Date())) + ' , ';
		str += ' "catalog" : "' + catalogList[Math.floor(Math.random() * catalogList.length)] + '" , ';
		str += ' "price" : ' + Math.floor(Math.random() * 100 + 1) * 100 + ' , ';
		str += ' "comments" : ' + getRandomComments(5) + ' },';

	}
	if (randomIteration > 0)
		str = str.substring(0, str.length - 1);
	return str;
}


function getRadomAds(num) {
	
	var randomIteration = Math.floor(Math.random() * num);
	var str = "[";
	for (var i = randomIteration; i > 0; i--) {
		
		var adRandomNum = [ Math.floor(Math.random() * adIds.length) ];
		var start = getRandomDate(new Date(2013, 0, 1), new Date());
		var end = getRandomDate(start, new Date(2015, 5, 1));

		str += '{ "id" : "ad_id-' + adRandomNum + '" , ';
		str += ' "name" : "ad_name-' + adRandomNum + '" , ';
		str += ' "startTime" : ' + JSON.stringify(getRandomDate(new Date(2012, 0, 1), new Date())) + ' , ';
		str += ' "catalog" : "' + catalogList[Math.floor(Math.random() * catalogList.length)] + '" , ';
		str += ' "price" : ' + Math.floor(Math.random() * 100 + 1) * 100 + ' , ';
		str += ' "comments" : ' + getRandomComments(5) + ' },';

	}
	if (randomIteration > 0)
		str = str.substring(0, str.length - 1);
	str += ']';
	return str;
}


function insertTheaterData(num) {
	db.test.insert({
		items : JSON.parse(getRandomTheaters(10)),
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

	db.test.insert({
		items : JSON.parse(getRadomDeals(10)),
		path : paths[Math.floor(Math.random() * paths.length)],
		loc : {
			type : "Point",
			coordinates : [ getRandomInRange(170, 179, 5),
					getRandomInRange(30, 40, 5) ]
		},
		tile : [ getRandomInRange(10, 20, 0), getRandomInRange(10, 20, 0) ]
	});
}


function insertAdData(num) {
	db.test.insert({
		items : JSON.parse(getRadomAds(10)),
		path : paths[Math.floor(Math.random() * paths.length)],
		loc : {
			type : "Point",
			coordinates : [ getRandomInRange(170, 179, 5),
					getRandomInRange(30, 40, 5) ]
		},
		tile : [ getRandomInRange(10, 20, 0), getRandomInRange(10, 20, 0) ]
	});
}

function insertTestData(num, type) {
	if (type == null)
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
			insertAdData(i);
	}
}


function insertAdData2(num) {
	db.testMany.insert({
		items : [],
		path : paths[8],
		loc : {
			type : "Point",
			coordinates : [170, 30]
		},
		tile : [ 10, 20 ]
	});
}

db.testMany.ensureIndex({
	"loc" : "2dsphere"
});
db.testMany.ensureIndex({
	"tile" : "2d"
}, {
	"min" : 0,
	"max" : 100
});

insertAdData2(1);
db.testMany.find().pretty();


for(var i=0; i<9999800; i++){
	db.testMany.update(
			   { path:  "/US/California/San Jose/Building J/floor2" },
			   {
				      $push: { items: JSON.parse(getRadomAds4Update(1))}
			   }
		);	
}
	
db.testMany.find().pretty();


db.testMany.remove(
		   { path: "/US/California/San Jose/Building J/floor2" }
	);

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

	var num = 1;
	var str = "";

	var start = getRandomDate(new Date(2013, 0, 1), new Date());
	var end = getRandomDate(start, new Date(2015, 5, 1));

	for (var i = 0; i < num; i++) {
		str += JSON.stringify({

			
			items : JSON.parse(getRadomDeals(10)),
			path : paths[Math.floor(Math.random() * paths.length)],
			loc : {
				type : "Point",
				coordinates : [ getRandomInRange(170, 179, 5),
						getRandomInRange(30, 40, 5) ]
			},
			tile : [ getRandomInRange(10, 20, 0), getRandomInRange(10, 20, 0) ]
			

		});
	}

	document.getElementById("debug1").innerHTML =str;

};
