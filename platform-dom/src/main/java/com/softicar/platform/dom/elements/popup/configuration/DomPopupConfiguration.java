package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.compositor.IDomPopupContext;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupContextCenterPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupContextTopCenterPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupContextTopLeftPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupEventCoordinatesPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupViewportCenterPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupViewportOriginPlacementStrategy;
import com.softicar.platform.dom.elements.popup.placement.strategy.IDomPopupPlacementStrategy;
import com.softicar.platform.dom.style.CssPercent;
import com.softicar.platform.dom.user.CurrentDomPreferences;
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
	private IDomPopupDisplayMode displayMode;
	private DomPopupChildClosingMode childClosingMode;
	private IDomPopupPlacementStrategy placementStrategy;
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
		this.displayMode = DomPopupDisplayModes.DRAGGABLE;
		this.childClosingMode = null;
		this.placementStrategy = null;
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
	public IDomPopupDisplayMode getDisplayMode() {

		return displayMode;
	}

	/**
	 * Defines the {@link DomPopupDisplayMode} for the {@link DomPopup}.
	 *
	 * @param displayMode
	 *            the {@link DomPopupDisplayMode} (never <i>null</i>)
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayMode(IDomPopupDisplayMode displayMode) {

		this.displayMode = Objects.requireNonNull(displayMode);
		return this;
	}

	/**
	 * Sets {@link DomPopupDisplayModes#DIALOG} for the {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayModeDialog() {

		return setDisplayMode(DomPopupDisplayModes.DIALOG);
	}

	/**
	 * Sets {@link DomPopupDisplayModes#DRAGGABLE} for the {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayModeDraggable() {

		return setDisplayMode(DomPopupDisplayModes.DRAGGABLE);
	}

	/**
	 * Sets {@link DomPopupDisplayModes#DRAGGABLE_MODAL} for the
	 * {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayModeDraggableModal() {

		return setDisplayMode(DomPopupDisplayModes.DRAGGABLE_MODAL);
	}

	/**
	 * Sets {@link DomPopupDisplayModes#MAXIMIZED} for the {@link DomPopup}.
	 * <p>
	 * When using this mode, {@link #setChildClosingModeInteractiveAll()} or
	 * {@link #setChildClosingModeAutomaticAll()} is recommended.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayModeMaximized() {

		return setDisplayMode(DomPopupDisplayModes.MAXIMIZED);
	}

	/**
	 * Sets {@link DomPopupDisplayModes#POPOVER} for the {@link DomPopup}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setDisplayModePopover() {

		return setDisplayMode(DomPopupDisplayModes.POPOVER);
	}

	@Override
	public IDomPopupPlacementStrategy getPlacementStrategy() {

		if (displayMode == DomPopupDisplayModes.POPOVER) {
			return Optional.ofNullable(placementStrategy).orElse(new DomPopupEventCoordinatesPlacementStrategy());
		} else {
			return Optional.ofNullable(placementStrategy).orElse(CurrentDomPreferences.get().getPreferredPopupPlacementStrategy());
		}
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
	 * Defines the {@link IDomPopupPlacementStrategy} for the {@link DomPopup}.
	 *
	 * @param placementStrategy
	 *            the {@link IDomPopupPlacementStrategy} (never <i>null</i>)
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPlacementStrategy(IDomPopupPlacementStrategy placementStrategy) {

		this.placementStrategy = Objects.requireNonNull(placementStrategy);
		return this;
	}

	/**
	 * Defines a placement strategy that displays the {@link DomPopup} at the
	 * current event coordinates.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPlacementStrategyByEvent() {

		return setPlacementStrategy(CurrentDomPreferences.get().getPreferredPopupPlacementStrategy());
	}

	/**
	 * Defines a placement strategy that displays the {@link DomPopup} at the
	 * center of the {@link IDomPopupContext}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPlacementStrategyByContextCenter() {

		return setPlacementStrategy(new DomPopupContextCenterPlacementStrategy());
	}

	/**
	 * Defines a placement strategy that displays the {@link DomPopup}
	 * horizontally centered and top-aligned in the {@link IDomPopupContext}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPlacementStrategyByContextTopCenter() {

		return setPlacementStrategy(new DomPopupContextTopCenterPlacementStrategy());
	}

	/**
	 * Defines a placement strategy that displays the {@link DomPopup} in the
	 * top-left corner of the {@link IDomPopupContext}.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPlacementStrategyByContextTopLeft() {

		return setPlacementStrategy(new DomPopupContextTopLeftPlacementStrategy());
	}

	/**
	 * Defines a placement strategy that displays the {@link DomPopup} at the
	 * center of the viewport.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPlacementStrategyByViewportCenter() {

		return setPlacementStrategyByViewportCenter(CssPercent._50, CssPercent._50);
	}

	/**
	 * Defines a placement strategy that displays the {@link DomPopup} relative
	 * to the center of the viewport.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPlacementStrategyByViewportCenter(CssPercent xPercent, CssPercent yPercent) {

		return setPlacementStrategy(new DomPopupViewportCenterPlacementStrategy(xPercent, yPercent));
	}

	/**
	 * Defines a placement strategy that displays the {@link DomPopup} at the
	 * top-left corner of the viewport.
	 *
	 * @return this {@link DomPopupConfiguration}
	 */
	public DomPopupConfiguration setPlacementStrategyByViewportOrigin() {

		return setPlacementStrategyByViewportOrigin(CssPercent._0, CssPercent._0);
	}

	/**
	 * Defines a placement strategy that displays the {@link DomPopup} relative
	 * to the top-left corner of the viewport.
	 *
	 * @return this {@link DomPopup}
	 */
	public DomPopupConfiguration setPlacementStrategyByViewportOrigin(CssPercent xPercent, CssPercent yPercent) {

		return setPlacementStrategy(new DomPopupViewportOriginPlacementStrategy(xPercent, yPercent));
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
