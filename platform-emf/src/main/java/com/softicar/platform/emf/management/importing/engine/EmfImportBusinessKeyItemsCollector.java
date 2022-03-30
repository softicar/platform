package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfImportBusinessKeyItemsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private final EmfImportItem item;

	public EmfImportBusinessKeyItemsCollector(IEmfTable<R, P, S> table, EmfImportItem item) {

		this.table = table;
		this.item = item;
	}

	public void collect() {

		for (IDbField<R, ?> field: table.getBusinessKey().getFields()) {

			EmfImportItem constituent = new EmfImportItem(field);
			item.addConstituent(constituent);

			IEmfAttribute<R, ?> attribute = table.getAttribute(field);

			if (attribute instanceof EmfForeignRowAttribute) {
				EmfForeignRowAttribute<R, ?> foreignRowAttribute = CastUtils.cast(attribute);
				IEmfTable<?, ?, ?> emfTable = foreignRowAttribute.getTargetTable();
				new EmfImportBusinessKeyItemsCollector<>(emfTable, constituent).collect();
			}
		}
	}
}
