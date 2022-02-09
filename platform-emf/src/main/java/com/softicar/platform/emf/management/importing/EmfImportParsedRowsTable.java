package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

public class EmfImportParsedRowsTable<R extends IEmfTableRow<R, P>, P, S> extends AbstractInMemoryDataTable<R> {

	private final EmfImportEngine<R, P, S> engine;

	public EmfImportParsedRowsTable(EmfImportEngine<R, P, S> engine) {

		this.engine = engine;

		for (IDbField<R, ?> field: engine.getFieldsToImport()) {
			addFieldColumn(field);
		}
	}

	private <V> void addFieldColumn(IDbField<R, V> field) {

		newColumn(field.getValueType().getValueClass())//
			.setGetter(field::getValue)
			.setTitle(engine.getFieldTitle(field))
			.addColumn();
	}

	@Override
	protected Collection<R> getTableRows() {

		return engine.getParsedRows();
	}
}
