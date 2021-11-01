package com.softicar.platform.emf.log.viewer.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.log.viewer.EmfLogAttributeFilter;
import com.softicar.platform.emf.log.viewer.EmfLogDisplayFactoryWrapper;
import com.softicar.platform.emf.log.viewer.item.EmfLogItem;
import com.softicar.platform.emf.log.viewer.item.EmfLogItemLoader;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

class EmfLogDataTable<R extends IEmfTableRow<R, ?>> extends AbstractInMemoryDataTable<EmfLogDataTableRow<R>> {

	private final Collection<ColumnHandlerApplier<?>> columnHandlerAppliers;
	private final IDataTableColumn<EmfLogDataTableRow<R>, DayTime> atColumn;
	private final Collection<EmfLogItem<R>> logItems;
	private final Collection<IEmfAttribute<R, ?>> loggedAttributes;

	public EmfLogDataTable(R tableRow) {

		this.columnHandlerAppliers = new ArrayList<>();

		this.atColumn = newColumn(DayTime.class)//
			.setComparator(DayTime::compareTo)
			.setGetter(row -> row.getAt())
			.setTitle(EmfI18n.UPDATED_AT)
			.addColumn();

		newColumn(IBasicUser.class)//
			.setComparator(IBasicUser::compareTo)
			.setGetter(row -> row.getBy())
			.setTitle(EmfI18n.UPDATED_BY)
			.addColumn();

		EmfLogItemLoader<R> loader = new EmfLogItemLoader<R>().load(tableRow);
		this.logItems = loader.getLogItems();
		this.loggedAttributes = loader.getLoggedAttributes();

		addColumns(tableRow);
	}

	@Override
	protected Iterable<EmfLogDataTableRow<R>> getTableRows() {

		return logItems//
			.stream()
			.map(EmfLogDataTableRow::new)
			.collect(Collectors.toList());
	}

	public EmfDataTableDivBuilder<EmfLogDataTableRow<R>> applyCustomization(EmfDataTableDivBuilder<EmfLogDataTableRow<R>> divBuilder) {

		columnHandlerAppliers.forEach(it -> it.apply(divBuilder));
		divBuilder.setOrderBy(atColumn, OrderDirection.DESCENDING);
		return divBuilder;
	}

	private void addColumns(R tableRow) {

		new EmfLogAttributeFilter<>(tableRow)//
			.getDisplayableAttributes()
			.stream()
			.filter(loggedAttributes::contains)
			.forEach(this::addColumn);
	}

	private <V> void addColumn(IEmfAttribute<R, V> attribute) {

		IDataTableColumn<EmfLogDataTableRow<R>, V> column = newColumn(attribute.getValueClass())//
			.setComparator(attribute.getValueComparator())
			.setGetter(row -> attribute.getValue(row.getImpermanentEntity()))
			.setTitle(attribute.getTitle())
			.addColumn();
		AttributeColumnHandler<V> columnHandler = new AttributeColumnHandler<>(attribute);
		addColumnHandlerApplier(column, columnHandler);
	}

	private <V> void addColumnHandlerApplier(IDataTableColumn<EmfLogDataTableRow<R>, V> column, AttributeColumnHandler<V> columnHandler) {

		columnHandlerAppliers.add(new ColumnHandlerApplier<>(column, columnHandler));
	}

	private class AttributeColumnHandler<V> extends EmfDataTableRowBasedColumnHandler<EmfLogDataTableRow<R>, V> {

		private final IEmfAttribute<R, V> attribute;

		public AttributeColumnHandler(IEmfAttribute<R, V> attribute) {

			this.attribute = attribute;
		}

		@Override
		public void buildCell(IEmfDataTableCell<EmfLogDataTableRow<R>, V> cell, EmfLogDataTableRow<R> row) {

			R tableRow = row.getImpermanentEntity();
			Optional<IDomElement> displayElement = new EmfLogDisplayFactoryWrapper().createDisplay(() -> attribute.createTabularDisplay(tableRow));
			if (displayElement.isPresent()) {
				cell.appendChild(displayElement.get());
			} else {
				super.buildCell(cell, row);
			}
		}
	}

	private class ColumnHandlerApplier<V> {

		private final IDataTableColumn<EmfLogDataTableRow<R>, V> column;
		private final AttributeColumnHandler<V> columnHandler;

		public ColumnHandlerApplier(IDataTableColumn<EmfLogDataTableRow<R>, V> column, AttributeColumnHandler<V> columnHandler) {

			this.column = column;
			this.columnHandler = columnHandler;
		}

		public void apply(EmfDataTableDivBuilder<EmfLogDataTableRow<R>> divBuilder) {

			divBuilder.setColumnHandler(column, columnHandler);
		}
	}
}
