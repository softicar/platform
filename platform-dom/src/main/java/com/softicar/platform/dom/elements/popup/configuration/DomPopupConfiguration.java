package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.position.strategy.DomPopupEventCoordinatesPositionStrategy;
import com.softicar.platform.dom.elements.popup.position.strategy.DomPopupViewportCenterPositionStrategy;
import com.softicar.platform.dom.elements.popup.position.strategy.DomPopupViewportOriginPositionStrategy;
import com.softicar.platform.dom.elements.popup.position.strategy.IDomPopupPositionStrategy;
import com.softicar.platform.dom.style.CssPercent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A mutable implementation of {@link IDomPopupConfiguration}.
 *
 * @author Alexander Schmidt
 */
public class DomPopupConfiguration implements IDomPopupConfiguration {

	private IDisplayString caption;
	private IDisplayString subCaption;
	private DomPopupDisplayMode displayMode;
	private DomPopupChildClosingMode childClosingMode;
	private IDomPopupPositionStrategy positionStrategy;
	private INullaryVoidFunction callbackBeforeOpen;
	private INullaryVoidFunction callbackBeforeClose;
	private boolean confirmBeforeClose;
	private final List<ITestMarker> frameMarkers;

	/**
	 * Creates a new instance, with sane defaults.
	 */
	public DomPopupConfiguration() {

		this.caption = null;
		this.subCaption = null;
		this.displayMode = DomPopupDisplayMode.DRAGGABLE;
		this.childClosingMode = null;
		this.positionStrategy = new DomPopupEventCoordinatesPositionStrategy();
		this.callbackBeforeOpen = INullaryVoidFunction.NO_OPERATION;
		this.callbackBeforeClose = INullaryVoidFunction.NO_OPERATION;
		this.confirmBeforeClose = false;
		this.frameMarkers = new ArrayList<>();
	}

	@Override
	public Optional<IDisplayString> getCaption() {

		return Optional.ofNullable(caption);
	}

	/**
	 * Defines the caption for the {@link DomPopup}.
	 *
	 * @param caption
	 *            the caption text (may be <i>null</i>)
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setCaption(IDisplayString caption) {

		this.caption = caption;
		return this;
	}

	@Override
	public Optional<IDisplayString> getSubCaption() {

		return Optional.ofNullable(subCaption);
	}

	/**
	 * Defines the sub caption for the {@link DomPopup}.
	 *
	 * @param subCaption
	 *            the sub caption text (may be <i>null</i>)
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setSubCaption(IDisplayString subCaption) {

		this.subCaption = subCaption;
		return this;
	}

	@Override
	public DomPopupDisplayMode getDisplayMode() {

		return displayMode;
	}

	/**
	 * Defines the {@link DomPopupDisplayMode} for the {@link DomPopup}.
	 *
	 * @param displayMode
	 *            the {@link DomPopupDisplayMode} (never <i>null</i>)
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayMode(DomPopupDisplayMode displayMode) {

		this.displayMode = Objects.requireNonNull(displayMode);
		return this;
	}

	/**
	 * Sets {@link DomPopupDisplayMode#DIALOG} for the {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayModeDialog() {

		return setDisplayMode(DomPopupDisplayMode.DIALOG);
	}

	/**
	 * Sets {@link DomPopupDisplayMode#DRAGGABLE} for the {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayModeDraggable() {

		return setDisplayMode(DomPopupDisplayMode.DRAGGABLE);
	}

	/**
	 * Sets {@link DomPopupDisplayMode#DRAGGABLE_MODAL} for the
	 * {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayModeDraggableModal() {

		return setDisplayMode(DomPopupDisplayMode.DRAGGABLE_MODAL);
	}

	/**
	 * Sets {@link DomPopupDisplayMode#MAXIMIZED} for the {@link DomPopup}.
	 * <p>
	 * When using this mode, {@link #setChildClosingModeInteractiveAll()} or
	 * {@link #setChildClosingModeAutomaticAll()} is recommended.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayModeMaximized() {

		return setDisplayMode(DomPopupDisplayMode.MAXIMIZED);
	}

	/**
	 * Sets {@link DomPopupDisplayMode#POPOVER} for the {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayModePopover() {

		return setDisplayMode(DomPopupDisplayMode.POPOVER);
	}

	@Override
	public IDomPopupPositionStrategy getPositionStrategy() {

		return positionStrategy;
	}

	/**
	 * Defines the {@link DomPopupChildClosingMode} for the {@link DomPopup}.
	 * <p>
	 * If <i>null</i> is given, {@link #getChildClosingMode()} will default to
	 * {@link DomPopupDisplayMode#getDefaultChildClosingMode()}.
	 *
	 * @param childClosingMode
	 *            the {@link DomPopupChildClosingMode} (may be <i>null</i>)
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setChildClosingMode(DomPopupChildClosingMode childClosingMode) {

		this.childClosingMode = Objects.requireNonNull(childClosingMode);
		return this;
	}

	/**
	 * Sets {@link DomPopupChildClosingMode#AUTOMATIC_ALL} for the
	 * {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setChildClosingModeAutomaticAll() {

		return setChildClosingMode(DomPopupChildClosingMode.AUTOMATIC_ALL);
	}

	/**
	 * Sets {@link DomPopupChildClosingMode#AUTOMATIC_NONE} for the
	 * {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setChildClosingModeAutomaticNone() {

		return setChildClosingMode(DomPopupChildClosingMode.AUTOMATIC_NONE);
	}

	/**
	 * Sets {@link DomPopupChildClosingMode#INTERACTIVE_ALL} for the
	 * {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setChildClosingModeInteractiveAll() {

		return setChildClosingMode(DomPopupChildClosingMode.INTERACTIVE_ALL);
	}

	/**
	 * Sets {@link DomPopupChildClosingMode#INTERACTIVE_OPTIONAL} for the
	 * {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setChildClosingModeInteractiveOptional() {

		return setChildClosingMode(DomPopupChildClosingMode.INTERACTIVE_OPTIONAL);
	}

	/**
	 * Returns the {@link DomPopupChildClosingMode} of the {@link DomPopup}.
	 * <p>
	 * If no such mode was defined,
	 * {@link DomPopupDisplayMode#getDefaultChildClosingMode()} will be used.
	 *
	 * @return the {@link DomPopupChildClosingMode} (never <i>null</i>)
	 */
	@Override
	public DomPopupChildClosingMode getChildClosingMode() {

		return Optional.ofNullable(childClosingMode).orElse(displayMode.getDefaultChildClosingMode());
	}

