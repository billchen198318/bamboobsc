/*
 * jsPlumb
 * 
 * Title:jsPlumb 1.7.2
 * 
 * Provides a way to visually connect elements on an HTML page, using SVG or VML.  
 * 
 * This file contains the 'vanilla' adapter - having no external dependencies other than bundled libs.
 *
 * Copyright (c) 2010 - 2014 Simon Porritt (simon@jsplumbtoolkit.com)
 * 
 * http://jsplumbtoolkit.com
 * http://github.com/sporritt/jsplumb
 * 
 * Dual licensed under the MIT and GPL2 licenses.
 */
;(function() {

	"use strict";

	 var _getDragManager = function(instance, category) {

        category = category || "main";
        var key = "_katavorio_" + category;
		var k = instance[key],
			e = instance.getEventManager();
			
		if (!k) {
			k = new Katavorio( {
				bind:e.on,
				unbind:e.off,
				getSize:jsPlumb.getSize,
				getPosition:function(el) {
					var o = jsPlumbAdapter.getOffset(el, instance);
					return [o.left, o.top];
				},
				setPosition:function(el, xy) {
					el.style.left = xy[0] + "px";
					el.style.top = xy[1] + "px";
				},
				addClass:jsPlumbAdapter.addClass,
				removeClass:jsPlumbAdapter.removeClass,
				intersects:Biltong.intersects,
				indexOf:jsPlumbUtil.indexOf,
				css:{
					noSelect : instance.dragSelectClass,
					droppable:"jsplumb-droppable",
					draggable:"jsplumb-draggable",
					drag:"jsplumb-drag",
					selected:"jsplumb-drag-selected",
					active:"jsplumb-drag-active",
					hover:"jsplumb-drag-hover"
				}
			});
			instance[key] = k;
			instance.bind("zoom", k.setZoom);
		}
		return k;
	};
	 
	 var _animProps = function(o, p) {
		var _one = function(pName) {
			if (p[pName]) {
				if (jsPlumbUtil.isString(p[pName])) {
					var m = p[pName].match(/-=/) ? -1 : 1,
						v = p[pName].substring(2);
					return o[pName] + (m * v);
				}
				else return p[pName];
			}
			else 
				return o[pName];
		};
		return [ _one("left"), _one("top") ];
	 };

	jsPlumb.extend(jsPlumbInstance.prototype, {

        scopeChange:function(el, elId, endpoints, scope, types) {

        },
	
		getDOMElement:function(el) { 
			if (el == null) return null;
			// here we pluck the first entry if el was a list of entries.
			// this is not my favourite thing to do, but previous versions of 
			// jsplumb supported jquery selectors, and it is possible a selector 
			// will be passed in here.
			el = typeof el === "string" ? el : el.length != null ? el[0] : el;
			return typeof el === "string" ? document.getElementById(el) : el; 
		},
		getElementObject:function(el) { return el; },
		removeElement : function(element) {
			_getDragManager(this).elementRemoved(element);
			this.getEventManager().remove(element);
		},
		//
		// this adapter supports a rudimentary animation function. no easing is supported.  only
		// left/top properties are supported. property delta args are expected to be in the form
		//
		// +=x.xxxx
		//
		// or
		//
		// -=x.xxxx
		//
		doAnimate:function(el, properties, options) { 
			options = options || {};
			var o = jsPlumbAdapter.getOffset(el, this),
				ap = _animProps(o, properties),
				ldist = ap[0] - o.left,
				tdist = ap[1] - o.top,
				d = options.duration || 250,
				step = 15, steps = d / step,
				linc = (step / d) * ldist,
				tinc = (step / d) * tdist,
				idx = 0,
				int = setInterval(function() {
					jsPlumbAdapter.setPosition(el, {
						left:o.left + (linc * (idx + 1)),
						top:o.top + (tinc * (idx + 1))
					});
					if (options.step != null) options.step();
					idx++;
					if (idx >= steps) {
						window.clearInterval(int);
						if (options.complete != null) options.complete();
					}
				}, step);
		},
		getSelector:function(ctx, spec) { 
			var sel = null;
			if (arguments.length == 1) {
				sel = ctx.nodeType != null ? ctx : document.querySelectorAll(ctx);
			}
			else
				sel = ctx.querySelectorAll(spec); 
				
			return sel;
		},
		// DRAG/DROP
		destroyDraggable:function(el, category) {
			_getDragManager(this, category).destroyDraggable(el);
		},
		destroyDroppable:function(el, category) {
			_getDragManager(this, category).destroyDroppable(el);
		},
		initDraggable : function(el, options, category) {
			_getDragManager(this, category).draggable(el, options);
		},
		initDroppable : function(el, options, category) {
			_getDragManager(this, category).droppable(el, options);
		},
		isAlreadyDraggable : function(el) { return el._katavorioDrag != null; },
		isDragSupported : function(el, options) { return true; },
		isDropSupported : function(el, options) { return true; },
		getDragObject : function(eventArgs) { return eventArgs[0].drag.getDragElement(); },
		getDragScope : function(el) {
			return el._katavorioDrag && el._katavorioDrag.scopes.join(" ") || "";
		},
		getDropEvent : function(args) { return args[0].e; },
		getDropScope : function(el) {
			return el._katavorioDrop && el._katavorioDrop.scopes.join(" ") || "";
		},
		getUIPosition : function(eventArgs, zoom) {
			return {
				left:eventArgs[0].pos[0],
				top:eventArgs[0].pos[1]
			};
		},
		isDragFilterSupported:function() { return true; },
		setDragFilter : function(el, filter) {
			if (el._katavorioDrag) {
				el._katavorioDrag.setFilter(filter);
			}
		},
		setElementDraggable : function(el, draggable) { 
			el = jsPlumb.getDOMElement(el);
			if (el._katavorioDrag)
				el._katavorioDrag.setEnabled(draggable);
		},
		setDragScope : function(el, scope) { 
			if (el._katavorioDrag)
				el._katavorioDrag.k.setDragScope(el, scope);
		},
		dragEvents : {
			'start':'start', 'stop':'stop', 'drag':'drag', 'step':'step',
			'over':'over', 'out':'out', 'drop':'drop', 'complete':'complete'
		},
		animEvents:{
			'step':"step", 'complete':'complete'
		},
		stopDrag : function(el) {
			if (el._katavorioDrag)
				el._katavorioDrag.abort();
        },
// 		MULTIPLE ELEMENT DRAG
		// these methods are unique to this adapter, because katavorio
		// supports dragging multiple elements.
		addToDragSelection:function(spec) {
			_getDragManager(this).select(spec);
		},
		removeFromDragSelection:function(spec) {
			_getDragManager(this).deselect(spec);
		},
		clearDragSelection:function() {
			_getDragManager(this).deselectAll();
		},
        getOriginalEvent : function(e) { return e; },
        // each adapter needs to use its own trigger method, because it triggers a drag. Mottle's trigger method
        // works perfectly well but does not cause a drag to start with jQuery. Presumably this is due to some
        // intricacy in the way in which jQuery UI's draggable method registers events.
        trigger : function(el, event, originalEvent) {
            this.getEventManager().trigger(el, event, originalEvent);
        }
    });

	var ready = function (f) {
		var _do = function() {
			if (/complete|loaded|interactive/.test(document.readyState) && typeof(document.body) != "undefined" && document.body != null)
	            f();	        
	        else
	            setTimeout(_do, 9);
		};
		
		_do();
    };
	ready(jsPlumb.init);
	
}).call(this);
