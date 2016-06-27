var express = require('express');
var router = express.Router();
var unirest = require('unirest');

// Set the configuration settings 
var CALLBACK_URL = 'http://localhost:3000/oauth/callback';
// var CALLBACK_URL = 'http://10.106.8.53:3000/oauth/callback';

var credentials = {
  clientID: 'TVTGu9ZvLUWKPl4cK4JZbmlsmsoa',
  clientSecret: '76NPGQzWuaZgbpDufUSVImVKVDAa',
  site: 'https://10.106.8.53:9443',
  authorizationPath: '/oauth2/authorize',
  tokenPath : '/oauth2/token'
};


// Initialize the OAuth2 Library 
var oauth2 = require('simple-oauth2')(credentials);
 
// Authorization uri definition 
var authorization_uri = oauth2.authCode.authorizeURL({
  redirect_uri: CALLBACK_URL,
  // scope: 'user', //For github
  scope : 'openid',
  state: '3(#0/!~'
});

// TODO REMOVE ME : For test only
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

// Initial page redirecting to Authentication Provider 
router.get('/auth', function (req, res) {
	console.log("redirecting to authorization_uri ......");
    res.redirect(authorization_uri);
});
 
// Callback service parsing the authorization token and asking for the access token 
router.get('/callback', function (req, res) {
  var token;

  console.log("[callback] start.....");	
  var code = req.query.code;
  console.log("[code] " + code);	

  oauth2.authCode.getToken({
    code: code,
    redirect_uri: CALLBACK_URL
  }, saveToken);
 
  function saveToken(error, result) {
    if (error) { 
    	console.log('[Access Token Error]', error.message); 
    	res.send(error);
    }else{
	    token = oauth2.accessToken.create(result);
	    console.log('[Token]' +  token);
	    console.log('[Token.token]' +  token.token);
	    console.log('[Token.token.access_token]' +  getAccessToekn(token.token));
		  
		unirest.get('https://api.github.com/user')
			.headers({'Authorization' : 'token ' + getAccessToekn(token.token),
				'User-Agent': 'nodejs oauth test'
			}).end(function (data) {
			console.log("[RES] => " + JSON.stringify(data));
			res.send("<b>Hello " + data.body.login + "</b></br>" + JSON.stringify(data));
		});
    }	
  }
  
});

function getAccessToekn(str){
	return str.split("=")[1].split('&')[0];
}




// Password flow
router.get('/auth/pass', function (req, res) {
	console.log("[Password Credentail Flow] started...");
	// Get the access token object. 
	var token;

	// var tokenConfig = {
	//   username: 'testuser',
	//   password: 'testuser' 
	// };
	 
	var tokenConfig = {
	  username: 'testuser',
	  password: 'testuser' 
	};

	// Callbacks 
	// Save the access token 
	oauth2.password.getToken(tokenConfig, function saveToken(error, result) {
	  
	  console.log("[getToken()] started...");

	  if (error) { 
	  	console.log('[Access Token Error]', error.message); 
	  }

	  token = oauth2.accessToken.create(result);
	  console.log('[Access Token] => ', JSON.stringify(token)); 
	  console.log("[api()] started... => " + token.token.access_token);	 

	  oauth2.api('GET', '/users', {
	    access_token: token.token.access_token
	  }, function (err, data) {
	    // console.log("[data] => "+data);
	    res.send(data);
	    if(err)
	    	console.log("[err] => " +err);
	  });
	});
	 
	// Promises 
	// Save the access token 

	// console.log("[getToken()] started...");
	// oauth2.password.getToken(tokenConfig)
	//   .then(function saveToken(result) {
	//     token = oauth2.accessToken.create(result);
	//    	console.log('[Access Token] => ' + JSON.stringify(token)); 
	//     return oauth2.api('GET', '/users', { access_token: token.token.access_token });
	//   })
	//   .then(function evalResource(data) {
	//     console.log(data);
	//     res.send(data);
	//   });
});

router.get('/auth/cred', function (req, res) {
	console.log("[Client Credential flow] started...");
	// Get the access token object.
	var cred = {
	  clientID: '7gq5zLlAxAhJVLfqkcldnPjCYgEa',
	  clientSecret: 'EXXOJ4GQfWcXjIy7Qqyu6p9z0fUa',
	  site: 'https://10.106.8.53:9443/login'
	};

	// Initialize the OAuth2 Library
	var oauth2 = require('simple-oauth2')(cred);
	var token;
	var tokenConfig = {};

	// Callbacks
	// Get the access token object for the client
	oauth2.client.getToken(tokenConfig, function saveToken(error, result) {
	  if (error) { console.log('Access Token Error', error.message); }
	  token = oauth2.accessToken.create(result);
	});


	// Promises
	// Get the access token object for the client

	// oauth2.client
	  // .getToken(tokenConfig)
	  // .then(function saveToken(result) {
	  //   token = oauth2.accessToken.create(result);
	  //   console.log('[Access Token result]', JSON.stringify(result));
	  //   console.log('[Access Token] => ', token);

	  // })
	  // .catch(function logError(error) {
	  //   console.log('[Access Token error]', error.message);
	  // });

	oauth2.password.getToken(tokenConfig, function saveToken(error, result) {
	  
	  console.log("[getToken()] started...");

	  if (error) { 
	  	console.log('[Access Token Error]', error.message); 
	  }

	  token = oauth2.accessToken.create(result);
	  console.log('[Access Token] => ', token); 
	  console.log("[Access Token] token.token.access_token => " + token.token.access_token);	 

	  oauth2.api('GET', '/userinfo?schema=openid', {
	    access_token: token.token.access_token
	  }, function (err, data) {
	    // console.log("[data] => "+data);
	    res.send(data);
	    if(err)
	    	console.log("[err] => " +err);
	  });
	});

});



router.get('/', function (req, res) {
  res.send('Hello<br><a href="/oauth/auth">Log in with WSO2 IS Server - Authentication Code Flow</a></br>'
  				+ '<a href="/oauth/auth/pass">Log in with WSO2 IS Server - Password Credentail Flow </a></br>'
  				+ '<a href="/oauth/auth/cred">Log in with WSO2 IS Server - Client Credentials Flow </a></br>');
});

module.exports = router;
