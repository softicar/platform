package com.softicar.platform.emf.data.table.export.node;

import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.data.table.export.popup.TableExportPopupButton;

/**
 * Implemented by {@link IDomNode}s whose contents shall completely be ignored
 * during table exports triggered via {@link TableExportPopupButton}.
 *
 * @author Alexander Schmidt
 */
public interface ITableExportEmptyNode extends ITableExportManipulatedNode {

	// nothing
}
