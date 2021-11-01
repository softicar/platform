package com.softicar.platform.dom.elements;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.parent.DomParentElement;

/**
 * This class represents an Html table header element.
 *
 * @author Oliver Richers
 */
public class DomTHead extends DomParentElement {

	@Override
	public DomElementTag getTag() {

		return DomElementTag.THEAD;
	}

	/**
	 * Appends a new row to the header.
	 *
	 * @return the newly created row
	 */
	public DomRow appendRow() {

		DomRow row = new DomRow();
		appendChild(row);
		return row;
	}

	/**
	 * Appends a new row to the header and appends a header cell for every
	 * specified label.
	 *
	 * @param labels
	 *            list of labels to be appended to the row
	 * @return the newly created row
	 */
	public DomRow appendRow(String...labels) {

		DomRow row = appendRow();
		for (String label: labels) {
			row.appendHeaderCell(label);
		}
		return row;
	}

	public DomRow appendRow(Iterable<String> labels) {

		DomRow row = appendRow();
		for (String label: labels) {
			row.appendHeaderCell(label);
		}
		return row;
	}

	/**
	 * Appends a new {@link DomRow}. For each given {@link IDisplayString}, a
	 * {@link DomHeaderCell} which contains that {@link IDisplayString} is
	 * appended to the {@link DomRow}.
	 *
	 * @param cellLabels
	 *            the labels for which header row cells shall be appended
	 * @return the newly created {@link DomRow} (never null)
	 */
	public DomRow appendRow(IDisplayString...cellLabels) {

		DomRow row = appendRow();
		for (IDisplayString label: cellLabels) {
			row.appendHeaderCell(label);
		}
		return row;
	}
}
