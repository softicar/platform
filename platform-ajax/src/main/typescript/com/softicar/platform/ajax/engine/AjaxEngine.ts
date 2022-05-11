class AjaxEngine {
	private readonly nodes = new Map<number, Node>();
	private zIndex = 100;

	public allocateZIndex() {
		this.zIndex += 1;
		return '' + this.zIndex;
	}

	public setMaximumZIndex(node: HTMLElement) {
		if(node.style.zIndex != '' + this.zIndex) {
			node.style.zIndex = this.allocateZIndex();
		}
	}

	public getNode(nodeId: number) {
		return this.nodes.get(nodeId) as Node;
	}

	public getElement(nodeId: number) {
		return this.nodes.get(nodeId) as HTMLElement;
	}

	/**
	 * Initializes the head object of this document.
	 */
	public initializeHead(nodeId: number) {
		this.initializeNode(nodeId, document.head);
	}

	/**
	 * Initializes the body object of this document.
	 */
	public initializeBody(nodeId: number) {
		this.initializeNode(nodeId, document.body);
	}

	/**
	 * Executes the given Javascript code.
	 */
	public executeScriptCode(scriptCode: string) {
		eval(scriptCode);
	}

	/**
	 * Creates a new HTML element with the given HTML tag.
	 */
	public createElement(nodeId: number, tag: string) {
		let node = document.createElement(tag);
		this.initializeNode(nodeId, node);

		// if this is a value node, approve its current value
		switch(tag.toUpperCase()) {
		case 'INPUT':
		case 'TEXTAREA':
		case 'SELECT':
			VALUE_NODE_MAP.addNode(node);
		}
	}

	/**
	 * Creates a new text element with the given text.
	 */
	public createTextNode(nodeId: number, text: string) {
		let node = document.createTextNode(text);
		this.nodes.set(nodeId, node);
	}

	/**
	 * Appends the given child node to the parent element.
	 */
	public appendChild(parentId: number, childId: number) {
		this.getElement(parentId).appendChild(this.getNode(childId));
	}

	/**
	 * Inserts the given child node to the parent element before the other child.
	 */
	public insertBefore(parentId: number, childId: number, otherChildId: number) {
		this.getElement(parentId).insertBefore(this.getNode(childId), this.getNode(otherChildId));
	}

	/**
	 * Removes the given child node from the parent element.
	 */
	public removeChild(parentId: number, childId: number) {
		this.getElement(parentId).removeChild(this.getNode(childId));
	}

	/**
	 * Inserts the given child node with the new child node.
	 */
	public replaceChild(parentId: number, newChildId: number, oldChildId: number) {
		this.getElement(parentId).replaceChild(this.getNode(newChildId), this.getNode(oldChildId));
	}

	public setAttribute(nodeId: number, attribute: string, value: string) {
		this.getElement(nodeId).setAttribute(attribute, value);
	}
	
	public listenToEvent(nodeID: number, event: string, doListen: boolean) {
		let element = this.getElement(nodeID);
		if(element == null)
			return;
	
		let handler = doListen? handleDomEvent : null;
	
		switch(event)
		{
		case 'CLICK':       element.onclick = handler; break;
		case 'CHANGE':      CHANGE_EVENT_MANAGER.setListenToChangeEvent(element, doListen); break;
		case 'CONTEXTMENU': element.oncontextmenu = doListen? (event => {handleDomEvent(event); event.preventDefault();}) : null; break;
		case 'DBLCLICK':    element.ondblclick = handler; break;
		case 'ENTER':       KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
		case 'ESCAPE':      KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
		case 'KEYPRESS':    element.onkeypress = handler; break;
		case 'SPACE':       KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
		case 'TAB':         KEYBOARD_EVENT_MANAGER.setListenToKey(element, event, doListen); break;
		default: alert('Unknown event ' + event + '.');
		}
	}

	private initializeNode(nodeId: number, node: HTMLElement) {
		node.id = 'n' + nodeId;
		this.nodes.set(nodeId, node);
	}
}

const _DOM_CONTEXT_ = new AjaxEngine();

// -------------------- abbreviations -------------------- //

const c = _DOM_CONTEXT_;

function n(nodeId: number) {
	return c.getNode(nodeId);
}

function e(nodeId: number, tag: string) {
	return c.createElement(nodeId, tag);
}

function t(nodeId: number, text: string) {
	return c.createTextNode(nodeId, text);
}

function l(nodeId: number, eventType: string) {
	c.listenToEvent(nodeId, eventType, true);
}

function u(nodeId: number, eventType: string) {
	c.listenToEvent(nodeId, eventType, false);
}

function a(parentId: number, childId: number) {
	c.appendChild(parentId, childId);
}

function i(parentId: number, childId: number, oherChildId: number) {
	c.insertBefore(parentId, childId, oherChildId);
}

function r(parentId: number, childId: number) {
	c.removeChild(parentId, childId);
}

function p(parentId: number, newChildId: number, oldChldId: number) {
	c.replaceChild(parentId, newChildId, oldChldId);
}

function E(parentId: number, childId: number, tag: string) {
	e(childId, tag);
	a(parentId, childId);
}

function T(parentId: number, childId: number, text: string) {
	t(childId, text);
	a(parentId, childId);
}

function s(elementId: number, attribute: string, value: string) {
	c.setAttribute(elementId, attribute, value);
}
