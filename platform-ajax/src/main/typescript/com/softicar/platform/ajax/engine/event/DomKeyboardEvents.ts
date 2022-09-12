
class KeyboardEventManager {
	private readonly handlers = new Map<string, KeyboardEventHandler>();

	public setListenToKeyDown(node: HTMLElement, enabled: boolean) {
		this.getKeyHandler(node).setListenToKeyDown(enabled);
	}

	public setListenToKeyUp(node: HTMLElement, enabled: boolean) {
		this.getKeyHandler(node).setListenToKeyUp(enabled);
	}
	
	public setListenToKeys(node: HTMLElement, keys: string[]) {
		this.getKeyHandler(node).setListenToKeys(keys);
	}
	
	public setListenToKey(node: HTMLElement, eventName: string, enabled: boolean) {
		let key = this.getKey(eventName);
		this.getKeyHandler(node).setListenTo(key, eventName, enabled);
	}
	
	public setFireOnKeyUp(node: HTMLElement, eventName: string, enabled: boolean) {
		let key = this.getKey(eventName);
		this.getKeyHandler(node).setFireOnKeyUp(key, enabled);
	}
	
	public setPreventDefaultBehavior(node: HTMLElement, eventName: string, enabled: boolean) {
		let key = this.getKey(eventName);
		this.getKeyHandler(node).setPreventDefault(key, enabled);
	}
	
	public setCssClassOnKeyDown(node: HTMLElement, eventName: string, cssTargetNode: HTMLElement, cssClassNames: string) {
		let key = this.getKey(eventName);
		this.getKeyHandler(node).setCssClassApplier(key, new CssClassApplier(cssTargetNode, cssClassNames));
	}

	private getKeyHandler(node: HTMLElement) {
		let handler = this.handlers.get(node.id);
		if(!handler) {
			handler = new KeyboardEventHandler(node).install();
			this.handlers.set(node.id, handler);
		}
		return handler;
	}
	
	private getKey(eventName: string): string {
		switch(eventName) {
			case 'ENTER':  return "Enter";
			case 'ESCAPE': return "Escape";
			case 'SPACE':  return " ";
			case 'TAB':    return "Tab";
		}
		throw new Error("Internal error: Unsupported keyboard event name.");
	}
}

const KEYBOARD_EVENT_MANAGER = new KeyboardEventManager();

class KeyboardEventHandler {
	private readonly node;
	private readonly keyCodes = new Map<string, string>();
	private readonly fireOnKeyUp = new Map<string, boolean>();
	private readonly preventDefault = new Map<string, boolean>();
	private readonly cssClassApplier = new Map<string, CssClassApplier>();
	private listenToKeyDown = false;
	private listenToKeyUp = false;
	private listenToKeys = new Set<string>();
	private lastKeyDown = "0";

	public constructor(node: HTMLElement) {
		this.node = node;
	}
	
	public install() {
		if(!this.node.onkeydown && !this.node.onkeyup) {
			this.node.onkeydown = event => this.handleKeyDown(event);
			this.node.onkeyup = event => this.handleKeyUp(event);
		} else {
			console.log('Warning: Skipped installation of a keyboard event listener.');
		}
		return this;
	}
	
	public setListenToKeyDown(enabled: boolean) {
		this.listenToKeyDown = enabled;
	}

	public setListenToKeyUp(enabled: boolean) {
		this.listenToKeyUp = enabled;
	}

	public setListenToKeys(keys: string[]) {
		this.listenToKeys = new Set<string>(keys);
	}

	public setListenTo(key: string, eventName: string, enabled: boolean) {
		if(enabled) {
			this.keyCodes.set(key, eventName);
			this.preventDefault.set(key, true);
		} else {
			this.keyCodes.delete(key);
		}
	}

	public setFireOnKeyUp(key: string, enabled: boolean) {
		this.fireOnKeyUp.set(key, enabled);
	}

	public setPreventDefault(key: string, enabled: boolean) {
		this.preventDefault.set(key, enabled);
	}

	public setCssClassApplier(key: string, applier: CssClassApplier) {
		this.cssClassApplier.set(key, applier);
	}

	private handleKeyDown(event: KeyboardEvent) {
		let eventName = this.keyCodes.get(event.key);
		if(eventName) {
			if(!this.fireOnKeyUp.get(event.key) && !event.repeat) {
				sendOrDelegateEvent(this.node, event, eventName);
			}
			let applier = this.cssClassApplier.get(event.key);
			if(applier) {
				applier.addClasses();
			}
			this.stopFurtherHandling(event);
		}
		if(this.listenToKeyDown && this.listenToKeys.has(event.key)) {
			sendOrDelegateEvent(this.node, event, 'KEYDOWN');
		}
		this.lastKeyDown = event.key;
	}

	private handleKeyUp(event: KeyboardEvent) {
		var eventName = this.keyCodes.get(event.key);
		if(eventName) {
			if(this.fireOnKeyUp.get(event.key) && this.lastKeyDown == event.key) {
				sendOrDelegateEvent(this.node, event, eventName);
			}
			let applier = this.cssClassApplier.get(event.key);
			if(applier) {
				applier.removeClasses();
			}
			this.stopFurtherHandling(event);
		}
		if(this.listenToKeyUp && this.listenToKeys.has(event.key)) {
			sendOrDelegateEvent(this.node, event, 'KEYUP');
		}
		this.lastKeyDown = "0";
	}

	private stopFurtherHandling(event: KeyboardEvent) {
		event.stopPropagation();
		if(this.preventDefault.get(event.key)) {
			event.preventDefault();
		}
	}
}

class CssClassApplier {
	private readonly node: HTMLElement;
	private readonly classes: string[];

	public constructor(node: HTMLElement, classes: string) {
		this.node = node;
		this.classes = classes != ""? classes.split(" ") : [];
	}

	public addClasses() {
		this.classes.forEach(c => this.node.classList.add(c));
	}

	public removeClasses() {
		this.classes.forEach(c => this.node.classList.remove(c));
	}
}
