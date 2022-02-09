package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmfImportDataPreviewTable<R extends IEmfTableRow<R, P>, P, S> extends AbstractInMemoryDataTable<List<String>> {

	private final EmfImportEngine<R, P, S> engine;
	private final Collection<List<String>> rows;

	public EmfImportDataPreviewTable(EmfImportEngine<R, P, S> engine) {

		this.engine = engine;
		this.rows = new ArrayList<>();

		var index = 0;
		for (IDbField<R, ?> field: engine.getFieldsToImport()) {
			addColumn(field, index);
			index++;
		}
	}

	public void clear() {

		rows.clear();
	}

	public void addRows(Collection<List<String>> rows) {

		this.rows.addAll(rows);
	}

	private void addColumn(IDbField<R, ?> field, int index) {

		newColumn(String.class)//
			.setGetter(row -> row.get(index))
			.setTitle(engine.getFieldTitle(field))
			.addColumn();
	}

	@Override
	protected Collection<List<String>> getTableRows() {

		return rows;
	}
}
