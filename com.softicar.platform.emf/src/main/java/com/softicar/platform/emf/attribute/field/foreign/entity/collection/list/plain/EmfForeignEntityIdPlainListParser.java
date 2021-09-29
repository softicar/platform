package com.softicar.platform.emf.attribute.field.foreign.entity.collection.list.plain;

import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EmfForeignEntityIdPlainListParser {

	private final String idList;

	public EmfForeignEntityIdPlainListParser(String idList) {

		this.idList = idList;
	}

	public void parseToIds(Consumer<Integer> idConsumer) {

		for (String idString: idList.split(",")) {
			IntegerParser.parse(idString).ifPresent(idConsumer);
		}
	}

	public List<Integer> parseToIdList() {

		List<Integer> itemIds = new ArrayList<>();
		parseToIds(itemIds::add);
		return itemIds;
	}

	public <R extends IEmfTableRow<R, ?>> List<R> parseToEntityList(IDbTable<R, Integer> entityTable) {

		List<Integer> orderedIds = parseToIdList();
		Map<Integer, R> map = entityTable.getAllAsMap(orderedIds);
		return orderedIds.stream().map(map::get).filter(Objects::nonNull).collect(Collectors.toList());
	}
}
