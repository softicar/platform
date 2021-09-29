package com.softicar.platform.ajax.drag;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.request.AjaxParameterUtils;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.dom.event.IDomDropEvent;
import com.softicar.platform.dom.node.IDomNode;

/**
 * Contains the parameters of a drop event.
 *
 * @author Oliver Richers
 */
public class AjaxDomDropEvent implements IDomDropEvent {

	private final IDomNode node;
	private final int startX;
	private final int startY;
	private final int dropX;
	private final int dropY;

	public AjaxDomDropEvent(IAjaxDocument document, IAjaxRequest ajaxRequest) {

		this.node = document.getNode(ajaxRequest.getParameter("n"));
		this.startX = AjaxParameterUtils.getInteger(ajaxRequest, "sx");
		this.startY = AjaxParameterUtils.getInteger(ajaxRequest, "sy");
		this.dropX = AjaxParameterUtils.getInteger(ajaxRequest, "dx");
		this.dropY = AjaxParameterUtils.getInteger(ajaxRequest, "dy");
	}

	@Override
	public IDomNode getNode() {

		return node;
	}

	@Override
	public int getStartX() {

		return startX;
	}

	@Override
	public int getStartY() {

		return startY;
	}

	@Override
	public int getDropX() {

		return dropX;
	}

	@Override
	public int getDropY() {

		return dropY;
	}
}
