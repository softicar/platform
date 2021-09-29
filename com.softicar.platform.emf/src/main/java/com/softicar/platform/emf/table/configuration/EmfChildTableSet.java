package com.softicar.platform.emf.table.configuration;

import com.softicar.platform.common.container.map.identity.IdentityHashList;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

public class EmfChildTableSet<R extends IEmfTableRow<R, ?>> {

	private final IdentityHashList<IEmfTable<?, ?, R>> tables;

	public EmfChildTableSet() {

		this.tables = new IdentityHashList<>();
	}

	public EmfChildTableSet<R> add(IEmfTable<?, ?, R> table) {

		tables.add(table);
		return this;
	}

	public Collection<IEmfTable<?, ?, R>> getChildTables() {

		return tables;
	}
}
