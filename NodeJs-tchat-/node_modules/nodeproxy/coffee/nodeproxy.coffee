nodeProxy = (func, callingproxy) ->
	proxyFunction = () -> 
		func.apply(callingproxy, arguments)	
	proxyFunction

module.exports = nodeProxy