package com.softicar.platform.integration.database.structure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DatabaseStructureTableDefinitionsConverter {

	public String convertToJson(List<DatabaseStructureTableDefinition> definitionList) {

		return new GsonBuilder()//
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.create()
			.toJson(getSortedDefinitions(definitionList));
	}

	public DatabaseStructureTableDefinitionList convertToDefinitionList(String json) {

		var list = new Gson().fromJson(json, DatabaseStructureTableDefinitionList.class);
		return Optional.ofNullable(list).orElse(new DatabaseStructureTableDefinitionList());
	}

	private List<DatabaseStructureTableDefinition> getSortedDefinitions(List<DatabaseStructureTableDefinition> definitions) {

		return definitions//
			.stream()
			.sorted(this::compareDefinitions)
			.collect(Collectors.toList());
	}

	private int compareDefinitions(DatabaseStructureTableDefinition first, DatabaseStructureTableDefinition second) {

		return first.getTableName().compareTo(second.getTableName());
	}
}
