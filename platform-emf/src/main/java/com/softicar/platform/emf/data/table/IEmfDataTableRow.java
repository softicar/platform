package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.dom.input.IDomListeningNode;

/**
 * Represents a result row of an {@link IEmfDataTable}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public interface IEmfDataTableRow<R> extends IDomElement, IRefreshable, IDomListeningNode, IDomEventHandler {

	IEmfDataTable<R> getTable();

	R getDataRow();

	void showAsSelected(boolean selected);

	boolean isSelected();

	// FIXME two boolean parameters is bad design; improve API
	void setSelected(boolean doSelect, boolean doCallbacks);

	void setEventHandler(IEmfDataTableRowEventHandler<R> eventHandler);
}
