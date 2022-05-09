
function makeDraggable(draggedNode: HTMLElement, initNode: HTMLElement, notifyOnDrop: boolean) {
	new DragContext(draggedNode, notifyOnDrop).setup(initNode);
}

class DragContext {
	private draggedNode: HTMLElement;
	private notifyOnDrop: boolean;
	private cursorStart = new Point();
	private dragStart = new Point();
	private dragPosition = new Point();
	private moveHandler = (event: Event) => this.onMove(event);
	private dropHandler = (event: Event) => this.onDrop(event);

	public constructor(draggedNode: HTMLElement, notifyOnDrop: boolean) {
		this.draggedNode = draggedNode;
		this.notifyOnDrop = notifyOnDrop;
	}

	public setup(initNode: HTMLElement) {
		initNode.addEventListener("mousedown", event => this.onDragStart(event));
		initNode.addEventListener("touchstart", event => this.onDragStart(event));
		initNode.style.userSelect = "none"; // disable text selection while dragging
		initNode.style.touchAction = "none"; // disable touch panning to avoid weird interaction with dragging
	}
	
	public onDragStart(event: Event) {
		this.addDragListener();
		this.setDocumentTextSelection(false);

		this.cursorStart = this.getCursorPosition(event);
		this.dragStart = this.getDraggedNodePosition();
		this.dragPosition = this.dragStart;

		_DOM_CONTEXT_.setMaximumZIndex(this.draggedNode);
	}

	public onMove(event: Event) {
		let cursor = this.getCursorPosition(event);
		if(cursor.x >= 0 && cursor.y >= 0) {
			this.dragPosition = this.dragStart.plus(cursor.minus(this.cursorStart))
			this.draggedNode.style.left = this.dragPosition.x + "px";
			this.draggedNode.style.top = this.dragPosition.y + "px";
		}
	}

	public onDrop(event: Event) {
		this.removeDragListener();
		this.setDocumentTextSelection(true);

		// let the server handle the drop
		if(this.notifyOnDrop && (this.dragPosition.x != this.dragStart.x || this.dragPosition.y != this.dragStart.y)) {
			if(AJAX_REQUEST_LOCK.lock()) {
				let parameters = {
					'a' : AJAX_REQUEST_DRAG_AND_DROP,
					'n' : this.draggedNode.id,
					'sx': this.dragStart.x,
					'sy': this.dragStart.y,
					'dx': this.dragPosition.x,
					'dy': this.dragPosition.y
				};

				GLOBAL.copyNodeValues(parameters);

				ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters));
				ACTION_QUEUE.executeNextAction();
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
	
	private getDraggedNodePosition() {
		let style = this.draggedNode.style;
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
