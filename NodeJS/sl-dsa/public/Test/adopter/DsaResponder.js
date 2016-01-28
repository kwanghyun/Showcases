
var SerialConn = function(){
		device : {},
		deviceName : "USB0",
		baudRate : "115200",
		dataBits : "8",
		flowControl : "2",
		parity : "3",
		stopBits : "1"
		
		setConnection : function(callback) {
			var alreadyExists = global.serialPorts.contains(deviceConfig.deviceName);
			device = global.serialPorts.port(deviceConfig.deviceName);
			device.baudRate = this.baudRate
			device.dataBits = this.dataBits;
			device.flowControl = this.flowControl;
			device.parity = this.parity;
			device.stopBits = this.stopBits;
			
			// TODO Find better place to put
			device.iecId = global.device.serialNumber;
			console.log("Locker S/N ::: " + device.iecId );
			
			if (!alreadyExists)
				device.readyRead.connect(this.dataHanlder(callback));
		}
	
		dataHanlder(callback){
			var data = device.readAll();
			
			var str = "";
			for (var i = 0; i < data.length; ++i) {
				str += data[i] < 32 ? '.' : String.fromCharCode(data[i]);
			}

			console.log("serialportListener(): data from device : "
					+ serialportListener);
		}
	};
