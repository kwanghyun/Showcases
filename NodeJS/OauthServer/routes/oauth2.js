var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var oauthserver = require('oauth2-server');

app.oauth = oauthserver({
  model: model,
  grants: ['auth_code', 'password', 'refresh_token'],
  debug: true,
  accessTokenLifetime: model.accessTokenLifetime
});

/*
router.all(path, [callback, ...] callback)
This method is just like the router.METHOD() methods, except that it matches all HTTP methods (verbs).

This method is extremely useful for mapping “global” logic for specific path prefixes or arbitrary matches. For example, if you placed the following route at the top of all other route definitions, it would require that all routes from that point on would require authentication, and automatically load a user. Keep in mind that these callbacks do not have to act as end points; loadUser can perform a task, then call next() to continue matching subsequent routes.

router.all('*', requireAuthentication, loadUser);
Or the equivalent:

router.all('*', requireAuthentication)
router.all('*', loadUser);
Another example of this is white-listed “global” functionality. Here the example is much like before, but it only restricts paths prefixed with “/api”:

router.all('/api/*', requireAuthentication);
*/

router.all('/oauth/token', app.oauth.grant());


router.get('/secret', app.oauth.authorise(), function (req, res) {
  // Will require a valid access_token
  res.send('Secret area');
});

module.exports = router;
