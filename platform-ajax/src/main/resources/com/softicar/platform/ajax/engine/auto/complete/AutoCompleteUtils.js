
function getAbsolutePosition(node) {
	var position = {x: node.offsetLeft, y: node.offsetTop};
	if(node.offsetParent)
	{
		var positionParent = getAbsolutePosition(node.offsetParent);
		position.x += positionParent.x;
		position.y += positionParent.y;
	}
	return position;
}

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

function showAtAbsolutePosition(element, left, top, width) {

	element.style.left = left + 'px';
	element.style.top = top + 'px';
	element.style.width = width + 'px';
	element.style.boxSizing = 'border-box';
	document.body.appendChild(element);
	AJAX_ENGINE.raise(element);
}

function Utils() {

	this.getAbsolutePosition = getAbsolutePosition;
	this.isVisible = isVisible;
	this.hasFocus = hasFocus;
	this.getBoundingRect = getBoundingRect;
	this.showAtAbsolutePosition = showAtAbsolutePosition;
}

var UTILS = new Utils();
