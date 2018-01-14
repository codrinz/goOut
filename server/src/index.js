const Koa = require('koa');
const app = new Koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({server});
const Router = require('koa-router');
const cors = require('koa-cors');
const bodyparser = require('koa-bodyparser');

app.use(bodyparser());
app.use(cors());
app.use(async function (ctx, next) {
    const start = new Date();
    await next();
    const ms = new Date() - start;
    console.log(`${ctx.method} ${ctx.url} ${ctx.response.status} - ${ms}ms`);
});

const shows = [];

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

router.post('/add', ctx => {
    const headers = ctx.request.body;
    console.log("body: " + JSON.stringify(headers));
    const sid = headers.sid;
    const showName = headers.showName;
    const day = headers.day;
    const month = headers.month;
    const freeSpots = headers.freeSpots;
    const allSpots = headers.allSpots;
    if(typeof showName != 'undefined' || typeof day != 'undefined' || typeof month != 'undefined' || typeof freeSpots != 'undefined' || typeof allSpots != 'undefined'){
        const index = shows.findIndex(show => show.sid === sid);
        if(index === -1){
            let show = {
                sid,
                showName,
                day,
                month,
                freeSpots,
                allSpots
            };
            shows.push(show);
            broadcast(show);
            ctx.response.body = show;
            ctx.response.status = 200;
        }else{
            ctx.response.body = {text: 'The show already exists'};
            ctx.response.status = 404;
        }
    }else{
        ctx.response.body = {text: 'Missing data'};
        ctx.response.status = 404;
    }
}
)

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);