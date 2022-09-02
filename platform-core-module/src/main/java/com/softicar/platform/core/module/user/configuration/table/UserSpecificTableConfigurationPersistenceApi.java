package com.softicar.platform.core.module.user.configuration.table;

import com.google.gson.Gson;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.emf.data.table.EmfDataTablePath;
import com.softicar.platform.emf.data.table.column.title.EmfDataTableColumnTitlesHasher;
import com.softicar.platform.emf.persistence.EmfPersistentTableConfiguration;
import com.softicar.platform.emf.persistence.IEmfPersistenceApi;
import java.util.Objects;
import java.util.Optional;

/**
 * A user-specific implementation of {@link IEmfPersistenceApi}.
 * <p>
 * Saves and restores table configurations for the current user.
 *
 * @author Alexander Schmidt
 * @author Thees Koester
 */
public class UserSpecificTableConfigurationPersistenceApi implements IEmfPersistenceApi {

	@Override
	public void savePersistentTableConfiguration(EmfDataTablePath dataTablePath, EmfPersistentTableConfiguration configuration) {

		validatePath(dataTablePath);
		validateConfiguration(configuration);

		String configurationJson = new Gson().toJson(configuration);

		try (var transaction = new DbTransaction()) {
			loadConfigurationRecord(dataTablePath.getHash(), configuration.columnTitlesHash)//
				.orElse(new AGUserSpecificTableConfiguration())
				.setTablePathHash(dataTablePath.getHash())
				.setUser(CurrentUser.get())
				.setColumnTitlesHash(configuration.columnTitlesHash)
				.setSerialization(configurationJson)
				.save();
			transaction.commit();
		}
	}

	@Override
	public Optional<EmfPersistentTableConfiguration> loadPersistentTableConfiguration(EmfDataTablePath dataTablePath,
			EmfDataTableColumnTitlesHasher columnTitlesHasher) {

		validatePath(dataTablePath);
		validateColumnTitlesHash(columnTitlesHasher);

		try (var transaction = new DbTransaction()) {
			var configurationRecord = loadConfigurationRecord(dataTablePath.getHash(), columnTitlesHasher.getHash()).orElse(null);

			Optional<EmfPersistentTableConfiguration> configuration;
			if (configurationRecord == null) {
				configuration = Optional.empty();
			} else {
				configuration = Optional.of(createUserSpecificTableConfiguration(configurationRecord.getSerialization()));
			}

			transaction.commit();

			return configuration;
		}
	}

	private EmfPersistentTableConfiguration createUserSpecificTableConfiguration(String configurationJson) {

		return new Gson().fromJson(configurationJson, EmfPersistentTableConfiguration.class);
	}

	private Optional<AGUserSpecificTableConfiguration> loadConfigurationRecord(String tablePathHash, String columnTitlesHash) {

		return AGUserSpecificTableConfiguration.TABLE//
			.createSelect(SqlSelectLock.FOR_UPDATE)
			.where(AGUserSpecificTableConfiguration.USER.isEqual(CurrentUser.get()))
			.where(AGUserSpecificTableConfiguration.TABLE_PATH_HASH.isEqual(tablePathHash))
			.where(AGUserSpecificTableConfiguration.COLUMN_TITLES_HASH.isEqual(columnTitlesHash))
			.getFirstAsOptional();
	}

	private void validatePath(EmfDataTablePath dataTablePath) {

		Objects.requireNonNull(dataTablePath);
	}

	private void validateConfiguration(EmfPersistentTableConfiguration configuration) {

		Objects.requireNonNull(configuration);
		Objects.requireNonNull(configuration.columnTitlesHash);
	}

	private void validateColumnTitlesHash(EmfDataTableColumnTitlesHasher columnTitlesHash) {

		Objects.requireNonNull(columnTitlesHash);
	}
}
