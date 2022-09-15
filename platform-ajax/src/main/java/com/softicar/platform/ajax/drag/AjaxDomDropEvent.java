package com.softicar.platform.ajax.drag;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.request.AjaxRequestMessage;
import com.softicar.platform.dom.event.IDomDropEvent;
import com.softicar.platform.dom.node.IDomNode;

/**
 * Contains the parameters of a drop event.
 *
 * @author Oliver Richers
 */
public class AjaxDomDropEvent implements IDomDropEvent {

	private final AjaxRequestMessage message;
	private final IDomNode node;

	public AjaxDomDropEvent(IAjaxDocument document, AjaxRequestMessage message) {

		this.message = message;
		this.node = message.getNode(document);
	}

	@Override
	public IDomNode getNode() {

		return node;
	}

	@Override
	public double getStartX() {

		return message.getDragStart().getX();
	}

	@Override
	public double getStartY() {

		return message.getDragStart().getY();
	}

	@Override
	public double getDropX() {

		return message.getDragPosition().getX();
	}

	@Override
	public double getDropY() {

		return message.getDragPosition().getY();
	}
}
