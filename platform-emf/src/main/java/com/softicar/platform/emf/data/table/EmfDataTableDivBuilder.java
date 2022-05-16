package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.db.core.database.IDbDatabase;
import com.softicar.platform.db.core.database.IDbDatabaseScope;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableValueBasedColumnHandler;
import com.softicar.platform.emf.data.table.empty.EmfDataTableEmptyTablePlaceholderDiv;
import com.softicar.platform.emf.data.table.header.secondary.EmfDataTableExtraRowDefinition;
import com.softicar.platform.emf.data.table.header.secondary.IEmfDataTableExtraRowColumnGroupList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Create new instances of {@link IEmfDataTableDiv}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class EmfDataTableDivBuilder<R> {

	private final EmfDataTableConfig<R> config;

	public EmfDataTableDivBuilder(IDataTable<R> dataTable) {

		this.config = new EmfDataTableConfig<>(dataTable);
	}

	/**
	 * Defines the action column handler for the table.
	 * <p>
	 * When an action column handler is defined, the table will display an
	 * additional action column. If row selection is disabled, this action
	 * column is the left-most column. If row selection is enabled,the action
	 * column is the second column from the left, while the row selection column
	 * is the left-most column.
	 *
	 * @param actionColumnHandler
	 *            the action column handler (never null)
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setActionColumnHandler(IEmfDataTableActionColumnHandler<R> actionColumnHandler) {

		config.setActionColumnHandler(actionColumnHandler);
		return this;
	}

	public <T> EmfDataTableDivBuilder<R> setColumnHandler(IDataTableColumn<R, T> column, IEmfDataTableValueBasedColumnHandler<T> columnHandler) {

		config.setColumnHandler(column, columnHandler);
		return this;
	}

	public <T> EmfDataTableDivBuilder<R> setColumnHandler(IDataTableColumn<R, T> column, IEmfDataTableRowBasedColumnHandler<R, T> columnHandler) {

		config.setColumnHandler(column, columnHandler);
		return this;
	}

	public <T> EmfDataTableDivBuilder<R> setColumnTitle(IDataTableColumn<R, T> column, IDisplayString title) {

		config.getColumnSettings(column).setTitleOverride(title);
		return this;
	}

	public <T> EmfDataTableDivBuilder<R> setColumnTitle(IDataTableColumn<R, T> column, String title, Object...args) {

		config.getColumnSettings(column).setTitleOverride(IDisplayString.format(title, args));
		return this;
	}

	/**
	 * Conceals the given column.
	 * <p>
	 * In contrast to {@link #setHidden}, the user cannot reveal this column. It
	 * is completely removed from the user interface.
	 *
	 * @param column
	 *            the column to conceal
	 * @param concealed
	 *            <i>true</i> to conceal the column; <i>false</i> otherwise
	 * @return this builder
	 */
	public <T> EmfDataTableDivBuilder<R> setConcealed(IDataTableColumn<R, T> column, boolean concealed) {

		config.getColumnSettings(column).setConcealed(concealed);
		return this;
	}

	/**
	 * Hides the given column.
	 * <p>
	 * In contrast to {@link #setConcealed}, the user has the possibility to
	 * reveal the given column.
	 *
	 * @param column
	 *            the column to hide (never <i>null</i>)
	 * @param hidden
	 *            <i>true</i> to hide the column; <i>false</i> otherwise
	 * @return this builder
	 */
	public <T> EmfDataTableDivBuilder<R> setHidden(IDataTableColumn<R, T> column, boolean hidden) {

		config.getColumnSettings(column).setHidden(hidden);
		return this;
	}

	/**
	 * Defines if the table header for the given cell shall be shown vertically.
	 *
	 * @param column
	 *            the column to change
	 * @param verticalHeader
	 *            <i>true</i> to show header vertically or <i>false</i> to show
	 *            header horizontally (default)
	 * @return this builder
	 */
	public <T> EmfDataTableDivBuilder<R> setVerticalHeader(IDataTableColumn<R, T> column, boolean verticalHeader) {

		config.getColumnSettings(column).setVerticalHeader(verticalHeader);
		return this;
	}

	/**
	 * Defines the default alignment for the given column.
	 *
	 * @param column
	 *            the column to align
	 * @param alignment
	 *            the alignment
	 * @return this builder
	 */
	public <T> EmfDataTableDivBuilder<R> setAlignment(IDataTableColumn<R, T> column, CssTextAlign alignment) {

		config.getColumnSettings(column).setAlignment(alignment);
		return this;
	}

	/**
	 * Defines if the IDs of {@link IEntity} should be displayed.
	 * <p>
	 * By default, the IDs are not shown.
	 *
	 * @param column
	 *            the column
	 * @param showIds
	 *            <i>true</i> to show the ID; <i>false</i> otherwise
	 * @return this builder
	 */
	public <T extends IEntity> EmfDataTableDivBuilder<R> setShowIds(IDataTableColumn<R, T> column, boolean showIds) {

		config.getColumnSettings(column).setShowIds(showIds);
		return this;
	}

	/**
	 * Adds the given column to the ordering list.
	 *
	 * @param column
	 *            the column to order by
	 * @param direction
	 *            the order direction
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> addOrderBy(IDataTableColumn<R, ?> column, OrderDirection direction) {

		this.config.addOrderBy(column, direction);
		return this;
	}

	/**
	 * Clears the current ordering list and adds the given column as single
	 * element.
	 *
	 * @param column
	 *            the column to order by
	 * @param direction
	 *            the order direction
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setOrderBy(IDataTableColumn<R, ?> column, OrderDirection direction) {

		this.config.setOrderBy(column, direction);
		return this;
	}

	/**
	 * Adds the given test marker for the specified {@link IDataTableColumn}.
	 *
	 * @param column
	 *            the {@link IDataTableColumn} (never <i>null</i>)
	 * @param marker
	 *            the test marker
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> addColumnMarker(IDataTableColumn<R, ?> column, IStaticObject marker) {

		config.getColumnSettings(column).addMarker(marker);
		return this;
	}

	/**
	 * Enabled or disables row selection.
	 * <p>
	 * When enabled, an extra table column is shown where the user can select
	 * individual table rows. The selected rows can be retrieved by calling
	 * {@link IEmfDataTableDiv#getSelectedRows()}.
	 *
	 * @param rowSelectionMode
	 *            {@link EmfDataTableRowSelectionMode#MULTI} or
	 *            {@link EmfDataTableRowSelectionMode#SINGLE} to enable row
	 *            selection, {@link EmfDataTableRowSelectionMode#NONE} otherwise
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setRowSelectionMode(EmfDataTableRowSelectionMode rowSelectionMode) {

		this.config.setRowSelectionMode(rowSelectionMode);
		return this;
	}

	/**
	 * Convenience method for
	 * {@link #setRowSelectionMode(EmfDataTableRowSelectionMode)} with
	 * {@link EmfDataTableRowSelectionMode#NONE}.
	 *
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setRowSelectionModeNone() {

		return setRowSelectionMode(EmfDataTableRowSelectionMode.NONE);
	}

	/**
	 * Convenience method for
	 * {@link #setRowSelectionMode(EmfDataTableRowSelectionMode)} with
	 * {@link EmfDataTableRowSelectionMode#SINGLE}.
	 *
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setRowSelectionModeSingle() {

		return setRowSelectionMode(EmfDataTableRowSelectionMode.SINGLE);
	}

	/**
	 * Convenience method for
	 * {@link #setRowSelectionMode(EmfDataTableRowSelectionMode)} with
	 * {@link EmfDataTableRowSelectionMode#MULTI}.
	 *
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setRowSelectionModeMulti() {

		return setRowSelectionMode(EmfDataTableRowSelectionMode.MULTI);
	}

	public EmfDataTableDivBuilder<R> addRowSelectionActionButton(DomButton button) {

		this.config.addRowSelectionActionButton(button);
		return this;
	}

	/**
	 * Defines a callback for the row selection.
	 * <p>
	 * Whenever the row selection is called the callback will be triggered.
	 *
	 * @param rowSelectionCallback
	 *            the callback instance
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setRowSelectionCallback(IEmfDataTableRowSelectionCallback<R> rowSelectionCallback) {

		this.config.setRowSelectionCallback(rowSelectionCallback);
		return this;
	}

	public EmfDataTableDivBuilder<R> setRowCustomizer(IEmfDataTableRowCustomizer<R> customizer) {

		this.config.setRowCustomizer(customizer);
		return this;
	}

	/**
	 * Defines a pre-fetcher to be executed before the pre-fetching methods of
	 * any column handler.
	 *
	 * @param callbackBeforePrefetch
	 *            pre-fetcher to be executed
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setCallbackBeforePrefetch(IDataTablePrefetcher<R> callbackBeforePrefetch) {

		this.config.setCallbackBeforePrefetch(callbackBeforePrefetch);
		return this;
	}

	/**
	 * Defines the default page size, i.e. the number of rows to show per page.
	 *
	 * @param pageSize
	 *            the number of rows to show per page
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setPageSize(int pageSize) {

		this.config.setPageSize(pageSize);
		return this;
	}

	/**
	 * Defines whether action buttons (like "table settings" and "export") shall
	 * be displayed in table navigation elements.
	 * <p>
	 * Navigation action buttons are displayed by default.
	 *
	 * @param hideNavigationActionButtons
	 *            <i>true</i> to hide the navigation action buttons,
	 *            <i>false</i> otherwise
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setHideNavigationActionButtons(boolean hideNavigationActionButtons) {

		this.config.setHideNavigationActionButtons(hideNavigationActionButtons);
		return this;
	}

	/**
	 * Enabled or disables the table navigation.
	 * <p>
	 * The table navigation is enabled by default. For very tiny tables that
	 * will not exceed the page size, the navigation would be useless and can be
	 * disabled, using this method.
	 *
	 * @param hideNavigation
	 *            <i>true</i> to hide the navigation; <i>false</i> otherwise
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setHideNavigation(boolean hideNavigation) {

		this.config.setHideNavigation(hideNavigation);
		return this;
	}

	/**
	 * Enabled or disables the table navigation at the bottom of the table.
	 *
	 * @param hideNavigationAtBottom
	 *            <i>true</i> to hide the navigation at the bottom of the table,
	 *            <i>false</i> otherwise
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setHideNavigationAtBottom(boolean hideNavigationAtBottom) {

		this.config.setHideNavigationAtBottom(hideNavigationAtBottom);
		return this;
	}

	/**
	 * Enabled or disables the table navigation at the top of the table.
	 *
	 * @param hideNavigationAtTop
	 *            <i>true</i> to hide the navigation at the top of the table,
	 *            <i>false</i> otherwise
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setHideNavigationAtTop(boolean hideNavigationAtTop) {

		this.config.setHideNavigationAtTop(hideNavigationAtTop);
		return this;
	}

	/**
	 * Adds a secondary header row to be displayed above the primary (regular)
	 * header row.
	 * <p>
	 * Further secondary header rows are appended below previously added
	 * secondary header rows:
	 *
	 * <pre>
	 * Secondary Header 1
	 * Secondary Header 2
	 * ...
	 * Primary Header
	 * </pre>
	 *
	 * @return a {@link EmfDataTableExtraRowDefinition} to initialize the added
	 *         secondary header row
	 */
	public EmfDataTableExtraRowDefinition<R> addSecondaryHeaderRow() {

		IEmfDataTableExtraRowColumnGroupList<R> columnGroupList = config.getSecondaryHeaderRowAccumulator().addNewColumnGroupList();
		return new EmfDataTableExtraRowDefinition<>(this, columnGroupList);
	}

	/**
	 * Adds a secondary header row to be displayed above the primary (regular)
	 * header row, and initializes it with the given {@link Consumer}.
	 * <p>
	 * Subsequently added secondary header rows are appended below previously
	 * added secondary header rows:
	 *
	 * <pre>
	 * Secondary Header 1
	 * Secondary Header 2
	 * ...
	 * Primary Header
	 * </pre>
	 *
	 * @param initializer
	 *            initializes the added row (never null)
	 * @return this {@link EmfDataTableDivBuilder}
	 */
	public EmfDataTableDivBuilder<R> addSecondaryHeaderRow(Consumer<EmfDataTableExtraRowDefinition<R>> initializer) {

		initializer.accept(addSecondaryHeaderRow());
		return this;
	}

	/**
	 * Adds a footer row to be displayed at the bottom of the table.
	 * <p>
	 * Subsequently added footer rows are appended below previously added footer
	 * rows:
	 *
	 * <pre>
	 * Footer 1
	 * Footer 2
	 * ...
	 * </pre>
	 *
	 * @return a {@link EmfDataTableExtraRowDefinition} to initialize the added
	 *         footer row
	 */
	public EmfDataTableExtraRowDefinition<R> addFooterRow() {

		IEmfDataTableExtraRowColumnGroupList<R> columnGroupList = config.getFooterRowAccumulator().addNewColumnGroupList();
		return new EmfDataTableExtraRowDefinition<>(this, columnGroupList);
	}

	/**
	 * Adds a footer row to be displayed at the bottom of the table, and
	 * initializes it with the given {@link Consumer}.
	 * <p>
	 * Subsequently added footer rows are appended below previously added footer
	 * rows:
	 *
	 * <pre>
	 * Footer 1
	 * Footer 2
	 * ...
	 * </pre>
	 *
	 * @param initializer
	 *            initializes the added row (never null)
	 * @return this {@link EmfDataTableDivBuilder}
	 */
	public EmfDataTableDivBuilder<R> addFooterRow(Consumer<EmfDataTableExtraRowDefinition<R>> initializer) {

		initializer.accept(addFooterRow());
		return this;
	}

	/**
	 * Defines the {@link IDbDatabase} to be used for loading data rows.
	 *
	 * @param databaseScopeSupplier
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setDatabaseScopeSupplier(Supplier<IDbDatabaseScope> databaseScopeSupplier) {

		this.config.setDatabaseScopeSupplier(databaseScopeSupplier);
		return this;
	}

	/**
	 * Adds a marker to be used for the {@link DomTable} in the
	 * {@link IEmfDataTableDiv} to be built.
	 *
	 * @param marker
	 *            the {@link IStaticObject} marker (never <i>null</i>)
	 */
	public EmfDataTableDivBuilder<R> addTableMarker(IStaticObject marker) {

		this.config.addTableMarker(marker);
		return this;
	}

	/**
	 * Adds a marker to be used for the {@link IEmfDataTableDiv} to be built.
	 *
	 * @param marker
	 *            the {@link IStaticObject} marker (never <i>null</i>)
	 */
	public EmfDataTableDivBuilder<R> addTableDivMarker(IStaticObject marker) {

		this.config.addTableDivMarker(marker);
		return this;
	}

	/**
	 * Defines a callback to be invoked whenever the content of the table
	 * changes.
	 * <p>
	 * Filter operations change the content of the table. Paging does not change
	 * the content of the table.
	 * <p>
	 * The given callback is invoked with all current rows in the table.
	 * <p>
	 * Warning: This can be resource-intensive for tables with many rows, if the
	 * data is not completely in-memory anyway.
	 *
	 * @param callback
	 *            the callback to be invoked whenever the content of the table
	 *            changes (never null)
	 * @return this builder
	 */
	public EmfDataTableDivBuilder<R> setCallbackAfterContentChange(Consumer<Collection<R>> callback) {

		this.config.setCallbackAfterContentChange(callback);
		return this;
	}

	/**
	 * Defines a factory for a placeholder {@link IDomNode} shown if the table
	 * contains no rows.
	 * <p>
	 * The default placeholder is {@link EmfDataTableEmptyTablePlaceholderDiv}.
	 *
	 * @param placeholderFactory
	 *            the placeholder factory (never <i>null</i>)
	 * @return this
	 */
	public EmfDataTableDivBuilder<R> setEmptyTablePlaceholderFactory(Supplier<IDomNode> placeholderFactory) {

		this.config.setEmptyTablePlaceholderFactory(placeholderFactory);
		return this;
	}

	public IEmfDataTableDiv<R> build() {

		return new EmfDataTableDiv<>(config);
	}

	public IEmfDataTableDiv<R> buildAndAppendTo(IDomParentElement parentElement) {

		return parentElement.appendChild(build());
	}
}
