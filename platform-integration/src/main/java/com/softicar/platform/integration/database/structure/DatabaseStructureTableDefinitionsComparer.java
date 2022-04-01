package com.softicar.platform.integration.database.structure;

import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.collections4.SetUtils;

public class DatabaseStructureTableDefinitionsComparer {

	public DatabaseStructureTableDefinitionsComparison compare(DatabaseStructureTableDefinitionList first, DatabaseStructureTableDefinitionList second) {

		Objects.requireNonNull(first);
		Objects.requireNonNull(second);

		var firstOnlyDefinitions = new ArrayList<DatabaseStructureTableDefinition>();
		var secondOnlyDefinitions = new ArrayList<DatabaseStructureTableDefinition>();
		var conflictingDefinitions = new ArrayList<DatabaseStructureTableConflictingDefinition>();

		var firstDefinitionMap = new DatabaseStructureTableDefinitionMap(first);
		var secondDefinitionMap = new DatabaseStructureTableDefinitionMap(second);

		var firstKeys = new TreeSet<>(firstDefinitionMap.keySet());
		var secondKeys = new TreeSet<>(secondDefinitionMap.keySet());

		var firstOnlyTableNames = SetUtils.difference(firstKeys, secondKeys);
		var secondOnlyTableNames = SetUtils.difference(secondKeys, firstKeys);
		var commonTableNames = SetUtils.intersection(firstKeys, secondKeys);

		for (String tableName: commonTableNames) {
			var firstDefinition = firstDefinitionMap.get(tableName);
			var secondDefinition = secondDefinitionMap.get(tableName);
			if (isConflictingCreateStatements(firstDefinition, secondDefinition)) {
				var conflictingDefinition = new DatabaseStructureTableConflictingDefinition(//
					tableName,
					firstDefinition.getCreateStatement(),
					secondDefinition.getCreateStatement());
				conflictingDefinitions.add(conflictingDefinition);
			}
		}

		firstDefinitionMap.keySet().retainAll(firstOnlyTableNames);
		firstOnlyDefinitions.addAll(firstDefinitionMap.values());

		secondDefinitionMap.keySet().retainAll(secondOnlyTableNames);
		secondOnlyDefinitions.addAll(secondDefinitionMap.values());

		return new DatabaseStructureTableDefinitionsComparison()//
			.setFirstOnlyDefinitions(firstOnlyDefinitions)
			.setSecondOnlyDefinitions(secondOnlyDefinitions)
			.setConflictingDefinitions(conflictingDefinitions);
	}

	private boolean isConflictingCreateStatements(DatabaseStructureTableDefinition firstDefinition, DatabaseStructureTableDefinition secondDefinition) {

		return !firstDefinition.getCreateStatement().equals(secondDefinition.getCreateStatement());
	}

	private class DatabaseStructureTableDefinitionMap extends TreeMap<String, DatabaseStructureTableDefinition> {

		public DatabaseStructureTableDefinitionMap(DatabaseStructureTableDefinitionList definitionList) {

			for (var definition: definitionList) {
				put(definition.getTableName(), definition);
			}
		}
	}
}
