package com.softicar.platform.integration.database.structure;

import java.util.ArrayList;
import java.util.Collection;

public class DatabaseStructureTableDefinitionsComparison {

	private Collection<DatabaseStructureTableDefinition> firstOnlyDefinitions;
	private Collection<DatabaseStructureTableDefinition> secondOnlyDefinitions;
	private Collection<DatabaseStructureTableConflictingDefinition> conflictingDefinitions;

	public DatabaseStructureTableDefinitionsComparison() {

		this.firstOnlyDefinitions = new ArrayList<>();
		this.secondOnlyDefinitions = new ArrayList<>();
		this.conflictingDefinitions = new ArrayList<>();
	}

	public DatabaseStructureTableDefinitionsComparison setFirstOnlyDefinitions(Collection<DatabaseStructureTableDefinition> firstOnlyDefinitions) {

		this.firstOnlyDefinitions = firstOnlyDefinitions;
		return this;
	}

	public Collection<DatabaseStructureTableDefinition> getFirstOnlyDefinitions() {

		return firstOnlyDefinitions;
	}

	public DatabaseStructureTableDefinitionsComparison setSecondOnlyDefinitions(Collection<DatabaseStructureTableDefinition> secondOnlyDefinitions) {

		this.secondOnlyDefinitions = secondOnlyDefinitions;
		return this;
	}

	public Collection<DatabaseStructureTableDefinition> getSecondOnlyDefinitions() {

		return secondOnlyDefinitions;
	}

	public DatabaseStructureTableDefinitionsComparison setConflictingDefinitions(
			Collection<DatabaseStructureTableConflictingDefinition> conflictingDefinitions) {

		this.conflictingDefinitions = conflictingDefinitions;
		return this;
	}

	public Collection<DatabaseStructureTableConflictingDefinition> getConflictingDefinitions() {

		return conflictingDefinitions;
	}

	public boolean isNoDifference() {

		return firstOnlyDefinitions.isEmpty() && secondOnlyDefinitions.isEmpty() && conflictingDefinitions.isEmpty();
	}
}
