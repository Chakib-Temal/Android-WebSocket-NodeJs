(function() {
  var nodeProxy;

  nodeProxy = function(func, callingproxy) {
    var proxyFunction;
    proxyFunction = function() {
      return func.apply(callingproxy, arguments);
    };
    return proxyFunction;
  };

  module.exports = nodeProxy;

}).call(this);
