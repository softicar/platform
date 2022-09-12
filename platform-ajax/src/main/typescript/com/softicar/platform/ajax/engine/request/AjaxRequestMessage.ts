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
		return this.setString('e', eventType.toUpperCase());
	}

	// ------------------------------ keyboard ------------------------------ //

	public setKey(key: string) {
		return this.setString("key", key);
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

	// ------------------------------ bounding client rect ------------------------------ //

	public setBoundingClientRect(rect: DOMRect) {
		this.setNumber("bcrX", rect.x);
		this.setNumber("bcrY", rect.y);
		this.setNumber("bcrW", rect.width);
		this.setNumber("bcrH", rect.height);
		return this;
	}

	// ------------------------------ delta ------------------------------ //

	public setDeltaX(value: number) {
		this.setNumber("deltaX", value);
	}

	public setDeltaY(value: number) {
		this.setNumber("deltaY", value);
	}

	public setDeltaZ(value: number) {
		this.setNumber("deltaZ", value);
	}

	// ------------------------------ encoding ------------------------------ //

	public encode() {
		return new AjaxRequestMessageEncoder(this.data).encode();
	}

	public encodeToHex() {
		return new AjaxRequestMessageEncoder(this.data).encodeToHex();
	}

	// ------------------------------ redundancy ------------------------------ //

	public isRedundantTo(other: AjaxRequestMessage) {
		if(this.isKeepAlive()) {
			return true; // keep-alive is redundant to any other message
		} else if(this.isSameAction(other) && this.isOnSameNode(other)) {
			if(this.isDomEvent()) {
				return this.isRedundantDomEvents(other);
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	private isRedundantDomEvents(other: AjaxRequestMessage) {
		if(this.isSameEventType(other)) {
			if(this.isPassiveEventType() && (this.isSent() || other.isSent())) {
				return false; // allow new passive events, i.e. implicitly triggered events
			} else if(this.isKeyEventType()) {
				return false; // never drop key events
			} else if(this.isWheelEventType()) {
				return this.isSameDeltaDirections(other);
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	private isSameDeltaDirections(other: AjaxRequestMessage) {
		let thisDeltaX = Number(this.data.get('deltaX') ?? 0);
		let otherDeltaX = Number(other.data.get('deltaX') ?? 0);
		let deltaXSameSign = Math.sign(thisDeltaX) == Math.sign(otherDeltaX);

		let thisDeltaY = Number(this.data.get('deltaY') ?? 0);
		let otherDeltaY = Number(other.data.get('deltaY') ?? 0);
		let deltaYSameSign = Math.sign(thisDeltaY) == Math.sign(otherDeltaY);

		let thisDeltaZ = Number(this.data.get('deltaZ') ?? 0);
		let otherDeltaZ = Number(other.data.get('deltaZ') ?? 0);
		let deltaZSameSign = Math.sign(thisDeltaZ) == Math.sign(otherDeltaZ);

		return deltaXSameSign && deltaYSameSign && deltaZSameSign;
	}

	// ------------------------------ obsolete ------------------------------ //

	public isObsolete() {
		let nodeId = this.data.get('n');
		if(nodeId) {
			return document.getElementById(nodeId) === null;
		} else {
			return false;
		}
	}
	
	// ------------------------------ getter ------------------------------ //

	private isKeepAlive() {
		return this.data.get('a') === '' + AJAX_REQUEST_KEEP_ALIVE;
	}

	private isDomEvent() {
		return this.data.get('a') === '' + AJAX_REQUEST_DOM_EVENT;
	}

	private isSameAction(other: AjaxRequestMessage) {
		return this.data.get('a') === other.data.get('a');
	}

	private isOnSameNode(other: AjaxRequestMessage) {
		return this.data.get('n') === other.data.get('n');
	}

	private isSameEventType(other: AjaxRequestMessage) {
		return this.data.get('e') === other.data.get('e');
	}
	
	private isPassiveEventType() {
		return this.data.get('e') == 'CHANGE' || this.data.get('e') == 'INPUT';
	}
	
	private isKeyEventType() {
		return this.data.get('e') == 'KEYDOWN' || this.data.get('e') == 'KEYUP';
	}
	
	private isWheelEventType() {
		return this.data.get('e') == 'WHEEL';
	}

	private isSent() {
		return this.data.has('x');
	}

	// ------------------------------ setter ------------------------------ //

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
