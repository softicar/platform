package com.softicar.platform.emf.data.table.export.spanning.element;

import com.softicar.platform.dom.elements.AbstractDomCell;

/**
 * An {@link AbstractDomCell} with explicitly defined col- and rowspan.
 * 
 * @author Alexander Schmidt
 */
public class TableExportSpanningCell extends TableExportSpanningNode<AbstractDomCell> {

	public TableExportSpanningCell(AbstractDomCell element, int colspan, int rowspan) {

		super(element, colspan, rowspan);
	}
}
