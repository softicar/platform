
class ActionQueue {
	private actions: Action[];
	private begin: number;
	private end: number;
	
	constructor() {
		this.actions = [];
		this.begin = 0;
		this.end = 0;
	}
	
	// This adds the specified action to the end of the action queue.
	enqueueAction(action: Action) {
		this.actions[this.end] = action;
		this.end++;
	}

	// This removes the action at the begin of the action queue
	// and returns it. If the action queue is empty, this returns
	// null.
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

	// This removes the first action from the action queue and 
	// calls execute() on it. When there are no more actions in 
	// the queue, a new keep-alive timeout is scheduled and the
	// global event lock is released.
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
