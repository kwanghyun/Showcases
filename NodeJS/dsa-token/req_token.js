  var DS = require('./dslink.node.js');

  var args = ['-b', '10.203.52.22:8080/conn', '--log', 'finest', '--token', '4wjdWfFCEeui5YYiIcocGraTVURaTasGbr0d9gJbtKwLcjxD'];

  link = new DS.LinkProvider(args, "token-req", {isRequester: true});
  link.connect();
  link.onRequesterReady.then(function(requester){
  	requester.invoke('/sys/tokens/root/add',{}, 4, function (update){
  		console.log(update.updates);
  	})
  });
