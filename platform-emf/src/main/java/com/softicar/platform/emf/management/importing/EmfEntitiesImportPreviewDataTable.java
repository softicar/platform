package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;

public class EmfEntitiesImportPreviewDataTable<R extends IEmfTableRow<R, P>, P, S> extends AbstractInMemoryDataTable<R> {

	private final IEmfTable<R, P, S> entityTable;
	private final Collection<R> rows;

	public EmfEntitiesImportPreviewDataTable(IEmfTable<R, P, S> entityTable) {

		this.entityTable = entityTable;
		this.rows = new ArrayList<>();

		for (IDbField<R, ?> field: entityTable.getAllFields()) {
			addFieldColumn(field);
		}
	}

	public void clear() {

		rows.clear();
	}

	public void addRows(Collection<R> rows) {

		this.rows.addAll(rows);
	}

	private <V> void addFieldColumn(IDbField<R, V> field) {

		newColumn(field.getValueType().getValueClass())//
			.setGetter(field::getValue)
			.setTitle(entityTable.getAttribute(field).getTitle())
			.addColumn();
	}

	@Override
	protected Collection<R> getTableRows() {

		return rows;
	}
}
