
function DomPopupEngine() {

	this.showPopup = showPopup;
	this.hidePopup = hidePopup;

	// Displays the popup with the specified node id
	// at the specified coordinates.
	function showPopup(popup, x, y, xp, yp) {

		// get window size and scrolling position
		var sizeX = window.innerWidth;
		var sizeY = window.innerHeight;
		var scrollX = window.pageXOffset;
		var scrollY = window.pageYOffset;

		// set popup display and positioning
		popup.style.display = 'block';
		popup.style.position = 'absolute';

		// compute left
		var popupWidth = popup.offsetWidth;
		switch(xp)
		{
		default:
		case "LEFT": var popupLeft = scrollX+x; break;
		case "CENTER": var popupLeft = scrollX+x - popupWidth/2; break;
		case "RIGHT": var popupLeft = scrollX+x - popupWidth+1; break;
		}

		// keep left in sane range
		popupLeft = Math.min(popupLeft, scrollX+sizeX-popupWidth);
		popupLeft = Math.max(popupLeft, scrollX);

		// compute top
		var popupHeight = popup.offsetHeight;
		switch(yp)
		{
		default:
		case "TOP": var popupTop = scrollY+y; break;
		case "CENTER": var popupTop = scrollY+y - popupHeight/2; break;
		case "BOTTOM": var popupTop = scrollY+y - popupHeight+1; break;
		}

		// keep top in sane range
		popupTop = Math.min(popupTop, scrollY+sizeY-popupHeight);
		popupTop = Math.max(popupTop, scrollY);

		// show popup
		popup.style.left = popupLeft + 'px';
		popup.style.top = popupTop + 'px';
		_DOM_CONTEXT_.setMaximumZIndex(popup);
		addEventListeners(popup);
		markAsPopup(popup);
	}

	// Hides the popup with the specified node id.
	function hidePopup(popup) {

		// nothing to do
	}

	// -------------------- private -------------------- //

	function addEventListeners(popup) {

		var options = {capture:true,passive:true};
		popup.addEventListener('mousedown', onMouseDown, options);
		popup.addEventListener('focus', onFocus, options);
		// Please note that redundant event handlers are discarded
		// automatically, as long as they are not defined inline.
	}

	function onMouseDown(event) {

		bringToFront(event.currentTarget);
	}

	function onFocus(event) {

		bringToFront(event.currentTarget);
	}

	function bringToFront(popup) {

		// find maximum z-index used by popups
		var maximumZIndex = 0;
		var children = document.body.childNodes;
		for(var index in children) {
			var element = children[index];
			if(isPopup(element) && element.style.zIndex > maximumZIndex) {
				maximumZIndex = element.style.zIndex;
			}
		}

		// increase z-index if necessary
		if(popup.style.zIndex < maximumZIndex) {
			_DOM_CONTEXT_.setMaximumZIndex(popup);
		}
	}

	function markAsPopup(popup) {

		popup['data-is-popup'] = true;
	}

	function isPopup(popup) {

		return popup['data-is-popup'];
	}
}

var DOM_POPUP_ENGINE = new DomPopupEngine();
