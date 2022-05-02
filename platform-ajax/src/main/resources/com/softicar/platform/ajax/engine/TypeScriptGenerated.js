"use strict";
class ActionQueue {
    constructor() {
        this.actions = [];
        this.begin = 0;
        this.end = 0;
    }
    // This adds the specified action to the end of the action queue.
    enqueueAction(action) {
        this.actions[this.end] = action;
        this.end++;
    }
    // This removes the action at the begin of the action queue
    // and returns it. If the action queue is empty, this returns
    // null.
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
    // This removes the first action from the action queue and 
    // calls execute() on it. When there are no more actions in 
    // the queue, a new keep-alive timeout is scheduled and the
    // global event lock is released.
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
// This action represents a server request. When executed, this
// sends a request to the server with the specified parameters. 
// After the server has replied, a new JavascriptAction with the 
// returned javascript code of the server is enqueue and the next 
// action of the action queue is executed.
//
// If you leave form undefined, this will send an ajax request via
// XMLHttpRequest, else the specified form is submitted.
class ServerRequestAction {
    constructor(parameters, form) {
        this.parameters = parameters;
        this.form = form;
    }
    execute() {
        SR_sendAjaxRequest(this.parameters, this.form);
    }
}
// This action represents a javascipt action. When executed, this
// executes the specified javascript code and afterwards executes 
// the next action of the action queue.
class JavascriptAction {
    constructor(javascriptCode) {
        this.javascriptCode = javascriptCode;
    }
    execute() {
        eval(this.javascriptCode);
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
/**
 * This class takes care that no session timeout occurs
 * by sending a keep-alive message to the server.
 * <p>
 * @param delay the number of milliseconds between every
 *              keep-alive message sent to the server
 */
class KeepAlive {
    constructor(delay) {
        this.delay = delay;
        this.handlerId = -1;
    }
    /**
     * This will schedule a new keep-alive message.
     * <p>
     * Any keep-alive message that was scheduled before the
     * call to this function will be canceled.
     */
    schedule() {
        // clear any previously scheduled timeout
        if (this.handlerId >= 0) {
            clearTimeout(this.handlerId);
        }
        // schedule new timeout with default delay		
        this.handlerId = setTimeout(this.handleKeepAliveTimeout, this.delay);
    }
    handleKeepAliveTimeout() {
        // ignore if the session timed out
        if (SESSION_TIMED_OUT) {
            return;
        }
        // try to get the global event lock
        if (lock(LOCK_REASON_KEEP_ALIVE)) {
            let parameters = {
                'a': AJAX_REQUEST_KEEP_ALIVE
            };
            ACTION_QUEUE.enqueueAction(new ServerRequestAction(parameters, null));
            ACTION_QUEUE.executeNextAction();
        }
        else {
            // Since the global event lock was active there was no point
            // in sending a keep-alive, anyway. So, just reschedule a new
            // timeout.
            KEEP_ALIVE.schedule();
        }
    }
}
let KEEP_ALIVE = new KeepAlive(3 * 60 * 1000);
