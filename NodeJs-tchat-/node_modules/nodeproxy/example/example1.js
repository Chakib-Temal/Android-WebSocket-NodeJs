var nodeproxy = require('../jscript/nodeproxy');

function ObjectOne(){
	function helloWorld(){
		console.log("Hello " + this.name);
	}
	return {
		helloWorld: helloWorld
	}
}

function ObjectTwo(){
	function init(){
		var contextObject = { name: 'iain' };
		var objectOne = new ObjectOne();
		nodeproxy(objectOne.helloWorld, contextObject)();
	}
	return {
		init: init
	}
}

var objectTwo = new ObjectTwo();
objectTwo.init();