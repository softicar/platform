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
	private final WeakIdentityHashMap<DomPopup, IDomNode> backdropNodeMap;

	public DomDefaultPopupCompositor() {

		this.frameMap = new WeakIdentityHashMap<>();
		this.spawningNodeMap = new WeakIdentityHashMap<>();
		this.backdropNodeMap = new WeakIdentityHashMap<>();
	}

	@Override
	public void open(DomPopup popup) {

		Objects.requireNonNull(popup);
		if (popup.isAppended()) {
			return;
		}

		IDomNode spawningNode = determineSpawningNode();
		spawningNodeMap.put(popup, spawningNode);

		var configuration = popup.getConfiguration();
		var displayMode = configuration.getDisplayMode();

		var frame = new DomPopupFrame(popup);
		frameMap.put(popup, frame);
		getDomDocument().getBody().appendChild(frame);

		if (displayMode.isModal()) {
			showBackdrop(popup);
		}

		configuration.getCallbackBeforeOpen().apply();

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
			getFrame(popup).ifPresent(getDomEngine()::focus);
		}
	}

	@Override
	public void refreshFrame(DomPopup popup) {

		Objects.requireNonNull(popup);

		getFrame(popup).ifPresent(frame -> {
			frame.refreshCaptions();
		});
	}

	private IDomNode determineSpawningNode() {

		return Optional//
			.ofNullable(getCurrentEvent())
			.map(IDomEvent::getCurrentTarget)
			.orElse(null);
	}

	private INullaryVoidFunction determineBackdropCallback(DomPopup popup) {

		var modalMode = popup.getConfiguration().getDisplayMode().getModalMode();
		if (modalMode == DomPopupModalMode.MODAL_DISMISSABLE) {
			return () -> closeWithoutConfirm(popup);
		} else {
			return () -> focus(popup);
		}
	}

	private void showBackdrop(DomPopup popup) {

		var backdrop = new DomModalDialogBackdrop(determineBackdropCallback(popup));
		backdropNodeMap.put(popup, backdrop);

		getDomEngine().setMaximumZIndex(backdrop);
		getDomDocument().getBody().appendChild(backdrop);
	}

	private void hidePopup(DomPopup popup) {

		popup.getConfiguration().getCallbackBeforeClose().apply();

		getFrame(popup).ifPresent(frame -> {
			popup.disappend();
			frame.disappend();
			getDomEngine().hidePopup(frame);
		});

		getBackdropNode(popup).ifPresent(backdrop -> {
			backdrop.disappend();
		});

		getSpawningNode(popup).ifPresent(getDomEngine()::focus);
	}

	private Optional<DomPopupFrame> getFrame(DomPopup popup) {

		return Optional.ofNullable(frameMap.get(popup));
	}

	private Optional<IDomNode> getSpawningNode(DomPopup popup) {

		return Optional.ofNullable(spawningNodeMap.get(popup));
	}

	private Optional<IDomNode> getBackdropNode(DomPopup popup) {

		return Optional.ofNullable(backdropNodeMap.get(popup));
	}

	private IDomEngine getDomEngine() {

		return getDomDocument().getEngine();
	}

	private IDomEvent getCurrentEvent() {

		return getDomDocument().getCurrentEvent();
	}

	private IDomDocument getDomDocument() {

		return CurrentDomDocument.get();
	}
}
