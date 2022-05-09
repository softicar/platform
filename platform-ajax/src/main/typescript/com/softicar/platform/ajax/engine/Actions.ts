/**
 * An executable object that can be put into an {@link ActionQueue}.
 */
interface Action {

	/**
	 * Executes this {@link Action} object.
	 */
	execute(): void;
}

/**
 * This {@link Action} represents an {@link AjaxRequest} action.
 */
class AjaxRequestAction implements Action {
	private parameters: any;
	private form: HTMLFormElement | null;

	public constructor(parameters: any, form: HTMLFormElement | null = null) {
		this.parameters = parameters;
		this.form = form;
	}

    /**
	 * Sends a request to the server with the specified request parameters.
	 * After the server has replied, a new {@link JavaScriptAction} with the 
	 * returned JavaScript code from the server is enqueue and the next 
	 * {@link Action} of the {@link ActionQueue} is executed automatically.
	 */
	public execute() {
		new AjaxRequest(this.parameters, this.form).send();
	}
}

/**
 * This {@link Action} executes JavaScript code.
 */
class JavaScriptAction implements Action {
	private javaScriptCode: string;
	
	public constructor(javaScriptCode: string) {
		this.javaScriptCode = javaScriptCode;
	}

	/**
	 * Executes the JavaScript code and then executes the next {@link Action}
	 * of the {@link ActionQueue}.
	 */
	public execute() {
		eval(this.javaScriptCode);
		ACTION_QUEUE.executeNextAction();
	}
}
