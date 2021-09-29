
function AutoCompleteStatusIndicator(id, text, inputContext) {

	var indicator = null;

	this.show = show;
	this.hide = hide;

	function show() {

		if(!indicator) {
			var height = inputContext.getInputFieldClientHeight() / 1.25;
			var offset = -height / 2;
			indicator = new IndicatorText(id, height, text);
			indicator.show(inputContext.getInput(), offset, offset);
		}
	}

	function hide() {

		if(indicator) {
			indicator.hide();
			indicator = null;
		}
	}
}