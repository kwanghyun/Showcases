
(function() {
window.localStorage.setItem("test_iec_storage", "true");

var keys = [];
var i, key;
for (i = 0; true; ++i) {
  key = window.localStorage.key(i);
  if (key == null)
    break;
  keys.push(key);
}

console.log(keys);

if(keys.indexOf("test_iec_storage") > -1 &&
    window.localStorage.getItem(keys[keys.indexOf("test_iec_storage")]) === "true") {
  console.log("basic local storage working in IEC");window.localStorage.removeItem("test_iec_storage");
  keys = [];
  for (i = 0; true; ++i) {
    key = window.localStorage.key(i);
    if (key == null)
      break;
    keys.push(key);
  }

  if(keys.indexOf("test_iec_storage") === -1)
    console.log("Local storage working in IEC.");
}
})();

// "use strict";

// var cmdFailPercentage = 20;

// var createStorage = function(max){
// 	console.log("Creating - item count :: " + max);

// 	localStorage.clear();

// 	for(var i = 1; i <= max; i ++){
// 		localStorage.setItem("id-" + i, 1);	
// 	}
// };

// var doTest = function(max, iteration, interval, start) {
	
// 	if(start == iteration)
// 		return;

// 	console.log("Increasing local storage values - Iteration No. " + iteration);
	
// 	for(var i = 1; i <= max; i++){
// 		var id = "id-" + i;
// 		var value = localStorage.getItem(id);
// 		// console.log("id :: [" + id + "] value :: [" + value + "]");
// 		localStorage.setItem(id, parseInt(value) + 1);
// 		// localStorage.setItem(id, value++);
// 	}

// 	// console.log("Checking local storage consistency");
// 	var firstItemValue = localStorage.getItem("id-1")
// 	// console.log("firstItemValue :: " + firstItemValue);

// 	for(var i = 1; i <= max; i++){
// 		var id = "id-" + i;
// 		var value = localStorage.getItem(id);
// 		console.log("id => " + id + ", value : " + value);
		
// 		if( firstItemValue != value){
// 			console.log("################################################")
// 			console.log("### FOUND CONCISTENCY ISSUE #### :: id :: " + id);
// 			console.log("################################################")
// 		}
// 	}
	
//     setTimeout(function () {
//         doTest(max, iteration, interval, start + 1);
//     }, interval);
// };

// var startTest = function(item_count, iteration, interval){

// 	if (typeof(Storage) != "undefined") {

// 		createStorage(item_count);	
// 		console.log("iteration-" + iteration);
// 		doTest(item_count, iteration, interval, 1);
	
// 	} else {
// 	    document.getElementById("result").innerHTML = "Sorry, your browser does not support Web Storage...";
// 	}
// }

// var getLocalStrageValues = function(max){
// 	for(var i = 1; i <= max; i++){
// 		var id = "id-" + i;
// 		var value = localStorage.getItem(id);
// 		console.log("[INIT] " + id + " :: " + value);
// 	}
// }


// $(document).ready(function(){
// 	getLocalStrageValues(5);
// 	$("#runTest").click(function(){
//     	//Item count, iteration, interval)
//     	startTest(5, 10, 5000);
//     });
// });
