var express        =         require("express");
var bodyParser     =         require("body-parser");
var session        =         require('express-session');
var app            =         express();
app.use('/public', express.static(__dirname + '/public')); // Rendre le dossier public public
var url = require('url'); // generation des URL automatiquement en cas de changement de pc


var server = app.listen(9999, function () {
    console.log('sever listen in port 9999');
});

var io = require('socket.io').listen(server);

// recuperation des parametres de la requete
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());


function generateUrl(req) {
    var requrl = url.format({
        protocol: req.protocol,
        host: req.get('host'),
    });
    return requrl;
}


app.get('/measure',function (req,res) {
    res.render('measure.html.twig');
});

var compter = 0;

io.on('connection', function(socket){

    socket.on('sendInformationUser', function(data){
        console.log(data + ' just logged in');
        socket.broadcast.emit('hello to you too',  data);
        //to self
        socket.emit('hello to you too', "hello to you too " + data);
        compter++;
    });

    socket.on('message', function (data) {

    });

    socket.on('getListUsersConnected',function (data) {

    });

    socket.on('publicMessage', function (data) {
        socket.broadcast.emit('broadcasting', data);
        //envoyer a moi mm aussi
        socket.emit('broadcasting', data);
    });

});
