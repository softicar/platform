
function makeDraggable(draggedNodeID, initNodeID, notifyOnDrop)
{
	var draggedNode = _DOM_CONTEXT_.getNode(draggedNodeID);
	var initNode = _DOM_CONTEXT_.getNode(initNodeID);

	var dragContext = new DragContext(draggedNode, notifyOnDrop);
	initNode.addEventListener("mousedown", dragContext.onMoveStart);
	initNode.addEventListener("touchstart", dragContext.onMoveStart);

	initNode.style.userSelect = "none"; // disable text selection while dragging
	initNode.style.touchAction = "none"; // disable touch panning to avoid weird interaction with dragging
}

function DragContext(draggedNode, notifyOnDrop)
{
	var m_this = this;
	var m_draggedNode = draggedNode;
	var m_notifyOnDrop = notifyOnDrop;
	var m_dragStartX = null;
	var m_dragStartY = null;
	var m_clientStartX = null;
	var m_clientStartY = null;
	var m_currentX = null;
	var m_currentY = null;

	this.getCoordinates = function(event)
	{
		if(event instanceof MouseEvent) {
			return [event.clientX, event.clientY];
		} else if (event instanceof TouchEvent) {
			var firstTouch = event.touches[0];
			return [Math.round(firstTouch.clientX), Math.round(firstTouch.clientY)]
		}
	};
	
	this.onMoveStart = function(event)
	{
		// disable text selection
		document.onselectstart = function() {
			return false;
		};
		
		// update style of dragged node
		_DOM_CONTEXT_.setMaximumZIndex(m_draggedNode);
		var style = m_draggedNode.style;
		m_dragStartX = m_currentX = style.left? parseInt(style.left) : 0;
		m_dragStartY = m_currentY = style.top?  parseInt(style.top)  : 0;
		
		var coordinates = m_this.getCoordinates(event);
		m_clientStartX = coordinates[0];
		m_clientStartY = coordinates[1];
		
		// enable event listeners
		document.addEventListener("mousemove", m_this.onMove,    true);
		document.addEventListener("mouseup",   m_this.onMoveEnd, true);
		document.addEventListener("touchmove", m_this.onMove,    true);
		document.addEventListener("touchend",  m_this.onMoveEnd, true);
	};

	this.onMove = function(event)
	{
		var coordinates = m_this.getCoordinates(event);
		var clientX = coordinates[0];
		var clientY = coordinates[1];
		
		if(clientX >= 0)
		{
			m_currentX = m_dragStartX + (clientX - m_clientStartX);
			m_draggedNode.style.left = m_currentX + "px";
		}

		if(clientY >= 0)
		{
			m_currentY = m_dragStartY + (clientY - m_clientStartY);
			m_draggedNode.style.top = m_currentY + "px";
		}
	};
	
	this.onMoveEnd = function(event)
	{
		// remove event listeners
		document.removeEventListener("mousemove", m_this.onMove,    true);
		document.removeEventListener("mouseup",   m_this.onMoveEnd, true);
		document.removeEventListener("touchmove", m_this.onMove,    true);
		document.removeEventListener("touchend",  m_this.onMoveEnd, true);

		// re-enable text selection
		document.onselectstart = function() {
			return true;
		};

		// let the server handle the drop
		if(m_notifyOnDrop && (m_currentX != m_dragStartX || m_currentY != m_dragStartY))
			if(lock(LOCK_REASON_DRAG_AND_DROP))
			{
				var parameters =
				{
					'a' : AJAX_REQUEST_DRAG_AND_DROP,
					'n' : m_draggedNode.id,
					'sx': m_dragStartX,
					'sy': m_dragStartY,
					'dx': m_currentX,
					'dy': m_currentY
				};

				GLOBAL.copyNodeValues(parameters);

				ACTION_QUEUE.enqueueAction(new AjaxRequestAction(parameters));
				ACTION_QUEUE.executeNextAction();
			}
	};
}
