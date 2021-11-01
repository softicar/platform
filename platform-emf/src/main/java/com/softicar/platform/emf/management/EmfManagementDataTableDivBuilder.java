package com.softicar.platform.emf.management;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Objects;
import java.util.function.Consumer;

public class EmfManagementDataTableDivBuilder<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> entityTable;
	private final EmfManagementDataTable<R, ?, S> dataTable;
	private final EmfDataTableDivBuilder<R> builder;
	private Consumer<EmfDataTableDivBuilder<R>> dataTableDivCustomizer;

	public EmfManagementDataTableDivBuilder(IEmfTable<R, P, S> entityTable, EmfManagementDataTable<R, ?, S> dataTable) {

		this.entityTable = entityTable;
		this.dataTable = dataTable;
		this.builder = new EmfDataTableDivBuilder<>(dataTable);
		this.dataTableDivCustomizer = Consumers.noOperation();
	}

	public EmfManagementDataTableDivBuilder<R, P, S> setDataTableDivCustomizer(Consumer<EmfDataTableDivBuilder<R>> dataTableDivCustomizer) {

		this.dataTableDivCustomizer = Objects.requireNonNull(dataTableDivCustomizer);
		return this;
	}

	public IEmfDataTableDiv<R> build() {

		builder.setActionColumnHandler(new EmfManagementActionColumnHandler<>(entityTable));
		builder.setRowCustomizer(new EmfManagementDataTableRowCustomizer<>());
		dataTableDivCustomizer.accept(builder);
		dataTable.setColumnHandlers(builder);
		entityTable.getManagementConfiguration().getOrderBys().forEach(this::addOrderByToBuilder);
		entityTable.getManagementConfiguration().getRowCustomizer().ifPresent(builder::setRowCustomizer);
		return builder.build();
	}

	private void addOrderByToBuilder(Pair<IEmfAttribute<R, ?>, OrderDirection> orderBy) {

		dataTable.getFieldStrategy(orderBy.getFirst()).ifPresent(strategy -> strategy.addOrderBy(builder, orderBy.getSecond()));
	}
}
