package com.softicar.platform.emf.data.table.export.node;

import com.softicar.platform.emf.data.table.export.popup.TableExportPopupButton;

/**
 * Implemented by nodes whose contents shall be fetched in a type safe manner
 * during table exports triggered via {@link TableExportPopupButton}.
 *
 * @author Alexander Schmidt
 */
public interface ITableExportTypedNode extends ITableExportManipulatedNode {

	TableExportTypedNodeValue getTypedExportNodeValue();
}
