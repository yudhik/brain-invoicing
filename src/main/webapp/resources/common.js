if (typeof(jQuery) != "undefined")
  var $j = jQuery.noConflict();

$ = function() {
  var elements = new Array();
  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    if (typeof element == 'string')
      element = document.getElementById(element);

    if (arguments.length == 1)
      return element;

    elements.push(element);
  }

  return elements;
}

String.prototype.trim = function()
{
  a = this.replace(/^\s+/, '');
  return a.replace(/\s+$/, '');
}
