package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html table row element.
 *
 * @author Oliver Richers
 */
public class DomRow extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.TR;
	}

	/**
	 * Appends a new empty cell to this row.
	 *
	 * @return the new cell
	 */
	public DomCell appendCell() {

		DomCell cell = new DomCell();
		appendChild(cell);
		return cell;
	}

	/**
	 * Appends a new cell to this row and appends the specified children to the
	 * new cell.
	 *
	 * @return the new cell
	 */
	public DomCell appendCell(Object...children) {

		DomCell cell = appendCell();
		cell.appendChildren(children);
		return cell;
	}

	/**
	 * Appends a new {@link DomCell} to this row, with the given
	 * {@link IDisplayString} as textual content.
	 *
	 * @param displayString
	 *            the {@link IDisplayString} to display in the {@link DomCell}
	 *            (never null)
	 * @return the appended {@link DomCell}
	 */
	public DomCell appendCell(IDisplayString displayString) {

		return appendCell(displayString.toString());
	}

	/**
	 * Appends the specified cell to this row.
	 *
	 * @return the new cell
	 */
	public <T extends DomCell> T appendCell(T childCell) {

		return appendChild(childCell);
	}

	/**
	 * Appends the specified header cell to this row.
	 *
	 * @return the new cell
	 */
	public <T extends DomHeaderCell> T appendCell(T childCell) {

		return appendChild(childCell);
	}

	public DomCell appendTextCell(String text) {

		DomCell cell = appendCell();
		cell.appendText(text);
		return cell;
	}

	public DomCell appendTextCell(String format, Object...args) {

		return appendCell(String.format(format, args));
	}

	/**
	 * Appends a new cell for every specified child to this row.
	 *
	 * @param children
	 */
	public void appendCells(Object...children) {

		for (Object child: children) {
			if (child instanceof DomCell || child instanceof DomHeaderCell) {
				appendChild(child);
			} else {
				appendCell(child);
			}
		}
	}

	public void appendCells(IDisplayString...displayStrings) {

		for (IDisplayString displayString: displayStrings) {
			appendCell(displayString);
		}
	}

	/**
	 * Appends a new empty header cell to this row.
	 *
	 * @return the new cell
	 */
	public DomHeaderCell appendHeaderCell() {

		DomHeaderCell cell = new DomHeaderCell();
		appendChild(cell);
		return cell;
	}

	/**
	 * Appends a new header cell to this row and appends the specified children
	 * to the new cell.
	 *
	 * @return the new cell
	 */
	public DomHeaderCell appendHeaderCell(Object...children) {

		DomHeaderCell cell = appendHeaderCell();
		cell.appendChildren(children);
		return cell;
	}

	/**
	 * Appends a new {@link DomHeaderCell} to this row, with the given
	 * {@link IDisplayString} as textual content.
	 *
	 * @param displayString
	 *            the {@link IDisplayString} to display in the
	 *            {@link DomHeaderCell} (never null)
	 * @return the appended {@link DomHeaderCell}
	 */
	public DomHeaderCell appendHeaderCell(IDisplayString displayString) {

		return appendHeaderCell(displayString.toString());
	}

	/**
	 * Appends the specified {@link DomHeaderCell} to this row.
	 *
	 * @return the given {@link DomHeaderCell}
	 */
	public <T extends DomHeaderCell> T appendHeaderCell(T childHeaderCell) {

		return appendChild(childHeaderCell);
	}

	/**
	 * Appends a new header cell for every specified child to this row.
	 */
	public void appendHeaderCells(Object...children) {

		for (Object child: children) {
			appendHeaderCell(child);
		}
	}

	public void appendHeaderCells(IDisplayString...displayStrings) {

		for (IDisplayString displayString: displayStrings) {
			appendHeaderCell(displayString);
		}
	}
}
