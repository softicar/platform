
class AjaxRequest {
	private parameters: any;
	private form: HTMLFormElement | null;

	public constructor(parameters: any, form: HTMLFormElement | null = null) {
		this.parameters = parameters;
		this.form = form;
	}

	public send() {
		// add standard parameters
		this.parameters.i = DOCUMENT_INSTANCE_UUID;
		this.parameters.x = AJAX_REQUEST_MANAGER.registerRequest(this);
		if(DEBUG)
			this.parameters.debug = true;
		if(VERBOSE)
			this.parameters.verbose = true;
	
		// send asynchronous request
		if(this.form) {
			new FormRequest(this.form)
				.setMessage(new AjaxMessageEncoder(this.parameters).encodeToHex())
				.submitForm();
		} else {
			new HttpRequest()
				.setMessage(new AjaxMessageEncoder(this.parameters).encode())
				.sendAsync(request => this.handleServerRequestResponse(request));
		}
	}

	public handleFormRequestResponse(responseText: string) {
		AJAX_REQUEST_MANAGER.finishRequest(this);
		ACTION_QUEUE.enqueueAction(new JavaScriptAction(responseText));
		ACTION_QUEUE.executeNextAction();
	}

	public handleServerRequestResponse(request: XMLHttpRequest) {
		AJAX_REQUEST_MANAGER.finishRequest(this);

		if(request.status == HTTP_STATUS_SUCCESS) {
			if(request.responseText) {
				ACTION_QUEUE.enqueueAction(new JavaScriptAction(request.responseText));
				ACTION_QUEUE.executeNextAction();
			}
		} else if(request.status == HTTP_STATUS_GONE) {
			handleSessionTimeout();
		} else if(request.status != 0) {
			alert("HTTP Error " + request.status + ": " + request.statusText);
		} else {
			// ignore this error, request was probably canceled by client
		}
	}
}
