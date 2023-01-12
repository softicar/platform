
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
		let xHandle = 0;
		switch(xAlign) {
			case "LEFT": xHandle = 0; break;
			case "CENTER": xHandle = popupWidth/2; break;
			case "RIGHT": xHandle = popupWidth; break;
		}

		// compute top
		let popupHeight = popupFrame.offsetHeight;
		let yHandle = 0;
		switch(yAlign) {
			case "TOP": yHandle = 0; break;
			case "CENTER": yHandle = popupHeight/2; break;
			case "BOTTOM": yHandle = popupHeight; break;
		}

		// move popup
		popupFrame.style.left = `calc(${x}% - ${xHandle}px)`;
		popupFrame.style.top = `calc(${y}% - ${yHandle}px)`;
	}

	private movePopupByPixels(parent: HTMLElement, popupFrame: HTMLElement, x: number, y: number, xAlign: string, yAlign: string) {
		// get window size and scrolling position
		let scrollX = window.scrollX + parent.scrollLeft;
		let scrollY = window.scrollY + parent.scrollTop;
		let sizeX = window.innerWidth;

		// compute left
		let popupWidth = popupFrame.offsetWidth;
		let xHandle = 0;
		switch(xAlign) {
			case "LEFT": xHandle = scrollX+x; break;
			case "CENTER": xHandle = scrollX+x - popupWidth/2; break;
			case "RIGHT": xHandle = scrollX+x - popupWidth+1; break;
		}

		// keep left in sane range
		xHandle = Math.min(xHandle, scrollX+sizeX-popupWidth);
		xHandle = Math.max(xHandle, scrollX);

		// compute top
		let popupHeight = popupFrame.offsetHeight;
		let yHandle = 0;
		switch(yAlign) {
			case "TOP": yHandle = scrollY+y; break;
			case "CENTER": yHandle = scrollY+y - popupHeight/2; break;
			case "BOTTOM": yHandle = scrollY+y - popupHeight+1; break;
		}

		// compensate for position of parent
		if(parent != document.body) {
			let parentRect = parent.getBoundingClientRect();
			xHandle -= parentRect.left;
			yHandle -= parentRect.top;
		}
		xHandle = Math.max(xHandle, 0);
		yHandle = Math.max(yHandle, 0);

		// move popup
		popupFrame.style.left = xHandle + 'px';
		popupFrame.style.top = yHandle + 'px';
	}
}

const POPUP_ENGINE = new DomPopupEngine();
