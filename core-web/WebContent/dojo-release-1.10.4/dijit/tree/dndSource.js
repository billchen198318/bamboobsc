//>>built
define("dijit/tree/dndSource",["dojo/_base/array","dojo/_base/declare","dojo/dnd/common","dojo/dom-class","dojo/dom-geometry","dojo/_base/lang","dojo/on","dojo/touch","dojo/topic","dojo/dnd/Manager","./_dndSelector"],function(_1,_2,_3,_4,_5,_6,on,_7,_8,_9,_a){
var _b=_2("dijit.tree.dndSource",_a,{isSource:true,accept:["text","treeNode"],copyOnly:false,dragThreshold:5,betweenThreshold:0,generateText:true,constructor:function(_c,_d){
if(!_d){
_d={};
}
_6.mixin(this,_d);
var _e=_d.accept instanceof Array?_d.accept:["text","treeNode"];
this.accept=null;
if(_e.length){
this.accept={};
for(var i=0;i<_e.length;++i){
this.accept[_e[i]]=1;
}
}
this.isDragging=false;
this.mouseDown=false;
this.targetAnchor=null;
this.targetBox=null;
this.dropPosition="";
this._lastX=0;
this._lastY=0;
this.sourceState="";
if(this.isSource){
_4.add(this.node,"dojoDndSource");
}
this.targetState="";
if(this.accept){
_4.add(this.node,"dojoDndTarget");
}
this.topics=[_8.subscribe("/dnd/source/over",_6.hitch(this,"onDndSourceOver")),_8.subscribe("/dnd/start",_6.hitch(this,"onDndStart")),_8.subscribe("/dnd/drop",_6.hitch(this,"onDndDrop")),_8.subscribe("/dnd/cancel",_6.hitch(this,"onDndCancel"))];
},checkAcceptance:function(){
return true;
},copyState:function(_f){
return this.copyOnly||_f;
},destroy:function(){
this.inherited(arguments);
var h;
while(h=this.topics.pop()){
h.remove();
}
this.targetAnchor=null;
},_onDragMouse:function(e,_10){
var m=_9.manager(),_11=this.targetAnchor,_12=this.current,_13=this.dropPosition;
var _14="Over";
if(_12&&this.betweenThreshold>0){
if(!this.targetBox||_11!=_12){
this.targetBox=_5.position(_12.rowNode,true);
}
if((e.pageY-this.targetBox.y)<=this.betweenThreshold){
_14="Before";
}else{
if((e.pageY-this.targetBox.y)>=(this.targetBox.h-this.betweenThreshold)){
_14="After";
}
}
}
if(_10||_12!=_11||_14!=_13){
if(_11){
this._removeItemClass(_11.rowNode,_13);
}
if(_12){
this._addItemClass(_12.rowNode,_14);
}
if(!_12){
m.canDrop(false);
}else{
if(_12==this.tree.rootNode&&_14!="Over"){
m.canDrop(false);
}else{
var _15=false,_16=false;
if(m.source==this){
_16=(_14==="Over");
for(var _17 in this.selection){
var _18=this.selection[_17];
if(_18.item===_12.item){
_15=true;
break;
}
if(_18.getParent().id!==_12.id){
_16=false;
}
}
}
m.canDrop(!_15&&!_16&&!this._isParentChildDrop(m.source,_12.rowNode)&&this.checkItemAcceptance(_12.rowNode,m.source,_14.toLowerCase()));
}
}
this.targetAnchor=_12;
this.dropPosition=_14;
}
},onMouseMove:function(e){
if(this.isDragging&&this.targetState=="Disabled"){
return;
}
this.inherited(arguments);
var m=_9.manager();
if(this.isDragging){
this._onDragMouse(e);
}else{
if(this.mouseDown&&this.isSource&&(Math.abs(e.pageX-this._lastX)>=this.dragThreshold||Math.abs(e.pageY-this._lastY)>=this.dragThreshold)){
var _19=this.getSelectedTreeNodes();
if(_19.length){
if(_19.length>1){
var _1a=this.selection,i=0,r=[],n,p;
nextitem:
while((n=_19[i++])){
for(p=n.getParent();p&&p!==this.tree;p=p.getParent()){
if(_1a[p.id]){
continue nextitem;
}
}
r.push(n);
}
_19=r;
}
_19=_1.map(_19,function(n){
return n.domNode;
});
m.startDrag(this,_19,this.copyState(_3.getCopyKeyState(e)));
this._onDragMouse(e,true);
}
}
}
},onMouseDown:function(e){
this.mouseDown=true;
this.mouseButton=e.button;
this._lastX=e.pageX;
this._lastY=e.pageY;
this.inherited(arguments);
},onMouseUp:function(e){
if(this.mouseDown){
this.mouseDown=false;
this.inherited(arguments);
}
},onMouseOut:function(){
this.inherited(arguments);
this._unmarkTargetAnchor();
},checkItemAcceptance:function(){
return true;
},onDndSourceOver:function(_1b){
if(this!=_1b){
this.mouseDown=false;
this._unmarkTargetAnchor();
}else{
if(this.isDragging){
var m=_9.manager();
m.canDrop(false);
}
}
},onDndStart:function(_1c,_1d,_1e){
if(this.isSource){
this._changeState("Source",this==_1c?(_1e?"Copied":"Moved"):"");
}
var _1f=this.checkAcceptance(_1c,_1d);
this._changeState("Target",_1f?"":"Disabled");
if(this==_1c){
_9.manager().overSource(this);
}
this.isDragging=true;
},itemCreator:function(_20){
return _1.map(_20,function(_21){
return {"id":_21.id,"name":_21.textContent||_21.innerText||""};
});
},onDndDrop:function(_22,_23,_24){
if(this.containerState=="Over"){
var _25=this.tree,_26=_25.model,_27=this.targetAnchor;
this.isDragging=false;
var _28;
var _29;
var _2a;
_28=(_27&&_27.item)||_25.item;
if(this.dropPosition=="Before"||this.dropPosition=="After"){
_28=(_27.getParent()&&_27.getParent().item)||_25.item;
_29=_27.getIndexInParent();
if(this.dropPosition=="After"){
_29=_27.getIndexInParent()+1;
_2a=_27.getNextSibling()&&_27.getNextSibling().item;
}else{
_2a=_27.item;
}
}else{
_28=(_27&&_27.item)||_25.item;
}
var _2b;
_1.forEach(_23,function(_2c,idx){
var _2d=_22.getItem(_2c.id);
if(_1.indexOf(_2d.type,"treeNode")!=-1){
var _2e=_2d.data,_2f=_2e.item,_30=_2e.getParent().item;
}
if(_22==this){
if(typeof _29=="number"){
if(_28==_30&&_2e.getIndexInParent()<_29){
_29-=1;
}
}
_26.pasteItem(_2f,_30,_28,_24,_29,_2a);
}else{
if(_26.isItem(_2f)){
_26.pasteItem(_2f,_30,_28,_24,_29,_2a);
}else{
if(!_2b){
_2b=this.itemCreator(_23,_27.rowNode,_22);
}
_26.newItem(_2b[idx],_28,_29,_2a);
}
}
},this);
this.tree._expandNode(_27);
}
this.onDndCancel();
},onDndCancel:function(){
this._unmarkTargetAnchor();
this.isDragging=false;
this.mouseDown=false;
delete this.mouseButton;
this._changeState("Source","");
this._changeState("Target","");
},onOverEvent:function(){
this.inherited(arguments);
_9.manager().overSource(this);
},onOutEvent:function(){
this._unmarkTargetAnchor();
var m=_9.manager();
if(this.isDragging){
m.canDrop(false);
}
m.outSource(this);
this.inherited(arguments);
},_isParentChildDrop:function(_31,_32){
if(!_31.tree||_31.tree!=this.tree){
return false;
}
var _33=_31.tree.domNode;
var ids=_31.selection;
var _34=_32.parentNode;
while(_34!=_33&&!ids[_34.id]){
_34=_34.parentNode;
}
return _34.id&&ids[_34.id];
},_unmarkTargetAnchor:function(){
if(!this.targetAnchor){
return;
}
this._removeItemClass(this.targetAnchor.rowNode,this.dropPosition);
this.targetAnchor=null;
this.targetBox=null;
this.dropPosition=null;
},_markDndStatus:function(_35){
this._changeState("Source",_35?"Copied":"Moved");
}});
return _b;
});
