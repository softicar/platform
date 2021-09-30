package com.softicar.platform.db.runtime.table.logic.save;

import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTable;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class DbTableRowSaverSlaveList extends AbstractList<DbTableRowSaverSlave<?, ?>> {

	private final List<DbTableRowSaverSlave<?, ?>> slaves;

	public <R extends IDbTableRow<R, P>, P> DbTableRowSaverSlaveList(IDbTable<R, P> table, Collection<R> rows) {

		this.slaves = new ArrayList<>();
		addSlaves(table, rows);
		Collections.reverse(slaves);
	}

	@Override
	public DbTableRowSaverSlave<?, ?> get(int index) {

		return slaves.get(index);
	}

	@Override
	public int size() {

		return slaves.size();
	}

	public List<DbTableRowSaverSlave<?, ?>> getSlaves() {

		return slaves;
	}

	private <R extends IDbTableRow<R, P>, P> void addSlaves(IDbTable<R, P> table, Collection<R> rows) {

		slaves.add(new DbTableRowSaverSlave<>(table, rows));
		addBaseSaverSlave(table, rows);
	}

	private <R extends IDbTableRow<R, P>, P> void addBaseSaverSlave(IDbTable<R, P> table, Collection<R> rows) {

		table.ifSubObjectTable(subObjectTable -> addBaseSaverSlave(subObjectTable, rows));
	}

	private <R, B extends IDbEntity<B, Q>, Q> void addBaseSaverSlave(IDbSubObjectTable<? super R, B, Q> table, Collection<R> rows) {

		IDbForeignRowField<? super R, B, Q> baseField = table.getPrimaryKeyField();
		addSlaves(baseField.getTargetTable(), getBases(baseField, rows));
	}

	private <R, B extends IDbEntity<B, Q>, Q> Collection<B> getBases(IDbForeignRowField<? super R, B, Q> baseField, Collection<R> rows) {

		return rows//
			.stream()
			.map(baseField::getValueDirectly)
			.filter(base -> !base.stub())
			.collect(Collectors.toList());
	}
}
