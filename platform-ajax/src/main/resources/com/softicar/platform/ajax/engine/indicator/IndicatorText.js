
function IndicatorText(id, size, text) {

	this.show = show;
	this.hide = hide;

	var div = document.createElement('div');
	div.id = id;
	div.style.width = size + 'px';
	div.style.height = size + 'px';
	div.style.fontSize = size - 2 + 'px';

	div.style.position = 'absolute';
	div.style.display = 'flex';
	div.style.justifyContent = 'center';
	div.style.alignItems = 'center';
	div.style.color = '#808080';
	div.style.backgroundColor = '#0F0'; // TODO debug. remove.

	div.style.cursor = 'default';
	div.appendChild(document.createTextNode(text));

	function show(parent, left, top) {

		hide();
		div.style.left = left + 'px';
		div.style.top = top + 'px';
		parent.appendChild(div);
	}

	function hide() {

		if(div.parentNode) {
			div.parentNode.removeChild(div);
		}
	}
}
