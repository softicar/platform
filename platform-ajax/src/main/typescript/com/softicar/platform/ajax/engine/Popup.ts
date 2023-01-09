
class DomPopupEngine {
	/**
	 * Initializes the popup with the specified popup frame node ID.
	 *
	 * @param autoRaise <i>true</i> if the popup shall be raised upon click or focus; <i>false</i> otherwise
	 */
	public initializePopup(popupFrame: HTMLElement, autoRaise: boolean) {
		if(autoRaise) {
			this.addEventListeners(popupFrame);
		}
	}

	/**
	 * Moves the popup with the specified popup frame node ID to the specified coordinates.
	 */
	public movePopup(popupFrame: HTMLElement, x: number, y: number, offsetUnit: string, xAlign: string, yAlign: string) {
		
		let parent = popupFrame.parentElement;
		if(parent != null) {
			// This exiplicit position assignment seems to be very important.
			// Without it, the computation of the offset-width of the frame
			// seems to be nondeterministic.
			popupFrame.style.position = 'absolute';

			if(offsetUnit == "PERCENT") {
				this.movePopupByPercent(popupFrame, x, y, xAlign, yAlign);
			} else {
				this.movePopupByPixels(parent, popupFrame, x, y, xAlign, yAlign);
			}
		} else {
			console.log("Warning: Ignored an attempt to move a non-appended popup. Popup frame ID: " + popupFrame.id);
		}
	}

	// -------------------- private -------------------- //

	private addEventListeners(popupFrame: HTMLElement) {
		let options = {capture:true,passive:true};
		popupFrame.addEventListener('mousedown', _ => this.raise(popupFrame), options);
		popupFrame.addEventListener('focus', _ => this.raise(popupFrame), options);
	}

	private raise(popupFrame: HTMLElement) {
		AJAX_ENGINE.raise(popupFrame);
	}

	private movePopupByPercent(popupFrame: HTMLElement, x: number, y: number, xAlign: string, yAlign: string) {
		// compute left
		let popupWidth = popupFrame.offsetWidth;
		let popupLeft = 0;
		switch(xAlign) {
			case "LEFT": popupLeft = 0; break;
			case "CENTER": popupLeft = popupWidth/2; break;
			case "RIGHT": popupLeft = popupWidth; break;
		}

		// compute top
		let popupHeight = popupFrame.offsetHeight;
		let popupTop = 0;
		switch(yAlign) {
			case "TOP": popupTop = 0; break;
			case "CENTER": popupTop = popupHeight/2; break;
			case "BOTTOM": popupTop = popupHeight; break;
		}

		// move popup
		popupFrame.style.left = `calc(${x}% - ${popupLeft}px)`;
		popupFrame.style.top = `calc(${y}% - ${popupTop}px)`;
	}

	private movePopupByPixels(parent: HTMLElement, popupFrame: HTMLElement, x: number, y: number, xAlign: string, yAlign: string) {
		// get window size and scrolling position
		let scrollX = window.scrollX + parent.scrollLeft;
		let scrollY = window.scrollY + parent.scrollTop;
		let sizeX = window.innerWidth;

		// compute left
		let popupWidth = popupFrame.offsetWidth;
		let popupLeft = 0;
		switch(xAlign) {
			case "LEFT": popupLeft = scrollX+x; break;
			case "CENTER": popupLeft = scrollX+x - popupWidth/2; break;
			case "RIGHT": popupLeft = scrollX+x - popupWidth+1; break;
		}

		// keep left in sane range
		popupLeft = Math.min(popupLeft, scrollX+sizeX-popupWidth);
		popupLeft = Math.max(popupLeft, scrollX);

		// compute top
		let popupHeight = popupFrame.offsetHeight;
		let popupTop = 0;
		switch(yAlign) {
			case "TOP": popupTop = scrollY+y; break;
			case "CENTER": popupTop = scrollY+y - popupHeight/2; break;
			case "BOTTOM": popupTop = scrollY+y - popupHeight+1; break;
		}

		// compensate for position of parent
		if(parent != document.body) {
			let parentRect = parent.getBoundingClientRect();
			popupLeft -= parentRect.left;
			popupTop -= parentRect.top;
		}
		popupLeft = Math.max(popupLeft, 0);
		popupTop = Math.max(popupTop, 0);

		// move popup
		popupFrame.style.left = popupLeft + 'px';
		popupFrame.style.top = popupTop + 'px';
	}
}

const POPUP_ENGINE = new DomPopupEngine();
