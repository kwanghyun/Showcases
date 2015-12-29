var prompt = require('prompt');
var redis = require("redis"),
    publisher = redis.createClient(6379, '10.106.9.143'),
    subscriber = redis.createClient(6379, '10.106.9.143'),
    msg_count = 0;

var channel_name = "op_channel";

// Start the prompt

prompt.start();

prompt.get(['command'], function (err, result) {
  console.log('Command-line input received:');
  console.log('  Command: ' + result.command);
  publisher.publish(channel_name, result.command);
});

subscriber.psubscribe("workerList");

subscriber.on("psubscribe", function (channel, count) {
    console.log("psubscribe done.........");
});

subscriber.on("pmessage", function (channel, message) {
    console.log("pmessage => " + channel + ": " + message);
});