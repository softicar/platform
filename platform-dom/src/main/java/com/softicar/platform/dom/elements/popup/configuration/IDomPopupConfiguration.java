package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.placement.strategy.IDomPopupPlacementStrategy;
import java.util.List;
import java.util.Optional;

/**
 * Holds configuration parameters for a {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public interface IDomPopupConfiguration {

	/**
	 * Returns the caption of the {@link DomPopup}.
	 *
	 * @return the caption
	 */
	Optional<IDisplayString> getCaption();

	/**
	 * Returns the sub caption of the {@link DomPopup}.
	 *
	 * @return the sub caption
	 */
	Optional<IDisplayString> getSubCaption();

	/**
	 * Returns the {@link DomPopupDisplayMode} of the {@link DomPopup}.
	 *
	 * @return the {@link DomPopupDisplayMode} (never <i>null</i>)
	 */
	IDomPopupDisplayMode getDisplayMode();

	/**
	 * Returns the {@link DomPopupChildClosingMode} of the {@link DomPopup}.
	 *
	 * @return the {@link DomPopupChildClosingMode} (never <i>null</i>)
	 */
	DomPopupChildClosingMode getChildClosingMode();

	/**
	 * Returns the {@link IDomPopupPlacementStrategy} of the {@link DomPopup}.
	 *
	 * @return the {@link IDomPopupPlacementStrategy} (never <i>null</i>)
	 */
	IDomPopupPlacementStrategy getPlacementStrategy();

	/**
	 * Returns the callback to be executed directly before the {@link DomPopup}
	 * is opened.
	 *
	 * @return the callback (never <i>null</i>)
	 */
	INullaryVoidFunction getCallbackBeforeOpen();

	/**
	 * Returns the callback to be executed directly before the {@link DomPopup}
	 * is closed.
	 *
	 * @return the callback (never <i>null</i>)
	 */
	INullaryVoidFunction getCallbackBeforeClose();

	/**
	 * Determines whether the user shall be prompted for confirmation before the
	 * {@link DomPopup} is closed.
	 *
	 * @return <i>true</i> if closing the {@link DomPopup} requires
	 *         confirmation; <i>false</i> otherwise
	 */
	boolean isConfirmBeforeClose();

	/**
	 * Returns a {@link List} of {@link ITestMarker} to be set on the
	 * {@link DomPopupFrame} of the {@link DomPopup}, upon creation of the
	 * former.
	 *
	 * @return the {@link ITestMarker}s for the {@link DomPopupFrame}
	 */
	List<ITestMarker> getFrameMarkers();
}
