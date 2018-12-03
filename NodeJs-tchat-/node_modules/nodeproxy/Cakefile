{exec} = require "child_process"
fs = require "fs"
sourceDir = "jscript"
specDirPattern = ///spec$///
applicationName = "catchify"

compile = (callback) ->
	exec "coffee --compile --output #{sourceDir}/ coffee/", (err, stdout, stderr) ->
		fatalError(err, "Failed to compile coffeescript!") if err
		success("Coffeescript compiled", callback)


trim = (string) -> 
	string.replace(/^\s+|\s+$/g,"")

fatalError = (err, message) ->
	log "ERROR: #{message} \n---------------------------------------------------------------"
	throw err

success = (message, callback) ->
	log "SUCCESS: #{message}"
	callback() if callback

log = (message) ->
	console.log trim(message)

task "check", "compile all coffeescript and run vows", ->
	compile()