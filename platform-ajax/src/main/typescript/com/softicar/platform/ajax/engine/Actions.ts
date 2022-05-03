
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
 * This {@link Action} represents a server request.
 */
class ServerRequestAction implements Action {
	private parameters: any;
	private form: any;

	constructor(parameters: any, form: any) {
		this.parameters = parameters;
		this.form = form;
	}

    /**
	 * Sends a request to the server with the specified request parameters.
	 * After the server has replied, a new {@link JavaScriptAction} with the 
	 * returned JavaScript code from the server is enqueue and the next 
	 * {@link Action} of the {@link ActionQueue} is executed automatically.
	 *
	 * If you leave the form parameter undefined, this will send the request
	 * via XMLHttpRequest, else, the specified form is submitted.
	 */
	execute() {
		SR_sendAjaxRequest(this.parameters, this.form);
	}
}

/**
 * This {@link Action} executes JavaScript code.
 */
class JavaScriptAction implements Action {
	private javaScriptCode: string;
	
	constructor(javaScriptCode: string) {
		this.javaScriptCode = javaScriptCode;
	}

	/**
	 * Executes the JavaScript code and then executes the next {@link Action}
	 * of the {@link ActionQueue}.
	 */
	execute() {
		eval(this.javaScriptCode);
		ACTION_QUEUE.executeNextAction();
	}
}
