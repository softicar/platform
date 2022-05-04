let SESSION_TIMED_OUT = false;
let SESSION_TIMEOUT_DIALOG: HTMLElement;

function setSessionTimeoutDialog(dialog: HTMLElement) {
	SESSION_TIMEOUT_DIALOG = dialog;
}

function handleSessionTimeout() {
	SESSION_TIMED_OUT = true;

	if(SESSION_TIMEOUT_DIALOG) {
		SESSION_TIMEOUT_DIALOG.style.zIndex = _DOM_CONTEXT_.allocateZIndex();
		SESSION_TIMEOUT_DIALOG.classList.remove(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
	}
}
