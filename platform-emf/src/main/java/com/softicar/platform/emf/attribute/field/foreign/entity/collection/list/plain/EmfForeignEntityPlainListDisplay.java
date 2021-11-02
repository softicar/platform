package com.softicar.platform.emf.attribute.field.foreign.entity.collection.list.plain;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.List;
import java.util.Map;

public class EmfForeignEntityPlainListDisplay<I extends IEntity> extends DomDiv {

	public EmfForeignEntityPlainListDisplay(IDbTable<I, Integer> itemTable, String value) {

		List<Integer> idList = new EmfForeignEntityIdPlainListParser(value).parseToIdList();
		if (!idList.isEmpty()) {
			Map<Integer, I> itemMap = itemTable.getAllAsMap(idList);
			for (Integer id: idList) {
				I item = itemMap.get(id);
				if (item != null) {
					appendText(item.toDisplay());
				}
			}
		}
	}
}
