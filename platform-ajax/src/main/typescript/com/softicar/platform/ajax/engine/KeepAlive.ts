/**
 * The purpose of this class is to keep the HTTP session of the user alive.
 * 
 * As long as the client browser is open and working, this sends a keep-alive
 * request to the server on a regular basis.
 */
class KeepAlive {
	private delay: number;
	private handlerId: number;

	/**
	 * Constructs this {@link KeepAlive} object.
	 *
	 * @param delay the number of milliseconds between every 
	 *              keep-alive request sent to the server
	 */
	constructor(delay: number) {
		this.delay = delay;
		this.handlerId = -1;
	}

	/**
	 * Schedules a new keep-alive request.
	 * 
	 * Any keep-alive request that was scheduled before the call to this
	 * function will be canceled.
	 */
	schedule() {
		// clear any previously scheduled timeout
		if(this.handlerId >= 0) {
			clearTimeout(this.handlerId);
		}

		// schedule new timeout with default delay		
		this.handlerId = setTimeout(this.handleKeepAliveTimeout, this.delay);
	}

	private handleKeepAliveTimeout() {
		// ignore if the session timed out
		if(SESSION_TIMED_OUT) {
			return;
		}
	
		// try to get the global event lock
		if(AJAX_REQUEST_LOCK.lock()) {
			let parameters = {
				'a': AJAX_REQUEST_KEEP_ALIVE
			};

			ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters));
			ACTION_QUEUE.executeNextAction();
		} else {
			// Since the global event lock was active there was no point
			// in sending a keep-alive, anyway. So, just reschedule a new
			// timeout.
			KEEP_ALIVE.schedule();
		}
	}
}

const KEEP_ALIVE = new KeepAlive(KEEP_ALIVE_REQUEST_DELAY);
