package com.softicar.platform.emf.persistence;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.emf.data.table.column.title.EmfDataTableColumnTitlesHasher;
import java.util.Optional;

/**
 * Dummy implementation of {@link IEmfPersistenceApi} which does actually
 * nothing.
 *
 * @author Oliver Richers
 */
public class EmfDummyPersistenceApi implements IEmfPersistenceApi {

	@Override
	public void savePersistentTableConfiguration(DataTableIdentifier tableIdentifier, EmfPersistentTableConfiguration dto) {

		// nothing to do
	}

	@Override
	public Optional<EmfPersistentTableConfiguration> loadPersistentTableConfiguration(DataTableIdentifier tableIdentifier,
			EmfDataTableColumnTitlesHasher columnTitlesHash) {

		return Optional.empty();
	}
}
