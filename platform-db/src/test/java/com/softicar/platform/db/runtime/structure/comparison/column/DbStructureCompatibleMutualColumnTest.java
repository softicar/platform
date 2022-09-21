package com.softicar.platform.db.runtime.structure.comparison.column;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import java.util.Arrays;
import org.junit.Test;

public class DbStructureCompatibleMutualColumnTest extends AbstractDbStructureCompatibleComparerStrategyTest {

	@Test
	public void testInfoIfColumnLogicalNameNotEqual() {

		addColumnStructure(sample).setLogicalName("someOtherName");
		addColumnStructure(reference).setLogicalName("anotherName");
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testNoDiagnosticIfColumnLogicalNameEqual() {

		addColumnStructure(sample).setLogicalName("someLogicalName");
		addColumnStructure(reference).setLogicalName("someLogicalName");
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnTypeNotEqualWithErroneuousDifferences() {

		addColumnStructure(sample).setFieldType(SqlFieldType.INTEGER);
		addColumnStructure(reference).setFieldType(SqlFieldType.BOOLEAN);
		addOtherColumnStructure(sample).setFieldType(SqlFieldType.INTEGER);
		addOtherColumnStructure(reference).setFieldType(SqlFieldType.DOUBLE);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(2);
	}

	@Test
	public void testWarningIfColumnTypeNotEqualWithUnsafeDifference() {

		addColumnStructure(sample).setFieldType(SqlFieldType.INTEGER);
		addColumnStructure(reference).setFieldType(SqlFieldType.LONG);
		addOtherColumnStructure(sample).setFieldType(SqlFieldType.FLOAT);
		addOtherColumnStructure(reference).setFieldType(SqlFieldType.DOUBLE);
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(2);
	}

	@Test
	public void testNoDiagnosticIfColumnTypeEqual() {

		addColumnStructure(sample).setFieldType(SqlFieldType.BOOLEAN);
		addColumnStructure(reference).setFieldType(SqlFieldType.BOOLEAN);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnEnumValuesSampleHasMore() {

		addColumnStructure(sample).setEnumValues(Arrays.asList("first", "second"));
		addColumnStructure(reference).setEnumValues(Arrays.asList("first"));
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testWarningIfColumnEnumValuesReferenceHasMore() {

		addColumnStructure(sample).setEnumValues(Arrays.asList("first"));
		addColumnStructure(reference).setEnumValues(Arrays.asList("first", "second"));
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testNoDiagnosticIfColumnEnumValuesEqual() {

		addColumnStructure(sample).setEnumValues(Arrays.asList("first", "second"));
		addColumnStructure(reference).setEnumValues(Arrays.asList("first", "second"));
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnBitsReferenceSmaller() {

		addColumnStructure(sample).setBits(10);
		addColumnStructure(reference).setBits(5);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testInfoIfColumnBitsReferenceGreater() {

		addColumnStructure(sample).setBits(5);
		addColumnStructure(reference).setBits(10);
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testNoDiagnosticIfColumnBitsEqual() {

		addColumnStructure(sample).setBits(10);
		addColumnStructure(reference).setBits(10);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnUnsignedFlagNotEqual() {

		addColumnStructure(sample).setUnsigned(false);
		addColumnStructure(reference).setUnsigned(true);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnUnsignedFlagEqual() {

		addColumnStructure(sample).setUnsigned(true);
		addColumnStructure(reference).setUnsigned(true);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnMaxLengthReferenceSmaller() {

		addColumnStructure(sample).setMaxLength(10);
		addColumnStructure(reference).setMaxLength(5);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testInfoIfColumnMaxLengthReferenceGreater() {

		addColumnStructure(sample).setMaxLength(5);
		addColumnStructure(reference).setMaxLength(10);
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testNoDiagnosticIfColumnMaxLengthEqual() {

		addColumnStructure(sample).setMaxLength(10);
		addColumnStructure(reference).setMaxLength(10);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnLengthBitsReferenceSmaller() {

		addColumnStructure(sample).setLengthBits(10);
		addColumnStructure(reference).setLengthBits(5);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testInfoIfColumnLengthBitsReferenceGreater() {

		addColumnStructure(sample).setLengthBits(5);
		addColumnStructure(reference).setLengthBits(10);
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testNoDiagnosticIfColumnLengthBitsEqual() {

		addColumnStructure(sample).setLengthBits(10);
		addColumnStructure(reference).setLengthBits(10);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testWarningIfColumnCharacterSetNotEqual() {

		addColumnStructure(sample).setCharacterSet("someCharacterSet");
		addColumnStructure(reference).setCharacterSet("someOtherCharacterSet");
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testWarningIfColumnCollationNotEqual() {

		addColumnStructure(sample).setCollation("someCollation");
		addColumnStructure(reference).setCollation("someOtherCollation");
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testNoDiagnosticIfColumnCollationEqual() {

		addColumnStructure(sample).setCollation("someCollation");
		addColumnStructure(reference).setCollation("someCollation");
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnPrecisionNotEqual() {

		addColumnStructure(sample).setPrecision(5);
		addColumnStructure(reference).setPrecision(10);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnPrecisionEqual() {

		addColumnStructure(sample).setPrecision(10);
		addColumnStructure(reference).setPrecision(10);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnScaleNotEqual() {

		addColumnStructure(sample).setScale(5);
		addColumnStructure(reference).setScale(10);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnScaleEqual() {

		addColumnStructure(sample).setScale(10);
		addColumnStructure(reference).setScale(10);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnNullableFlagOnlySample() {

		addColumnStructure(sample).setNullable(true);
		addColumnStructure(reference).setNullable(false);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testWarningIfColumnNullableFlagOnlyReference() {

		addColumnStructure(sample).setNullable(false);
		addColumnStructure(reference).setNullable(true);
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testNoDiagnosticIfColumnNullableFlagEqual() {

		addColumnStructure(sample).setNullable(false);
		addColumnStructure(reference).setNullable(false);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnAutoIncrementFlagNotEqual() {

		addColumnStructure(sample).setAutoIncrement(false);
		addColumnStructure(reference).setAutoIncrement(true);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnAutoIncrementFlagEqual() {

		addColumnStructure(sample).setAutoIncrement(false);
		addColumnStructure(reference).setAutoIncrement(false);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnBaseColumnFlagNotEqual() {

		addColumnStructure(sample).setBase(false);
		addColumnStructure(reference).setBase(true);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnBaseColumnFlagEqual() {

		addColumnStructure(sample).setBase(true);
		addColumnStructure(reference).setBase(true);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testNoDiagnosticIfColumnForeignKeyConstraintEqual() {

		sample
			.addForeignKeyStructure(
				createForeignKeyStructure(sample)//
					.addColumnPair(addColumnStructure(sample).getNameOrThrow(), "something"));
		reference
			.addForeignKeyStructure(
				createForeignKeyStructure(reference)//
					.addColumnPair(addColumnStructure(reference).getNameOrThrow(), "something"));
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testWarningIfColumnDefaultValueTypeNotEqual() {

		addColumnStructure(sample).setDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP);
		addColumnStructure(reference).setDefaultType(DbColumnDefaultType.NONE);
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testNoDiagnosticIfColumnDefaultValueTypeEqual() {

		addColumnStructure(sample).setDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP);
		addColumnStructure(reference).setDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testWarningIfColumnDefaultValueNotEqual() {

		addColumnStructure(sample).setDefaultValue("someDefaultValue");
		addColumnStructure(reference).setDefaultValue("someOtherDefaultValue");
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testNoDiagnosticIfColumnDefaultValueEqual() {

		addColumnStructure(sample).setDefaultValue("someDefaultValue");
		addColumnStructure(reference).setDefaultValue("someDefaultValue");
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testInfoIfColumnCommentNotEqual() {

		addColumnStructure(sample).setComment("someComment");
		addColumnStructure(reference).setComment("someOtherComment");
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testNoDiagnosticIfColumnCommentEqual() {

		addColumnStructure(sample).setComment("someComment");
		addColumnStructure(reference).setComment("someComment");
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}
}
