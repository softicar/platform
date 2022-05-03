
/**
 * An execution queue for {@link Action} objects.
 */
class ActionQueue {
	private actions: Action[];
	private begin: number;
	private end: number;
	
	constructor() {
		this.actions = [];
		this.begin = 0;
		this.end = 0;
	}
	
	/**
	 * Adds the specified {@link Action} to the end of this {@link ActionQueue}.
	 */
	enqueueAction(action: Action) {
		this.actions[this.end] = action;
		this.end++;
	}

	/**
	 * Removes the {@link Action} at the begin of this {@link ActionQueue}
	 * and returns it. If this {@link ActionQueue} is empty, this returns null.
	 */
	dequeueAction() {
		if(this.begin < this.end) {
			let action = this.actions[this.begin];
			delete this.actions[this.begin];
			++this.begin;
			return action;
		} else {
			return null;
		}
	}

	/**
	 * Removes the first {@link Action} from this {@link ActionQueue} and calls
	 * {@link Action#execute()} on it. If this {@link ActionQueue} is empty, a
	 * {@link KeepAlive} timeout is scheduled instead, and the global event lock
	 * is released.
	 */
	executeNextAction() {
		let action = this.dequeueAction();
		if(action) {
			action.execute();
		} else {
			KEEP_ALIVE.schedule();
			unlock();
		}
	}
}

let ACTION_QUEUE = new ActionQueue();
