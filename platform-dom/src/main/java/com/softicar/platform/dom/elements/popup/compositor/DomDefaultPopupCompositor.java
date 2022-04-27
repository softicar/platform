package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.container.map.weak.identity.WeakIdentityHashMap;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.elements.dialog.DomModalDialogBackdrop;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.configuration.DomPopupModalMode;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import java.util.Objects;
import java.util.Optional;

/**
 * The default implementation of an {@link IDomPopupCompositor}.
 *
 * @author Alexander Schmidt
 */
public class DomDefaultPopupCompositor implements IDomPopupCompositor {

	private final WeakIdentityHashMap<DomPopup, DomPopupFrame> frameMap;
	private final WeakIdentityHashMap<DomPopup, IDomNode> spawningNodeMap;
	private final WeakIdentityHashMap<DomPopup, IDomNode> backdropMap;

	public DomDefaultPopupCompositor() {

		this.frameMap = new WeakIdentityHashMap<>();
		this.spawningNodeMap = new WeakIdentityHashMap<>();
		this.backdropMap = new WeakIdentityHashMap<>();
	}

	@Override
	public void show(DomPopup popup) {

		Objects.requireNonNull(popup);
		if (popup.isAppended()) {
			return;
		}

		IDomNode spawningNode = determineSpawningNode();
		spawningNodeMap.put(popup, spawningNode);

		var configuration = popup.getConfiguration();
		var frame = new DomPopupFrame(popup);
		frame.setCaption(configuration.getCaption());
		frame.setSubCaption(configuration.getSubCaption());
		configuration.getFrameMarker().ifPresent(frame::setMarker);
		frameMap.put(popup, frame);

		if (frame.getParent() == null) {
			getDomDocument().getBody().appendChild(frame);
		}

		var displayMode = configuration.getDisplayMode();
		frame.initialize(displayMode.hasHeader());

		if (displayMode.isModal()) {
			showBackdrop(popup);
		}

		configuration.getCallbackBeforeShow().apply();

		var position = configuration.getPositionStrategy().getPosition(getCurrentEvent());
		getDomEngine().showPopup(frame, position.getX(), position.getY(), position.getXAlign(), position.getYAlign());

		if (displayMode.isModal()) {
			getDomEngine().trapTabFocus(frame);
		}

		focus(popup);
	}

	@Override
	public void close(DomPopup popup) {

		Objects.requireNonNull(popup);
		if (!popup.isAppended()) {
			return;
		}

		if (popup.getConfiguration().isConfirmBeforeClose()) {
			popup.executeConfirm(() -> hidePopup(popup), DomI18n.ARE_YOU_SURE_TO_CLOSE_THIS_WINDOW_QUESTION);
		} else {
			hidePopup(popup);
		}
	}

	@Override
	public void closeWithoutConfirm(DomPopup popup) {

		Objects.requireNonNull(popup);
		if (!popup.isAppended()) {
			return;
		}

		hidePopup(popup);
	}

	@Override
	public void focus(DomPopup popup) {

		Objects.requireNonNull(popup);
		if (!popup.isAppended()) {
			return;
		}

		if (!IDomTextualInput.focusFirstTextualInput(popup)) {
			Optional.ofNullable(frameMap.get(popup)).ifPresent(getDomEngine()::focus);
		}
	}

	@Override
	public void refreshFrame(DomPopup popup) {

		Objects.requireNonNull(popup);

		Optional.ofNullable(frameMap.get(popup)).ifPresent(frame -> {
			var configuration = popup.getConfiguration();
			frame.setCaption(configuration.getCaption());
			frame.setSubCaption(configuration.getSubCaption());
		});
	}

	private void showBackdrop(DomPopup popup) {

		var backdrop = new DomModalDialogBackdrop(determineBackdropCallback(popup));
		backdropMap.put(popup, backdrop);

		getDomEngine().setMaximumZIndex(backdrop);
		getDomDocument().getBody().appendChild(backdrop);
	}

	private INullaryVoidFunction determineBackdropCallback(DomPopup popup) {

		DomPopupModalMode modalMode = popup.getConfiguration().getDisplayMode().getModalMode();
		if (modalMode == DomPopupModalMode.MODAL_DISMISSABLE) {
			return () -> closeWithoutConfirm(popup);
		} else {
			return () -> focus(popup);
		}
	}

	private void hidePopup(DomPopup popup) {

		popup.getConfiguration().getCallbackBeforeClose().apply();

		Optional.ofNullable(frameMap.get(popup)).ifPresent(frame -> {
			popup.disappend();
			frame.disappend();
			getDomEngine().hidePopup(frame);
		});

		Optional.ofNullable(backdropMap.get(popup)).ifPresent(backdrop -> {
			backdrop.disappend();
		});

		Optional.ofNullable(spawningNodeMap.get(popup)).ifPresent(getDomEngine()::focus);
	}

	private IDomEngine getDomEngine() {

		return getDomDocument().getEngine();
	}

	private IDomNode determineSpawningNode() {

		return Optional//
			.ofNullable(getCurrentEvent())
			.map(IDomEvent::getCurrentTarget)
			.orElse(null);
	}

	private IDomEvent getCurrentEvent() {

		return getDomDocument().getCurrentEvent();
	}

	private IDomDocument getDomDocument() {

		return CurrentDomDocument.get();
	}
}
