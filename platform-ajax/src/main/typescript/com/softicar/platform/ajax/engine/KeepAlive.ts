
/**
 * This class takes care that no session timeout occurs
 * by sending a keep-alive message to the server.
 * <p>
 * @param delay the number of milliseconds between every 
 *              keep-alive message sent to the server
 */
class KeepAlive {
	private delay: number;
	private handlerId: number;

	constructor(delay: number) {
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
		if(this.handlerId >= 0) {
			clearTimeout(this.handlerId);
		}

		// schedule new timeout with default delay		
		this.handlerId = setTimeout(this.handleKeepAliveTimeout, this.delay);
	}

	handleKeepAliveTimeout() {
		// ignore if the session timed out
		if(SESSION_TIMED_OUT) {
			return;
		}
	
		// try to get the global event lock
		if(lock(LOCK_REASON_KEEP_ALIVE)) {
			let parameters = {
				'a': AJAX_REQUEST_KEEP_ALIVE
			};

			ACTION_QUEUE.enqueueAction(new ServerRequestAction(parameters, null));
			ACTION_QUEUE.executeNextAction();
		} else {
			// Since the global event lock was active there was no point
			// in sending a keep-alive, anyway. So, just reschedule a new
			// timeout.
			KEEP_ALIVE.schedule();
		}
	}
}

let KEEP_ALIVE = new KeepAlive(3*60*1000);
