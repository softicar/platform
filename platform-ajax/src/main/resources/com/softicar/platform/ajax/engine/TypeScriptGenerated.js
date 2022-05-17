"use strict";
class AjaxEngine {
    constructor() {
        this.nodes = new Map();
        this.zIndex = 100;
    }
    allocateZIndex() {
        this.zIndex += 1;
        return '' + this.zIndex;
    }
    setMaximumZIndex(node) {
        if (node.style.zIndex != '' + this.zIndex) {
            node.style.zIndex = this.allocateZIndex();
        }
    }
    getNode(nodeId) {
        return this.nodes.get(nodeId);
    }
    getElement(nodeId) {
        return this.nodes.get(nodeId);
    }
    initializeHead(nodeId) {
        this.initializeNode(nodeId, document.head);
    }
    initializeBody(nodeId) {
        this.initializeNode(nodeId, document.body);
    }
    executeScriptCode(scriptCode) {
        eval(scriptCode);
    }
    createElement(nodeId, tag) {
        let node = document.createElement(tag);
        this.initializeNode(nodeId, node);
        switch (tag.toUpperCase()) {
            case 'INPUT':
            case 'TEXTAREA':
            case 'SELECT':
                VALUE_NODE_MAP.addNode(node);
        }
    }
    createTextNode(nodeId, text) {
        let node = document.createTextNode(text);
        this.nodes.set(nodeId, node);
    }
    appendChild(parentId, childId) {
        this.getElement(parentId).appendChild(this.getNode(childId));
    }
    insertBefore(parentId, childId, otherChildId) {
        this.getElement(parentId).insertBefore(this.getNode(childId), this.getNode(otherChildId));
    }
    removeChild(parentId, childId) {
        this.getElement(parentId).removeChild(this.getNode(childId));
    }
    replaceChild(parentId, newChildId, oldChildId) {
        this.getElement(parentId).replaceChild(this.getNode(newChildId), this.getNode(oldChildId));
    }
    setAttribute(nodeId, attribute, value) {
        this.getElement(nodeId).setAttribute(attribute, value);
    }
    listenToEvent(nodeID, event, doListen) {
        let element = this.getElement(nodeID);
        if (element == null)
            return;
        let handler = doListen ? handleDomEvent : null;
        switch (event) {
            case 'CLICK':
                element.onclick = handler;
                break;
            case 'CHANGE':
                CHANGE_EVENT_MANAGER.setListenToChangeEvent(element, doListen);
                break;
            case 'CONTEXTMENU':
                element.oncontextmenu = doListen ? (event => { handleDomEvent(event); event.preventDefault(); }) : null;
                break;
            case 'DBLCLICK':
                element.ondblclick = handler;
                break;
            case 'ENTER':
                KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen);
                break;
            case 'ESCAPE':
                KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen);
                break;
            case 'KEYPRESS':
                element.onkeypress = handler;
                break;
            case 'SPACE':
                KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen);
                break;
            case 'TAB':
                KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen);
                break;
            default: alert('Unknown event ' + event + '.');
        }
    }
    initializeNode(nodeId, node) {
        node.id = 'n' + nodeId;
        this.nodes.set(nodeId, node);
    }
}
const _DOM_CONTEXT_ = new AjaxEngine();
const c = _DOM_CONTEXT_;
function n(nodeId) {
    return c.getNode(nodeId);
}
function e(nodeId, tag) {
    return c.createElement(nodeId, tag);
}
function t(nodeId, text) {
    return c.createTextNode(nodeId, text);
}
function l(nodeId, eventType) {
    c.listenToEvent(nodeId, eventType, true);
}
function u(nodeId, eventType) {
    c.listenToEvent(nodeId, eventType, false);
}
function a(parentId, childId) {
    c.appendChild(parentId, childId);
}
function i(parentId, childId, oherChildId) {
    c.insertBefore(parentId, childId, oherChildId);
}
function r(parentId, childId) {
    c.removeChild(parentId, childId);
}
function p(parentId, newChildId, oldChldId) {
    c.replaceChild(parentId, newChildId, oldChldId);
}
function E(parentId, childId, tag) {
    e(childId, tag);
    a(parentId, childId);
}
function T(parentId, childId, text) {
    t(childId, text);
    a(parentId, childId);
}
function s(elementId, attribute, value) {
    c.setAttribute(elementId, attribute, value);
}
const AJAX_CSS_PSEUDO_CLASS_HIDDEN = 'hidden';
const AJAX_REQUEST_KEEP_ALIVE = 2;
const AJAX_REQUEST_TIMEOUT = 5;
const AJAX_REQUEST_DOM_EVENT = 6;
const AJAX_REQUEST_DRAG_AND_DROP = 7;
const AJAX_REQUEST_UPLOAD = 8;
const AJAX_REQUEST_AUTO_COMPLETE = 10;
const DOM_VK_TAB = 9;
const DOM_VK_ENTER = 13;
const DOM_VK_ESCAPE = 27;
const DOM_VK_SPACE = 32;
const DOM_VK_UP = 38;
const DOM_VK_DOWN = 40;
const KEEP_ALIVE_REQUEST_DELAY = 3 * 60 * 1000;
const HTTP_REQUEST_STATE_UNSET = 0;
const HTTP_REQUEST_STATE_OPENED = 1;
const HTTP_REQUEST_STATE_HEADERS_RECEIVED = 2;
const HTTP_REQUEST_STATE_LOADING = 3;
const HTTP_REQUEST_STATE_DONE = 4;
const HTTP_STATUS_SUCCESS = 200;
const HTTP_STATUS_GONE = 410;
const TIMEOUT_RETRY_DELAY = 500;
function makeDraggable(movedNode, draggedNode, limitingNode, notifyOnDrop) {
    new DragContext(movedNode, limitingNode, notifyOnDrop).setup(draggedNode);
}
class DragContext {
    constructor(movedNode, limitingNode, notifyOnDrop) {
        this.cursorStart = new Point();
        this.dragStart = new Point();
        this.dragPosition = new Point();
        this.moveHandler = (event) => this.onMove(event);
        this.dropHandler = (event) => this.onDrop(event);
        this.movedNode = movedNode;
        this.limitingNode = limitingNode;
        this.notifyOnDrop = notifyOnDrop;
    }
    setup(draggedNode) {
        draggedNode.addEventListener("mousedown", event => this.onDragStart(event));
        draggedNode.addEventListener("touchstart", event => this.onDragStart(event));
        draggedNode.style.userSelect = "none";
        draggedNode.style.touchAction = "none";
    }
    onDragStart(event) {
        this.addDragListener();
        this.setDocumentTextSelection(false);
        this.cursorStart = this.getCursorPosition(event);
        this.dragStart = this.getMovedNodePosition();
        this.dragPosition = this.dragStart;
        _DOM_CONTEXT_.setMaximumZIndex(this.movedNode);
    }
    onMove(event) {
        let cursor = this.getCursorPosition(event);
        let minX = this.limitingNode ? this.limitingNode.getBoundingClientRect().left : 0;
        let minY = this.limitingNode ? this.limitingNode.getBoundingClientRect().top : 0;
        if (cursor.x >= minX && cursor.y >= minY) {
            this.dragPosition = this.dragStart.plus(cursor.minus(this.cursorStart));
            this.movedNode.style.left = this.dragPosition.x + "px";
            this.movedNode.style.top = this.dragPosition.y + "px";
        }
    }
    onDrop(event) {
        this.removeDragListener();
        this.setDocumentTextSelection(true);
        if (this.notifyOnDrop && (this.dragPosition.x != this.dragStart.x || this.dragPosition.y != this.dragStart.y)) {
            if (AJAX_REQUEST_LOCK.lock()) {
                let message = new AjaxRequestMessage()
                    .setAction(AJAX_REQUEST_DRAG_AND_DROP)
                    .setNode(this.movedNode)
                    .setDragStart(this.dragStart)
                    .setDragPosition(this.dragPosition);
                new AjaxRequest(message).send();
            }
        }
    }
    getCursorPosition(event) {
        if (event instanceof MouseEvent) {
            return new Point(event.clientX, event.clientY);
        }
        else if (event instanceof TouchEvent) {
            let firstTouch = event.touches[0];
            return new Point(Math.round(firstTouch.clientX), Math.round(firstTouch.clientY));
        }
        else {
            return new Point();
        }
    }
    getMovedNodePosition() {
        let style = this.movedNode.style;
        let x = style.left ? parseInt(style.left) : 0;
        let y = style.top ? parseInt(style.top) : 0;
        return new Point(x, y);
    }
    setDocumentTextSelection(enabled) {
        document.onselectstart = function () { return enabled; };
    }
    addDragListener() {
        document.addEventListener("mousemove", this.moveHandler, true);
        document.addEventListener("touchmove", this.moveHandler, true);
        document.addEventListener("mouseup", this.dropHandler, true);
        document.addEventListener("touchend", this.dropHandler, true);
    }
    removeDragListener() {
        document.removeEventListener("mousemove", this.moveHandler, true);
        document.removeEventListener("touchmove", this.moveHandler, true);
        document.removeEventListener("mouseup", this.dropHandler, true);
        document.removeEventListener("touchend", this.dropHandler, true);
    }
}
class KeepAlive {
    constructor(delay) {
        this.delay = delay;
        this.handlerId = -1;
    }
    schedule() {
        if (this.handlerId >= 0) {
            clearTimeout(this.handlerId);
        }
        this.handlerId = setTimeout(this.handleKeepAliveTimeout, this.delay);
    }
    handleKeepAliveTimeout() {
        if (SESSION_TIMED_OUT) {
            return;
        }
        if (AJAX_REQUEST_LOCK.lock()) {
            let message = new AjaxRequestMessage().setAction(AJAX_REQUEST_KEEP_ALIVE);
            new AjaxRequest(message).send();
        }
        else {
            KEEP_ALIVE.schedule();
        }
    }
}
const KEEP_ALIVE = new KeepAlive(KEEP_ALIVE_REQUEST_DELAY);
class Point {
    constructor(x = 0, y = 0) {
        this._x = x;
        this._y = y;
    }
    get x() {
        return this._x;
    }
    get y() {
        return this._y;
    }
    plus(point) {
        return new Point(this.x + point.x, this.y + point.y);
    }
    minus(point) {
        return new Point(this.x - point.x, this.y - point.y);
    }
}
let SESSION_TIMED_OUT = false;
let SESSION_TIMEOUT_DIALOG;
function setSessionTimeoutDialog(dialog) {
    SESSION_TIMEOUT_DIALOG = dialog;
}
function handleSessionTimeout() {
    SESSION_TIMED_OUT = true;
    if (SESSION_TIMEOUT_DIALOG) {
        SESSION_TIMEOUT_DIALOG.style.zIndex = _DOM_CONTEXT_.allocateZIndex();
        SESSION_TIMEOUT_DIALOG.classList.remove(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
    }
}
function insertTextAtCaret(input, text) {
    let selectionStart = input.selectionStart;
    if (selectionStart !== null) {
        let value = input.value;
        let front = value.substring(0, selectionStart);
        let back = value.substring(selectionStart, value.length);
        let scrollTop = input.scrollTop;
        input.value = front + text + back;
        input.selectionStart = selectionStart;
        input.selectionEnd = selectionStart;
        input.scrollTop = scrollTop;
        input.focus();
    }
}
function moveCaretToPosition(input, position) {
    input.selectionStart = input.selectionEnd = position;
}
function sendUploadRequestThroughForm(form) {
    if (AJAX_REQUEST_LOCK.lock()) {
        let message = new AjaxRequestMessage()
            .setAction(AJAX_REQUEST_UPLOAD)
            .setNode(form);
        new AjaxRequest(message, form).send();
    }
    else {
        alert(LOCK_MESSAGE);
    }
}
function pushBrowserHistoryState(page, url) {
    history.pushState({ page: page }, "", url);
}
class ValueNodeMap {
    constructor() {
        this.states = new Map();
    }
    addNode(node) {
        this.states.set(node, new ValueNodeState(node));
    }
    approveNodeValues() {
        this.states.forEach((state, _) => state.approveValue());
    }
    copyNodeValues(message) {
        this.states.forEach((state, _) => state.updatePendingValue(message));
    }
    isValueChanged(node) {
        return this.getState(node).isValueChanged();
    }
    setValue(node, value) {
        node.value = value;
        this.getState(node).assumeValue(value);
        AUTO_COMPLETE_ENGINE.setCommittedValue(node, value);
    }
    setSelectedOptions(select, options) {
        let type = select.type;
        if (type == "select-one") {
            options[0].selected = true;
        }
        else if (type == "select-multiple") {
            for (let option of select.options) {
                option.selected = options.includes(option);
            }
        }
        else {
            throw new Error(`Internal error: Unknown select type '${type}' on node ${select.id}.`);
        }
        this.getState(select).assumeValue(this.getState(select).getCurrentValue());
    }
    getState(node) {
        let state = this.states.get(node);
        if (state) {
            return state;
        }
        else {
            throw new Error(`Internal error: Missing value state for node ${node.id}.`);
        }
    }
}
class ValueNodeState {
    constructor(node) {
        this.node = node;
        this.approvedValue = this.pendingValue = this.getCurrentValue();
    }
    approveValue() {
        this.approvedValue = this.pendingValue;
    }
    isValueChanged() {
        return this.getCurrentValue() != this.approvedValue;
    }
    assumeValue(value) {
        this.approvedValue = this.pendingValue = value;
    }
    updatePendingValue(message) {
        this.pendingValue = this.getCurrentValue();
        if (this.pendingValue != this.approvedValue) {
            message.setNodeValue(this.node, this.pendingValue);
        }
    }
    getCurrentValue() {
        switch (this.node.tagName.toUpperCase()) {
            case 'INPUT':
                let input = this.node;
                return '' + input.value;
            case 'TEXTAREA':
                let textArea = this.node;
                return '' + textArea.value;
            case 'SELECT':
                let value = [];
                let select = this.node;
                for (let option of select.selectedOptions) {
                    value.push(option.id);
                }
                return value.join(',');
        }
        return '';
    }
}
const VALUE_NODE_MAP = new ValueNodeMap();
let WORKING_INDICATOR_ENABLED = true;
let WORKING_INDICATOR;
function setWorkingIndicator(indicator) {
    WORKING_INDICATOR = indicator;
}
function showWorkingIndicator() {
    if (WORKING_INDICATOR && WORKING_INDICATOR_ENABLED) {
        WORKING_INDICATOR.style.zIndex = _DOM_CONTEXT_.allocateZIndex();
        WORKING_INDICATOR.classList.remove(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
    }
}
function hideWorkingIndicator() {
    if (WORKING_INDICATOR) {
        WORKING_INDICATOR.classList.add(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
    }
}
function setWorkingIndicatorEnabled(enabled) {
    WORKING_INDICATOR_ENABLED = enabled;
}
class ChangeEventManager {
    setListenToChangeEvent(node, listen) {
        if (listen) {
            node.onchange = event => this.handleChangeEvent(event);
        }
        else {
            node.onchange = null;
        }
    }
    handleChangeEvent(event) {
        let node = event.currentTarget;
        if (event.isTrusted && AUTO_COMPLETE_ENGINE.isEnabledForInput(node)) {
            return;
        }
        if (VALUE_NODE_MAP.isValueChanged(node)) {
            sendOrDelegateEvent(node, event, event.type);
        }
    }
}
const CHANGE_EVENT_MANAGER = new ChangeEventManager();
function handleDomEvent(event) {
    sendOrDelegateEvent(event.currentTarget, event, event.type);
}
function sendEventToServer(event, eventType) {
    if (AJAX_REQUEST_LOCK.lock()) {
        let element = event.currentTarget;
        let boundingRect = element.getBoundingClientRect();
        let message = new AjaxRequestMessage()
            .setAction(AJAX_REQUEST_DOM_EVENT)
            .setNode(element)
            .setEventType(eventType)
            .setWindowPageOffset(new Point(window.pageXOffset, window.pageYOffset))
            .setWindowInnerSize(new Point(window.innerWidth, window.innerHeight));
        if (event instanceof MouseEvent) {
            message.setMousePosition(new Point(event.clientX, event.clientY));
            message.setMouseRelativePosition(new Point(event.clientX - boundingRect.left, event.clientY - boundingRect.top));
        }
        else {
            message.setMousePosition(new Point(boundingRect.x + boundingRect.width / 2, boundingRect.y + boundingRect.height / 2));
        }
        if (event instanceof KeyboardEvent) {
            message.setKeyCode(event.keyCode);
        }
        if (event instanceof KeyboardEvent || event instanceof MouseEvent) {
            message.setModifierKey('altKey', event.altKey);
            message.setModifierKey('ctrlKey', event.ctrlKey);
            message.setModifierKey('metaKey', event.metaKey);
            message.setModifierKey('shiftKey', event.shiftKey);
        }
        new AjaxRequest(message).send();
    }
    else {
        alert(LOCK_MESSAGE);
    }
}
const EVENT_DELEGATIONS = new Map();
function getOrCreateEventDelegator(element) {
    let delegator = EVENT_DELEGATIONS.get(element);
    if (!delegator) {
        delegator = new EventToClickDelegator();
        EVENT_DELEGATIONS.set(element, delegator);
    }
    return delegator;
}
function setClickTargetForEventDelegation(element, eventType, targetNode) {
    getOrCreateEventDelegator(element).setDelegation(eventType, targetNode);
}
function sendOrDelegateEvent(element, event, eventType) {
    let delegator = EVENT_DELEGATIONS.get(element);
    if (delegator && delegator.isDelegated(eventType)) {
        delegator.delegateEvent(eventType);
    }
    else {
        sendEventToServer(event, eventType);
    }
    event.stopPropagation();
}
class EventToClickDelegator {
    constructor() {
        this.targets = new Map();
    }
    setDelegation(eventType, targetElement) {
        this.targets.set(eventType.toLowerCase(), targetElement);
    }
    isDelegated(eventType) {
        return this.getTargetNode(eventType) ? true : false;
    }
    delegateEvent(eventType) {
        this.getTargetNode(eventType).click();
    }
    getTargetNode(eventType) {
        return this.targets.get(eventType.toLowerCase());
    }
}
class KeyboardEventManager {
    constructor() {
        this.handlers = new Map();
    }
    setListenToKey(node, eventName, enabled) {
        let key = this.getKey(eventName);
        this.getKeyHandler(node).setListenTo(key, eventName, enabled);
    }
    setFireOnKeyUp(node, eventName, enabled) {
        let key = this.getKey(eventName);
        this.getKeyHandler(node).setFireOnKeyUp(key, enabled);
    }
    setPreventDefaultBehavior(node, eventName, enabled) {
        let key = this.getKey(eventName);
        this.getKeyHandler(node).setPreventDefault(key, enabled);
    }
    setCssClassOnKeyDown(node, eventName, cssTargetNode, cssClassNames) {
        let key = this.getKey(eventName);
        this.getKeyHandler(node).setCssClassApplier(key, new CssClassApplier(cssTargetNode, cssClassNames));
    }
    getKeyHandler(node) {
        let handler = this.handlers.get(node.id);
        if (!handler) {
            handler = new KeyboardEventHandler(node).install();
            this.handlers.set(node.id, handler);
        }
        return handler;
    }
    getKey(eventName) {
        switch (eventName) {
            case 'ENTER': return DOM_VK_ENTER;
            case 'ESCAPE': return DOM_VK_ESCAPE;
            case 'SPACE': return DOM_VK_SPACE;
            case 'TAB': return DOM_VK_TAB;
        }
        throw new Error("Internal error: Unsupported keyboard event name.");
    }
}
const KEYBOARD_EVENT_MANAGER = new KeyboardEventManager();
class KeyboardEventHandler {
    constructor(node) {
        this.keyCodes = new Map();
        this.fireOnKeyUp = new Map();
        this.preventDefault = new Map();
        this.cssClassApplier = new Map();
        this.lastKeyDown = 0;
        this.node = node;
    }
    install() {
        if (!this.node.onkeydown && !this.node.onkeyup) {
            this.node.onkeydown = event => this.handleKeyDown(event);
            this.node.onkeyup = event => this.handleKeyUp(event);
        }
        else {
            console.log('Warning: Skipped installation of keyboard event listeners.');
        }
        return this;
    }
    setListenTo(key, eventName, enabled) {
        if (enabled) {
            this.keyCodes.set(key, eventName);
            this.preventDefault.set(key, true);
        }
        else {
            this.keyCodes.delete(key);
        }
    }
    setFireOnKeyUp(key, enabled) {
        this.fireOnKeyUp.set(key, enabled);
    }
    setPreventDefault(key, enabled) {
        this.preventDefault.set(key, enabled);
    }
    setCssClassApplier(key, applier) {
        this.cssClassApplier.set(key, applier);
    }
    handleKeyDown(event) {
        let eventName = this.keyCodes.get(event.keyCode);
        if (eventName) {
            if (!this.fireOnKeyUp.get(event.keyCode) && !event.repeat) {
                sendOrDelegateEvent(this.node, event, eventName);
            }
            let applier = this.cssClassApplier.get(event.keyCode);
            if (applier) {
                applier.addClasses();
            }
            this.stopFurtherHandling(event);
        }
        this.lastKeyDown = event.keyCode;
    }
    handleKeyUp(event) {
        var eventName = this.keyCodes.get(event.keyCode);
        if (eventName) {
            if (this.fireOnKeyUp.get(event.keyCode) && this.lastKeyDown == event.keyCode) {
                sendOrDelegateEvent(this.node, event, eventName);
            }
            let applier = this.cssClassApplier.get(event.keyCode);
            if (applier) {
                applier.removeClasses();
            }
            this.stopFurtherHandling(event);
        }
        this.lastKeyDown = 0;
    }
    stopFurtherHandling(event) {
        event.stopPropagation();
        if (this.preventDefault.get(event.keyCode)) {
            event.preventDefault();
        }
    }
}
class CssClassApplier {
    constructor(node, classes) {
        this.node = node;
        this.classes = classes != "" ? classes.split(" ") : [];
    }
    addClasses() {
        this.classes.forEach(c => this.node.classList.add(c));
    }
    removeClasses() {
        this.classes.forEach(c => this.node.classList.remove(c));
    }
}
function scheduleTimeout(timeoutNode, milliseconds) {
    setTimeout(() => handleTimeout(timeoutNode), milliseconds);
}
function handleTimeout(timeoutNode) {
    if (AJAX_REQUEST_LOCK.lock()) {
        let message = new AjaxRequestMessage()
            .setAction(AJAX_REQUEST_TIMEOUT)
            .setNode(timeoutNode);
        new AjaxRequest(message).send();
    }
    else {
        scheduleTimeout(timeoutNode, TIMEOUT_RETRY_DELAY);
    }
}
class AjaxRequest {
    constructor(message, form = null) {
        this.message = message;
        this.form = form;
    }
    send() {
        this.message.copyNodeValues();
        this.message.setRequestIndex(AJAX_REQUEST_MANAGER.openRequest(this));
        if (this.form) {
            new FormRequest(this.form, response => this.handleFormRequestResponse(response))
                .setMessage(this.message.encodeToHex())
                .submitForm();
        }
        else {
            new HttpRequest()
                .setMessage(this.message.encode())
                .sendAsync(request => this.handleHttpRequestResponse(request));
        }
    }
    handleFormRequestResponse(response) {
        this.close();
        this.executeJavaScript(response);
    }
    handleHttpRequestResponse(request) {
        this.close();
        if (request.status == HTTP_STATUS_SUCCESS) {
            this.executeJavaScript(request.responseText);
        }
        else if (request.status == HTTP_STATUS_GONE) {
            handleSessionTimeout();
        }
        else if (request.status != 0) {
            alert("HTTP Error " + request.status + ": " + request.statusText);
        }
        else {
        }
    }
    close() {
        AJAX_REQUEST_MANAGER.closeRequest(this);
        AJAX_REQUEST_LOCK.release();
        KEEP_ALIVE.schedule();
    }
    executeJavaScript(javaScriptCode) {
        eval(javaScriptCode);
    }
}
class AjaxRequestLock {
    constructor() {
        this.locked = false;
    }
    lock() {
        if (!this.locked) {
            this.locked = true;
            showWorkingIndicator();
            return true;
        }
        else {
            return false;
        }
    }
    release() {
        if (this.locked) {
            this.locked = false;
            hideWorkingIndicator();
            AUTO_COMPLETE_ENGINE.notifyChangeEventReturned();
        }
        else {
            alert("Internal error: AJAX request lock already released.");
        }
    }
    isLocked() {
        return this.locked;
    }
}
const AJAX_REQUEST_LOCK = new AjaxRequestLock();
class AjaxRequestManager {
    constructor() {
        this.currentRequest = null;
        this.requestIndex = 0;
    }
    openRequest(request) {
        if (this.currentRequest == null) {
            this.currentRequest = request;
            return this.requestIndex;
        }
        else {
            throw new Error("Internal error: Tried to send two server request at the same time.");
        }
    }
    closeRequest(request) {
        if (request === this.currentRequest) {
            this.currentRequest = null;
            this.requestIndex += 1;
        }
        else {
            throw new Error("Internal error: Given request does not match current request.");
        }
    }
    getCurrentRequest() {
        return this.currentRequest;
    }
}
const AJAX_REQUEST_MANAGER = new AjaxRequestManager();
class AjaxRequestMessage {
    constructor() {
        this.data = new Map();
        this.setString('i', DOCUMENT_INSTANCE_UUID);
        this.setBooleanIfTrue('debug', DEBUG);
        this.setBooleanIfTrue('verbose', VERBOSE);
    }
    copyNodeValues() {
        VALUE_NODE_MAP.copyNodeValues(this);
    }
    setRequestIndex(requestIndex) {
        return this.setNumber('x', requestIndex);
    }
    setAction(action) {
        return this.setNumber('a', action);
    }
    setNode(node) {
        return this.setString('n', node.id);
    }
    setNodeValue(node, value) {
        return this.setString('V' + node.id.substring(1), value);
    }
    setEventType(eventType) {
        return this.setString('e', eventType);
    }
    setKeyCode(keyCode) {
        return this.setNumber('k', keyCode);
    }
    setModifierKey(name, value) {
        return this.setBooleanIfTrue(name, value);
    }
    setMousePosition(position) {
        return this.setPoint('c', position);
    }
    setMouseRelativePosition(position) {
        return this.setPoint('r', position);
    }
    setDragStart(start) {
        return this.setPoint('s', start);
    }
    setDragPosition(position) {
        return this.setPoint('d', position);
    }
    setWindowPageOffset(pageOffset) {
        return this.setPoint('s', pageOffset);
    }
    setWindowInnerSize(innerSize) {
        return this.setPoint('w', innerSize);
    }
    setAutoCompletePattern(pattern) {
        return this.setString('p', pattern);
    }
    encode() {
        return new AjaxRequestMessageEncoder(this.data).encode();
    }
    encodeToHex() {
        return new AjaxRequestMessageEncoder(this.data).encodeToHex();
    }
    setString(key, value) {
        this.data.set(key, value);
        return this;
    }
    setNumber(key, value) {
        return this.setString(key, '' + value);
    }
    setBooleanIfTrue(key, value) {
        if (value) {
            this.setString(key, '1');
        }
        return this;
    }
    setPoint(key, value) {
        this.setNumber(key + 'x', value.x);
        this.setNumber(key + 'y', value.y);
        return this;
    }
}
class AjaxRequestMessageEncoder {
    constructor(data) {
        this.data = data;
    }
    encode() {
        let data = [];
        for (let [key, value] of this.data) {
            data.push(key.length + "\n" + key);
            data.push(value.length + "\n" + value);
        }
        return data.join("");
    }
    encodeToHex() {
        return this.encodeTextCharsToHex(this.encode());
    }
    encodeTextCharsToHex(text) {
        let hex = [];
        for (let i = 0; i < text.length; i++) {
            let codePoint = text.charCodeAt(i);
            let utf8 = this.encodeCodePointToUtf8(codePoint);
            hex.push(this.hexBytes(utf8));
        }
        return hex.join("");
    }
    encodeCodePointToUtf8(codePoint) {
        let bytes = [];
        if (codePoint <= 0x7F) {
            bytes.push(codePoint);
        }
        else if (codePoint <= 0x7FF) {
            bytes.push(128 + 64 + (codePoint >> 6));
            bytes.push(128 + (codePoint & 63));
        }
        else if (codePoint <= 0xFFFF) {
            bytes.push(128 + 64 + 32 + (codePoint >> 12));
            bytes.push(128 + ((codePoint >> 6) & 63));
            bytes.push(128 + (codePoint & 63));
        }
        else {
            bytes.push(128 + 64 + 32 + 16 + (codePoint >> 18));
            bytes.push(128 + ((codePoint >> 12) & 63));
            bytes.push(128 + ((codePoint >> 6) & 63));
            bytes.push(128 + (codePoint & 63));
        }
        return bytes;
    }
    hexBytes(bytes) {
        let hex = [];
        for (let i in bytes) {
            hex.push(this.hexByte(bytes[i]));
        }
        return hex.join("");
    }
    hexByte(byte) {
        return this.hexNibble(byte >> 4) + this.hexNibble(byte & 15);
    }
    hexNibble(nibble) {
        return AjaxRequestMessageEncoder.HEX_DIGITS.charAt(nibble);
    }
}
AjaxRequestMessageEncoder.HEX_DIGITS = "0123456789ABCDEF";
class FormRequest {
    constructor(form, responseHandler) {
        this.form = form;
        this.frame = this.createTargetFrame();
        this.input = this.createMessageInput();
        this.responseHandler = responseHandler;
    }
    setMessage(message) {
        this.input.value = message;
        return this;
    }
    submitForm() {
        document.body.appendChild(this.frame);
        this.form.method = 'post';
        this.form.target = this.frame.name;
        this.form.enctype = 'multipart/form-data';
        if (this.form.hasChildNodes()) {
            this.form.insertBefore(this.input, this.form.firstChild);
        }
        else {
            this.form.appendChild(this.input);
        }
        this.form.submit();
        CURRENT_FORM_REQUEST = this;
    }
    handleResponse(response) {
        this.finish();
        this.responseHandler(response);
    }
    finish() {
        document.body.removeChild(this.frame);
        this.form.removeChild(this.input);
        CURRENT_FORM_REQUEST = null;
    }
    createTargetFrame() {
        let frame = document.createElement('iframe');
        frame.name = FormRequest.TARGET_FRAME_NAME;
        frame.style.display = 'none';
        return frame;
    }
    createMessageInput() {
        let input = document.createElement('input');
        input.name = FormRequest.MESSAGE_INPUT_NAME;
        input.type = 'hidden';
        input.value = '';
        return input;
    }
}
FormRequest.TARGET_FRAME_NAME = 'form-request-frame';
FormRequest.MESSAGE_INPUT_NAME = 'form-request-input';
function handleFormRequestResponse(response) {
    if (CURRENT_FORM_REQUEST) {
        CURRENT_FORM_REQUEST.handleResponse(response);
    }
}
let CURRENT_FORM_REQUEST = null;
class HttpRequest {
    constructor() {
        this.url = '';
        this.message = '';
        this.contentType = 'text/plain; charset=UTF-8';
    }
    setUrl(url) {
        this.url = url;
        return this;
    }
    setMessage(message) {
        this.message = message;
        return this;
    }
    setContentType(contentType) {
        this.contentType = contentType;
        return this;
    }
    sendSync() {
        let request = this.createHttpRequest(false);
        request.send(this.message);
        return request;
    }
    sendAsync(responseHandler) {
        let request = this.createHttpRequest(true);
        request.onreadystatechange = function () {
            if (request.readyState == HTTP_REQUEST_STATE_DONE) {
                responseHandler(request);
            }
        };
        request.send(this.message);
        return request;
    }
    createHttpRequest(async) {
        let request = new XMLHttpRequest();
        request.open('POST', this.url, async);
        request.setRequestHeader('Content-Type', this.contentType);
        return request;
    }
}
