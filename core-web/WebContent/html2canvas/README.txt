http://vanilla-js.blogspot.tw/2014/03/html2canvas-error-index-or-size-is.html


 html2canvas Error : Index or size is negative or greater than the allowed amount Bug fix
 
 
Для исправления ошибки "Error : Index or size is negative or greater than the allowed amoun" в библиотеке html2canvas необходимо заменить строку

ctx.drawImage(canvas, bounds.left, bounds.top, bounds.width, bounds.height, 0, 0, bounds.width, bounds.height);

на строки:

var imgData = canvas.getContext("2d").getImageData(bounds.left, bounds.top, bounds.width, bounds.height);
ctx.putImageData(imgData, 0, 0);

