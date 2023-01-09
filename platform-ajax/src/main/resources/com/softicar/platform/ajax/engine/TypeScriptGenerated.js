"use strict";
class AjaxEngine {
    constructor() {
        this.nodes = new Map();
        this.zIndex = 100;
    }
    raise(element) {
        if (element.style.zIndex != '' + this.zIndex) {
            this.zIndex += 1;
            element.style.zIndex = '' + this.zIndex;
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
    pushBrowserHistoryState(page, url) {
        history.pushState({ page: page }, "", url);
    }
    initializeNode(nodeId, node) {
        node.id = 'n' + nodeId;
        this.nodes.set(nodeId, node);
    }
}
const AJAX_ENGINE = new AjaxEngine();
const AJAX_CSS_PSEUDO_CLASS_HIDDEN = 'hidden';
const AJAX_REQUEST_KEEP_ALIVE = 'KEEP_ALIVE';
const AJAX_REQUEST_TIMEOUT = 'TIMEOUT';
const AJAX_REQUEST_DOM_EVENT = 'DOM_EVENT';
const AJAX_REQUEST_DRAG_AND_DROP = 'DRAG_AND_DROP';
const AJAX_REQUEST_UPLOAD = 'UPLOAD';
const DOM_MODIFIER_ALT = 'ALT';
const DOM_MODIFIER_CONTROL = 'CONTROL';
const DOM_MODIFIER_META = 'META';
const DOM_MODIFIER_SHIFT = 'SHIFT';
const KEEP_ALIVE_REQUEST_DELAY = 3 * 60 * 1000;
const KEY_ENTER = 'Enter';
const KEY_ESCAPE = 'Escape';
const KEY_SPACE = ' ';
const KEY_TAB = 'Tab';
const HTTP_REQUEST_STATE_UNSET = 0;
const HTTP_REQUEST_STATE_OPENED = 1;
const HTTP_REQUEST_STATE_HEADERS_RECEIVED = 2;
const HTTP_REQUEST_STATE_LOADING = 3;
const HTTP_REQUEST_STATE_DONE = 4;
const HTTP_STATUS_SUCCESS = 200;
const HTTP_STATUS_GONE = 410;
function makeDraggable(draggedNode, dragHandleNode, notifyOnDrop) {
    var handler = DRAG_AND_DROP_MANAGER.getHandler(draggedNode);
    handler.setNotifyOnDrop(notifyOnDrop);
    handler.install(dragHandleNode);
}
function setDragLimitNode(draggedNode, limitNode) {
    DRAG_AND_DROP_MANAGER
        .getHandler(draggedNode)
        .setLimitNode(limitNode);
}
function setDragScrollNode(draggedNode, scrollNode) {
    DRAG_AND_DROP_MANAGER
        .getHandler(draggedNode)
        .setScrollNode(scrollNode);
}
class DragAndDropManager {
    constructor() {
        this.handlers = new Map();
    }
    getHandler(node) {
        let handler = this.handlers.get(node.id);
        if (!handler) {
            handler = new DragAndDropHandler(node);
            this.handlers.set(node.id, handler);
        }
        return handler;
    }
}
const DRAG_AND_DROP_MANAGER = new DragAndDropManager();
class DragAndDropHandler {
    constructor(draggedNode) {
        this.cursorStart = new Vector2d();
        this.scrollStart = new Vector2d();
        this.dragStart = new Vector2d();
        this.dragPosition = new Vector2d();
        this.dragHandler = (event) => this.onDrag(event);
        this.dropHandler = (_) => this.onDrop();
        this.draggedNode = draggedNode;
        this.limitNode = null;
        this.scrollNode = null;
        this.notifyOnDrop = false;
        this.dragHandleNode = null;
    }
    install(dragHandleNode) {
        this.dragHandleNode = dragHandleNode;
        dragHandleNode.addEventListener("mousedown", event => this.onDragStart(event));
        dragHandleNode.addEventListener("touchstart", event => this.onDragStart(event));
        dragHandleNode.style.userSelect = "none";
        dragHandleNode.style.touchAction = "none";
        dragHandleNode.ondragstart = function () { return false; };
        dragHandleNode.classList.add(DRAGGABLE_CSS_CLASS);
    }
    setLimitNode(limitNode) {
        this.limitNode = limitNode;
    }
    setScrollNode(scrollNode) {
        this.scrollNode = scrollNode;
    }
    setNotifyOnDrop(notifyOnDrop) {
        this.notifyOnDrop = notifyOnDrop;
    }
    onDragStart(event) {
        var _a;
        this.addDragListener();
        this.setDocumentTextSelection(false);
        this.cursorStart = this.getCursorPosition(event);
        this.scrollStart = this.getScrollPosition();
        this.dragStart = this.getDraggedNodePosition();
        this.dragPosition = this.dragStart;
        (_a = this.dragHandleNode) === null || _a === void 0 ? void 0 : _a.classList.add(DRAGGING_CSS_CLASS);
        AJAX_ENGINE.raise(this.draggedNode);
    }
    onDrag(event) {
        if (this.scrollNode) {
            let delta = this.getCursorPosition(event).minus(this.cursorStart);
            let position = this.clampScrollPosition(this.scrollStart.minus(delta));
            this.scrollNode.scrollTo(position.x, position.y);
        }
        else {
            let cursor = this.getLimitRect().clamp(this.getCursorPosition(event));
            this.dragPosition = this.dragStart.plus(cursor.minus(this.cursorStart));
            this.draggedNode.style.left = this.dragPosition.x + "px";
            this.draggedNode.style.top = this.dragPosition.y + "px";
        }
    }
    onDrop() {
        var _a;
        this.removeDragListener();
        this.setDocumentTextSelection(true);
        (_a = this.dragHandleNode) === null || _a === void 0 ? void 0 : _a.classList.remove(DRAGGING_CSS_CLASS);
        if (this.notifyOnDrop && (this.dragPosition.x != this.dragStart.x || this.dragPosition.y != this.dragStart.y)) {
            let message = new AjaxRequestMessage()
                .setActionType(AJAX_REQUEST_DRAG_AND_DROP)
                .setNode(this.draggedNode)
                .setDragStart(this.dragStart)
                .setDragPosition(this.dragPosition);
            AJAX_REQUEST_QUEUE.submit(message);
        }
    }
    clampScrollPosition(scrollPosition) {
        var _a, _b;
        let scrollRect = (_b = (_a = this.scrollNode) === null || _a === void 0 ? void 0 : _a.getBoundingClientRect()) !== null && _b !== void 0 ? _b : new DOMRect(0, 0, 0, 0);
        let nodeRect = this.draggedNode.getBoundingClientRect();
        return new Vector2d(clamp(scrollPosition.x, 0, Math.max(nodeRect.width - scrollRect.width, 0)), clamp(scrollPosition.y, 0, Math.max(nodeRect.height - scrollRect.height, 0)));
    }
    getLimitRect() {
        if (this.limitNode && this.limitNode != document.body) {
            return Rect.fromDomRect(this.limitNode.getBoundingClientRect());
        }
        else {
            return new Rect(0, 0, window.innerWidth, window.innerHeight);
        }
    }
    getCursorPosition(event) {
        if (event instanceof MouseEvent) {
            return new Vector2d(event.clientX, event.clientY);
        }
        else if (event instanceof TouchEvent) {
            let firstTouch = event.touches[0];
            return new Vector2d(Math.round(firstTouch.clientX), Math.round(firstTouch.clientY));
        }
        else {
            return new Vector2d();
        }
    }
    getScrollPosition() {
        var _a, _b, _c, _d;
        let x = (_b = (_a = this.scrollNode) === null || _a === void 0 ? void 0 : _a.scrollLeft) !== null && _b !== void 0 ? _b : 0;
        let y = (_d = (_c = this.scrollNode) === null || _c === void 0 ? void 0 : _c.scrollTop) !== null && _d !== void 0 ? _d : 0;
        return new Vector2d(x, y);
    }
    getDraggedNodePosition() {
        let style = window.getComputedStyle(this.draggedNode);
        let x = style.left ? parseInt(style.left) : 0;
        let y = style.top ? parseInt(style.top) : 0;
        return new Vector2d(x, y);
    }
    setDocumentTextSelection(enabled) {
        document.onselectstart = function () { return enabled; };
    }
    addDragListener() {
        document.addEventListener("mousemove", this.dragHandler, true);
        document.addEventListener("touchmove", this.dragHandler, true);
        document.addEventListener("mouseup", this.dropHandler, true);
        document.addEventListener("touchend", this.dropHandler, true);
    }
    removeDragListener() {
        document.removeEventListener("mousemove", this.dragHandler, true);
        document.removeEventListener("touchmove", this.dragHandler, true);
        document.removeEventListener("mouseup", this.dropHandler, true);
        document.removeEventListener("touchend", this.dropHandler, true);
    }
}
function n(nodeId) {
    return AJAX_ENGINE.getNode(nodeId);
}
function e(nodeId, tag) {
    return AJAX_ENGINE.createElement(nodeId, tag);
}
function t(nodeId, text) {
    return AJAX_ENGINE.createTextNode(nodeId, text);
}
function l(nodeId, eventType) {
    listenToDomEvent(nodeId, eventType, true);
}
function u(nodeId, eventType) {
    listenToDomEvent(nodeId, eventType, false);
}
function a(parentId, childId) {
    AJAX_ENGINE.appendChild(parentId, childId);
}
function i(parentId, childId, oherChildId) {
    AJAX_ENGINE.insertBefore(parentId, childId, oherChildId);
}
function r(parentId, childId) {
    AJAX_ENGINE.removeChild(parentId, childId);
}
function p(parentId, newChildId, oldChldId) {
    AJAX_ENGINE.replaceChild(parentId, newChildId, oldChldId);
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
    AJAX_ENGINE.setAttribute(elementId, attribute, value);
}
class FocusTrap {
    constructor(root) {
        this.root = root;
        this.firstFocusableNode = root;
        this.lastFocusableNode = root;
    }
    install() {
        let focusableNodes = this.root.querySelectorAll(FocusTrap.FOCUSABLE_SELECTORS);
        let focusableRoot = this.root.matches(FocusTrap.FOCUSABLE_SELECTORS);
        if (focusableNodes.length == 0 && !focusableRoot) {
            console.log("Failed to trap focus: Neither the given node nor one of its children are focusable.");
            return;
        }
        this.firstFocusableNode = focusableRoot ? this.root : focusableNodes[0];
        this.lastFocusableNode = focusableNodes.length == 0 ? this.root : focusableNodes[focusableNodes.length - 1];
        this.focusElement(this.firstFocusableNode);
        this.root.addEventListener('keydown', event => this.handleKeyDown(event));
    }
    handleKeyDown(event) {
        let tabPressed = event.key === 'Tab' || event.code === 'Tab';
        if (tabPressed) {
            if (event.shiftKey) {
                if (document.activeElement === this.firstFocusableNode) {
                    this.focusElement(this.lastFocusableNode);
                    event.preventDefault();
                }
            }
            else {
                if (document.activeElement === this.lastFocusableNode) {
                    this.focusElement(this.firstFocusableNode);
                    event.preventDefault();
                }
            }
        }
    }
    focusElement(element) {
        element.focus();
    }
}
FocusTrap.FOCUSABLE_SELECTORS = 'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])';
function trapTabFocus(root) {
    new FocusTrap(root).install();
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
        let message = new AjaxRequestMessage().setActionType(AJAX_REQUEST_KEEP_ALIVE);
        AJAX_REQUEST_QUEUE.submit(message);
    }
}
const KEEP_ALIVE = new KeepAlive(KEEP_ALIVE_REQUEST_DELAY);
function clamp(value, minimum, maximum) {
    value = Math.max(value, minimum);
    value = Math.min(value, maximum);
    return value;
}
class DomPopupEngine {
    initializePopup(popupFrame, autoRaise) {
        if (autoRaise) {
            this.addEventListeners(popupFrame);
        }
    }
    movePopup(popupFrame, x, y, offsetUnit, xAlign, yAlign) {
        let parent = popupFrame.parentElement;
        if (parent != null) {
            popupFrame.style.position = 'absolute';
            if (offsetUnit == "PERCENT") {
                this.movePopupByPercent(popupFrame, x, y, xAlign, yAlign);
            }
            else {
                this.movePopupByPixels(parent, popupFrame, x, y, xAlign, yAlign);
            }
        }
        else {
            console.log("Warning: Ignored an attempt to move a non-appended popup. Popup frame ID: " + popupFrame.id);
        }
    }
    addEventListeners(popupFrame) {
        let options = { capture: true, passive: true };
        popupFrame.addEventListener('mousedown', _ => this.raise(popupFrame), options);
        popupFrame.addEventListener('focus', _ => this.raise(popupFrame), options);
    }
    raise(popupFrame) {
        AJAX_ENGINE.raise(popupFrame);
    }
    movePopupByPercent(popupFrame, x, y, xAlign, yAlign) {
        let popupWidth = popupFrame.offsetWidth;
        let popupLeft = 0;
        switch (xAlign) {
            case "LEFT":
                popupLeft = 0;
                break;
            case "CENTER":
                popupLeft = popupWidth / 2;
                break;
            case "RIGHT":
                popupLeft = popupWidth;
                break;
        }
        let popupHeight = popupFrame.offsetHeight;
        let popupTop = 0;
        switch (yAlign) {
            case "TOP":
                popupTop = 0;
                break;
            case "CENTER":
                popupTop = popupHeight / 2;
                break;
            case "BOTTOM":
                popupTop = popupHeight;
                break;
        }
        popupFrame.style.left = `calc(${x}% - ${popupLeft}px)`;
        popupFrame.style.top = `calc(${y}% - ${popupTop}px)`;
    }
    movePopupByPixels(parent, popupFrame, x, y, xAlign, yAlign) {
        let scrollX = window.scrollX + parent.scrollLeft;
        let scrollY = window.scrollY + parent.scrollTop;
        let sizeX = window.innerWidth;
        let popupWidth = popupFrame.offsetWidth;
        let popupLeft = 0;
        switch (xAlign) {
            case "LEFT":
                popupLeft = scrollX + x;
                break;
            case "CENTER":
                popupLeft = scrollX + x - popupWidth / 2;
                break;
            case "RIGHT":
                popupLeft = scrollX + x - popupWidth + 1;
                break;
        }
        popupLeft = Math.min(popupLeft, scrollX + sizeX - popupWidth);
        popupLeft = Math.max(popupLeft, scrollX);
        let popupHeight = popupFrame.offsetHeight;
        let popupTop = 0;
        switch (yAlign) {
            case "TOP":
                popupTop = scrollY + y;
                break;
            case "CENTER":
                popupTop = scrollY + y - popupHeight / 2;
                break;
            case "BOTTOM":
                popupTop = scrollY + y - popupHeight + 1;
                break;
        }
        if (parent != document.body) {
            let parentRect = parent.getBoundingClientRect();
            popupLeft -= parentRect.left;
            popupTop -= parentRect.top;
        }
        popupLeft = Math.max(popupLeft, 0);
        popupTop = Math.max(popupTop, 0);
        popupFrame.style.left = popupLeft + 'px';
        popupFrame.style.top = popupTop + 'px';
    }
}
const POPUP_ENGINE = new DomPopupEngine();
class Rect {
    constructor(x = 0, y = 0, width = 0, height = 0) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    static fromDomRect(rect) {
        return new Rect(rect.x, rect.y, rect.width, rect.height);
    }
    clamp(point) {
        return new Vector2d(clamp(point.x, this.x, this.x + this.width - 1), clamp(point.y, this.y, this.y + this.height - 1));
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
        SESSION_TIMEOUT_DIALOG.classList.remove(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
        AJAX_ENGINE.raise(SESSION_TIMEOUT_DIALOG);
    }
}
function scheduleTimeout(timeoutNode, milliseconds) {
    setTimeout(() => handleTimeout(timeoutNode), milliseconds);
}
function handleTimeout(timeoutNode) {
    let message = new AjaxRequestMessage()
        .setActionType(AJAX_REQUEST_TIMEOUT)
        .setNode(timeoutNode);
    AJAX_REQUEST_QUEUE.submit(message);
}
function sendUploadRequestThroughForm(form) {
    let formRect = form.getBoundingClientRect();
    let message = new AjaxRequestMessage()
        .setActionType(AJAX_REQUEST_UPLOAD)
        .setNode(form)
        .setMousePosition(new Vector2d(formRect.x, formRect.y))
        .setWindowPageOffset(new Vector2d(window.pageXOffset, window.pageYOffset))
        .setWindowInnerSize(new Vector2d(window.innerWidth, window.innerHeight));
    AJAX_REQUEST_QUEUE.submit(message, form);
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
class Vector2d {
    constructor(x = 0, y = 0) {
        this.x = x;
        this.y = y;
    }
    plus(other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }
    minus(other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }
}
class Vector3d {
    constructor(x = 0, y = 0, z = 0) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    plus(other) {
        return new Vector3d(this.x + other.x, this.y + other.y, this.z + other.z);
    }
    minus(other) {
        return new Vector3d(this.x - other.x, this.y - other.y, this.z - other.z);
    }
}
let WORKING_INDICATOR_ENABLED = true;
let WORKING_INDICATOR;
function setWorkingIndicator(indicator) {
    WORKING_INDICATOR = indicator;
}
function showWorkingIndicator() {
    if (WORKING_INDICATOR && WORKING_INDICATOR_ENABLED) {
        WORKING_INDICATOR.classList.remove(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
        AJAX_ENGINE.raise(WORKING_INDICATOR);
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
function listenToDomEvent(nodeId, event, doListen) {
    let element = AJAX_ENGINE.getElement(nodeId);
    if (element == null)
        return;
    let handler = doListen ? handleDomEvent : null;
    switch (event) {
        case 'BLUR':
            element.onblur = handler;
            break;
        case 'CLICK':
            element.onclick = handler;
            break;
        case 'CHANGE':
            element.onchange = handler;
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
        case 'FOCUS':
            element.onfocus = handler;
            break;
        case 'INPUT':
            element.oninput = handler;
            break;
        case 'KEYDOWN':
            KEYBOARD_EVENT_MANAGER.setListenToKeyDown(element, doListen);
            break;
        case 'KEYUP':
            KEYBOARD_EVENT_MANAGER.setListenToKeyUp(element, doListen);
            break;
        case 'SPACE':
            KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen);
            break;
        case 'TAB':
            KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen);
            break;
        case 'WHEEL':
            WHEEL_EVENT_MANAGER.setListenToWheel(element, doListen);
            break;
        default: alert('Unknown event: ' + event);
    }
}
function setPreventDefaultOnMouseDown(element, enabled) {
    element.onmousedown = enabled ? (event) => event.preventDefault() : null;
}
function setPreventDefaultOnWheel(element, modifiers, enabled) {
    WHEEL_EVENT_MANAGER.setPreventDefaultBehavior(element, new Set(modifiers), enabled);
}
function setListenToKeys(element, keys) {
    KEYBOARD_EVENT_MANAGER.setListenToKeys(element, keys);
}
function setHeightAndWidthToComputedValues(node) {
    setTimeout(() => {
        let computedStyle = window.getComputedStyle(node);
        node.style.height = computedStyle.height;
        node.style.width = computedStyle.width;
    });
}
function setHeightAndWidthOnLoad(image, targetNode) {
    if (image.complete) {
        setHeightAndWidthToComputedValues(targetNode);
    }
    else {
        image.addEventListener('load', _ => setHeightAndWidthToComputedValues(targetNode));
    }
}
function handleDomEvent(event) {
    sendOrDelegateEvent(event.currentTarget, event, event.type);
}
function sendDomEventToServer(event, eventType) {
    let element = event.currentTarget;
    let boundingRect = element.getBoundingClientRect();
    let message = new AjaxRequestMessage()
        .setActionType(AJAX_REQUEST_DOM_EVENT)
        .setNode(element)
        .setEventType(eventType)
        .setWindowPageOffset(new Vector2d(window.pageXOffset, window.pageYOffset))
        .setWindowInnerSize(new Vector2d(window.innerWidth, window.innerHeight))
        .setNodeRect(boundingRect);
    if (event instanceof MouseEvent) {
        message.setMousePosition(new Vector2d(event.clientX, event.clientY));
        message.setMouseRelativePosition(new Vector2d(event.clientX - boundingRect.left, event.clientY - boundingRect.top));
    }
    else {
        message.setMousePosition(new Vector2d(boundingRect.x + boundingRect.width / 2, boundingRect.y + boundingRect.height / 2));
    }
    if (event instanceof KeyboardEvent) {
        message.setKey(event.key);
    }
    if (event instanceof KeyboardEvent || event instanceof MouseEvent) {
        message.setModifierKey(DOM_MODIFIER_ALT, event.altKey);
        message.setModifierKey(DOM_MODIFIER_CONTROL, event.ctrlKey);
        message.setModifierKey(DOM_MODIFIER_META, event.metaKey);
        message.setModifierKey(DOM_MODIFIER_SHIFT, event.shiftKey);
    }
    if (event instanceof WheelEvent) {
        message.setWheelDelta(new Vector3d(event.deltaX, event.deltaY, event.deltaZ));
    }
    AJAX_REQUEST_QUEUE.submit(message);
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
        sendDomEventToServer(event, eventType);
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
    setListenToKeyDown(node, enabled) {
        this.getKeyHandler(node).setListenToKeyDown(enabled);
    }
    setListenToKeyUp(node, enabled) {
        this.getKeyHandler(node).setListenToKeyUp(enabled);
    }
    setListenToKeys(node, keys) {
        this.getKeyHandler(node).setListenToKeys(keys);
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
            case 'ENTER': return KEY_ENTER;
            case 'ESCAPE': return KEY_ESCAPE;
            case 'SPACE': return KEY_SPACE;
            case 'TAB': return KEY_TAB;
        }
        throw new Error("Internal error: Unsupported keyboard event name.");
    }
}
const KEYBOARD_EVENT_MANAGER = new KeyboardEventManager();
class KeyboardEventHandler {
    constructor(node) {
        this.keys = new Map();
        this.fireOnKeyUp = new Map();
        this.preventDefault = new Map();
        this.cssClassApplier = new Map();
        this.listenToKeyDown = false;
        this.listenToKeyUp = false;
        this.listenToKeys = new Set();
        this.lastKeyDown = "";
        this.node = node;
    }
    install() {
        if (!this.node.onkeydown && !this.node.onkeyup) {
            this.node.onkeydown = event => this.handleKeyDown(event);
            this.node.onkeyup = event => this.handleKeyUp(event);
        }
        else {
            console.log('Warning: Skipped installation of a keyboard event listener.');
        }
        return this;
    }
    setListenToKeyDown(enabled) {
        this.listenToKeyDown = enabled;
    }
    setListenToKeyUp(enabled) {
        this.listenToKeyUp = enabled;
    }
    setListenToKeys(keys) {
        this.listenToKeys = new Set(keys);
    }
    setListenTo(key, eventName, enabled) {
        if (enabled) {
            this.keys.set(key, eventName);
            this.preventDefault.set(key, true);
        }
        else {
            this.keys.delete(key);
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
        let eventName = this.keys.get(event.key);
        if (eventName) {
            if (!this.fireOnKeyUp.get(event.key) && !event.repeat) {
                sendOrDelegateEvent(this.node, event, eventName);
            }
            let applier = this.cssClassApplier.get(event.key);
            if (applier) {
                applier.addClasses();
            }
            this.stopFurtherHandling(event);
        }
        if (this.listenToKeyDown && this.listenToKeys.has(event.key)) {
            sendOrDelegateEvent(this.node, event, 'KEYDOWN');
        }
        this.lastKeyDown = event.key;
    }
    handleKeyUp(event) {
        var eventName = this.keys.get(event.key);
        if (eventName) {
            if (this.fireOnKeyUp.get(event.key) && this.lastKeyDown == event.key) {
                sendOrDelegateEvent(this.node, event, eventName);
            }
            let applier = this.cssClassApplier.get(event.key);
            if (applier) {
                applier.removeClasses();
            }
            this.stopFurtherHandling(event);
        }
        if (this.listenToKeyUp && this.listenToKeys.has(event.key)) {
            sendOrDelegateEvent(this.node, event, 'KEYUP');
        }
        this.lastKeyDown = "";
    }
    stopFurtherHandling(event) {
        event.stopPropagation();
        if (this.preventDefault.get(event.key)) {
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
class WheelEventManager {
    constructor() {
        this.handlers = new Map();
    }
    setListenToWheel(node, enabled) {
        this.getHandler(node).setListenToWheel(enabled);
    }
    setPreventDefaultBehavior(node, modifiers, enabled) {
        this.getHandler(node).setPreventDefault(modifiers, enabled);
    }
    getHandler(node) {
        let handler = this.handlers.get(node.id);
        if (!handler) {
            handler = new WheelEventHandler(node).install();
            this.handlers.set(node.id, handler);
        }
        return handler;
    }
}
const WHEEL_EVENT_MANAGER = new WheelEventManager();
class WheelEventHandler {
    constructor(node) {
        this.preventDefault = new Map();
        this.listenToWheel = false;
        this.node = node;
    }
    install() {
        if (!this.node.onwheel) {
            this.node.onwheel = event => this.handleWheel(event);
        }
        else {
            console.log('Warning: Skipped installation of a wheel event listener.');
        }
        return this;
    }
    setListenToWheel(enabled) {
        this.listenToWheel = enabled;
    }
    setPreventDefault(modifiers, enabled) {
        this.preventDefault.set(JSON.stringify(Array.from(modifiers)), enabled);
    }
    handleWheel(event) {
        let modifiers = new Set();
        if (event.altKey)
            modifiers.add(DOM_MODIFIER_ALT);
        if (event.ctrlKey)
            modifiers.add(DOM_MODIFIER_CONTROL);
        if (event.metaKey)
            modifiers.add(DOM_MODIFIER_META);
        if (event.shiftKey)
            modifiers.add(DOM_MODIFIER_SHIFT);
        if (this.listenToWheel) {
            handleDomEvent(event);
        }
        if (this.preventDefault.get(JSON.stringify(Array.from(modifiers)))) {
            event.stopPropagation();
            event.preventDefault();
        }
    }
}
class AjaxRequest {
    constructor(message, form) {
        this.message = message;
        this.form = form;
    }
    send(requestIndex) {
        this.message.copyNodeValues();
        this.message.setRequestIndex(requestIndex);
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
    isRedundant(message) {
        return this.message.isRedundantTo(message);
    }
    isObsolete() {
        return this.message.isObsolete();
    }
    handleFormRequestResponse(response) {
        AJAX_REQUEST_QUEUE.onRequestResponse(response);
    }
    handleHttpRequestResponse(request) {
        if (request.status == HTTP_STATUS_SUCCESS) {
            AJAX_REQUEST_QUEUE.onRequestResponse(request.responseText);
        }
        else {
            if (request.status == HTTP_STATUS_GONE) {
                handleSessionTimeout();
            }
            else if (request.status != 0) {
                alert("HTTP Error " + request.status + ": " + request.statusText);
            }
            else {
            }
            AJAX_REQUEST_QUEUE.onRequestResponse("");
        }
    }
}
class AjaxRequestMessage {
    constructor() {
        this.instanceUuid = DOCUMENT_INSTANCE_UUID;
        this.requestIndex = -1;
        this.actionType = null;
        this.eventType = null;
        this.nodeId = null;
        this.nodeRect = new Rect();
        this.nodeValues = {};
        this.key = "";
        this.modifierKeys = {};
        this.cursor = new Vector2d();
        this.cursorRelative = new Vector2d();
        this.wheelDelta = new Vector3d();
        this.dragStart = new Vector2d();
        this.dragPosition = new Vector2d();
        this.windowPageOffset = new Vector2d();
        this.windowInnerSize = new Vector2d();
    }
    copyNodeValues() {
        VALUE_NODE_MAP.copyNodeValues(this);
    }
    setRequestIndex(requestIndex) {
        this.requestIndex = requestIndex;
        return this;
    }
    setActionType(actionType) {
        this.actionType = actionType;
        return this;
    }
    setEventType(eventType) {
        this.eventType = eventType.toUpperCase();
        return this;
    }
    setNode(node) {
        this.nodeId = node.id;
        return this;
    }
    setNodeRect(boundingClientRect) {
        this.nodeRect = Rect.fromDomRect(boundingClientRect);
        return this;
    }
    setNodeValue(node, value) {
        this.nodeValues[node.id] = value;
        return this;
    }
    setKey(key) {
        this.key = key;
        return this;
    }
    setModifierKey(name, value) {
        this.modifierKeys[name] = value;
        return this;
    }
    setMousePosition(position) {
        this.cursor = position;
        return this;
    }
    setMouseRelativePosition(position) {
        this.cursorRelative = position;
        return this;
    }
    setWheelDelta(delta) {
        this.wheelDelta = delta;
        return this;
    }
    setDragStart(start) {
        this.dragStart = start;
        return this;
    }
    setDragPosition(position) {
        this.dragPosition = position;
        return this;
    }
    setWindowPageOffset(pageOffset) {
        this.windowPageOffset = pageOffset;
        return this;
    }
    setWindowInnerSize(innerSize) {
        this.windowInnerSize = innerSize;
        return this;
    }
    encode() {
        return new AjaxRequestMessageEncoder(this).encode();
    }
    encodeToHex() {
        return new AjaxRequestMessageEncoder(this).encodeToHex();
    }
    isRedundantTo(other) {
        if (this.isKeepAlive()) {
            return true;
        }
        else if (this.isSameAction(other) && this.isOnSameNode(other)) {
            if (this.isDomEvent()) {
                return this.isRedundantDomEvents(other);
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    isRedundantDomEvents(other) {
        if (this.isSameEventType(other)) {
            if (this.isPassiveEventType() && (this.isSent() || other.isSent())) {
                return false;
            }
            else if (this.isKeyEventType()) {
                return false;
            }
            else if (this.isWheelEventType()) {
                return this.isSameDeltaDirections(other);
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    isSameDeltaDirections(other) {
        let deltaXSameSign = Math.sign(this.wheelDelta.x) == Math.sign(other.wheelDelta.x);
        let deltaYSameSign = Math.sign(this.wheelDelta.y) == Math.sign(other.wheelDelta.y);
        let deltaZSameSign = Math.sign(this.wheelDelta.z) == Math.sign(other.wheelDelta.z);
        return deltaXSameSign && deltaYSameSign && deltaZSameSign;
    }
    isObsolete() {
        if (this.nodeId) {
            return document.getElementById(this.nodeId) === null;
        }
        else {
            return false;
        }
    }
    isKeepAlive() {
        return this.actionType == AJAX_REQUEST_KEEP_ALIVE;
    }
    isDomEvent() {
        return this.actionType == AJAX_REQUEST_DOM_EVENT;
    }
    isSameAction(other) {
        return this.actionType == other.actionType;
    }
    isOnSameNode(other) {
        return this.nodeId == other.nodeId;
    }
    isSameEventType(other) {
        return this.eventType == other.eventType;
    }
    isPassiveEventType() {
        return this.eventType == 'CHANGE' || this.eventType == 'INPUT';
    }
    isKeyEventType() {
        return this.eventType == 'KEYDOWN' || this.eventType == 'KEYUP';
    }
    isWheelEventType() {
        return this.eventType == 'WHEEL';
    }
    isSent() {
        return this.requestIndex >= 0;
    }
}
class AjaxRequestMessageEncoder {
    constructor(message) {
        this.message = message;
    }
    encode() {
        return JSON.stringify(this.message);
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
class AjaxRequestQueue {
    constructor() {
        this.requests = new Array();
        this.requestIndex = 0;
        this.waitingForServer = false;
    }
    submit(message, form = null) {
        if (!this.isRedundant(message)) {
            this.push(new AjaxRequest(message, form));
        }
    }
    onRequestResponse(javaScript) {
        this.finishRequest();
        this.executeJavaScript(javaScript);
        this.process();
    }
    hasRequests() {
        return this.requests.length > 0;
    }
    finishRequest() {
        this.requests.shift();
        this.requestIndex += 1;
        this.waitingForServer = false;
        KEEP_ALIVE.schedule();
    }
    executeJavaScript(javaScript) {
        if (javaScript) {
            eval(javaScript);
        }
    }
    push(request) {
        this.requests.push(request);
        this.process();
    }
    process() {
        if (!this.waitingForServer) {
            this.dropObsoleteRequests();
            if (this.hasRequests()) {
                this.sendNextRequest();
                this.waitingForServer = true;
                showWorkingIndicator();
            }
            else {
                hideWorkingIndicator();
            }
        }
    }
    dropObsoleteRequests() {
        while (this.hasRequests() && this.getNextRequest().isObsolete()) {
            this.requests.shift();
        }
    }
    getNextRequest() {
        return this.requests[0];
    }
    sendNextRequest() {
        let request = this.getNextRequest();
        if (request) {
            request.send(this.requestIndex);
        }
        else {
            throw new Error("Internal error: Undefined request object in request queue.");
        }
    }
    isRedundant(message) {
        for (let request of this.requests) {
            if (request.isRedundant(message)) {
                return true;
            }
        }
        return false;
    }
}
var AJAX_REQUEST_QUEUE = new AjaxRequestQueue();
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
        this.contentType = 'application/json; charset=UTF-8';
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
