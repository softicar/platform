
class AjaxRequestManager {
	private currentRequest: AjaxRequest | null;
	private requestIndex: number;

	public constructor() {
		this.currentRequest = null;
		this.requestIndex = 0;
	}

	public openRequest(request: AjaxRequest): number {
		if(this.currentRequest == null) {
			this.currentRequest = request;
			return this.requestIndex;
		} else {
			throw new Error("Internal error: Tried to send two server request at the same time.");
		}
	}

	public closeRequest(request: AjaxRequest) {
		if(request === this.currentRequest) {
			this.currentRequest = null;
			this.requestIndex += 1;
		} else {
			throw new Error("Internal error: Given request does not match current request.");
		}
	}
	
	public getCurrentRequest() {
		return this.currentRequest;
	}
}

const AJAX_REQUEST_MANAGER = new AjaxRequestManager();
