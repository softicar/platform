
function DomPopupEngine() {

	this.initializePopup = initializePopup;
	this.movePopup = movePopup;

	/**
	 * Initializes the popup with the specified popup frame node ID.
	 *
	 * @param autoRaise <i>true</i> if the popup shall be raised upon click or focus; <i>false</i> otherwise
	 */
	function initializePopup(popupFrame, autoRaise) {

		if(autoRaise) {
			addEventListeners(popupFrame);
		}
	}

	/**
	 * Moves the popup with the specified popup frame node ID to the specified coordinates.
	 */
	function movePopup(popupFrame, x, y, xAlign, yAlign) {

		// get window size and scrolling position
		var sizeX = window.innerWidth;
		var sizeY = window.innerHeight;
		var scrollX = window.pageXOffset;
		var scrollY = window.pageYOffset;

		// compute left
		var popupWidth = popupFrame.offsetWidth;
		switch(xAlign)
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
		var popupHeight = popupFrame.offsetHeight;
		switch(yAlign)
		{
		default:
		case "TOP": var popupTop = scrollY+y; break;
		case "CENTER": var popupTop = scrollY+y - popupHeight/2; break;
		case "BOTTOM": var popupTop = scrollY+y - popupHeight+1; break;
		}

		// keep top in sane range
		popupTop = Math.min(popupTop, scrollY+sizeY-popupHeight);
		popupTop = Math.max(popupTop, scrollY);

		// compensate for position of parent
		var parent = popupFrame.parentElement;
		if(parent != null) {
			if(parent !=  document.body) {
				let parentRect = parent.getBoundingClientRect();
				popupLeft -= parentRect.left;
				popupTop -= parentRect.top;
			}
		} else {
			console.log("Warning: Tried to move a popup that was not appended. Its position might be unexpected.");
		}

		// move popup
		popupFrame.style.left = popupLeft + 'px';
		popupFrame.style.top = popupTop + 'px';
	}

	// -------------------- private -------------------- //

	function addEventListeners(popupFrame) {

		var options = {capture:true,passive:true};
		popupFrame.addEventListener('mousedown', onMouseDown, options);
		popupFrame.addEventListener('focus', onFocus, options);
		// Please note that redundant event handlers are discarded
		// automatically, as long as they are not defined inline.
	}

	function onMouseDown(event) {

		raise(event.currentTarget);
	}

	function onFocus(event) {

		raise(event.currentTarget);
	}

	function raise(popupFrame) {

		_DOM_CONTEXT_.setMaximumZIndex(popupFrame);
	}
}

var DOM_POPUP_ENGINE = new DomPopupEngine();
