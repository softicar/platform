
function DomEventEngine() {

	this.setListenToChangeEvent = setListenToChangeEvent;

	function setListenToChangeEvent(node, listen) {

		if(listen) {
			node.onchange = handleChangeEvent;
		} else {
			node.onchange = undefined;
		}
	}

	function handleChangeEvent(event) {

		var node = event.currentTarget;

		// skip native change events for auto-complete inputs
		if(event.isTrusted && AUTO_COMPLETE_ENGINE.isEnabledForInput(node)) {
			return;
		}

		// only send or delegate event if value really changed
		if(GLOBAL.isValueChanged(node)) {
			sendOrDelegateEvent(node, event, event.type);
		}
	}
}

var DOM_EVENT_ENGINE = new DomEventEngine();
