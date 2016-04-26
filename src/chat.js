var net = require('net');

var PORT = 6778;

function initialize(socket) {
	socket.on('data', function(data) {
		process.stdout.write(data);	
	});
	process.openStdin().addListener('data', function(data) {
		socket.write(data);
	});
}

if (typeof process.argv[2] === 'string') {
	var socket = new net.Socket();
	socket.connect(PORT, process.argv[2])
	initialize(socket);
} else {
	var server = net.createServer(function(socket) {
		initialize(socket);
	});
	server.listen(PORT);
}