	/**
	 * Defines the {@link IDomPopupPositionStrategy} for the {@link DomPopup}.
	 *
	 * @param positionStrategy
	 *            the {@link IDomPopupPositionStrategy} (never <i>null</i>)
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPositionStrategy(IDomPopupPositionStrategy positionStrategy) {

		this.positionStrategy = Objects.requireNonNull(positionStrategy);
		return this;
	}

	/**
	 * Defines a position strategy that displays the {@link DomPopup} at the
	 * current event coordinates.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPositionStrategyByEvent() {

		return setPositionStrategy(new DomPopupEventCoordinatesPositionStrategy());
	}

	/**
	 * Defines a position strategy that displays the {@link DomPopup} at the
	 * center of the viewport.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPositionStrategyByViewportCenter() {

		return setPositionStrategyByViewportCenter(CssPercent._50, CssPercent._50);
	}

	/**
	 * Defines a position strategy that displays the {@link DomPopup} relative
	 * to the center of the viewport.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPositionStrategyByViewportCenter(CssPercent xPercent, CssPercent yPercent) {

		return setPositionStrategy(new DomPopupViewportCenterPositionStrategy(xPercent, yPercent));
	}

	/**
	 * Defines a position strategy that displays the {@link DomPopup} at the
	 * top-left corner of the viewport.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPositionStrategyByViewportOrigin() {

		return setPositionStrategyByViewportOrigin(CssPercent._0, CssPercent._0);
	}

	/**
	 * Defines a position strategy that displays the {@link DomPopup} relative
	 * to the top-left corner of the viewport.
	 *
	 * @return this {@link DomPopup}
	 */
	public DomPopupConfiguration setPositionStrategyByViewportOrigin(CssPercent xPercent, CssPercent yPercent) {

		return setPositionStrategy(new DomPopupViewportOriginPositionStrategy(xPercent, yPercent));
	}

	@Override
	public INullaryVoidFunction getCallbackBeforeOpen() {

		return callbackBeforeOpen;
	}

	/**
	 * Defines the callback to be executed directly before the {@link DomPopup}
	 * is opened.
	 *
	 * @param callbackBeforeOpen
	 *            the callback (never <i>null</i>)
	 * @return this {@link DomPopup}
	 */
	public DomPopupConfiguration setCallbackBeforeOpen(INullaryVoidFunction callbackBeforeOpen) {

		this.callbackBeforeOpen = Objects.requireNonNull(callbackBeforeOpen);
		return this;
	}

	@Override
	public INullaryVoidFunction getCallbackBeforeClose() {

		return callbackBeforeClose;
	}

	/**
	 * Defines the callback to be executed directly before the {@link DomPopup}
	 * is closed.
	 *
	 * @param callbackBeforeClose
	 *            the callback (never <i>null</i>)
	 * @return this {@link DomPopup}
	 */
	public DomPopupConfiguration setCallbackBeforeClose(INullaryVoidFunction callbackBeforeClose) {

		this.callbackBeforeClose = Objects.requireNonNull(callbackBeforeClose);
		return this;
	}

	@Override
	public boolean isConfirmBeforeClose() {

		return confirmBeforeClose;
	}

	/**
	 * Defines whether the user shall be prompted for confirmation before the
	 * {@link DomPopup} is closed.
	 *
	 * @param confirmBeforeClose
	 *            <i>true</i> if closing the {@link DomPopup} requires
	 *            confirmation; <i>false</i> otherwise
	 * @return this {@link DomPopup}
	 */
	public DomPopupConfiguration setConfirmBeforeClose(boolean confirmBeforeClose) {

		this.confirmBeforeClose = confirmBeforeClose;
		return this;
	}

	@Override
	public List<ITestMarker> getFrameMarkers() {

		return frameMarkers;
	}

	/**
	 * Adds an {@link ITestMarker} to be set on the {@link DomPopupFrame} of the
	 * {@link DomPopup}, upon creation of the former.
	 *
	 * @param frameMarker
	 *            the {@link ITestMarker} for the {@link DomPopupFrame}
	 * @return this {@link DomPopup}
	 */
	public DomPopupConfiguration addFrameMarker(ITestMarker frameMarker) {

		this.frameMarkers.add(Objects.requireNonNull(frameMarker));
		return this;
	}
}
