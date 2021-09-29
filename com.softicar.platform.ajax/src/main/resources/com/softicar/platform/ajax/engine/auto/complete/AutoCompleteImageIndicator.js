
function AutoCompleteImageIndicator(id, inputContext, resourceUrl, title) {

	var indicator = null;

	this.show = show;
	this.hide = hide;

	function show() {

		if(!indicator) {
			indicator = new IndicatorImage(id, resourceUrl, 10, title);
			indicator.show(inputContext.getInput(), -4, -4);
		}
	}

	function hide() {

		if(indicator) {
			indicator.hide();
			indicator = null;
		}
	}
}