
function IndicatorLoading(id, size, border, color) {

	this.show = show;
	this.hide = hide;

	var div = document.createElement('div');
	div.id = id;
	div.style.width = size + 'px';
	div.style.height = size + 'px';
	div.style.border = border + 'px solid ' + color;
	div.style.borderTop = border + 'px solid rgba(128,128,128,0.5)';
	div.style.borderRadius = '50%';
	div.style.animation = 'AjaxSpinAnimation 1.5s linear infinite';
	div.style.position = 'absolute';

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
