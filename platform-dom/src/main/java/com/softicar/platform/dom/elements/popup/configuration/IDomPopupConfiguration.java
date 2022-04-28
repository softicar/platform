package com.softicar.platform.dom.elements.popup.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.position.strategy.IDomPopupPositionStrategy;
import java.util.List;

/**
 * Holds configuration parameters for a {@link DomPopup}.
 *
 * @author Alexander Schmidt
 */
public interface IDomPopupConfiguration {

	/**
	 * Returns the caption of the {@link DomPopup}.
	 *
	 * @return the caption (may be <i>null</i>)
	 */
	IDisplayString getCaption();

	/**
	 * Returns the sub caption of the {@link DomPopup}.
	 *
	 * @return the sub caption (may be <i>null</i>)
	 */
	IDisplayString getSubCaption();

	/**
	 * Returns the {@link DomPopupDisplayMode} of the {@link DomPopup}.
	 *
	 * @return the {@link DomPopupDisplayMode} (never <i>null</i>)
	 */
	DomPopupDisplayMode getDisplayMode();

	/**
	 * Returns the {@link IDomPopupPositionStrategy} of the {@link DomPopup}.
	 *
	 * @return the {@link IDomPopupPositionStrategy} (never <i>null</i>)
	 */
	IDomPopupPositionStrategy getPositionStrategy();

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
	 * Returns a {@link List} of {@link IStaticObject} markers to be set on the
	 * {@link DomPopupFrame} of the {@link DomPopup}, upon creation of the
	 * former.
	 *
	 * @return the {@link IStaticObject} markers for the {@link DomPopupFrame}
	 */
	List<IStaticObject> getFrameMarkers();
}
