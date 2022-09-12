package com.softicar.platform.ajax.request;

import com.google.gson.GsonBuilder;
import com.softicar.platform.ajax.document.action.AjaxDocumentActionType;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomModifier;
import com.softicar.platform.dom.event.DomVector2d;
import com.softicar.platform.dom.event.DomRect;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AjaxRequestMessage {

	private String instanceUuid;
	private int requestIndex;
	private AjaxDocumentActionType action;
	private String nodeId;
	private Map<String, String> nodeValues;
	private DomEventType eventType;
	private String key;
	private Integer keyCode;
	private Map<DomModifier, Boolean> modifierKeys;
	private DomVector2d cursor;
	private DomVector2d cursorRelative;
	private DomVector2d dragStart;
	private DomVector2d dragPosition;
	private DomVector2d windowPageOffset;
	private DomVector2d windowInnerSize;
	private DomRect boundingClientRect;
	private double deltaX;
	private double deltaY;
	private double deltaZ;

	public static AjaxRequestMessage parseJson(String json) {

		return new GsonBuilder()//
			.create()
			.fromJson(json, AjaxRequestMessage.class);
	}

	@Override
	public String toString() {

		return new GsonBuilder()//
			.setPrettyPrinting()
			.create()
			.toJson(this);
	}

	public UUID getInstanceUuid() {

		return UUID.fromString(instanceUuid);
	}

	public int getRequestIndex() {

		return requestIndex;
	}

	public AjaxDocumentActionType getAction() {

		return action;
	}

	public IDomNode getNode(IDomDocument document) {

		return nodeId != null? document.getNode(nodeId) : null;
	}

	public Map<String, String> getNodeValues() {

		return nodeValues;
	}

	public DomEventType getEventType() {

		return eventType;
	}

	public String getKey() {

		return key;
	}

	public Integer getKeyCode() {

		return keyCode;
	}

	public boolean isModifierKey(DomModifier modifier) {

		return Optional.ofNullable(modifierKeys.get(modifier)).orElse(false);
	}

	public DomVector2d getCursor() {

		return cursor;
	}

	public DomVector2d getCursorRelative() {

		return cursorRelative;
	}

	public DomVector2d getDragStart() {

		return dragStart;
	}

	public DomVector2d getDragPosition() {

		return dragPosition;
	}

	public DomVector2d getWindowPageOffset() {

		return windowPageOffset;
	}

	public DomVector2d getWindowInnerSize() {

		return windowInnerSize;
	}

	public DomRect getBoundingClientRect() {

		return boundingClientRect;
	}

	public double getDeltaX() {

		return deltaX;
	}

	public double getDeltaY() {

		return deltaY;
	}

	public double getDeltaZ() {

		return deltaZ;
	}
}
