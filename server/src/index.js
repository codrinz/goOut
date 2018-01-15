const Koa = require('koa');
const mysql = require('mysql');
const app = new Koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({server});
const Router = require('koa-router');
const cors = require('koa-cors');
const bodyparser = require('koa-bodyparser');
var jwt = require('jwt-simple');
var secret = "xxx";

var connection = mysql.createConnection({
    host     : 'localhost',
    user     : 'admin',
    password : 'admin',
    database : 'showItNow'
  });
connection.connect();

app.use(bodyparser());
app.use(cors());
app.use(async function (ctx, next) {
    const start = new Date();
    await next();
    const ms = new Date() - start;
    console.log(`${ctx.method} ${ctx.url} ${ctx.response.status} - ${ms}ms`);
});

var shows = []

function cacheDB(){
connection.query("Select * from shows", function (error, results, fields) {
    if (error) throw error;
    shows = results;
  });
}
  
cacheDB();

const router = new Router();

const broadcast = (data) =>
    wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
            client.send(JSON.stringify(data));
        }
    });

router.get('/testConnection',ctx => {
    ctx.response.body = "good";
    ctx.response.status = 200;
});

router.get('/getAll',ctx => {
    ctx.response.body = shows;
    ctx.response.status = 200;
}
);

router.post('/delete', ctx => {
    const headers = ctx.request.body;
    const token = headers.token;
    const decodedToken = jwt.decode(token,secret);
    if(decodedToken.user_type!==0)
    {
        ctx.response.body = {text: 'Not authorized'};
        ctx.response.status = 400;
        return;
    }
    console.log("body: " + JSON.stringify(headers));
    const showName = headers.showName;
    if (typeof showName != 'undefined') {
        const index = shows.findIndex(show => show.showName === showName);
        if (index === -1) {
            ctx.response.body = {text: 'Invalid name'};
            ctx.response.status = 404;
        } else {
            broadcast({
                operation: 'delete',
                show: shows[index]
            })
            connection.query("Delete from shows where showName = ?",[showName], function (error, results, fields) {
                if (error) throw error;
                shows = results;
              });
            ctx.response.body = {text: 'The show was deleted'};
            ctx.response.status = 200;
            cacheDB();
        }
    } else {
        ctx.response.body = {text: 'Name missing'};
        ctx.response.status = 404;
    }
}
);

router.post('/login', ctx => {
    const headers = ctx.request.body;
    console.log("body: " + JSON.stringify(headers));
    const rUser = headers.user;
    const password = headers.user;
    connection.querry("Select * FROM accounts WHERE user = ? and password = ?",[user,password], function (error, results, fields) {
        if (error) throw error;
        if(results.length != 0){
            var payload = {
                username: rUser,
                user_type: results[0].user_type
            };
            
            ctx.response.body = {
                token: jwt.encode(payload,secret),
                user_type: results[0].user_type
            };
            ctx.response.status = 200;
        }else{
            ctx.response.status = 400;
            ctx.response.body = "Invalid user";
        }
      });
});

router.post('/add', ctx => {
    const headers = ctx.request.body;
    const token = headers.token;
    const decodedToken = jwt.decode(token,secret);
    if(decodedToken.user_type!==0)
    {
        ctx.response.body = {text: 'Not authorized'};
        ctx.response.status = 400;
        return;
    }
    console.log("body: " + JSON.stringify(headers));
    const sid = headers.sid;
    const showName = headers.showName;
    const day = headers.day;
    const month = headers.month;
    const freeSpots = headers.freeSpots;
    const allSpots = headers.allSpots;
    if(typeof showName != 'undefined' || typeof day != 'undefined' || typeof month != 'undefined' || typeof freeSpots != 'undefined' || typeof allSpots != 'undefined'){
        const index = shows.findIndex(show => show.showName === showName);
        if(index === -1){
            let show = {
                sid,
                showName,
                day,
                month,
                freeSpots,
                allSpots
            };
            
            connection.query("INSERT INTO shows(showName, day, month, freeSpots, allSpots) VALUES (?,?,?,?,?)",[showName,
                    day,
                    month,
                    freeSpots,
                    allSpots], function (error, results, fields) {
                    if (error) throw error;
                  });
            
            
            broadcast({
                operation: 'insert',
                show: show
            }
            );
            ctx.response.body = show;
            ctx.response.status = 200;
            cacheDB();
        }else{
            ctx.response.body = {text: 'The show already exists'};
            ctx.response.status = 404;
        }
    }else{
        ctx.response.body = {text: 'Missing data'};
        ctx.response.status = 404;
    }
}
);



app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);