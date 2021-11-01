
function AutoCompleteStatusHandler(inputContext, inputField) {

	var indicatorCommitting = new AutoCompleteLoadingIndicator(
			'AutoCompleteIndicatorCommitting',
			inputContext,
			'rgba(255,0,0,1)');
	
	var indicatorLoading = new AutoCompleteLoadingIndicator(
			'AutoCompleteIndicatorLoading',
			inputContext,
			'rgba(96,96,255,1)');
	
	var indicatorGeneric = new AutoCompleteImageIndicator(
			'AutoCompleteIndicatorGeneric',
			inputContext,
			AUTO_COMPLETE_IMAGE_GENERIC,
			AUTO_COMPLETE_TEXT_GENERIC);
	
	var indicatorNotOkay = new AutoCompleteImageIndicator(
			'AutoCompleteIndicatorNotOkay',
			inputContext,
			AUTO_COMPLETE_IMAGE_NOT_OKAY,
			AUTO_COMPLETE_TEXT_NOT_OKAY);
	
	var indicatorValueAmbiguous = new AutoCompleteImageIndicator(
			'AutoCompleteIndicatorValueAmbiguous',
			inputContext,
			AUTO_COMPLETE_IMAGE_VALUE_AMBIGUOUS,
			AUTO_COMPLETE_TEXT_VALUE_AMBIGUOUS);
	
	var indicatorValueIllegal = new AutoCompleteImageIndicator(
			'AutoCompleteIndicatorValueIllegal',
			inputContext,
			AUTO_COMPLETE_IMAGE_VALUE_ILLEGAL,
			AUTO_COMPLETE_TEXT_VALUE_ILLEGAL);
	
	var indicatorValueMissing = new AutoCompleteImageIndicator(
			'AutoCompleteIndicatorValueMissing',
			inputContext,
			AUTO_COMPLETE_IMAGE_VALUE_MISSING,
			AUTO_COMPLETE_TEXT_VALUE_MISSING);
	
	var indicatorValueValid = new AutoCompleteImageIndicator(
			'AutoCompleteIndicatorValueValid',
			inputContext,
			AUTO_COMPLETE_IMAGE_VALUE_VALID,
			AUTO_COMPLETE_TEXT_VALUE_VALID);

	var currentIndicator = null;

	// -------------------- interface -------------------- //

	this.update = update;

	// -------------------- implementation -------------------- //

	function update() {

		if(inputContext.getConfiguration().isEnabled()) {
			var state = inputContext.getState();
			var genericIndicatorMode = inputContext.getConfiguration().getIndicatorMode().isGeneric();
			if(state.isCommitting()) {
				showIndicator(indicatorCommitting);
			} else if(state.isLoading()) {
				showIndicator(indicatorLoading);
			} else if(genericIndicatorMode) {
				showIndicator(indicatorGeneric);
			} else if(state.isValueAmbiguous()) {
				showIndicator(inputContext.hasFocus() ? indicatorValueAmbiguous : indicatorNotOkay);
			} else if(state.isValueIllegal()) {
				showIndicator(inputContext.hasFocus() ? indicatorValueIllegal : indicatorNotOkay);
			} else if(state.isValueMissing()) {
				showIndicator(indicatorValueMissing);
			} else if(state.isValueValid()) {
				showIndicator(indicatorValueValid);
			} else {
				hideIndicator();
			}
		} else {
			hideIndicator();
		}
	}

	// -------------------- private -------------------- //

	function showIndicator(indicator) {

		if(indicator !== null) {
			if(currentIndicator && currentIndicator !== indicator) {
				hideIndicator();
			}
			indicator.show();
			currentIndicator = indicator;
		} else {
			hideIndicator();
		}
	}

	function hideIndicator() {

		if(currentIndicator) {
			currentIndicator.hide();
			currentIndicator = null;
		}
	}
}
