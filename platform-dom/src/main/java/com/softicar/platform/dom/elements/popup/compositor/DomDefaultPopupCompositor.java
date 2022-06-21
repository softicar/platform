package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.elements.dialog.DomModalDialog;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.configuration.DomPopupChildClosingMode;
import com.softicar.platform.dom.elements.popup.configuration.IDomPopupConfiguration;
import com.softicar.platform.dom.elements.popup.modal.DomModalPopupBackdrop;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.IDomFocusable;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * The default implementation of an {@link IDomPopupCompositor}.
 *
 * @author Alexander Schmidt
 */
public class DomDefaultPopupCompositor implements IDomPopupCompositor {

	private final DomPopupStateTracker stateTracker;
	private final Map<DomPopup, IDomNode> spawningNodeMap;
	private final Map<DomPopup, IDomPopupContext> contextMap;
	private final DomPopupBackdropTracker backdropTracker;
	private final DomPopupHierarchyGraph hierarchyGraph;
	private final DomPopupFrameHighlighter frameHighlighter;
	private final DomPopupContextStasher contextStasher;

	public DomDefaultPopupCompositor() {

		this.stateTracker = new DomPopupStateTracker();
		this.spawningNodeMap = new WeakHashMap<>();
		this.contextMap = new WeakHashMap<>();
		this.backdropTracker = new DomPopupBackdropTracker();
		this.hierarchyGraph = new DomPopupHierarchyGraph();
		this.frameHighlighter = new DomPopupFrameHighlighter();
		this.contextStasher = new DomPopupContextStasher();
	}

	@Override
	public void open(DomPopup popup) {

		if (!stateTracker.isOpen(popup)) {
			var configuration = popup.getConfiguration();
			var displayMode = configuration.getDisplayMode();

			// -------- fetch spawning node -------- //
			IDomNode spawningNode = getCurrentEventTarget().orElse(getBody());
			spawningNodeMap.put(popup, spawningNode);

			// -------- determine context -------- //
			var context = findContainingContext(spawningNode);
			contextMap.put(popup, context);

			// -------- execute before-open callback -------- //
			applyCallbackBeforeOpen(configuration);

			// -------- append backdrop -------- //
			appendBackdrop(popup, context);

			// -------- create frame -------- //
			var frame = new DomPopupFrame(popup, context);

			// -------- append frame -------- //
			if (displayMode.isMaximized()) {
				contextStasher.stash(context);
			}
			context.appendChild(frame);
			stateTracker.setOpen(popup);

			// -------- set up popup -------- //
			initializePopup(configuration, frame);

			// -------- maintain hierarchy -------- //
			new DomParentNodeFinder<>(DomPopup.class).findClosestParent(spawningNode).ifPresent(parent -> {
				hierarchyGraph.add(parent, popup);
			});
		}

		showPopup(popup);
	}

	@Override
	public void close(DomPopup popup) {

		closePopup(//
			popup,
			popup.getConfiguration().getChildClosingMode().isCloseAll());
	}

	@Override
	public void closeAll() {

		stateTracker.getAllOpenInReverseOrder().forEach(this::close);
		backdropTracker.clear().forEach(DomModalPopupBackdrop::disappend);
		hierarchyGraph.clear();
		contextStasher.clear();
		stateTracker.clear();
	}

	@Override
	public void closeInteractively(DomPopup popup) {

		if (stateTracker.isOpen(popup)) {
			var childClosingMode = popup.getConfiguration().getChildClosingMode();
			var childPopups = hierarchyGraph.getAllChildPopups(popup);

			if (childClosingMode.isInteractive() && !childPopups.isEmpty()) {
				buildChildClosingDialog(popup, childPopups, childClosingMode).open();
			}

			else if (popup.getConfiguration().isConfirmBeforeClose()) {
				popup.executeConfirm(() -> closePopup(popup, childClosingMode.isCloseAll()), DomI18n.ARE_YOU_SURE_TO_CLOSE_THIS_WINDOW_QUESTION);
			}

			else {
				closePopup(popup, childClosingMode.isCloseAll());
			}
		}
	}

