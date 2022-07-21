package com.softicar.platform.db.runtime.structure.comparison.column;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import java.util.Arrays;
import org.junit.Test;

public class DbStructureCompatibleReferenceExclusiveColumnTest extends AbstractDbStructureCompatibleComparerStrategyTest {

	@Test
	public void testErrorIfSurplusColumnNotNegligibleNullable() {

		addColumnStructure(reference).setNullable(false).setDefaultType(DbColumnDefaultType.NONE);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testInfoIfSurplusColumnNegligibleWithNullable() {

		addColumnStructure(reference).setNullable(true).setDefaultType(DbColumnDefaultType.NONE);
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testInfoIfSurplusColumnNegligibleWithDefault() {

		addColumnStructure(reference).setNullable(false).setDefaultType(DbColumnDefaultType.NORMAL);
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testInfoIfColumnLogicalNameNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setLogicalName("anotherName");
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testErrorIfColumnTypeNotEqualWithErroneuousDifferences() {

		addColumnStructure(sample);
		addColumnStructure(reference).setFieldType(SqlFieldType.BOOLEAN);
		addOtherColumnStructure(sample);
		addOtherColumnStructure(reference).setFieldType(SqlFieldType.DOUBLE);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(2);
	}

	@Test
	public void testWarningIfColumnEnumValuesReferenceHasMore() {

		addColumnStructure(sample);
		addColumnStructure(reference).setEnumValues(Arrays.asList("first", "second"));
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testInfoIfColumnBitsReferenceGreater() {

		addColumnStructure(sample);
		addColumnStructure(reference).setBits(64);
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testErrorIfColumnUnsignedFlagNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setUnsigned(true);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testInfoIfColumnMaxLengthReferenceGreater() {

		addColumnStructure(sample);
		addColumnStructure(reference).setMaxLength(10);
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testInfoIfColumnLengthBitsReferenceGreater() {

		addColumnStructure(sample);
		addColumnStructure(reference).setLengthBits(10);
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testWarningIfColumnCharacterSetNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setCharacterSet("someOtherCharacterSet");
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testWarningIfColumnCollationNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setCollation("someOtherCollation");
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testErrorIfColumnPrecisionNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setPrecision(10);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnScaleNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setScale(10);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testWarningIfColumnNullableFlagOnlyReference() {

		addColumnStructure(sample);
		addColumnStructure(reference).setNullable(true);
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testErrorIfColumnAutoIncrementFlagNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setAutoIncrement(true);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnBaseColumnFlagNotEqual() {

		addColumnStructure(sample).setBase(false);
		addColumnStructure(reference).setBase(true);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testWarningIfColumnDefaultValueTypeNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setDefaultType(DbColumnDefaultType.NORMAL);
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testWarningIfColumnDefaultValueNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setDefaultValue("someOtherDefaultValue");
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testInfoIfColumnCommentNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setComment("someComment");
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}
}
