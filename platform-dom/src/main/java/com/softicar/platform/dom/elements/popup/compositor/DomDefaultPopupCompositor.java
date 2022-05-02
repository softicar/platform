package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.modal.DomModalPopupBackdrop;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

/**
 * The default implementation of an {@link IDomPopupCompositor}.
 *
 * @author Alexander Schmidt
 */
public class DomDefaultPopupCompositor implements IDomPopupCompositor {

	private final Map<DomPopup, DomPopupFrame> frameMap;
	private final Map<DomPopup, IDomNode> spawningNodeMap;
	private final Map<DomPopup, DomModalPopupBackdrop> backdropMap;
	private List<DomModalPopupBackdrop> backdropStack;

	public DomDefaultPopupCompositor() {

		this.frameMap = new WeakHashMap<>();
		this.spawningNodeMap = new WeakHashMap<>();
		this.backdropMap = new WeakHashMap<>();
		this.backdropStack = new ArrayList<>();
	}

	@Override
	public void open(DomPopup popup) {

		if (!popup.isAppended()) {
			determineSpawningNode().ifPresent(it -> spawningNodeMap.put(popup, it));

			var configuration = popup.getConfiguration();
			var displayMode = configuration.getDisplayMode();

			var frame = new DomPopupFrame(popup);
			frameMap.put(popup, frame);
			getDomDocument().getBody().appendChild(frame);

			if (displayMode.getModalMode().isModal()) {
				showBackdrop(popup);
				refreshBackdropVisibility();
			}

			configuration.getCallbackBeforeOpen().apply();

			var position = configuration.getPositionStrategy().getPosition(getCurrentEvent());
			getDomEngine().showPopup(frame, position.getX(), position.getY(), position.getXAlign(), position.getYAlign());

			if (displayMode.getModalMode().isModal()) {
				getDomEngine().trapTabFocus(frame);
			}

			focus(popup);
		}
	}

	@Override
	public void close(DomPopup popup) {

		if (popup.isAppended()) {
			hidePopup(popup);
			refreshBackdropVisibility();
		}
	}

	@Override
	public void closeAll() {

		frameMap.keySet().forEach(this::close);
	}

	@Override
	public void closeInteractively(DomPopup popup) {

		if (popup.isAppended()) {
			if (popup.getConfiguration().isConfirmBeforeClose()) {
				popup.executeConfirm(() -> hidePopup(popup), DomI18n.ARE_YOU_SURE_TO_CLOSE_THIS_WINDOW_QUESTION);
			} else {
				hidePopup(popup);
			}
		}
	}

	@Override
	public void focus(DomPopup popup) {

		if (popup.isAppended()) {
			if (!IDomTextualInput.focusFirstTextualInput(popup)) {
				getFrame(popup).ifPresent(getDomEngine()::focus);
			}
		}
	}

	@Override
	public void refreshFrame(DomPopup popup) {

		getFrame(popup).ifPresent(frame -> {
			frame.refreshCaptions();
		});
	}

	private Optional<IDomNode> determineSpawningNode() {

		return Optional//
			.ofNullable(getCurrentEvent())
			.map(IDomEvent::getCurrentTarget);
	}

	private INullaryVoidFunction determineBackdropCallback(DomPopup popup) {

		var modalMode = popup.getConfiguration().getDisplayMode().getModalMode();
		if (modalMode.isDismissable()) {
			return () -> close(popup);
		} else {
			return () -> focus(popup);
		}
	}

	private void showBackdrop(DomPopup popup) {

		boolean backdropVisible = popup.getConfiguration().getDisplayMode().getModalMode().isBackdropVisible();
		var backdrop = new DomModalPopupBackdrop(determineBackdropCallback(popup), backdropVisible);
		backdropMap.put(popup, backdrop);
		backdropStack.add(backdrop);

		getDomEngine().setMaximumZIndex(backdrop);
		getDomDocument().getBody().appendChild(backdrop);
	}

	private void refreshBackdropVisibility() {

		refreshBackdropStack();

		backdropStack.forEach(it -> it.removeCssClass(DomCssPseudoClasses.VISIBLE));
		backdropStack//
			.stream()
			.filter(DomModalPopupBackdrop::isAppended)
			.filter(DomModalPopupBackdrop::isVisible)
			.findFirst()
			.ifPresent(it -> it.addCssClass(DomCssPseudoClasses.VISIBLE));
	}

	private void refreshBackdropStack() {

		this.backdropStack = backdropStack//
			.stream()
			.filter(DomModalPopupBackdrop::isAppended)
			.collect(Collectors.toList());
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

		Objects.requireNonNull(popup);
		return Optional.ofNullable(frameMap.get(popup));
	}

	private Optional<IDomNode> getSpawningNode(DomPopup popup) {

		Objects.requireNonNull(popup);
		return Optional.ofNullable(spawningNodeMap.get(popup));
	}

	private Optional<IDomNode> getBackdropNode(DomPopup popup) {

		Objects.requireNonNull(popup);
		return Optional.ofNullable(backdropMap.get(popup));
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
