
interface HttpResponseHandler {
	(request: XMLHttpRequest): void;
}

class HttpRequest {
	private url: string;
	private message: string;
	private contentType: string;
	
	constructor() {
		this.url = '';
		this.message = '';
		this.contentType = 'text/plain; charset=UTF-8';
	}

	setUrl(url: string) {
		this.url = url;
		return this;
	}
	
	setMessage(message: string) {
		this.message = message;
		return this;
	}

	setContentType(contentType: string) {
		this.contentType = contentType;
		return this;
	}

	sendSync() {
		let request = this.createHttpRequest(false);
		request.send(this.message);
		return request;
	}

	sendAsync(responseHandler: HttpResponseHandler) {
		let request = this.createHttpRequest(true);
		request.onreadystatechange = function() {
			if(request.readyState == HTTP_REQUEST_STATE_DONE) {
				responseHandler(request);
			}
		}
		request.send(this.message);
		return request;
	}

	private createHttpRequest(async: boolean) {
		let request = new XMLHttpRequest();
		request.open('POST', this.url, async);
		request.setRequestHeader('Content-Type', this.contentType);
		return request;
	}
}
