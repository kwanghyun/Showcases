var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var consul = require('consul')({host:'10.106.8.158', port:'8500'});

var routes = require('./routes/index');
var users = require('./routes/users');
var oauth = require('./routes/oauth');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', routes);
app.use('/users', users);
app.use('/oauth', oauth);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});


module.exports = app;

console.log("App is running.........");

// Returns the members as seen by the consul agent.
consul.agent.members(function(err, result) {
  if (err) throw err;
  console.log("[consul.agent.members] DONE");
  console.log(result);
});

// Registers a new service.
consul.agent.service.register({name : 'node-demo-app', id : 'node-demo-app-12345'}, function(err) {
  if (err) throw err;
  console.log("[consul.agent.service.register] DONE");
});


// Registers a new check.

// var check = {
//   name: 'node-demo-app-check',
//   ttl: '10s',
//   notes: 'This is an node-demo-app check.',
// };

// consul.agent.check.register(check, function(err) {
//   if (err) throw err;
//   console.log("[consul.agent.check.register] DONE");
// });


consul.agent.check.deregister('node-demo-app', function(err) {
  if (err) throw err;
  console.log("[consul.agent.check.deregister] DONE");
});

// Returns the nodes and health info of a service.
consul.health.service({service:'spark', passing : true }, function(err, result) {
  if (err) throw err;
  console.log("[consul.health.service] DONE");
  console.log(result);
});

