package com.softicar.platform.dom.elements.popup.placement.strategy;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.placement.DomPopupPlacement;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.style.CssPercent;

/**
 * Determines a {@link DomPopupPlacement}, relative to the center of the
 * viewport.
 *
 * @author Alexander Schmidt
 */
public class DomPopupViewportCenterPlacementStrategy extends AbstractDomPopupViewportAlignmentPlacementStrategy {

	/**
	 * Constructs a new {@link DomPopupViewportCenterPlacementStrategy}.
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
	public DomPopupViewportCenterPlacementStrategy(CssPercent xPercent, CssPercent yPercent) {

		super(xPercent, yPercent, DomPopupXAlign.CENTER, DomPopupYAlign.CENTER);
	}
}
