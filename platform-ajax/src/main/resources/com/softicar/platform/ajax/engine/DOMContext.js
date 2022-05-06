
// constants
var AJAX_CSS_PSEUDO_CLASS_HIDDEN = 'hidden';
var TIMEOUT_RETRY_DELAY = 500;

// class definition of DOMContext.
function _DOMContext_()
{
	var m_parent = parent;
	var m_document = document;
	var m_head = null;
	var m_body = null;
	var m_nodeMap = {};
	var m_locked = false;
	var m_lockTime = null;
	var m_zIndex = 100;
	var m_lastScheduledTimeout = null;

	this.getDocument = function()
	{
		return m_document;
	};

	this.getBody = function()
	{
		return m_body;
	};

	this.getNodeMap = function()
	{
		return m_nodeMap;
	};

	this.allocateZIndex = function()
	{
		m_zIndex++;
		return m_zIndex;
	};

	this.setMaximumZIndex = function(node)
	{
		if(node.style.zIndex != m_zIndex) {
			m_zIndex++;
			node.style.zIndex = m_zIndex;
		}
	};

	this.getNode = function(id)
	{
		var node = m_nodeMap[id];
		return node;
	};

	// initializes the head object of this document
	this.initializeHead = function(nodeId)
	{
		m_head = m_document.getElementsByTagName('head')[0];
		m_head.id = 'n' + nodeId;
		m_nodeMap[nodeId] = m_head;
	};

	// initializes the body object of this document
	this.initializeBody = function(nodeId)
	{
		m_body = m_document.getElementsByTagName('body')[0];
		m_body.id = 'n' + nodeId;
		m_nodeMap[nodeId] = m_body;
	};

	// executes the given Javascript code
	this.executeScriptCode = function(scriptCode)
	{
		eval(scriptCode);
	};

	// creates a new HTML element
	this.createElement = function(nodeID, htmlType)
	{
		// create node
		var node = m_document.createElement(htmlType);
		node.id = 'n' + nodeID;
		m_nodeMap[nodeID] = node;

		// add to value node map if it's a value node
		switch(htmlType.toUpperCase())
		{
		case 'INPUT':
		case 'TEXTAREA':
		case 'SELECT':
			GLOBAL.addValueNode(nodeID, node);
		}
	};

	// creates a new text node
	this.createTextNode = function(nodeID, text)
	{
		var n = m_document.createTextNode(text);
		m_nodeMap[nodeID] = n;
	};

	// appends the specified child to the parent
	this.appendChild = function(parentID, childID)
	{
		this.getNode(parentID).appendChild(this.getNode(childID));
	};

	// appends the specified child to the parent
	this.insertBefore = function(parentID, childID, otherChildID)
	{
		this.getNode(parentID).insertBefore(this.getNode(childID), this.getNode(otherChildID));
	};

	// removes the specified child from the parent
	this.removeChild = function(parentID, childID)
	{
		this.getNode(parentID).removeChild(this.getNode(childID));
	};

	// replaces the old child of the parent with the new child
	this.replaceChild = function(parentID, newChildID, oldChildID)
	{
		this.getNode(parentID).replaceChild(this.getNode(newChildID), this.getNode(oldChildID));
	};

	this.setAttribute = function(nodeID, attribute, value)
	{
		this.getNode(nodeID).setAttribute(attribute, value);
	};
}

// Global DOMContext object.
var _DOM_CONTEXT_ = new _DOMContext_();

function getBody()
{
	return _DOM_CONTEXT_.getBody();
}

function getNode(id)
{
	return document.getElementById(id);
}

// ******************************************************************************** //
// * Input functions                                                              * //
// ******************************************************************************** //

