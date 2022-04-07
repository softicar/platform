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

	public EmfImportBusinessKeyColumnsCollector(IEmfAttribute<R, ?> fieldAttribute, EmfImportColumn<R, ?> column) {

		this.fieldAttribute = fieldAttribute;
		this.column = column;
	}

	public void collect() {

		IEmfTable<R, P, S> targetTable = fetchTargetTable();

		for (IDbField<R, ?> targetTableField: targetTable.getBusinessKey().getFields()) {

			EmfImportColumn<R, ?> foreignKeyColumn = new EmfImportColumn<>(targetTableField);
			column.addForeignKeyColumn(foreignKeyColumn);

			IEmfAttribute<R, ?> targetTableFieldAttribute = targetTable.getAttribute(targetTableField);

			if (targetTableFieldAttribute instanceof EmfForeignRowAttribute) {
				new EmfImportBusinessKeyColumnsCollector<>(targetTableFieldAttribute, foreignKeyColumn).collect();
			}
		}
	}

	private IEmfTable<R, P, S> fetchTargetTable() {

		IEmfTable<?, ?, ?> targetTable = ((EmfForeignRowAttribute<R, ?>) fieldAttribute).getTargetTable();
		return CastUtils.cast(targetTable);
	}
}
