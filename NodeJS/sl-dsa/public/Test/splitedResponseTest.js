
$(document).ready(function(){
	
	var successCount = 0;
	var failCount = 0;

	var RS_SPLIT_TEST_DATA = new Array();
	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * TEST DATA SET UP START
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	function setSplittedResponseTestData(index){
		/* * * * * * * * * * * * * * * * 
		 * CASE 1 : Normal 
		 * * * * * * * * * * * * * * * */
		var testIndex = 1;
		eval("var inputArr" + testIndex + " = ['#EB011000.']");
		eval("var outputArr" + testIndex + " = ['#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});

		/* * * * * * * * * * * * * * * * 
		 * CASE 2 : Abnormal - Partial Response at the end.
		 * * * * * * * * * * * * * * * */		
		testIndex++;
		eval("var inputArr" + testIndex + " = ['#EB011000.#EB','011000.']");
		eval("var outputArr" + testIndex + " = ['#EB011000','#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});

		/* * * * * * * * * * * * * * * * 
		 * CASE 3 : Abnormal - Partial Response at the begining.
		 * [,value,value] -> First response should be null since it's not completed response
		 * * * * * * * * * * * * * * * */		
		testIndex++;
		eval("var inputArr" + testIndex + " = ['#EB011','000.#EB011000.']");
		eval("var outputArr" + testIndex + " = [,'#EB011000','#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});

		/* * * * * * * * * * * * * * * * 
		 * CASE 4 : Abnormal - Complete 2 Responses  at once.
		 * * * * * * * * * * * * * * * */		
		testIndex++;
		eval("var inputArr" + testIndex + " = ['#EB011000.','#EB011000.']");
		eval("var outputArr" + testIndex + " = ['#EB011000','#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});
		
		/* * * * * * * * * * * * * * * * 
		 * CASE 5 : Abnormal - Partial Response at the begining and the end.
		 * * * * * * * * * * * * * * * */		
		testIndex++;
		eval("var inputArr" + testIndex + " = ['#EB0','11000.#EB01','1000.']");
		eval("var outputArr" + testIndex + " = [,'#EB011000','#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});
		
		/* * * * * * * * * * * * * * * * 
		 * CASE 6 : Abnormal - Multiple Partial Responses.
		 * * * * * * * * * * * * * * * */		
		testIndex++;
		eval("var inputArr" + testIndex + " = ['#EB0','110','00.#EB01','1000.']");
		eval("var outputArr" + testIndex + " = [,,'#EB011000','#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});
		
		/* * * * * * * * * * * * * * * * 
		 * CASE 7 : Abnormal - Multiple Partial Responses.
		 * * * * * * * * * * * * * * * */		
		testIndex++;
		eval("var inputArr" + testIndex + " = ['#E','B0','110','00.#EB01','1000.']");
		eval("var outputArr" + testIndex + " = [,,,'#EB011000','#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});
		
		/* * * * * * * * * * * * * * * * 
		 * CASE 8 : Abnormal - Multiple Partial 3 Responses.
		 * * * * * * * * * * * * * * * */		
		testIndex++;
		eval("var inputArr" + testIndex + " = ['#EB0110','00.#EB01','1000.#EB','011000.']");
		eval("var outputArr" + testIndex + " = [,'#EB011000','#EB011000','#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});
		
		/* * * * * * * * * * * * * * * * 
		 * CASE 9 : Abnormal - Multiple Partial 3 Responses.
		 * * * * * * * * * * * * * * * */		
		testIndex++;
		eval("var inputArr" + testIndex + " =  ['#OP011.','#EB011000.','#EB011000.']");
		eval("var outputArr" + testIndex + " = ['#OP011','#EB011000','#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});
		
		/* * * * * * * * * * * * * * * * 
		 * CASE 10 : Abnormal - Multiple responses in a single response.
		 * * * * * * * * * * * * * * * */		
		testIndex++;
		eval("var inputArr" + testIndex + " =  ['#OP011CC.#EB011000.#EB011000.']");
		eval("var outputArr" + testIndex + " = ['#OP011CC','#EB011000','#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});


		
		/* * * * * * * * * * * * * * * * 
		 * CASE 11 : Abnormal - Multiple responses in a single response and additional single response.
		 * * * * * * * * * * * * * * * */		
		testIndex++;
		eval("var inputArr" + testIndex + " =  ['#OP011CC.#EB011000.#EB011000.','#EB011000.']");
		eval("var outputArr" + testIndex + " = ['#OP011CC','#EB011000','#EB011000','#EB011000']");

		RS_SPLIT_TEST_DATA.push({
			type : 'RSS_' + testIndex,
			input : eval("inputArr" + testIndex),
			expected :eval("outputArr" + testIndex)
		});


	};
	
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * TEST DATA SET UP END
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	$("#runTest").click(function(){
    	doTestAll();
    });



	function doTestAll() {
		 
		setSplittedResponseTestData();
				 
		for ( var index in RS_SPLIT_TEST_DATA) {
			createSplittedResponseTableRows(index);
			doTest(index , "splittedResponse");
		}
		
			
		document.getElementById("successCount").innerHTML = successCount;
		document.getElementById("failCount").innerHTML = failCount;
	}
	
    

	function doTest(index, testType) {
		var convertedStr = '';
		var prefix = '';
		var outputData = new Array();

		switch (testType){

		case "splittedResponse":
			prefix = 'rss_';
			var s = new SerialConnection();
			
			var arrs = RS_SPLIT_TEST_DATA[index].input;
			for(var idx in arrs){
				
				var result = s.serialPort_filterData(arrs[idx]);
				console.log("#######[" + RS_SPLIT_TEST_DATA[index].type + "]result ::: " + result );
		
				outputData.push(result);
				console.log("#######[" + RS_SPLIT_TEST_DATA[index].type + "]outputData ::: " + outputData.toString() );
			}
			console.log("#################END OF TEST#################");
			break;
		default :
			throw "NO TEST TYPE DEFINED"
		} 
		
		document.getElementById(prefix +'output' + index).innerHTML = outputData;
		
		var expected = document.getElementById(prefix + 'expected' + index).innerHTML;

		if(outputData == expected){
			document.getElementById(prefix + 'result' + index).innerHTML = "TRUE";
			successCount++
		}
		else{
			var div = document.getElementById(prefix + 'result' + index);
			div.innerHTML = "FALSE";
			div.style.backgroundColor = 'RED';
			failCount ++;
		}		
	}

	function createSplittedResponseTableRows(index){
		var str = '<tr><td>'+ RS_SPLIT_TEST_DATA[index].type +'</td>';
		str += '<td ><div id="rss_input'+index+'" style="width: 250px; word-wrap:break-word;">'+RS_SPLIT_TEST_DATA[index].input+'</div></td>';
		str += '<td ><div id="rss_output'+index+'" style="width: 250px; word-wrap:break-word;"></div></td>';
		str += '<td><div id="rss_expected'+index+'" style="width: 250px; word-wrap:break-word;" >'+RS_SPLIT_TEST_DATA[index].expected+'</div></td>';
		str += '<td><div id="rss_result'+index+'"></div></td></tr>'; 
		$('#splittedResponseTable tr:last').after(str);
	};

});
