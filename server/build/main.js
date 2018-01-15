require("source-map-support").install();
module.exports =
/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;
/******/
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "/";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 11);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_c_mobile_server_node_modules_babel_runtime_regenerator__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_c_mobile_server_node_modules_babel_runtime_regenerator___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_c_mobile_server_node_modules_babel_runtime_regenerator__);


function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { return Promise.resolve(value).then(function (value) { step("next", value); }, function (err) { step("throw", err); }); } } return step("next"); }); }; }

var Koa = __webpack_require__(4);
var mysql = __webpack_require__(8);
var app = new Koa();
var server = __webpack_require__(2).createServer(app.callback());
var WebSocket = __webpack_require__(10);
var wss = new WebSocket.Server({ server: server });
var Router = __webpack_require__(7);
var cors = __webpack_require__(6);
var bodyparser = __webpack_require__(5);
var jwt = __webpack_require__(3);
var secret = "xxx";

var connection = mysql.createConnection({
    host: 'localhost',
    user: 'admin',
    password: 'admin',
    database: 'showItNow'
});
connection.connect();

app.use(bodyparser());
app.use(cors());
app.use(function () {
    var _ref = _asyncToGenerator( /*#__PURE__*/__WEBPACK_IMPORTED_MODULE_0_c_mobile_server_node_modules_babel_runtime_regenerator___default.a.mark(function _callee(ctx, next) {
        var start, ms;
        return __WEBPACK_IMPORTED_MODULE_0_c_mobile_server_node_modules_babel_runtime_regenerator___default.a.wrap(function _callee$(_context) {
            while (1) {
                switch (_context.prev = _context.next) {
                    case 0:
                        start = new Date();
                        _context.next = 3;
                        return next();

                    case 3:
                        ms = new Date() - start;

                        console.log(ctx.method + ' ' + ctx.url + ' ' + ctx.response.status + ' - ' + ms + 'ms');

                    case 5:
                    case 'end':
                        return _context.stop();
                }
            }
        }, _callee, this);
    }));

    return function (_x, _x2) {
        return _ref.apply(this, arguments);
    };
}());

var shows = [];

function cacheDB() {
    connection.query("Select * from shows", function (error, results, fields) {
        if (error) throw error;
        shows = results;
    });
}

cacheDB();

var router = new Router();

var broadcast = function broadcast(data) {
    return wss.clients.forEach(function (client) {
        if (client.readyState === WebSocket.OPEN) {
            client.send(JSON.stringify(data));
        }
    });
};

router.get('/testConnection', function (ctx) {
    ctx.response.body = "good";
    ctx.response.status = 200;
});

router.get('/getAll', function (ctx) {
    ctx.response.body = shows;
    ctx.response.status = 200;
});

router.post('/delete', function (ctx) {
    var headers = ctx.request.body;
    var token = headers.token;
    var decodedToken = jwt.decode(token, secret);
    if (decodedToken.user_type !== 0) {
        ctx.response.body = { text: 'Not authorized' };
        ctx.response.status = 400;
        return;
    }
    console.log("body: " + JSON.stringify(headers));
    var showName = headers.showName;
    if (typeof showName != 'undefined') {
        var index = shows.findIndex(function (show) {
            return show.showName === showName;
        });
        if (index === -1) {
            ctx.response.body = { text: 'Invalid name' };
            ctx.response.status = 404;
        } else {
            broadcast({
                operation: 'delete',
                show: shows[index]
            });
            connection.query("Delete from shows where showName = ?", [showName], function (error, results, fields) {
                if (error) throw error;
                shows = results;
            });
            ctx.response.body = { text: 'The show was deleted' };
            ctx.response.status = 200;
            cacheDB();
        }
    } else {
        ctx.response.body = { text: 'Name missing' };
        ctx.response.status = 404;
    }
});

router.post('/login', function (ctx) {
    var headers = ctx.request.body;
    console.log("body: " + JSON.stringify(headers));
    var rUser = headers.user;
    var password = headers.user;
    connection.querry("Select * FROM accounts WHERE user = ? and password = ?", [user, password], function (error, results, fields) {
        if (error) throw error;
        if (results.length != 0) {
            var payload = {
                username: rUser,
                user_type: results[0].user_type
            };

            ctx.response.body = {
                token: jwt.encode(payload, secret),
                user_type: results[0].user_type
            };
            ctx.response.status = 200;
        } else {
            ctx.response.status = 400;
            ctx.response.body = "Invalid user";
        }
    });
});

router.post('/add', function (ctx) {
    var headers = ctx.request.body;
    var token = headers.token;
    var decodedToken = jwt.decode(token, secret);
    if (decodedToken.user_type !== 0) {
        ctx.response.body = { text: 'Not authorized' };
        ctx.response.status = 400;
        return;
    }
    console.log("body: " + JSON.stringify(headers));
    var sid = headers.sid;
    var showName = headers.showName;
    var day = headers.day;
    var month = headers.month;
    var freeSpots = headers.freeSpots;
    var allSpots = headers.allSpots;
    if (typeof showName != 'undefined' || typeof day != 'undefined' || typeof month != 'undefined' || typeof freeSpots != 'undefined' || typeof allSpots != 'undefined') {
        var index = shows.findIndex(function (show) {
            return show.showName === showName;
        });
        if (index === -1) {
            var show = {
                sid: sid,
                showName: showName,
                day: day,
                month: month,
                freeSpots: freeSpots,
                allSpots: allSpots
            };

            connection.query("INSERT INTO shows(showName, day, month, freeSpots, allSpots) VALUES (?,?,?,?,?)", [showName, day, month, freeSpots, allSpots], function (error, results, fields) {
                if (error) throw error;
            });

            broadcast({
                operation: 'insert',
                show: show
            });
            ctx.response.body = show;
            ctx.response.status = 200;
            cacheDB();
        } else {
            ctx.response.body = { text: 'The show already exists' };
            ctx.response.status = 404;
        }
    } else {
        ctx.response.body = { text: 'Missing data' };
        ctx.response.status = 404;
    }
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);

/***/ },
/* 1 */
/***/ function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(9);


/***/ },
/* 2 */
/***/ function(module, exports) {

module.exports = require("http");

/***/ },
/* 3 */
/***/ function(module, exports) {

module.exports = require("jwt-simple");

/***/ },
/* 4 */
/***/ function(module, exports) {

module.exports = require("koa");

/***/ },
/* 5 */
/***/ function(module, exports) {

module.exports = require("koa-bodyparser");

/***/ },
/* 6 */
/***/ function(module, exports) {

module.exports = require("koa-cors");

/***/ },
/* 7 */
/***/ function(module, exports) {

module.exports = require("koa-router");

/***/ },
/* 8 */
/***/ function(module, exports) {

module.exports = require("mysql");

/***/ },
/* 9 */
/***/ function(module, exports) {

module.exports = require("regenerator-runtime");

/***/ },
/* 10 */
/***/ function(module, exports) {

module.exports = require("ws");

/***/ },
/* 11 */
/***/ function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(0);


/***/ }
/******/ ]);
//# sourceMappingURL=main.map