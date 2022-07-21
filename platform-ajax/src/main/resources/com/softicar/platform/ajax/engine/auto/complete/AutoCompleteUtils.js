
function isVisible(node) {
	return node.offsetWidth > 0 && node.offsetHeight > 0;
}

function hasFocus(node) {
	return node == document.activeElement;
}

function isEmpty(dictionary) {
	return getSize(dictionary) == 0;
}

function getSize(dictionary) {
	return Object.keys(dictionary).length;
}

function getBoundingRect(element) {

	var r = element.getBoundingClientRect();
	var x = window.pageXOffset;
	var y = window.pageYOffset;
	return {
		left: r.left + x, right: r.right + x,
		top: r.top + y, bottom: r.bottom + y,
		width: r.width, height: r.height
	};
}

function showAtAbsolutePosition(element, inputContext) {

	element.style.top = inputContext.getInputFieldOffsetHeight() + 'px';
	element.style.minWidth = inputContext.getInputFieldBoundingRect().width + 'px';
	element.style.boxSizing = 'border-box';
	inputContext.getInput().appendChild(element);
	AJAX_ENGINE.raise(element);
}

function Utils() {

	this.isVisible = isVisible;
	this.hasFocus = hasFocus;
	this.getBoundingRect = getBoundingRect;
	this.showAtAbsolutePosition = showAtAbsolutePosition;
}

var UTILS = new Utils();
