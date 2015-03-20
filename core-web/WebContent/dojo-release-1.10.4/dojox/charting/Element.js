//>>built
define("dojox/charting/Element",["dojo/_base/array","dojo/dom-construct","dojo/_base/declare","dojox/gfx","dojox/gfx/shape"],function(_1,_2,_3,_4,_5){
return _3("dojox.charting.Element",null,{chart:null,group:null,htmlElements:null,dirty:true,renderingOptions:null,constructor:function(_6,_7){
this.chart=_6;
this.group=null;
this.htmlElements=[];
this.dirty=true;
this.trailingSymbol="...";
this._events=[];
if(_7&&_7.renderingOptions){
this.renderingOptions=_7.renderingOptions;
}
},purgeGroup:function(){
this.destroyHtmlElements();
if(this.group){
this.getGroup().removeShape();
var _8=this.getGroup().children;
if(_5.dispose){
for(var i=0;i<_8.length;++i){
_5.dispose(_8[i],true);
}
}
if(this.getGroup().rawNode){
_2.empty(this.getGroup().rawNode);
}
this.getGroup().clear();
if(_5.dispose){
_5.dispose(this.getGroup(),true);
}
if(this.getGroup()!=this.group){
if(this.group.rawNode){
_2.empty(this.group.rawNode);
}
this.group.clear();
if(_5.dispose){
_5.dispose(this.group,true);
}
}
this.group=null;
}
this.dirty=true;
if(this._events.length){
_1.forEach(this._events,function(_9){
_9.shape.disconnect(_9.handle);
});
this._events=[];
}
return this;
},cleanGroup:function(_a){
this.destroyHtmlElements();
if(!_a){
_a=this.chart.surface;
}
if(this.group){
var _b;
var _c=this.getGroup().children;
if(_5.dispose){
for(var i=0;i<_c.length;++i){
_5.dispose(_c[i],true);
}
}
if(this.getGroup().rawNode){
_b=this.getGroup().bgNode;
_2.empty(this.getGroup().rawNode);
}
this.getGroup().clear();
if(_b){
this.getGroup().rawNode.appendChild(_b);
}
}else{
this.group=_a.createGroup();
if(this.renderingOptions&&this.group.rawNode&&this.group.rawNode.namespaceURI=="http://www.w3.org/2000/svg"){
for(var _d in this.renderingOptions){
this.group.rawNode.setAttribute(_d,this.renderingOptions[_d]);
}
}
}
this.dirty=true;
return this;
},getGroup:function(){
return this.group;
},destroyHtmlElements:function(){
if(this.htmlElements.length){
_1.forEach(this.htmlElements,_2.destroy);
this.htmlElements=[];
}
},destroy:function(){
this.purgeGroup();
},getTextWidth:function(s,_e){
return _4._base._getTextBox(s,{font:_e}).w||0;
},getTextWithLimitLength:function(s,_f,_10,_11){
if(!s||s.length<=0){
return {text:"",truncated:_11||false};
}
if(!_10||_10<=0){
return {text:s,truncated:_11||false};
}
var _12=2,_13=0.618,_14=s.substring(0,1)+this.trailingSymbol,_15=this.getTextWidth(_14,_f);
if(_10<=_15){
return {text:_14,truncated:true};
}
var _16=this.getTextWidth(s,_f);
if(_16<=_10){
return {text:s,truncated:_11||false};
}else{
var _17=0,end=s.length;
while(_17<end){
if(end-_17<=_12){
while(this.getTextWidth(s.substring(0,_17)+this.trailingSymbol,_f)>_10){
_17-=1;
}
return {text:(s.substring(0,_17)+this.trailingSymbol),truncated:true};
}
var _18=_17+Math.round((end-_17)*_13),_19=this.getTextWidth(s.substring(0,_18),_f);
if(_19<_10){
_17=_18;
end=end;
}else{
_17=_17;
end=_18;
}
}
}
},getTextWithLimitCharCount:function(s,_1a,_1b,_1c){
if(!s||s.length<=0){
return {text:"",truncated:_1c||false};
}
if(!_1b||_1b<=0||s.length<=_1b){
return {text:s,truncated:_1c||false};
}
return {text:s.substring(0,_1b)+this.trailingSymbol,truncated:true};
},_plotFill:function(_1d,dim,_1e){
if(!_1d||!_1d.type||!_1d.space){
return _1d;
}
var _1f=_1d.space,_20;
switch(_1d.type){
case "linear":
if(_1f==="plot"||_1f==="shapeX"||_1f==="shapeY"){
_1d=_4.makeParameters(_4.defaultLinearGradient,_1d);
_1d.space=_1f;
if(_1f==="plot"||_1f==="shapeX"){
_20=dim.height-_1e.t-_1e.b;
_1d.y1=_1e.t+_20*_1d.y1/100;
_1d.y2=_1e.t+_20*_1d.y2/100;
}
if(_1f==="plot"||_1f==="shapeY"){
_20=dim.width-_1e.l-_1e.r;
_1d.x1=_1e.l+_20*_1d.x1/100;
_1d.x2=_1e.l+_20*_1d.x2/100;
}
}
break;
case "radial":
if(_1f==="plot"){
_1d=_4.makeParameters(_4.defaultRadialGradient,_1d);
_1d.space=_1f;
var _21=dim.width-_1e.l-_1e.r,_22=dim.height-_1e.t-_1e.b;
_1d.cx=_1e.l+_21*_1d.cx/100;
_1d.cy=_1e.t+_22*_1d.cy/100;
_1d.r=_1d.r*Math.sqrt(_21*_21+_22*_22)/200;
}
break;
case "pattern":
if(_1f==="plot"||_1f==="shapeX"||_1f==="shapeY"){
_1d=_4.makeParameters(_4.defaultPattern,_1d);
_1d.space=_1f;
if(_1f==="plot"||_1f==="shapeX"){
_20=dim.height-_1e.t-_1e.b;
_1d.y=_1e.t+_20*_1d.y/100;
_1d.height=_20*_1d.height/100;
}
if(_1f==="plot"||_1f==="shapeY"){
_20=dim.width-_1e.l-_1e.r;
_1d.x=_1e.l+_20*_1d.x/100;
_1d.width=_20*_1d.width/100;
}
}
break;
}
return _1d;
},_shapeFill:function(_23,_24){
if(!_23||!_23.space){
return _23;
}
var _25=_23.space,_26;
switch(_23.type){
case "linear":
if(_25==="shape"||_25==="shapeX"||_25==="shapeY"){
_23=_4.makeParameters(_4.defaultLinearGradient,_23);
_23.space=_25;
if(_25==="shape"||_25==="shapeX"){
_26=_24.width;
_23.x1=_24.x+_26*_23.x1/100;
_23.x2=_24.x+_26*_23.x2/100;
}
if(_25==="shape"||_25==="shapeY"){
_26=_24.height;
_23.y1=_24.y+_26*_23.y1/100;
_23.y2=_24.y+_26*_23.y2/100;
}
}
break;
case "radial":
if(_25==="shape"){
_23=_4.makeParameters(_4.defaultRadialGradient,_23);
_23.space=_25;
_23.cx=_24.x+_24.width/2;
_23.cy=_24.y+_24.height/2;
_23.r=_23.r*_24.width/200;
}
break;
case "pattern":
if(_25==="shape"||_25==="shapeX"||_25==="shapeY"){
_23=_4.makeParameters(_4.defaultPattern,_23);
_23.space=_25;
if(_25==="shape"||_25==="shapeX"){
_26=_24.width;
_23.x=_24.x+_26*_23.x/100;
_23.width=_26*_23.width/100;
}
if(_25==="shape"||_25==="shapeY"){
_26=_24.height;
_23.y=_24.y+_26*_23.y/100;
_23.height=_26*_23.height/100;
}
}
break;
}
return _23;
},_pseudoRadialFill:function(_27,_28,_29,_2a,end){
if(!_27||_27.type!=="radial"||_27.space!=="shape"){
return _27;
}
var _2b=_27.space;
_27=_4.makeParameters(_4.defaultRadialGradient,_27);
_27.space=_2b;
if(arguments.length<4){
_27.cx=_28.x;
_27.cy=_28.y;
_27.r=_27.r*_29/100;
return _27;
}
var _2c=arguments.length<5?_2a:(end+_2a)/2;
return {type:"linear",x1:_28.x,y1:_28.y,x2:_28.x+_27.r*_29*Math.cos(_2c)/100,y2:_28.y+_27.r*_29*Math.sin(_2c)/100,colors:_27.colors};
}});
});
