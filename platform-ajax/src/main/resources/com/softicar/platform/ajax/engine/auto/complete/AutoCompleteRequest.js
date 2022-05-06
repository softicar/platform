
function AutoCompleteRequest(inputContext, pattern, callback) {

	this.send = send;

	function send() {

		var message = {};
		message.a = AJAX_REQUEST_AUTO_COMPLETE;
		message.i = DOCUMENT_INSTANCE_UUID;
		message.n = inputContext.getInput().id;
		message.p = pattern;
		new HttpRequest()
			.setMessage(new AjaxMessageEncoder(message).encode())
			.sendAsync(handleResponse);
	}

	function getPattern() {

		return pattern;
	}

	function handleResponse(request) {

		if(request.status == HTTP_STATUS_SUCCESS && request.responseText) {
			callback(pattern, eval(request.responseText));
		}
	}
}
