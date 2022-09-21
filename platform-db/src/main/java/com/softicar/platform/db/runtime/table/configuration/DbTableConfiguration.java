package com.softicar.platform.db.runtime.table.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.table.row.getter.DbTableRowGetterStrategy;
import com.softicar.platform.db.runtime.table.row.getter.IDbTableRowGetterStrategy;
import java.util.Objects;

/**
 * Default implementation of {@link IDbTableConfiguration}.
 *
 * @author Oliver Richers
 */
public class DbTableConfiguration<R extends IDbTableRow<R, P>, P> implements IDbTableConfiguration<R, P> {

	private static final IDbTableDataInitializer DEFAULT_DATA_INITIALIZER = new DbTableDataInitializer();
	private IDbTableDataInitializer dataInitializer;
	private IDbTableRowGetterStrategy<R, P> tableRowGetterStrategy;
	private IDisplayString title;
	private IDisplayString pluralTitle;

	public DbTableConfiguration(IDbTableBuilder<R, P> tableBuilder) {

		this.dataInitializer = DEFAULT_DATA_INITIALIZER;
		this.tableRowGetterStrategy = new DbTableRowGetterStrategy<>(tableBuilder.getTable());
		this.title = tableBuilder.getTitle();
		this.pluralTitle = tableBuilder.getPluralTitle();
	}

	// ------------------------------ getters ------------------------------ //

	@Override
	public IDbTableDataInitializer getDataInitializer() {

		return dataInitializer;
	}

	@Override
	public IDbTableRowGetterStrategy<R, P> getTableRowGetterStrategy() {

		return tableRowGetterStrategy;
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public IDisplayString getPluralTitle() {

		return pluralTitle;
	}

	// ------------------------------ setters ------------------------------ //

	public void setDataInitializer(IDbTableDataInitializer dataInitializer) {

		this.dataInitializer = dataInitializer;
	}

	public void setTableRowGetterStrategy(IDbTableRowGetterStrategy<R, P> tableRowGetterStrategy) {

		this.tableRowGetterStrategy = tableRowGetterStrategy;
	}

	public void setTitle(IDisplayString title) {

		this.title = Objects.requireNonNull(title);
	}

	public void setPluralTitle(IDisplayString pluralTitle) {

		this.pluralTitle = Objects.requireNonNull(pluralTitle);
	}
}
