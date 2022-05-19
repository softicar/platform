class AjaxEngine {
	private readonly nodes = new Map<number, Node>();
	private zIndex = 100;

	/**
	 * Raises the z-index of the given element to the maximum.
	 */
	public raise(element: HTMLElement) {
		if(element.style.zIndex != '' + this.zIndex) {
			this.zIndex += 1;
			element.style.zIndex = '' + this.zIndex;
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

	/**
	 * Pushes the given URL into the history of the browser.
	 */
	public pushBrowserHistoryState(page: string, url: string) {
	    history.pushState({page: page}, "", url);
	}

	private initializeNode(nodeId: number, node: HTMLElement) {
		node.id = 'n' + nodeId;
		this.nodes.set(nodeId, node);
	}
}

const AJAX_ENGINE = new AjaxEngine();
