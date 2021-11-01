package com.softicar.platform.dom.elements.cell.separator;

import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomElementsCssClasses;

/**
 * A table body cell to separate cells or lines.
 *
 * @author Oliver Richers
 */
public class DomSeparatorCell extends DomCell implements IDomSeparatorCell {

	public DomSeparatorCell() {

		setCssClass(DomElementsCssClasses.DOM_SEPARATOR_CELL);
	}
}
