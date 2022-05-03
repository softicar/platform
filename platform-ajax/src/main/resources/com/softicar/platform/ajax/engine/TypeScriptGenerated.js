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
class ServerRequestAction {
    constructor(parameters, form) {
        this.parameters = parameters;
        this.form = form;
    }
    execute() {
        SR_sendAjaxRequest(this.parameters, this.form);
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
            ACTION_QUEUE.enqueueAction(new ServerRequestAction(parameters, null));
            ACTION_QUEUE.executeNextAction();
        }
        else {
            KEEP_ALIVE.schedule();
        }
    }
}
let KEEP_ALIVE = new KeepAlive(3 * 60 * 1000);