// implementation taken from here: http://web.archive.org/web/20110102112946/http://www.scottklarr.com/topic/425/how-to-insert-text-into-a-textarea-where-the-cursor-is/
_DOMContext_.prototype.insertAtCaret = function(inputNodeId, text) {
	var inputNode = GLOBAL.context.getNode(inputNodeId);
	var scrollPos = inputNode.scrollTop;
	var strPos = 0;
	var browser = (inputNode.selectionStart || inputNode.selectionStart == '0') ? "nonie" : (document.selection ? "ie" : false);

	if (browser == "nonie") {
		strPos = inputNode.selectionStart;
	} else if (browser == "ie") {
		inputNode.focus();
		var range = document.selection.createRange();
		range.moveStart('character', -inputNode.value.length);
		strPos = range.text.length;
	}

	var front = inputNode.value.substring(0, strPos);
	var back = inputNode.value.substring(strPos, inputNode.value.length);
	inputNode.value = front + text + back;
	strPos = strPos + text.length;

	if (browser == "nonie") {
		inputNode.selectionStart = strPos;
		inputNode.selectionEnd = strPos;
		inputNode.focus();
	} else if (browser == "ie") {
		inputNode.focus();
		var range = document.selection.createRange();
		range.moveStart('character', -inputNode.value.length);
		range.moveStart('character', strPos);
		range.moveEnd('character', 0);
		range.select();
	}

	inputNode.scrollTop = scrollPos;
};

_DOMContext_.prototype.moveCaretToPosition = function(inputNodeId, position) {
	var inputNode = GLOBAL.context.getNode(inputNodeId);
	if (typeof inputNode.selectionStart == "number") {
		inputNode.selectionStart = inputNode.selectionEnd = position;
	}
};

// ******************************************************************************** //
// * Form submit                                                                  * //
// ******************************************************************************** //

// formNodeID: the node ID of the form to submit
_DOMContext_.prototype.submitForm = function(formNodeID)
{
	if(lock(LOCK_REASON_UPLOAD))
	{
		var parameters = {};
		parameters.a = AJAX_REQUEST_UPLOAD;
		parameters.n = 'n' + formNodeID;
		GLOBAL.copyNodeValues(parameters);

		var form = GLOBAL.context.getNode(formNodeID);
		ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters, form));
		ACTION_QUEUE.executeNextAction();
	}
	else
		alert(LOCK_MESSAGE);
};

// ******************************************************************************** //
// * Events                                                                       * //
// ******************************************************************************** //

// schedule a timeout.
_DOMContext_.prototype.scheduleTimeout = function(timeoutNodeID, milliseconds)
{
	m_lastScheduledTimeout = setTimeout("_DOM_CONTEXT_.handleTimeout(" + timeoutNodeID + ");", milliseconds);
};

// clear a previously scheduled timeout
_DOMContext_.prototype.clearLastScheduledTimeout = function()
{
	if(!(typeof m_lastScheduledTimeout === 'undefined'))
	{
		clearTimeout(m_lastScheduledTimeout);
	}
};

_DOMContext_.prototype.handleTimeout = function(timeoutNodeID)
{
	if(lock(LOCK_REASON_TIMEOUT))
	{
		var parameters =
		{
			'a': AJAX_REQUEST_TIMEOUT,
			'n': 'n' + timeoutNodeID
		};

		GLOBAL.copyNodeValues(parameters);

		ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters));
		ACTION_QUEUE.executeNextAction();
	}
	else
		// re-schedule timeout
		_DOM_CONTEXT_.scheduleTimeout(timeoutNodeID, TIMEOUT_RETRY_DELAY);
};

_DOMContext_.prototype.listenToEvent = function(nodeID, event, doListen)
{
	var n = this.getNode(nodeID);
	if(n == null)
		return;

	var handler = doListen? handleDomEvent : undefined;

	switch(event)
	{
	case 'CLICK':       n.onclick = handler; break;
	case 'CHANGE':      DOM_EVENT_ENGINE.setListenToChangeEvent(n, doListen); break;
	case 'CONTEXTMENU': n.oncontextmenu = (event => {handler(event); event.preventDefault();}); break;
	case 'DBLCLICK':    n.ondblclick = handler; break;
	case 'ENTER':       setListenToKey(n, event, doListen); break;
	case 'ESCAPE':      setListenToKey(n, event, doListen); break;
	case 'KEYPRESS':    n.onkeypress = handler; break;
	case 'SPACE':       setListenToKey(n, event, doListen); break;
	case 'TAB':         setListenToKey(n, event, doListen); break;
	default: alert('Unknown event ' + event + '.');
	}
};

var KEY_HANDLERS = new Object();

function getEventKey(eventName) {
	switch(eventName) {
	case 'ENTER':  return DOM_VK_ENTER;
	case 'ESCAPE': return DOM_VK_ESCAPE;
	case 'SPACE':  return DOM_VK_SPACE;
	case 'TAB':    return DOM_VK_TAB;
	default:       return undefined;
	}
}

