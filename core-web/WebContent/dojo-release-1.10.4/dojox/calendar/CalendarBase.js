//>>built
define("dojox/calendar/CalendarBase",["dojo/_base/declare","dojo/_base/sniff","dojo/_base/event","dojo/_base/lang","dojo/_base/array","dojo/cldr/supplemental","dojo/dom","dojo/dom-class","dojo/dom-style","dojo/dom-construct","dojo/dom-geometry","dojo/date","dojo/date/locale","dojo/_base/fx","dojo/fx","dojo/on","dijit/_WidgetBase","dijit/_TemplatedMixin","dijit/_WidgetsInTemplateMixin","./StoreMixin","./StoreManager","dojox/widget/_Invalidating","dojox/widget/Selection","dojox/calendar/time","dojo/i18n!./nls/buttons"],function(_1,_2,_3,_4,_5,_6,_7,_8,_9,_a,_b,_c,_d,_e,fx,on,_f,_10,_11,_12,_13,_14,_15,_16,_17){
return _1("dojox.calendar.CalendarBase",[_f,_10,_11,_12,_14,_15],{baseClass:"dojoxCalendar",datePackage:_c,startDate:null,endDate:null,date:null,minDate:null,maxDate:null,dateInterval:"week",dateIntervalSteps:1,viewContainer:null,firstDayOfWeek:-1,formatItemTimeFunc:null,editable:true,moveEnabled:true,resizeEnabled:true,columnView:null,matrixView:null,columnViewProps:null,matrixViewProps:null,createOnGridClick:false,createItemFunc:null,currentView:null,_currentViewIndex:-1,views:null,_calendar:"gregorian",constructor:function(_18){
this.views=[];
this.invalidatingProperties=["store","items","startDate","endDate","views","date","minDate","maxDate","dateInterval","dateIntervalSteps","firstDayOfWeek"];
_18=_18||{};
this._calendar=_18.datePackage?_18.datePackage.substr(_18.datePackage.lastIndexOf(".")+1):this._calendar;
this.dateModule=_18.datePackage?_4.getObject(_18.datePackage,false):_c;
this.dateClassObj=this.dateModule.Date||Date;
this.dateLocaleModule=_18.datePackage?_4.getObject(_18.datePackage+".locale",false):_d;
this.invalidateRendering();
this.storeManager=new _13({owner:this,_ownerItemsProperty:"items"});
this.storeManager.on("layoutInvalidated",_4.hitch(this,this._refreshItemsRendering));
this.storeManager.on("dataLoaded",_4.hitch(this,function(_19){
this.set("items",_19);
}));
this.decorationStoreManager=new _13({owner:this,_ownerItemsProperty:"decorationItems"});
this.decorationStoreManager.on("layoutInvalidated",_4.hitch(this,this._refreshDecorationItemsRendering));
this.decorationStoreManager.on("dataLoaded",_4.hitch(this,function(_1a){
this.set("decorationItems",_1a);
}));
},buildRendering:function(){
this.inherited(arguments);
if(this.views==null||this.views.length==0){
this.set("views",this._createDefaultViews());
}
},_applyAttributes:function(){
this._applyAttr=true;
this.inherited(arguments);
delete this._applyAttr;
},_setStartDateAttr:function(_1b){
this._set("startDate",_1b);
this._timeRangeInvalidated=true;
this._startDateChanged=true;
},_setEndDateAttr:function(_1c){
this._set("endDate",_1c);
this._timeRangeInvalidated=true;
this._endDateChanged=true;
},_setDateAttr:function(_1d){
this._set("date",_1d);
this._timeRangeInvalidated=true;
this._dateChanged=true;
},_setDateIntervalAttr:function(_1e){
this._set("dateInterval",_1e);
this._timeRangeInvalidated=true;
},_setDateIntervalStepsAttr:function(_1f){
this._set("dateIntervalSteps",_1f);
this._timeRangeInvalidated=true;
},_setFirstDayOfWeekAttr:function(_20){
this._set("firstDayOfWeek",_20);
if(this.get("date")!=null&&this.get("dateInterval")=="week"){
this._timeRangeInvalidated=true;
}
},_setTextDirAttr:function(_21){
_5.forEach(this.views,function(_22){
_22.set("textDir",_21);
});
},refreshRendering:function(){
this.inherited(arguments);
this._validateProperties();
},_refreshItemsRendering:function(){
if(this.currentView){
this.currentView._refreshItemsRendering();
}
},_refreshDecorationItemsRendering:function(){
if(this.currentView){
this.currentView._refreshDecorationItemsRendering();
}
},resize:function(_23){
if(_23){
_b.setMarginBox(this.domNode,_23);
}
if(this.currentView){
this.currentView.resize();
}
},_validateProperties:function(){
var cal=this.dateModule;
var _24=this.get("startDate");
var _25=this.get("endDate");
var _26=this.get("date");
if(this.firstDayOfWeek<-1||this.firstDayOfWeek>6){
this._set("firstDayOfWeek",0);
}
var _27=this.get("minDate");
var _28=this.get("maxDate");
if(_27&&_28){
if(cal.compare(_27,_28)>0){
var t=_27;
this._set("minDate",_28);
this._set("maxDate",t);
}
}
if(_26==null&&(_24!=null||_25!=null)){
if(_24==null){
_24=new this.dateClassObj();
this._set("startDate",_24);
this._timeRangeInvalidated=true;
}
if(_25==null){
_25=new this.dateClassObj();
this._set("endDate",_25);
this._timeRangeInvalidated=true;
}
if(cal.compare(_24,_25)>0){
_25=cal.add(_24,"day",1);
this._set("endDate",_25);
this._timeRangeInvalidated=true;
}
}else{
if(this.date==null){
this._set("date",new this.dateClassObj());
this._timeRangeInvalidated=true;
}
var _29=this.get("dateInterval");
if(_29!="day"&&_29!="week"&&_29!="month"){
this._set("dateInterval","day");
this._timeRangeInvalidated=true;
}
var dis=this.get("dateIntervalSteps");
if(_4.isString(dis)){
dis=parseInt(dis);
this._set("dateIntervalSteps",dis);
}
if(dis<=0){
this.set("dateIntervalSteps",1);
this._timeRangeInvalidated=true;
}
}
if(this._timeRangeInvalidated){
this._timeRangeInvalidated=false;
var _2a=this.computeTimeInterval();
if(this._timeInterval==null||cal.compare(this._timeInterval[0],_2a[0])!=0||cal.compare(this._timeInterval[1],_2a[1])!=0){
if(this._dateChanged){
this._lastValidDate=this.get("date");
this._dateChanged=false;
}else{
if(this._startDateChanged||this._endDateChanged){
this._lastValidStartDate=this.get("startDate");
this._lastValidEndDate=this.get("endDate");
this._startDateChanged=false;
this._endDateChanged=false;
}
}
this.onTimeIntervalChange({oldStartTime:this._timeInterval==null?null:this._timeInterval[0],oldEndTime:this._timeInterval==null?null:this._timeInterval[1],startTime:_2a[0],endTime:_2a[1]});
}else{
if(this._dateChanged){
this._dateChanged=false;
if(this.lastValidDate!=null){
this._set("date",this.lastValidDate);
}
}else{
if(this._startDateChanged||this._endDateChanged){
this._startDateChanged=false;
this._endDateChanged=false;
this._set("startDate",this._lastValidStartDate);
this._set("endDate",this._lastValidEndDate);
}
}
return;
}
this._timeInterval=_2a;
var _2b=this.dateModule.difference(this._timeInterval[0],this._timeInterval[1],"day");
var _2c=this._computeCurrentView(_2a[0],_2a[1],_2b);
var _2d=_5.indexOf(this.views,_2c);
if(_2c==null||_2d==-1){
return;
}
this._performViewTransition(_2c,_2d,_2a,_2b);
}
},_performViewTransition:function(_2e,_2f,_30,_31){
var _32=this.currentView;
if(this.animateRange&&(!_2("ie")||_2("ie")>8)){
if(_32){
_32.beforeDeactivate();
var ltr=this.isLeftToRight();
var _33=this._animRangeInDir=="left"||this._animRangeInDir==null;
var _34=this._animRangeOutDir=="left"||this._animRangeOutDir==null;
this._animateRange(this.currentView.domNode,_34&&ltr,false,0,_34?-100:100,_4.hitch(this,function(){
_32.afterDeactivate();
_2e.beforeActivate();
this.animateRangeTimer=setTimeout(_4.hitch(this,function(){
this._applyViewChange(_2e,_2f,_30,_31);
this._animateRange(this.currentView.domNode,_33&&ltr,true,_33?-100:100,0,function(){
_2e.afterActivate();
});
this._animRangeInDir=null;
this._animRangeOutDir=null;
}),100);
}));
}else{
_2e.beforeActivate();
this._applyViewChange(_2e,_2f,_30,_31);
_2e.afterActivate();
}
}else{
if(_32){
_32.beforeDeactivate();
}
_2e.beforeActivate();
this._applyViewChange(_2e,_2f,_30,_31);
if(_32){
_32.afterDeactivate();
}
_2e.afterActivate();
}
},onViewConfigurationChange:function(_35){
},_applyViewChange:function(_36,_37,_38,_39){
this._configureView(_36,_37,_38,_39);
this.onViewConfigurationChange(_36);
if(_37!=this._currentViewIndex){
if(this.currentView==null){
_36.set("items",this.items);
_36.set("decorationItems",this.decorationItems);
this.set("currentView",_36);
}else{
if(this.items==null||this.items.length==0){
this.set("currentView",_36);
if(this.animateRange&&(!_2("ie")||_2("ie")>8)){
_9.set(this.currentView.domNode,"opacity",0);
}
_36.set("items",this.items);
_36.set("decorationItems",this.decorationItems);
}else{
this.currentView=_36;
_36.set("items",this.items);
_36.set("decorationItems",this.decorationItems);
this.set("currentView",_36);
if(this.animateRange&&(!_2("ie")||_2("ie")>8)){
_9.set(this.currentView.domNode,"opacity",0);
}
}
}
}
},_timeInterval:null,computeTimeInterval:function(){
var d=this.get("date");
var _3a=this.get("minDate");
var _3b=this.get("maxDate");
var cal=this.dateModule;
if(d==null){
var _3c=this.get("startDate");
var _3d=cal.add(this.get("endDate"),"day",1);
if(_3a!=null||_3b!=null){
var dur=this.dateModule.difference(_3c,_3d,"day");
if(cal.compare(_3a,_3c)>0){
_3c=_3a;
_3d=cal.add(_3c,"day",dur);
}
if(cal.compare(_3b,_3d)<0){
_3d=_3b;
_3c=cal.add(_3d,"day",-dur);
}
if(cal.compare(_3a,_3c)>0){
_3c=_3a;
_3d=_3b;
}
}
return [this.floorToDay(_3c),this.floorToDay(_3d)];
}else{
var _3e=this._computeTimeIntervalImpl(d);
if(_3a!=null){
var _3f=this._computeTimeIntervalImpl(_3a);
if(cal.compare(_3f[0],_3e[0])>0){
_3e=_3f;
}
}
if(_3b!=null){
var _40=this._computeTimeIntervalImpl(_3b);
if(cal.compare(_40[1],_3e[1])<0){
_3e=_40;
}
}
return _3e;
}
},_computeTimeIntervalImpl:function(d){
var cal=this.dateModule;
var s=this.floorToDay(d);
var di=this.get("dateInterval");
var dis=this.get("dateIntervalSteps");
var e;
switch(di){
case "day":
e=cal.add(s,"day",dis);
break;
case "week":
s=this.floorToWeek(s);
e=cal.add(s,"week",dis);
break;
case "month":
s.setDate(1);
e=cal.add(s,"month",dis);
break;
default:
e=cal.add(s,"day",1);
}
return [s,e];
},onTimeIntervalChange:function(e){
},views:null,_setViewsAttr:function(_41){
if(!this._applyAttr){
for(var i=0;i<this.views.length;i++){
this._onViewRemoved(this.views[i]);
}
}
if(_41!=null){
for(var i=0;i<_41.length;i++){
this._onViewAdded(_41[i]);
}
}
this._set("views",_41==null?[]:_41.concat());
},_getViewsAttr:function(){
return this.views.concat();
},_createDefaultViews:function(){
},addView:function(_42,_43){
if(_43<=0||_43>this.views.length){
_43=this.views.length;
}
this.views.splice(_43,_42);
this._onViewAdded(_42);
},removeView:function(_44){
if(index<0||index>=this.views.length){
return;
}
this._onViewRemoved(this.views[index]);
this.views.splice(index,1);
},_onViewAdded:function(_45){
_45.owner=this;
_45.buttonContainer=this.buttonContainer;
_45._calendar=this._calendar;
_45.datePackage=this.datePackage;
_45.dateModule=this.dateModule;
_45.dateClassObj=this.dateClassObj;
_45.dateLocaleModule=this.dateLocaleModule;
_9.set(_45.domNode,"display","none");
_8.add(_45.domNode,"view");
_a.place(_45.domNode,this.viewContainer);
this.onViewAdded(_45);
},onViewAdded:function(_46){
},_onViewRemoved:function(_47){
_47.owner=null;
_47.buttonContainer=null;
_8.remove(_47.domNode,"view");
this.viewContainer.removeChild(_47.domNode);
this.onViewRemoved(_47);
},onViewRemoved:function(_48){
},_setCurrentViewAttr:function(_49){
var _4a=_5.indexOf(this.views,_49);
if(_4a!=-1){
var _4b=this.get("currentView");
this._currentViewIndex=_4a;
this._set("currentView",_49);
this._showView(_4b,_49);
this.onCurrentViewChange({oldView:_4b,newView:_49});
}
},_getCurrentViewAttr:function(){
return this.views[this._currentViewIndex];
},onCurrentViewChange:function(e){
},_configureView:function(_4c,_4d,_4e,_4f){
var cal=this.dateModule;
if(_4c.viewKind=="columns"){
_4c.set("startDate",_4e[0]);
_4c.set("columnCount",_4f);
}else{
if(_4c.viewKind=="matrix"){
if(_4f>7){
var s=this.floorToWeek(_4e[0]);
var e=this.floorToWeek(_4e[1]);
if(cal.compare(e,_4e[1])!=0){
e=this.dateModule.add(e,"week",1);
}
_4f=this.dateModule.difference(s,e,"day");
_4c.set("startDate",s);
_4c.set("columnCount",7);
_4c.set("rowCount",Math.ceil(_4f/7));
_4c.set("refStartTime",_4e[0]);
_4c.set("refEndTime",_4e[1]);
}else{
_4c.set("startDate",_4e[0]);
_4c.set("columnCount",_4f);
_4c.set("rowCount",1);
_4c.set("refStartTime",null);
_4c.set("refEndTime",null);
}
}
}
},_computeCurrentView:function(_50,_51,_52){
return _52<=7?this.columnView:this.matrixView;
},matrixViewRowHeaderClick:function(e){
var _53=this.matrixView.getExpandedRowIndex();
if(_53==e.index){
this.matrixView.collapseRow();
}else{
if(_53==-1){
this.matrixView.expandRow(e.index);
}else{
var h=this.matrixView.on("expandAnimationEnd",_4.hitch(this,function(){
h.remove();
this.matrixView.expandRow(e.index);
}));
this.matrixView.collapseRow();
}
}
},columnViewColumnHeaderClick:function(e){
var cal=this.dateModule;
if(cal.compare(e.date,this._timeInterval[0])==0&&this.dateInterval=="day"&&this.dateIntervalSteps==1){
this.set("dateInterval","week");
}else{
this.set("date",e.date);
this.set("dateInterval","day");
this.set("dateIntervalSteps",1);
}
},viewChangeDuration:0,_showView:function(_54,_55){
if(_54!=null){
_9.set(_54.domNode,"display","none");
}
if(_55!=null){
_9.set(_55.domNode,"display","block");
_55.resize();
if(!_2("ie")||_2("ie")>7){
_9.set(_55.domNode,"opacity","1");
}
}
},_setItemsAttr:function(_56){
this._set("items",_56);
if(this.currentView){
this.currentView.set("items",_56);
if(!this._isEditing){
this.currentView.invalidateRendering();
}
}
},_setDecorationItemsAttr:function(_57){
this._set("decorationItems",_57);
if(this.currentView){
this.currentView.set("decorationItems",_57);
this.currentView.invalidateRendering();
}
},_setDecorationStoreAttr:function(_58){
this._set("decorationStore",_58);
this.decorationStore=_58;
this.decorationStoreManager.set("store",_58);
},floorToDay:function(_59,_5a){
return _16.floorToDay(_59,_5a,this.dateClassObj);
},floorToWeek:function(d){
return _16.floorToWeek(d,this.dateClassObj,this.dateModule,this.firstDayOfWeek,this.locale);
},newDate:function(obj){
return _16.newDate(obj,this.dateClassObj);
},isToday:function(_5b){
return _16.isToday(_5b,this.dateClassObj);
},isStartOfDay:function(d){
return _16.isStartOfDay(d,this.dateClassObj,this.dateModule);
},floorDate:function(_5c,_5d,_5e,_5f){
return _16.floor(_5c,_5d,_5e,_5f,this.classFuncObj);
},isOverlapping:function(_60,_61,_62,_63,_64,_65){
return _16.isOverlapping(_60,_61,_62,_63,_64,_65);
},animateRange:true,animationRangeDuration:400,_animateRange:function(_66,_67,_68,_69,xTo,_6a){
if(this.animateRangeTimer){
clearTimeout(this.animateRangeTimer);
delete this.animateRangeTimer;
}
var _6b=_68?_e.fadeIn:_e.fadeOut;
_9.set(_66,{left:_69+"px",right:(-_69)+"px"});
fx.combine([_e.animateProperty({node:_66,properties:{left:xTo,right:-xTo},duration:this.animationRangeDuration/2,onEnd:_6a}),_6b({node:_66,duration:this.animationRangeDuration/2})]).play();
},_animRangeOutDir:null,_animRangeOutDir:null,nextRange:function(){
this._animRangeOutDir="left";
this._animRangeInDir="right";
this._navigate(1);
},previousRange:function(){
this._animRangeOutDir="right";
this._animRangeInDir="left";
this._navigate(-1);
},_navigate:function(dir){
var d=this.get("date");
var cal=this.dateModule;
if(d==null){
var s=this.get("startDate");
var e=this.get("endDate");
var dur=cal.difference(s,e,"day");
if(dir==1){
e=cal.add(e,"day",1);
this.set("startDate",e);
this.set("endDate",cal.add(e,"day",dur));
}else{
s=cal.add(s,"day",-1);
this.set("startDate",cal.add(s,"day",-dur));
this.set("endDate",s);
}
}else{
var di=this.get("dateInterval");
var dis=this.get("dateIntervalSteps");
this.set("date",cal.add(d,di,dir*dis));
}
},goToday:function(){
this.set("date",this.floorToDay(new this.dateClassObj(),true));
this.set("dateInterval","day");
this.set("dateIntervalSteps",1);
},postCreate:function(){
this.inherited(arguments);
this.configureButtons();
},configureButtons:function(){
var rtl=!this.isLeftToRight();
if(this.previousButton){
this.previousButton.set("label",_17[rtl?"nextButton":"previousButton"]);
this.own(on(this.previousButton,"click",_4.hitch(this,this.previousRange)));
}
if(this.nextButton){
this.nextButton.set("label",_17[rtl?"previousButton":"nextButton"]);
this.own(on(this.nextButton,"click",_4.hitch(this,this.nextRange)));
}
if(rtl&&this.previousButton&&this.nextButton){
var t=this.previousButton;
this.previousButton=this.nextButton;
this.nextButton=t;
}
if(this.todayButton){
this.todayButton.set("label",_17.todayButton);
this.own(on(this.todayButton,"click",_4.hitch(this,this.todayButtonClick)));
}
if(this.dayButton){
this.dayButton.set("label",_17.dayButton);
this.own(on(this.dayButton,"click",_4.hitch(this,this.dayButtonClick)));
}
if(this.weekButton){
this.weekButton.set("label",_17.weekButton);
this.own(on(this.weekButton,"click",_4.hitch(this,this.weekButtonClick)));
}
if(this.fourDaysButton){
this.fourDaysButton.set("label",_17.fourDaysButton);
this.own(on(this.fourDaysButton,"click",_4.hitch(this,this.fourDaysButtonClick)));
}
if(this.monthButton){
this.monthButton.set("label",_17.monthButton);
this.own(on(this.monthButton,"click",_4.hitch(this,this.monthButtonClick)));
}
},todayButtonClick:function(e){
this.goToday();
},dayButtonClick:function(e){
if(this.get("date")==null){
this.set("date",this.floorToDay(new this.dateClassObj(),true));
}
this.set("dateInterval","day");
this.set("dateIntervalSteps",1);
},weekButtonClick:function(e){
this.set("dateInterval","week");
this.set("dateIntervalSteps",1);
},fourDaysButtonClick:function(e){
this.set("dateInterval","day");
this.set("dateIntervalSteps",4);
},monthButtonClick:function(e){
this.set("dateInterval","month");
this.set("dateIntervalSteps",1);
},updateRenderers:function(obj,_6c){
if(this.currentView){
this.currentView.updateRenderers(obj,_6c);
}
},getIdentity:function(_6d){
return _6d?_6d.id:null;
},_setHoveredItem:function(_6e,_6f){
if(this.hoveredItem&&_6e&&this.hoveredItem.id!=_6e.id||_6e==null||this.hoveredItem==null){
var old=this.hoveredItem;
this.hoveredItem=_6e;
this.updateRenderers([old,this.hoveredItem],true);
if(_6e&&_6f){
this.currentView._updateEditingCapabilities(_6e._item?_6e._item:_6e,_6f);
}
}
},hoveredItem:null,isItemHovered:function(_70){
return this.hoveredItem!=null&&this.hoveredItem.id==_70.id;
},isItemEditable:function(_71,_72){
return this.editable;
},isItemMoveEnabled:function(_73,_74){
return this.isItemEditable(_73,_74)&&this.moveEnabled;
},isItemResizeEnabled:function(_75,_76){
return this.isItemEditable(_75,_76)&&this.resizeEnabled;
},onGridClick:function(e){
},onGridDoubleClick:function(e){
},onItemClick:function(e){
},onItemDoubleClick:function(e){
},onItemContextMenu:function(e){
},onItemEditBegin:function(e){
},onItemEditEnd:function(e){
},onItemEditBeginGesture:function(e){
},onItemEditMoveGesture:function(e){
},onItemEditResizeGesture:function(e){
},onItemEditEndGesture:function(e){
},onItemRollOver:function(e){
},onItemRollOut:function(e){
},onColumnHeaderClick:function(e){
},onRowHeaderClick:function(e){
},onExpandRendererClick:function(e){
},_onRendererCreated:function(e){
this.onRendererCreated(e);
},onRendererCreated:function(e){
},_onRendererRecycled:function(e){
this.onRendererRecycled(e);
},onRendererRecycled:function(e){
},_onRendererReused:function(e){
this.onRendererReused(e);
},onRendererReused:function(e){
},_onRendererDestroyed:function(e){
this.onRendererDestroyed(e);
},onRendererDestroyed:function(e){
},_onRenderersLayoutDone:function(_77){
this.onRenderersLayoutDone(_77);
},onRenderersLayoutDone:function(_78){
}});
});
