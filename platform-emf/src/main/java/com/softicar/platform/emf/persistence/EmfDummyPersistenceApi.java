package com.softicar.platform.emf.persistence;

import com.softicar.platform.emf.data.table.EmfDataTablePath;
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
	public void savePersistentTableConfiguration(EmfDataTablePath dataTablePath, EmfPersistentTableConfiguration configuration) {

		// nothing to do
	}

	@Override
	public Optional<EmfPersistentTableConfiguration> loadPersistentTableConfiguration(EmfDataTablePath dataTablePath,
			EmfDataTableColumnTitlesHasher columnTitlesHasher) {

		return Optional.empty();
	}
}
