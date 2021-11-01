package com.softicar.platform.db.core.statement;

import com.softicar.platform.db.core.metadata.DbMetadataRequester;
import com.softicar.platform.db.core.metadata.DbMetadataSchema;
import com.softicar.platform.db.core.metadata.DbMetadataTable;

public class DbDdlStatements {

	public static DbMetadataSchema createSchema(String schemaName) {

		new DbStatement()//
			.addText("CREATE SCHEMA %s", schemaName)
			.execute();
		return new DbMetadataRequester().getSchema(schemaName).get();
	}

	public static DbMetadataTable createTable(DbMetadataSchema schema, String tableName, String definition) {

		new DbStatement()//
			.addText("CREATE TABLE ")
			.addText("`%s`.`%s` ", schema.getSchemaName(), tableName)
			.addText("(%s)", definition)
			.execute();
		return new DbMetadataRequester().getTable(schema, tableName).get();
	}

	public static DbMetadataTable createView(DbMetadataSchema schema, String tableName, String query) {

		new DbStatement()//
			.addText("CREATE VIEW ")
			.addText("`%s`.`%s` ", schema.getSchemaName(), tableName)
			.addText("AS %s", query)
			.execute();
		return new DbMetadataRequester().getTable(schema, tableName).get();
	}
}