function KeyHandler(node) {
	var keyCodes = new Object();
	var fireOnKeyUp = new Object();
	var preventDefault = new Object();
	var cssClassApplier = new Object();
	var lastKeyDown = null;

	this.setListenTo = function(key, eventName, enabled) {
		if(enabled) {
			keyCodes[key] = eventName;
		} else {
			delete keyCodes[key];
		}
	};

	this.setFireOnKeyUp = function(key, enabled) {
		fireOnKeyUp[key] = enabled;
	};

	this.setPreventDefault = function(key, enabled) {
		preventDefault[key] = enabled;
	};

	this.setCssClassApplier = function(key, applier) {
		cssClassApplier[key] = applier;
	};

	this.handleKeyDown = function(event) {
		var eventName = keyCodes[event.keyCode];
		if(eventName) {
			if(!fireOnKeyUp[event.keyCode] && !event.repeat) {
				sendOrDelegateEvent(node, event, eventName);
			}
			if(cssClassApplier[event.keyCode]) {
				cssClassApplier[event.keyCode].addClasses();
			}
			stopFurtherHandling(event);
		}
		lastKeyDown = event.keyCode;
	};

	this.handleKeyUp = function(event) {
		var eventName = keyCodes[event.keyCode];
		if(eventName) {
			if(fireOnKeyUp[event.keyCode] && lastKeyDown == event.keyCode) {
				sendOrDelegateEvent(node, event, eventName);
			}
			if(cssClassApplier[event.keyCode]) {
				cssClassApplier[event.keyCode].removeClasses();
			}
			stopFurtherHandling(event);
		}
		lastKeyDown = null;
	};

	function stopFurtherHandling(event) {
		event.stopPropagation();
		if(preventDefault[event.keyCode] != false) {
			event.preventDefault();
		}
	}

	// install event listeners, but only if none are defined yet
	if(!node.onkeydown && !node.onkeyup) {
		node.onkeydown = this.handleKeyDown;
		node.onkeyup = this.handleKeyUp;
	} else {
		console.log('warning: skipped installation of keyboard event listeners');
	}
}

function CssClassApplier(node, classNames) {
	var classes = classNames != ""? classNames.split(" ") : [];

	this.addClasses = function() {
		for(var i in classes) {
			node.classList.add(classes[i]);
		}
	};

	this.removeClasses = function() {
		for(var i in classes) {
			node.classList.remove(classes[i]);
		}
	};
}

function getKeyHandler(node) {
	var handler = KEY_HANDLERS[node.id];
	if(!handler) {
		handler = new KeyHandler(node);
		KEY_HANDLERS[node.id] = handler;
	}
	return handler;
}

function setListenToKey(node, eventName, enabled) {
	var key = getEventKey(eventName);
	getKeyHandler(node).setListenTo(key, eventName, enabled);
}

function setFireOnKeyUp(node, eventName, enabled) {
	var key = getEventKey(eventName);
	getKeyHandler(node).setFireOnKeyUp(key, enabled);
}

function setPreventDefaultBehavior(node, eventName, enabled) {
	var key = getEventKey(eventName);
	getKeyHandler(node).setPreventDefault(key, enabled);
}

function setCssClassOnKeyDown(node, eventName, cssTargetNode, cssClassNames) {
	var key = getEventKey(eventName);
	getKeyHandler(node).setCssClassApplier(key, new CssClassApplier(cssTargetNode, cssClassNames));
}

function handleDomEvent(event) {
	sendOrDelegateEvent(event.currentTarget, event, event.type);
}

function sendEventToServer(event, eventType) {
	if(lock(LOCK_REASON_DOM_EVENT)) {
		var boundingRect = event.currentTarget.getBoundingClientRect();
		var parameters = {
			'a' : AJAX_REQUEST_DOM_EVENT,
			'n' : event.currentTarget.id,
			'e' : eventType,
			'cx': event.clientX? event.clientX : (boundingRect.x + boundingRect.width / 2),
			'cy': event.clientY? event.clientY : (boundingRect.y + boundingRect.height / 2),
			'rx': event.clientX? event.clientX - boundingRect.left : null,
			'ry': event.clientY? event.clientY - boundingRect.top : null,
			'sx': window.pageXOffset,
			'sy': window.pageYOffset,
			'ww': window.innerWidth,
			'wh': window.innerHeight,
			'k' : event.keyCode? event.keyCode : null
		};

		addOptionalFlag(parameters, 'altKey', event.altKey);
		addOptionalFlag(parameters, 'ctrlKey', event.ctrlKey);
		addOptionalFlag(parameters, 'metaKey', event.metaKey);
		addOptionalFlag(parameters, 'shiftKey', event.shiftKey);

		GLOBAL.copyNodeValues(parameters);

		ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters));
		ACTION_QUEUE.executeNextAction();
	} else {
		alert(LOCK_MESSAGE);
	}
}

