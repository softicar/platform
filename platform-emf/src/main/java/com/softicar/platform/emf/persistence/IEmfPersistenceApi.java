package com.softicar.platform.emf.persistence;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.emf.data.table.column.title.EmfDataTableColumnTitlesHasher;
import java.util.Optional;

/**
 * Provides the interface for the EMF to access persistent (per-user)
 * configurations.
 *
 * @author Oliver Richers
 */
public interface IEmfPersistenceApi {

	/**
	 * Saves the given {@link EmfPersistentTableConfiguration} for the specified
	 * {@link DataTableIdentifier}.
	 *
	 * @param tableIdentifier
	 *            the {@link DataTableIdentifier} (never <i>null</i>)
	 * @param configuration
	 *            the {@link EmfPersistentTableConfiguration} to save (never
	 *            <i>null</i>)
	 */
	void savePersistentTableConfiguration(DataTableIdentifier tableIdentifier, EmfPersistentTableConfiguration configuration);

	/**
	 * Loads the {@link EmfPersistentTableConfiguration} for the specified
	 * {@link DataTableIdentifier}.
	 *
	 * @param tableIdentifier
	 *            the {@link DataTableIdentifier} to load the
	 *            {@link EmfPersistentTableConfiguration} for (never
	 *            <i>null</i>)
	 * @param columnTitlesHasher
	 *            a {@link EmfDataTableColumnTitlesHasher} that creates the
	 *            column titles hash
	 */
	Optional<EmfPersistentTableConfiguration> loadPersistentTableConfiguration(DataTableIdentifier tableIdentifier,
			EmfDataTableColumnTitlesHasher columnTitlesHasher);
}
