//>>built
define("dojox/charting/plot2d/Bars",["dojo/_base/lang","dojo/_base/array","dojo/_base/declare","dojo/has","./CartesianBase","./_PlotEvents","./common","dojox/gfx/fx","dojox/lang/utils","dojox/lang/functional","dojox/lang/functional/reversed"],function(_1,_2,_3,_4,_5,_6,dc,fx,du,df,_7){
var _8=_7.lambda("item.purgeGroup()");
return _3("dojox.charting.plot2d.Bars",[_5,_6],{defaultParams:{gap:0,animate:null,enableCache:false},optionalParams:{minBarSize:1,maxBarSize:1,stroke:{},outline:{},shadow:{},fill:{},filter:{},styleFunc:null,font:"",fontColor:""},constructor:function(_9,_a){
this.opt=_1.clone(_1.mixin(this.opt,this.defaultParams));
du.updateWithObject(this.opt,_a);
du.updateWithPattern(this.opt,_a,this.optionalParams);
this.animate=this.opt.animate;
this.renderingOptions={"shape-rendering":"crispEdges"};
},getSeriesStats:function(){
var _b=dc.collectSimpleStats(this.series),t;
_b.hmin-=0.5;
_b.hmax+=0.5;
t=_b.hmin,_b.hmin=_b.vmin,_b.vmin=t;
t=_b.hmax,_b.hmax=_b.vmax,_b.vmax=t;
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
},createLabel:function(_10,_11,_12,_13){
if(this.opt.labels&&this.opt.labelStyle=="outside"){
var y=_12.y+_12.height/2;
var x=_12.x+_12.width+this.opt.labelOffset;
this.renderLabel(_10,x,y,this._getLabel(isNaN(_11.y)?_11:_11.y),_13,"start");
}else{
this.inherited(arguments);
}
},render:function(dim,_14){
if(this.zoom&&!this.isDataDirty()){
return this.performZoom(dim,_14);
}
this.dirty=this.isDirty();
this.resetEvents();
var s;
if(this.dirty){
_2.forEach(this.series,_8);
this._eventSeries={};
this.cleanGroup();
s=this.getGroup();
df.forEachRev(this.series,function(_15){
_15.cleanGroup(s);
});
}
var t=this.chart.theme,ht=this._hScaler.scaler.getTransformerFromModel(this._hScaler),vt=this._vScaler.scaler.getTransformerFromModel(this._vScaler),_16=Math.max(0,this._hScaler.bounds.lower),_17=ht(_16),_18=this.events();
var bar=this.getBarProperties();
var _19=this.series.length;
_2.forEach(this.series,function(_1a){
if(_1a.hidden){
_19--;
}
});
var z=_19;
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
var _1b=t.next("bar",[this.opt,run]);
if(run.hidden){
run.dyn.fill=_1b.series.fill;
run.dyn.stroke=_1b.series.stroke;
continue;
}
z--;
var _1c=new Array(run.data.length);
s=run.group;
var _1d=_2.some(run.data,function(_1e){
return typeof _1e=="number"||(_1e&&!_1e.hasOwnProperty("x"));
});
var min=_1d?Math.max(0,Math.floor(this._vScaler.bounds.from-1)):0;
var max=_1d?Math.min(run.data.length,Math.ceil(this._vScaler.bounds.to)):run.data.length;
for(var j=min;j<max;++j){
var _1f=run.data[j];
if(_1f!=null){
var val=this.getValue(_1f,j,i,_1d),hv=ht(val.y),w=Math.abs(hv-_17),_20,_21;
if(this.opt.styleFunc||typeof _1f!="number"){
var _22=typeof _1f!="number"?[_1f]:[];
if(this.opt.styleFunc){
_22.push(this.opt.styleFunc(_1f));
}
_20=t.addMixin(_1b,"bar",_22,true);
}else{
_20=t.post(_1b,"bar");
}
if(w>=0&&bar.height>=1){
var _23={x:_14.l+(val.y<_16?hv:_17),y:dim.height-_14.b-vt(val.x+1.5)+bar.gap+bar.thickness*(_19-z-1),width:w,height:bar.height};
if(_20.series.shadow){
var _24=_1.clone(_23);
_24.x+=_20.series.shadow.dx;
_24.y+=_20.series.shadow.dy;
_21=this.createRect(run,s,_24).setFill(_20.series.shadow.color).setStroke(_20.series.shadow);
if(this.animate){
this._animateBar(_21,_14.l+_17,-w);
}
}
var _25=this._plotFill(_20.series.fill,dim,_14);
_25=this._shapeFill(_25,_23);
var _26=this.createRect(run,s,_23).setFill(_25).setStroke(_20.series.stroke);
if(_26.setFilter&&_20.series.filter){
_26.setFilter(_20.series.filter);
}
run.dyn.fill=_26.getFill();
run.dyn.stroke=_26.getStroke();
if(_18){
var o={element:"bar",index:j,run:run,shape:_26,shadow:_21,cx:val.y,cy:val.x+1.5,x:_1d?j:run.data[j].x,y:_1d?run.data[j]:run.data[j].y};
this._connectEvents(o);
_1c[j]=o;
}
if(!isNaN(val.py)&&val.py>_16){
_23.x+=ht(val.py);
_23.width-=ht(val.py);
}
this.createLabel(s,_1f,_23,_20);
if(this.animate){
this._animateBar(_26,_14.l+_17,-w);
}
}
}
}
this._eventSeries[run.name]=_1c;
run.dirty=false;
}
this.dirty=false;
if(_4("dojo-bidi")){
this._checkOrientation(this.group,dim,_14);
}
return this;
},getValue:function(_27,j,_28,_29){
var y,x;
if(_29){
if(typeof _27=="number"){
y=_27;
}else{
y=_27.y;
}
x=j;
}else{
y=_27.y;
x=_27.x-1;
}
return {y:y,x:x};
},getBarProperties:function(){
var f=dc.calculateBarSize(this._vScaler.bounds.scale,this.opt);
return {gap:f.gap,height:f.size,thickness:0};
},_animateBar:function(_2a,_2b,_2c){
if(_2c==0){
_2c=1;
}
fx.animateTransform(_1.delegate({shape:_2a,duration:1200,transform:[{name:"translate",start:[_2b-(_2b/_2c),0],end:[0,0]},{name:"scale",start:[1/_2c,1],end:[1,1]},{name:"original"}]},this.animate)).play();
}});
});
