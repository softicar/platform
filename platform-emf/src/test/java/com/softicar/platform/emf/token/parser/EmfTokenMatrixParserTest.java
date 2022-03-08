package com.softicar.platform.emf.token.parser;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.common.string.Padding;
import com.softicar.platform.db.runtime.field.IDbBigDecimalField;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbByteArrayField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbDoubleField;
import com.softicar.platform.db.runtime.field.IDbEnumField;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbFloatField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbLongField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.field.IDbTimeField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * Unit test for {@link EmfTokenMatrixParser}.
 *
 * @author Alexander Schmidt
 */
public class EmfTokenMatrixParserTest extends AbstractDbTest {

	private final ReferencedTestObject referencedTestObject;
	private final TokenRow tokenRow;
	private IDbTableRow<TestObject, Integer> tableRow;

	public EmfTokenMatrixParserTest() {

		this.referencedTestObject = insertReferencedTestObject();
		this.tokenRow = createSimpleTokenRow();
		this.tableRow = null;
	}

	// -------------------------------- All tokens types, w/o edge cases -------------------------------- //

	@Test
	public void testParseWithAllTokenTypes() {

		parse(tokenRow);
		assertValue(TestObject.ID_FIELD, 11);
		assertValue(TestObject.BIG_DECIMAL_FIELD, new BigDecimal("12345.09876"));
		assertValue(TestObject.BIG_DECIMAL_NULLABLE_FIELD, new BigDecimal("12345.09876"));
		assertValue(TestObject.BOOLEAN_FIELD, true);
		assertValue(TestObject.BOOLEAN_NULLABLE_FIELD, true);
		assertValue(TestObject.BYTES_FIELD, "I'm a blob".getBytes());
		assertValue(TestObject.BYTES_NULLABLE_FIELD, "I'm a blob".getBytes());
		assertValue(TestObject.BYTES_TINY_BLOB_FIELD, "I'm a blob".getBytes());
		assertValue(TestObject.BYTES_BLOB_FIELD, "I'm a blob".getBytes());
		assertValue(TestObject.DAY_FIELD, Day.fromYMD(2021, 12, 9));
		assertValue(TestObject.DAY_NULLABLE_FIELD, Day.fromYMD(2021, 12, 9));
		assertValue(TestObject.DAY_TIME_FIELD, DayTime.fromYMD_HMS(2021, 12, 9, 13, 59, 22));
		assertValue(TestObject.DAY_TIME_NULLABLE_FIELD, DayTime.fromYMD_HMS(2021, 12, 9, 13, 59, 22));
		assertValue(TestObject.DOUBLE_FIELD, 1234.5678d);
		assertValue(TestObject.DOUBLE_NULLABLE_FIELD, 1234.5678d);
		assertValue(TestObject.ENUM_FIELD, TestEnum.FOO);
		assertValue(TestObject.ENUM_NULLABLE_FIELD, TestEnum.FOO);
		assertValue(TestObject.FLOAT_FIELD, 123.456f);
		assertValue(TestObject.FLOAT_NULLABLE_FIELD, 123.456f);
		assertValue(TestObject.FOREIGN_FIELD, referencedTestObject);
		assertValue(TestObject.FOREIGN_NULLABLE_FIELD, referencedTestObject);
		assertValue(TestObject.INTEGER_FIELD, 5);
		assertValue(TestObject.INTEGER_NULLABLE_FIELD, 5);
		assertValue(TestObject.LONG_FIELD, 55l);
		assertValue(TestObject.LONG_NULLABLE_FIELD, 55l);
		assertValue(TestObject.STRING_FIELD, "foo");
		assertValue(TestObject.STRING_NULLABLE_FIELD, "foo");
		assertValue(TestObject.STRING_TINY_TEXT_FIELD, "foo");
		assertValue(TestObject.STRING_TEXT_FIELD, "foo");
		assertValue(TestObject.TIME_FIELD, new Time(13, 59, 22));
		assertValue(TestObject.TIME_NULLABLE_FIELD, new Time(13, 59, 22));
	}

	// -------------------------------- Row geometry violations -------------------------------- //

	@Test
	public void testParseWithRowOfTooManyTokens() {

		parseToException(//
			Arrays.asList("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32".split(",")),
			"Reason: Expected 31 columns but encountered 32");
	}

	@Test
	public void testParseWithRowOfTooFewTokens() {

		parseToException(//
			tokenRow.unset(TestObject.INTEGER_FIELD),
			"Reason: Expected 31 columns but encountered 30");
	}

	@Test
	public void testParseWithRowOfZeroTokens() {

		parseToException(//
			tokenRow.unsetAll(),
			"Reason: Expected 31 columns but encountered 0");
	}

	// -------------------------------- Exception text -------------------------------- //

	@Test
	public void testParseWithException() {

		try {
			parse(tokenRow.set(TestObject.BIG_DECIMAL_FIELD, "???"));
		} catch (Exception exception) {
			String message = exception.getMessage();
			assertTrue(//
				"The exception message does not start with the expected text. Exception message:\n" + message,
				message.startsWith("Row #1, Column #2: Failed to process token [???]:\n[11], **[???]**, [12345.09876], [1], [1], "));
		}
	}

	// -------------------------------- Multiple rows -------------------------------- //

	@Test
	public void testParseWithMultipleRows() {

		List<List<String>> tokenMatrix = List
			.of(//
				List.of("", "2022-01-25", "11", "foo"),
				List.of("", "2022-01-26", "22", "bar"),
				List.of("", "2022-01-27", "33", "baz"));

		var table = SimpleTestObject.TABLE;
		var tableRows = new EmfTokenMatrixParser<>(table).parse(tokenMatrix);

		assertEquals(3, tableRows.size());

		SimpleTestObject firstRow = tableRows.get(0);
		assertEquals(Day.fromYMD(2022, 1, 25), SimpleTestObject.DAY_FIELD.getValue(firstRow));
		assertEquals(11, SimpleTestObject.INTEGER_FIELD.getValue(firstRow));
		assertEquals("foo", SimpleTestObject.STRING_FIELD.getValue(firstRow));

		SimpleTestObject secondRow = tableRows.get(1);
		assertEquals(Day.fromYMD(2022, 1, 26), SimpleTestObject.DAY_FIELD.getValue(secondRow));
		assertEquals(22, SimpleTestObject.INTEGER_FIELD.getValue(secondRow));
		assertEquals("bar", SimpleTestObject.STRING_FIELD.getValue(secondRow));

		SimpleTestObject thirdRow = tableRows.get(2);
		assertEquals(Day.fromYMD(2022, 1, 27), SimpleTestObject.DAY_FIELD.getValue(thirdRow));
		assertEquals(33, SimpleTestObject.INTEGER_FIELD.getValue(thirdRow));
		assertEquals("baz", SimpleTestObject.STRING_FIELD.getValue(thirdRow));
	}

