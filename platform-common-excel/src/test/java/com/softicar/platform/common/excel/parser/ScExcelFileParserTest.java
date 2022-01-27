package com.softicar.platform.common.excel.parser;

import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.common.date.Day;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ScExcelFileParser}.
 * <p>
 * TODO test different constructors
 *
 * @author Alexander Schmidt
 */
public class ScExcelFileParserTest extends Assert {

	private static final Date EXPECTED_DATE = Day.fromYMD(2020, 5, 1).toDate();

	@Test
	public void testParseWithLayoutHandlingTestXlsFile() {

		var parser = new ScExcelFileParser(ScExcelFileParserTestFiles.LAYOUT_HANDLING_TEST_XLS.getResource()::getResourceAsStream);
		testParseWithLayoutHandlingTestFile(parser);
	}

	@Test
	public void testParseWithLayoutHandlingTestXlsxFile() {

		var parser = new ScExcelFileParser(ScExcelFileParserTestFiles.LAYOUT_HANDLING_TEST_XLSX.getResource()::getResourceAsStream);
		testParseWithLayoutHandlingTestFile(parser);
	}

	@Test
	public void testParseWithDateExtractionTestXlsFile() {

		var parser = new ScExcelFileParser(ScExcelFileParserTestFiles.DATE_EXTRACTION_TEST_XLS.getResource()::getResourceAsStream);
		testParseWithDateExtractionTestFile(parser);
	}

	@Test
	public void testParseWithDateExtractionTestXlsxFile() {

		var parser = new ScExcelFileParser(ScExcelFileParserTestFiles.DATE_EXTRACTION_TEST_XLSX.getResource()::getResourceAsStream);
		testParseWithDateExtractionTestFile(parser);
	}

	@Test
	public void testParseWithNumberExtractionTestXlsFile() {

		var parser = new ScExcelFileParser(ScExcelFileParserTestFiles.NUMBER_EXTRACTION_TEST_XLS.getResource()::getResourceAsStream);
		testParseWithNumberExtractionTestFile(parser);
	}

	@Test
	public void testParseWithNumberExtractionTestXlsxFile() {

		var parser = new ScExcelFileParser(ScExcelFileParserTestFiles.NUMBER_EXTRACTION_TEST_XLSX.getResource()::getResourceAsStream);
		testParseWithNumberExtractionTestFile(parser);
	}

	private void testParseWithLayoutHandlingTestFile(ScExcelFileParser parser) {

		ListTreeMap<Integer, ScExcelRow> sheets = parser.parse();

		assertEquals(2, sheets.size());

		// ======== Sheet 1 ======== //
		List<ScExcelRow> sheet1 = sheets.getList(0);
		assertEquals(4, sheet1.size());

		// ---- Sheet 1, Row 1 ---- //
		ScExcelRow sheet1row1 = sheet1.get(0);
		assertEquals(2, sheet1row1.size());
		assertEquals("s1a1", sheet1row1.get(0).getStringOrNull());
		assertEquals("s1b1", sheet1row1.get(1).getStringOrNull());

		// ---- Sheet 1, Row 2 ---- //
		ScExcelRow sheet1row2 = sheet1.get(1);
		assertEquals(1, sheet1row2.size());
		assertEquals("s1a2", sheet1row2.get(0).getStringOrNull());

		// ---- Sheet 1, Row 3 ---- //
		ScExcelRow sheet1row3 = sheet1.get(2);
		assertEquals(0, sheet1row3.size());

		// ---- Sheet 1, Row 4 ---- //
		ScExcelRow sheet1row4 = sheet1.get(3);
		assertEquals(1, sheet1row4.size());
		assertEquals("s1b4", sheet1row4.get(1).getStringOrNull());

		// ======== Sheet 2 ======== //
		List<ScExcelRow> sheet2 = sheets.getList(1);
		assertEquals(3, sheet2.size());

		// ---- Sheet 2, Row 1 ---- //
		ScExcelRow sheet2row1 = sheet2.get(0);
		assertEquals(0, sheet2row1.size());

		// ---- Sheet 2, Row 2 ---- //
		ScExcelRow sheet2row2 = sheet2.get(1);
		assertEquals(1, sheet2row2.size());
		assertEquals("s2a2", sheet2row2.get(0).getStringOrNull());

		// ---- Sheet 2, Row 3 ---- //
		ScExcelRow sheet2row3 = sheet2.get(2);
		assertEquals(2, sheet2row3.size());
		assertEquals("s2a3", sheet2row3.get(0).getStringOrNull());
		assertEquals("s2b3", sheet2row3.get(1).getStringOrNull());
	}