function addOptionalFlag(parameters, name, flag) {
	if(flag) {
		parameters[name] = 1;
	}
}

// ******************************************************************************** //
// * Session Timeout                                                              * //
// ******************************************************************************** //

var SESSION_TIMED_OUT = false;
var SESSION_TIMEOUT_DIALOG;

function setSessionTimeoutDialog(dialog)
{
	SESSION_TIMEOUT_DIALOG = dialog;
}

function handleSessionTimeout()
{
	SESSION_TIMED_OUT = true;

	if(SESSION_TIMEOUT_DIALOG)
	{
		SESSION_TIMEOUT_DIALOG.style.zIndex = _DOM_CONTEXT_.allocateZIndex();
		SESSION_TIMEOUT_DIALOG.classList.remove(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
	}
}

// ******************************************************************************** //
// * Working Indicator                                                            * //
// ******************************************************************************** //

var WORKING_INDICATOR_ENABLED = true;
var WORKING_INDICATOR;

function setWorkingIndicator(indicator)
{
	WORKING_INDICATOR = indicator;
}

function showWorkingIndicator()
{
	if(WORKING_INDICATOR && WORKING_INDICATOR_ENABLED)
	{
		WORKING_INDICATOR.style.zIndex = _DOM_CONTEXT_.allocateZIndex();
		WORKING_INDICATOR.classList.remove(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
	}
}

function hideWorkingIndicator()
{
	if(WORKING_INDICATOR)
	{
		WORKING_INDICATOR.classList.add(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
	}
}

function setWorkingIndicatorEnabled(enabled)
{
	WORKING_INDICATOR_ENABLED = enabled;
}

// ******************************************************************************** //
// * Global event lock                                                            * //
// ******************************************************************************** //

var GLOBAL_LOCK_COUNTER = 0;
var LOCK_REASON = null;

// This allocates the global lock. This function returns true
// if the lock could be successfully allocated, false otherwise.
function lock(lockReason)
{
	// NOTE: Assuming that incrementation is an atomic operation.
	++GLOBAL_LOCK_COUNTER;

	if(GLOBAL_LOCK_COUNTER == 1)
	{
		LOCK_REASON = lockReason;
		showWorkingIndicator();
		return true;
	}
	else
	{
		--GLOBAL_LOCK_COUNTER;
		return false;
	}
}

// This releases the global lock.
function unlock()
{
	// NOTE: Lock counter may be greater than one if right
	//       now some one else is trying to get the lock.
	if(GLOBAL_LOCK_COUNTER > 0)
	{
		hideWorkingIndicator();
		LOCK_REASON = null;
		--GLOBAL_LOCK_COUNTER;
		AUTO_COMPLETE_ENGINE.notifyChangeEventReturned();
	}
	else
		alert("Internal program error. Global lock counter may never become less than zero.");
}

// This returns true if the global lock is allocated.
function isLocked()
{
	return GLOBAL_LOCK_COUNTER > 0;
}

// ******************************************************************************** //
// * Abbreviations                                                                * //
// ******************************************************************************** //

var c = _DOM_CONTEXT_;
var d = c.getDocument();
var e = c.createElement;
var t = c.createTextNode;
var n = c.getNode;
function l(no,ev){c.listenToEvent(no,ev,true);};
function u(no,ev){c.listenToEvent(no,ev,false);};
function a(pa,ch){c.appendChild(pa,ch);};
function i(pa,ch,och){c.insertBefore(pa,ch,och);};
function r(pa,ch){c.removeChild(pa,ch);};
function p(pa,nch,och){c.replaceChild(pa,nch,och);};
function s(no,at,va){c.setAttribute(no,at,va);};
function E(pa,ch,ty){e(ch,ty);a(pa,ch);};
function T(pa,ch,te){t(ch,te);a(pa,ch);};
