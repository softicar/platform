package com.softicar.platform.emf.persistence;

import com.softicar.platform.emf.data.table.EmfDataTablePath;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.column.title.EmfDataTableColumnTitlesHasher;
import java.util.Optional;

/**
 * Provides the interface for the EMF to access persistent (per-user)
 * configurations.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IEmfPersistenceApi {

	/**
	 * Saves the given {@link EmfPersistentTableConfiguration} for the
	 * {@link IEmfDataTable} with the specified {@link EmfDataTablePath}.
	 *
	 * @param dataTablePath
	 *            the {@link EmfDataTablePath} of the {@link IEmfDataTable} to
	 *            save the {@link EmfPersistentTableConfiguration} for (never
	 *            <i>null</i>)
	 * @param configuration
	 *            the {@link EmfPersistentTableConfiguration} to save (never
	 *            <i>null</i>)
	 */
	void savePersistentTableConfiguration(EmfDataTablePath dataTablePath, EmfPersistentTableConfiguration configuration);

	/**
	 * Loads the {@link EmfPersistentTableConfiguration} for the
	 * {@link IEmfDataTable} with the specified {@link EmfDataTablePath}.
	 *
	 * @param dataTablePath
	 *            the {@link EmfDataTablePath} of the {@link IEmfDataTable} to
	 *            load the {@link EmfPersistentTableConfiguration} for (never
	 *            <i>null</i>)
	 * @param columnTitlesHasher
	 *            a {@link EmfDataTableColumnTitlesHasher} that creates the
	 *            column titles hash
	 */
	Optional<EmfPersistentTableConfiguration> loadPersistentTableConfiguration(EmfDataTablePath dataTablePath,
			EmfDataTableColumnTitlesHasher columnTitlesHasher);
}