	private void testParseWithDateExtractionTestFile(ScExcelFileParser parser) {

		ListTreeMap<Integer, ScExcelRow> sheets = parser.parse();

		assertEquals(1, sheets.size());
		List<ScExcelRow> sheet = sheets.getList(0);
		assertEquals(11, sheet.size());

		// ---- number cell (date-formatted) ---- //
		ScExcelCell a1 = sheet.get(0).get(0);
		assertEquals(EXPECTED_DATE, a1.getDateOrNull());

		// ---- number cell with dash-less ISO date ---- //
		ScExcelCell a2 = sheet.get(1).get(0);
		assertNull(a2.getDateOrNull());

		// ---- string cell with ISO date ---- //
		ScExcelCell a3 = sheet.get(2).get(0);
		assertEquals(EXPECTED_DATE, a3.getDateOrNull());

		// ---- string cell with German date ---- //
		ScExcelCell a4 = sheet.get(3).get(0);
		assertEquals(EXPECTED_DATE, a4.getDateOrNull());

		// ---- string cell with German short year date ---- //
		ScExcelCell a5 = sheet.get(4).get(0);
		assertEquals(EXPECTED_DATE, a5.getDateOrNull());

		// ---- string cell with German super short date ---- //
		ScExcelCell a6 = sheet.get(5).get(0);
		assertEquals(EXPECTED_DATE, a6.getDateOrNull());

		// ---- string cell with US date ---- //
		ScExcelCell a7 = sheet.get(6).get(0);
		assertEquals(EXPECTED_DATE, a7.getDateOrNull());

		// ---- string cell with US short year date ---- //
		ScExcelCell a8 = sheet.get(7).get(0);
		assertEquals(EXPECTED_DATE, a8.getDateOrNull());

		// ---- string cell with US super short date ---- //
		ScExcelCell a9 = sheet.get(8).get(0);
		assertEquals(EXPECTED_DATE, a9.getDateOrNull());

		// ---- empty cell ---- //
		ScExcelCell a10 = sheet.get(9).get(0);
		assertNull(a10);

		// ---- garbage cell ---- //
		ScExcelCell a11 = sheet.get(10).get(0);
		assertNull(a11.getDateOrNull());
	}

	private void testParseWithNumberExtractionTestFile(ScExcelFileParser parser) {

		ListTreeMap<Integer, ScExcelRow> sheets = parser.parse();

		assertEquals(1, sheets.size());
		List<ScExcelRow> sheet = sheets.getList(0);
		assertEquals(6, sheet.size());

		// ---- number cell (integer) ---- //
		ScExcelCell a1 = sheet.get(0).get(0);
		assertEquals(Integer.valueOf(10), a1.getIntegerOrNull());
		assertEquals(Long.valueOf(10), a1.getLongOrNull());
		assertEquals(Double.valueOf(10), a1.getDoubleOrNull());
		assertEquals(new BigDecimal("10"), a1.getBigDecimalOrNull());

		// ---- number cell (decimal) ---- //
		ScExcelCell a2 = sheet.get(1).get(0);
		assertEquals(Integer.valueOf(11), a2.getIntegerOrNull());
		assertEquals(Long.valueOf(11), a2.getLongOrNull());
		assertEquals(Double.valueOf(11.75), a2.getDoubleOrNull());
		assertEquals(new BigDecimal("11.75"), a2.getBigDecimalOrNull());

		// ---- string cell with dot ---- //
		ScExcelCell a3 = sheet.get(2).get(0);
		assertEquals(Integer.valueOf(30), a3.getIntegerOrNull());
		assertEquals(Long.valueOf(30), a3.getLongOrNull());
		assertEquals(Double.valueOf(30.50), a3.getDoubleOrNull());
		assertEquals(new BigDecimal("30.50"), a3.getBigDecimalOrNull());

		// ---- string cell with comma ---- //
		ScExcelCell a4 = sheet.get(3).get(0);
		assertEquals(Integer.valueOf(30), a4.getIntegerOrNull());
		assertEquals(Long.valueOf(30), a4.getLongOrNull());
		assertEquals(Double.valueOf(30.60), a4.getDoubleOrNull());
		assertEquals(new BigDecimal("30.60"), a4.getBigDecimalOrNull());

		// ---- empty cell ---- //
		ScExcelCell a5 = sheet.get(4).get(0);
		assertNull(a5);

		// ---- garbage cell ---- //
		ScExcelCell a6 = sheet.get(5).get(0);
		assertNull(a6.getIntegerOrNull());
		assertNull(a6.getLongOrNull());
		assertNull(a6.getDoubleOrNull());
		assertNull(a6.getBigDecimalOrNull());
	}
}
