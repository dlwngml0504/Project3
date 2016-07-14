
var mongoose = require('mongoose');
var dbURI = 'mongodb://localhost:27017/SoundCloud';
mongoose.connect(dbURI); // 기본 설정에 따라 포트가 상이 할 수 있습니다.
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function callback () {
   console.log("mongo db connection OK.");
});

var express = require('express')
, http = require('http')
, app = express()
, server = http.createServer(app);
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: false }));
var urlencodedParser = bodyParser.urlencoded({ extended: false });
app.use(bodyParser());
app.use(bodyParser.text());


app.post('/', function (request, respond) {
	console.log(request.body);
});


var Schema = mongoose.Schema;
var userSchema = new Schema({
	id : { type: String, required: true, unique: true },
	name : { type: String},
	state : {type: String}, //Lister or Musician
	accountnumber : {type : String},
	bank : {type : String},
	cash : {type : Number, default : 0},
	follower : [String],
	following : [String],
	pop : {type : Boolean, default : false},
	OST : {type : Boolean, default : false},
	rap : {type : Boolean, default : false},
	indi : {type : Boolean, default : false},
	metal : {type : Boolean, default : false},
	music : [String]//musician이라면 필요
});
var musicSchema = new Schema({
	title :  { type: String},
	lyricist : {type: String}, //작사가
	composer : {type : String},//작곡가
	uploader :  {type : String}, //upload한사람
	singer : {type : String},  //가수
	like_num : {type : Number, default : 0},
	view_num : {type : Number, default : 0},
	pop : {type : Boolean, default : false},
	OST : {type : Boolean, default : false},
	rap : {type : Boolean, default : false},
	indi : {type : Boolean, default : false},
	metal : {type : Boolean, default : false},
	date : {type:Date, default:Date.now},
	URL : {type : String}
});
var User = mongoose.model('UserInfo', userSchema);
var Music = mongoose.model('MusicInfo',musicSchema);
app.post('/login', function (request, respond) {
	console.log("Got a POST/login request for the homepage\n"+JSON.parse(request.body).id);
	var newUsr = new User();
	User.findOne({'id':JSON.parse(request.body).id},function(err,result){
        if(err){
            console.err(err);
            throw err;
        }
        if (result){
            console.log("Find");
            respond.write("Find");
        }
        else {
        	console.log("Not Find");
        }
        
    });
});
app.post('/follow', function (req, res) {
	console.log("Got a POST/follow request for the homepage\n"+JSON.parse(req.body).followed);
	var newUsr = new User();
	User.find({'id':JSON.parse(req.body).followed},function(err,result){
        if(err){
            console.err(err);
            throw err;
        }
        if(!result) return res.status(404).json({ error: 'id' });
        
        result[0].follower.push(JSON.parse(req.body).following);
        result[0].save(function(err){
            if(err) res.status(500).end("Failed");
            //res.end("Success");
        });
    });
	User.find({'id':JSON.parse(req.body).following},function(err,result){
        if(err){
            console.err(err);
            throw err;
        }
        if(!result) return res.status(404).json({ error: 'id' });
        
        result[0].following.push(JSON.parse(req.body).followed);
        result[0].save(function(err){
            if(err) res.status(500).end("Failed");
            res.end("Success");
        });
    });
});
app.post('/register', function (request, respond) {
	console.log("Got a POST/register request for the homepage\n"+request.body);
	var newUsr = new User(JSON.parse(request.body));
	newUsr.save(function(err){
		if (err) {
			console.log("ERROR : POST/register request");
			return;
		}
	});
});

app.post('/upload', function(request,respond) {
	console.log("Got a POST/upload request for the homepage\n"+request.body);
	var newMusic = new Music(JSON.parse(request.body));
	newMusic.save(function(err){
		if (err) {
			console.log("ERROR : POST/register request");
			return;
		}
	});
	var oldUser = new User(JSON.parse(newMusic.uploader));
	 User.find({"id":oldUser.id}, function(err, userinfo){
	        if(err) {
	        	return respond.status(500).json({ error: 'database failure' });
	        }
	        if(!userinfo) {
	        	return respond.status(404).json({ error: 'id' });
	        }
	        userinfo[0].music.push(newMusic.title);
	        userinfo[0].save(function(err){
	            if(err) respond.status(500).end("Failed");
	            respond.end("Success");
	        });
	    });
});
app.post('/all/music', function(request,respond) {
	console.log("Got a POST/all/music request for the homepage\n"+request.body);
	var arr = [];
	Music.find({}, function(err, resultCursor) {

		if(err){
            console.err(err);
            throw err;
        }
		for (var ob in resultCursor) {
			arr.push(resultCursor[ob]);
		}
		//console.log(arr);
		respond.send(arr);
		respond.end();
	});
});
app.post('/search/music', function(request,respond) {
	console.log("Got a POST/search/music request for the homepage\n"+request.body);
	//var searchMusic = new Music(JSON.parse(request));
	var searchMusic = JSON.parse(request.body);
	var arr = [];
	Music.find({}, function(err, resultCursor) {

		if(err){
            console.err(err);
            throw err;
        }
		for (var ob in resultCursor) {
			for (var i in searchMusic) {
				if (i=="pop"){
					if (resultCursor[ob].pop==true){
						arr.push(resultCursor[ob]);
						break;
					}
				}
				if (i=="OST"){
					if (resultCursor[ob].OST==true){
						arr.push(resultCursor[ob]);
						break;
					}
				}
				if (i=="indi"){
					if (resultCursor[ob].indi==true){
						arr.push(resultCursor[ob]);
						break;
					}
				}

				if (i=="rap"){
					if (resultCursor[ob].rap==true){
						arr.push(resultCursor[ob]);
						break;
					}
				}
				if (i=="metal"){
					if (resultCursor[ob].metal==true){
						arr.push(resultCursor[ob]);
						break;
					}
				}
			}
		}
		//console.log(arr);
		respond.send(arr);
		respond.end();
	});
});
app.get('/userinfo', function(req, res){
	console.log("GET USERINFO");
	//console.log(req.query.userinfo);
	var userinfo = JSON.parse(req.query.userinfo);
	var id = userinfo.id;
	console.log(id);
	res.end(id);
})

