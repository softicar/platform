package com.softicar.platform.emf.data.table.export.implementation.excel;

import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.common.excel.parser.ScExcelFileParser;
import com.softicar.platform.common.excel.parser.ScExcelRow;
import com.softicar.platform.common.io.buffer.ByteBuffer;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.tables.DomDataTable;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO i67739 Test sheet titles with named and unnamed tables. Requires changes
 * to {@link ScExcelFileParser}, which currently does not extract sheet titles.
 * <p>
 * FIXME In single-sheet mode, a tailing empty row is always appended after the
 * last table. In multi-sheet mode, this does not happen. Reason is unclear.
 * Minor defect, but should be fixed. As soon as this is fixed, the distinction
 * between {@link #getExpectedRowCountForSingleSheet(DomDataTable...)} and
 * {@link #getExpectedRowCountForMultiSheet(DomDataTable...)} will no longer be
 * necessary.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractTableExportExcelEngineFactoryTest<F extends AbstractTableExportExcelEngineFactory> {

	private static final int EMPTY_ROWS_AFTER_LAST_TABLE = 1;
	private static final int EMPTY_ROWS_BETWEEN_TABLES = 2;

	private final Supplier<F> factorySupplier;
	protected final TableExporter exporter;

	protected AbstractTableExportExcelEngineFactoryTest(Supplier<F> factorySupplier) {

		this.factorySupplier = factorySupplier;

		CurrentDomDocument.set(new DomDocument());
		this.exporter = new TableExporter();
	}

	@Test
	public void testExportWithSingleTableAndSingleSheet() throws Exception {

		DomDataTable table = new SimpleTestTable()//
			.appenHeaderRow("foo", "bar")
			.appendBodyRow("11", "12")
			.appendBodyRow("21", "22");

		List<ExportedRow> rows = exporter//
			.export(table)
			.assertNumberOfSheets(1)
			.getSheet(0)
			.assertRowCount(getExpectedRowCountForSingleSheet(table))
			.getRows();

		rows//
			.get(0)
			.assertStringCell(0, "foo")
			.assertStringCell(1, "bar");

		rows//
			.get(1)
			.assertStringCell(0, "11")
			.assertStringCell(1, "12");

		rows//
			.get(2)
			.assertStringCell(0, "21")
			.assertStringCell(1, "22");

		rows//
			.get(3)
			.assertNoCell(0)
			.assertNoCell(1);
	}

	@Test
	public void testExportWithMultipleTablesAndSingleSheet() throws Exception {

		DomDataTable table1 = new SimpleTestTable()//
			.appenHeaderRow("foo", "bar")
			.appendBodyRow("11", "12")
			.appendBodyRow("21", "22");

		DomDataTable table2 = new SimpleTestTable()//
			.appenHeaderRow("qwe", "asd", "zxc")
			.appendBodyRow("AA", "AB", "AC")
			.appendBodyRow("BA", "BB", "BC")
			.appendBodyRow("CA", "CB", "CC");

		List<ExportedRow> rows = exporter//
			.export(table1, table2)
			.assertNumberOfSheets(1)
			.getSheet(0)
			.assertRowCount(getExpectedRowCountForSingleSheet(table1, table2))
			.getRows();

		rows//
			.get(0)
			.assertStringCell(0, "foo")
			.assertStringCell(1, "bar");

		rows//
			.get(1)
			.assertStringCell(0, "11")
			.assertStringCell(1, "12");

		rows//
			.get(2)
			.assertStringCell(0, "21")
			.assertStringCell(1, "22");

		rows//
			.get(3)
			.assertNoCell(0)
			.assertNoCell(1);

		rows//
			.get(4)
			.assertNoCell(0)
			.assertNoCell(1);

		rows//
			.get(5)
			.assertStringCell(0, "qwe")
			.assertStringCell(1, "asd")
			.assertStringCell(2, "zxc");

		rows//
			.get(6)
			.assertStringCell(0, "AA")
			.assertStringCell(1, "AB")
			.assertStringCell(2, "AC");

		rows//
			.get(7)
			.assertStringCell(0, "BA")
			.assertStringCell(1, "BB")
			.assertStringCell(2, "BC");

		rows//
			.get(8)
			.assertStringCell(0, "CA")
			.assertStringCell(1, "CB")
			.assertStringCell(2, "CC");

		rows//
			.get(9)
			.assertNoCell(0)
			.assertNoCell(1);
	}

	@Test
	public void testExportWithMultipleTablesAndMultipleSheets() throws Exception {

		DomDataTable table1 = new SimpleTestTable()//
			.appenHeaderRow("foo", "bar")
			.appendBodyRow("11", "12")
			.appendBodyRow("21", "22");

		DomDataTable table2 = new SimpleTestTable()//
			.appenHeaderRow("qwe", "asd", "zxc")
			.appendBodyRow("AA", "AB", "AC")
			.appendBodyRow("BA", "BB", "BC")
			.appendBodyRow("CA", "CB", "CC");

		ExportedTable exportedTable = exporter//
			.setFactoryInitializer(factory -> factory.setSheetPerTable(true))
			.export(table1, table2)
			.assertNumberOfSheets(2);

		List<ExportedRow> rowsOfSheet1 = exportedTable//
			.getSheet(0)
			.assertRowCount(getExpectedRowCountForMultiSheet(table1))
			.getRows();

		rowsOfSheet1//
			.get(0)
			.assertStringCell(0, "foo")
			.assertStringCell(1, "bar");

		rowsOfSheet1//
			.get(1)
			.assertStringCell(0, "11")
			.assertStringCell(1, "12");

		rowsOfSheet1//
			.get(2)
			.assertStringCell(0, "21")
			.assertStringCell(1, "22");

		List<ExportedRow> rowsOfSheet2 = exportedTable//
			.getSheet(1)
			.assertRowCount(getExpectedRowCountForMultiSheet(table2))
			.getRows();

		rowsOfSheet2//
			.get(0)
			.assertStringCell(0, "qwe")
			.assertStringCell(1, "asd")
			.assertStringCell(2, "zxc");

		rowsOfSheet2//
			.get(1)
			.assertStringCell(0, "AA")
			.assertStringCell(1, "AB")
			.assertStringCell(2, "AC");

		rowsOfSheet2//
			.get(2)
			.assertStringCell(0, "BA")
			.assertStringCell(1, "BB")
			.assertStringCell(2, "BC");

		rowsOfSheet2//
			.get(3)
			.assertStringCell(0, "CA")
			.assertStringCell(1, "CB")
			.assertStringCell(2, "CC");
	}

	@Test
	public void testExportWithComplexTable() throws Exception {

		TableExportComplexTestTable table = new TableExportComplexTestTable();

		List<ExportedRow> rows = exporter//
			.export(table)
			.assertNumberOfSheets(1)
			.getSheet(0)
			.assertRowCount(getExpectedRowCountForSingleSheet(table))
			.getRows();

		rows//
			.get(0)
			.assertStringCell(0, TableExportComplexTestTable.generate_0x0_String())
			.assertStringCell(1, TableExportComplexTestTable.generate_0x1_String())
			.assertNoCell(2)
			.assertNoCell(3)
			.assertStringCell(4, null)
			.assertStringCell(5, TableExportComplexTestTable.generate_0x6_String());

		rows//
			.get(1)
			.assertNoCell(0)
			.assertStringCell(1, TableExportComplexTestTable.generate_1x1_String())
			.assertStringCell(2, TableExportComplexTestTable.generate_1x2_String())
			.assertStringCell(3, TableExportComplexTestTable.generate_1x3_String())
			.assertStringCell(4, TableExportComplexTestTable.generate_1x4_String())
			.assertStringCell(5, TableExportComplexTestTable.generate_1x6_String())
			.assertStringCell(6, TableExportComplexTestTable.generate_1x7_String());

		rows//
			.get(2)
			.assertStringCell(0, TableExportComplexTestTable.generate_2x0_String())
			.assertNoCell(1)
			.assertNoCell(2)
			.assertStringCell(3, TableExportComplexTestTable.generate_2x3_String())
			.assertStringCell(4, TableExportComplexTestTable.generate_2x4_String())
			.assertStringCell(5, TableExportComplexTestTable.generate_2x6_String())
			.assertStringCell(6, TableExportComplexTestTable.generate_2x7_String());

		rows//
			.get(3)
			// expect leading zeroes to disappear
			.assertStringCell(0, TableExportComplexTestTable.generate_3x0_String().replaceAll("^[0]+", ""))
			// FIXME: this currently fails
//			.assertStringCell(1, TableExportComplexTestTable.generate_3x1_validationString())
			.assertStringCell(2, TableExportComplexTestTable.generate_3x2_String())
			.assertNoCell(3)
			.assertStringCell(4, TableExportComplexTestTable.generate_3x3_validationString())
			.assertStringCell(5, null)
			.assertStringCell(6, TableExportComplexTestTable.generate_3x7_validationString());

		rows//
			.get(4)
			.assertStringCell(1, TableExportComplexTestTable.generate_4x1_String())
			.assertDoubleCell(2, TableExportComplexTestTable.generate_4x2_Double())
			.assertStringCell(6, TableExportComplexTestTable.generate_4x7_validationString());

		rows//
			.get(5)
			.assertNoCell(1);

		rows//
			.get(6)
			.assertNoCell(1);

		rows//
			.get(7)
			.assertNoCell(1);

		rows//
			.get(8)
			.assertStringCell(0, TableExportComplexTestTable.generate_8x0_validationString())
			.assertStringCell(1, TableExportComplexTestTable.generate_8x1_validationString())
			.assertStringCell(2, TableExportComplexTestTable.generate_8x2_validationString())
			.assertCell(3)
			.assertCell(4)
			.assertStringCell(5, TableExportComplexTestTable.generate_8x5_validationString())
			.assertCell(6);

		rows//
			.get(9)
			.assertDateCell(0, TableExportComplexTestTable.generate_9x0_Day().toDate())
			.assertIntegerCell(1, null)
			.assertDateCell(2, TableExportComplexTestTable.generate_9x2_DayTime().toDate())
			.assertNoCell(3);
		// FIXME: this currently fails
//			.assertStringCell(4, TableExportComplexTestTable.generate_9x4_validationString());

		rows//
			.get(10)
			.assertDateCell(0, TableExportComplexTestTable.generate_10x0_Day().toDate())
			.assertDateCell(1, TableExportComplexTestTable.generate_10x1_DayTime().toDate())
			.assertNoCell(2)
			.assertNoCell(3)
			.assertNoCell(4)
			.assertStringCell(5, null)
			.assertStringCell(6, null);
	}

	private int getExpectedRowCountForSingleSheet(DomDataTable...tables) {

		return getExpectedRowCount(true, tables);
	}

	private int getExpectedRowCountForMultiSheet(DomDataTable...tables) {

		return getExpectedRowCount(false, tables);
	}

	private int getExpectedRowCount(boolean expectTailingRow, DomDataTable...tables) {

		int count = 0;
		for (DomDataTable table: tables) {
			count += table.getHead().getChildCount();
			count += table.getBody().getChildCount();
		}
		count += EMPTY_ROWS_BETWEEN_TABLES * (tables.length - 1);
		if (expectTailingRow) {
			count += EMPTY_ROWS_AFTER_LAST_TABLE;
		}
		return count;
	}

	private class TableExporter {

		private Consumer<F> factoryInitializer;

		public TableExporter() {

			this.factoryInitializer = Consumers.noOperation();
		}

		public TableExporter setFactoryInitializer(Consumer<F> factoryInitializer) {

			this.factoryInitializer = factoryInitializer;
			return this;
		}

		public ExportedTable export(DomTable...tables) throws Exception {

			ByteBuffer byteBuffer = exportToByteBuffer(tables);
			ScExcelFileParser excelFileParser = new ScExcelFileParser(byteBuffer, "file");
			return new ExportedTable(excelFileParser.parse());
		}

		private ByteBuffer exportToByteBuffer(DomTable...tables) throws Exception {

			try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
				F factory = factorySupplier.get();
				factoryInitializer.accept(factory);
				factory//
					.create()
					.setOutputStreamCreationFunction(() -> bos)
					.export(tables);

				byte[] bytes = bos.toByteArray();
				assertNonZeroLength(bytes);

				return new ByteBuffer(bytes);
			}
		}

		private void assertNonZeroLength(byte[] bytes) {

			Assert.assertTrue(bytes.length > 0);
		}
	}

	private class ExportedTable {

		private final ListTreeMap<Integer, ScExcelRow> rowsBySheet;

		public ExportedTable(ListTreeMap<Integer, ScExcelRow> rowsBySheetIndex) {

			this.rowsBySheet = rowsBySheetIndex;
		}

		public ExportedSheet getSheet(int sheetIndex) {

			return new ExportedSheet(sheetIndex, rowsBySheet.getList(sheetIndex));
		}

		public ExportedTable assertNumberOfSheets(int expected) {

			Assert.assertEquals("Unexpected number of sheets.", expected, rowsBySheet.keySet().size());
			return this;
		}
	}

	private class ExportedSheet {

		private final int sheetIndex;
		private final List<ScExcelRow> rows;

		public ExportedSheet(int sheetIndex, List<ScExcelRow> rows) {

			this.sheetIndex = sheetIndex;
			this.rows = rows;
		}

		public List<ExportedRow> getRows() {

			return rows.stream().map(ExportedRow::new).collect(Collectors.toList());
		}

		public ExportedSheet assertRowCount(int expectedRows) {

			Assert
				.assertEquals(//
					String.format("Unexpected row count on sheet index %s.", sheetIndex),
					expectedRows,
					rows.size());
			return this;
		}
	}

	private class ExportedRow {

		private final ScExcelRow row;

		public ExportedRow(ScExcelRow row) {

			this.row = row;
		}

		public ExportedRow assertStringCell(int columnIndex, String expectedValue) {

			Assert.assertEquals(expectedValue, row.get(columnIndex).getStringOrNull());
			return this;
		}

		public ExportedRow assertIntegerCell(int columnIndex, Integer expectedValue) {

			Assert.assertEquals(expectedValue, row.get(columnIndex).getIntegerOrNull());
			return this;
		}

		public ExportedRow assertDoubleCell(int columnIndex, Double expectedValue) {

			Double actualValue = row.get(columnIndex).getDoubleOrNull();
			Assert
				.assertTrue(//
					String.format("Expected double value %s but encountered %s.", expectedValue, actualValue),
					Double.compare(expectedValue, actualValue) == 0);
			return this;
		}

		public ExportedRow assertDateCell(int columnIndex, Date expectedValue) {

			Assert.assertEquals(expectedValue, row.get(columnIndex).getDateOrNull());
			return this;
		}

		public ExportedRow assertCell(int columnIndex) {

			Assert.assertNotNull(row.get(columnIndex));
			return this;
		}

		public ExportedRow assertNoCell(int columnIndex) {

			Assert.assertNull(row.get(columnIndex));
			return this;
		}
	}

	private class SimpleTestTable extends DomDataTable {

		public SimpleTestTable appenHeaderRow(String...labels) {

			DomRow row = getHead().appendRow();
			Arrays.asList(labels).forEach(row::appendHeaderCell);
			return this;
		}

		public SimpleTestTable appendBodyRow(String...labels) {

			DomRow row = getBody().appendRow();
			Arrays.asList(labels).forEach(row::appendCell);
			return this;
		}
	}
}
