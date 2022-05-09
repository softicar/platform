"use strict";
class ActionQueue {
    constructor() {
        this.actions = [];
        this.begin = 0;
        this.end = 0;
    }
    enqueueAction(action) {
        this.actions[this.end] = action;
        this.end++;
    }
    dequeueAction() {
        if (this.begin < this.end) {
            let action = this.actions[this.begin];
            delete this.actions[this.begin];
            ++this.begin;
            return action;
        }
        else {
            return null;
        }
    }
    executeNextAction() {
        let action = this.dequeueAction();
        if (action) {
            action.execute();
        }
        else {
            KEEP_ALIVE.schedule();
            AJAX_REQUEST_LOCK.release();
        }
    }
}
let ACTION_QUEUE = new ActionQueue();
class AjaxRequestAction {
    constructor(parameters, form = null) {
        this.parameters = parameters;
        this.form = form;
    }
    execute() {
        new AjaxRequest(this.parameters, this.form).send();
    }
}
class JavaScriptAction {
    constructor(javaScriptCode) {
        this.javaScriptCode = javaScriptCode;
    }
    execute() {
        eval(this.javaScriptCode);
        ACTION_QUEUE.executeNextAction();
    }
}
class AjaxMessageEncoder {
    constructor(parameters) {
        this.parameters = parameters;
    }
    encode() {
        let data = [];
        for (let key in this.parameters) {
            data.push(key.length + "\n" + key);
            let value = this.parameters[key];
            if (value !== null && value !== undefined) {
                value = "" + value;
                data.push(value.length + "\n" + value);
            }
            else {
                data.push("-1\n");
            }
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
        return AjaxMessageEncoder.HEX_DIGITS.charAt(nibble);
    }
}
AjaxMessageEncoder.HEX_DIGITS = "0123456789ABCDEF";
class AjaxRequest {
    constructor(parameters, form = null) {
        this.parameters = parameters;
        this.form = form;
    }
    send() {
        this.parameters.i = DOCUMENT_INSTANCE_UUID;
        this.parameters.x = AJAX_REQUEST_MANAGER.registerRequest(this);
        if (DEBUG)
            this.parameters.debug = true;
        if (VERBOSE)
            this.parameters.verbose = true;
        if (this.form) {
            new FormRequest(this.form)
                .setMessage(new AjaxMessageEncoder(this.parameters).encodeToHex())
                .submitForm();
        }
        else {
            new HttpRequest()
                .setMessage(new AjaxMessageEncoder(this.parameters).encode())
                .sendAsync(request => this.handleServerRequestResponse(request));
        }
    }
    handleFormRequestResponse(responseText) {
        AJAX_REQUEST_MANAGER.finishRequest(this);
        ACTION_QUEUE.enqueueAction(new JavaScriptAction(responseText));
        ACTION_QUEUE.executeNextAction();
    }
    handleServerRequestResponse(request) {
        AJAX_REQUEST_MANAGER.finishRequest(this);
        if (request.status == HTTP_STATUS_SUCCESS) {
            if (request.responseText) {
                ACTION_QUEUE.enqueueAction(new JavaScriptAction(request.responseText));
                ACTION_QUEUE.executeNextAction();
            }
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
let AJAX_REQUEST_LOCK = new AjaxRequestLock();
class AjaxRequestManager {
    constructor() {
        this.currentRequest = null;
        this.requestIndex = 0;
    }
    registerRequest(request) {
        if (this.currentRequest == null) {
            this.currentRequest = request;
            return this.requestIndex;
        }
        else {
            throw new Error("Internal error: Tried to send two server request at the same time.");
        }
    }
    finishRequest(request) {
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
let AJAX_REQUEST_MANAGER = new AjaxRequestManager();
const AJAX_CSS_PSEUDO_CLASS_HIDDEN = 'hidden';
const AJAX_REQUEST_LOGIN = 0;
const AJAX_REQUEST_CREATE_DOCUMENT = 1;
const AJAX_REQUEST_KEEP_ALIVE = 2;
const AJAX_REQUEST_TIMEOUT = 5;
const AJAX_REQUEST_DOM_EVENT = 6;
const AJAX_REQUEST_DRAG_AND_DROP = 7;
const AJAX_REQUEST_UPLOAD = 8;
const AJAX_REQUEST_RESOURCE = 9;
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
function makeDraggable(draggedNode, initNode, notifyOnDrop) {
    new DragContext(draggedNode, notifyOnDrop).setup(initNode);
}
class DragContext {
    constructor(draggedNode, notifyOnDrop) {
        this.cursorStart = new Point();
        this.dragStart = new Point();
        this.dragPosition = new Point();
        this.moveHandler = (event) => this.onMove(event);
        this.dropHandler = (event) => this.onDrop(event);
        this.draggedNode = draggedNode;
        this.notifyOnDrop = notifyOnDrop;
    }
    setup(initNode) {
        initNode.addEventListener("mousedown", event => this.onDragStart(event));
        initNode.addEventListener("touchstart", event => this.onDragStart(event));
        initNode.style.userSelect = "none";
        initNode.style.touchAction = "none";
    }
    onDragStart(event) {
        this.addDragListener();
        this.setDocumentTextSelection(false);
        this.cursorStart = this.getCursorPosition(event);
        this.dragStart = this.getDraggedNodePosition();
        this.dragPosition = this.dragStart;
        _DOM_CONTEXT_.setMaximumZIndex(this.draggedNode);
    }
    onMove(event) {
        let cursor = this.getCursorPosition(event);
        if (cursor.x >= 0 && cursor.y >= 0) {
            this.dragPosition = this.dragStart.plus(cursor.minus(this.cursorStart));
            this.draggedNode.style.left = this.dragPosition.x + "px";
            this.draggedNode.style.top = this.dragPosition.y + "px";
        }
    }
    onDrop(event) {
        this.removeDragListener();
        this.setDocumentTextSelection(true);
        if (this.notifyOnDrop && (this.dragPosition.x != this.dragStart.x || this.dragPosition.y != this.dragStart.y)) {
            if (AJAX_REQUEST_LOCK.lock()) {
                let parameters = {
                    'a': AJAX_REQUEST_DRAG_AND_DROP,
                    'n': this.draggedNode.id,
                    'sx': this.dragStart.x,
                    'sy': this.dragStart.y,
                    'dx': this.dragPosition.x,
                    'dy': this.dragPosition.y
                };
                GLOBAL.copyNodeValues(parameters);
                ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters));
                ACTION_QUEUE.executeNextAction();
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
    getDraggedNodePosition() {
        let style = this.draggedNode.style;
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
class FormRequest {
    constructor(form) {
        this.form = form;
        this.frame = this.createTargetFrame();
        this.input = this.createMessageInput();
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
FormRequest.TARGET_FRAME_NAME = 'ajaxFrame';
FormRequest.MESSAGE_INPUT_NAME = 'ajaxInput';
function handleFormRequestResponse(responseText) {
    if (CURRENT_FORM_REQUEST) {
        CURRENT_FORM_REQUEST.finish();
    }
    let ajaxRequest = AJAX_REQUEST_MANAGER.getCurrentRequest();
    if (ajaxRequest) {
        ajaxRequest.handleFormRequestResponse(responseText);
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
            let parameters = {
                'a': AJAX_REQUEST_KEEP_ALIVE
            };
            ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters));
            ACTION_QUEUE.executeNextAction();
        }
        else {
            KEEP_ALIVE.schedule();
        }
    }
}
let KEEP_ALIVE = new KeepAlive(KEEP_ALIVE_REQUEST_DELAY);
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
function scheduleTimeout(timeoutNode, milliseconds) {
    setTimeout(() => handleTimeout(timeoutNode), milliseconds);
}
function handleTimeout(timeoutNode) {
    if (AJAX_REQUEST_LOCK.lock()) {
        let parameters = {
            a: AJAX_REQUEST_TIMEOUT,
            n: timeoutNode.id
        };
        GLOBAL.copyNodeValues(parameters);
        ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters));
        ACTION_QUEUE.executeNextAction();
    }
    else {
        scheduleTimeout(timeoutNode, TIMEOUT_RETRY_DELAY);
    }
}
function sendUploadRequestThroughForm(form) {
    if (AJAX_REQUEST_LOCK.lock()) {
        let parameters = {
            a: AJAX_REQUEST_UPLOAD,
            n: form.id
        };
        GLOBAL.copyNodeValues(parameters);
        ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters, form));
        ACTION_QUEUE.executeNextAction();
    }
    else {
        alert(LOCK_MESSAGE);
    }
}
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
