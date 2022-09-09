
class WheelEventManager {
	private readonly handlers = new Map<string, WheelEventHandler>();

	public setListenToWheel(node: HTMLElement, enabled: boolean) {
		this.getHandler(node).setListenToWheel(enabled);
	}

	public setPreventDefaultBehavior(node: HTMLElement, modifiers: Set<string>, enabled: boolean) {
		this.getHandler(node).setPreventDefault(modifiers, enabled);
	}

	private getHandler(node: HTMLElement) {
		let handler = this.handlers.get(node.id);
		if(!handler) {
			handler = new WheelEventHandler(node).install();
			this.handlers.set(node.id, handler);
		}
		return handler;
	}
}

const WHEEL_EVENT_MANAGER = new WheelEventManager();

class WheelEventHandler {
	private readonly node;
	private readonly preventDefault = new Map<string, boolean>();
	private listenToWheel = false;

	public constructor(node: HTMLElement) {
		this.node = node;
	}

	public install() {
		if(!this.node.onwheel) {
			this.node.onwheel = event => this.handleWheel(event);
		} else {
			console.log('Warning: Skipped installation of a wheel event listener.');
		}
		return this;
	}

	public setListenToWheel(enabled: boolean) {
		this.listenToWheel = enabled;
	}

	public setPreventDefault(modifiers: Set<string>, enabled: boolean) {
		this.preventDefault.set(JSON.stringify(Array.from(modifiers)), enabled);
	}

	private handleWheel(event: WheelEvent) {
		let modifiers = new Set<string>();
		if(event.altKey) modifiers.add(DOM_MODIFIER_ALT);
		if(event.ctrlKey) modifiers.add(DOM_MODIFIER_CONTROL);
		if(event.metaKey) modifiers.add(DOM_MODIFIER_META);
		if(event.shiftKey) modifiers.add(DOM_MODIFIER_SHIFT);

		if(this.listenToWheel) {
			handleDomEvent(event);
		}

		if(this.preventDefault.get(JSON.stringify(Array.from(modifiers)))) {
			event.stopPropagation();
			event.preventDefault();
		}
	}
}
