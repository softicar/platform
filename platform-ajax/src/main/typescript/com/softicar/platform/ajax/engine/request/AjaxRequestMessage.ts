/**
 * Contains the payload of an {@link AjaxRequest}.
 */
class AjaxRequestMessage {
	protected readonly instanceUuid = DOCUMENT_INSTANCE_UUID;
	protected requestIndex: number = -1;
	protected actionType: string | null = null;
	protected eventType: string | null = null;
	protected nodeId: string | null = null;
	protected nodeRect = new Rect();
	protected nodeValues: any = {};
	protected key = '';
	protected modifierKeys: any = {};
	protected cursor = new Vector2d();
	protected cursorRelative = new Vector2d();
	protected wheelDelta = new Vector3d();
	protected dragStart = new Vector2d();
	protected dragPosition = new Vector2d();
	protected windowPageOffset = new Vector2d();
	protected windowInnerSize = new Vector2d();
	protected windowSelection = '';

	public copyNodeValues() {
		VALUE_NODE_MAP.copyNodeValues(this);
	}

	public setRequestIndex(requestIndex: number) {
		this.requestIndex = requestIndex;
		return this;
	}

	public setActionType(actionType: string) {
		this.actionType = actionType;
		return this;
	}

	public setEventType(eventType: string) {
		this.eventType = eventType.toUpperCase();
		return this;
	}

	// ------------------------------ nodes ------------------------------ //

	public setNode(node: HTMLElement) {
		this.nodeId = node.id;
		return this;
	}

	public setNodeRect(boundingClientRect: DOMRect) {
		this.nodeRect = Rect.fromDomRect(boundingClientRect);
		return this;
	}

	public setNodeValue(node: HTMLElement, value: string) {
		this.nodeValues[node.id] = value;
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

	public setMousePosition(position: Vector2d) {
		this.cursor = position;
		return this;
	}

	public setMouseRelativePosition(position: Vector2d) {
		this.cursorRelative = position;
		return this;
	}

	public setWheelDelta(delta: Vector3d) {
		this.wheelDelta = delta;
		return this;
	}

	// ------------------------------ drag'n'drop ------------------------------ //

	public setDragStart(start: Vector2d) {
		this.dragStart = start;
		return this;
	}

	public setDragPosition(position: Vector2d) {
		this.dragPosition = position;
		return this;
	}

	// ------------------------------ window ------------------------------ //

	public setWindowPageOffset(pageOffset: Vector2d) {
		this.windowPageOffset = pageOffset;
		return this;
	}

	public setWindowInnerSize(innerSize: Vector2d) {
		this.windowInnerSize = innerSize;
		return this;
	}

	public setWindowSelection() {
		this.windowSelection = window?.getSelection()?.toString() ?? '';
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
		let deltaXSameSign = Math.sign(this.wheelDelta.x) == Math.sign(other.wheelDelta.x);
		let deltaYSameSign = Math.sign(this.wheelDelta.y) == Math.sign(other.wheelDelta.y);
		let deltaZSameSign = Math.sign(this.wheelDelta.z) == Math.sign(other.wheelDelta.z);
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
		return this.actionType == AJAX_REQUEST_KEEP_ALIVE;
	}

	private isDomEvent() {
		return this.actionType == AJAX_REQUEST_DOM_EVENT;
	}

	private isSameAction(other: AjaxRequestMessage) {
		return this.actionType == other.actionType;
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
