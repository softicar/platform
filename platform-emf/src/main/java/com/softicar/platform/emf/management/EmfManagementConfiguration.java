package com.softicar.platform.emf.management;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.data.table.IEmfDataTableRowCustomizer;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmfManagementConfiguration<R extends IEmfTableRow<R, ?>> implements IEmfManagementConfiguration<R> {

	private final IEmfTable<R, ?, ?> table;
	private final List<Pair<IEmfAttribute<R, ?>, OrderDirection>> orderBy;
	private Optional<IEmfDataTableRowCustomizer<R>> rowStyler;

	public EmfManagementConfiguration(IEmfTable<R, ?, ?> table) {

		this.table = table;
		this.orderBy = new ArrayList<>();
		this.rowStyler = Optional.empty();
	}

	@Override
	public List<Pair<IEmfAttribute<R, ?>, OrderDirection>> getOrderBys() {

		return orderBy;
	}

	public EmfManagementConfiguration<R> addOrderBy(IEmfAttribute<R, ?> attribute, OrderDirection direction) {

		orderBy.add(new Pair<>(attribute, direction));
		return this;
	}

	public EmfManagementConfiguration<R> addOrderBy(IDbField<R, ?> field, OrderDirection direction) {

		return addOrderBy(table.getAttribute(field), direction);
	}

	@Override
	public Optional<IEmfDataTableRowCustomizer<R>> getRowCustomizer() {

		return rowStyler;
	}

	public void setRowStyler(IEmfDataTableRowCustomizer<R> rowStyler) {

		this.rowStyler = Optional.of(rowStyler);
	}
}
