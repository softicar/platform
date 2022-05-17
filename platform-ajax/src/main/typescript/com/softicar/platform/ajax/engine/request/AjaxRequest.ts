
class AjaxRequest {
	private message: AjaxRequestMessage;
	private form: HTMLFormElement | null;

	public constructor(message: AjaxRequestMessage, form: HTMLFormElement | null = null) {
		this.message = message;
		this.form = form;
	}

	public send() {
		this.message.copyNodeValues();
		this.message.setRequestIndex(AJAX_REQUEST_MANAGER.openRequest(this));
	
		if(this.form) {
			new FormRequest(this.form, response => this.handleFormRequestResponse(response))
				.setMessage(this.message.encodeToHex())
				.submitForm();
		} else {
			new HttpRequest()
				.setMessage(this.message.encode())
				.sendAsync(request => this.handleHttpRequestResponse(request));
		}
	}

	private handleFormRequestResponse(response: string) {
		this.close();
		this.executeJavaScript(response);
	}

	private handleHttpRequestResponse(request: XMLHttpRequest) {
		this.close();

		if(request.status == HTTP_STATUS_SUCCESS) {
			this.executeJavaScript(request.responseText);
		} else if(request.status == HTTP_STATUS_GONE) {
			handleSessionTimeout();
		} else if(request.status != 0) {
			alert("HTTP Error " + request.status + ": " + request.statusText);
		} else {
			// ignore this error, request was probably canceled by client
		}
	}
	
	private close() {
		AJAX_REQUEST_MANAGER.closeRequest(this);
		AJAX_REQUEST_LOCK.release();
		KEEP_ALIVE.schedule();
	}
	
	private executeJavaScript(javaScriptCode: string) {
		eval(javaScriptCode);
	}
}
