
Lockers = new Mongo.Collection('lockers');
Orders = new Mongo.Collection('orders');
if (Meteor.isClient) {
  // This code only runs on the client
  angular.module('testapp',['angular-meteor']);
  EventBus = new Vertx.EventBus("http://10.106.8.162:8080/eventbus");
  /*
  EventBus.onopen = function() {
      EventBus.registerHandler("channel.sl.edge.events", function(error, msg) {
          if(error === null) console.log(msg.body);console.log(msg);
      })
  }
  EventBus.send('channel.sl.edge.events',"test", function(error, reply) {
      if(error === null) console.log(reply.body);
  });
*/
  angular.module('testapp').controller('testappCtrl', ['$scope', '$meteor',
  function ($scope, $meteor)
  {
       $scope.lockers = $meteor.collection( function() {
        return Lockers.find({}, { sort: { createdAt: -1 } })
      });
       $scope.orders = $meteor.collection( function() {
        return Orders.find({}, { sort: { createdAt: -1 } })
      });
      $scope.customerDeposit = function (order) {
        
      };
      $scope.courierDeposit = function (order) {
        
      };
      $scope.customerRetrieval = function (order) {
        
      };
      $scope.courierRetrieval = function (order) {
        
      };
      $scope.addLocker = function () {
        $scope.lockers.push( {
          site: $scope.locker_site,
          bank: $scope.locker_bank,
          logicalid: $scope.locker_logicalid,
          packageid: $scope.locker_packageid,
          lockersize: $scope.locker_lockersize,
          depositreleasecode: $scope.locker_depositreleasecode,
          pickupreleasecode: $scope.locker_pickupreleasecode,
          expdepositdate: $scope.locker_expecteddepositdate,
          packagedepositdate: $scope.locker_packagedepositdate,
          packageexpirydate: $scope.locker_packageexpirydate,
          packagestatus: $scope.locker_packagestatus 
        });
        document.getElementById("new-locker").reset();
      };
      $scope.addOrder=function()
      {
        $scope.orders.push({
          site: $scope.order_site,
          bank: $scope.order_bank,
          packageid: $scope.order_packageid,
          lockersize: $scope.order_lockersize,
          depositreleasecode: $scope.order_depositreleasecode,
          pickupreleasecode: $scope.order_pickupreleasecode,
          expdepositdate: $scope.order_expecteddepositdate,
          ordertype: $scope.order_packagestatus
        });
        document.getElementById("new-order").reset();
      };

  }]);
}

if (Meteor.isServer) {
  Meteor.startup(function () {
    process.env.MONGO_URL = 'mongodb://10.106.8.162:27017/demo';
  });
}