	@Override
	public void focus(DomPopup popup) {

		if (stateTracker.isOpen(popup)) {
			boolean focused = IDomFocusable.focusFirst(IDomTextualInput.class, popup);
			if (!focused) {
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

	private void closePopup(DomPopup popup, boolean closeChildren) {

		if (stateTracker.isOpen(popup)) {
			var childPopups = hierarchyGraph.getAllChildPopups(popup);

			// -------- close child popups -------- //
			if (closeChildren) {
				childPopups.forEach(child -> close(child));
			}

			// -------- unhighlight popups -------- //
			frameHighlighter.unhighlight(popup).unhighlight(childPopups);

			// -------- unstash content -------- //
			if (popup.getConfiguration().getDisplayMode().isMaximized()) {
				var context = findContainingContext(popup);
				contextStasher.unstash(context);
			}

			// -------- execute before-close callback -------- //
			popup.getConfiguration().getCallbackBeforeClose().apply();

			// -------- disappend frame -------- //
			getFrame(popup).ifPresent(DomPopupFrame::disappend);
			popup.disappend();
			stateTracker.setClosed(popup);

			// -------- maintain hierarchy -------- //
			hierarchyGraph.removeAllNonMatching(stateTracker::isOpen);

			// -------- remove backdrop -------- //
			removeBackdrop(popup);

			// -------- set focus -------- //
			getSpawningNode(popup).ifPresent(getDomEngine()::focus);
		}
	}

	private DomModalDialog buildChildClosingDialog(DomPopup popup, List<DomPopup> childPopups, DomPopupChildClosingMode childClosingMode) {

		var dialog = new DomPopupCompositorChildClosingDialog();
		dialog.setCallbackBeforeOpen(() -> frameHighlighter.highlight(popup).highlight(childPopups));
		if (!childClosingMode.isCloseAll()) {
			dialog.appendCloseOnlyThisButton(() -> closePopup(popup, false));
		}
		dialog.appendCloseAllButton(() -> closePopup(popup, true));
		dialog.appendCancelButton(() -> frameHighlighter.unhighlight(popup).unhighlight(childPopups));
		return dialog;
	}

	private void showPopup(DomPopup popup) {

		getFrame(popup).ifPresent(frame -> {
			var configuration = popup.getConfiguration();
			movePopup(configuration, frame);
			raisePopup(frame);
			trapTabFocus(configuration, frame);
			focus(popup);
		});
	}

	private void movePopup(IDomPopupConfiguration configuration, DomPopupFrame frame) {

		if (!configuration.getDisplayMode().isMaximized()) {
			var position = configuration.getPositionStrategy().getPosition(getCurrentEvent());
			getDomEngine().movePopup(frame, position.getX(), position.getY(), position.getXAlign(), position.getYAlign());
		}
	}

	private void trapTabFocus(IDomPopupConfiguration configuration, DomPopupFrame frame) {

		if (configuration.getDisplayMode().getModalMode().isModal()) {
			getDomEngine().trapTabFocus(frame);
		}
	}

	private void applyCallbackBeforeOpen(IDomPopupConfiguration configuration) {

		configuration.getCallbackBeforeOpen().apply();
	}

	private void initializePopup(IDomPopupConfiguration configuration, DomPopupFrame frame) {

		getDomEngine().initializePopup(frame, !configuration.getDisplayMode().isMaximized());
	}

	private void raisePopup(DomPopupFrame frame) {

		getDomEngine().raise(frame);
	}

	private void appendBackdrop(DomPopup popup, IDomPopupContext popupContext) {

		var modalMode = popup.getConfiguration().getDisplayMode().getModalMode();
		if (modalMode.isModal()) {
			boolean visible = modalMode.isBackdropVisible();
			var backdrop = new DomModalPopupBackdrop(determineBackdropCallback(popup), visible);
			backdropTracker.add(popup, backdrop);
			refreshBackdropVisibility();
			getDomEngine().raise(backdrop);
			popupContext.appendChild(backdrop);
		}
	}

	private void removeBackdrop(DomPopup popup) {

		backdropTracker.remove(popup).ifPresent(backdrop -> {
			backdrop.disappend();
			refreshBackdropVisibility();
		});
	}

	private void refreshBackdropVisibility() {

		var backdrops = backdropTracker.getAllBackdrops();
		backdrops.forEach(it -> it.removeCssClass(DomCssPseudoClasses.VISIBLE));
		backdrops//
			.stream()
			.filter(DomModalPopupBackdrop::isVisible)
			.findFirst()
			.ifPresent(it -> it.addCssClass(DomCssPseudoClasses.VISIBLE));
	}

	private INullaryVoidFunction determineBackdropCallback(DomPopup popup) {

		var modalMode = popup.getConfiguration().getDisplayMode().getModalMode();
		if (modalMode.isDismissable()) {
			return () -> closeInteractively(popup);
		} else {
			return () -> focus(popup);
		}
	}

	private IDomPopupContext findContainingContext(IDomNode node) {

		Optional<IDomPopupContext> context = findContextByParentFrame(node)//
			.or(() -> findClosestContext(node));

		return context.orElseGet(() -> {
			String message = "Warning: Failed to determine the %s for node %s (%s). Using the document body instead."
				.formatted(//
					IDomPopupContext.class.getSimpleName(),
					node.getNodeIdString(),
					node.getClass().getCanonicalName());
			Log.fwarning(message);
			return getBody();
		});
	}

	private Optional<IDomPopupContext> findContextByParentFrame(IDomNode node) {

		return new DomParentNodeFinder<>(DomPopup.class)//
			.findClosestParent(node, true)
			.map(contextMap::get);
	}

	private Optional<IDomPopupContext> findClosestContext(IDomNode node) {

		return new DomParentNodeFinder<>(IDomPopupContext.class)//
			.findClosestParent(node, true);
	}

	private Optional<DomPopupFrame> getFrame(DomPopup popup) {

		Objects.requireNonNull(popup);
		return new DomParentNodeFinder<>(DomPopupFrame.class).findClosestParent(popup);
	}

	private Optional<IDomNode> getSpawningNode(DomPopup popup) {

		Objects.requireNonNull(popup);
		return Optional.ofNullable(spawningNodeMap.get(popup));
	}

	private IDomEngine getDomEngine() {

		return getDomDocument().getEngine();
	}

	private IDomDocument getDomDocument() {

		return CurrentDomDocument.get();
	}

	private DomBody getBody() {

		return getDomDocument().getBody();
	}

	private IDomEvent getCurrentEvent() {

		return getDomDocument().getCurrentEvent();
	}

	private Optional<IDomNode> getCurrentEventTarget() {

		return Optional//
			.ofNullable(getCurrentEvent())
			.map(IDomEvent::getCurrentTarget);
	}
}
