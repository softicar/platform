
class FocusTrap {
	private static readonly FOCUSABLE_SELECTORS = 'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])';
	private root: HTMLElement;
	private firstFocusableNode: Element;
	private lastFocusableNode: Element;

	public constructor(root: HTMLElement) {
		this.root = root;
		this.firstFocusableNode = root;
		this.lastFocusableNode = root;
	}

	public install() {
		let focusableNodes = this.root.querySelectorAll(FocusTrap.FOCUSABLE_SELECTORS);
		let focusableRoot = this.root.matches(FocusTrap.FOCUSABLE_SELECTORS);
		if(focusableNodes.length == 0 && !focusableRoot) {
			console.log("Failed to trap focus: Neither the given node nor one of its children are focusable.");
			return;
		}
		this.firstFocusableNode = focusableRoot ? this.root : focusableNodes[0];
		this.lastFocusableNode = focusableNodes.length == 0 ? this.root : focusableNodes[focusableNodes.length - 1];

		this.focusElement(this.firstFocusableNode);
		this.root.addEventListener('keydown', event => this.handleKeyDown(event));
	}

	private handleKeyDown(event: KeyboardEvent) {
		let tabPressed = event.key === 'Tab' || event.code === 'Tab';

		if (tabPressed) {
			if (event.shiftKey) {
				if (document.activeElement === this.firstFocusableNode) {
					this.focusElement(this.lastFocusableNode);
					event.preventDefault();
				}
			} else {
				if (document.activeElement === this.lastFocusableNode) {
					this.focusElement(this.firstFocusableNode);
					event.preventDefault();
				}
			}
		}
	}
	
	private focusElement(element: Element) {
		(element as any).focus();
	}
}

function trapTabFocus(root: HTMLElement) {
	new FocusTrap(root).install();
}
