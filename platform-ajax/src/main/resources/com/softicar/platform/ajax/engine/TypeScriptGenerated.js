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
            unlock();
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
let AJAX_REQUEST_LOGIN = 0;
let AJAX_REQUEST_CREATE_DOCUMENT = 1;
let AJAX_REQUEST_KEEP_ALIVE = 2;
let AJAX_REQUEST_TIMEOUT = 5;
let AJAX_REQUEST_DOM_EVENT = 6;
let AJAX_REQUEST_DRAG_AND_DROP = 7;
let AJAX_REQUEST_UPLOAD = 8;
let AJAX_REQUEST_RESOURCE = 9;
let AJAX_REQUEST_AUTO_COMPLETE = 10;
let DOM_VK_TAB = 9;
let DOM_VK_ENTER = 13;
let DOM_VK_ESCAPE = 27;
let DOM_VK_SPACE = 32;
let DOM_VK_UP = 38;
let DOM_VK_DOWN = 40;
let LOCK_REASON_DOM_EVENT = 0;
let LOCK_REASON_TIMEOUT = 1;
let LOCK_REASON_KEEP_ALIVE = 2;
let LOCK_REASON_DRAG_AND_DROP = 3;
let LOCK_REASON_UPLOAD = 4;
let KEEP_ALIVE_REQUEST_DELAY = 3 * 60 * 1000;
let HTTP_REQUEST_STATE_UNSET = 0;
let HTTP_REQUEST_STATE_OPENED = 1;
let HTTP_REQUEST_STATE_HEADERS_RECEIVED = 2;
let HTTP_REQUEST_STATE_LOADING = 3;
let HTTP_REQUEST_STATE_DONE = 4;
let HTTP_STATUS_SUCCESS = 200;
let HTTP_STATUS_GONE = 410;
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
        if (lock(LOCK_REASON_KEEP_ALIVE)) {
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
