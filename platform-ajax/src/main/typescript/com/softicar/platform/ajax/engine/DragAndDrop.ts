
function makeDraggable(movedNode: HTMLElement, draggedNode: HTMLElement, limitingNode: HTMLElement | null, notifyOnDrop: boolean) {
	new DragContext(movedNode, limitingNode, notifyOnDrop).setup(draggedNode);
}

class DragContext {
	private movedNode: HTMLElement;
	private limitingNode: HTMLElement | null;
	private notifyOnDrop: boolean;
	private cursorStart = new Point();
	private dragStart = new Point();
	private dragPosition = new Point();
	private moveHandler = (event: Event) => this.onMove(event);
	private dropHandler = (event: Event) => this.onDrop(event);

	public constructor(movedNode: HTMLElement, limitingNode: HTMLElement | null, notifyOnDrop: boolean) {
		this.movedNode = movedNode;
		this.limitingNode = limitingNode;
		this.notifyOnDrop = notifyOnDrop;
	}

	public setup(draggedNode: HTMLElement) {
		draggedNode.addEventListener("mousedown", event => this.onDragStart(event));
		draggedNode.addEventListener("touchstart", event => this.onDragStart(event));
		draggedNode.style.userSelect = "none"; // disable text selection while dragging
		draggedNode.style.touchAction = "none"; // disable touch panning to avoid weird interaction with dragging
	}
	
	public onDragStart(event: Event) {
		this.addDragListener();
		this.setDocumentTextSelection(false);

		this.cursorStart = this.getCursorPosition(event);
		this.dragStart = this.getMovedNodePosition();
		this.dragPosition = this.dragStart;

		_DOM_CONTEXT_.setMaximumZIndex(this.movedNode);
	}

	public onMove(event: Event) {
		let cursor = this.getCursorPosition(event);
		let rect = this.limitingNode?.getBoundingClientRect();
		let minX = rect?.left ?? 0;
		let minY = rect?.top ?? 0;
		if(cursor.x >= minX && cursor.y >= minY) {
			this.dragPosition = this.dragStart.plus(cursor.minus(this.cursorStart))
			this.movedNode.style.left = this.dragPosition.x + "px";
			this.movedNode.style.top = this.dragPosition.y + "px";
		}
	}

	public onDrop(event: Event) {
		this.removeDragListener();
		this.setDocumentTextSelection(true);

		// let the server handle the drop
		if(this.notifyOnDrop && (this.dragPosition.x != this.dragStart.x || this.dragPosition.y != this.dragStart.y)) {
			if(AJAX_REQUEST_LOCK.lock()) {
				let message = new AjaxRequestMessage()
					.setAction(AJAX_REQUEST_DRAG_AND_DROP)
					.setNode(this.movedNode)
					.setDragStart(this.dragStart)
					.setDragPosition(this.dragPosition);
				new AjaxRequest(message).send();
			}
		}
	}

	private getCursorPosition(event: Event) {
		if(event instanceof MouseEvent) {
			return new Point(event.clientX, event.clientY);
		} else if (event instanceof TouchEvent) {
			let firstTouch = event.touches[0];
			return new Point(Math.round(firstTouch.clientX), Math.round(firstTouch.clientY));
		} else {
			return new Point();
		}
	}
	
	private getMovedNodePosition() {
		let style = this.movedNode.style;
		let x = style.left? parseInt(style.left) : 0;
		let y = style.top? parseInt(style.top) : 0;
		return new Point(x, y);
	}
	
	private setDocumentTextSelection(enabled: boolean) {
		document.onselectstart = function() { return enabled; };
	}

	private addDragListener() {
		document.addEventListener("mousemove", this.moveHandler, true);
		document.addEventListener("touchmove", this.moveHandler, true);
		document.addEventListener("mouseup", this.dropHandler, true);
		document.addEventListener("touchend", this.dropHandler, true);
	}
	
	private removeDragListener() {
		document.removeEventListener("mousemove", this.moveHandler, true);
		document.removeEventListener("touchmove", this.moveHandler, true);
		document.removeEventListener("mouseup", this.dropHandler, true);
		document.removeEventListener("touchend", this.dropHandler, true);
	}
}
