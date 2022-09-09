
function makeDraggable(draggedNode: HTMLElement, dragHandleNode: HTMLElement, limitingNode: HTMLElement | null, notifyOnDrop: boolean) {
	var handler = DRAG_AND_DROP_MANAGER.getHandler(draggedNode);
	handler.setLimitingNode(limitingNode);
	handler.setNotifyOnDrop(notifyOnDrop);
	handler.install(dragHandleNode);
}

class DragAndDropManager {
	private readonly handlers = new Map<string, DragAndDropHandler>();

	public getHandler(node: HTMLElement) {
		let handler = this.handlers.get(node.id);
		if(!handler) {
			handler = new DragAndDropHandler(node);
			this.handlers.set(node.id, handler);
		}
		return handler;
	}
}

const DRAG_AND_DROP_MANAGER = new DragAndDropManager();

class DragAndDropHandler {
	private draggedNode: HTMLElement;
	private dragHandleNode: HTMLElement | null;
	private limitingNode: HTMLElement | null;
	private notifyOnDrop: boolean;
	private cursorStart = new Point();
	private dragStart = new Point();
	private dragPosition = new Point();
	private dragHandler = (event: Event) => this.onDrag(event);
	private dropHandler = (event: Event) => this.onDrop(event);

	public constructor(draggedNode: HTMLElement) {
		this.draggedNode = draggedNode;
		this.limitingNode = null;
		this.notifyOnDrop = false;
		this.dragHandleNode = null;
	}

	public install(dragHandleNode: HTMLElement) {
		this.dragHandleNode = dragHandleNode;
		dragHandleNode.addEventListener("mousedown", event => this.onDragStart(event));
		dragHandleNode.addEventListener("touchstart", event => this.onDragStart(event));
		dragHandleNode.style.userSelect = "none"; // disable text selection while dragging
		dragHandleNode.style.touchAction = "none"; // disable touch panning to avoid weird interaction with dragging
		dragHandleNode.ondragstart = function() { return false; }; // disable default dragging implementation
		dragHandleNode.classList.add(DRAGGABLE_CSS_CLASS);
	}
	
	public setLimitingNode(limitingNode: HTMLElement | null) {
		this.limitingNode = limitingNode;
	}
	
	public setNotifyOnDrop(notifyOnDrop: boolean) {
		this.notifyOnDrop = notifyOnDrop;
	}

	public onDragStart(event: Event) {
		this.addDragListener();
		this.setDocumentTextSelection(false);

		this.cursorStart = this.getCursorPosition(event);
		this.dragStart = this.getDraggedNodePosition();
		this.dragPosition = this.dragStart;

		this.dragHandleNode?.classList.add(DRAGGING_CSS_CLASS);

		AJAX_ENGINE.raise(this.draggedNode);
	}

	public onDrag(event: Event) {
		let cursor = this.getCursorPosition(event);
		let rect = this.limitingNode?.getBoundingClientRect();
		let minX = rect?.left ?? 0;
		let maxX = rect?.right ?? Infinity;
		let minY = rect?.top ?? 0;
		let maxY = rect?.bottom ?? Infinity;
		if(minX <= cursor.x && cursor.x <= maxX && minY <= cursor.y && cursor.y <= maxY) {
			this.dragPosition = this.dragStart.plus(cursor.minus(this.cursorStart))
			this.draggedNode.style.left = this.dragPosition.x + "px";
			this.draggedNode.style.top = this.dragPosition.y + "px";
		}
	}

	public onDrop(event: Event) {
		this.removeDragListener();
		this.setDocumentTextSelection(true);

		this.dragHandleNode?.classList.remove(DRAGGING_CSS_CLASS);

		// let the server handle the drop
		if(this.notifyOnDrop && (this.dragPosition.x != this.dragStart.x || this.dragPosition.y != this.dragStart.y)) {
			let message = new AjaxRequestMessage()
				.setAction(AJAX_REQUEST_DRAG_AND_DROP)
				.setNode(this.draggedNode)
				.setDragStart(this.dragStart)
				.setDragPosition(this.dragPosition);
			AJAX_REQUEST_QUEUE.submit(message);
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
		let style = window.getComputedStyle(this.draggedNode);
		let x = style.left? parseInt(style.left) : 0;
		let y = style.top? parseInt(style.top) : 0;
		return new Point(x, y);
	}
	
	private setDocumentTextSelection(enabled: boolean) {
		document.onselectstart = function() { return enabled; };
	}

	private addDragListener() {
		document.addEventListener("mousemove", this.dragHandler, true);
		document.addEventListener("touchmove", this.dragHandler, true);
		document.addEventListener("mouseup", this.dropHandler, true);
		document.addEventListener("touchend", this.dropHandler, true);
	}
	
	private removeDragListener() {
		document.removeEventListener("mousemove", this.dragHandler, true);
		document.removeEventListener("touchmove", this.dragHandler, true);
		document.removeEventListener("mouseup", this.dropHandler, true);
		document.removeEventListener("touchend", this.dropHandler, true);
	}
}
