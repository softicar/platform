
function AutoCompleteState(inputContext) {
	
	var loading = false;
	var committing = false;
	var valueState = null;
	
	this.isLoading = isLoading;
	this.isCommitting = isCommitting;
	this.isValueAmbiguous = isValueAmbiguous;
	this.isValueIllegal = isValueIllegal;
	this.isValueMissing = isValueMissing;
	this.isValueValid = isValueValid;
	
	this.setLoading = setLoading;
	this.setCommitting = setCommitting;
	this.setValueState = setValueState;
	
	function isLoading() {
		
		return loading;
	}

	function isCommitting() {

		return committing;
	}
	
	function isValueAmbiguous() {
		
		return valueState == AutoCompleteValueState.AMBIGUOUS;
	}
	
	function isValueIllegal() {
		
		return valueState == AutoCompleteValueState.ILLEGAL;
	}
	
	function isValueMissing() {
		
		return valueState == AutoCompleteValueState.MISSING;
	}
	
	function isValueValid() {
		
		return valueState == AutoCompleteValueState.VALID;
	}
	
	function setLoading(loading_) {
		
		loading = loading_;
	}
	
	function setCommitting(committing_) {
		
		committing = committing_;
	}
	
	function setValueState(valueState_) {
		
		valueState = valueState_;
	}
}
