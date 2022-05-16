/**
 * Contains the payload of an {@link AjaxRequest}.
 */
class AjaxRequestMessage {
	private readonly data = new Map<string, string>();

	public constructor() {
		this.setString('i', DOCUMENT_INSTANCE_UUID);
		this.setBooleanIfTrue('debug', DEBUG);
		this.setBooleanIfTrue('verbose', VERBOSE);
	}
	
	public copyNodeValues() {
		VALUE_NODE_MAP.copyNodeValues(this);
	}

	public setRequestIndex(requestIndex: number) {
		return this.setNumber('x', requestIndex);
	}

	public setAction(action: number) {
		return this.setNumber('a', action);
	}

	public setNode(node: HTMLElement) {
		return this.setString('n', node.id);
	}

	public setNodeValue(node: HTMLElement, value: string) {
		return this.setString('V' + node.id.substring(1), value);
	}

	public setEventType(eventType: string) {
		return this.setString('e', eventType);
	}

	// ------------------------------ keyboard ------------------------------ //

	public setKeyCode(keyCode: number) {
		return this.setNumber('k', keyCode);
	}
	
	public setModifierKey(name: string, value: boolean) {
		return this.setBooleanIfTrue(name, value);
	}

	// ------------------------------ mouse ------------------------------ //

	public setMousePosition(position: Point) {
		return this.setPoint('c', position);
	}

	public setMouseRelativePosition(position: Point) {
		return this.setPoint('r', position);
	}

	// ------------------------------ drag'n'drop ------------------------------ //

	public setDragStart(start: Point) {
		return this.setPoint('s', start);
	}

	public setDragPosition(position: Point) {
		return this.setPoint('d', position);
	}

	// ------------------------------ window ------------------------------ //

	public setWindowPageOffset(pageOffset: Point) {
		return this.setPoint('s', pageOffset);
	}

	public setWindowInnerSize(innerSize: Point) {
		return this.setPoint('w', innerSize);
	}

	// ------------------------------ auto-complete ------------------------------ //

	public setAutoCompletePattern(pattern: string) {
		return this.setString('p', pattern);
	}

	// ------------------------------ encoding ------------------------------ //

	public encode() {
		return new AjaxRequestMessageEncoder(this.data).encode();
	}

	public encodeToHex() {
		return new AjaxRequestMessageEncoder(this.data).encodeToHex();
	}

	// ------------------------------ private ------------------------------ //

	private setString(key: string, value: string) {
		this.data.set(key, value);
		return this;
	}

	private setNumber(key: string, value: number) {
		return this.setString(key, '' + value);
	}

	private setBooleanIfTrue(key: string, value: boolean) {
		if(value) {
			this.setString(key, '1');
		}
		return this;
	}

	private setPoint(key: string, value: Point) {
		this.setNumber(key + 'x', value.x);
		this.setNumber(key + 'y', value.y);
		return this;
	}
}
