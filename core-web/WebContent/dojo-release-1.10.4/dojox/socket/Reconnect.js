//>>built
define("dojox/socket/Reconnect",["dojox/socket","dojo/aspect"],function(_1,_2){
_1.Reconnect=function(_3,_4){
var _5=_4.reconnectTime||10000;
var _6,_7;
_4=_4||{};
_2.after(_3,"onclose",function(_8){
clearTimeout(_6);
if(!_8.wasClean){
_3.disconnected(function(){
_1.replace(_3,_7=_3.reconnect());
});
}
},true);
if(!_3.disconnected){
_3.disconnected=function(_9){
setTimeout(function(){
_9();
_6=setTimeout(function(){
if(_7.readyState<2){
_5=_4.reconnectTime||10000;
}
},10000);
},_5);
_5*=_4.backoffRate||2;
};
}
if(!_3.reconnect){
_3.reconnect=function(){
return _3.args?_1.LongPoll(_3.args):_1.WebSocket({url:_3.URL||_3.url});
};
}
return _3;
};
return _1.Reconnect;
});
