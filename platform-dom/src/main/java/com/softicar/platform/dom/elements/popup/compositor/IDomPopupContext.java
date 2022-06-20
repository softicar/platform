package com.softicar.platform.dom.elements.popup.compositor;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.parent.IDomParentElement;

/**
 * An {@link IDomParentElement} that serves as a parent for the
 * {@link DomPopupFrame} of a {@link DomPopup}.
 * <p>
 * The {@link DomPopupFrame} of any {@link DomPopup} is appended to an element
 * that implements this marker interface.
 *
 * @author Alexander Schmidt
 */
public interface IDomPopupContext extends IDomParentElement {

	// nothing
}