app.get('/userinfo/:id', function(req, res){
	 User.findOne({"id":req.params.id}, function(err, userinfo){
	        if(err) return res.status(500).send({error: 'database failure'});
	        res.json(userinfo);
	        console.log("Loading Complete");
	    })
});

app.put('/buycash/:id', function(req, res){
	console.log("Buy Cash");
	console.log(req.params.id);
	console.log(req.body);
	
    User.find({"id":req.params.id}, function(err, userinfo){
    	console.log(req.params.id);
    	console.log(userinfo[0].cash);
    	console.log(userinfo);
        if(err) return res.status(500).json({ error: 'database failure' });
        if(!userinfo) return res.status(404).json({ error: 'id' });
 
        userinfo[0].cash += parseInt(req.body.value);
        console.log(userinfo[0].cash);
        userinfo[0].save(function(err){
            if(err) res.status(500).end("Failed");
            res.end("Success");
        });
    });
 
});

app.post('/donatecash', function(req, res){
	console.log("Donate Cash");
    User.find({"id":JSON.parse(req.body).id}, function(err, userinfo){
 
        if(err) return res.status(500).json({ error: 'database failure' });
        if(!userinfo) return res.status(404).json({ error: 'id' });
        console.log(userinfo[0].cash);
        console.log(JSON.parse(req.body).value);
        userinfo[0].cash -= parseInt(JSON.parse(req.body).value);
        console.log(userinfo[0].cash);
        userinfo[0].save(function(err){
            if(err) res.status(500).end("Failed");
            res.end("Success");
        });
    });
});

app.get('/getmusic/:title', function(req,res){
	console.log("Get Music");
	console.log(req.params.title);
	Music.find({"title":req.params.title}, function(err, musicinfo){
		if(err) return res.status(500).json({error:'database failure'});
		if(!musicinfo) return res.status(404).json({error:'music'});
		var url = musicinfo[0].URL;
		console.log(url);
		res.write(url)
		res.send();
	})
});
app.post('/likemusic', function(req,res){
	console.log("Get Music");
	console.log(req.body);
	Music.find({"URL":JSON.parse(req.body).URL}, function(err, musicinfo){
		if(err) {
			return res.status(500).json({error:'database failure'});
		}
		if(!musicinfo) {
			return res.status(404).json({error:'music'});
		}
		console.log(musicinfo[0].like_num);
		musicinfo[0].like_num++;
		console.log(musicinfo[0].like_num);
		musicinfo[0].save(function(err){
	            if(err) res.status(500).end("Failed");
	            res.end("Success");
	        });
	})
});
app.post('/detailmusic', function(req,res){
	console.log("Detail Music");
	console.log(req.body);
	Music.find({"URL":JSON.parse(req.body).URL}, function(err, musicinfo){
		if(err) {
			return res.status(500).json({error:'database failure'});
		}
		if(!musicinfo) {
			return res.status(404).json({error:'music'});
		}
		console.log(musicinfo[0]);
		//res.write(musicinfo[0]);
		res.send(musicinfo[0]);
		res.end();
	})
});

app.get('/userinfo/follower/:id', function(req,res){
	console.log("Get follower");
	console.log(req.params.id);
	User.find({"id":req.params.id}, function(err, userinfo){
		console.log(userinfo);
		if(err) return res.status(500).json({error:'database failure'});
		if(!userinfo) return res.status(404).json({error:'id'});
		console.log(userinfo[0].follower);
	
		res.write(userinfo[0].follower.toString());
		res.send();
	})
});

app.get('/userinfo/following/:id', function(req,res){
	console.log("Get following");
	console.log(req.params.id);
	User.find({"id":req.params.id}, function(err, userinfo){
		console.log(userinfo);
		if(err) return res.status(500).json({error:'database failure'});
		if(!userinfo) return res.status(404).json({error:'id'});
		console.log(userinfo[0].following);
	
		res.write(userinfo[0].following.toString());
		res.send();
	})
});

app.get('/userinfo/musics/:id', function(req,res){
	console.log("Get Musics");
	console.log(req.params.id);
	User.find({"id":req.params.id}, function(err, userinfo){
		console.log(userinfo);
		if(err) return res.status(500).json({error:'database failure'});
		if(!userinfo) return res.status(404).json({error:'id'});
		console.log(userinfo[0].music);
	
		res.write(userinfo[0].music.toString());
		res.send();
	})
});
server.listen(1337, function() {
  //console.log('Express server listening on port ' + server.address().port);
});