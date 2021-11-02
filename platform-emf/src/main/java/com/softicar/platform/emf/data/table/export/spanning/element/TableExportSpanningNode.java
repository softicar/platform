package com.softicar.platform.emf.data.table.export.spanning.element;

import com.softicar.platform.dom.node.IDomNode;

/**
 * @param <OT>
 *            The type of the wrapped spanning node
 * @author Alexander Schmidt
 */
public class TableExportSpanningNode<OT extends IDomNode> extends AbstractTableExportSpanningElement<OT> {

	public TableExportSpanningNode(OT element, int colspan, int rowspan) {

		super(element, colspan, rowspan);
	}
}
