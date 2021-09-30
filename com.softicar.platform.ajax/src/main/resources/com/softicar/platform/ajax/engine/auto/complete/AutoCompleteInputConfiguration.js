
function AutoCompleteInputConfiguration(context) {

	var validationMode = AutoCompleteInputValidationMode.DEDUCTIVE;
	var indicatorMode = AutoCompleteInputIndicatorMode.VALIDATION;
	var mandatory = false;
	var enabled = true;
	
	this.getIndicatorMode = getIndicatorMode;
	this.setIndicatorMode = setIndicatorMode;
	this.getValidationMode = getValidationMode;
	this.setValidationMode = setValidationMode;
	this.isMandatory = isMandatory;
	this.setMandatory = setMandatory;
	this.isEnabled = isEnabled;
	this.setEnabled = setEnabled;
	
	function getIndicatorMode() {
		
		return indicatorMode;
	}
	
	function setIndicatorMode(indicatorMode_) {

		indicatorMode = indicatorMode_;
		context.updateState();
	}
	
	function getValidationMode() {
		
		return validationMode;
	}
	
	function setValidationMode(validationMode_) {

		validationMode = validationMode_;
		context.updateState();
	}
	
	function isMandatory() {
		
		return mandatory;
	}
	
	function setMandatory(mandatory_) {
	
		mandatory = mandatory_;
		context.updateState();
	}
	
	function isEnabled() {
		
		return enabled;
	}
	
	function setEnabled(enabled_) {
		
		enabled = enabled_;
		context.updateState();
	}
}
