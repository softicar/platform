package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import java.math.BigDecimal;
import java.util.function.Supplier;

public class TestTable extends AbstractInMemoryDataTable<TestTableRow> {

	private final IDataTableColumn<TestTableRow, Integer> integerColumn;
	private final IDataTableColumn<TestTableRow, Long> longColumn;
	private final IDataTableColumn<TestTableRow, Double> doubleColumn;
	private final IDataTableColumn<TestTableRow, BigDecimal> bigDecimalColumn;
	private final IDataTableColumn<TestTableRow, String> stringColumn;
	private final IDataTableColumn<TestTableRow, Boolean> booleanColumn;
	private final IDataTableColumn<TestTableRow, Day> dayColumn;
	private final IDataTableColumn<TestTableRow, DayTime> dayTimeColumn;
	private final IDataTableColumn<TestTableRow, TestEnum> testEnumColumn;
	private final IDataTableColumn<TestTableRow, IEntity> entityColumn;
	private final Supplier<? extends Iterable<TestTableRow>> rowSupplier;
	private DataTableIdentifier identifier;

	public TestTable(Supplier<? extends Iterable<TestTableRow>> rowSupplier) {

		this.integerColumn = newColumn(Integer.class)//
			.setGetter(TestTableRow::getIntegerValue)
			.setTitle(IDisplayString.create("Integer"))
			.addColumn();
		this.longColumn = newColumn(Long.class)//
			.setGetter(TestTableRow::getLongValue)
			.setTitle(IDisplayString.create("Long"))
			.addColumn();
		this.doubleColumn = newColumn(Double.class)//
			.setGetter(TestTableRow::getDoubleValue)
			.setTitle(IDisplayString.create("Double"))
			.addColumn();
		this.bigDecimalColumn = newColumn(BigDecimal.class)//
			.setGetter(TestTableRow::getBigDecimalValue)
			.setTitle(IDisplayString.create("BigDecimal"))
			.addColumn();
		this.stringColumn = newColumn(String.class)//
			.setGetter(TestTableRow::getStringValue)
			.setTitle(IDisplayString.create("String"))
			.addColumn();
		this.booleanColumn = newColumn(Boolean.class)//
			.setGetter(TestTableRow::getBooleanValue)
			.setTitle(IDisplayString.create("Boolean"))
			.addColumn();
		this.dayColumn = newColumn(Day.class)//
			.setGetter(TestTableRow::getDayValue)
			.setTitle(IDisplayString.create("Day"))
			.addColumn();
		this.dayTimeColumn = newColumn(DayTime.class)//
			.setGetter(TestTableRow::getDayTimeValue)
			.setTitle(IDisplayString.create("DayTime"))
			.addColumn();
		this.testEnumColumn = newColumn(TestEnum.class)//
			.setGetter(TestTableRow::getTestEnumValue)
			.setTitle(IDisplayString.create("TestEnum"))
			.addColumn();
		this.entityColumn = newColumn(IEntity.class)//
			.setGetter(TestTableRow::getEntityValue)
			.setTitle(IDisplayString.create("Entity"))
			.addColumn();
		this.rowSupplier = rowSupplier;

		this.identifier = super.getIdentifier();
	}

	@Override
	protected Iterable<TestTableRow> getTableRows() {

		return rowSupplier.get();
	}

	public TestTable setIdentifier(DataTableIdentifier identifier) {

		this.identifier = identifier;
		return this;
	}

	@Override
	public DataTableIdentifier getIdentifier() {

		return identifier;
	}

	public IDataTableColumn<TestTableRow, Integer> getIntegerColumn() {

		return integerColumn;
	}

	public IDataTableColumn<TestTableRow, Long> getLongColumn() {

		return longColumn;
	}

	public IDataTableColumn<TestTableRow, Double> getDoubleColumn() {

		return doubleColumn;
	}

	public IDataTableColumn<TestTableRow, BigDecimal> getBigDecimalColumn() {

		return bigDecimalColumn;
	}

	public IDataTableColumn<TestTableRow, String> getStringColumn() {

		return stringColumn;
	}

	public IDataTableColumn<TestTableRow, Boolean> getBooleanColumn() {

		return booleanColumn;
	}

	public IDataTableColumn<TestTableRow, Day> getDayColumn() {

		return dayColumn;
	}

	public IDataTableColumn<TestTableRow, DayTime> getDayTimeColumn() {

		return dayTimeColumn;
	}

	public IDataTableColumn<TestTableRow, TestEnum> getTestEnumColumn() {

		return testEnumColumn;
	}

	public IDataTableColumn<TestTableRow, IEntity> getEntityColumn() {

		return entityColumn;
	}
}
