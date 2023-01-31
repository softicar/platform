/**
 * Waits for a set of predicates before executing a given function.
 * <p>
 * This class periodically checks all given predicates and executes the given
 * function only if all predicates are true.
 */
class DelayedExecutor {
	private functionToExecute: () => void;
	private predicates: (() => boolean)[];
	private delayMillis = 100;
	private remainingMillis = 60000;

	/**
	 * Constructs this instance to execute the given function.
	 */
	public constructor(functionToExecute: () => void) {
		this.functionToExecute = functionToExecute;
		this.predicates = [];
	}
	
	/**
	 * Adds the given predicate to the set of predicates to check.
	 */
	public addWaitForPredicate(predicate: () => boolean) {
		this.predicates.push(predicate);
		return this;
	}
	
	/**
	 * Adds a predicate checking that the given global variable is defined.
	 */
	public addWaitForVariable(variable: string) {
		return this.addWaitForPredicate(() => window.hasOwnProperty(variable));
	}
	
	/**
	 * Adds a predicate checking that the given node is appended to the document.
	 */
	public addWaitForNodeWithId(nodeId: string) {
		return this.addWaitForPredicate(() => document.getElementById(nodeId) !== null);
	}

	/**
	 * Defines the number of milliseconds to wait between checking all predicates.
	 */
	public setDelayMillis(delayMillis: number) {
		this.delayMillis = delayMillis;
		return this;
	}

	/**
	 * Defines the maximum number of milliseconds to wait for all predicates.
	 * <p>
	 * When the time has expired, the predicates will not be checked anymore,
	 * and the given function will not be executed.
	 */
	public setMaximumMillis(maximumMillis: number) {
		this.remainingMillis = maximumMillis;
		return this;
	}

	/**
	 * Starts the periodic check of all predicates, and finally the execution
	 * of the given function if all predicates are true.
	 */
	public start() {
		if(this.testPredicates()) {
			this.functionToExecute();
		} else if(this.remainingMillis > 0) {
			this.remainingMillis -= this.delayMillis;
			setTimeout(() => this.start(), this.delayMillis);
		}
	}
	
	private testPredicates() {
		for(let predicate of this.predicates) {
			if(!predicate()) {
				return false;
			}
		}
		return true;
	}
}
