
function AutoCompleteRequest(inputContext, pattern, callback) {

	this.send = send;

	function send() {

		var message = {};
		message.a = AJAX_REQUEST_AUTO_COMPLETE;
		message.i = DOCUMENT_INSTANCE_UUID;
		message.n = inputContext.getInput().id;
		message.p = pattern;
		SR_sendAsyncHTTPMessage('', SR_encodeHTTPMessage(message), handleResponse);
	}

	function getPattern() {

		return pattern;
	}

	function handleResponse(request, success) {

		if(success && request.responseText) {
			callback(pattern, eval(request.responseText));
		}
	}
}
