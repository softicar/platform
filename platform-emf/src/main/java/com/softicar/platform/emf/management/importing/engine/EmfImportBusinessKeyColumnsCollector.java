package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfImportBusinessKeyColumnsCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final EmfImportColumn<R, ?> foreignKeyColumn;
	private final IEmfAttribute<R, ?> foreignKeyColumnAttribute;

	public EmfImportBusinessKeyColumnsCollector(EmfImportColumn<R, ?> foreignKeyColumn, IEmfAttribute<R, ?> foreignKeyColumnAttribute) {

		this.foreignKeyColumnAttribute = foreignKeyColumnAttribute;
		this.foreignKeyColumn = foreignKeyColumn;
	}

	public void collect() {

		IEmfTable<R, P, S> targetTable = fetchTargetTable();

		for (IDbField<R, ?> targetTableBusinessKeyField: targetTable.getBusinessKey().getFields()) {

			EmfImportColumn<R, ?> column = new EmfImportColumn<>(targetTableBusinessKeyField);
			foreignKeyColumn.addParentColumn(column);

			IEmfAttribute<R, ?> attribute = targetTable.getAttribute(targetTableBusinessKeyField);

			if (attribute instanceof EmfForeignRowAttribute) {
				new EmfImportBusinessKeyColumnsCollector<>(column, attribute).collect();
			}
		}
	}

	private IEmfTable<R, P, S> fetchTargetTable() {

		IEmfTable<?, ?, ?> targetTable = ((EmfForeignRowAttribute<R, ?>) foreignKeyColumnAttribute).getTargetTable();
		return CastUtils.cast(targetTable);
	}
}
