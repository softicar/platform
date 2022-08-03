/**
 * Tracks the values of inputs and selects on the server and client side.
 */
class ValueNodeMap {
	private readonly states = new Map<HTMLElement, ValueNodeState>();

	/**
	 * Adds the given value node to this map.
	 */
	public addNode(node: HTMLElement) {
		this.states.set(node, new ValueNodeState(node));
	}

	/**
	 * Approves all pending values.
	 */
	public approveNodeValues() {
		this.states.forEach((state, _) => state.approveValue());
	}

	/**
	 * Transfers all changed values of value nodes to the specified data structure.
	 */
	public copyNodeValues(message: AjaxRequestMessage) {
		this.states.forEach((state, _) => state.updatePendingValue(message));
	}

	public isValueChanged(node: HTMLElement) {
		return this.getState(node).isValueChanged();
	}

	public setValue(node: HTMLInputElement, value: string) {
		node.value = value;
		this.getState(node).assumeValue(value);
	}

	public setSelectedOptions(select: HTMLSelectElement, options: HTMLOptionElement[]) {
		let type = select.type;
		if(type == "select-one") {
			options[0].selected = true;
		} else if(type == "select-multiple") {
			for(let option of select.options) {
				option.selected = options.includes(option);
			}
		} else {
			throw new Error(`Internal error: Unknown select type '${type}' on node ${select.id}.`);
		}

		this.getState(select).assumeValue(this.getState(select).getCurrentValue());
	}

	private getState(node: HTMLElement) {
		let state = this.states.get(node);
		if(state) {
			return state;
		} else {
			throw new Error(`Internal error: Missing value state for node ${node.id}.`);
		}
	}
}

class ValueNodeState {
	private readonly node: HTMLElement;
	private approvedValue: string;
	private pendingValue: string;

	public constructor(node: HTMLElement) {
		this.node = node;
		this.approvedValue = this.pendingValue = this.getCurrentValue();
	}
	
	public approveValue() {
		this.approvedValue = this.pendingValue;
	}
	
	public isValueChanged() {
		return this.getCurrentValue() != this.approvedValue;
	}
	
	public assumeValue(value: string) {
		this.approvedValue = this.pendingValue = value;
	}

	public updatePendingValue(message: AjaxRequestMessage) {
		this.pendingValue = this.getCurrentValue();
		if(this.pendingValue != this.approvedValue) {
			message.setNodeValue(this.node, this.pendingValue);
		}
	} 

	public getCurrentValue(): string {
		switch(this.node.tagName.toUpperCase()) {
		case 'INPUT':
			let input = this.node as HTMLInputElement;
			return '' + input.value;
		case 'TEXTAREA':
			let textArea = this.node as HTMLTextAreaElement;
			return '' + textArea.value;
		case 'SELECT':
			let value = [];
			let select = this.node as HTMLSelectElement;
			for(let option of select.selectedOptions) {
				value.push(option.id);
			}
			return value.join(',');
		}
		return '';
	}
}

const VALUE_NODE_MAP = new ValueNodeMap();
