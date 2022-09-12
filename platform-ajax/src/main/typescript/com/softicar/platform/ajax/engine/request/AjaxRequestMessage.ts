/**
 * Contains the payload of an {@link AjaxRequest}.
 */
class AjaxRequestMessage {
	protected readonly instanceUuid: string = DOCUMENT_INSTANCE_UUID;
	protected requestIndex: number = -1;
	protected action: string | null = null;
	protected nodeId: string | null = null;
	protected nodeValues: any = {};
	protected eventType: string | null = null;
	protected key: string = "";
	protected modifierKeys: any = {};
	protected cursor: Point = new Point();
	protected cursorRelative: Point = new Point();
	protected dragStart: Point = new Point();
	protected dragPosition: Point = new Point();
	protected windowPageOffset: Point = new Point();
	protected windowInnerSize: Point = new Point();
	protected boundingClientRect: Rect = new Rect();
	protected deltaX: number = 0;
	protected deltaY: number = 0;
	protected deltaZ: number = 0;

	public copyNodeValues() {
		VALUE_NODE_MAP.copyNodeValues(this);
	}

	public setRequestIndex(requestIndex: number) {
		this.requestIndex = requestIndex;
		return this;
	}

	public setAction(action: string) {
		this.action = action;
		return this;
	}

	public setNode(node: HTMLElement) {
		this.nodeId = node.id;
		return this;
	}

	public setNodeValue(node: HTMLElement, value: string) {
		this.nodeValues[node.id] = value;
		return this;
	}

	public setEventType(eventType: string) {
		this.eventType = eventType.toUpperCase();
		return this;
	}

	// ------------------------------ keyboard ------------------------------ //

	public setKey(key: string) {
		this.key  = key;
		return this;
	}
	
	public setModifierKey(name: string, value: boolean) {
		this.modifierKeys[name] = value;
		return this;
	}

	// ------------------------------ mouse ------------------------------ //

	public setMousePosition(position: Point) {
		this.cursor = position;
		return this;
	}

	public setMouseRelativePosition(position: Point) {
		this.cursorRelative = position;
		return this;
	}

	// ------------------------------ drag'n'drop ------------------------------ //

	public setDragStart(start: Point) {
		this.dragStart = start;
		return this;
	}

	public setDragPosition(position: Point) {
		this.dragPosition = position;
		return this;
	}

	// ------------------------------ window ------------------------------ //

	public setWindowPageOffset(pageOffset: Point) {
		this.windowPageOffset = pageOffset;
		return this;
	}

	public setWindowInnerSize(innerSize: Point) {
		this.windowInnerSize = innerSize;
		return this;
	}

	// ------------------------------ bounding client rect ------------------------------ //

	public setBoundingClientRect(boundingClientRect: DOMRect) {
		this.boundingClientRect = Rect.fromDomRect(boundingClientRect);
		return this;
	}

	// ------------------------------ delta ------------------------------ //

	public setDeltaX(value: number) {
		this.deltaX = value;
		return this;
	}

	public setDeltaY(value: number) {
		this.deltaY = value;
		return this;
	}

	public setDeltaZ(value: number) {
		this.deltaZ = value;
		return this;
	}

	// ------------------------------ encoding ------------------------------ //

	public encode() {
		return new AjaxRequestMessageEncoder(this).encode();
	}

	public encodeToHex() {
		return new AjaxRequestMessageEncoder(this).encodeToHex();
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
		let deltaXSameSign = Math.sign(this.deltaX) == Math.sign(other.deltaX);
		let deltaYSameSign = Math.sign(this.deltaY) == Math.sign(other.deltaY);
		let deltaZSameSign = Math.sign(this.deltaZ) == Math.sign(other.deltaZ);
		return deltaXSameSign && deltaYSameSign && deltaZSameSign;
	}

	// ------------------------------ obsolete ------------------------------ //

	public isObsolete() {
		if(this.nodeId) {
			return document.getElementById(this.nodeId) === null;
		} else {
			return false;
		}
	}
	
	// ------------------------------ getter ------------------------------ //

	private isKeepAlive() {
		return this.action == AJAX_REQUEST_KEEP_ALIVE;
	}

	private isDomEvent() {
		return this.action == AJAX_REQUEST_DOM_EVENT;
	}

	private isSameAction(other: AjaxRequestMessage) {
		return this.action == other.action;
	}

	private isOnSameNode(other: AjaxRequestMessage) {
		return this.nodeId == other.nodeId;
	}

	private isSameEventType(other: AjaxRequestMessage) {
		return this.eventType == other.eventType;
	}
	
	private isPassiveEventType() {
		return this.eventType == 'CHANGE' || this.eventType == 'INPUT';
	}
	
	private isKeyEventType() {
		return this.eventType == 'KEYDOWN' || this.eventType == 'KEYUP';
	}
	
	private isWheelEventType() {
		return this.eventType == 'WHEEL';
	}

	private isSent() {
		return this.requestIndex >= 0;
	}
}
