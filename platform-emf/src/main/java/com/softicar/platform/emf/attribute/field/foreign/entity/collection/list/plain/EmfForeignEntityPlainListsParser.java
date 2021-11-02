package com.softicar.platform.emf.attribute.field.foreign.entity.collection.list.plain;

import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class EmfForeignEntityPlainListsParser {

	private final Collection<String> idLists;

	public EmfForeignEntityPlainListsParser(Collection<String> idLists) {

		this.idLists = idLists;
	}

	public <E> Map<Integer, E> parseToEntityMap(IDbTable<E, Integer> entityTable) {

		Set<Integer> entityIds = new TreeSet<>();
		for (String idList: idLists) {
			new EmfForeignEntityIdPlainListParser(idList).parseToIds(entityIds::add);
		}
		return entityTable.getAllAsMap(entityIds);
	}
}
