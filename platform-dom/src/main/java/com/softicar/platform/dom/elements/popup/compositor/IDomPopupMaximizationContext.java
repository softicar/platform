package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.parent.IDomParentElement;

/**
 * An {@link IDomParentElement} that serves as a container for a maximized
 * {@link DomPopup}.
 * <p>
 * The {@link DomPopupFrame} of any maximized {@link DomPopup} is appended to an
 * element that implements this marker interface.
 *
 * @author Alexander Schmidt
 */
public interface IDomPopupMaximizationContext extends IDomParentElement {

	// nothing
}
