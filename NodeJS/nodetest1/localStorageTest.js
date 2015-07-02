"use strict";

var doTest = function(max, interval) {

	console.log("Store a item.")
	localStorage.setItem("id-" + max, "value-" + max);

	console.log("Checking local storage consistency");
	for(var i = 1; i <= max; i++){
		var id = "id-" + i;
		var value = localStorage.getItem(id);
		console.log("6th char is :: " + value.charAt(6));
		if(value.charAt(6) != i){
			console.log("################################################")
			console.log("############ FOUND CONCISTENCY ISSUE ###########");
			console.log("################################################")
		}
			
	}
	
    setTimeout(function () {
        
        doTest(provider, lockerStateList, interval);
    }, interval);
};

var main = function(){

	if (typeof(Storage) != "undefined") {
	  doTest(1, 1000);  

	} else {
	    document.getElementById("result").innerHTML = "Sorry, your browser does not support Web Storage...";
	}
}

main();