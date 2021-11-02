package com.softicar.platform.dom.elements;

import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;

/**
 * A deprecated wrapper class for {@link DomButton}.
 * <p>
 * Only exists for the sake of backwards compatibility.
 *
 * @author Alexander Schmidt
 * @deprecated use {@link DomButton} without a {@link DomActionBar} parent
 *             instead
 */
@Deprecated
public class DomIconAnchor extends DomButton {

	// nothing
}
