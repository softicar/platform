package com.softicar.platform.dom.elements.popup.placement.strategy;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.placement.DomPopupPlacement;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.style.CssPercent;

/**
 * Determines a {@link DomPopupPlacement}, relative to the origin (i.e. the
 * top-left corner) of the viewport.
 *
 * @author Alexander Schmidt
 */
public class DomPopupViewportOriginPlacementStrategy extends AbstractDomPopupViewportAlignedPlacementStrategy {

	/**
	 * Constructs a new {@link DomPopupViewportOriginPlacementStrategy}.
	 * <p>
	 * The given percentages represent the horizontal and vertical offsets by
	 * which the {@link DomPopup} shall be positioned, relative to the origin of
	 * the viewport.
	 *
	 * @param xPercent
	 *            the horizontal position, relative to the origin (never
	 *            <i>null</i>)
	 * @param yPercent
	 *            the vertical position, relative to the origin (never
	 *            <i>null</i>)
	 */
	public DomPopupViewportOriginPlacementStrategy(CssPercent xPercent, CssPercent yPercent) {

		super(xPercent, yPercent, DomPopupXAlign.LEFT, DomPopupYAlign.TOP);
	}
}
