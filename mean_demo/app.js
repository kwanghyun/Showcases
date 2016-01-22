

var app = angular.module('flapperNews', ['ui.router'])

app.factory('posts', [function(){
  var o = {
    posts: []
  };
  return o;
}]);

app.config([
'$stateProvider',
'$urlRouterProvider',
function($stateProvider, $urlRouterProvider) {

  $stateProvider
    .state('home', {
      url: '/home',
      templateUrl: '/home.html',
      controller: 'MainCtrl'
    });

  $urlRouterProvider.otherwise('home');
}]);


app.controller('MainCtrl', [
'$scope',
'posts'
,function($scope, posts){
	// $scope.posts = [
	//   {title: 'post 1', link: 'http://google.com', upvotes: 5},
	//   {title: 'post 2', link: 'http://google.com', upvotes: 2},
	//   {title: 'post 3', link: 'http://google.com', upvotes: 15},
	//   {title: 'post 4', link: 'http://google.com', upvotes: 9},
	//   {title: 'post 5', link: 'http://google.com', upvotes: 4}
	// ];

	$scope.test = 'Hello world!';
	$scope.posts = posts.posts;

	$scope.addPost = function(){
	  if(!$scope.title || $scope.title === '') { return; }
	  $scope.posts.push({
	    title: $scope.title,
	    link: $scope.link,
	    upvotes: 0
	  });
	  $scope.title = '';
	  $scope.link = '';
	};

	$scope.incrementUpvotes = function(post) {
	  post.upvotes += 1;
	};
}]);