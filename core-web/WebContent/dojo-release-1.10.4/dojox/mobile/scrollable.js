//>>built
define("dojox/mobile/scrollable",["dojo/_base/kernel","dojo/_base/connect","dojo/_base/event","dojo/_base/lang","dojo/_base/window","dojo/dom-class","dojo/dom-construct","dojo/dom-style","dojo/dom-geometry","dojo/touch","dijit/registry","dijit/form/_TextBoxMixin","./sniff","./_css3","./_maskUtils","dojo/_base/declare","dojo/has!dojo-bidi?dojox/mobile/bidi/Scrollable"],function(_1,_2,_3,_4,_5,_6,_7,_8,_9,_a,_b,_c,_d,_e,_f,_10,_11){
var dm=_4.getObject("dojox.mobile",true);
_d.add("translate3d",function(){
if(_d("css3-animations")){
var _12=_5.doc.createElement("div");
_12.style[_e.name("transform")]="translate3d(0px,1px,0px)";
_5.doc.documentElement.appendChild(_12);
var v=_5.doc.defaultView.getComputedStyle(_12,"")[_e.name("transform",true)];
var _13=v&&v.indexOf("matrix")===0;
_5.doc.documentElement.removeChild(_12);
return _13;
}
});
var _14=function(){
};
_4.extend(_14,{fixedHeaderHeight:0,fixedFooterHeight:0,isLocalFooter:false,scrollBar:true,scrollDir:"v",weight:0.6,fadeScrollBar:true,disableFlashScrollBar:false,threshold:4,constraint:true,touchNode:null,propagatable:true,dirLock:false,height:"",scrollType:0,_parentPadBorderExtentsBottom:0,_moved:false,init:function(_15){
if(_15){
for(var p in _15){
if(_15.hasOwnProperty(p)){
this[p]=((p=="domNode"||p=="containerNode")&&typeof _15[p]=="string")?_5.doc.getElementById(_15[p]):_15[p];
}
}
}
if(typeof this.domNode.style.msTouchAction!="undefined"){
this.domNode.style.msTouchAction="none";
}
this.touchNode=this.touchNode||this.containerNode;
this._v=(this.scrollDir.indexOf("v")!=-1);
this._h=(this.scrollDir.indexOf("h")!=-1);
this._f=(this.scrollDir=="f");
this._ch=[];
this._ch.push(_2.connect(this.touchNode,_a.press,this,"onTouchStart"));
if(_d("css3-animations")){
this._useTopLeft=this.scrollType?this.scrollType===2:false;
if(!this._useTopLeft){
this._useTransformTransition=this.scrollType?this.scrollType===3:_d("ios")>=6||_d("android")||_d("bb");
}
if(!this._useTopLeft){
if(this._useTransformTransition){
this._ch.push(_2.connect(this.containerNode,_e.name("transitionEnd"),this,"onFlickAnimationEnd"));
}else{
this._ch.push(_2.connect(this.containerNode,_e.name("animationEnd"),this,"onFlickAnimationEnd"));
this._ch.push(_2.connect(this.containerNode,_e.name("animationStart"),this,"onFlickAnimationStart"));
for(var i=0;i<3;i++){
this.setKeyframes(null,null,i);
}
}
if(_d("translate3d")){
_8.set(this.containerNode,_e.name("transform"),"translate3d(0,0,0)");
}
}else{
this._ch.push(_2.connect(this.containerNode,_e.name("transitionEnd"),this,"onFlickAnimationEnd"));
}
}
this._speed={x:0,y:0};
this._appFooterHeight=0;
if(this.isTopLevel()&&!this.noResize){
this.resize();
}
var _16=this;
setTimeout(function(){
_16.flashScrollBar();
},600);
if(_5.global.addEventListener){
this._onScroll=function(e){
if(!_16.domNode||_16.domNode.style.display==="none"){
return;
}
var _17=_16.domNode.scrollTop;
var _18=_16.domNode.scrollLeft;
var pos;
if(_17>0||_18>0){
pos=_16.getPos();
_16.domNode.scrollLeft=0;
_16.domNode.scrollTop=0;
_16.scrollTo({x:pos.x-_18,y:pos.y-_17});
}
};
_5.global.addEventListener("scroll",this._onScroll,true);
}
if(!this.disableTouchScroll&&this.domNode.addEventListener){
this._onFocusScroll=function(e){
if(!_16.domNode||_16.domNode.style.display==="none"){
return;
}
var _19=_5.doc.activeElement;
var _1a,_1b;
if(_19){
_1a=_19.getBoundingClientRect();
_1b=_16.domNode.getBoundingClientRect();
if(_1a.height<_16.getDim().d.h){
if(_1a.top<(_1b.top+_16.fixedHeaderHeight)){
_16.scrollIntoView(_19,true);
}else{
if((_1a.top+_1a.height)>(_1b.top+_1b.height-_16.fixedFooterHeight)){
_16.scrollIntoView(_19,false);
}
}
}
}
};
this.domNode.addEventListener("focus",this._onFocusScroll,true);
}
},isTopLevel:function(){
return true;
},cleanup:function(){
if(this._ch){
for(var i=0;i<this._ch.length;i++){
_2.disconnect(this._ch[i]);
}
this._ch=null;
}
if(this._onScroll&&_5.global.removeEventListener){
_5.global.removeEventListener("scroll",this._onScroll,true);
this._onScroll=null;
}
if(this._onFocusScroll&&this.domNode.removeEventListener){
this.domNode.removeEventListener("focus",this._onFocusScroll,true);
this._onFocusScroll=null;
}
},findDisp:function(_1c){
if(!_1c.parentNode){
return null;
}
if(_1c.nodeType===1&&_6.contains(_1c,"mblSwapView")&&_1c.style.display!=="none"){
return _1c;
}
var _1d=_1c.parentNode.childNodes;
for(var i=0;i<_1d.length;i++){
var n=_1d[i];
if(n.nodeType===1&&_6.contains(n,"mblView")&&n.style.display!=="none"){
return n;
}
}
return _1c;
},getScreenSize:function(){
return {h:_5.global.innerHeight||_5.doc.documentElement.clientHeight||_5.doc.documentElement.offsetHeight,w:_5.global.innerWidth||_5.doc.documentElement.clientWidth||_5.doc.documentElement.offsetWidth};
},resize:function(e){
this._appFooterHeight=(this._fixedAppFooter)?this._fixedAppFooter.offsetHeight:0;
if(this.isLocalHeader){
this.containerNode.style.marginTop=this.fixedHeaderHeight+"px";
}
var top=0;
for(var n=this.domNode;n&&n.tagName!="BODY";n=n.offsetParent){
n=this.findDisp(n);
if(!n){
break;
}
top+=n.offsetTop+_9.getBorderExtents(n).h;
}
var h,_1e=this.getScreenSize().h,dh=_1e-top-this._appFooterHeight;
if(this.height==="inherit"){
if(this.domNode.offsetParent){
h=_9.getContentBox(this.domNode.offsetParent).h-_9.getBorderExtents(this.domNode).h+"px";
}
}else{
if(this.height==="auto"){
var _1f=this.domNode.offsetParent;
if(_1f){
this.domNode.style.height="0px";
var _20=_1f.getBoundingClientRect(),_21=this.domNode.getBoundingClientRect(),_22=_20.bottom-this._appFooterHeight-this._parentPadBorderExtentsBottom;
if(_21.bottom>=_22){
dh=_1e-(_21.top-_20.top)-this._appFooterHeight-this._parentPadBorderExtentsBottom;
}else{
dh=_22-_21.bottom;
}
}
var _23=Math.max(this.domNode.scrollHeight,this.containerNode.scrollHeight);
h=(_23?Math.min(_23,dh):dh)+"px";
}else{
if(this.height){
h=this.height;
}
}
}
if(!h){
h=dh+"px";
}
if(h.charAt(0)!=="-"&&h!=="default"){
this.domNode.style.height=h;
}
if(!this._conn){
this.onTouchEnd();
}
},onFlickAnimationStart:function(e){
if(e){
_3.stop(e);
}
},onFlickAnimationEnd:function(e){
if(_d("ios")){
this._keepInputCaretInActiveElement();
}
if(e){
var an=e.animationName;
if(an&&an.indexOf("scrollableViewScroll2")===-1){
if(an.indexOf("scrollableViewScroll0")!==-1){
if(this._scrollBarNodeV){
_6.remove(this._scrollBarNodeV,"mblScrollableScrollTo0");
}
}else{
if(an.indexOf("scrollableViewScroll1")!==-1){
if(this._scrollBarNodeH){
_6.remove(this._scrollBarNodeH,"mblScrollableScrollTo1");
}
}else{
if(this._scrollBarNodeV){
this._scrollBarNodeV.className="";
}
if(this._scrollBarNodeH){
this._scrollBarNodeH.className="";
}
}
}
return;
}
if(this._useTransformTransition||this._useTopLeft){
var n=e.target;
if(n===this._scrollBarV||n===this._scrollBarH){
var cls="mblScrollableScrollTo"+(n===this._scrollBarV?"0":"1");
if(_6.contains(n,cls)){
_6.remove(n,cls);
}else{
n.className="";
}
return;
}
}
if(e.srcElement){
_3.stop(e);
}
}
this.stopAnimation();
if(this._bounce){
var _24=this;
var _25=_24._bounce;
setTimeout(function(){
_24.slideTo(_25,0.3,"ease-out");
},0);
_24._bounce=undefined;
}else{
this.hideScrollBar();
this.removeCover();
}
},isFormElement:function(_26){
if(_26&&_26.nodeType!==1){
_26=_26.parentNode;
}
if(!_26||_26.nodeType!==1){
return false;
}
var t=_26.tagName;
return (t==="SELECT"||t==="INPUT"||t==="TEXTAREA"||t==="BUTTON");
},onTouchStart:function(e){
if(this.disableTouchScroll){
return;
}
if(this._conn&&(new Date()).getTime()-this.startTime<500){
return;
}
if(!this._conn){
this._conn=[];
this._conn.push(_2.connect(_5.doc,_a.move,this,"onTouchMove"));
this._conn.push(_2.connect(_5.doc,_a.release,this,"onTouchEnd"));
}
this._aborted=false;
if(_6.contains(this.containerNode,"mblScrollableScrollTo2")){
this.abort();
}else{
if(this._scrollBarNodeV){
this._scrollBarNodeV.className="";
}
if(this._scrollBarNodeH){
this._scrollBarNodeH.className="";
}
}
this.touchStartX=e.touches?e.touches[0].pageX:e.clientX;
this.touchStartY=e.touches?e.touches[0].pageY:e.clientY;
this.startTime=(new Date()).getTime();
this.startPos=this.getPos();
this._dim=this.getDim();
this._time=[0];
this._posX=[this.touchStartX];
this._posY=[this.touchStartY];
this._locked=false;
this._moved=false;
this._preventDefaultInNextTouchMove=true;
if(!this.isFormElement(e.target)){
this.propagatable?e.preventDefault():_3.stop(e);
this._preventDefaultInNextTouchMove=false;
}
},onTouchMove:function(e){
if(this._locked){
return;
}
if(this._preventDefaultInNextTouchMove){
this._preventDefaultInNextTouchMove=false;
var _27=_b.getEnclosingWidget(((e.targetTouches&&e.targetTouches.length===1)?e.targetTouches[0]:e).target);
if(_27&&_27.isInstanceOf(_c)){
this.propagatable?e.preventDefault():_3.stop(e);
}
}
var x=e.touches?e.touches[0].pageX:e.clientX;
var y=e.touches?e.touches[0].pageY:e.clientY;
var dx=x-this.touchStartX;
var dy=y-this.touchStartY;
var to={x:this.startPos.x+dx,y:this.startPos.y+dy};
var dim=this._dim;
dx=Math.abs(dx);
dy=Math.abs(dy);
if(this._time.length==1){
if(this.dirLock){
if(this._v&&!this._h&&dx>=this.threshold&&dx>=dy||(this._h||this._f)&&!this._v&&dy>=this.threshold&&dy>=dx){
this._locked=true;
return;
}
}
if(this._v&&this._h){
if(dy<this.threshold&&dx<this.threshold){
return;
}
}else{
if(this._v&&dy<this.threshold||(this._h||this._f)&&dx<this.threshold){
return;
}
}
this._moved=true;
this.addCover();
this.showScrollBar();
}
var _28=this.weight;
if(this._v&&this.constraint){
if(to.y>0){
to.y=Math.round(to.y*_28);
}else{
if(to.y<-dim.o.h){
if(dim.c.h<dim.d.h){
to.y=Math.round(to.y*_28);
}else{
to.y=-dim.o.h-Math.round((-dim.o.h-to.y)*_28);
}
}
}
}
if((this._h||this._f)&&this.constraint){
if(to.x>0){
to.x=Math.round(to.x*_28);
}else{
if(to.x<-dim.o.w){
if(dim.c.w<dim.d.w){
to.x=Math.round(to.x*_28);
}else{
to.x=-dim.o.w-Math.round((-dim.o.w-to.x)*_28);
}
}
}
}
this.scrollTo(to);
var max=10;
var n=this._time.length;
if(n>=2){
this._moved=true;
var d0,d1;
if(this._v&&!this._h){
d0=this._posY[n-1]-this._posY[n-2];
d1=y-this._posY[n-1];
}else{
if(!this._v&&this._h){
d0=this._posX[n-1]-this._posX[n-2];
d1=x-this._posX[n-1];
}
}
if(d0*d1<0){
this._time=[this._time[n-1]];
this._posX=[this._posX[n-1]];
this._posY=[this._posY[n-1]];
n=1;
}
}
if(n==max){
this._time.shift();
this._posX.shift();
this._posY.shift();
}
this._time.push((new Date()).getTime()-this.startTime);
this._posX.push(x);
this._posY.push(y);
},_keepInputCaretInActiveElement:function(){
var _29=_5.doc.activeElement;
var _2a;
if(_29&&(_29.tagName=="INPUT"||_29.tagName=="TEXTAREA")){
_2a=_29.value;
if(_29.type=="number"||_29.type=="week"){
if(_2a){
_29.value=_29.value+1;
}else{
_29.value=(_29.type=="week")?"2013-W10":1;
}
_29.value=_2a;
}else{
_29.value=_29.value+" ";
_29.value=_2a;
}
}
},onTouchEnd:function(e){
if(this._locked){
return;
}
var _2b=this._speed={x:0,y:0};
var dim=this._dim;
var pos=this.getPos();
var to={};
if(e){
if(!this._conn){
return;
}
for(var i=0;i<this._conn.length;i++){
_2.disconnect(this._conn[i]);
}
this._conn=null;
var _2c=false;
if(!this._aborted&&!this._moved){
_2c=true;
}
if(_2c){
this.hideScrollBar();
this.removeCover();
if(_d("touch")&&_d("clicks-prevented")&&!this.isFormElement(e.target)){
var _2d=e.target;
if(_2d.nodeType!=1){
_2d=_2d.parentNode;
}
setTimeout(function(){
dm._sendClick(_2d,e);
});
}
return;
}
_2b=this._speed=this.getSpeed();
}else{
if(pos.x==0&&pos.y==0){
return;
}
dim=this.getDim();
}
if(this._v){
to.y=pos.y+_2b.y;
}
if(this._h||this._f){
to.x=pos.x+_2b.x;
}
if(this.adjustDestination(to,pos,dim)===false){
return;
}
if(this.constraint){
if(this.scrollDir=="v"&&dim.c.h<dim.d.h){
this.slideTo({y:0},0.3,"ease-out");
return;
}else{
if(this.scrollDir=="h"&&dim.c.w<dim.d.w){
this.slideTo({x:0},0.3,"ease-out");
return;
}else{
if(this._v&&this._h&&dim.c.h<dim.d.h&&dim.c.w<dim.d.w){
this.slideTo({x:0,y:0},0.3,"ease-out");
return;
}
}
}
}
var _2e,_2f="ease-out";
var _30={};
if(this._v&&this.constraint){
if(to.y>0){
if(pos.y>0){
_2e=0.3;
to.y=0;
}else{
to.y=Math.min(to.y,20);
_2f="linear";
_30.y=0;
}
}else{
if(-_2b.y>dim.o.h-(-pos.y)){
if(pos.y<-dim.o.h){
_2e=0.3;
to.y=dim.c.h<=dim.d.h?0:-dim.o.h;
}else{
to.y=Math.max(to.y,-dim.o.h-20);
_2f="linear";
_30.y=-dim.o.h;
}
}
}
}
if((this._h||this._f)&&this.constraint){
if(to.x>0){
if(pos.x>0){
_2e=0.3;
to.x=0;
}else{
to.x=Math.min(to.x,20);
_2f="linear";
_30.x=0;
}
}else{
if(-_2b.x>dim.o.w-(-pos.x)){
if(pos.x<-dim.o.w){
_2e=0.3;
to.x=dim.c.w<=dim.d.w?0:-dim.o.w;
}else{
to.x=Math.max(to.x,-dim.o.w-20);
_2f="linear";
_30.x=-dim.o.w;
}
}
}
}
this._bounce=(_30.x!==undefined||_30.y!==undefined)?_30:undefined;
if(_2e===undefined){
var _31,_32;
if(this._v&&this._h){
_32=Math.sqrt(_2b.x*_2b.x+_2b.y*_2b.y);
_31=Math.sqrt(Math.pow(to.y-pos.y,2)+Math.pow(to.x-pos.x,2));
}else{
if(this._v){
_32=_2b.y;
_31=to.y-pos.y;
}else{
if(this._h){
_32=_2b.x;
_31=to.x-pos.x;
}
}
}
if(_31===0&&!e){
return;
}
_2e=_32!==0?Math.abs(_31/_32):0.01;
}
this.slideTo(to,_2e,_2f);
},adjustDestination:function(to,pos,dim){
return true;
},abort:function(){
this._aborted=true;
this.scrollTo(this.getPos());
this.stopAnimation();
},stopAnimation:function(){
_6.remove(this.containerNode,"mblScrollableScrollTo2");
if(this._scrollBarV){
this._scrollBarV.className="";
}
if(this._scrollBarH){
this._scrollBarH.className="";
}
if(this._useTransformTransition||this._useTopLeft){
this.containerNode.style[_e.name("transition")]="";
if(this._scrollBarV){
this._scrollBarV.style[_e.name("transition")]="";
}
if(this._scrollBarH){
this._scrollBarH.style[_e.name("transition")]="";
}
}
},scrollIntoView:function(_33,_34,_35){
if(!this._v){
return;
}
var c=this.containerNode,h=this.getDim().d.h,top=0;
for(var n=_33;n!==c;n=n.offsetParent){
if(!n||n.tagName==="BODY"){
return;
}
top+=n.offsetTop;
}
var y=_34?Math.max(h-c.offsetHeight,-top):Math.min(0,h-top-_33.offsetHeight);
(_35&&typeof _35==="number")?this.slideTo({y:y},_35,"ease-out"):this.scrollTo({y:y});
},getSpeed:function(){
var x=0,y=0,n=this._time.length;
if(n>=2&&(new Date()).getTime()-this.startTime-this._time[n-1]<500){
var dy=this._posY[n-(n>3?2:1)]-this._posY[(n-6)>=0?n-6:0];
var dx=this._posX[n-(n>3?2:1)]-this._posX[(n-6)>=0?n-6:0];
var dt=this._time[n-(n>3?2:1)]-this._time[(n-6)>=0?n-6:0];
y=this.calcSpeed(dy,dt);
x=this.calcSpeed(dx,dt);
}
return {x:x,y:y};
},calcSpeed:function(_36,_37){
return Math.round(_36/_37*100)*4;
},scrollTo:function(to,_38,_39){
var _3a,_3b,_3c;
var _3d=true;
if(!this._aborted&&this._conn){
if(!this._dim){
this._dim=this.getDim();
}
_3b=(to.y>0)?to.y:0;
_3c=(this._dim.o.h+to.y<0)?-1*(this._dim.o.h+to.y):0;
_3a={bubbles:false,cancelable:false,x:to.x,y:to.y,beforeTop:_3b>0,beforeTopHeight:_3b,afterBottom:_3c>0,afterBottomHeight:_3c};
_3d=this.onBeforeScroll(_3a);
}
if(_3d){
var s=(_39||this.containerNode).style;
if(_d("css3-animations")){
if(!this._useTopLeft){
if(this._useTransformTransition){
s[_e.name("transition")]="";
}
s[_e.name("transform")]=this.makeTranslateStr(to);
}else{
s[_e.name("transition")]="";
if(this._v){
s.top=to.y+"px";
}
if(this._h||this._f){
s.left=to.x+"px";
}
}
}else{
if(this._v){
s.top=to.y+"px";
}
if(this._h||this._f){
s.left=to.x+"px";
}
}
if(_d("ios")){
this._keepInputCaretInActiveElement();
}
if(!_38){
this.scrollScrollBarTo(this.calcScrollBarPos(to));
}
if(_3a){
this.onAfterScroll(_3a);
}
}
},onBeforeScroll:function(e){
return true;
},onAfterScroll:function(e){
},slideTo:function(to,_3e,_3f){
this._runSlideAnimation(this.getPos(),to,_3e,_3f,this.containerNode,2);
this.slideScrollBarTo(to,_3e,_3f);
},makeTranslateStr:function(to){
var y=this._v&&typeof to.y=="number"?to.y+"px":"0px";
var x=(this._h||this._f)&&typeof to.x=="number"?to.x+"px":"0px";
return _d("translate3d")?"translate3d("+x+","+y+",0px)":"translate("+x+","+y+")";
},getPos:function(){
if(_d("css3-animations")){
var s=_5.doc.defaultView.getComputedStyle(this.containerNode,"");
if(!this._useTopLeft){
var m=s[_e.name("transform")];
if(m&&m.indexOf("matrix")===0){
var arr=m.split(/[,\s\)]+/);
var i=m.indexOf("matrix3d")===0?12:4;
return {y:arr[i+1]-0,x:arr[i]-0};
}
return {x:0,y:0};
}else{
return {x:parseInt(s.left)||0,y:parseInt(s.top)||0};
}
}else{
var y=parseInt(this.containerNode.style.top)||0;
return {y:y,x:this.containerNode.offsetLeft};
}
},getDim:function(){
var d={};
d.c={h:this.containerNode.offsetHeight,w:this.containerNode.offsetWidth};
d.v={h:this.domNode.offsetHeight+this._appFooterHeight,w:this.domNode.offsetWidth};
d.d={h:d.v.h-this.fixedHeaderHeight-this.fixedFooterHeight-this._appFooterHeight,w:d.v.w};
d.o={h:d.c.h-d.v.h+this.fixedHeaderHeight+this.fixedFooterHeight+this._appFooterHeight,w:d.c.w-d.v.w};
return d;
},showScrollBar:function(){
if(!this.scrollBar){
return;
}
var dim=this._dim;
if(this.scrollDir=="v"&&dim.c.h<=dim.d.h){
return;
}
if(this.scrollDir=="h"&&dim.c.w<=dim.d.w){
return;
}
if(this._v&&this._h&&dim.c.h<=dim.d.h&&dim.c.w<=dim.d.w){
return;
}
var _40=function(_41,dir){
var bar=_41["_scrollBarNode"+dir];
if(!bar){
var _42=_7.create("div",null,_41.domNode);
var _43={position:"absolute",overflow:"hidden"};
if(dir=="V"){
_43.right="2px";
_43.width="5px";
}else{
_43.bottom=(_41.isLocalFooter?_41.fixedFooterHeight:0)+2+"px";
_43.height="5px";
}
_8.set(_42,_43);
_42.className="mblScrollBarWrapper";
_41["_scrollBarWrapper"+dir]=_42;
bar=_7.create("div",null,_42);
_8.set(bar,_e.add({opacity:0.6,position:"absolute",backgroundColor:"#606060",fontSize:"1px",MozBorderRadius:"2px",zIndex:2147483647},{borderRadius:"2px",transformOrigin:"0 0"}));
_8.set(bar,dir=="V"?{width:"5px"}:{height:"5px"});
_41["_scrollBarNode"+dir]=bar;
}
return bar;
};
if(this._v&&!this._scrollBarV){
this._scrollBarV=_40(this,"V");
}
if(this._h&&!this._scrollBarH){
this._scrollBarH=_40(this,"H");
}
this.resetScrollBar();
},hideScrollBar:function(){
if(this.fadeScrollBar&&_d("css3-animations")){
if(!dm._fadeRule){
var _44=_7.create("style",null,_5.doc.getElementsByTagName("head")[0]);
_44.textContent=".mblScrollableFadeScrollBar{"+"  "+_e.name("animation-duration",true)+": 1s;"+"  "+_e.name("animation-name",true)+": scrollableViewFadeScrollBar;}"+"@"+_e.name("keyframes",true)+" scrollableViewFadeScrollBar{"+"  from { opacity: 0.6; }"+"  to { opacity: 0; }}";
dm._fadeRule=_44.sheet.cssRules[1];
}
}
if(!this.scrollBar){
return;
}
var f=function(bar,_45){
_8.set(bar,_e.add({opacity:0},{animationDuration:""}));
if(!(_45._useTopLeft&&_d("android"))){
bar.className="mblScrollableFadeScrollBar";
}
};
if(this._scrollBarV){
f(this._scrollBarV,this);
this._scrollBarV=null;
}
if(this._scrollBarH){
f(this._scrollBarH,this);
this._scrollBarH=null;
}
},calcScrollBarPos:function(to){
var pos={};
var dim=this._dim;
var f=function(_46,_47,t,d,c){
var y=Math.round((d-_47-8)/(d-c)*t);
if(y<-_47+5){
y=-_47+5;
}
if(y>_46-5){
y=_46-5;
}
return y;
};
if(typeof to.y=="number"&&this._scrollBarV){
pos.y=f(this._scrollBarWrapperV.offsetHeight,this._scrollBarV.offsetHeight,to.y,dim.d.h,dim.c.h);
}
if(typeof to.x=="number"&&this._scrollBarH){
pos.x=f(this._scrollBarWrapperH.offsetWidth,this._scrollBarH.offsetWidth,to.x,dim.d.w,dim.c.w);
}
return pos;
},scrollScrollBarTo:function(to){
if(!this.scrollBar){
return;
}
if(this._v&&this._scrollBarV&&typeof to.y=="number"){
if(_d("css3-animations")){
if(!this._useTopLeft){
if(this._useTransformTransition){
this._scrollBarV.style[_e.name("transition")]="";
}
this._scrollBarV.style[_e.name("transform")]=this.makeTranslateStr({y:to.y});
}else{
_8.set(this._scrollBarV,_e.add({top:to.y+"px"},{transition:""}));
}
}else{
this._scrollBarV.style.top=to.y+"px";
}
}
if(this._h&&this._scrollBarH&&typeof to.x=="number"){
if(_d("css3-animations")){
if(!this._useTopLeft){
if(this._useTransformTransition){
this._scrollBarH.style[_e.name("transition")]="";
}
this._scrollBarH.style[_e.name("transform")]=this.makeTranslateStr({x:to.x});
}else{
_8.set(this._scrollBarH,_e.add({left:to.x+"px"},{transition:""}));
}
}else{
this._scrollBarH.style.left=to.x+"px";
}
}
},slideScrollBarTo:function(to,_48,_49){
if(!this.scrollBar){
return;
}
var _4a=this.calcScrollBarPos(this.getPos());
var _4b=this.calcScrollBarPos(to);
if(this._v&&this._scrollBarV){
this._runSlideAnimation({y:_4a.y},{y:_4b.y},_48,_49,this._scrollBarV,0);
}
if(this._h&&this._scrollBarH){
this._runSlideAnimation({x:_4a.x},{x:_4b.x},_48,_49,this._scrollBarH,1);
}
},_runSlideAnimation:function(_4c,to,_4d,_4e,_4f,idx){
if(_d("css3-animations")){
if(!this._useTopLeft){
if(this._useTransformTransition){
if(to.x===undefined){
to.x=_4c.x;
}
if(to.y===undefined){
to.y=_4c.y;
}
if(to.x!==_4c.x||to.y!==_4c.y){
this.onFlickAnimationStart();
_8.set(_4f,_e.add({},{transitionProperty:_e.name("transform"),transitionDuration:_4d+"s",transitionTimingFunction:_4e}));
var t=this.makeTranslateStr(to);
setTimeout(function(){
_8.set(_4f,_e.add({},{transform:t}));
},0);
_6.add(_4f,"mblScrollableScrollTo"+idx);
}else{
this.hideScrollBar();
this.removeCover();
}
}else{
this.setKeyframes(_4c,to,idx);
_8.set(_4f,_e.add({},{animationDuration:_4d+"s",animationTimingFunction:_4e}));
_6.add(_4f,"mblScrollableScrollTo"+idx);
if(idx==2){
this.scrollTo(to,true,_4f);
}else{
this.scrollScrollBarTo(to);
}
}
}else{
if(to.x!==undefined||to.y!==undefined){
this.onFlickAnimationStart();
_8.set(_4f,_e.add({},{transitionProperty:(to.x!==undefined&&to.y!==undefined)?"top, left":to.y!==undefined?"top":"left",transitionDuration:_4d+"s",transitionTimingFunction:_4e}));
setTimeout(function(){
var _50={};
if(to.x!==undefined){
_50.left=to.x+"px";
}
if(to.y!==undefined){
_50.top=to.y+"px";
}
_8.set(_4f,_50);
},0);
_6.add(_4f,"mblScrollableScrollTo"+idx);
}
}
}else{
if(_1.fx&&_1.fx.easing&&_4d){
var _51=this;
var s=_1.fx.slideTo({node:_4f,duration:_4d*1000,left:to.x,top:to.y,easing:(_4e=="ease-out")?_1.fx.easing.quadOut:_1.fx.easing.linear,onBegin:function(){
if(idx==2){
_51.onFlickAnimationStart();
}
},onEnd:function(){
if(idx==2){
_51.onFlickAnimationEnd();
}
}}).play();
}else{
if(idx==2){
this.onFlickAnimationStart();
this.scrollTo(to,false,_4f);
this.onFlickAnimationEnd();
}else{
this.scrollScrollBarTo(to);
}
}
}
},resetScrollBar:function(){
var f=function(_52,bar,d,c,hd,v){
if(!bar){
return;
}
var _53={};
_53[v?"top":"left"]=hd+4+"px";
var t=(d-8)<=0?1:d-8;
_53[v?"height":"width"]=t+"px";
_8.set(_52,_53);
var l=Math.round(d*d/c);
l=Math.min(Math.max(l-8,5),t);
bar.style[v?"height":"width"]=l+"px";
_8.set(bar,{"opacity":0.6});
};
var dim=this.getDim();
f(this._scrollBarWrapperV,this._scrollBarV,dim.d.h,dim.c.h,this.fixedHeaderHeight,true);
f(this._scrollBarWrapperH,this._scrollBarH,dim.d.w,dim.c.w,0);
this.createMask();
},createMask:function(){
if(!(_d("mask-image"))){
return;
}
if(this._scrollBarWrapperV){
var h=this._scrollBarWrapperV.offsetHeight;
_f.createRoundMask(this._scrollBarWrapperV,0,0,0,0,5,h,2,2,0.5);
}
if(this._scrollBarWrapperH){
var w=this._scrollBarWrapperH.offsetWidth;
_f.createRoundMask(this._scrollBarWrapperH,0,0,0,0,w,5,2,2,0.5);
}
},flashScrollBar:function(){
if(this.disableFlashScrollBar||!this.domNode){
return;
}
this._dim=this.getDim();
if(this._dim.d.h<=0){
return;
}
this.showScrollBar();
var _54=this;
setTimeout(function(){
_54.hideScrollBar();
},300);
},addCover:function(){
if(!_d("touch")&&!this.noCover){
if(!dm._cover){
dm._cover=_7.create("div",null,_5.doc.body);
dm._cover.className="mblScrollableCover";
_8.set(dm._cover,{backgroundColor:"#ffff00",opacity:0,position:"absolute",top:"0px",left:"0px",width:"100%",height:"100%",zIndex:2147483647});
this._ch.push(_2.connect(dm._cover,_a.press,this,"onTouchEnd"));
}else{
dm._cover.style.display="";
}
this.setSelectable(dm._cover,false);
this.setSelectable(this.domNode,false);
}
},removeCover:function(){
if(!_d("touch")&&dm._cover){
dm._cover.style.display="none";
this.setSelectable(dm._cover,true);
this.setSelectable(this.domNode,true);
}
},setKeyframes:function(_55,to,idx){
if(!dm._rule){
dm._rule=[];
}
if(!dm._rule[idx]){
var _56=_7.create("style",null,_5.doc.getElementsByTagName("head")[0]);
_56.textContent=".mblScrollableScrollTo"+idx+"{"+_e.name("animation-name",true)+": scrollableViewScroll"+idx+";}"+"@"+_e.name("keyframes",true)+" scrollableViewScroll"+idx+"{}";
dm._rule[idx]=_56.sheet.cssRules[1];
}
var _57=dm._rule[idx];
if(_57){
if(_55){
_57.deleteRule(_d("webkit")?"from":0);
(_57.insertRule||_57.appendRule).call(_57,"from { "+_e.name("transform",true)+": "+this.makeTranslateStr(_55)+"; }");
}
if(to){
if(to.x===undefined){
to.x=_55.x;
}
if(to.y===undefined){
to.y=_55.y;
}
_57.deleteRule(_d("webkit")?"to":1);
(_57.insertRule||_57.appendRule).call(_57,"to { "+_e.name("transform",true)+": "+this.makeTranslateStr(to)+"; }");
}
}
},setSelectable:function(_58,_59){
_58.style.KhtmlUserSelect=_59?"auto":"none";
_58.style.MozUserSelect=_59?"":"none";
_58.onselectstart=_59?null:function(){
return false;
};
if(_d("ie")){
_58.unselectable=_59?"":"on";
var _5a=_58.getElementsByTagName("*");
for(var i=0;i<_5a.length;i++){
_5a[i].unselectable=_59?"":"on";
}
}
}});
_14=_d("dojo-bidi")?_10("dojox.mobile.Scrollable",[_14,_11]):_14;
_4.setObject("dojox.mobile.scrollable",_14);
return _14;
});
