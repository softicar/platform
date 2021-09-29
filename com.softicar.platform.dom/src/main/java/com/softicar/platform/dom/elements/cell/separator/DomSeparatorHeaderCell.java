package com.softicar.platform.dom.elements.cell.separator;

import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomHeaderCell;

/**
 * A table header cell to separate cells or lines.
 *
 * @author Oliver Richers
 */
public class DomSeparatorHeaderCell extends DomHeaderCell implements IDomSeparatorCell {

	public DomSeparatorHeaderCell() {

		setCssClass(DomElementsCssClasses.DOM_SEPARATOR_CELL);
	}
}
