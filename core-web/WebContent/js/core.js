/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
dojo.declare("GS.CORE", null ,{
    ver: '0.7.0',
    name: 'bambooCORE', // core name
    full_name: 'bambooBSC - opensource Balanced Scorecard (BSC) Business Intelligence', // project full name
    /*
        constructor: function(ver, name, full_name) {
            this.ver = ver;
	        this.name = name;
	        this.full_name = full_name;
        },
    */
    pleaseWaitDlgId: 'pleaseWaitDlg',
    getVer : function() {
        return this.ver;
    },
    getName : function() {
        return this.name;
    },
    getFullName : function() {
        return this.full_name;
    },
    getPleaseWaitDlgId : function() {
    	return this.pleaseWaitDlgId;
    },
    generateGuid : function() { // http://stackoverflow.com/questions/105034/how-to-create-a-guid-uuid-in-javascript
    	return (this.generateRandomStr()+this.generateRandomStr()+"-"+this.generateRandomStr()+"-"+this.generateRandomStr()+"-"+this.generateRandomStr()+"-"+this.generateRandomStr()+this.generateRandomStr()+this.generateRandomStr()); 
    },
    generateRandomStr : function() { // http://stackoverflow.com/questions/105034/how-to-create-a-guid-uuid-in-javascript
    	return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
    },
    getStrToHex : function(str) {
        var hex = '';
        for(var i=0; str!=null && i<str.length;i++) {
            hex += ''+str.charCodeAt(i).toString(16);
        }
        return hex;	
    },
    getStrToBytes : function(str) {
    	var uStr = encodeURIComponent(str);
    	var bytes = [];
    	for (var i = 0; i < uStr.length; ++i) {
    	    bytes.push(uStr.charCodeAt(i));
    	}
    	return bytes;
    },
    getStrToBase64 : function(str) {
    	return dojox.encoding.base64.encode(this.getStrToBytes(str));
    },
    getBase64ToStr : function(str) {
    	var bytes = dojox.encoding.base64.decode(str);
    	return String.fromCharCode.apply(null, bytes);
    },
    roundFloat : function(num, pos) {
    	return ( Math.round( num * Math.pow(10,pos) ) / Math.pow(10,pos) ).toFixed(pos);
    },
    isNormalInteger : function(str) {
    	return /^\+?(0|[1-9]\d*)$/.test(str);
    },
    isBlank : function(str) {
    	return (!str || /^\s*$/.test(str));
    },
    isEmpty : function(str) {
    	return (!str || 0 === str.length);
    },
    escape1 : function(str) {
    	return str.replace(/&/g, "＆")
    		.replace(/</g, "〈")
    		.replace(/>/g, "〉")
    		.replace(/"/g, "”")
    		.replace(/'/g, "’")
    		.replace(/\//g, "╱")
    		.replace(/\\/g, "╲");
    }
    
});

dojo.declare("GS.ViewPage", GS.CORE, {	
    basePath: '.',    
    constructor: function(basePath) {
        this.basePath = basePath;
    }, 
    getBasePath : function() {
        return this.basePath;
    },
    setBasePath : function(basePath) {
        this.basePath = basePath;
    },
    loadingDlgShow : function() {
    	dijit.byId( this.getPleaseWaitDlgId() ).show();
    },
    loadingDlgHide : function() {
    	dijit.byId( this.getPleaseWaitDlgId() ).hide();
    },
    showPleaseWait : function() {
    	dojo.style(dijit.byId( this.getPleaseWaitDlgId() ).closeButtonNode,"display","none");
    	dijit.byId( this.getPleaseWaitDlgId() ).show();    	
    },
    hidePleaseWait : function() {
    	dijit.byId( this.getPleaseWaitDlgId() ).hide();
    },    
    addOrUpdateContentPane : function(tabContainerId, childTabId, tabTitle, tabHref, tabClosable, tabPreventCache, iframeMode) {
    	if (dijit.byId(childTabId)!=null) {
    		var childTabPane = dijit.byId(childTabId);
    		if ( childTabPane.get("href")!=tabHref && null!=tabHref && ''!=tabHref) {
    			childTabPane.attr("href", tabHref);
    		}
    		
    		if (iframeMode) {
    			this.closeContentPane(tabContainerId, childTabId);
    	    	var contentPane = new dojox.layout.ContentPane({ 
    	    		id				: childTabId, 
    				title			: tabTitle, 
    				href			: tabHref, 
    				closable		: tabClosable,
    				preventCache	: tabPreventCache,
    				executeScripts	: true
    			});
    	    	if (iframeMode) {
    	    		contentPane.setContent("<iframe style='width:100%;height:100%;border:0px' border='0' src='" + tabHref + "'></iframe>");
    	    	}
    	    	dijit.byId(tabContainerId).addChild(contentPane);
    	    	dijit.byId(tabContainerId).selectChild(childTabId);	    			
    			return;
    		}
    		
    		childTabPane.refresh();   
    		dijit.byId(tabContainerId).selectChild(childTabId);
    		return;
    	}
    	//dijit.layout.ContentPane change-to dojox.layout.ContentPane
    	var contentPane = new dojox.layout.ContentPane({ 
    		id				: childTabId, 
			title			: tabTitle, 
			href			: tabHref, 
			closable		: tabClosable,
			preventCache	: tabPreventCache,
			executeScripts	: true
		});
    	if (iframeMode) {
    		contentPane.setContent("<iframe style='width:100%;height:100%;border:0px' border='0' src='" + tabHref + "'></iframe>");
    	}
    	dijit.byId(tabContainerId).addChild(contentPane);
    	dijit.byId(tabContainerId).selectChild(childTabId);	
    },
    closeContentPane : function(tabContainerId, childTabId) {
    	if (dijit.byId(childTabId)==null) {
    		return;
    	}
    	dijit.byId(tabContainerId).removeChild( dijit.byId(childTabId) );
    	dijit.byId(childTabId).destroyRecursive(true);
    },
    refreshContentPane : function(childTabId) {
    	if (dijit.byId(childTabId)==null) {
    		return;
    	}
    	var childTabPane = dijit.byId(childTabId);
    	childTabPane.refresh();
    },
    toasterShow : function(toasterId, txtTitle, txtContent, callbackFn, attributeType) {
    	if (dijit.byId(toasterId)==null) {
    		alert('miss ' + toasterId + " : " + txtContent);
    		if (null!=callbackFn && eval("typeof " + callbackFn + "=='function'") ) {
    			callbackFn();
    		}
    		return;
    	}
    	
    	/*
    	 * message
    	 * warning
    	 * error
    	 * fatal
    	 */
    	var _t = this.getStrToBase64(txtTitle);    	
    	var _c = this.getStrToBase64(txtContent);
    	var toasterTxtContent = '<br/><br/><a herf="#" onclick="viewPage.showBeforeAlertDialog(\'' + _t + '\', \'' + _c + '\');"><img src="./icons/help-about.png" border="0" />&nbsp;<b>Click show info.</b></a> ';
    	
    	toasterTxtContent = '<b><font color="#000000" size="3">' + txtTitle + '</font></b>' + '<br/><hr size="2" color="#242424" /><font color="#242424" size="2">' + txtContent + '</font>' + toasterTxtContent;
    	dijit.byId(toasterId).setContent(toasterTxtContent, attributeType);
    	dijit.byId(toasterId).show();  
		if (null!=callbackFn && eval("typeof " + callbackFn + "=='function'") ) {
			callbackFn();
		}    	
    },    
    showBeforeAlertDialog : function(encTitle, encContent) {
    	this.alertDialog(decodeURIComponent(this.getBase64ToStr(encTitle)), decodeURIComponent(this.getBase64ToStr(encContent)), function(){});
    },    
    alertDialog : function(txtTitle, txtContent, callbackFn) {
    	var thisDialog = new dijit.Dialog({ 
    		title: '<img src="./icons/help-about.png" border="0">&nbsp;' + txtTitle, 
    		content: '<textarea cols="100" rows="10" style="border:dotted 2px #CFECEC; font-size: 11pt; color: #151515;" readonly>' + txtContent.replace(/<br\s*[\/]?>/gi, "\n").replace("/<BR\s*[\/]?>/gi", "\n") + '</textarea><BR/>' 
    	}).placeAt(dojo.body());
    	var okButton = null;
    	dojo.body().appendChild(thisDialog.domNode);	
    	if (null!=callbackFn && eval("typeof " + callbackFn + "=='function'") ) {		
    		var callback = function(mouseEvent) {
    			thisDialog.hide();
    			thisDialog.destroyRecursive();
    			callbackFn();
    		};
    	    var actionBar = dojo.create("div", {
    	        "class": "dijitDialogPaneActionBar"
    	    }, thisDialog.containerNode);			
    		okButton = new dijit.form.Button({ label: 'OK', id: 'ok'+this.generateGuid(), onClick: callback, iconClass: 'dijitIconApplication', class: 'alt-primary' }).placeAt(actionBar);
    		//thisDialog.containerNode.appendChild(okButton.domNode);	
    	}		
    	thisDialog.startup();
    	thisDialog.show();
    	thisDialog.connect(
    			thisDialog, 
    			'hide', 
    			function() { 
    				thisDialog.destroy(); 
    				if (okButton!=null) { 
    					okButton.destroy(); 
    				} 
    			} 
    	);    	
    },
    confirmDialog : function(dialogId, title, question, callbackFn, e) {
    	var confirmDialog = dijit.byId(dialogId);
    	if (confirmDialog==null) {
    		confirmDialog = new dijit.Dialog({ id: dialogId, title: title, content: question });
    	}	
    	var callback = function(mouseEvent) {
    		confirmDialog.hide();
    		confirmDialog.destroyRecursive();
    		if (window.event) {
    			e = window.event;			
    		} 
    		var srcEl = mouseEvent.srcElement ? mouseEvent.srcElement : mouseEvent.target; //IE or Firefox	
    		
    		/*
    		alert('srcEl.id='+srcEl.id);
    		alert('srcEl.value='+srcEl.value);
    		
    		if (srcEl.id == 'yes_label') {
    			callbackFn(true, e);
    		} else {			
    			callbackFn(false, e);
    		}	
    		*/
    		if (srcEl.value == 'true' ) {
    			callbackFn(true, e);
    		} else {
    			callbackFn(false, e);
    		}
    		
    	};	
        var actionBar = dojo.create("div", {
            "class": "dijitDialogPaneActionBar"
        }, confirmDialog.containerNode);			
    	//var questionDiv = dojo.create('div', { innerHTML: question });
    	var yesButton = new dijit.form.Button({ label: 'Yes', value: 'true', id: 'yes', onClick: callback, iconClass: 'dijitIconConfigure', class: 'alt-warning' }).placeAt(actionBar);
    	var noButton = new dijit.form.Button({ label: 'No', value : 'false', id: 'no', onClick: callback, iconClass: 'dijitEditorIconCancel', class: 'alt-primary' }).placeAt(actionBar);
    	//confirmDialog.containerNode.appendChild(questionDiv);
    	//confirmDialog.containerNode.appendChild(yesButton.domNode);
    	//confirmDialog.containerNode.appendChild(noButton.domNode);
    	confirmDialog.show();
    	confirmDialog.connect(
    			confirmDialog, 
    			'hide', 
    			function() { 
    				confirmDialog.destroy(); 
    				yesButton.destroy(); 
    				noButton.destroy(); 
    			} 
    	);    	
    },
    xhrSendForm : function(_urlAction, _formId, _handleAs, _timeout, _sync, _preventCache, _loadFunction, _errFunction, _showPleaseWait) { 
    	if (_showPleaseWait) {
    		this.showPleaseWait();
    	}
        var xhrArgs={
        		url			: 	_urlAction, // this.getBasePath() + _urlAction
        		form		: 	dojo.byId(_formId),
        		handleAs	: 	_handleAs, 
        		timeout		: 	_timeout,
        		sync		: 	_sync,
        		preventCache: 	_preventCache,
        		load: function(data) {
        			if (_showPleaseWait) {
        				setTimeout(function(){
        					this.hidePleaseWait();
        				}, 350);        				
        			}
        			if (data==null || (typeof data=='undefined') ) {
        				alert('Unexpected error!');
        				return;
        			}    			
        			if ('Y'!=data.login) {
        				alertDialog("login", "Please try login again!", function(){}, 'N');
        				return;
        			}         			
        			if ('Y'!=data.isAuthorize) {
        				alertDialog("Authorize", "No permission!", function(){}, 'N');
        				return;        				
        			}
        			_loadFunction(data);
        		},
        		error: function(error) {
        			if (_showPleaseWait) {
        				setTimeout(function(){
        					this.hidePleaseWait();
        				}, 350);        				
        			}      			
        			_errFunction(error);
        		}
        };
        var deferred = dojo.xhrPost(xhrArgs);	    	
    },
    xhrSendParameter : function(_urlAction, _parameter, _handleAs, _timeout, _sync, _preventCache, _loadFunction, _errFunction, _showPleaseWait) {
    	if (_showPleaseWait) {
    		this.showPleaseWait();
    	}    	
        var xhrArgs={
        		url			: 	_urlAction, // this.getBasePath() + _urlAction
        		postData	: 	dojo.objectToQuery(_parameter), 
        		handleAs	: 	_handleAs,
        		timeout		: 	_timeout,
        		sync		: 	_sync,
        		preventCache: 	_preventCache,
        		load: function(data) {	
        			if (_showPleaseWait) {
        				setTimeout(function(){
        					this.hidePleaseWait();
        				}, 350);        				
        			}     			
        			if (data==null || (typeof data=='undefined') ) {
        				alert('Unexpected error!');
        				return;
        			}    			
        			if ('Y'!=data.login) {
        				alertDialog("login", "Please try login again!", function(){}, 'N');
        				return;
        			}       
        			if ('Y'!=data.isAuthorize) {
        				alertDialog("Authorize", "No permission!", function(){}, 'N');
        				return;        				
        			}        			
        			_loadFunction(data);  			
        		},
        		error: function(error) {
        			if (_showPleaseWait) {
        				setTimeout(function(){
        					this.hidePleaseWait();
        				}, 350);        				
        			}    			
        			_errFunction(error);
        		}
        };
        var deferred = dojo.xhrPost(xhrArgs);	    	
    },
    getSVGImageData : function(_selectId) {
    	return 'data:image/svg+xml;base64,' + btoa( this.getSVGData(_selectId) );	
    },  
    getSVGData : function(_selectId) {
    	var exportData = d3.select( _selectId )
    		.attr('version', 1.1)
    		.attr('xmlns', 'http://www.w3.org/2000/svg')
    		.node().parentNode.innerHTML;
    	return exportData;	
    },       
    getSVGImage2CanvasToDataUrlPNG : function(_selectId) {
    	var svg = viewPage.getSVGImageData( _selectId );
    	var canvas = document.createElement('canvas');
    	canvg(canvas, svg, {
    		ignoreMouse: true,
    		ignoreAnimation: true,
    		useCORS: true
    	});    	
    	return canvas.toDataURL('image/png');
    },
    toggleFullscreen : function(element) {
    	/**
    	 * need for toolbar button fullscreen and exit-fullscreen
    	 * src copy from http://davidwalsh.name/fullscreen
    	 */
    	if (!document.fullscreenElement && !document.mozFullScreenElement 
    			&& !document.webkitFullscreenElement && !document.msFullscreenElement) {
    		if(element.requestFullscreen) {
    			element.requestFullscreen();
    		} else if(element.mozRequestFullScreen) {
    			element.mozRequestFullScreen();
    		} else if(element.webkitRequestFullscreen) {
    			element.webkitRequestFullscreen();
    		} else if(element.msRequestFullscreen) {
    			element.msRequestFullscreen();
    		}    		
    	} else {
    		if(document.exitFullscreen) {
    			document.exitFullscreen();
    		} else if(document.mozCancelFullScreen) {
    			document.mozCancelFullScreen();
    		} else if(document.webkitExitFullscreen) {
    			document.webkitExitFullscreen();
    		}    		
    	}
    	
    }
        
});
