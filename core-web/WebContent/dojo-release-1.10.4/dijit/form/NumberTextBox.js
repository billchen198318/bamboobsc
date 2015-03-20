//>>built
define("dijit/form/NumberTextBox",["dojo/_base/declare","dojo/_base/lang","dojo/i18n","dojo/string","dojo/number","./RangeBoundTextBox"],function(_1,_2,_3,_4,_5,_6){
var _7=function(_8){
var _8=_8||{},_9=_3.getLocalization("dojo.cldr","number",_3.normalizeLocale(_8.locale)),_a=_8.pattern?_8.pattern:_9[(_8.type||"decimal")+"Format"];
var _b;
if(typeof _8.places=="number"){
_b=_8.places;
}else{
if(typeof _8.places==="string"&&_8.places.length>0){
_b=_8.places.replace(/.*,/,"");
}else{
_b=(_a.indexOf(".")!=-1?_a.split(".")[1].replace(/[^#0]/g,"").length:0);
}
}
return {sep:_9.decimal,places:_b};
};
var _c=_1("dijit.form.NumberTextBoxMixin",null,{pattern:function(_d){
return "("+(this.focused&&this.editOptions?this._regExpGenerator(_2.delegate(_d,this.editOptions))+"|":"")+this._regExpGenerator(_d)+")";
},value:NaN,editOptions:{pattern:"#.######"},_formatter:_5.format,_regExpGenerator:_5.regexp,_decimalInfo:_7(),postMixInProperties:function(){
this.inherited(arguments);
this._set("type","text");
},_setConstraintsAttr:function(_e){
var _f=typeof _e.places=="number"?_e.places:0;
if(_f){
_f++;
}
if(typeof _e.max!="number"){
_e.max=9*Math.pow(10,15-_f);
}
if(typeof _e.min!="number"){
_e.min=-9*Math.pow(10,15-_f);
}
this.inherited(arguments,[_e]);
if(this.focusNode&&this.focusNode.value&&!isNaN(this.value)){
this.set("value",this.value);
}
this._decimalInfo=_7(_e);
},_onFocus:function(){
if(this.disabled||this.readOnly){
return;
}
var val=this.get("value");
if(typeof val=="number"&&!isNaN(val)){
var _10=this.format(val,this.constraints);
if(_10!==undefined){
this.textbox.value=_10;
}
}
this.inherited(arguments);
},format:function(_11,_12){
var _13=String(_11);
if(typeof _11!="number"){
return _13;
}
if(isNaN(_11)){
return "";
}
if(!("rangeCheck" in this&&this.rangeCheck(_11,_12))&&_12.exponent!==false&&/\de[-+]?\d/i.test(_13)){
return _13;
}
if(this.editOptions&&this.focused){
_12=_2.mixin({},_12,this.editOptions);
}
return this._formatter(_11,_12);
},_parser:_5.parse,parse:function(_14,_15){
var v=this._parser(_14,_2.mixin({},_15,(this.editOptions&&this.focused)?this.editOptions:{}));
if(this.editOptions&&this.focused&&isNaN(v)){
v=this._parser(_14,_15);
}
return v;
},_getDisplayedValueAttr:function(){
var v=this.inherited(arguments);
return isNaN(v)?this.textbox.value:v;
},filter:function(_16){
if(_16==null||typeof _16=="string"&&_16==""){
return NaN;
}else{
if(typeof _16=="number"&&!isNaN(_16)&&_16!=0){
_16=_5.round(_16,this._decimalInfo.places);
}
}
return this.inherited(arguments,[_16]);
},serialize:function(_17,_18){
return (typeof _17!="number"||isNaN(_17))?"":this.inherited(arguments);
},_setBlurValue:function(){
var val=_2.hitch(_2.delegate(this,{focused:true}),"get")("value");
this._setValueAttr(val,true);
},_setValueAttr:function(_19,_1a,_1b){
if(_19!==undefined&&_1b===undefined){
_1b=String(_19);
if(typeof _19=="number"){
if(isNaN(_19)){
_1b="";
}else{
if(("rangeCheck" in this&&this.rangeCheck(_19,this.constraints))||this.constraints.exponent===false||!/\de[-+]?\d/i.test(_1b)){
_1b=undefined;
}
}
}else{
if(!_19){
_1b="";
_19=NaN;
}else{
_19=undefined;
}
}
}
this.inherited(arguments,[_19,_1a,_1b]);
},_getValueAttr:function(){
var v=this.inherited(arguments);
if(isNaN(v)&&this.textbox.value!==""){
if(this.constraints.exponent!==false&&/\de[-+]?\d/i.test(this.textbox.value)&&(new RegExp("^"+_5._realNumberRegexp(_2.delegate(this.constraints))+"$").test(this.textbox.value))){
var n=Number(this.textbox.value);
return isNaN(n)?undefined:n;
}else{
return undefined;
}
}else{
return v;
}
},isValid:function(_1c){
if(!this.focused||this._isEmpty(this.textbox.value)){
return this.inherited(arguments);
}else{
var v=this.get("value");
if(!isNaN(v)&&this.rangeCheck(v,this.constraints)){
if(this.constraints.exponent!==false&&/\de[-+]?\d/i.test(this.textbox.value)){
return true;
}else{
return this.inherited(arguments);
}
}else{
return false;
}
}
},_isValidSubset:function(){
var _1d=(typeof this.constraints.min=="number"),_1e=(typeof this.constraints.max=="number"),_1f=this.get("value");
if(isNaN(_1f)||(!_1d&&!_1e)){
return this.inherited(arguments);
}
var _20=_1f|0,_21=_1f<0,_22=this.textbox.value.indexOf(this._decimalInfo.sep)!=-1,_23=this.maxLength||20,_24=_23-this.textbox.value.length,_25=_22?this.textbox.value.split(this._decimalInfo.sep)[1].replace(/[^0-9]/g,""):"";
var _26=_22?_20+"."+_25:_20+"";
var _27=_4.rep("9",_24),_28=_1f,_29=_1f;
if(_21){
_28=Number(_26+_27);
}else{
_29=Number(_26+_27);
}
return !((_1d&&_29<this.constraints.min)||(_1e&&_28>this.constraints.max));
}});
var _2a=_1("dijit.form.NumberTextBox",[_6,_c],{baseClass:"dijitTextBox dijitNumberTextBox"});
_2a.Mixin=_c;
return _2a;
});
