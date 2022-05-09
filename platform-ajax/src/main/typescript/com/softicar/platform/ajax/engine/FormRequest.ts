
class FormRequest {
	private static TARGET_FRAME_NAME = 'ajaxFrame';
	private static MESSAGE_INPUT_NAME = 'ajaxInput';
	private form: HTMLFormElement;
	private frame: HTMLIFrameElement;
	private input: HTMLInputElement;

	public constructor(form: HTMLFormElement) {
		this.form = form;
		this.frame = this.createTargetFrame();
		this.input = this.createMessageInput();
	}

	public setMessage(message: string) {
		this.input.value = message;
		return this;
	}

	public submitForm() {
		// setup target iframe
		document.body.appendChild(this.frame);
	
		// setup form
		this.form.method = 'post';
		this.form.target = this.frame.name;
		this.form.enctype = 'multipart/form-data';
		
		// setup message input
		if(this.form.hasChildNodes()) {
			this.form.insertBefore(this.input, this.form.firstChild);
		} else {
			this.form.appendChild(this.input);
		}

		this.form.submit();
		CURRENT_FORM_REQUEST = this;
	}
	
	public finish() {
		document.body.removeChild(this.frame);
		this.form.removeChild(this.input);
		CURRENT_FORM_REQUEST = null;
	}

	private createTargetFrame() {
		let frame = document.createElement('iframe');
		frame.name = FormRequest.TARGET_FRAME_NAME;
		frame.style.display = 'none';
		return frame;
	}
	
	private createMessageInput() {
		let input = document.createElement('input');
		input.name = FormRequest.MESSAGE_INPUT_NAME;
		input.type = 'hidden';
		input.value = '';
		return input;
	}
}

function handleFormRequestResponse(responseText: string) {
	if(CURRENT_FORM_REQUEST) {
		CURRENT_FORM_REQUEST.finish();
	}

	let ajaxRequest = AJAX_REQUEST_MANAGER.getCurrentRequest();
	if(ajaxRequest) {
		ajaxRequest.handleFormRequestResponse(responseText);
	}
}

let CURRENT_FORM_REQUEST: FormRequest | null = null;
