
$(document).ready(function(){
	
	var successCount = 0;
	var failCount = 0;
	var PREFIX = 'eh_';
	var TEST_TYPE = 'eventHanlderTest';
	var TEST_DATA_LIST = new Array();
	var testIndex = 1;
	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * TEST DATA SET UP START
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	function setTest(index){
		/* * * * * * * * * * * * * * * * 
		 * CASE 1 : Normal 
		 * * * * * * * * * * * * * * * */
		
		var inputArr = ['#EB011000.'];
		var expectedArr = [JSON.stringify({
			"logicalId":"locker1",
			"physicalId":"011",
			"doorState":"CLOSE",
			"occupancyStatus":"VACANT",
			"alarmState":"OFF"})];
		addToTestData(testIndex, inputArr, expectedArr);


	};
	
	function addToTestData(testIndex, inputArr, expectedArr){
		testIndex++;
		TEST_DATA_LIST.push({
			type : PREFIX + testIndex,
			input : inputArr,
			expected : expectedArr
		});
	}
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * TEST DATA SET UP END
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	$("#runTest").click(function(){
    	doTestAll();
    });



	function doTestAll() {
		 
		setTest();
				 
		for ( var index in TEST_DATA_LIST) {
			createTable(index);
			doTest(index , TEST_TYPE);
		}
					
		document.getElementById("successCount").innerHTML = successCount;
		document.getElementById("failCount").innerHTML = failCount;
	}
	
    
	function doTest(index, testType) {
		
		var outputData = new Array();

		switch (testType){

		case TEST_TYPE:
			
			console.log("============ START OF TEST [" + TEST_DATA_LIST[index].type + "]============");
			var inputs = TEST_DATA_LIST[index].input;
			for(var idx in inputs){
				
				
				console.log("[doTest()]::[" + TEST_DATA_LIST[index].type + "]result ::: " + result );
		
				outputData.push(result);
				console.log("[doTest()]::[" + TEST_DATA_LIST[index].type + "]outputData ::: " + outputData.toString() );
			}
			console.log("  ");

		default :
			throw "NO TEST TYPE DEFINED"
		} 
		
		processTestResult(index);
	}

	function processTestResult(index){
		document.getElementById(PREFIX +'output' + index).innerHTML = outputData;
		
		var expected = document.getElementById(PREFIX + 'expected' + index).innerHTML;

		if(outputData == expected){
			document.getElementById(PREFIX + 'result' + index).innerHTML = "TRUE";
			successCount++
		}
		else{
			var div = document.getElementById(PREFIX + 'result' + index);
			div.innerHTML = "FALSE";
			div.style.backgroundColor = 'RED';
			failCount ++;
		}		
	}

	function createTable(index){
		var str = '<tr><td>'+ TEST_DATA_LIST[index].type +'</td>';
		str += '<td ><div id=' + PREFIX + '"input'+index+'" style="width: 250px; word-wrap:break-word;">'+TEST_DATA_LIST[index].input+'</div></td>';		
		str += '<td><div id="' + PREFIX + 'expected'+index+'" style="width: 250px; word-wrap:break-word;" >'+TEST_DATA_LIST[index].expected+'</div></td>';
		str += '<td ><div id="' + PREFIX + 'output'+index+'" style="width: 250px; word-wrap:break-word;"></div></td>';
		str += '<td><div id="' + PREFIX + 'result'+index+'"></div></td></tr>'; 
		$('#testTable tr:last').after(str);
	};

});
