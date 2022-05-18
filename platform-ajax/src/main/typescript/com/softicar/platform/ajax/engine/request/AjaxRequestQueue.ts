/**
 * Handles the sending of {@link AjaxRequest} instances.
 * <p>
 * This class automatically drops redundant {@link AjaxRequest} instances,
 * e.g. multiple click events on the same {@link HTMLElement}. The redundancy
 * rules are defined in {@link AjaxRequestMessage#isRedundantTo}.
 */
class AjaxRequestQueue {
	private readonly requests = new Array<AjaxRequest>();
	private requestIndex = 0;
	private waitingForServer = false;

	public submit(message: AjaxRequestMessage, form: HTMLFormElement | null = null) {
		if(!this.isRedundant(message)) {
			this.push(new AjaxRequest(message, form));
		}
	}

	public onRequestResponse(javaScript: string) {
		this.finishRequest();
		this.executeJavaScript(javaScript);
		this.process();
	}

	private finishRequest() {
		this.requests.shift();
		this.requestIndex += 1;
		this.waitingForServer = false;

		// FIXME this ad-hoc call looks like a hack (PLAT-853)
		AUTO_COMPLETE_ENGINE.notifyChangeEventReturned();
		KEEP_ALIVE.schedule();
	}

	private executeJavaScript(javaScript: string) {
		if(javaScript) {
			eval(javaScript);
		}
	}

	private push(request: AjaxRequest) {
		this.requests.push(request);
		this.process();
	}

	private process() {
		if(!this.waitingForServer) {
			this.dropObsoleteRequests();
			
			if(this.hasRequests()) {
				this.sendNextRequest();
				this.waitingForServer = true;
				showWorkingIndicator();
			} else {
				hideWorkingIndicator();
			}
		}
	}

	private dropObsoleteRequests() {
		while(this.hasRequests() && this.getNextRequest().isObsolete()) {
			this.requests.shift();
		}
	}

	private hasRequests() {
		return this.requests.length > 0;
	}
	
	private getNextRequest() {
		return this.requests[0];
	}
		
	private sendNextRequest() {
		let request = this.getNextRequest();
		if(request) {
			request.send(this.requestIndex);
		} else {
			throw new Error("Internal error: Undefined request object in request queue.");
		}
	}

	private isRedundant(message: AjaxRequestMessage) {
		for(let request of this.requests) {
			if(request.isRedundant(message)) {
				return true;
			}
		}
		return false;
	}
}

const AJAX_REQUEST_QUEUE = new AjaxRequestQueue();
