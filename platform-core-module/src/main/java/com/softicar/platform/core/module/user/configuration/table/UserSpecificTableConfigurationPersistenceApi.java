package com.softicar.platform.core.module.user.configuration.table;

import com.google.gson.Gson;
import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.emf.data.table.column.title.EmfDataTableColumnTitlesHash;
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
	public void savePersistentTableConfiguration(DataTableIdentifier tableIdentifier, EmfPersistentTableConfiguration configuration) {

		validateIdentifier(tableIdentifier);
		validateConfiguration(configuration);

		String configurationJson = new Gson().toJson(configuration);

		// TODO remove
		Log.finfo("saving: %s", configurationJson);

		try (var transaction = new DbTransaction()) {
			loadConfigurationRecord(tableIdentifier)//
				.orElse(new AGUserSpecificTableConfiguration())
				.setTableIdentifierHash(tableIdentifier.getHash())
				.setUser(CurrentUser.get())
				.setColumnTitlesHash(configuration.columnTitlesHash)
				.setSerialization(configurationJson)
				.save();
			transaction.commit();

			// TODO remove
			Log.finfo("saved.");
		}
	}

	@Override
	public Optional<EmfPersistentTableConfiguration> loadPersistentTableConfiguration(DataTableIdentifier tableIdentifier,
			EmfDataTableColumnTitlesHash columnTitlesHash) {

		// TODO remove
		Log.finfo("loading...");

		validateIdentifier(tableIdentifier);
		validateColumnTitlesHash(columnTitlesHash);

		try (var transaction = new DbTransaction()) {
			var configurationRecord = loadConfigurationRecord(tableIdentifier).orElse(null);

			Optional<EmfPersistentTableConfiguration> configuration;
			if (configurationRecord == null) {
				configuration = Optional.empty();
			} else if (!configurationRecord.getColumnTitlesHash().equals(columnTitlesHash.getHash())) {
				configurationRecord.delete();
				configuration = Optional.empty();
			} else {
				String json = configurationRecord.getSerialization();

				// TODO remove
				Log.finfo("loaded: %s", json);
				configuration = Optional.of(createUserSpecificTableConfiguration(json));
			}

			transaction.commit();

			return configuration;
		}
	}

	private EmfPersistentTableConfiguration createUserSpecificTableConfiguration(String configurationJson) {

		return new Gson().fromJson(configurationJson, EmfPersistentTableConfiguration.class);
	}

	private Optional<AGUserSpecificTableConfiguration> loadConfigurationRecord(DataTableIdentifier tableIdentifier) {

		return AGUserSpecificTableConfiguration.TABLE//
			.createSelect(SqlSelectLock.FOR_UPDATE)
			.where(AGUserSpecificTableConfiguration.TABLE_IDENTIFIER_HASH.equal(tableIdentifier.getHash()))
			.where(AGUserSpecificTableConfiguration.USER.equal(CurrentUser.get()))
			.getFirstAsOptional();
	}

	private void validateIdentifier(DataTableIdentifier tableIdentifier) {

		Objects.requireNonNull(tableIdentifier);
	}

	private void validateConfiguration(EmfPersistentTableConfiguration configuration) {

		Objects.requireNonNull(configuration);
		Objects.requireNonNull(configuration.columnTitlesHash);
	}

	private void validateColumnTitlesHash(EmfDataTableColumnTitlesHash columnTitlesHash) {

		Objects.requireNonNull(columnTitlesHash);
	}
}
