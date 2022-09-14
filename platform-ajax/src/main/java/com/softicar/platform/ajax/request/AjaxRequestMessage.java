package com.softicar.platform.ajax.request;

import com.google.gson.GsonBuilder;
import com.softicar.platform.ajax.document.action.AjaxDocumentActionType;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomModifier;
import com.softicar.platform.dom.event.DomRect;
import com.softicar.platform.dom.event.DomVector2d;
import com.softicar.platform.dom.event.DomVector3d;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

public class AjaxRequestMessage {

	private String instanceUuid;
	private int requestIndex;
	private AjaxDocumentActionType actionType;
	private DomEventType eventType;
	private String nodeId;
	private DomRect nodeRect;
	private Map<String, String> nodeValues;
	private String key;
	private Map<DomModifier, Boolean> modifierKeys;
	private DomVector2d cursor;
	private DomVector2d cursorRelative;
	private DomVector3d wheelDelta;
	private DomVector2d dragStart;
	private DomVector2d dragPosition;
	private DomVector2d windowPageOffset;
	private DomVector2d windowInnerSize;

	public AjaxRequestMessage() {

		initialize();
	}

	public AjaxRequestMessage validate() {

		if (instanceUuid == null || instanceUuid.isBlank()) {
			throw new RuntimeException("Missing document instance UUID.");
		}
		if (requestIndex < 0) {
			throw new RuntimeException("Missing AJAX request index.");
		}
		if (actionType == null) {
			throw new RuntimeException("Missing AJAX action type.");
		}
		if (actionType == AjaxDocumentActionType.DOM_EVENT && eventType == null) {
			throw new RuntimeException("Missing DOM event type.");
		}
		return this;
	}

	public static AjaxRequestMessage parseJson(String json) {

		return new GsonBuilder()//
			.create()
			.fromJson(json, AjaxRequestMessage.class)
			.validate();
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

	public AjaxDocumentActionType getActionType() {

		return actionType;
	}

	public DomEventType getEventType() {

		return eventType;
	}

	// ------------------------------ nodes ------------------------------ //

	public IDomNode getNode(IDomDocument document) {

		return nodeId != null? document.getNode(nodeId) : null;
	}

	public DomRect getNodeRect() {

		return nodeRect;
	}

	public Map<String, String> getNodeValues() {

		return nodeValues;
	}

	// ------------------------------ keyboard ------------------------------ //

	public String getKey() {

		return key;
	}

	public boolean isModifierKey(DomModifier modifier) {

		return Optional.ofNullable(modifierKeys.get(modifier)).orElse(false);
	}

	// ------------------------------ mouse ------------------------------ //

	public DomVector2d getCursor() {

		return cursor;
	}

	public DomVector2d getCursorRelative() {

		return cursorRelative;
	}

	public DomVector3d getWheelDelta() {

		return wheelDelta;
	}

	// ------------------------------ drag'n'drop ------------------------------ //

	public DomVector2d getDragStart() {

		return dragStart;
	}

	public DomVector2d getDragPosition() {

		return dragPosition;
	}

	// ------------------------------ window ------------------------------ //

	public DomVector2d getWindowPageOffset() {

		return windowPageOffset;
	}

	public DomVector2d getWindowInnerSize() {

		return windowInnerSize;
	}

	// ------------------------------ initialize ------------------------------ //

	private void initialize() {

		this.instanceUuid = null;
		this.requestIndex = -1;
		this.actionType = null;
		this.eventType = null;
		this.nodeId = null;
		this.nodeRect = new DomRect();
		this.nodeValues = new TreeMap<>();
		this.key = "";
		this.modifierKeys = new TreeMap<>();
		this.cursor = new DomVector2d();
		this.cursorRelative = new DomVector2d();
		this.dragStart = new DomVector2d();
		this.dragPosition = new DomVector2d();
		this.wheelDelta = new DomVector3d();
		this.windowPageOffset = new DomVector2d();
		this.windowInnerSize = new DomVector2d();
	}
}
