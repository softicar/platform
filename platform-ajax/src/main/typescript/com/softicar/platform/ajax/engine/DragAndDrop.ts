
function makeDraggable(draggedNode: HTMLElement, dragHandleNode: HTMLElement, notifyOnDrop: boolean) {
	var handler = DRAG_AND_DROP_MANAGER.getHandler(draggedNode);
	handler.setNotifyOnDrop(notifyOnDrop);
	handler.install(dragHandleNode);
}

function setDragLimitNode(draggedNode: HTMLElement, limitNode: HTMLElement) {
	DRAG_AND_DROP_MANAGER
		.getHandler(draggedNode)
		.setLimitNode(limitNode);
}

function setDragScrollNode(draggedNode: HTMLElement, scrollNode: HTMLElement) {
	DRAG_AND_DROP_MANAGER
		.getHandler(draggedNode)
		.setScrollNode(scrollNode);
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
	private limitNode: HTMLElement | null;
	private scrollNode: HTMLElement | null;
	private notifyOnDrop: boolean;
	private cursorStart = new Vector2d();
	private scrollStart = new Vector2d();
	private dragStart = new Vector2d();
	private dragPosition = new Vector2d();
	private dragHandler = (event: Event) => this.onDrag(event);
	private dropHandler = (_: Event) => this.onDrop();

	public constructor(draggedNode: HTMLElement) {
		this.draggedNode = draggedNode;
		this.limitNode = null;
		this.scrollNode = null;
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
	
	public setLimitNode(limitNode: HTMLElement | null) {
		this.limitNode = limitNode;
	}
	
	public setScrollNode(scrollNode: HTMLElement | null) {
		this.scrollNode = scrollNode;
	}
	
	public setNotifyOnDrop(notifyOnDrop: boolean) {
		this.notifyOnDrop = notifyOnDrop;
	}

	public onDragStart(event: Event) {
		this.addDragListener();
		this.setDocumentTextSelection(false);

		this.cursorStart = this.getCursorPosition(event);
		this.scrollStart = this.getScrollPosition();
		this.dragStart = this.getDraggedNodePosition();
		this.dragPosition = this.dragStart;

		this.dragHandleNode?.classList.add(DRAGGING_CSS_CLASS);

		AJAX_ENGINE.raise(this.draggedNode);
	}

	public onDrag(event: Event) {
		if(this.scrollNode) {
			let delta = this.getCursorPosition(event).minus(this.cursorStart);
			let position = this.clampScrollPosition(this.scrollStart.minus(delta));
			this.scrollNode.scrollTo(position.x, position.y);
		} else {
			let cursor = this.getLimitRect().clamp(this.getCursorPosition(event));
			this.dragPosition = this.dragStart.plus(cursor.minus(this.cursorStart));
			this.draggedNode.style.left = this.dragPosition.x + "px";
			this.draggedNode.style.top = this.dragPosition.y + "px";
		}
	}

	public onDrop() {
		this.removeDragListener();
		this.setDocumentTextSelection(true);

		this.dragHandleNode?.classList.remove(DRAGGING_CSS_CLASS);

		// let the server handle the drop
		if(this.notifyOnDrop && (this.dragPosition.x != this.dragStart.x || this.dragPosition.y != this.dragStart.y)) {
			let message = new AjaxRequestMessage()
				.setActionType(AJAX_REQUEST_DRAG_AND_DROP)
				.setNode(this.draggedNode)
				.setDragStart(this.dragStart)
				.setDragPosition(this.dragPosition);
			AJAX_REQUEST_QUEUE.submit(message);
		}
	}
	
	private clampScrollPosition(scrollPosition: Vector2d) {
		let scrollRect = this.scrollNode?.getBoundingClientRect() ?? new DOMRect(0,0,0,0);
		let nodeRect = this.draggedNode.getBoundingClientRect();
		return new Vector2d(
			clamp(scrollPosition.x, 0, Math.max(nodeRect.width - scrollRect.width, 0)),
			clamp(scrollPosition.y, 0, Math.max(nodeRect.height - scrollRect.height, 0)));
	}
	
	private getLimitRect() {
		if(this.limitNode) {
			return Rect.fromDomRect(this.limitNode.getBoundingClientRect());
		} else {
			return new Rect(0, 0, Infinity, Infinity);
		}
	}

	private getCursorPosition(event: Event) {
		if(event instanceof MouseEvent) {
			return new Vector2d(event.clientX, event.clientY);
		} else if (event instanceof TouchEvent) {
			let firstTouch = event.touches[0];
			return new Vector2d(Math.round(firstTouch.clientX), Math.round(firstTouch.clientY));
		} else {
			return new Vector2d();
		}
	}

	private getScrollPosition() {
		let x = this.scrollNode?.scrollLeft ?? 0;
		let y = this.scrollNode?.scrollTop ?? 0;
		return new Vector2d(x, y);
	}

	private getDraggedNodePosition() {
		let style = window.getComputedStyle(this.draggedNode);
		let x = style.left? parseInt(style.left) : 0;
		let y = style.top? parseInt(style.top) : 0;
		return new Vector2d(x, y);
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
