
function FocusTrap() {

	const focusableSelectors = 'button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])';

	this.trapTabFocus = function(node) {

		let focusableNodes = node.querySelectorAll(focusableSelectors);
		let focusableRoot = node.matches(focusableSelectors);
		if(focusableNodes.length == 0 && !focusableRoot) {
			console.log("Failed to trap focus: Neither the given node nor one of its children are focusable.");
			return;
		}

		let firstFocusableNode = focusableRoot ? node : focusableNodes[0];
		let lastFocusableNode = focusableNodes.length == 0 ? node : focusableNodes[focusableNodes.length - 1];

		node.addEventListener('keydown', function(event) {
			let tabPressed = event.key === 'Tab' || event.code === 'Tab';

			if (tabPressed) {
				if (event.shiftKey) {
					if (document.activeElement === firstFocusableNode) {
						lastFocusableNode.focus();
						event.preventDefault();
					}
				} else {
					if (document.activeElement === lastFocusableNode) {
						firstFocusableNode.focus();
						event.preventDefault();
					}
				}
			}
		});

		firstFocusableNode.focus();
	}
}

var FOCUS_TRAP = new FocusTrap();
