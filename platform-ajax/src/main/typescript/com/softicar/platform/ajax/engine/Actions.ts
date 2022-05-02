
interface Action {
	execute(): void;
}

// This action represents a server request. When executed, this
// sends a request to the server with the specified parameters. 
// After the server has replied, a new JavascriptAction with the 
// returned javascript code of the server is enqueue and the next 
// action of the action queue is executed.
//
// If you leave form undefined, this will send an ajax request via
// XMLHttpRequest, else the specified form is submitted.
class ServerRequestAction implements Action {
	private parameters: any;
	private form: any;

	constructor(parameters: any, form: any) {
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
class JavascriptAction implements Action {
	private javascriptCode: string;
	
	constructor(javascriptCode: string) {
		this.javascriptCode = javascriptCode;
	}

	execute() {
		eval(this.javascriptCode);
		ACTION_QUEUE.executeNextAction();
	}
}
