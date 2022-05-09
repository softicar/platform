let WORKING_INDICATOR_ENABLED = true;
let WORKING_INDICATOR: HTMLElement;

function setWorkingIndicator(indicator: HTMLElement) {
	WORKING_INDICATOR = indicator;
}

function showWorkingIndicator() {
	if(WORKING_INDICATOR && WORKING_INDICATOR_ENABLED)
	{
		WORKING_INDICATOR.style.zIndex = _DOM_CONTEXT_.allocateZIndex();
		WORKING_INDICATOR.classList.remove(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
	}
}

function hideWorkingIndicator() {
	if(WORKING_INDICATOR) {
		WORKING_INDICATOR.classList.add(AJAX_CSS_PSEUDO_CLASS_HIDDEN);
	}
}

function setWorkingIndicatorEnabled(enabled: boolean) {
	WORKING_INDICATOR_ENABLED = enabled;
}
