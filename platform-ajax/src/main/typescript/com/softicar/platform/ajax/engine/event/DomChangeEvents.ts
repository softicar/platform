/**
 * Manages the listening and handling of change events.
 */
class ChangeEventManager {

	public setListenToChangeEvent(node: HTMLElement, listen: boolean) {
		if(listen) {
			node.onchange = event => this.handleChangeEvent(event);
		} else {
			node.onchange = null;
		}
	}

	private handleChangeEvent(event: Event) {
		let node = event.currentTarget as HTMLElement;

		// only send or delegate event if value really changed
		if(VALUE_NODE_MAP.isValueChanged(node)) {
			sendOrDelegateEvent(node, event, event.type);
		}
	}
}

const CHANGE_EVENT_MANAGER = new ChangeEventManager();
