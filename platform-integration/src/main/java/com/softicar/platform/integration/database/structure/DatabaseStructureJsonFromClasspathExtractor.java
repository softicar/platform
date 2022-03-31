package com.softicar.platform.integration.database.structure;

import com.softicar.platform.common.code.classpath.iterable.ClasspathFileIterable;
import com.softicar.platform.common.code.classpath.metadata.ClasspathFilesMetadata;
import com.softicar.platform.db.runtime.structure.DbRuntimeTableStructure;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.finder.DbTableFinder;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.structure.utils.DbTableStructureSqlGenerator;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DatabaseStructureJsonFromClasspathExtractor {

	private final String tablePackagePrefix;

	public DatabaseStructureJsonFromClasspathExtractor(String tablePackagePrefix) {

		this.tablePackagePrefix = Objects.requireNonNull(tablePackagePrefix);
	}

	public String extractJson() {

		List<DatabaseStructureTableDefinition> definitions = loadDefinitions();
		return new DatabaseStructureTableDefinitionsConverter().convertToJson(definitions);
	}

	private List<DatabaseStructureTableDefinition> loadDefinitions() {

		return loadUniqueTables()//
			.stream()
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

	private Collection<IDbTable<?, ?>> loadUniqueTables() {

		var metadata = new ClasspathFilesMetadata(new ClasspathFileIterable());
		var tableFinder = new DbTableFinder<>(metadata, IDbTable.class, IDbTableRow.class);
		var tables = tableFinder.findAllTables().stream().filter(this::isRelevant).collect(Collectors.toList());
		var uniqueTables = new HashSet<IDbTable<?, ?>>(tables.size());
		tables.forEach(uniqueTables::add);
		return uniqueTables;
	}

	private boolean isRelevant(IDbTable<?, ?> table) {

		return table.getClass().getCanonicalName().startsWith(tablePackagePrefix + ".");
	}
}
