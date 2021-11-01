package com.softicar.platform.emf.data.table.export.engine.factory;

import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.emf.data.table.export.engine.ITableExportEngine;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportEngineConfiguration;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResultContainer;
import java.util.Collection;

/**
 * Common interface of factories to create {@link ITableExportEngine} instances.
 *
 * @param <ET>
 *            the type of the {@link ITableExportEngine} to create
 * @author Alexander Schmidt
 */
public interface ITableExportEngineFactory<ET extends ITableExportEngine<?>> extends IDisplayable {

	/**
	 * @return a new instance of an {@link ITableExportEngine} (never null)
	 */
	ET create();

	/**
	 * Checks preconditions for the instantiation of an
	 * {@link ITableExportEngine}.
	 *
	 * @param tableModel
	 *            a collection of {@link TableExportTableModel} instances for
	 *            which the preconditions should be checked (never null)
	 * @return a {@link TableExportPreconditionResultContainer} containing the
	 *         results of the precondition check (may be null)
	 */
	TableExportPreconditionResultContainer checkPreconditions(Collection<TableExportTableModel> tableModel);

	/**
	 * Returns the {@link TableExportEngineConfiguration} which this factory
	 * will pass to the engine it creates.
	 *
	 * @return the {@link TableExportEngineConfiguration} (never null)
	 */
	TableExportEngineConfiguration getEngineConfiguration();
}
