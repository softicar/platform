package com.softicar.platform.dom.elements.popup.position.strategy;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.position.DomPopupPosition;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.style.CssPercent;

/**
 * Determines a {@link DomPopupPosition}, relative to the center of the
 * viewport.
 *
 * @author Alexander Schmidt
 */
public class DomPopupViewportCenterPositionStrategy extends AbstractDomPopupViewportAlignmentPositionStrategy {

	/**
	 * Constructs a new {@link DomPopupViewportCenterPositionStrategy}.
	 * <p>
	 * The given percentages represent the horizontal and vertical offsets by
	 * which the {@link DomPopup} shall be positioned, relative to the center of
	 * the viewport.
	 *
	 * @param xPercent
	 *            the horizontal position, relative to the center (never
	 *            <i>null</i>)
	 * @param yPercent
	 *            the vertical position, relative to the center (never
	 *            <i>null</i>)
	 */
	public DomPopupViewportCenterPositionStrategy(CssPercent xPercent, CssPercent yPercent) {

		super(xPercent, yPercent, DomPopupXAlign.CENTER, DomPopupYAlign.CENTER);
	}
}
