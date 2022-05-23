package com.softicar.platform.emf.matrix;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.matrix.dimension.IEmfSettingMatrixDimensionStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractEmfSettingMatrixDivTest extends AbstractEmfTest {

	protected static final String ROW_NAME_ONE = "rowOne";
	protected static final String ROW_NAME_TWO = "rowTwo";
	protected static final String COLUMN_NAME_ONE = "columnOne";
	protected static final String COLUMN_NAME_TWO = "columnTwo";
	protected static final int VALUE_EDITED = 99;
	protected static final int VALUE_ONE_ONE = 11;
	protected static final int VALUE_ONE_TWO = 12;
	protected static final int VALUE_TWO_ONE = 21;
	protected static final int VALUE_TWO_TWO = 22;

	protected final Setup setup;
	protected final Interaction interaction;
	protected final Asserter asserter;

	protected AbstractEmfSettingMatrixDivTest() {

		this.setup = new Setup();
		this.interaction = new Interaction();
		this.asserter = new Asserter();
	}

	protected class Setup {

		private final Collection<TestEntry> entries;
		private boolean editable;
		private IEmfSettingMatrixDimensionStrategy rowStrategy;
		private IEmfSettingMatrixDimensionStrategy columnStrategy;
		private TestMatrixConfiguration configuration;
		private TestMatrixModelPersistence persistence;
		private boolean executed;

		public Setup() {

			this.entries = new ArrayList<>();
			this.editable = true;
			this.rowStrategy = null;
			this.columnStrategy = null;
			this.configuration = null;
			this.persistence = null;
			this.executed = false;
		}

		public Setup addStandardComparableEntries() {

			addComparableEntry(ROW_NAME_ONE, COLUMN_NAME_ONE, VALUE_ONE_ONE);
			addComparableEntry(ROW_NAME_ONE, COLUMN_NAME_TWO, VALUE_ONE_TWO);
			addComparableEntry(ROW_NAME_TWO, COLUMN_NAME_ONE, VALUE_TWO_ONE);
			addComparableEntry(ROW_NAME_TWO, COLUMN_NAME_TWO, VALUE_TWO_TWO);
			return this;
		}

		public Setup addComparableEntry(String rowName, String columnName, int quantity) {

			entries
				.add(
					new TestEntry(//
						new ComparableTestRow(rowName),
						new ComparableTestColumn(columnName),
						new TestValue(quantity)));
			return this;
		}

		public Setup addNonComparableEntry(String rowName, String columnName, int quantity) {

			entries
				.add(
					new TestEntry(//
						new TestRow(rowName),
						new TestColumn(columnName),
						new TestValue(quantity)));
			return this;
		}

		public Setup setEditable(boolean editable) {

			this.editable = editable;
			return this;
		}

		public Setup setRowStrategy(IEmfSettingMatrixDimensionStrategy rowStrategy) {

			this.rowStrategy = rowStrategy;
			return this;
		}

		public Setup setColumnStrategy(IEmfSettingMatrixDimensionStrategy columnStrategy) {

			this.columnStrategy = columnStrategy;
			return this;
		}

		public void assertExecuted() {

			assertTrue("Test setup was not executed.", executed);
		}

		public void executeSetup() {

			if (!executed) {
				setNodeSupplier(() -> {
					this.configuration = createConfiguration();
					this.persistence = createPersistence();
					return new EmfSettingMatrixDiv<>(configuration, persistence);
				});
				this.executed = true;
			} else {
				fail("Tried to execute the test setup more than once.");
			}
		}

		public TestMatrixConfiguration getConfiguration() {

			assertExecuted();
			return configuration;
		}

		public TestMatrixModelPersistence getPersistence() {

			assertExecuted();
			return persistence;
		}

		private TestMatrixConfiguration createConfiguration() {

			return new TestMatrixConfiguration(editable, rowStrategy, columnStrategy);
		}

		private TestMatrixModelPersistence createPersistence() {

			return new TestMatrixModelPersistence(entries);
		}
	}

	protected class Interaction {

		public Interaction enterRowFilterText(String text) {

			findBody()//
				.findInput(EmfSettingMatrixMarker.ROW_FILTER_INPUT)
				.setInputValue(text);
			return this;
		}

		public Interaction enterColumnFilterText(String text) {

			findBody()//
				.findInput(EmfSettingMatrixMarker.COLUMN_FILTER_INPUT)
				.setInputValue(text);
			return this;
		}

		public Interaction clickFlipCheckbox() {

			findBody()//
				.findNode(EmfSettingMatrixMarker.FLIP_CHECKBOX)
				.click();
			return this;
		}

		public Interaction clickApply() {

			findBody()//
				.findNode(EmfSettingMatrixMarker.APPLY_BUTTON)
				.click();
			return this;
		}

		public Interaction setFirstInputValue(int value) {

			findBody()//
				.findNodes(TestValueInput.class)
				.iterator()
				.next()
				.setValue(new TestValue(value));
			return this;
		}

		public Interaction clickSaveButton() {

			findBody()//
				.findNodes(EmfSettingMatrixMarker.SAVE_BUTTON)
				.first()
				.click();
			return this;
		}

		public Interaction clickReloadButton() {

			findBody()//
				.findNodes(EmfSettingMatrixMarker.RELOAD_BUTTON)
				.first()
				.click();
			return this;
		}
	}

	protected class Asserter {

		public Asserter assertRowNames(String...rowNames) {

			assertTexts("row", EmfSettingMatrixMarker.ROW_NAME_CELL, rowNames);
			return this;
		}

		public Asserter assertColumnNames(String...columnNames) {

			assertTexts("column", EmfSettingMatrixMarker.COLUMN_NAME_CELL, columnNames);
			return this;
		}

		public Asserter assertDummyRow() {

			findBody().findNode(EmfSettingMatrixMarker.DUMMY_CELL);
			return this;
		}

		public Asserter assertValues(Integer...values) {

			IDomNodeIterable<TestValueInput> nodes = findBody().findNodes(TestValueInput.class);
			for (String valueString: toValueStrings(values)) {
				boolean found = nodes//
					.stream()
					.map(TestValueInput::getValue)
					.filter(it -> it.isPresent())
					.map(value -> value.get().getQuantity() + "")
					.anyMatch(it -> it.equals(valueString));
				assertTrue(String.format("Failed to find value \"%s\"", valueString), found);
			}
			return this;
		}

		public Asserter assertFirstInputValue(int value) {

			int inputValue = findBody()//
				.findNodes(TestValueInput.class)
				.iterator()
				.next()
				.getValue()
				.orElseThrow(() -> new RuntimeException("Input element did not contain a valid value."))
				.getQuantity();
			assertEquals(value, inputValue);
			return this;
		}

		public Asserter assertPersistenceLoaded() {

			assertTrue(setup.getPersistence().getLoadCount() > 0);
			return this;
		}

		public Asserter assertPersistenceLoadCount(int count) {

			assertEquals(count, setup.getPersistence().getLoadCount());
			return this;
		}

		public Asserter assertPersistenceSaved() {

			assertTrue(setup.getPersistence().getSaveCount() > 0);
			return this;
		}

		public Asserter assertPersistenceSaveCount(int count) {

			assertEquals(count, setup.getPersistence().getSaveCount());
			return this;
		}

		private Asserter assertTexts(String description, IStaticObject parentMarker, String...texts) {

			IDomNodeIterable<IDomNode> nodes = findBody().findNodes(parentMarker);
			for (String text: texts) {
				boolean found = nodes.stream().map(node -> new DomNodeTester(getEngine(), node)).anyMatch(node -> node.containsText(text));
				assertTrue(String.format("Failed to find %s: \"%s\"", description, text), found);
			}
			return this;
		}

		private List<String> toValueStrings(Integer...values) {

			return Arrays.asList(values).stream().map(it -> it.toString()).collect(Collectors.toList());
		}
	}

	private static class TestMatrixModelPersistence implements IEmfSettingMatrixPersistence<TestRow, TestColumn, TestValue> {

		private final Collection<TestEntry> entries;
		private int loadCount;
		private int saveCount;

		public TestMatrixModelPersistence(Collection<TestEntry> entries) {

			this.entries = entries;
			this.loadCount = 0;
			this.saveCount = 0;
		}

		@Override
		public void save(IEmfSettingMatrixModelData<TestRow, TestColumn, TestValue> deltaModelData) {

			this.entries.clear();
			for (TestRow row: deltaModelData.getRows()) {
				for (TestColumn column: deltaModelData.getColumns()) {
					Optional<TestValue> value = deltaModelData.getValue(row, column);
					if (value.isPresent()) {
						entries.add(new TestEntry(row, column, value.get()));
					}
				}
			}
			this.saveCount++;
		}

		@Override
		public void load(IEmfSettingMatrixModelData<TestRow, TestColumn, TestValue> modelData) {

			for (TestEntry entry: entries) {
				modelData.setValue(entry.getRow(), entry.getColumn(), entry.getValue());
			}
			this.loadCount++;
		}

		public int getSaveCount() {

			return saveCount;
		}

		public int getLoadCount() {

			return loadCount;
		}
	}

	private static class TestMatrixConfiguration extends AbstractEmfSettingMatrixConfiguration<TestRow, TestColumn, TestValue> {

		private final boolean editable;
		private final IEmfSettingMatrixDimensionStrategy rowStrategy;
		private final IEmfSettingMatrixDimensionStrategy columnStrategy;

		public TestMatrixConfiguration(boolean editable, IEmfSettingMatrixDimensionStrategy rowStrategy, IEmfSettingMatrixDimensionStrategy columnStrategy) {

			this.editable = editable;
			this.rowStrategy = rowStrategy;
			this.columnStrategy = columnStrategy;
		}

		@Override
		public boolean isEditable() {

			return editable;
		}

		@Override
		public Supplier<TestValue> getDefaultValueSupplier() {

			return () -> new TestValue(0);
		}

		@Override
		public IEmfSettingMatrixDimensionStrategy getRowStrategy() {

			return Optional//
				.ofNullable(rowStrategy)
				.orElse(super.getRowStrategy());
		}

		@Override
		public IEmfSettingMatrixDimensionStrategy getColumnStrategy() {

			return Optional//
				.ofNullable(columnStrategy)
				.orElse(super.getColumnStrategy());
		}

		@Override
		public IEmfSettingMatrixModelEntryInput<TestValue> createInput(Supplier<Optional<TestValue>> originalValueSupplier) {

			return new TestValueInput();
		}

		@Override
		public IDisplayString getRowTypeDisplayName() {

			return IDisplayString.create("TestRows");
		}

		@Override
		public IDisplayString getColumnTypeDisplayName() {

			return IDisplayString.create("TestColumns");
		}

		@Override
		public IDisplayString getRowDisplayName(TestRow row) {

			return IDisplayString.create(row.getName());
		}

		@Override
		public IDisplayString getColumnDisplayName(TestColumn column) {

			return IDisplayString.create(column.getName());
		}

		@Override
		public boolean isDefaultValue(TestValue value) {

			return value == null || value.getQuantity() == 0;
		}

		@Override
		public TestValue deepCopyValue(TestValue value) {

			return new TestValue(value.getQuantity());
		}

		@Override
		public TestValue mergeValues(TestValue inferior, TestValue superior) {

			return deepCopyValue(superior);
		}

		@Override
		public boolean isEqualValues(TestValue first, TestValue second) {

			return first.getQuantity() == second.getQuantity();
		}
	}

	private static class TestValueInput extends DomDiv implements IEmfSettingMatrixModelEntryInput<TestValue> {

		private final DomIntegerInput input;

		public TestValueInput() {

			this.input = appendChild(new DomIntegerInput());
		}

		@Override
		public void setValue(TestValue value) {

			input
				.setValue(
					Optional//
						.ofNullable(value)
						.map(it -> it.getQuantity())
						.orElse(null));
		}

		@Override
		public void setInputDisabled(boolean disabled) {

			input.setDisabled(disabled);
		}

		@Override
		public Optional<TestValue> getValue() {

			return input.getValue().map(TestValue::new);
		}
	}

	private static class TestEntry {

		private final TestRow row;
		private final TestColumn column;
		private final TestValue value;

		public TestEntry(TestRow row, TestColumn column, TestValue value) {

			this.row = row;
			this.column = column;
			this.value = value;
		}

		public TestRow getRow() {

			return row;
		}

		public TestColumn getColumn() {

			return column;
		}

		public TestValue getValue() {

			return value;
		}
	}

	private static class TestRow {

		private final String name;

		public TestRow(String name) {

			this.name = name;
		}

		public String getName() {

			return name;
		}

		@Override
		public String toString() {

			return name;
		}
	}

	private static class ComparableTestRow extends TestRow implements Comparable<ComparableTestRow> {

		public ComparableTestRow(String name) {

			super(name);
		}

		@Override
		public int compareTo(ComparableTestRow other) {

			return Comparator//
				.comparing(ComparableTestRow::getName)
				.compare(this, other);
		}
	}

	private static class TestColumn {

		private final String name;

		public TestColumn(String name) {

			this.name = name;
		}

		public String getName() {

			return name;
		}

		@Override
		public String toString() {

			return name;
		}
	}

	private static class ComparableTestColumn extends TestColumn implements Comparable<ComparableTestColumn> {

		public ComparableTestColumn(String name) {

			super(name);
		}

		@Override
		public int compareTo(ComparableTestColumn other) {

			return Comparator//
				.comparing(ComparableTestColumn::getName)
				.compare(this, other);
		}
	}

	private static class TestValue {

		private final int quantity;

		public TestValue(int quantity) {

			this.quantity = quantity;
		}

		public int getQuantity() {

			return quantity;
		}

		@Override
		public String toString() {

			return quantity + "";
		}
	}
}
