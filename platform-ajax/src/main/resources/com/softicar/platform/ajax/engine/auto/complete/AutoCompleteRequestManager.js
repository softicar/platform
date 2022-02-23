
function AutoCompleteRequestManager(inputContext, callback) {

	// -------------------- fields -------------------- //

	var self = this;
	var requests = {};
	var desiredPattern = null;

	// -------------------- interface -------------------- //

	this.submit = submit;
	this.cancelAll = cancelAll;
	this.isLoading = isLoading;

	// -------------------- implementation -------------------- //

	function submit(pattern) {

		desiredPattern = pattern;
		scheduleRequest(pattern);
	}

	function cancelAll() {

		requests = {};
		desiredPattern = null;
	}

	function isLoading(pattern) {

		return !!requests[pattern];
	}

	function scheduleRequest(pattern) {

		if(!requests[pattern]) {
			setTimeout(sendRequest.bind(self, pattern), getRequestDelay());
		}
	}

	function sendRequest(pattern) {

		if(pattern == desiredPattern) {
			var request = new AutoCompleteRequest(inputContext, pattern, handleRequestResponse);
			request.send();
			requests[pattern] = request;
			inputContext.updateState();
		}
	}

	function getRequestDelay() {

		return getSize(requests) * 100;
	}

	function handleRequestResponse(pattern, response) {

		delete requests[pattern];
		if(pattern == desiredPattern) {
			callback(pattern, response);
		}
	}
}
