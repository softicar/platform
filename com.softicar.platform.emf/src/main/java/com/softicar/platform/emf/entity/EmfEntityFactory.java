package com.softicar.platform.emf.entity;

import com.softicar.platform.emf.entity.table.IEmfEntityTable;

public class EmfEntityFactory<R extends IEmfEntity<R, P>, P, S> {

	private final IEmfEntityTable<R, P, S> table;
	private final S scope;

	public EmfEntityFactory(IEmfEntityTable<R, P, S> table, S scope) {

		this.table = table;
		this.scope = scope;
	}

	public R createEntity() {

		R tableRow = table.createObject();
		table.getTableSpecialization().initializeFields(tableRow, scope);
		return tableRow;
	}
}
