var net = require('net');

var PORT = 6778;

function writeData(out) {
	return function(data) {
		out.write(data);
	}
}

function initialize(socket) {
	socket.on('data', writeData(process.stdout));
	process.openStdin().addListener('data', writeData(socket));
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