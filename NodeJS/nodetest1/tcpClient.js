var net = require('net');

var client = new net.Socket();
client.connect(5170, '10.106.8.16', function() {
	console.log('Connected');
	try{
		client.write('THIS IS DEMO DATA' + '\n');
	}catch(err){
		console.log(err);
	}
		
});

client.on('data', function(data) {
	console.log('Received: ' + data);
	client.destroy(); // kill client after server's response
});

client.on('close', function() {
	console.log('Connection closed');
});
