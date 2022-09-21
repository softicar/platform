package com.softicar.platform.db.runtime.table.creator;

import com.softicar.platform.common.container.list.HashList;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.db.core.connection.DbConnection;
import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.db.core.connection.IDbConnection;
import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.statement.IDbStatement;
import com.softicar.platform.db.core.statement.IDbStatementExecutionListener;
import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * This observer automatically creates database tables when they are accessed.
 *
 * @author Oliver Richers
 * @author Daniel Klose
 */
public class DbAutomaticTableCreator implements IDbStatementExecutionListener {

	private final Supplier<Integer> autoIncrementSupplier;
	private final Collection<IDbTable<?, ?>> existingTables;
	private final Collection<IDbTable<?, ?>> existingTablesWithoutForeignKeys;
	private final Collection<IDbTable<?, ?>> existingTablesWithoutDefaultData;
	private final Collection<IDbTable<?, ?>> pendingTables;
	private IDbConnection connection;

	public DbAutomaticTableCreator(Supplier<Integer> autoIncrementSupplier) {

		this.autoIncrementSupplier = autoIncrementSupplier;
		this.existingTables = new HashList<>();
		this.existingTablesWithoutForeignKeys = new HashList<>();
		this.existingTablesWithoutDefaultData = new HashList<>();
		this.pendingTables = new HashList<>();
		this.connection = null;
	}

	@Override
	public void beforeExecution(IDbStatement statement) {

		Collection<IDbTable<?, ?>> tables = getDbTables(statement);
		if (isCreateTableStatement(statement)) {
			pendingTables.addAll(tables);
			tables.forEach(this::createReferencedTables);
		} else {
			tables.forEach(this::createTable);
		}
	}

	@Override
	public void afterExecution(IDbStatement statement) {

		if (isCreateTableStatement(statement)) {
			Collection<IDbTable<?, ?>> tables = getDbTables(statement);
			existingTables.addAll(tables);
			existingTablesWithoutDefaultData.addAll(tables);
			pendingTables.removeAll(tables);
		}
		if (pendingTables.isEmpty()) {
			addMissingForeignKeys();
			insertDefaultData();
		}
	}

	private static Collection<IDbTable<?, ?>> getDbTables(IDbStatement statement) {

		// We cannot use Collectors.toList() because of a bug in Java 8 compiler.
		Collection<IDbTable<?, ?>> tables = new ArrayList<>();
		statement//
			.getTables()
			.stream()
			.filter(IDbTable.class::isInstance)
			.map(IDbTable.class::cast)
			.forEach(tables::add);
		return tables;
	}

	private boolean isCreateTableStatement(IDbStatement statement) {

		return statement.getText().startsWith("CREATE TABLE");
	}

	private void addMissingForeignKeys() {

		if (!existingTablesWithoutForeignKeys.isEmpty()) {
			Collection<IDbTable<?, ?>> tablesToAddForeignKeys = new HashList<>(existingTablesWithoutForeignKeys);
			existingTablesWithoutForeignKeys.clear();
			tablesToAddForeignKeys.forEach(table -> new DbTableStructureForeignKeysAdder(table).addForeignKeys());
		}
	}

	private void insertDefaultData() {

		if (!existingTablesWithoutDefaultData.isEmpty()) {
			Collection<IDbTable<?, ?>> tablesToAddDefaultData = new HashList<>(existingTablesWithoutDefaultData);
			existingTablesWithoutDefaultData.clear();
			tablesToAddDefaultData.forEach(IDbTable::insertDefaultData);
		}
	}

	private void createReferencedTables(IDbTable<?, ?> table) {

		for (IDbTable<?, ?> referencedTable: table.getReferencedTables()) {
			if (referencedTable.getReferencedTables().stream().anyMatch(pendingTables::contains)) {
				createTableWithoutForeignKeys(referencedTable);
			} else {
				createTable(referencedTable);
			}
		}
	}

	private void createTable(IDbTable<?, ?> table) {

		if (!existingTables.contains(table) && !pendingTables.contains(table)) {
			try (var scope = new DbConnectionOverrideScope(getConnection())) {
				new DbTableStructureCreator(table)//
					.setAutoIncrement(autoIncrementSupplier.get())
					.create();
			} catch (Exception exception) {
				// print exception message to avoid silent failures
				Log.ferror(exception);
				throw exception;
			}
		}
	}

	private void createTableWithoutForeignKeys(IDbTable<?, ?> table) {

		if (!existingTables.contains(table) && !pendingTables.contains(table)) {
			try (var scope = new DbConnectionOverrideScope(getConnection())) {
				new DbTableStructureCreator(table)//
					.setAutoIncrement(autoIncrementSupplier.get())
					.setSkipForeignKeyCreation(true)
					.setIfNotExists(true)
					.create();
			}
			existingTablesWithoutForeignKeys.add(table);
		}
	}

	private IDbConnection getConnection() {

		if (connection == null) {
			this.connection = new DbConnection(DbCurrentDatabase.get());
		}
		return connection;
	}
}