	@Test
	public void testParseWithMultipleRowsAndFailure() {

		List<List<String>> tokenMatrix = List
			.of(//
				List.of("", "2022-01-25", "11", "foo"),
				List.of("", "2022-01-26", "???", "bar"),
				List.of("", "2022-01-27", "33", "baz"));

		var table = SimpleTestObject.TABLE;

		try {
			new EmfTokenMatrixParser<>(table).parse(tokenMatrix);
		} catch (Exception exception) {
			assertEquals(
				"Row #2, Column #3: Failed to process token [???]:\n[], [2022-01-26], **[???]**, [bar]\nReason: '???' is not of type 'Integer (Example: 128)'",
				exception.getMessage());
		}
	}

	// -------------------------------- BigDecimal tokens -------------------------------- //

	@Test
	public void testParseWithBigDecimalToken() {

		parse(tokenRow);
		assertValue(TestObject.BIG_DECIMAL_FIELD, new BigDecimal("12345.09876"));
	}

	@Test
	public void testParseWithBigDecimalTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.BIG_DECIMAL_FIELD, "???"),
			"Reason: '???' is not of type 'Decimal Number (Example: 123.456)'");
	}

	@Test
	public void testParseWithBigDecimalTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.BIG_DECIMAL_FIELD, ""),
			"Reason: A value of type 'Decimal Number (Example: 123.456)' is missing");
	}

	@Test
	public void testParseWithBigDecimalTokenUndefinedAndNullableField() {

		tokenRow.set(TestObject.BIG_DECIMAL_NULLABLE_FIELD, "");
		parse(tokenRow);
		assertValue(TestObject.BIG_DECIMAL_NULLABLE_FIELD, null);
	}

	@Test
	public void testParseWithBigDecimalTokenAndLeadingZeroes() {

		parse(tokenRow.set(TestObject.BIG_DECIMAL_FIELD, "00345.09876"));
		assertValue(TestObject.BIG_DECIMAL_FIELD, new BigDecimal("345.09876"));
	}

	@Test
	public void testParseWithBigDecimalTokenAndLeadingDot() {

		parse(tokenRow.set(TestObject.BIG_DECIMAL_FIELD, ".09876"));
		assertValue(TestObject.BIG_DECIMAL_FIELD, new BigDecimal("0.09876"));
	}

	@Test
	public void testParseWithBigDecimalTokenZero() {

		parse(tokenRow.set(TestObject.BIG_DECIMAL_FIELD, "0"));
		assertValue(TestObject.BIG_DECIMAL_FIELD, new BigDecimal("0"));
	}

	@Test
	public void testParseWithBigDecimalTokenNegative() {

		parse(tokenRow.set(TestObject.BIG_DECIMAL_FIELD, "-12345.09876"));
		assertValue(TestObject.BIG_DECIMAL_FIELD, new BigDecimal("-12345.09876"));
	}

	@Test
	public void testParseWithBigDecimalTokenAndPrecisionExceeded() {

		parseToException(//
			tokenRow.set(TestObject.BIG_DECIMAL_FIELD, "123456.09876"),
			"Reason: Number of digits exceeded (maximum: 10, encountered: 11)");
	}

	@Test
	public void testParseWithBigDecimalTokenAndPrecisionInferior() {

		parse(tokenRow.set(TestObject.BIG_DECIMAL_FIELD, "1234.09876"));
		assertValue(TestObject.BIG_DECIMAL_FIELD, new BigDecimal("1234.09876"));
	}

	@Test
	public void testParseWithBigDecimalTokenAndScaleExceeded() {

		parseToException(//
			tokenRow.set(TestObject.BIG_DECIMAL_FIELD, "1234.098765"),
			"Reason: Number of decimal places exceeded (maximum: 5, encountered: 6)");
	}

	@Test
	public void testParseWithBigDecimalTokenAndScaleInferior() {

		parse(tokenRow.set(TestObject.BIG_DECIMAL_FIELD, "12345.0987"));
		assertValue(TestObject.BIG_DECIMAL_FIELD, new BigDecimal("12345.09870"));
	}

	// -------------------------------- Boolean tokens -------------------------------- //

	@Test
	public void testParseWithBooleanToken() {

		parse(tokenRow);
		assertValue(TestObject.BOOLEAN_FIELD, true);
	}

	@Test
	public void testParseWithBooleanTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.BOOLEAN_FIELD, "2"),
			"Reason: '2' is not of type 'Boolean (1=Yes, 0=No)'");
	}

	@Test
	public void testParseWithBooleanTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.BOOLEAN_FIELD, ""),
			"Reason: A value of type 'Boolean (1=Yes, 0=No)' is missing");
	}

	@Test
	public void testParseWithBooleanTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.BOOLEAN_NULLABLE_FIELD, ""));
		assertValue(TestObject.BOOLEAN_NULLABLE_FIELD, null);
	}

	// -------------------------------- Byte-Array tokens -------------------------------- //

	@Test
	public void testParseWithByteArrayToken() {

		parse(tokenRow);
		assertValue(TestObject.BYTES_FIELD, "I'm a blob".getBytes());
	}

	@Test
	public void testParseWithByteArrayTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.BYTES_FIELD, "???"),
			"Reason: '???' is not of type 'Base64-Encoded Binary Data'");
	}

	@Test
	public void testParseWithByteArrayTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.BYTES_FIELD, ""),
			"Reason: A value of type 'Base64-Encoded Binary Data' is missing");
	}

	@Test
	public void testParseWithByteArrayTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.BYTES_NULLABLE_FIELD, ""));
		assertValue(TestObject.BYTES_NULLABLE_FIELD, null);
	}

	@Test
	public void testParseWithStringTokenAndVarbinaryFieldAndCharactersAtUpperLimit() {

		parse(tokenRow.set(TestObject.BYTES_FIELD, Base64.getEncoder().encodeToString(Padding.padRight("", "x", 16).getBytes())));
		assertValue(TestObject.BYTES_FIELD, Padding.padRight("", "x", 16).getBytes());
	}

	@Test
	public void testParseWithStringTokenAndVarbinaryFieldAndCharactersAboveUpperLimit() {

		parseToException(//
			tokenRow.set(TestObject.BYTES_FIELD, Base64.getEncoder().encodeToString(Padding.padRight("", "x", 17).getBytes())),
			"Reason: Too many bytes (maximum: 16, encountered: 17)");
	}

	@Test
	public void testParseWithStringTokenAndTinyBlobFieldAndCharactersAtUpperLimit() {

		parse(tokenRow.set(TestObject.BYTES_TINY_BLOB_FIELD, Base64.getEncoder().encodeToString(Padding.padRight("", "x", 255).getBytes())));
		assertValue(TestObject.BYTES_TINY_BLOB_FIELD, Padding.padRight("", "x", 255).getBytes());
	}

	@Test
	public void testParseWithStringTokenAndTinyBlobFieldAndCharactersAboveUpperLimit() {

		parseToException(//
			tokenRow.set(TestObject.BYTES_TINY_BLOB_FIELD, Base64.getEncoder().encodeToString(Padding.padRight("", "x", 256).getBytes())),
			"Reason: Too many bytes (maximum: 255, encountered: 256)");
	}

	@Test
	public void testParseWithStringTokenAndBlobFieldAndCharactersAtUpperLimit() {

		parse(tokenRow.set(TestObject.BYTES_BLOB_FIELD, Base64.getEncoder().encodeToString(Padding.padRight("", "x", 65535).getBytes())));
		assertValue(TestObject.BYTES_BLOB_FIELD, Padding.padRight("", "x", 65535).getBytes());
	}

	@Test
	public void testParseWithStringTokenAndBlobFieldAndCharactersAboveUpperLimit() {

		parseToException(//
			tokenRow.set(TestObject.BYTES_BLOB_FIELD, Base64.getEncoder().encodeToString(Padding.padRight("", "x", 65536).getBytes())),
			"Reason: Too many bytes (maximum: 65535, encountered: 65536)");
	}

	// Omitted tests for MEDIUMBLOB (length bits: 24; 16MB) and LONGBLOB (length bits: 32; 4GB) due to memory consumption concerns.

	// -------------------------------- Day tokens -------------------------------- //

	@Test
	public void testParseWithDayToken() {

		parse(tokenRow);
		assertValue(TestObject.DAY_FIELD, Day.fromYMD(2021, 12, 9));
	}

	@Test
	public void testParseWithDayTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.DAY_FIELD, "???"),
			"Reason: '???' is not of type 'Day (Example: 2000-12-31)'");
	}

	@Test
	public void testParseWithDayTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.DAY_FIELD, ""),
			"Reason: A value of type 'Day (Example: 2000-12-31)' is missing");
	}

	@Test
	public void testParseWithDayTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.DAY_NULLABLE_FIELD, ""));
		assertValue(TestObject.DAY_NULLABLE_FIELD, null);
	}

	@Test
	public void testParseWithDayTokenAndNonexistentCalendayDay() {

		parseToException(//
			tokenRow.set(TestObject.DAY_FIELD, "2021-02-29"),
			"Reason: Calendar day '2021-02-29' does not exist");
	}

	@Test
	public void testParseWithDayTokenInGermanFormat() {

		parseToException(//
			tokenRow.set(TestObject.DAY_FIELD, "09.12.2021"),
			"Reason: '09.12.2021' is not of type 'Day (Example: 2000-12-31)'");
	}

	@Test
	public void testParseWithDayTokenInUnitedStatesFormat() {

		parseToException(//
			tokenRow.set(TestObject.DAY_FIELD, "12/09/2021"),
			"Reason: '12/09/2021' is not of type 'Day (Example: 2000-12-31)'");
	}

	// -------------------------------- DayTime tokens -------------------------------- //

	@Test
	public void testParseWithDayTimeToken() {

		parse(tokenRow);
		assertValue(TestObject.DAY_TIME_FIELD, DayTime.fromYMD_HMS(2021, 12, 9, 13, 59, 22));
	}

	@Test
	public void testParseWithDayTimeTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.DAY_TIME_FIELD, "???"),
			"Reason: '???' is not of type 'Daytime (Example: 2000-12-31 14:59:59)'");
	}

	@Test
	public void testParseWithDayTimeTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.DAY_TIME_FIELD, ""),
			"Reason: A value of type 'Daytime (Example: 2000-12-31 14:59:59)' is missing");
	}

	@Test
	public void testParseWithDayTimeTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.DAY_TIME_NULLABLE_FIELD, ""));
		assertValue(TestObject.DAY_TIME_NULLABLE_FIELD, null);
	}

	@Test
	public void testParseWithDayTimeTokenAndNonexistentCalendayDay() {

		parseToException(//
			tokenRow.set(TestObject.DAY_TIME_FIELD, "2021-02-29 13:59:22"),
			"Reason: Point in time '2021-02-29 13:59:22' does not exist");
	}

	@Test
	public void testParseWithDayTimeTokenAndNonexistentHour() {

		parseToException(//
			tokenRow.set(TestObject.DAY_TIME_FIELD, "2021-12-09 26:59:22"),
			"Reason: Point in time '2021-12-09 26:59:22' does not exist");
	}

	@Test
	public void testParseWithDayTimeTokenAndSingleDigitHour() {

		parse(tokenRow.set(TestObject.DAY_TIME_FIELD, "2021-12-09 4:59:22"));
		assertValue(TestObject.DAY_TIME_FIELD, DayTime.fromYMD_HMS(2021, 12, 9, 4, 59, 22));
	}

	// -------------------------------- Double tokens -------------------------------- //

	@Test
	public void testParseWithDoubleToken() {

		parse(tokenRow);
		assertValue(TestObject.DOUBLE_FIELD, 1234.5678d);
	}

	@Test
	public void testParseWithDoubleTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.DOUBLE_FIELD, "???"),
			"Reason: '???' is not of type 'Double-Precision Floating-Point Number (Example: 123.456)'");
	}

	@Test
	public void testParseWithDoubleTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.DOUBLE_FIELD, ""),
			"Reason: A value of type 'Double-Precision Floating-Point Number (Example: 123.456)' is missing");
	}

	@Test
	public void testParseWithDoubleTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.DOUBLE_NULLABLE_FIELD, ""));
		assertValue(TestObject.DOUBLE_NULLABLE_FIELD, null);
	}

	@Test
	public void testParseWithDoubleTokenAndComma() {

		parseToException(//
			tokenRow.set(TestObject.DOUBLE_FIELD, "123,456"),
			"Reason: '123,456' is not of type 'Double-Precision Floating-Point Number (Example: 123.456)'");
	}

	@Test
	public void testParseWithDoubleTokenAndLeadingZero() {

		parse(tokenRow.set(TestObject.DOUBLE_FIELD, "0123.456"));
		assertValue(TestObject.DOUBLE_FIELD, 123.456d);
	}

	@Test
	public void testParseWithDoubleTokenAndLeadingDot() {

		parse(tokenRow.set(TestObject.DOUBLE_FIELD, ".456"));
		assertValue(TestObject.DOUBLE_FIELD, 0.456d);
	}

	@Test
	public void testParseWithDoubleTokenZero() {

		parse(tokenRow.set(TestObject.DOUBLE_FIELD, "0"));
		assertValue(TestObject.DOUBLE_FIELD, 0d);
	}

	@Test
	public void testParseWithDoubleTokenNegative() {

		parse(tokenRow.set(TestObject.DOUBLE_FIELD, "-123.456"));
		assertValue(TestObject.DOUBLE_FIELD, -123.456d);
	}

	@Test
	public void testParseWithDoubleTokenAndExponentialENotation() {

		parse(tokenRow.set(TestObject.DOUBLE_FIELD, "3.4E10"));
		assertValue(TestObject.DOUBLE_FIELD, 3.4E10d);
	}

	@Test
	public void testParseWithDoubleTokenAndExponentialEPlusNotation() {

		parse(tokenRow.set(TestObject.DOUBLE_FIELD, "3.4e+010"));
		assertValue(TestObject.DOUBLE_FIELD, 3.4E10d);
	}

	// -------------------------------- Enum tokens -------------------------------- //

	@Test
	public void testParseWithEnumToken() {

		parse(tokenRow);
		assertValue(TestObject.ENUM_FIELD, TestEnum.FOO);
	}

	@Test
	public void testParseWithEnumTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.ENUM_FIELD, "XXX"),
			"Reason: Enumerator 'XXX' could not be found in enumeration 'TestEnum'. Available values: BAR, FOO");
	}

	@Test
	public void testParseWithEnumTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.ENUM_FIELD, ""),
			"Reason: A value of type 'Enumerator' is missing");
	}

	@Test
	public void testParseWithEnumTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.ENUM_NULLABLE_FIELD, ""));
		assertValue(TestObject.ENUM_NULLABLE_FIELD, null);
	}

	// -------------------------------- Float tokens -------------------------------- //

	@Test
	public void testParseWithFloatToken() {

		parse(tokenRow);
		assertValue(TestObject.FLOAT_FIELD, 123.456f);
	}

	@Test
	public void testParseWithFloatTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.FLOAT_FIELD, "???"),
			"Reason: '???' is not of type 'Floating-Point Number (Example: 123.456)'");
	}

	@Test
	public void testParseWithFloatTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.FLOAT_FIELD, ""),
			"Reason: A value of type 'Floating-Point Number (Example: 123.456)' is missing");
	}

	@Test
	public void testParseWithFloatTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.FLOAT_NULLABLE_FIELD, ""));
		assertValue(TestObject.FLOAT_NULLABLE_FIELD, null);
	}

	@Test
	public void testParseWithFloatTokenAndComma() {

		parseToException(//
			tokenRow.set(TestObject.FLOAT_FIELD, "123,456"),
			"Reason: '123,456' is not of type 'Floating-Point Number (Example: 123.456)'");
	}

	@Test
	public void testParseWithFloatTokenAndLeadingZero() {

		parse(tokenRow.set(TestObject.FLOAT_FIELD, "0123.456"));
		assertValue(TestObject.FLOAT_FIELD, 123.456f);
	}

	@Test
	public void testParseWithFloatTokenAndLeadingDot() {

		parse(tokenRow.set(TestObject.FLOAT_FIELD, ".456"));
		assertValue(TestObject.FLOAT_FIELD, 0.456f);
	}

	@Test
	public void testParseWithFloatTokenZero() {

		parse(tokenRow.set(TestObject.FLOAT_FIELD, "0"));
		assertValue(TestObject.FLOAT_FIELD, 0f);
	}

	@Test
	public void testParseWithFloatTokenNegative() {

		parse(tokenRow.set(TestObject.FLOAT_FIELD, "-123.456"));
		assertValue(TestObject.FLOAT_FIELD, -123.456f);
	}

	@Test
	public void testParseWithFloatTokenAndExponentialENotation() {

		parse(tokenRow.set(TestObject.FLOAT_FIELD, "3.4E10"));
		assertValue(TestObject.FLOAT_FIELD, 3.4E10f);
	}

	@Test
	public void testParseWithFloatTokenAndExponentialEPlusNotation() {

		parse(tokenRow.set(TestObject.FLOAT_FIELD, "3.4e+010"));
		assertValue(TestObject.FLOAT_FIELD, 3.4E10f);
	}

	// -------------------------------- Foreign tokens -------------------------------- //

	@Test
	public void testParseWithForeignToken() {

		parse(tokenRow);
		assertValue(TestObject.FOREIGN_FIELD, referencedTestObject);
	}

	@Test
	public void testParseWithForeignTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.FOREIGN_FIELD, "???"),
			"Reason: '???' is not of type 'Foreign ID (Example: 128)'");
	}

	@Test
	public void testParseWithForeignTokenAndDanglingReference() {

		parseToException(//
			tokenRow.set(TestObject.FOREIGN_FIELD, Integer.MAX_VALUE + ""),
			"Reason: The record with ID #2147483647 could not be found in table `Test`.`ReferencedTestObject`");
	}

	@Test
	public void testParseWithForeignTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.FOREIGN_FIELD, ""),
			"Reason: A value of type 'Foreign ID (Example: 128)' is missing");
	}

	@Test
	public void testParseWithForeignTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.FOREIGN_NULLABLE_FIELD, ""));
		assertValue(TestObject.FOREIGN_NULLABLE_FIELD, null);
	}

	// -------------------------------- ID tokens -------------------------------- //

	@Test
	public void testParseWithIdToken() {

		parse(tokenRow);
		assertValue(TestObject.ID_FIELD, 11);
	}

	@Test
	public void testParseWithIdTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.ID_FIELD, "???"),
			"Reason: '???' is not of type 'ID (Example: 128)'");
	}

	@Test
	public void testParseWithIdTokenUndefined() {

		parse(tokenRow.set(TestObject.ID_FIELD, ""));
		assertValue(TestObject.ID_FIELD, null);
	}

	@Test
	public void testParseWithIdTokenPositive() {

		parse(tokenRow.set(TestObject.ID_FIELD, "1"));
		assertValue(TestObject.ID_FIELD, 1);
	}

	@Test
	public void testParseWithIdTokenZero() {

		parseToException(//
			tokenRow.set(TestObject.ID_FIELD, "0"),
			"Reason: Value '0' is out of range [1 ... 2147483647]");
	}

	@Test
	public void testParseWithIdTokenNegative() {

		parseToException(//
			tokenRow.set(TestObject.ID_FIELD, "-1"),
			"Reason: Value '-1' is out of range [1 ... 2147483647]");
	}

	@Test
	public void testParseWithIdTokenAtUpperLimit() {

		parse(tokenRow.set(TestObject.ID_FIELD, "2147483647"));
		assertValue(TestObject.ID_FIELD, 2147483647);
	}

	@Test
	public void testParseWithIdTokenAboveUpperLimit() {

		parseToException(//
			tokenRow.set(TestObject.ID_FIELD, "10000000000"),
			"Reason: Value '10000000000' is out of range [1 ... 2147483647]");
	}

	// -------------------------------- Integer tokens -------------------------------- //

	@Test
	public void testParseWithIntegerToken() {

		parse(tokenRow);
		assertValue(TestObject.INTEGER_FIELD, 5);
	}

	@Test
	public void testParseWithIntegerTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.INTEGER_FIELD, "???"),
			"Reason: '???' is not of type 'Integer (Example: 128)'");
	}

	@Test
	public void testParseWithIntegerTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.INTEGER_FIELD, ""),
			"Reason: A value of type 'Integer (Example: 128)' is missing");
	}

	@Test
	public void testParseWithIntegerTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.INTEGER_NULLABLE_FIELD, ""));
		assertValue(TestObject.INTEGER_NULLABLE_FIELD, null);
	}

	@Test
	public void testParseWithIntegerTokenPositive() {

		parse(tokenRow.set(TestObject.INTEGER_FIELD, "1"));
		assertValue(TestObject.INTEGER_FIELD, 1);
	}

	@Test
	public void testParseWithIntegerTokenZero() {

		parse(tokenRow.set(TestObject.INTEGER_FIELD, "0"));
		assertValue(TestObject.INTEGER_FIELD, 0);
	}

	@Test
	public void testParseWithIntegerTokenNegative() {

		parse(tokenRow.set(TestObject.INTEGER_FIELD, "-1"));
		assertValue(TestObject.INTEGER_FIELD, -1);
	}

	@Test
	public void testParseWithIntegerTokenAtLowerLimit() {

		parse(tokenRow.set(TestObject.INTEGER_FIELD, "-2147483647"));
		assertValue(TestObject.INTEGER_FIELD, -2147483647);
	}

	@Test
	public void testParseWithIntegerTokenAtUpperLimit() {

		parse(tokenRow.set(TestObject.INTEGER_FIELD, "2147483647"));
		assertValue(TestObject.INTEGER_FIELD, 2147483647);
	}

	@Test
	public void testParseWithIntegerTokenBelowLowerLimit() {

		parseToException(//
			tokenRow.set(TestObject.INTEGER_FIELD, "-10000000000"),
			"Reason: Value '-10000000000' is out of range [-2147483647 ... 2147483647]");
	}

	@Test
	public void testParseWithIntegerTokenAboveUpperLimit() {

		parseToException(//
			tokenRow.set(TestObject.INTEGER_FIELD, "10000000000"),
			"Reason: Value '10000000000' is out of range [-2147483647 ... 2147483647]");
	}

	// -------------------------------- Long tokens -------------------------------- //

	@Test
	public void testParseWithLongToken() {

		parse(tokenRow);
		assertValue(TestObject.LONG_FIELD, 55l);
	}

	@Test
	public void testParseWithLongTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.LONG_FIELD, "???"),
			"Reason: '???' is not of type 'Long-Integer (Example: 128)'");
	}

	@Test
	public void testParseWithLongTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.LONG_FIELD, ""),
			"Reason: A value of type 'Long-Integer (Example: 128)' is missing");
	}

	@Test
	public void testParseWithLongTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.LONG_NULLABLE_FIELD, ""));
		assertValue(TestObject.LONG_NULLABLE_FIELD, null);
	}

	@Test
	public void testParseWithLongTokenPositive() {

		parse(tokenRow.set(TestObject.LONG_FIELD, "1"));
		assertValue(TestObject.LONG_FIELD, 1l);
	}

	@Test
	public void testParseWithLongTokenZero() {

		parse(tokenRow.set(TestObject.LONG_FIELD, "0"));
		assertValue(TestObject.LONG_FIELD, 0l);
	}

	@Test
	public void testParseWithLongTokenNegative() {

		parse(tokenRow.set(TestObject.LONG_FIELD, "-1"));
		assertValue(TestObject.LONG_FIELD, -1l);
	}

	@Test
	public void testParseWithLongTokenAtLowerLimit() {

		parse(tokenRow.set(TestObject.LONG_FIELD, "-9223372036854775807"));
		assertValue(TestObject.LONG_FIELD, -9223372036854775807l);
	}

	@Test
	public void testParseWithLongTokenAtUpperLimit() {

		parse(tokenRow.set(TestObject.LONG_FIELD, "9223372036854775807"));
		assertValue(TestObject.LONG_FIELD, 9223372036854775807l);
	}

	@Test
	public void testParseWithLongTokenBelowLowerLimit() {

		parseToException(//
			tokenRow.set(TestObject.LONG_FIELD, "-10000000000000000000"),
			"Reason: Value '-10000000000000000000' is out of range [-9223372036854775807 ... 9223372036854775807]");
	}

	@Test
	public void testParseWithLongTokenAboveUpperLimit() {

		parseToException(//
			tokenRow.set(TestObject.LONG_FIELD, "10000000000000000000"),
			"Reason: Value '10000000000000000000' is out of range [-9223372036854775807 ... 9223372036854775807]");
	}

	// -------------------------------- String tokens -------------------------------- //

	@Test
	public void testParseWithStringToken() {

		parse(tokenRow);
		assertValue(TestObject.STRING_FIELD, "foo");
	}

	@Test
	public void testParseWithStringTokenEmptyAndMandatoryField() {

		parse(tokenRow.set(TestObject.STRING_FIELD, ""));
		assertValue(TestObject.STRING_FIELD, "");
	}

	@Test
	public void testParseWithStringTokenEmptyAndNullableField() {

		parse(tokenRow.set(TestObject.STRING_NULLABLE_FIELD, ""));
		assertValue(TestObject.STRING_NULLABLE_FIELD, "");
	}

	@Test
	public void testParseWithStringTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.STRING_FIELD, "NULL"),
			"Reason: A value of type 'Text' is missing");
	}

	@Test
	public void testParseWithStringTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.STRING_NULLABLE_FIELD, "NULL"));
		assertValue(TestObject.STRING_NULLABLE_FIELD, null);
	}

	@Test
	public void testParseWithStringTokenAndVarcharFieldAndCharactersAtUpperLimit() {

		parse(tokenRow.set(TestObject.STRING_FIELD, Padding.padRight("", "x", 16)));
		assertValue(TestObject.STRING_FIELD, Padding.padRight("", "x", 16));
	}

	@Test
	public void testParseWithStringTokenAndVarcharFieldAndCharactersAboveUpperLimit() {

		parseToException(//
			tokenRow.set(TestObject.STRING_FIELD, Padding.padRight("", "x", 17)),
			"Reason: Too many characters (maximum: 16, encountered: 17)");
	}

	@Test
	public void testParseWithStringTokenAndTinyTextFieldAndCharactersAtUpperLimit() {

		parse(tokenRow.set(TestObject.STRING_TINY_TEXT_FIELD, Padding.padRight("", "x", 255)));
		assertValue(TestObject.STRING_TINY_TEXT_FIELD, Padding.padRight("", "x", 255));
	}

	@Test
	public void testParseWithStringTokenAndTinyTextFieldAndCharactersAboveUpperLimit() {

		parseToException(//
			tokenRow.set(TestObject.STRING_TINY_TEXT_FIELD, Padding.padRight("", "x", 256)),
			"Reason: Too many characters (maximum: 255, encountered: 256)");
	}

	@Test
	public void testParseWithStringTokenAndTextFieldAndCharactersAtUpperLimit() {

		parse(tokenRow.set(TestObject.STRING_TEXT_FIELD, Padding.padRight("", "x", 65535)));
		assertValue(TestObject.STRING_TEXT_FIELD, Padding.padRight("", "x", 65535));
	}

	@Test
	public void testParseWithStringTokenAndTextFieldAndCharactersAboveUpperLimit() {

		parseToException(//
			tokenRow.set(TestObject.STRING_TEXT_FIELD, Padding.padRight("", "x", 65536)),
			"Reason: Too many characters (maximum: 65535, encountered: 65536)");
	}

	// Omitted tests for MEDIUMTEXT (length bits: 24; 16MB) and LONGTEXT (length bits: 32; 4GB) due to memory consumption concerns.

	// -------------------------------- Time tokens -------------------------------- //

	@Test
	public void testParseWithTimeToken() {

		parse(tokenRow);
		assertValue(TestObject.TIME_FIELD, new Time(13, 59, 22));
	}

	@Test
	public void testParseWithTimeTokenIllegal() {

		parseToException(//
			tokenRow.set(TestObject.TIME_FIELD, "???"),
			"Reason: '???' is not of type 'Time (Example: 14:30:00)'");
	}

	@Test
	public void testParseWithTimeTokenUndefinedAndMandatoryField() {

		parseToException(//
			tokenRow.set(TestObject.TIME_FIELD, ""),
			"Reason: A value of type 'Time (Example: 14:30:00)' is missing");
	}

	@Test
	public void testParseWithTimeTokenUndefinedAndNullableField() {

		parse(tokenRow.set(TestObject.TIME_NULLABLE_FIELD, ""));
		assertValue(TestObject.TIME_NULLABLE_FIELD, null);
	}

	@Test
	public void testParseWithTimeTokenAndNonexistentHour() {

		parseToException(//
			tokenRow.set(TestObject.TIME_FIELD, "33:59:22"),
			"Reason: '33:59:22' is not of type 'Time (Example: 14:30:00)'");
	}

	@Test
	public void testParseWithTimeTokenAndSingleDigitHour() {

		parse(tokenRow.set(TestObject.TIME_FIELD, "3:59:22"));
		assertValue(TestObject.TIME_FIELD, new Time(3, 59, 22));
	}

	// -------------------------------- private -------------------------------- //

	private ReferencedTestObject insertReferencedTestObject() {

		return new ReferencedTestObject().save();
	}

	private void parse(TokenRow tokenRow) {

		parse(tokenRow.toList());
	}

	private void parse(List<String> tokenList) {

		this.tableRow = new EmfTokenMatrixParser<>(TestObject.TABLE).parse(List.of(tokenList)).get(0);
	}

	private void parseToException(TokenRow tokenRow, String containedMessage) {

		assertExceptionMessageContains(//
			() -> parse(tokenRow),
			IDisplayString.create(containedMessage));
	}

	private void parseToException(List<String> tokenList, String containedMessage) {

		assertExceptionMessageContains(//
			() -> parse(tokenList),
			IDisplayString.create(containedMessage));
	}

	private <V> void assertValue(IDbField<TestObject, V> field, V expected) {

		Objects.requireNonNull(tableRow);
		V actual = field.getValue(tableRow.getThis());
		boolean equal = field.getValueType().compare(expected, actual) == 0;
		if (!equal) {
			throw new AssertionError("Expected '%s' but encountered '%s'.".formatted(expected, actual));
		}
	}

	private TokenRow createSimpleTokenRow() {

		return new TokenRow()//
			.set(TestObject.ID_FIELD, "11")
			.set(TestObject.BIG_DECIMAL_FIELD, "12345.09876")
			.set(TestObject.BIG_DECIMAL_NULLABLE_FIELD, "12345.09876")
			.set(TestObject.BOOLEAN_FIELD, "1")
			.set(TestObject.BOOLEAN_NULLABLE_FIELD, "1")
			.set(TestObject.BYTES_FIELD, Base64.getEncoder().encodeToString("I'm a blob".getBytes()))
			.set(TestObject.BYTES_NULLABLE_FIELD, Base64.getEncoder().encodeToString("I'm a blob".getBytes()))
			.set(TestObject.BYTES_TINY_BLOB_FIELD, Base64.getEncoder().encodeToString("I'm a blob".getBytes()))
			.set(TestObject.BYTES_BLOB_FIELD, Base64.getEncoder().encodeToString("I'm a blob".getBytes()))
			.set(TestObject.DAY_FIELD, "2021-12-09")
			.set(TestObject.DAY_NULLABLE_FIELD, "2021-12-09")
			.set(TestObject.DAY_TIME_FIELD, "2021-12-09 13:59:22")
			.set(TestObject.DAY_TIME_NULLABLE_FIELD, "2021-12-09 13:59:22")
			.set(TestObject.DOUBLE_FIELD, "1234.5678")
			.set(TestObject.DOUBLE_NULLABLE_FIELD, "1234.5678")
			.set(TestObject.ENUM_FIELD, "FOO")
			.set(TestObject.ENUM_NULLABLE_FIELD, "FOO")
			.set(TestObject.FLOAT_FIELD, "123.456")
			.set(TestObject.FLOAT_NULLABLE_FIELD, "123.456")
			.set(TestObject.FOREIGN_FIELD, referencedTestObject.getId() + "")
			.set(TestObject.FOREIGN_NULLABLE_FIELD, referencedTestObject.getId() + "")
			.set(TestObject.INTEGER_FIELD, "5")
			.set(TestObject.INTEGER_NULLABLE_FIELD, "5")
			.set(TestObject.LONG_FIELD, "55")
			.set(TestObject.LONG_NULLABLE_FIELD, "55")
			.set(TestObject.STRING_FIELD, "foo")
			.set(TestObject.STRING_NULLABLE_FIELD, "foo")
			.set(TestObject.STRING_TINY_TEXT_FIELD, "foo")
			.set(TestObject.STRING_TEXT_FIELD, "foo")
			.set(TestObject.TIME_FIELD, "13:59:22")
			.set(TestObject.TIME_NULLABLE_FIELD, "13:59:22");
	}

	private static class TokenRow {

		private final Map<IDbField<TestObject, ?>, String> valueMap;

		public TokenRow() {

			this.valueMap = new HashMap<>();
		}

		public TokenRow set(IDbField<TestObject, ?> field, String value) {

			valueMap.put(field, Objects.requireNonNull(value));
			return this;
		}

		public TokenRow unset(IDbField<TestObject, ?> field) {

			valueMap.remove(field);
			return this;
		}

		public TokenRow unsetAll() {

			valueMap.clear();
			return this;
		}

		public List<String> toList() {

			return TestObject.TABLE//
				.getAllFields()
				.stream()
				.map(valueMap::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		}
	}

	// -------------------------------- TestObject -------------------------------- //

	public static class TestObject extends TestObjectGenerated implements IEmfObject<TestObject> {

		@Override
		public IDisplayString toDisplayWithoutId() {

			return new DisplayString().append(TestObject.STRING_FIELD.getValue(this));
		}
	}

	public static class TestObjectGenerated extends AbstractDbObject<TestObject> {

		// @formatter:off
		public static final DbObjectTableBuilder<TestObject, TestObjectGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "TestObject", TestObject::new, TestObject.class);
		static {
			BUILDER.setTitle(IDisplayString.create("Test Object"));
			BUILDER.setTitle(IDisplayString.create("Test Objects"));
		}
		public static final IDbIdField<TestObject> ID_FIELD = BUILDER.addIdField("id", o->o.idValue, (o,v)->o.idValue=v);
		public static final IDbBigDecimalField<TestObject> BIG_DECIMAL_FIELD = BUILDER.addBigDecimalField("decimalValue", o->o.decimalValue, (o,v)->o.decimalValue=v).setSize(10, 5);
		public static final IDbBigDecimalField<TestObject> BIG_DECIMAL_NULLABLE_FIELD = BUILDER.addBigDecimalField("decimalValueNullable", o->o.decimalValueNullable, (o,v)->o.decimalValueNullable=v).setSize(10, 5).setNullable();
		public static final IDbBooleanField<TestObject> BOOLEAN_FIELD = BUILDER.addBooleanField("booleanValue", o->o.booleanValue, (o,v)->o.booleanValue=v);
		public static final IDbBooleanField<TestObject> BOOLEAN_NULLABLE_FIELD = BUILDER.addBooleanField("booleanValueNullable", o->o.booleanValueNullable, (o,v)->o.booleanValueNullable=v).setNullable();
		public static final IDbByteArrayField<TestObject> BYTES_FIELD = BUILDER.addByteArrayField("bytesValue", o->o.bytesValue, (o,v)->o.bytesValue=v).setMaximumLength(16);
		public static final IDbByteArrayField<TestObject> BYTES_NULLABLE_FIELD = BUILDER.addByteArrayField("bytesValueNullable", o->o.bytesValueNullable, (o,v)->o.bytesValueNullable=v).setMaximumLength(16).setNullable();
		public static final IDbByteArrayField<TestObject> BYTES_TINY_BLOB_FIELD = BUILDER.addByteArrayField("bytesTinyBlobValue", o->o.bytesTinyBlobValue, (o,v)->o.bytesTinyBlobValue=v).setLengthBits(8);
		public static final IDbByteArrayField<TestObject> BYTES_BLOB_FIELD = BUILDER.addByteArrayField("bytesBlobValue", o->o.bytesBlobValue, (o,v)->o.bytesBlobValue=v).setLengthBits(16);
		public static final IDbDayField<TestObject> DAY_FIELD = BUILDER.addDayField("dayValue", o->o.dayValue, (o,v)->o.dayValue=v);
		public static final IDbDayField<TestObject> DAY_NULLABLE_FIELD = BUILDER.addDayField("dayValueNullable", o->o.dayValueNullable, (o,v)->o.dayValueNullable=v).setNullable();
		public static final IDbDayTimeField<TestObject> DAY_TIME_FIELD = BUILDER.addDayTimeField("dayTimeValue", o->o.dayTimeValue, (o,v)->o.dayTimeValue=v);
		public static final IDbDayTimeField<TestObject> DAY_TIME_NULLABLE_FIELD = BUILDER.addDayTimeField("dayTimeValueNullable", o->o.dayTimeValueNullable, (o,v)->o.dayTimeValueNullable=v).setNullable();
		public static final IDbDoubleField<TestObject> DOUBLE_FIELD = BUILDER.addDoubleField("doubleValue", o->o.doubleValue, (o,v)->o.doubleValue=v);
		public static final IDbDoubleField<TestObject> DOUBLE_NULLABLE_FIELD = BUILDER.addDoubleField("doubleValueNullable", o->o.doubleValueNullable, (o,v)->o.doubleValueNullable=v).setNullable();
		public static final IDbEnumField<TestObject, TestEnum> ENUM_FIELD = BUILDER.addEnumField("enumValue", o->o.enumValue, (o,v)->o.enumValue=v, TestEnum.class);
		public static final IDbEnumField<TestObject, TestEnum> ENUM_NULLABLE_FIELD = BUILDER.addEnumField("enumValueNullable", o->o.enumValueNullable, (o,v)->o.enumValueNullable=v, TestEnum.class).setNullable();
		public static final IDbFloatField<TestObject> FLOAT_FIELD = BUILDER.addFloatField("floatValue", o->o.floatValue, (o,v)->o.floatValue=v);
		public static final IDbFloatField<TestObject> FLOAT_NULLABLE_FIELD = BUILDER.addFloatField("floatValueNullable", o->o.floatValueNullable, (o,v)->o.floatValueNullable=v).setNullable();
		public static final IDbForeignField<TestObject, ReferencedTestObject> FOREIGN_FIELD = BUILDER.addForeignField("foreignValue", o->o.foreignValue, (o,v)->o.foreignValue=v, ReferencedTestObject.ID_FIELD);
		public static final IDbForeignField<TestObject, ReferencedTestObject> FOREIGN_NULLABLE_FIELD = BUILDER.addForeignField("foreignValueNullable", o->o.foreignValueNullable, (o,v)->o.foreignValueNullable=v, ReferencedTestObject.ID_FIELD).setNullable();
		public static final IDbIntegerField<TestObject> INTEGER_FIELD = BUILDER.addIntegerField("integerValue", o->o.integerValue, (o,v)->o.integerValue=v);
		public static final IDbIntegerField<TestObject> INTEGER_NULLABLE_FIELD = BUILDER.addIntegerField("integerValueNullable", o->o.integerValueNullable, (o,v)->o.integerValueNullable=v).setNullable();
		public static final IDbLongField<TestObject> LONG_FIELD = BUILDER.addLongField("longValue", o->o.longValue, (o,v)->o.longValue=v);
		public static final IDbLongField<TestObject> LONG_NULLABLE_FIELD = BUILDER.addLongField("longValueNullable", o->o.longValueNullable, (o,v)->o.longValueNullable=v).setNullable();
		public static final IDbStringField<TestObject> STRING_FIELD = BUILDER.addStringField("stringValue", o->o.stringValue, (o,v)->o.stringValue=v).setMaximumLength(16);
		public static final IDbStringField<TestObject> STRING_NULLABLE_FIELD = BUILDER.addStringField("stringValueNullable", o->o.stringValueNullable, (o,v)->o.stringValueNullable=v).setMaximumLength(16).setNullable();
		public static final IDbStringField<TestObject> STRING_TINY_TEXT_FIELD = BUILDER.addStringField("stringTinyTextValue", o->o.stringTinyTextValue, (o,v)->o.stringTinyTextValue=v).setLengthBits(8);
		public static final IDbStringField<TestObject> STRING_TEXT_FIELD = BUILDER.addStringField("stringTextValue", o->o.stringTextValue, (o,v)->o.stringTextValue=v).setLengthBits(16);
		public static final IDbTimeField<TestObject> TIME_FIELD = BUILDER.addTimeField("timeValue", o->o.timeValue, (o,v)->o.timeValue=v);
		public static final IDbTimeField<TestObject> TIME_NULLABLE_FIELD = BUILDER.addTimeField("timeValueNullable", o->o.timeValueNullable, (o,v)->o.timeValueNullable=v).setNullable();
		public static final TestObjectTable TABLE = new TestObjectTable(BUILDER);
		// @formatter:on

		private Integer idValue;
		private BigDecimal decimalValue;
		private BigDecimal decimalValueNullable;
		private Boolean booleanValue;
		private Boolean booleanValueNullable;
		private byte[] bytesValue;
		private byte[] bytesValueNullable;
		private byte[] bytesTinyBlobValue;
		private byte[] bytesBlobValue;
		private Day dayValue;
		private Day dayValueNullable;
		private DayTime dayTimeValue;
		private DayTime dayTimeValueNullable;
		private Double doubleValue;
		private Double doubleValueNullable;
		private TestEnum enumValue;
		private TestEnum enumValueNullable;
		private Float floatValue;
		private Float floatValueNullable;
		private ReferencedTestObject foreignValue;
		private ReferencedTestObject foreignValueNullable;
		private Integer integerValue;
		private Integer integerValueNullable;
		private Long longValue;
		private Long longValueNullable;
		private String stringValue;
		private String stringValueNullable;
		private String stringTinyTextValue;
		private String stringTextValue;
		private Time timeValue;
		private Time timeValueNullable;

		@Override
		public TestObjectTable table() {

			return TABLE;
		}
	}

	public static class TestObjectTable extends EmfObjectTable<TestObject, EmfTestModuleInstance> {

		public TestObjectTable(IDbObjectTableBuilder<TestObject> builder) {

			super(builder);
		}
	}

	public static enum TestEnum {

		BAR,
		FOO
	}

	// -------------------------------- SimpleTestObject -------------------------------- //

	public static class SimpleTestObject extends SimpleTestObjectGenerated implements IEmfObject<SimpleTestObject> {

		@Override
		public IDisplayString toDisplayWithoutId() {

			return new DisplayString().append(SimpleTestObject.STRING_FIELD.getValue(this));
		}
	}

	public static class SimpleTestObjectGenerated extends AbstractDbObject<SimpleTestObject> {

		// @formatter:off
		public static final DbObjectTableBuilder<SimpleTestObject, SimpleTestObjectGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "SimpleTestObject", SimpleTestObject::new, SimpleTestObject.class);
		static {
			BUILDER.setTitle(IDisplayString.create("Simple Test Object"));
			BUILDER.setTitle(IDisplayString.create("Simple Test Objects"));
		}
		public static final IDbIdField<SimpleTestObject> ID_FIELD = BUILDER.addIdField("id", o->o.idValue, (o,v)->o.idValue=v);
		public static final IDbDayField<SimpleTestObject> DAY_FIELD = BUILDER.addDayField("dayValue", o->o.dayValue, (o,v)->o.dayValue=v);
		public static final IDbIntegerField<SimpleTestObject> INTEGER_FIELD = BUILDER.addIntegerField("integerValue", o->o.integerValue, (o,v)->o.integerValue=v);
		public static final IDbStringField<SimpleTestObject> STRING_FIELD = BUILDER.addStringField("stringValue", o->o.stringValue, (o,v)->o.stringValue=v).setMaximumLength(16);
		public static final SimpleTestObjectTable TABLE = new SimpleTestObjectTable(BUILDER);
		// @formatter:on

		private Integer idValue;
		private Day dayValue;
		private Integer integerValue;
		private String stringValue;

		@Override
		public SimpleTestObjectTable table() {

			return TABLE;
		}
	}

	public static class SimpleTestObjectTable extends EmfObjectTable<SimpleTestObject, EmfTestModuleInstance> {

		public SimpleTestObjectTable(IDbObjectTableBuilder<SimpleTestObject> builder) {

			super(builder);
		}
	}

	// -------------------------------- ReferencedTestObject -------------------------------- //

	public static class ReferencedTestObject extends AbstractDbObject<ReferencedTestObject> {

		// @formatter:off
		public static final DbObjectTableBuilder<ReferencedTestObject, ReferencedTestObject> BUILDER = new DbObjectTableBuilder<>("Test", "ReferencedTestObject", ReferencedTestObject::new, ReferencedTestObject.class);
		public static final IDbIdField<ReferencedTestObject> ID_FIELD = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
		public static final DbObjectTable<ReferencedTestObject> TABLE = new DbObjectTable<>(BUILDER);
		// @formatter:on

		private Integer id;

		@Override
		public DbObjectTable<ReferencedTestObject> table() {

			return TABLE;
		}
	}
}
