/*
 * jsPlumb
 * 
 * Title:jsPlumb 1.7.5
 * 
 * Provides a way to visually connect elements on an HTML page, using SVG or VML.  
 * 
 * This file contains the jQuery adapter.
 *
 * Copyright (c) 2010 - 2015 jsPlumb (hello@jsplumbtoolkit.com)
 * 
 * http://jsplumbtoolkit.com
 * http://github.com/sporritt/jsplumb
 * 
 * Dual licensed under the MIT and GPL2 licenses.
 */
;
(function ($, root) {

    "use strict";
    var _jp = root.jsPlumb, _ju = root.jsPlumbUtil;

    var _getElementObject = function (el) {
        return typeof(el) == "string" ? $("#" + el) : $(el);
    };

    $.extend(root.jsPlumbInstance.prototype, {

        animationSupported:true,

// ---------------------------- DOM MANIPULATION ---------------------------------------		


        /**
         * gets a DOM element from the given input, which might be a string (in which case we just do document.getElementById),
         * a selector (in which case we return el[0]), or a DOM element already (we assume this if it's not either of the other
         * two cases).  this is the opposite of getElementObject below.
         */
        getDOMElement: function (el) {
            if (el == null) return null;
            if (typeof(el) == "string") return document.getElementById(el);
            else if (el.context || el.length != null) return el[0];
            else return el;
        },

        /**
         * removes an element from the DOM.  doing it via the library is
         * safer from a memory perspective, as it ix expected that the library's
         * remove method will unbind any event listeners before removing the element from the DOM.
         */
        removeElement: function (element) {
            _getElementObject(element).remove();
        },

// ---------------------------- END DOM MANIPULATION ---------------------------------------

// ---------------------------- MISCELLANEOUS ---------------------------------------

        /**
         * animates the given element.
         */
        doAnimate: function (el, properties, options) {
            $(el).animate(properties, options);
        },

// ---------------------------- END MISCELLANEOUS ---------------------------------------		

// -------------------------------------- DRAG/DROP	---------------------------------

        destroyDraggable: function (el) {
            if ($(el).data("draggable"))
                $(el).draggable("destroy");
        },

        destroyDroppable: function (el) {
            if ($(el).data("droppable"))
                $(el).droppable("destroy");
        },
        /**
         * initialises the given element to be draggable.
         */
        initDraggable: function (el, options, isPlumbedComponent) {
            options = options || {};
            el = $(el);

            options.start = _ju.wrap(options.start, function () {
                $("body").addClass(this.dragSelectClass);
            }, false);

            options.stop = _ju.wrap(options.stop, function () {
                $("body").removeClass(this.dragSelectClass);
            });

            // remove helper directive if present and no override
            if (!options.doNotRemoveHelper)
                options.helper = null;


            if (isPlumbedComponent == "internal")
                options.scope = options.scope || _jp.Defaults.Scope;

            el.draggable(options);
        },

        /**
         * initialises the given element to be droppable.
         */
        initDroppable: function (el, options) {
            options.scope = options.scope || _jp.Defaults.Scope;
            $(el).droppable(options);
        },

        isAlreadyDraggable: function (el) {
            return $(el).hasClass("ui-draggable");
        },

        /**
         * returns whether or not drag is supported (by the library, not whether or not it is disabled) for the given element.
         */
        isDragSupported: function (el, options) {
            return $(el).draggable;
        },

        /**
         * returns whether or not drop is supported (by the library, not whether or not it is disabled) for the given element.
         */
        isDropSupported: function (el, options) {
            return $(el).droppable;
        },
        /**
         * takes the args passed to an event function and returns you an object representing that which is being dragged.
         */
        getDragObject: function (eventArgs) {
            //return eventArgs[1].draggable || eventArgs[1].helper;
            return eventArgs[1].helper || eventArgs[1].draggable;
        },

        getDragScope: function (el) {
            return $(el).draggable("option", "scope");
        },

        getDropEvent: function (args) {
            return args[0];
        },

        getDropScope: function (el) {
            return $(el).droppable("option", "scope");
        },
        /**
         * takes the args passed to an event function and returns you an object that gives the
         * position of the object being moved, as a js object with the same params as the result of
         * getOffset, ie: { left: xxx, top: xxx }.
         *
         * different libraries have different signatures for their event callbacks.
         * see getDragObject as well
         */
        getUIPosition: function (eventArgs, zoom, dontAdjustHelper) {
            var ret;
            zoom = zoom || 1;
            if (eventArgs.length == 1) {
                ret = { left: eventArgs[0].pageX, top: eventArgs[0].pageY };
            }
            else {
                var ui = eventArgs[1],
                    _offset = ui.position;//ui.offset;

                ret = _offset || ui.absolutePosition;

                // adjust ui position to account for zoom, because jquery ui does not do this.
                if (!dontAdjustHelper) {
                    ui.position.left /= zoom;
                    ui.position.top /= zoom;
                }
            }
            return { left: ret.left, top: ret.top  };
        },

        setDragFilter: function (el, filter) {
            if (_jp.isAlreadyDraggable(el))
                $(el).draggable("option", "cancel", filter);
        },

        setElementDraggable: function (el, draggable) {
            $(el).draggable("option", "disabled", !draggable);
        },

        setDragScope: function (el, scope) {
            $(el).draggable("option", "scope", scope);
        },
        /**
         * mapping of drag events for jQuery
         */
        dragEvents: {
            'start': 'start', 'stop': 'stop', 'drag': 'drag', 'step': 'step',
            'over': 'over', 'out': 'out', 'drop': 'drop', 'complete': 'complete'
        },
        animEvents: {
            'step': "step", 'complete': 'complete'
        },
        getOriginalEvent: function (e) {
            return e.originalEvent || e;
        },
        /**
         * note that jquery ignores the name of the event you wanted to trigger, and figures it out for itself.
         * Mottle does not.  (YUI, in fact, cannot even pass an original event...but we don't support YUI any longer).
         * @param el
         * @param event
         * @param originalEvent
         */
        trigger: function (el, event, originalEvent) {
            el = this.getDOMElement(el);
            var h = jQuery._data($(el)[0], "handle");
            h(originalEvent);
        }

// -------------------------------------- END DRAG/DROP	---------------------------------		

    });

    $(document).ready(_jp.init);

})(jQuery, this);

