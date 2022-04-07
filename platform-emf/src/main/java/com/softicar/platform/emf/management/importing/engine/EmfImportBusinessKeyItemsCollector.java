package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfImportBusinessKeyItemsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final EmfImportItem<R, ?> item;
	private final IEmfAttribute<R, ?> fieldAttribute;

	public EmfImportBusinessKeyItemsCollector(IEmfAttribute<R, ?> fieldAttribute, EmfImportItem<R, ?> item) {

		this.fieldAttribute = fieldAttribute;
		this.item = item;
	}

	public void collect() {

		IEmfTable<R, P, S> targetTable = fetchTargetTable();

		for (IDbField<R, ?> targetTableField: targetTable.getBusinessKey().getFields()) {

			EmfImportItem<R, ?> constituent = new EmfImportItem<>(targetTableField);
			item.addConstituent(constituent);

			IEmfAttribute<R, ?> targetTableFieldAttribute = targetTable.getAttribute(targetTableField);

			if (targetTableFieldAttribute instanceof EmfForeignRowAttribute) {
				new EmfImportBusinessKeyItemsCollector<>(targetTableFieldAttribute, constituent).collect();
			}
		}
	}

	private IEmfTable<R, P, S> fetchTargetTable() {

		IEmfTable<?, ?, ?> targetTable = ((EmfForeignRowAttribute<R, ?>) fieldAttribute).getTargetTable();
		return CastUtils.cast(targetTable);
	}
}
