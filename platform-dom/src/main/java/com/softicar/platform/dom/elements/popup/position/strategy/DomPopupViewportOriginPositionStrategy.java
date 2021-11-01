package com.softicar.platform.dom.elements.popup.position.strategy;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.position.DomPopupPosition;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.style.CssPercent;

/**
 * Determines a {@link DomPopupPosition}, relative to the origin (i.e. the
 * top-left corner) of the viewport.
 *
 * @author Alexander Schmidt
 */
public class DomPopupViewportOriginPositionStrategy extends AbstractDomPopupViewportAlignmentPositionStrategy {

	/**
	 * Constructs a new {@link DomPopupViewportOriginPositionStrategy}.
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
	public DomPopupViewportOriginPositionStrategy(CssPercent xPercent, CssPercent yPercent) {

		super(xPercent, yPercent, DomPopupXAlign.LEFT, DomPopupYAlign.TOP);
	}
}
