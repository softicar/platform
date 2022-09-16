package com.softicar.platform.integration.database.structure;

import com.softicar.platform.db.runtime.structure.DbRuntimeTableStructure;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.finder.DbTableFinder;
import com.softicar.platform.db.structure.utils.DbTableStructureSqlGenerator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DatabaseStructureJsonFromClasspathExtractor {

	private final String tablePackagePrefix;

	// Use this method to print the current db-table state to the console
//	public static void main(String[] args) {
//
//		System.out.println(new DatabaseStructureJsonFromClasspathExtractor("com.softicar.platform").extractJson());
//	}

	public DatabaseStructureJsonFromClasspathExtractor(String tablePackagePrefix) {

		this.tablePackagePrefix = Objects.requireNonNull(tablePackagePrefix);
	}

	public String extractJson() {

		List<DatabaseStructureTableDefinition> definitions = loadDefinitions();
		return new DatabaseStructureTableDefinitionsConverter().convertToJson(definitions);
	}

	private List<DatabaseStructureTableDefinition> loadDefinitions() {

		return new DbTableFinder()//
			.findAllTables()
			.stream()
			.filter(this::isRelevant)
			.map(this::createDefinition)
			.collect(Collectors.toList());
	}

	private DatabaseStructureTableDefinition createDefinition(IDbTable<?, ?> table) {

		return new DatabaseStructureTableDefinition(//
			table.getFullName().getQuoted(),
			determineCreateStatement(table));
	}

	private String determineCreateStatement(IDbTable<?, ?> table) {

		var structure = new DbRuntimeTableStructure(table);
		var sqlGenerator = new DbTableStructureSqlGenerator(structure);
		return sqlGenerator.getCreateTableStatement();
	}

	private boolean isRelevant(IDbTable<?, ?> table) {

		return table.getClass().getCanonicalName().startsWith(tablePackagePrefix + ".");
	}
}
