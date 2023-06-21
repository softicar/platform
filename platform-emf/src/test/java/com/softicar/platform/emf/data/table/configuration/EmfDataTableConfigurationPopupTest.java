package com.softicar.platform.emf.data.table.configuration;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.configuration.testing.EmfDataTableConfigurationPopupAsserter;
import com.softicar.platform.emf.data.table.configuration.testing.EmfDataTableConfigurationPopupTestInteractor;
import com.softicar.platform.emf.data.table.configuration.testing.EmfDataTableConfigurationTableAsserter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class EmfDataTableConfigurationPopupTest extends AbstractEmfTest {

	private final TestTable dataTable;
	private final EmfDataTableConfigurationPopupTestInteractor interactor;
	private final EmfDataTableConfigurationTableAsserter tableAsserter;
	private final EmfDataTableConfigurationPopupAsserter popupAsserter;

	public EmfDataTableConfigurationPopupTest() {

		this.dataTable = new TestTable();
		setNodeSupplier(() -> new EmfDataTableDivBuilder<>(dataTable).build());

		this.interactor = new EmfDataTableConfigurationPopupTestInteractor(this);
		this.tableAsserter = new EmfDataTableConfigurationTableAsserter(this, EmfTestMarker.DATA_TABLE);
		this.popupAsserter = new EmfDataTableConfigurationPopupAsserter(this);
	}

	// -------------------------------- page size -------------------------------- //

	@Test
	public void testPageSizeUnchanged() {

		interactor//
			.openConfiguration()
			.clickApply();
		tableAsserter.assertRows(5);

		interactor.openConfiguration();
		popupAsserter.assertPageSize("20");
	}

	@Test
	public void testPageSizeChanged() {

		interactor//
			.openConfiguration()
			.enterPageSize("2")
			.clickApply();
		tableAsserter.assertRows(2);

		interactor.openConfiguration();
		popupAsserter.assertPageSize("2");
	}

	@Test
	public void testPageSizeChangedToMany() {

		interactor//
			.openConfiguration()
			.enterPageSize("5000")
			.clickApply();
		tableAsserter.assertRows(5);

		interactor.openConfiguration();
		popupAsserter.assertPageSize("5000");
	}

	@Test
	public void testPageSizeChangedToEmpty() {

		interactor//
			.openConfiguration()
			.enterPageSize("")
			.clickApply();
		tableAsserter.assertRows(5);

		interactor.openConfiguration();
		popupAsserter.assertPageSize("20");
	}

	@Test
	public void testPageSizeChangedToZero() {

		interactor//
			.openConfiguration()
			.enterPageSize("0")
			.clickApply();
		tableAsserter.assertRows(1);

		interactor.openConfiguration();
		popupAsserter.assertPageSize("1");
	}

	@Test
	public void testPageSizeChangedToNegative() {

		interactor//
			.openConfiguration()
			.enterPageSize("-1")
			.clickApply();
		tableAsserter.assertRows(1);

		interactor.openConfiguration();
		popupAsserter.assertPageSize("1");
	}

	@Test
	public void testPageSizeAllWithFewRecords() {

		dataTable//
			.clearRows()
			.addDummyRows(5);

		interactor//
			.openConfiguration()
			.clickPageSizeAll();
		// when clicking "all", page size won't fall below the default value
		popupAsserter.assertPageSize("20");

		interactor.clickApply();
		tableAsserter.assertRows(5);

		interactor.openConfiguration();
		popupAsserter.assertPageSize("20");
	}

	@Test
	public void testPageSizeAllWithManyRecords() {

		dataTable//
			.clearRows()
			.addDummyRows(100);

		interactor//
			.openConfiguration()
			.clickPageSizeAll();
		popupAsserter.assertPageSize("100");

		interactor.clickApply();
		tableAsserter.assertRows(100);

		interactor.openConfiguration();
		popupAsserter.assertPageSize("100");
	}

	// -------------------------------- column hiding -------------------------------- //

	@Test
	public void testHideSomeColumns() {

		interactor//
			.openConfiguration()
			.toggle("ID")
			.toggle("Amount")
			.clickApply();
		tableAsserter.assertDisplayedColumns("Name", "Day");

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(false, "ID")
			.assertRow(true, "Name")
			.assertRow(false, "Amount")
			.assertRow(true, "Day")
			.assertNoMoreRows();
	}

	@Test
	public void testHideSomeColumnsAndInvert() {

		interactor//
			.openConfiguration()
			.toggle("ID")
			.toggle("Amount")
			.clickRowSelectionInvert()
			.clickApply();
		tableAsserter.assertDisplayedColumns("ID", "Amount");

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(false, "Name")
			.assertRow(true, "Amount")
			.assertRow(false, "Day")
			.assertNoMoreRows();
	}

	@Test
	public void testHideSomeColumnsAndSelectAll() {

		interactor//
			.openConfiguration()
			.toggle("ID")
			.toggle("Amount")
			.clickRowSelectionAll()
			.clickApply();
		tableAsserter.assertDisplayedColumns("ID", "Name", "Amount", "Day");

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Day")
			.assertNoMoreRows();
	}

	@Test
	public void testHideSomeColumnsAndSelectNone() {

		interactor//
			.openConfiguration()
			.toggle("ID")
			.toggle("Amount")
			.clickRowSelectionNone()
			.clickApply();
		tableAsserter.assertDisplayedColumns("");

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(false, "ID")
			.assertRow(false, "Name")
			.assertRow(false, "Amount")
			.assertRow(false, "Day")
			.assertNoMoreRows();
	}

	@Test
	public void testHideAllColumns() {

		interactor//
			.openConfiguration()
			.toggle("ID")
			.toggle("Name")
			.toggle("Amount")
			.toggle("Day")
			.clickApply();
		tableAsserter.assertDisplayedColumns("");

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(false, "ID")
			.assertRow(false, "Name")
			.assertRow(false, "Amount")
			.assertRow(false, "Day")
			.assertNoMoreRows();
	}

	// -------------------------------- column positions -------------------------------- //

	@Test
	public void testMoveColumnPositions() {

		interactor//
			.openConfiguration()
			.moveDown("Amount")
			.moveUp("Day")
			.selectPosition("ID", 3)
			.clickApply();
		tableAsserter.assertDisplayedColumns("Day", "Name", "ID", "Amount");

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "Day")
			.assertRow(true, "Name")
			.assertRow(true, "ID")
			.assertRow(true, "Amount")
			.assertNoMoreRows();
	}

	@Test
	public void testMoveColumnPositionOnUpperEdge() {

		interactor//
			.openConfiguration()
			.moveUp("ID")
			.clickApply();
		tableAsserter.assertDisplayedColumns("ID", "Name", "Amount", "Day");

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Day")
			.assertNoMoreRows();
	}

	@Test
	public void testMoveColumnPositionOnLowerEdge() {

		interactor//
			.openConfiguration()
			.moveDown("Day")
			.clickApply();
		tableAsserter.assertDisplayedColumns("ID", "Name", "Amount", "Day");

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Day")
			.assertNoMoreRows();
	}

	// -------------------------------- column ordering -------------------------------- //

	@Test
	public void testOrderByColumns() {

		interactor//
			.openConfiguration()
			.selectOrdering("Name", OrderDirection.ASCENDING, 2)
			.selectOrdering("Amount", OrderDirection.DESCENDING, 1)
			.selectOrdering("ID", OrderDirection.ASCENDING, 3)
			.clickApply();
		tableAsserter//
			.assertDisplayedColumns("ID", "Name", "Amount", "Day")
			.assertNextOrdering(dataTable.getAmountColumn(), OrderDirection.DESCENDING)
			.assertNextOrdering(dataTable.getNameColumn(), OrderDirection.ASCENDING)
			.assertNextOrdering(dataTable.getIdColumn(), OrderDirection.ASCENDING)
			.assertNoMoreOrdering();

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "ID", OrderDirection.ASCENDING, 3)
			.assertRow(true, "Name", OrderDirection.ASCENDING, 2)
			.assertRow(true, "Amount", OrderDirection.DESCENDING, 1)
			.assertRow(true, "Day")
			.assertNoMoreRows();
	}

	@Test
	public void testOrderByColumnsWithoutPriority() {

		interactor//
			.openConfiguration()
			.selectOrderDirection("Name", OrderDirection.ASCENDING)
			.selectOrderDirection("Amount", OrderDirection.DESCENDING)
			.selectOrderDirection("ID", OrderDirection.ASCENDING)
			.clickApply();
		tableAsserter//
			.assertDisplayedColumns("ID", "Name", "Amount", "Day")
			.assertNextOrdering(dataTable.getIdColumn(), OrderDirection.ASCENDING)
			.assertNextOrdering(dataTable.getNameColumn(), OrderDirection.ASCENDING)
			.assertNextOrdering(dataTable.getAmountColumn(), OrderDirection.DESCENDING)
			.assertNoMoreOrdering();

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "ID", OrderDirection.ASCENDING, 1)
			.assertRow(true, "Name", OrderDirection.ASCENDING, 2)
			.assertRow(true, "Amount", OrderDirection.DESCENDING, 3)
			.assertRow(true, "Day")
			.assertNoMoreRows();
	}

	@Test
	public void testOrderByColumnsWithoutDirection() {

		interactor//
			.openConfiguration()
			.selectOrderPriority("Name", 2)
			.selectOrderPriority("Amount", 1)
			.selectOrderPriority("ID", 3)
			.clickApply();
		tableAsserter//
			.assertDisplayedColumns("ID", "Name", "Amount", "Day")
			.assertNoMoreOrdering();

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Day")
			.assertNoMoreRows();
	}

	@Test
	public void testOrderByColumnsWithSamePriorities() {

		interactor//
			.openConfiguration()
			.selectOrderDirection("Name", OrderDirection.ASCENDING)
			.selectOrderDirection("Amount", OrderDirection.DESCENDING)
			.selectOrdering("ID", OrderDirection.ASCENDING, 2)
			.selectOrdering("Day", OrderDirection.DESCENDING, 2)
			.clickApply();
		tableAsserter//
			.assertDisplayedColumns("ID", "Name", "Amount", "Day")
			.assertNextOrdering(dataTable.getIdColumn(), OrderDirection.ASCENDING)
			.assertNextOrdering(dataTable.getDayColumn(), OrderDirection.DESCENDING)
			.assertNextOrdering(dataTable.getNameColumn(), OrderDirection.ASCENDING)
			.assertNextOrdering(dataTable.getAmountColumn(), OrderDirection.DESCENDING)
			.assertNoMoreOrdering();

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "ID", OrderDirection.ASCENDING, 1)
			.assertRow(true, "Name", OrderDirection.ASCENDING, 3)
			.assertRow(true, "Amount", OrderDirection.DESCENDING, 4)
			.assertRow(true, "Day", OrderDirection.DESCENDING, 2)
			.assertNoMoreRows();
	}

	// -------------------------------- all settings at once -------------------------------- //

	@Test
	public void testPageSizeAndHideAndMoveAndOrderByColumns() {

		interactor//
			.openConfiguration()
			.enterPageSize("3")
			.toggle("ID")
			.selectOrdering("ID", OrderDirection.DESCENDING, 2)
			.selectOrdering("Amount", OrderDirection.ASCENDING, 1)
			.selectOrdering("Day", OrderDirection.DESCENDING, 1)
			.moveUp("Name")
			.moveDown("Amount")
			.clickApply();
		tableAsserter//
			.assertRows(3)
			.assertDisplayedColumns("Name", "Day", "Amount")
			.assertNextOrdering(dataTable.getDayColumn(), OrderDirection.DESCENDING)
			.assertNextOrdering(dataTable.getAmountColumn(), OrderDirection.ASCENDING)
			.assertNextOrdering(dataTable.getIdColumn(), OrderDirection.DESCENDING)
			.assertNoMoreOrdering();

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "Name")
			.assertRow(false, "ID", OrderDirection.DESCENDING, 3)
			.assertRow(true, "Day", OrderDirection.DESCENDING, 1)
			.assertRow(true, "Amount", OrderDirection.ASCENDING, 2)
			.assertNoMoreRows();
	}

	@Test
	public void testPageSizeAndHideAndMoveAndOrderByColumnsWithCancel() {

		interactor//
			.openConfiguration()
			.enterPageSize("3")
			.toggle("ID")
			.selectOrdering("ID", OrderDirection.DESCENDING, 2)
			.selectOrdering("Amount", OrderDirection.ASCENDING, 1)
			.selectOrdering("Day", OrderDirection.DESCENDING, 1)
			.moveUp("Name")
			.moveDown("Amount")
			.clickCancel();
		tableAsserter//
			.assertRows(5)
			.assertDisplayedColumns("ID", "Name", "Amount", "Day")
			.assertNoMoreOrdering();

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Day")
			.assertNoMoreRows();
	}

	@Test
	public void testPageSizeAndHideAndMoveAndOrderByColumnsWithApplyAndResetAndCancel() {

		interactor//
			.openConfiguration()
			.enterPageSize("3")
			.toggle("ID")
			.selectOrdering("ID", OrderDirection.DESCENDING, 2)
			.selectOrdering("Amount", OrderDirection.ASCENDING, 1)
			.selectOrdering("Day", OrderDirection.DESCENDING, 1)
			.moveUp("Name")
			.moveDown("Amount")
			.clickApply()
			.openConfiguration()
			.clickReset()
			.clickCancel();
		tableAsserter//
			.assertRows(3)
			.assertDisplayedColumns("Name", "Day", "Amount")
			.assertNextOrdering(dataTable.getDayColumn(), OrderDirection.DESCENDING)
			.assertNextOrdering(dataTable.getAmountColumn(), OrderDirection.ASCENDING)
			.assertNextOrdering(dataTable.getIdColumn(), OrderDirection.DESCENDING)
			.assertNoMoreOrdering();

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "Name")
			.assertRow(false, "ID", OrderDirection.DESCENDING, 3)
			.assertRow(true, "Day", OrderDirection.DESCENDING, 1)
			.assertRow(true, "Amount", OrderDirection.ASCENDING, 2)
			.assertNoMoreRows();
	}

	@Test
	public void testPageSizeAndHideAndMoveAndOrderByColumnsWithApplyAndResetAndApply() {

		interactor//
			.openConfiguration()
			.enterPageSize("3")
			.toggle("ID")
			.selectOrdering("ID", OrderDirection.DESCENDING, 2)
			.selectOrdering("Amount", OrderDirection.ASCENDING, 1)
			.selectOrdering("Day", OrderDirection.DESCENDING, 1)
			.moveUp("Name")
			.moveDown("Amount")
			.clickApply()
			.openConfiguration()
			.clickReset()
			.clickApply();
		tableAsserter//
			.assertRows(5)
			.assertDisplayedColumns("ID", "Name", "Amount", "Day")
			.assertNoMoreOrdering();

		interactor.openConfiguration();
		popupAsserter//
			.assertRow(true, "ID")
			.assertRow(true, "Name")
			.assertRow(true, "Amount")
			.assertRow(true, "Day")
			.assertNoMoreRows();
	}

	// -------------------------------- private -------------------------------- //

	private static class TestTable extends AbstractInMemoryDataTable<TestTableRow> {

		private final List<TestTableRow> rows;
		private final IDataTableColumn<TestTableRow, Integer> idColumn;
		private final IDataTableColumn<TestTableRow, String> nameColumn;
		private final IDataTableColumn<TestTableRow, BigDecimal> amountColumn;
		private final IDataTableColumn<TestTableRow, Day> dayColumn;

		public TestTable() {

			this.idColumn = newColumn(Integer.class)//
				.setGetter(TestTableRow::getId)
				.setTitle(IDisplayString.create("ID"))
				.addColumn();
			this.nameColumn = newColumn(String.class)//
				.setGetter(TestTableRow::getName)
				.setTitle(IDisplayString.create("Name"))
				.addColumn();
			this.amountColumn = newColumn(BigDecimal.class)//
				.setGetter(TestTableRow::getAmount)
				.setTitle(IDisplayString.create("Amount"))
				.addColumn();
			this.dayColumn = newColumn(Day.class)//
				.setGetter(TestTableRow::getDay)
				.setTitle(IDisplayString.create("Day"))
				.addColumn();

			this.rows = new ArrayList<>();
			addRow("foo", new BigDecimal("11.11"));
			addRow("bar", new BigDecimal("22.22"));
			addRow("bar", new BigDecimal("33.33"));
			addRow("bar", new BigDecimal("11.11"));
			addRow("baz", new BigDecimal("22.22"));
		}

		@Override
		public DataTableIdentifier getIdentifier() {

			return new DataTableIdentifier("8624bd05-ed90-4fd8-b77f-89efb1d7b11a");
		}

		@Override
		protected Iterable<TestTableRow> getTableRows() {

			return rows;
		}

		public TestTable addRow(String name, BigDecimal amount) {

			rows.add(new TestTableRow(rows.size() + 1, name, amount, Day.fromYMD(2021, 1, 1)));
			return this;
		}

		public TestTable addDummyRows(int count) {

			for (int i = 0; i < count; i++) {
				addRow("", BigDecimal.ZERO);
			}
			return this;
		}

		public TestTable clearRows() {

			rows.clear();
			return this;
		}

		public IDataTableColumn<TestTableRow, Integer> getIdColumn() {

			return idColumn;
		}

		public IDataTableColumn<TestTableRow, String> getNameColumn() {

			return nameColumn;
		}

		public IDataTableColumn<TestTableRow, BigDecimal> getAmountColumn() {

			return amountColumn;
		}

		public IDataTableColumn<TestTableRow, Day> getDayColumn() {

			return dayColumn;
		}
	}

	private static class TestTableRow {

		private final Integer id;
		private final String name;
		private final BigDecimal amount;
		private final Day day;

		public TestTableRow(Integer id, String name, BigDecimal amount, Day day) {

			this.id = id;
			this.name = name;
			this.amount = amount;
			this.day = day;
		}

		public Integer getId() {

			return id;
		}

		public String getName() {

			return name;
		}

		public BigDecimal getAmount() {

			return amount;
		}

		public Day getDay() {

			return day;
		}
	}
}
