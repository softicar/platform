package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfImportBusinessKeyColumnsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final EmfImportColumn<R, ?> column;
	private final IEmfAttribute<R, ?> fieldAttribute;

	public EmfImportBusinessKeyColumnsCollector(EmfImportColumn<R, ?> column, IEmfAttribute<R, ?> fieldAttribute) {

		this.fieldAttribute = fieldAttribute;
		this.column = column;
	}

	public void collect() {

		IEmfTable<R, P, S> targetTable = fetchTargetTable();

		for (IDbField<R, ?> targetTableBusinessKeyField: targetTable.getBusinessKey().getFields()) {

			EmfImportColumn<R, ?> parentColumn = new EmfImportColumn<>(targetTableBusinessKeyField);
			column.addParentColumn(parentColumn);

			IEmfAttribute<R, ?> parentFieldAttribute = targetTable.getAttribute(targetTableBusinessKeyField);

			if (parentFieldAttribute instanceof EmfForeignRowAttribute) {
				new EmfImportBusinessKeyColumnsCollector<>(parentColumn, parentFieldAttribute).collect();
			}
		}
	}

	private IEmfTable<R, P, S> fetchTargetTable() {

		IEmfTable<?, ?, ?> targetTable = ((EmfForeignRowAttribute<R, ?>) fieldAttribute).getTargetTable();
		return CastUtils.cast(targetTable);
	}
}
