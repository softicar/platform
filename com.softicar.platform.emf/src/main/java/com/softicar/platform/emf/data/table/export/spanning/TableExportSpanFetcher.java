package com.softicar.platform.emf.data.table.export.spanning;

import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.elements.AbstractDomCell;

/**
 * Determines col- and rowspans from {@link AbstractDomCell}s.
 * 
 * @author Alexander Schmidt
 */
public class TableExportSpanFetcher {

	private static final String COLSPAN_HTML_ATTRIBUTE_NAME = "colspan";
	private static final String ROWSPAN_HTML_ATTRIBUTE_NAME = "rowspan";

	public static int getColspanFromCell(AbstractDomCell cell) {

		return getAttributeOrOneFromCell(cell, COLSPAN_HTML_ATTRIBUTE_NAME);
	}

	public static int getRowspanFromCell(AbstractDomCell cell) {

		return getAttributeOrOneFromCell(cell, ROWSPAN_HTML_ATTRIBUTE_NAME);
	}

	/**
	 * @param cell
	 * @param attributeName
	 * @return The value of the attribute with the given name. In case the
	 *         attribute is not set, 1 is returned. Guaranteed to return at
	 *         least 1 in any case.
	 */
	private static int getAttributeOrOneFromCell(AbstractDomCell cell, String attributeName) {

		int colSpanValue = 0;
		IDomAttribute attribute = cell.getAttribute(attributeName);

		if (attribute != null) {
			String value = attribute.getValue();

			if (IntegerParser.isInteger(value)) {
				colSpanValue = Integer.parseInt(value);
			}
		}

		return Math.max(1, colSpanValue);
	}
}
