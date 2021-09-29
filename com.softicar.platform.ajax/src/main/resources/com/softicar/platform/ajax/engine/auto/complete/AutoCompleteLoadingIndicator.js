
function AutoCompleteLoadingIndicator(id, inputContext, color) {

	var indicator = null;

	this.show = show;
	this.hide = hide;

	function show() {

		if(!indicator) {
			indicator = new IndicatorLoading(id, 8, 2, color);
			indicator.show(inputContext.getInput(), -5, -5);
		}
	}

	function hide() {

		if(indicator) {
			indicator.hide();
			indicator = null;
		}
	}
}