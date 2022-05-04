/**
 * Maintains the lock for {@link AjaxRequest} execution.
 */
class AjaxRequestLock {
	private locked = false;

	/**
	 * Acquires the global event lock. This function returns true
	 * if the lock could be successfully allocated, false otherwise.
	 */
	lock() {
		if(!this.locked) {
			this.locked = true;
			showWorkingIndicator();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Releases the global event lock.
	 */
	release() {
		if(this.locked) {
			this.locked = false;
			hideWorkingIndicator();
			
			// FIXME this ad-hoc call looks like a hack
			AUTO_COMPLETE_ENGINE.notifyChangeEventReturned();
		}
		else {
			alert("Internal error: AJAX request lock already released.");
		}
	}
	
	/**
	 * Returns true if the global event lock is allocated.
	 */
	isLocked() {
		return this.locked;
	}
}

let AJAX_REQUEST_LOCK = new AjaxRequestLock();
