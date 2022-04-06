package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfImportBusinessKeyItemsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private final EmfImportItem<R> item;

	public EmfImportBusinessKeyItemsCollector(IEmfTable<R, P, S> table, EmfImportItem<R> item) {

		this.table = table;
		this.item = item;
	}

	public void collect() {

		for (IDbField<?, ?> field: table.getBusinessKey().getFields()) {

//			var s = table.createSelect();
//			s = s.where(field.equal(null));
//			s.getOne();

			EmfImportItem<?> constituent = new EmfImportItem<>(field);
			item.addConstituent((EmfImportItem<R>) constituent);

			IEmfAttribute<?, ?> attribute = table.getAttribute((IDbField<R, ?>) field);

			if (attribute instanceof EmfForeignRowAttribute) {
				IEmfTable<?, ?, ?> emfTable = ((EmfForeignRowAttribute<R, ?>) attribute).getTargetTable();
				new EmfImportBusinessKeyItemsCollector<>(emfTable, constituent).collect();
			}
		}
	}
}
