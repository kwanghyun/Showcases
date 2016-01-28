
$(document).ready(function(){
	
	var successCount = 0;
	var failCount = 0;
	var PREFIX = 'eh_';
	var TEST_TYPE = 'eventHanlderTest';
	var TEST_DATA_LIST = new Array();
	var lockerManager = new PanasonicLockerManager({isSensorEnabled:true}, getLockerList());
		
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * TEST DATA SET UP START
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	function setTest(index){


		/* * * * * * * * * * * * * * * * 
		 * 011 : CLOSE 
		 * * * * * * * * * * * * * * * */
		var testIndex = 1;
		var inputArr = ['#EB011000'];
		var expectedArr = [JSON.stringify({
			"logicalId":"locker1",
			"physicalId":"011",
			"doorState":"CLOSE",
			"occupancyStatus":"VACANT",
			"alarmState":"OFF"})];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : OPEN
		 * * * * * * * * * * * * * * * */
		var testIndex = 2;
		var inputArr = ['#EB011100'];
		var expectedArr = [JSON.stringify({
			"logicalId":"locker1",
			"physicalId":"011",
			"doorState":"OPEN",
			"occupancyStatus":"VACANT",
			"alarmState":"OFF"})];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : OPEN => Ignore event
		 * * * * * * * * * * * * * * * */
		var testIndex = 3;
		var inputArr = ['#EB011100'];
		var expectedArr = [''];
		addToTestData(testIndex, inputArr, expectedArr);


		/* * * * * * * * * * * * * * * * 
		 * 012 : OPEN
		 * * * * * * * * * * * * * * * */
		var testIndex = 4;
		var inputArr = ['#EB012100'];
		var expectedArr = [JSON.stringify({
			"logicalId":"locker2",
			"physicalId":"012",
			"doorState":"OPEN",
			"occupancyStatus":"VACANT",
			"alarmState":"OFF"})];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : OPEN, OCCUPIED => Ignore event
		 * * * * * * * * * * * * * * * */
		var testIndex = 5;
		var inputArr = ['#EB011110'];
		var expectedArr = [''];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : OPEN, VACANT => Ignore event
		 * * * * * * * * * * * * * * * */
		var testIndex = 6;
		var inputArr = ['#EB011100'];
		var expectedArr = [''];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : OPEN, OCCUPIED => Ignore event
		 * * * * * * * * * * * * * * * */
		var testIndex = 7;
		var inputArr = ['#EB011110'];
		var expectedArr = [''];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : CLOSE, OCCUPIED => Ignore event
		 * * * * * * * * * * * * * * * */
		var testIndex = 8;
		var inputArr = ['#EB011010'];
		var expectedArr = [JSON.stringify({
			"logicalId":"locker1",
			"physicalId":"011",
			"doorState":"CLOSE",
			"occupancyStatus":"OCCUPIED",
			"alarmState":"OFF"})];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : OEPN, OCCUPIED => OEPN DOOR FOR PICK UP
		 * * * * * * * * * * * * * * * */
		var testIndex = 9;
		var inputArr = ['#EB011110'];
		var expectedArr = [JSON.stringify({
			"logicalId":"locker1",
			"physicalId":"011",
			"doorState":"OPEN",
			"occupancyStatus":"OCCUPIED",
			"alarmState":"OFF"})];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : OEPN, VACANT => PICK UP THE PACAKGE => Ignore event
		 * * * * * * * * * * * * * * * */
		var testIndex = 10;
		var inputArr = ['#EB011100'];
		var expectedArr = [''];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : OEPN, VACANT => PUT BACK THE PACAKGE => Ignore Event
		 * * * * * * * * * * * * * * * */
		var testIndex = 11;
		var inputArr = ['#EB011110'];
		var expectedArr = [''];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : OEPN, VACANT => CLOSE DOOR
		 * * * * * * * * * * * * * * * */
		var testIndex = 12;
		var inputArr = ['#EB011000'];
		var expectedArr = [JSON.stringify({
			"logicalId":"locker1",
			"physicalId":"011",
			"doorState":"CLOSE",
			"occupancyStatus":"VACANT",
			"alarmState":"OFF"})];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : OPEN DOOR
		 * * * * * * * * * * * * * * * */
		var testIndex = 13;
		var inputArr = ['#EB011100'];
		var expectedArr = [JSON.stringify({
			"logicalId":"locker1",
			"physicalId":"011",
			"doorState":"OPEN",
			"occupancyStatus":"VACANT",
			"alarmState":"OFF"})];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : PUT PACKAGE
		 * * * * * * * * * * * * * * * */
		var testIndex = 14;
		var inputArr = ['#EB011110'];
		var expectedArr = [''];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : TAKE OUT PACKAGE
		 * * * * * * * * * * * * * * * */
		var testIndex = 15;
		var inputArr = ['#EB011100'];
		var expectedArr = [''];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : PUT PACKAGE BACK
		 * * * * * * * * * * * * * * * */
		var testIndex = 16;
		var inputArr = ['#EB011110'];
		var expectedArr = [''];
		addToTestData(testIndex, inputArr, expectedArr);

		/* * * * * * * * * * * * * * * * 
		 * 011 : CLOSE DOOR
		 * * * * * * * * * * * * * * * */
		var testIndex = 16;
		var inputArr = ['#EB011010'];
		var expectedArr = [JSON.stringify({
			"logicalId":"locker1",
			"physicalId":"011",
			"doorState":"CLOSE",
			"occupancyStatus":"OCCUPIED",
			"alarmState":"OFF"})];
		addToTestData(testIndex, inputArr, expectedArr);

	};

	function addToTestData(testIndex, inputArr, expectedArr){
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

		// lockerManager.getInitialLockerStatus();
	}
	
    
	function doTest(index, testType) {
		
		var outputData = new Array();

		switch (testType){

		case TEST_TYPE:
			console.log("============ START OF TEST [" + TEST_DATA_LIST[index].type + "]============");
			var inputs = TEST_DATA_LIST[index].input;
			for(var idx in inputs){
				
				var result = lockerManager.eventTest(inputs[idx]);
				lockerManager.open(["1"]);
				console.log("[doTest()]::[" + TEST_DATA_LIST[index].type + "]result ::: " + result );
		
				outputData.push(result);
				console.log("[doTest()]::[" + TEST_DATA_LIST[index].type + "]outputData ::: " + outputData.toString() );
			}
			console.log("  ");
			break;

		default :
			throw "NO TEST TYPE DEFINED"
		} 
		
		processTestResult(index, outputData);
	}

	function processTestResult(index, outputData){
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

	function getLockerList(){
		var lockerList = [
			{
				"logicalId" : "1",
				"physicalId" : "011"
			},
			{
				"logicalId" : "2",
				"physicalId" : "012"
			},
			{
				"logicalId" : "3",
				"physicalId" : "013"
			},
			{
				"logicalId" : "4",
				"physicalId" : "014"
			},
			{
				"logicalId" : "5",
				"physicalId" : "015"
			},
			{
				"logicalId" : "6",
				"physicalId" : "016"
			},
			{
				"logicalId" : "7",
				"physicalId" : "021"
			},
			{
				"logicalId" : "8",
				"physicalId" : "022"
			},
			{
				"logicalId" : "9",
				"physicalId" : "023"
			},
			{
				"logicalId" : "10",
				"physicalId" : "024"
			},
			{
				"logicalId" : "11",
				"physicalId" : "025"
			},
			{
				"logicalId" : "12",
				"physicalId" : "026"
			},
			{
				"logicalId" : "13",
				"physicalId" : "031"
			},
			{
				"logicalId" : "14",
				"physicalId" : "032"
			},
			{
				"logicalId" : "15",
				"physicalId" : "033"
			}
		];
		return lockerList;
	}

});
