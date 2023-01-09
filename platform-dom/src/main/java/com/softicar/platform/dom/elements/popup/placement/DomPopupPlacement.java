package com.softicar.platform.dom.elements.popup.placement;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.IDomPopupFrame;
import com.softicar.platform.dom.engine.DomPopupOffsetUnit;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import java.util.Objects;

/**
 * Represents the position at which a {@link DomPopup} is placed.
 *
 * @author Alexander Schmidt
 */
public class DomPopupPlacement {

	private final int x;
	private final int y;
	private final DomPopupOffsetUnit offsetUnit;
	private final DomPopupXAlign xAlign;
	private final DomPopupYAlign yAlign;

	/**
	 * Constructs a new {@link DomPopupPlacement}.
	 *
	 * @param x
	 *            the horizontal offset of the {@link IDomPopupFrame}
	 * @param y
	 *            the vertical offset of the {@link IDomPopupFrame}
	 * @param offsetUnit
	 *            the unit in which the horizontal and vertical offsets are
	 *            specified (never <i>null</i>)
	 * @param xAlign
	 *            the horizontal alignment of the {@link IDomPopupFrame} (never
	 *            <i>null</i>)
	 * @param yAlign
	 *            the vertical alignment of the {@link IDomPopupFrame} (never
	 *            <i>null</i>)
	 */
	public DomPopupPlacement(int x, int y, DomPopupOffsetUnit offsetUnit, DomPopupXAlign xAlign, DomPopupYAlign yAlign) {

		this.x = x;
		this.y = y;
		this.offsetUnit = Objects.requireNonNull(offsetUnit);
		this.xAlign = Objects.requireNonNull(xAlign);
		this.yAlign = Objects.requireNonNull(yAlign);
	}

	/**
	 * Returns the horizontal offset at which the {@link DomPopup} is placed.
	 *
	 * @return the horizontal offset
	 */
	public int getX() {

		return x;
	}

	/**
	 * Returns the vertical offset at which the {@link DomPopup} is placed.
	 *
	 * @return the vertical offset
	 */
	public int getY() {

		return y;
	}

	/**
	 * Returns the unit in which the horizontal and vertical offsets are
	 * specified.
	 *
	 * @return the offset unit (never <i>null</i>)
	 */
	public DomPopupOffsetUnit getOffsetUnit() {

		return offsetUnit;
	}

	/**
	 * Returns the horizontal alignment of the {@link DomPopup}, relative to the
	 * horizontal placement, as per {@link #getX()}.
	 *
	 * @return the horizontal alignment (never <i>null</i>)
	 */
	public DomPopupXAlign getXAlign() {

		return xAlign;
	}

	/**
	 * Returns the vertical alignment of the {@link DomPopup}, relative to the
	 * vertical placement, as per {@link #getY()}.
	 *
	 * @return the vertical alignment (never <i>null</i>)
	 */
	public DomPopupYAlign getYAlign() {

		return yAlign;
	}
}
