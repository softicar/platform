
function IndicatorImage(id, resourceUrl, size, title) {

	this.show = show;
	this.hide = hide;

	var img = document.createElement("img");
	img.id = id;
	img.src = resourceUrl;
	img.style.position = 'absolute';
	img.style.cursor = 'default';
	img.style.filter = 'drop-shadow(0px 0px 1px #000)';
	if(size) {
		img.style.width = size + 'px';
		img.style.height = size + 'px';
	}
	if(title) {
		img.title = title;
	}

	function show(parent, left, top) {

		hide();
		img.style.left = left + 'px';
		img.style.top = top + 'px';
		parent.appendChild(img);
	}

	function hide() {

		if(img.parentNode) {
			img.parentNode.removeChild(img);
		}
	}
}
