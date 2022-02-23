
function AutoCompleteInputContext(input, inputField) {

	var self = this;
	var committedValue = inputField.value;
	var authoritativeValue = inputField.value;
	var filterPattern = null;
	var filterItems = [];
	var overlayDiv = null;
	var state = new AutoCompleteState(self);
	var popup = new AutoCompletePopup(self, onSelection);
	var statusHandler = new AutoCompleteStatusHandler(self, inputField);
	var requestManager = new AutoCompleteRequestManager(self, onRequestResponse);
	var configuration = new AutoCompleteInputConfiguration(self);

	// -------------------- interface -------------------- //

	this.setup = setup;
	this.getConfiguration = getConfiguration;
	this.getInput = getInput;
	this.getState = getState;
	this.updateState = updateState;
	this.setCommittedValue = setCommittedValue;
	this.setInputInvalid = setInputInvalid;
	this.getInputField = getInputField;
	this.getPattern = getPattern;
	this.isPopupUpToDate = isPopupUpToDate;
	this.hasChangeHandler = hasChangeHandler;
	this.onChangeEventReturned = onChangeEventReturned;

	this.hasFocus = hasFocus;
	this.getInputFieldAbsolutePosition = getInputFieldAbsolutePosition;
	this.getInputFieldClientHeight = getInputFieldClientHeight;
	this.getInputFieldOffsetHeight = getInputFieldOffsetHeight;
	this.getInputFieldBoundingRect = getInputFieldBoundingRect; 

	// -------------------- logic -------------------- //

	function setup() {

		inputField.onfocus = onFocus;
		inputField.onblur = onBlur;
		inputField.oninput = onInput;
		inputField.onkeydown = onKeyDown;
		inputField.onkeyup = onKeyUp;

		updateState();
	}
	
	function getConfiguration() {
		
		return configuration;
	}
	
	// -------------------- event listeners -------------------- //

	function onFocus() {

		updateState();
	}

	function onBlur() {

		sendChangeEventIfNecessary();
		leaveModalModeIfNecessary();

		terminateSelection();
	}

	function onInput(event) {

		if(isCommittedValue()) {
			leaveModalModeIfNecessary();
		} else {
			enterModalModeIfNecessary();
		}

		requestManager.submit(getPattern());
		popup.initializeSelection();
	}

	function onKeyDown(event) {

		// suppress propagation of ENTER while loading
		if(getState().isLoading() && event.keyCode == DOM_VK_ENTER) {
			event.stopPropagation();
		}
		
		// suppress all keys while change event is handled
		if(isChangeEventActive()) {
			event.preventDefault();
			event.stopPropagation();
			return;
		}
		
		if(event.keyCode == DOM_VK_ESCAPE) {
			// prevent default browser action
			// to undo value change on ESCAPE
			event.preventDefault();
		} else if(popup.isShown()) {
			if(event.keyCode == DOM_VK_UP) {
				popup.moveSelection(true);
			} else if(event.keyCode == DOM_VK_DOWN) {
				popup.moveSelection(false);
			} else if(event.keyCode == DOM_VK_TAB) {
				// if pressing TAB while popup is shown
				// should not execute the default behavior
				event.preventDefault();
			}
			event.stopPropagation();
		} else {
			if(event.keyCode == DOM_VK_UP || event.keyCode == DOM_VK_DOWN) {
				requestManager.submit(getPattern());
			}
		}
	}

	function onKeyUp(event) {

		if(event.keyCode == DOM_VK_ESCAPE) {
			if(popup.isShown()) {
				applyCurrentValue();
				terminateSelection();
				event.stopPropagation();
			}
		} else if(event.keyCode == DOM_VK_ENTER || event.keyCode == DOM_VK_TAB) {
			if(popup.isShown() && popup.getSelectedIndex() !== null) {
				onSelection();
				if(!isChangeEventActive()) {
					leaveModalModeIfNecessary();
				}
				event.stopPropagation();
			} else {
				if(isEmptyPattern()) {
					applyValue("");
				}
				if(hasChangeHandler() && event.keyCode == DOM_VK_ENTER) {
					event.stopPropagation();
				}
			}
		}
	}

	function onRequestResponse(pattern, response) {

		filterPattern = pattern;
		filterItems = response.items;
		if(hasFocus() && !isChangeEventActive()) {
			popup.show(pattern, response);
			popup.setMaximumZIndex();
		}
		if(isPopupUpToDate()) {
			requestManager.cancelAll();
		}
		updateState();
	}

	function onSelection(index) {

		if(popup.isShown()) {
			var index = index != null? index : popup.getSelectedIndex();
			if(index !== null) {
				applyValue(popup.getValue(index));
			}
		}
	}

	function onChangeEventReturned() {
		
		updateState();
	}
	
	function onModalOverlayClicked(event) {
		
		applyCurrentValue();
		terminateSelection();
		
		// suppress clicks while change event is handled
		if(isChangeEventActive()) {
			event.preventDefault();
			event.stopPropagation();
			return;
		}
	}
	
	// -------------------- change event -------------------- //
	
	function sendChangeEvent() {
		
		if(GLOBAL.isValueChanged(inputField)) {
			AUTO_COMPLETE_ENGINE.sendChangeEvent(self);
		}
	}
	
	function isChangeEventActive() {
		
		return AUTO_COMPLETE_ENGINE.isChangeEventActive(self);
	}
	
	// -------------------- state -------------------- //

	function updateState() {

		state.setLoading(requestManager.isLoading(getPattern()));
		state.setCommitting(isCommitting());
		state.setValueState(determineValueState());
		statusHandler.update();
	}
	
	function determineValueState() {
		
		if(isValueMissing()) {
			return AutoCompleteValueState.MISSING;
		} else if(isValueEmptyOrValidationModePermissive()) {
			return AutoCompleteValueState.VALID;
		} else if(isAuthoritativeValue()) {
			return AutoCompleteValueState.VALID;
		} else if(configuration.getValidationMode().isRestrictive()) {
			if(isPatternInFilterItems()) {
				return AutoCompleteValueState.VALID;
			} else {
				return AutoCompleteValueState.ILLEGAL;
			}
		} else {
			if(isSoleResultForCurrentPattern()) {
				return AutoCompleteValueState.VALID;
			} else {
				if(isSeveralResultsForCurrentPattern()) {
					if(isPatternInFilterItems()) {
						return AutoCompleteValueState.VALID;
					} else {
						return AutoCompleteValueState.AMBIGUOUS;
					}
				} else {
					return AutoCompleteValueState.ILLEGAL;
				}
			}
		}
	}
	
	function isValueMissing() {

		return configuration.isMandatory() && isEmptyPattern();
	}
	
	function isValueEmptyOrValidationModePermissive() {

		return isEmptyPattern() || configuration.getValidationMode().isPermissive();
	}
	
	function isSoleResultForCurrentPattern() {
		
		return getItemCountForCurrentPattern() == 1;
	}
	
	function isSeveralResultsForCurrentPattern() {
		
		return getItemCountForCurrentPattern() > 1;
	}
	
	function getItemCountForCurrentPattern() {
		
		if(filterPattern == getPattern()) {
			return filterItems.length;
		} else {
			return undefined;
		}
	}
	
	function isEmptyPattern() {
		
		return getPattern() == "";
	}
	
	function isPatternInFilterItems() {
		
		var pattern = getPattern();
		for(var i = 0; i < filterItems.length; i++) {
			if(filterItems[i].v == pattern) {
				return true;
			}
		}
		return false;
	}
	
	function getState() {

		return state;
	}

	function isCommitting() {

		return isChangeEventActive();
	}

	// -------------------- value -------------------- //

	function isCommittedValue() {

		return inputField.value == committedValue;
	}
	
	function isAuthoritativeValue() {
		
		return inputField.value == authoritativeValue;
	}

	function setCommittedValue(value) {

		committedValue = value;
		authoritativeValue = value;
		leaveModalModeIfNecessary();
		updateState();
	}

	function setInputInvalid() {

		authoritativeValue = "";
		filterPattern = null;
		filterItems = [];
		updateState();
	}
	
	function applyValue(value) {

		requestManager.cancelAll();
		authoritativeValue = value;

		inputField.value = value;
		sendChangeEventIfNecessary();

		terminateSelection();
	}
	
	function applyCurrentValue() {

		sendChangeEventIfNecessary();
	}
	
	function sendChangeEventIfNecessary() {
		
		if(hasChangeHandler() && !isCommittedValue()) {
			sendChangeEvent();
		}
	}

	function terminateSelection() {

		if(popup.isShown()) {
			popup.hide();
		}
		updateState();
	}

	// -------------------- miscellaneous -------------------- //

	function getInput() {

		return input;
	}

	function getInputField() {

		return inputField;
	}

	function getPattern() {

		var pattern = inputField.value;
		return pattern ? pattern.trim() : "";
	}

	function isPopupUpToDate() {

		return popup.isShown() && popup.getPattern() == getPattern();
	}
	
	function hasChangeHandler() {

		return !!inputField.onchange;
	}

	// -------------------- modal mode -------------------- //

	function enterModalModeIfNecessary() {

		if(hasChangeHandler() && overlayDiv === null) {
			// create overlay div
			overlayDiv = document.createElement('div');
			overlayDiv.id = 'AjaxAutoCompleteModalDiv';
			overlayDiv.style.position = 'fixed';
			overlayDiv.style.top = '0';
			overlayDiv.style.right = '0';
			overlayDiv.style.bottom = '0';
			overlayDiv.style.left = '0';
			overlayDiv.style.backgroundColor = DEBUG? 'rgba(255,255,255,0.5)' : 'transparent';
			overlayDiv.onmousedown = onModalOverlayClicked;
			input.parentNode.insertBefore(overlayDiv, input.nextSibling);

			// position input
			inputField.style.position = 'relative';

			_DOM_CONTEXT_.setMaximumZIndex(overlayDiv);
			_DOM_CONTEXT_.setMaximumZIndex(input);
			popup.setMaximumZIndex();
		}
	}

	function leaveModalModeIfNecessary() {

		if(overlayDiv) {
			inputField.style.position = 'static';
			inputField.style.zIndex = 'auto';
			overlayDiv.parentNode.removeChild(overlayDiv);
			overlayDiv = null;
		}
	}
	
	// -------------------- canvas -------------------- //

	function hasFocus() {

		return UTILS.hasFocus(inputField);
	}

	function getInputFieldAbsolutePosition() {

		return UTILS.getAbsolutePosition(inputField);
	}
	
	function getInputFieldBoundingRect() {
		
		return UTILS.getBoundingRect(inputField);
	}
	
	function getInputFieldClientHeight() {

		return inputField.clientHeight;
	}

	function getInputFieldOffsetHeight() {

		return inputField.offsetHeight;
	}
}
