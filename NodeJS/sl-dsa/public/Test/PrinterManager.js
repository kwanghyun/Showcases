"use strict";

var glb_serialPrinter;
var glb_serialPrinterListener;
var glb_serialPrinterConnection;

var PrinterMessageBuilder = (function($) {
	var command = "";

	var PAGE_CUT = "1B69";
	var LINE_FEED_1 = "0A";
	var LINE_FEED_2 = "0A0A";
	var LINE_FEED_6 = "0A0A0A0A0A0A";
	var CR = "0D";

	function createInstance(){
		console.log("createInstance() :: this => " + command);
		return this;
	};
	
	function appendMessage(cmd){

		for (var i = 0; i < cmd.length; ++i){
			command = command + convert(cmd[i]);
		}
		command = command + CR;
		console.log("appendCommand() :: command => " + command);
		return this;
	};

	function addLineFeed(){
		// command = command + LINE_FEED_1;
		command = command + CR;
		console.log("addLineFeed() :: command => " + command);
		return this;
	}

	function build(){
		var head = LINE_FEED_1;
		var returnCommand = head + command + LINE_FEED_6 + PAGE_CUT;
		console.log("build() :: command => " + command);
		command = "";
		return returnCommand;
	};

	function convert(char){
		switch(char){
			case " ":
				return "20";
			case "!":
				return "21";
			case "#":
				return "23";
			case "$":
				return "24";
			case "%":
				return "25";
			case "&":
				return "26";
			case "(":
				return "28";
			case ")":
				return "29";
			case "*":
				return "2A";
			case "+":
				return "2B";
			case ",":
				return "2C";
			case "-":
				return "2D";
			case ".":
				return "2E";
			case "/":
				return "2F";
			case "0":
				return "30";
			case "1":
				return "31";
			case "2":
				return "32";
			case "3":
				return "33";
			case "4":
				return "34";
			case "5":
				return "35";
			case "6":
				return "36";
			case "7":
				return "37";
			case "8":
				return "38";
			case "9":
				return "39";
			case ":":
				return "3A";
			case ";":
				return "3B";
			case "@":
				return "40";
			case "A":
				return "41";
			case "B":
				return "42";
			case "C":
				return "43";
			case "D":
				return "44";
			case "E":
				return "45";
			case "F":
				return "46";
			case "G":
				return "47";
			case "H":
				return "48";
			case "I":
				return "49";
			case "J":
				return "4A";
			case "K":
				return "4B";
			case "L":
				return "4C";
			case "M":
				return "4D";
			case "N":
				return "4E";
			case "O":
				return "4F";
			case "P":
				return "50";
			case "Q":
				return "51";
			case "R":
				return "52";
			case "S":
				return "53";
			case "T":
				return "54";
			case "U":
				return "55";
			case "V":
				return "56";
			case "W":
				return "57";
			case "X":
				return "58";
			case "Y":
				return "59";
			case "Z":
				return "5A";
			case "[":
				return "5B";
			case "]":
				return "5D";
			case "a":
				return "61";
			case "b":
				return "62";
			case "c":
				return "63";
			case "d":
				return "64";
			case "e":
				return "65";
			case "f":
				return "66";
			case "g":
				return "67";
			case "h":
				return "68";
			case "i":
				return "69";
			case "j":
				return "6A";
			case "k":
				return "6B";
			case "l":
				return "6C";
			case "m":
				return "6D";
			case "n":
				return "6E";
			case "o":
				return "6F";
			case "p":
				return "70";
			case "q":
				return "71";
			case "r":
				return "72";
			case "s":
				return "73";
			case "t":
				return "74";
			case "u":
				return "75";
			case "v":
				return "76";
			case "w":
				return "77";
			case "x":
				return "78";
			case "y":
				return "79";
			case "z":
				return "7A";
			case "{":
				return "7B";
			case "}":
				return "7D";

			default :
				console.log("convert() NOT-EXISTING CHAR : " + char);
				return "";
		}
	}

	return {
		createInstance: createInstance,
		appendMessage: appendMessage,
		addLineFeed: addLineFeed,
		build: build
	};
})(jQuery);


var PrinterManager = (function($) {
	var deviceName = "";
	var baudRate = 115200;
	var dataBits = 8;
	var flowControl = 0;
	var parity = 0;
	var stopBits = 1;
	var command = "";


	function initialize() {
		try{
			global.serialPorts.deleteAll();
			var devices = global.serialPorts.availableDevices;
			var rs232connections = [];
			
		    for (var i = 0; i < devices.length; ++i)
		    {		    	
		        if(devices[i].length > 3 && devices[i].substring(0,3) === "USB"){
		        	console.log("device :: [" + i + "] - " + devices[i] );
		        	rs232connections.push(devices[i]);
		        }		        	
		    }

		    deviceName = rs232connections[0];
			console.log("deviceName :: " + deviceName);

			console.log("global.serialPorts.contains :: " + global.serialPorts.contains(deviceName));
			var alreadyExists = global.serialPorts.contains(deviceName);

			console.log("baudRate:: " + baudRate);
			console.log("dataBits:: " + dataBits);
			console.log("flowControl:: " + flowControl);
			console.log("parity:: " + parity);
			console.log("stopBits:: " + stopBits);
			console.log("baudRate:: " + baudRate);
			glb_serialPrinter = global.serialPorts.port(deviceName);
			console.log("### glb_serialPrinter :: " + glb_serialPrinter);
			glb_serialPrinter.baudRate = baudRate;
			glb_serialPrinter.dataBits = dataBits;
			glb_serialPrinter.flowControl = flowControl;
			glb_serialPrinter.parity = parity;
			glb_serialPrinter.stopBits = stopBits;
			
			
			if (!alreadyExists){
				glb_serialPrinter.readyRead.connect(eventHandler);
			}
		}catch(e){
			console.log("Exception Can't not connect to the IEC Printer");
			console.log(e);
		}

		glb_serialPrinterListener = eventHandler;
		glb_serialPrinterConnection = this;

	};


	function print(cmd){
		// str = str + "0A";
		sendSerialCommand(cmd);
	};

	function cutPage(){
		sendSerialCommand(PAGE_CUT);
	};

	function sendSerialCommand(cmd){
		console.log("[sendSerialCommand()]::command => " + cmd);
		try{
			glb_serialPrinter.write(hex2a(cmd));	
		}catch(e){
			console.log(e);
			throw "Can't send a serial command! Please check the Serial Connection"
		}
	};

	function convertStringToCommand(str){
		var command = '';
		// str = str + this.getBCC(str);
		console.log("[convertStringToCommand() :: str] : " + str);
		
		for (var i = 0; i < str.length; i++) {
			var hex = str.charCodeAt(i).toString(16);
			command += str.charCodeAt(i).toString(16);
		}
		console.log("[convertStringToCommand()] : " + command);

		return command;
	};

	function hex2a(hex){
		var str = '';
		for (var i = 0; i < hex.length; i += 2)
			str += String.fromCharCode(parseInt(hex.substr(i, 2), 16));
		return str;
	};

	function eventHandler(){
		// var response = glb_serialPrinter.readAll();
		var response = "DUMMY";
		console.log("[eventHandler()]::::response::::"+ response);
		jQuery(document).trigger("printerResponse", response);

		return;
	};

	return {
		init: initialize,
		print: print
	};
})(jQuery);

