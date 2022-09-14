
class HttpRequest {
	private url: string;
	private message: string;
	private contentType: string;
	
	public constructor() {
		this.url = '';
		this.message = '';
		this.contentType = 'application/json; charset=UTF-8';
	}

	public setUrl(url: string) {
		this.url = url;
		return this;
	}
	
	public setMessage(message: string) {
		this.message = message;
		return this;
	}

	public setContentType(contentType: string) {
		this.contentType = contentType;
		return this;
	}

	public sendSync() {
		let request = this.createHttpRequest(false);
		request.send(this.message);
		return request;
	}

	public sendAsync(responseHandler: (request: XMLHttpRequest) => void) {
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
