/**
 * A request sent to the server side of the AJAX engine.
 * <p>
 * This class should not be used directly but only through {@link AjaxRequestQueue}.
 */
class AjaxRequest {
	private message: AjaxRequestMessage;
	private form: HTMLFormElement | null;

	public constructor(message: AjaxRequestMessage, form: HTMLFormElement | null) {
		this.message = message;
		this.form = form;
	}

	public send(requestIndex: number) {
		this.message.copyNodeValues();
		this.message.setRequestIndex(requestIndex);
	
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

	public isRedundant(message: AjaxRequestMessage) {
		return this.message.isRedundantTo(message);
	}

	public isObsolete() {
		return this.message.isObsolete();
	}

	private handleFormRequestResponse(response: string) {
		AJAX_REQUEST_QUEUE.onRequestResponse(response);
	}

	private handleHttpRequestResponse(request: XMLHttpRequest) {
		if(request.status == HTTP_STATUS_SUCCESS) {
			AJAX_REQUEST_QUEUE.onRequestResponse(request.responseText);
		} else {
			if(request.status == HTTP_STATUS_GONE) {
				handleSessionTimeout();
			} else if(request.status != 0) {
				alert("HTTP Error " + request.status + ": " + request.statusText);
			} else {
				// ignore this error
			}
			AJAX_REQUEST_QUEUE.onRequestResponse("");
		}
	}
}
