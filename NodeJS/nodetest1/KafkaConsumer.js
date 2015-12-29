"use strict";

var kafka = require('kafka-node'),
    Consumer = kafka.Consumer,
    client = new kafka.Client("10.106.9.157:2181"),
    consumer = new Consumer(
        client,
        [
            { topic: 'sl_audit_topic'}
        ],
        {
        	groupId: 'audit-group',//consumer group id, deafult `kafka-node-group` 
            autoCommit: true
        }
    );

consumer.on('message', function (message) {
	console.log("Received Message from Kafka");
    console.log(message);
    console.log("   ");
});