//>>built
define("dojox/charting/plot2d/Columns",["dojo/_base/lang","dojo/_base/array","dojo/_base/declare","dojo/has","./CartesianBase","./_PlotEvents","./common","dojox/lang/functional","dojox/lang/functional/reversed","dojox/lang/utils","dojox/gfx/fx"],function(_1,_2,_3,_4,_5,_6,dc,df,_7,du,fx){
var _8=_7.lambda("item.purgeGroup()");
return _3("dojox.charting.plot2d.Columns",[_5,_6],{defaultParams:{gap:0,animate:null,enableCache:false},optionalParams:{minBarSize:1,maxBarSize:1,stroke:{},outline:{},shadow:{},fill:{},filter:{},styleFunc:null,font:"",fontColor:""},constructor:function(_9,_a){
this.opt=_1.clone(_1.mixin(this.opt,this.defaultParams));
du.updateWithObject(this.opt,_a);
du.updateWithPattern(this.opt,_a,this.optionalParams);
this.animate=this.opt.animate;
this.renderingOptions={"shape-rendering":"crispEdges"};
},getSeriesStats:function(){
var _b=dc.collectSimpleStats(this.series);
_b.hmin-=0.5;
_b.hmax+=0.5;
return _b;
},createRect:function(_c,_d,_e){
var _f;
if(this.opt.enableCache&&_c._rectFreePool.length>0){
_f=_c._rectFreePool.pop();
_f.setShape(_e);
_d.add(_f);
}else{
_f=_d.createRect(_e);
}
if(this.opt.enableCache){
_c._rectUsePool.push(_f);
}
return _f;
},render:function(dim,_10){
if(this.zoom&&!this.isDataDirty()){
return this.performZoom(dim,_10);
}
this.resetEvents();
this.dirty=this.isDirty();
var s;
if(this.dirty){
_2.forEach(this.series,_8);
this._eventSeries={};
this.cleanGroup();
s=this.getGroup();
df.forEachRev(this.series,function(_11){
_11.cleanGroup(s);
});
}
var t=this.chart.theme,ht=this._hScaler.scaler.getTransformerFromModel(this._hScaler),vt=this._vScaler.scaler.getTransformerFromModel(this._vScaler),_12=Math.max(0,this._vScaler.bounds.lower),_13=vt(_12),_14=this.events(),bar=this.getBarProperties();
var z=this.series.length;
_2.forEach(this.series,function(_15){
if(_15.hidden){
z--;
}
});
for(var i=this.series.length-1;i>=0;--i){
var run=this.series[i];
if(!this.dirty&&!run.dirty){
t.skip();
this._reconnectEvents(run.name);
continue;
}
run.cleanGroup();
if(this.opt.enableCache){
run._rectFreePool=(run._rectFreePool?run._rectFreePool:[]).concat(run._rectUsePool?run._rectUsePool:[]);
run._rectUsePool=[];
}
var _16=t.next("column",[this.opt,run]),_17=new Array(run.data.length);
if(run.hidden){
run.dyn.fill=_16.series.fill;
continue;
}
z--;
s=run.group;
var _18=_2.some(run.data,function(_19){
return typeof _19=="number"||(_19&&!_19.hasOwnProperty("x"));
});
var min=_18?Math.max(0,Math.floor(this._hScaler.bounds.from-1)):0;
var max=_18?Math.min(run.data.length,Math.ceil(this._hScaler.bounds.to)):run.data.length;
for(var j=min;j<max;++j){
var _1a=run.data[j];
if(_1a!=null){
var val=this.getValue(_1a,j,i,_18),vv=vt(val.y),h=Math.abs(vv-_13),_1b,_1c;
if(this.opt.styleFunc||typeof _1a!="number"){
var _1d=typeof _1a!="number"?[_1a]:[];
if(this.opt.styleFunc){
_1d.push(this.opt.styleFunc(_1a));
}
_1b=t.addMixin(_16,"column",_1d,true);
}else{
_1b=t.post(_16,"column");
}
if(bar.width>=1&&h>=0){
var _1e={x:_10.l+ht(val.x+0.5)+bar.gap+bar.thickness*z,y:dim.height-_10.b-(val.y>_12?vv:_13),width:bar.width,height:h};
if(_1b.series.shadow){
var _1f=_1.clone(_1e);
_1f.x+=_1b.series.shadow.dx;
_1f.y+=_1b.series.shadow.dy;
_1c=this.createRect(run,s,_1f).setFill(_1b.series.shadow.color).setStroke(_1b.series.shadow);
if(this.animate){
this._animateColumn(_1c,dim.height-_10.b+_13,h);
}
}
var _20=this._plotFill(_1b.series.fill,dim,_10);
_20=this._shapeFill(_20,_1e);
var _21=this.createRect(run,s,_1e).setFill(_20).setStroke(_1b.series.stroke);
if(_21.setFilter&&_1b.series.filter){
_21.setFilter(_1b.series.filter);
}
run.dyn.fill=_21.getFill();
run.dyn.stroke=_21.getStroke();
if(_14){
var o={element:"column",index:j,run:run,shape:_21,shadow:_1c,cx:val.x+0.5,cy:val.y,x:_18?j:run.data[j].x,y:_18?run.data[j]:run.data[j].y};
this._connectEvents(o);
_17[j]=o;
}
if(!isNaN(val.py)&&val.py>_12){
_1e.height=vv-vt(val.py);
}
this.createLabel(s,_1a,_1e,_1b);
if(this.animate){
this._animateColumn(_21,dim.height-_10.b-_13,h);
}
}
}
}
this._eventSeries[run.name]=_17;
run.dirty=false;
}
this.dirty=false;
if(_4("dojo-bidi")){
this._checkOrientation(this.group,dim,_10);
}
return this;
},getValue:function(_22,j,_23,_24){
var y,x;
if(_24){
if(typeof _22=="number"){
y=_22;
}else{
y=_22.y;
}
x=j;
}else{
y=_22.y;
x=_22.x-1;
}
return {x:x,y:y};
},getBarProperties:function(){
var f=dc.calculateBarSize(this._hScaler.bounds.scale,this.opt);
return {gap:f.gap,width:f.size,thickness:0};
},_animateColumn:function(_25,_26,_27){
if(_27==0){
_27=1;
}
fx.animateTransform(_1.delegate({shape:_25,duration:1200,transform:[{name:"translate",start:[0,_26-(_26/_27)],end:[0,0]},{name:"scale",start:[1,1/_27],end:[1,1]},{name:"original"}]},this.animate)).play();
}});
});
