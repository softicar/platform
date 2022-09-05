package com.softicar.platform.dom.elements.cell.separator;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomCell;

/**
 * A table body cell to separate cells or lines.
 *
 * @author Oliver Richers
 */
public class DomSeparatorCell extends DomCell implements IDomSeparatorCell {

	public DomSeparatorCell() {

		setCssClass(DomCssClasses.DOM_SEPARATOR_CELL);
	}
}
