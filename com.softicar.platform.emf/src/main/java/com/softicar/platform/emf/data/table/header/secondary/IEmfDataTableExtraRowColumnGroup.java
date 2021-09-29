package com.softicar.platform.emf.data.table.header.secondary;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.Collection;

public interface IEmfDataTableExtraRowColumnGroup<R> {

	IEmfDataTableExtraRowCellBuilder<R> getCellBuilder();

	Collection<? extends IDataTableColumn<?, ?>> getColumns();
}
