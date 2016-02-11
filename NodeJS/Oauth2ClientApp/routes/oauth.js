var express = require('express');
var router = express.Router();

// Set the configuration settings 


// var credentials = {
//   clientID: 'testuser',
//   clientSecret: 'testuser',
//   site: 'https://10.106.8.53:9443/login',
//   authorizationPath: '/oauth2/authorize',
//   tokenPath: '/oauth2/token'
// };

var credentials = {
  'clientID': '1015c2b97bd1f36eb883',
  'clientSecret': 'dcb58dcee5eeae69d1fd7778dbd0e3a2462c683e',
  // 'clientID': 'kwanghyun.jang@gmail.com',
  // 'clientSecret': 'jkh8019',
  'site': 'https://github.com/login',
  'authorizationPath': '/oauth/access_token',
  'tokenPath': '/oauth/authorize'
};

// Initialize the OAuth2 Library 
var oauth2 = require('simple-oauth2')(credentials);
 
// Authorization uri definition 
var authorization_uri = oauth2.authCode.authorizeURL({
  redirect_uri: 'http://10.154.16.108:3000/oauth/callback',
  scope: 'notifications',
  state: '3(#0/!~'
});
 
// Initial page redirecting to Authentication Provider 
router.get('/auth', function (req, res) {
	console.log("redirecting to authorization_uri ......");
    res.redirect(authorization_uri);
});
 
// Callback service parsing the authorization token and asking for the access token 
router.get('/callback', function (req, res) {
  console.log("[callback] start.....");	
  var code = req.query.code;
 
  oauth2.authCode.getToken({
    code: code,
    redirect_uri: 'http://10.154.16.108:3000/oauth/callback'
  }, saveToken);
 
  function saveToken(error, result) {
    if (error) { console.log('Access Token Error', error.message); }
    token = oauth2.accessToken.create(result);
  }
});

 
router.get('/', function (req, res) {
  res.send('Hello<br><a href="/oauth/auth">Log in with WSO2 IS Server</a>');
});

module.exports = router;
