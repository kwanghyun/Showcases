var express = require('express'),
	path = require('path'),
	partials = require('express-partials'),
	app = express(),
	router = express.Router();
	routes = require('./routes'),
	errorHandlers = require('./middleware/errorhandlers'),
	log = require('./middleware/log'),
	cookieParser = require('cookie-parser'),
	bodyParser = require('body-parser'),
	csrf = require('csurf'),
	session = require('express-session'),
	RedisStore = require('connect-redis')(session),
	util = require('./middleware/utilities'),
	flash = require('connect-flash'),
	config = require('./config'),
	io = require('./socket.io'),
	simple = require('./routes/simple'),
	passport = require('./passport');

//For DB TEST
//var dbConn = require('./db/connection');
//var db = dbConn.getDB("testdb", "users");
//For DB TEST


app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

//REST Routers
app.use('/gls', simple);

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
app.set('view options', {defaultLayout: 'layout'});

app.use(partials());
app.use(log.logger);
app.use(express.static(__dirname + '/static'));
app.use(cookieParser(config.secret));
app.use(session({
	secret: config.secret,
	saveUninitialized: true,
	resave: true,
	store: new RedisStore(
		{url: config.redisUrl})
	})
);
app.use(passport.passport.initialize());
app.use(passport.passport.session());
app.use(csrf());
app.use(util.csrf);
app.use(util.authenticated);
app.use(flash());

app.use(util.templateRoutes);

//routes

app.get('/', routes.index);
app.get(config.routes.login, routes.login);
app.get(config.routes.logout, routes.logOut);
app.get(config.routes.register, routes.register);
app.post(config.routes.register, routes.registerProcess);
app.get(config.routes.main, [util.requireAuthentication], routes.main);
app.get(config.routes.chat, [util.requireAuthentication], routes.chat);


//routes for passport
passport.routes(app);

app.use(errorHandlers.error);
app.use(errorHandlers.notFound);

var server = app.listen(config.port);

//db.users.findOne({
//	username : "asdf"
//}, function(err, doc) {
//	console.log(doc);
//});


// db.testdb.find({
//	parkinglotId : "parkinglot-id_58"
//}, function(err, data) {
//	if (err || !data)
//		console.log("No female users found");
//	else
//		data.forEach(function(_data) {
//			console.log(_data);
//		});
//});

//db.testdb.runCommand('count', function(err, res) {
//    console.log(res);
//});
//
//db.runCommand({ping:1}, function(err, res) {
//    if(!err && res.ok) console.log(JSON.stringify(res));
//});

io.startIo(server);
