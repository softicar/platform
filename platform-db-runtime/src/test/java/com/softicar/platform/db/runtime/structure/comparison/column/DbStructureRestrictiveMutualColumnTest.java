package com.softicar.platform.db.runtime.structure.comparison.column;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureRestrictiveComparerStrategyTest;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import java.util.Arrays;
import org.junit.Test;

/**
 * @author Daniel Klose
 */
public class DbStructureRestrictiveMutualColumnTest extends AbstractDbStructureRestrictiveComparerStrategyTest {

	@Test
	public void testErrorIfColumnLogicalNameNotEqual() {

		addColumnStructure(sample).setLogicalName("someOtherName");
		addColumnStructure(reference).setLogicalName("anotherName");
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnLogicalNameEqual() {

		addColumnStructure(sample).setLogicalName("someLogicalName");
		addColumnStructure(reference).setLogicalName("someLogicalName");
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnTypeNotEqual() {

		addColumnStructure(sample).setFieldType(SqlFieldType.BOOLEAN);
		addColumnStructure(reference).setFieldType(SqlFieldType.INTEGER);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnTypeEqual() {

		addColumnStructure(sample).setFieldType(SqlFieldType.BOOLEAN);
		addColumnStructure(reference).setFieldType(SqlFieldType.BOOLEAN);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnEnumValuesNotEqual() {

		addColumnStructure(sample).setEnumValues(Arrays.asList("first", "second"));
		addColumnStructure(reference).setEnumValues(Arrays.asList("first"));
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnEnumValuesEqual() {

		addColumnStructure(sample).setEnumValues(Arrays.asList("first", "second"));
		addColumnStructure(reference).setEnumValues(Arrays.asList("first", "second"));
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnBitsNotEqual() {

		addColumnStructure(sample).setBits(5);
		addColumnStructure(reference).setBits(10);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
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
	public void testErrorIfColumnMaxLengthNotEqual() {

		addColumnStructure(sample).setMaxLength(5);
		addColumnStructure(reference).setMaxLength(10);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnMaxLengthEqual() {

		addColumnStructure(sample).setMaxLength(10);
		addColumnStructure(reference).setMaxLength(10);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnLengthBitsNotEqual() {

		addColumnStructure(sample).setLengthBits(5);
		addColumnStructure(reference).setLengthBits(10);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnLengthBitsEqual() {

		addColumnStructure(sample).setLengthBits(10);
		addColumnStructure(reference).setLengthBits(10);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnCharacterSetNotEqual() {

		addColumnStructure(sample).setCharacterSet("someCharacterSet");
		addColumnStructure(reference).setCharacterSet("someOtherCharacterSet");
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnCollationNotEqual() {

		addColumnStructure(sample).setCollation("someCollation");
		addColumnStructure(reference).setCollation("someOtherCollation");
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
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
	public void testErrorIfColumnNullableFlagNotEqual() {

		addColumnStructure(sample).setNullable(false);
		addColumnStructure(reference).setNullable(true);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
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
	public void testErrorIfColumnForeignKeyConstraintNotEqual() {

		sample
			.addForeignKeyStructure(
				createForeignKeyStructure(sample)//
					.addColumnPair(addColumnStructure(sample).getNameOrThrow(), "something"));
		reference
			.addForeignKeyStructure(
				createForeignKeyStructure(reference)//
					.addColumnPair(addColumnStructure(reference).getNameOrThrow(), "other"));
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
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
	public void testErrorIfColumnDefaultValueTypeNotEqual() {

		addColumnStructure(sample).setDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP);
		addColumnStructure(reference).setDefaultType(DbColumnDefaultType.NONE);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnDefaultValueTypeEqual() {

		addColumnStructure(sample).setDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP);
		addColumnStructure(reference).setDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP);
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnDefaultValueNotEqual() {

		addColumnStructure(sample).setDefaultValue("someDefaultValue");
		addColumnStructure(reference).setDefaultValue("someOtherDefaultValue");
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnDefaultValueEqual() {

		addColumnStructure(sample).setDefaultValue("someDefaultValue");
		addColumnStructure(reference).setDefaultValue("someDefaultValue");
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}

	@Test
	public void testErrorIfColumnCommentNotEqual() {

		addColumnStructure(sample).setComment("someComment");
		addColumnStructure(reference).setComment("someOtherComment");
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testNoDiagnosticIfColumnCommentEqual() {

		addColumnStructure(sample).setComment("someComment");
		addColumnStructure(reference).setComment("someComment");
		executeColumnComparison();
		new Asserter().assertNoDiagnostics();
	}
}
