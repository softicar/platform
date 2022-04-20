package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.db.core.database.IDbDatabaseScope;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.data.table.column.settings.EmfDataTableColumnSettings;
import com.softicar.platform.emf.data.table.header.secondary.IEmfDataTableExtraRowColumnGroupListAccumulator;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IEmfDataTableConfig<R> {

	Optional<IEmfDataTableActionColumnHandler<R>> getActionColumnHandler();

	<T> IEmfDataTableRowBasedColumnHandler<R, T> getColumnHandler(IDataTableColumn<R, T> column);

	EmfDataTableColumnSettings getColumnSettings(IDataTableColumn<R, ?> column);

	IDataTable<R> getDataTable();

	Collection<IStaticObject> getTableMarkers();

	Collection<IStaticObject> getTableDivMarkers();

	EmfDataTableOrdering<R> getOrdering();

	int getPageSize();

	List<DomButton> getRowSelectionActionButtons();

	IEmfDataTableRowSelectionCallback<R> getRowSelectionCallback();

	Optional<IEmfDataTableRowCustomizer<R>> getRowCustomizer();

	Optional<IDataTablePrefetcher<R>> getCallbackBeforePrefetch();

	boolean isHideNavigationActionButtions();

	boolean isHideNavigationAtBottom();

	boolean isHideNavigationAtTop();

	boolean isRowSelectionEnabled();

	EmfDataTableRowSelectionMode getRowSelectionMode();

	Supplier<IDbDatabaseScope> getDatabaseScopeSupplier();

	IEmfDataTableExtraRowColumnGroupListAccumulator<R> getSecondaryHeaderRowAccumulator();

	IEmfDataTableExtraRowColumnGroupListAccumulator<R> getFooterRowAccumulator();

	Optional<Consumer<Collection<R>>> getCallbackAfterContentChange();

	Supplier<IDomNode> getEmptyTablePlaceholderFactory();
}
