
var AUTO_COMPLETE_INPUT_CONTEXT_KEY = 'data-auto-complete-input-context';

function AutoCompleteEngine() {

	var currentChangeEventInputContext = null;
	
	this.enableForInput = enableForInput;
	this.isEnabledForInput = isEnabledForInput;
	this.setCommittedValue = setCommittedValue;
	this.setInputInvalid = setInputInvalid;
	this.setValidationMode = setValidationMode;
	this.setIndicatorMode = setIndicatorMode;
	this.setMandatory = setMandatory;
	this.setEnabled = setEnabled;
	this.sendChangeEvent = sendChangeEvent;
	this.notifyChangeEventReturned = notifyChangeEventReturned;
	this.isChangeEventActive = isChangeEventActive;

	function enableForInput(input, inputField) {

		if(!isEnabledForInput(inputField)) {
			setInputContext(inputField, new AutoCompleteInputContext(input, inputField)).setup();
		}
	}

	function isEnabledForInput(inputField) {

		return getInputContext(inputField)? true : false;
	}

	function setCommittedValue(inputField, value) {

		var context = getInputContext(inputField);
		if(context) {
			context.setCommittedValue(value);
		}
	}
	
	function setInputInvalid(inputField) {
		
		var context = getInputContext(inputField);
		if(context) {
			context.setInputInvalid();
		}
	}

	function getInputContext(inputField) {

		return inputField[AUTO_COMPLETE_INPUT_CONTEXT_KEY];
	}

	function setInputContext(inputField, inputContext) {

		inputField[AUTO_COMPLETE_INPUT_CONTEXT_KEY] = inputContext;
		return inputContext;
	}
	
	function sendChangeEvent(inputContext) {
		
		// TODO need additional locking?
		if(!isChangeEventActive(inputContext)) {
			inputContext.getInputField().dispatchEvent(new Event('change'));
			currentChangeEventInputContext = inputContext;
		}
	}
	
	function notifyChangeEventReturned() {
		
		var context = currentChangeEventInputContext;
		currentChangeEventInputContext = null;
		if(!!context) {
			context.onChangeEventReturned();
		}
	}
	
	function isChangeEventActive(inputContext) {
		
		return currentChangeEventInputContext === inputContext;
	}
	
	// -------------------- configuration -------------------- //
	
	function setValidationMode(inputField, validationModeId) {
		
		var context = getInputContext(inputField);
		if(context) {
			context.getConfiguration().setValidationMode(AutoCompleteInputValidationMode.getById(validationModeId));
		}
	}
	
	function setIndicatorMode(inputField, indicatorModeId) {
		
		var context = getInputContext(inputField);
		if(context) {
			context.getConfiguration().setIndicatorMode(AutoCompleteInputIndicatorMode.getById(indicatorModeId));
		}
	}
	
	function setMandatory(inputField, mandatory) {
		
		var context = getInputContext(inputField);
		if(context) {
			context.getConfiguration().setMandatory(mandatory);
		}
	}
	
	function setEnabled(inputField, enabled) {
		
		var context = getInputContext(inputField);
		if(context) {
			context.getConfiguration().setEnabled(enabled);
		}
	}
}

var AUTO_COMPLETE_ENGINE = new AutoCompleteEngine();
