package com.softicar.platform.dom.event;

import com.softicar.platform.dom.node.IDomNode;

public interface IDomDropEvent {

	/**
	 * Returns the node that was dragged and dropped.
	 * 
	 * @return the dropped node, never null
	 */
	IDomNode getNode();

	/**
	 * The x position of the dragged node when the user started the drag and
	 * drop process.
	 * 
	 * @return x position at start of drag
	 */
	int getStartX();

	/**
	 * The y position of the dragged node when the user started the drag and
	 * drop process.
	 * 
	 * @return y position at start of drag
	 */
	int getStartY();

	/**
	 * The x position of the dragged node when the user dropped it.
	 * 
	 * @return x position of the dropped node
	 */
	int getDropX();

	/**
	 * The y position of the dragged node when the user dropped it.
	 * 
	 * @return y position of the dropped node
	 */
	int getDropY();
}
