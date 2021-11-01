package com.softicar.platform.dom.elements.popup.position;

import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import java.util.Objects;

/**
 * Represents the position at which a {@link DomPopup} is shown.
 *
 * @author Alexander Schmidt
 */
public class DomPopupPosition {

	private final int x;
	private final int y;
	private final DomPopupXAlign xAlign;
	private final DomPopupYAlign yAlign;

	/**
	 * Constructs a new {@link DomPopupPosition} at which a {@link DomPopup}
	 * shall be shown.
	 *
	 * @param x
	 *            the X coordinate
	 * @param y
	 *            the Y coordinate
	 * @param xAlign
	 *            the horizontal alignment (never <i>null</i>)
	 * @param yAlign
	 *            the vertical alignment (never <i>null</i>)
	 */
	public DomPopupPosition(int x, int y, DomPopupXAlign xAlign, DomPopupYAlign yAlign) {

		this.x = x;
		this.y = y;
		this.xAlign = Objects.requireNonNull(xAlign);
		this.yAlign = Objects.requireNonNull(yAlign);
	}

	/**
	 * Returns the X coordinate (i.e. the horizontal offset) at which the
	 * {@link DomPopup} is shown.
	 *
	 * @return the X coordinate
	 */
	public int getX() {

		return x;
	}

	/**
	 * Returns the Y coordinate (i.e. the vertical offset) at which the
	 * {@link DomPopup} is shown.
	 *
	 * @return the Y coordinate
	 */
	public int getY() {

		return y;
	}

	/**
	 * Returns the horizontal alignment of the {@link DomPopup}, relative to the
	 * X coordinate, as per {@link #getX()}.
	 *
	 * @return the horizontal alignment (never <i>null</i>)
	 */
	public DomPopupXAlign getXAlign() {

		return xAlign;
	}

	/**
	 * Returns the vertical alignment of the {@link DomPopup}, relative to the Y
	 * coordinate, as per {@link #getY()}.
	 *
	 * @return the vertical alignment (never <i>null</i>)
	 */
	public DomPopupYAlign getYAlign() {

		return yAlign;
	}
}
