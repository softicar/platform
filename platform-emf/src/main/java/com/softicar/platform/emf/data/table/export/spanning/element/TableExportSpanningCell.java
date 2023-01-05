package com.softicar.platform.emf.data.table.export.spanning.element;

import com.softicar.platform.dom.elements.AbstractDomCell;
import com.softicar.platform.dom.elements.IDomCell;

/**
 * An {@link AbstractDomCell} with explicitly defined col- and rowspan.
 *
 * @author Alexander Schmidt
 */
public class TableExportSpanningCell extends TableExportSpanningNode<IDomCell> {

	public TableExportSpanningCell(IDomCell cell, int colspan, int rowspan) {

		super(cell, colspan, rowspan);
	}
}
