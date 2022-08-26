package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.request.AjaxParameterUtils;
import com.softicar.platform.ajax.request.AjaxRequest;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomRect;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Optional;

/**
 * The AJAX implementation of {@link IDomEvent}.
 *
 * @author Oliver Richers
 */
public class AjaxDomEvent implements IDomEvent {

	private final DomEventType type;
	private final IDomNode currentTarget;
	private final Integer clientX;
	private final Integer clientY;
	private final Double relativeX;
	private final Double relativeY;
	private final Double scrollX;
	private final Double scrollY;
	private final String key;
	private final Integer keyCode;
	private final Integer windowWidth;
	private final Integer windowHeight;
	private final DomRect boundingClientRect;
	private final boolean altKey;
	private final boolean ctrlKey;
	private final boolean metaKey;
	private final boolean shiftKey;
	private final double deltaX;
	private final double deltaY;
	private final double deltaZ;
	private final IAjaxRequest ajaxRequest;

	/**
	 * Constructs a new event object using data supplied by the servlet request.
	 *
	 * @param document
	 *            the associated {@link IDomDocument} that this event was
	 *            triggered on
	 * @param ajaxRequest
	 *            the associated {@link AjaxRequest}, containing information
	 *            about this event
	 */
	public AjaxDomEvent(IDomDocument document, IAjaxRequest ajaxRequest) {

		this.type = DomEventType.valueOf(ajaxRequest.getParameter("e").toUpperCase());
		this.currentTarget = document.getNode(ajaxRequest.getParameter("n"));
		this.clientX = AjaxParameterUtils.getDoubleOrDefault(ajaxRequest, "cx", 0).intValue();
		this.clientY = AjaxParameterUtils.getDoubleOrDefault(ajaxRequest, "cy", 0).intValue();
		this.relativeX = AjaxParameterUtils.getDouble(ajaxRequest, "rx");
		this.relativeY = AjaxParameterUtils.getDouble(ajaxRequest, "ry");
		this.scrollX = AjaxParameterUtils.getDouble(ajaxRequest, "sx");
		this.scrollY = AjaxParameterUtils.getDouble(ajaxRequest, "sy");
		this.key = Optional.ofNullable(ajaxRequest.getParameter("key")).orElse("");
		this.keyCode = AjaxParameterUtils.getInteger(ajaxRequest, "k");
		this.windowWidth = AjaxParameterUtils.getInteger(ajaxRequest, "wx");
		this.windowHeight = AjaxParameterUtils.getInteger(ajaxRequest, "wy");
		this.boundingClientRect = readBoundingClientRect(ajaxRequest);
		this.altKey = AjaxParameterUtils.getIntOrDefault(ajaxRequest, "altKey", 0) != 0;
		this.ctrlKey = AjaxParameterUtils.getIntOrDefault(ajaxRequest, "ctrlKey", 0) != 0;
		this.metaKey = AjaxParameterUtils.getIntOrDefault(ajaxRequest, "metaKey", 0) != 0;
		this.shiftKey = AjaxParameterUtils.getIntOrDefault(ajaxRequest, "shiftKey", 0) != 0;
		this.deltaX = AjaxParameterUtils.getDoubleOrDefault(ajaxRequest, "deltaX", 0);
		this.deltaY = AjaxParameterUtils.getDoubleOrDefault(ajaxRequest, "deltaY", 0);
		this.deltaZ = AjaxParameterUtils.getDoubleOrDefault(ajaxRequest, "deltaZ", 0);
		this.ajaxRequest = ajaxRequest;
	}

	private DomRect readBoundingClientRect(IAjaxRequest ajaxRequest) {

		return new DomRect(//
			AjaxParameterUtils.getDouble(ajaxRequest, "bcrX"),
			AjaxParameterUtils.getDouble(ajaxRequest, "bcrY"),
			AjaxParameterUtils.getDouble(ajaxRequest, "bcrW"),
			AjaxParameterUtils.getDouble(ajaxRequest, "bcrH"));
	}

	/**
	 * Returns the associated {@link AjaxRequest} object.
	 *
	 * @return associated {@link AjaxRequest}, never null
	 */
	public IAjaxRequest getAjaxRequest() {

		return ajaxRequest;
	}

	@Override
	public DomEventType getType() {

		return type;
	}

	@Override
	public IDomNode getCurrentTarget() {

		return currentTarget;
	}

	@Override
	public int getClientX() {

		return clientX;
	}

	@Override
	public int getClientY() {

		return clientY;
	}

	@Override
	public Double getRelativeX() {

		return relativeX;
	}

	@Override
	public Double getRelativeY() {

		return relativeY;
	}

	@Override
	public Double getScrollX() {

		return scrollX;
	}

	@Override
	public Double getScrollY() {

		return scrollY;
	}

	@Override
	public String getKey() {

		return key;
	}

	@Override
	public Integer getKeyCode() {

		return keyCode;
	}

	@Override
	public int getWindowWidth() {

		return windowWidth;
	}

	@Override
	public int getWindowHeight() {

		return windowHeight;
	}

	@Override
	public DomRect getBoundingClientRect() {

		return boundingClientRect;
	}

	@Override
	public double getDeltaX() {

		return deltaX;
	}

	@Override
	public double getDeltaY() {

		return deltaY;
	}

	@Override
	public double getDeltaZ() {

		return deltaZ;
	}

	@Override
	public boolean isAltKey() {

		return altKey;
	}

	@Override
	public boolean isCtrlKey() {

		return ctrlKey;
	}

	@Override
	public boolean isMetaKey() {

		return metaKey;
	}

	@Override
	public boolean isShiftKey() {

		return shiftKey;
	}

	@Override
	public String toString() {

		return String
			.format(
				"type(%s) currentTarget(%s) client(%s,%s) scroll(%s,%s) relative(%s,%s) window(%s,%s) key(%s) keyCode(%s)",
				type.toString(),
				currentTarget.getNodeId(),
				clientX,
				clientY,
				scrollX,
				scrollY,
				relativeX,
				relativeY,
				windowWidth,
				windowHeight,
				key,
				keyCode);
	}
}
