package com.softicar.platform.db.runtime.utils;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.core.table.IDbCoreTable;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDbTableStructureTest extends AbstractDbTest {

	protected void assertForeignKeyMetaData(IDbCoreTable table, boolean shouldForeignKeysBePresent) {

		DatabaseMetaData metaData = DbConnections.getMetaData();
		String databaseName = table.getFullName().getDatabaseName();
		String simpleName = table.getFullName().getSimpleName();
		try (ResultSet importedKeys = metaData.getImportedKeys(null, databaseName, simpleName)) {
			if (shouldForeignKeysBePresent) {
				if (importedKeys.next()) {
					assertNotNull(importedKeys.getString("FK_NAME"));
					assertEquals("fk", importedKeys.getString("FKCOLUMN_NAME"));
					assertEquals("database", importedKeys.getString("PKTABLE_SCHEM"));
					assertEquals("table", importedKeys.getString("PKTABLE_NAME"));
					assertEquals("id", importedKeys.getString("PKCOLUMN_NAME"));
				}
			} else {
				assertFalse(importedKeys.next());
			}
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}

	protected void assertForeignKeyMetaDataPresent(IDbCoreTable table) {

		DbTableName tableName = table.getFullName();
		DatabaseMetaData metaData = DbConnections.getMetaData();
		try (ResultSet importedKeys = metaData.getImportedKeys(null, tableName.getDatabaseName(), tableName.getSimpleName())) {
			assertTrue(importedKeys.next());
		} catch (SQLException exception) {
			throw new RuntimeException(exception);
		}
	}
}
