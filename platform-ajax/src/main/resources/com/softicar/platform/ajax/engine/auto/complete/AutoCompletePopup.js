
function AutoCompletePopup(inputContext, applySelection) {

	// -------------------- fields -------------------- //

	var div = new DomElementBuilder('div').setId('AjaxAutoCompletePopup').setClassName('AjaxAutoCompletePopup').build();
	var pattern = null;
	var items = null;
	var cells = [];
	var selectedIndex = null;

	// -------------------- interface -------------------- //

	this.show = show;
	this.hide = hide;
	this.isShown = isShown;
	this.setMaximumZIndex = setMaximumZIndex;
	this.getPattern = getPattern;
	this.moveSelection = moveSelection;
	this.initializeSelection = initializeSelection;
	this.getSelectedIndex = getSelectedIndex;
	this.getValue = getValue;

	// -------------------- implementation -------------------- //

	function show(pattern_, response) {

		pattern = pattern_;
		items = response.items;

		clear();
		if(items.length > 0) {
			appendValues(response.maxRows);
			initializeSelection();
		} else {
			appendNoValues();
		}

		if(!isShown()) {
			showDiv();
		}
	}

	function clear() {

		cells = [];
		selectedIndex = null;
		while(div.childNodes.length > 0) {
			div.removeChild(div.firstChild);
		}
	}

	function appendValues(maxRows) {

		for(var i = 0; i < items.length; ++i) {
			if(i == maxRows){
				new DomElementBuilder('span')
				.appendTo(div)
				.setClassName('AjaxAutoCompleteMoreItems')
				.appendText("(" + AUTO_COMPLETE_TEXT_FURTHER_ENTRIES_AVAILABLE + ")");
				continue;
			}
			// append new row
			var builder = new DomElementBuilder('span')
				.appendTo(div)
				.setClassName('AjaxAutoCompleteItem');
			builder.getElement().onmousedown = applySelection.bind(this, i);
			cells[i] = builder.getElement();

			// append name with matching highlight
			var name = items[i].v;
			var index = name.toLowerCase().indexOf(pattern);
			if(index >= 0) {
				if(index > 0) {
					builder.appendText(name.substr(0, index));
				}
				if(pattern.length > 0) {
					builder.appendNewChild('span')
						.appendText(name.substr(index, pattern.length))
						.setClassName('AjaxAutoCompleteMatch');
				}
				if(name.length > index+pattern.length) {
					builder.appendText(name.substr(index+pattern.length));
				}
			} else {
				builder.appendText(name);
			}
			
			// append description
			var description = items[i].d;
			if(description) {
				builder.appendNewChild('span')
					.appendText('(' + description + ')')
					.setClassName('AjaxAutoCompleteDescription');
			}
		}
	}

	function appendNoValues() {

		// append row
		var message = pattern != ""? pattern + ": " + AUTO_COMPLETE_TEXT_NO_RECORDS_FOUND : "(" + AUTO_COMPLETE_TEXT_NO_RECORDS_FOUND + ")";
		var builder = new DomElementBuilder('div')
			.appendTo(div)
			.appendText(message)
			.setClassName('AjaxAutoCompleteNoItems');
	}

	function showDiv() {

		var position = inputContext.getInputFieldAbsolutePosition();
		var boundingRect = inputContext.getInputFieldBoundingRect();
		showAtAbsolutePosition(div, position.x, position.y + inputContext.getInputFieldOffsetHeight(), boundingRect.width);
	}

	function hide() {

		if(div.parentNode) {
			div.parentNode.removeChild(div);
		}
	}

	function isShown() {

		return !!div.parentNode;
	}
	
	function setMaximumZIndex() {
		
		if(isShown()) {
			_DOM_CONTEXT_.setMaximumZIndex(div);
		}
	}

	function getPattern() {

		return pattern;
	}

	function getValue(index) {

		return items[index].v;
	}

	// -------------------- selection -------------------- //

	function moveSelection(up) {

		if(items.length > 0) {
			if(up) {
				var begin = selectedIndex == 0 || selectedIndex === null;
				setSelection(begin? items.length-1 : selectedIndex-1);
			} else {
				var end = selectedIndex == items.length-1 || selectedIndex === null;
				setSelection(end? 0 : selectedIndex+1);
			}
		}
	}

	function getSelectedIndex() {

		return selectedIndex;
	}

	function setSelection(index) {

		if(items.length > 0) {
			clearSelection();
			selectedIndex = index;
			cells[selectedIndex].className = "AjaxAutoCompleteItem AjaxAutoCompleteSelectedItem";
		}
	}

	function clearSelection() {

		if(selectedIndex !== null) {
			if(cells[selectedIndex]) {
				cells[selectedIndex].className = "AjaxAutoCompleteItem";
			}
			selectedIndex = null;
		}
	}

	function initializeSelection() {

		if(inputContext.getPattern() == "" || inputContext.getPattern() != getPattern()) {
			clearSelection();
		} else {
			setSelection(0);
		}
	}
}
