package com.softicar.platform.db.runtime.structure.comparison.column;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureCompatibleComparerStrategyTest;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import java.util.Arrays;
import org.junit.Test;

public class DbStructureCompatibleSampleExclusiveColumnTest extends AbstractDbStructureCompatibleComparerStrategyTest {

	@Test
	public void testErrorIfSurplusColumnNotNegligibleNullable() {

		addColumnStructure(sample).setNullable(false).setDefaultType(DbColumnDefaultType.NONE);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfSurplusColumnNegligibleWithNullable() {

		addColumnStructure(sample).setNullable(true).setDefaultType(DbColumnDefaultType.NONE);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfSurplusColumnNegligibleWithDefault() {

		addColumnStructure(sample).setNullable(false).setDefaultType(DbColumnDefaultType.NORMAL);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testInfoIfColumnLogicalNameNotEqual() {

		addColumnStructure(sample).setLogicalName("anotherName");
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyInfos(1);
	}

	@Test
	public void testErrorIfColumnTypeNotEqualWithErroneuousDifferences() {

		addColumnStructure(sample).setFieldType(SqlFieldType.BOOLEAN);
		addColumnStructure(reference);
		addOtherColumnStructure(sample).setFieldType(SqlFieldType.DOUBLE);
		addOtherColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(2);
	}

	@Test
	public void testErrorIfColumnEnumValuesSampleHasMore() {

		addColumnStructure(sample).setEnumValues(Arrays.asList("first", "second"));
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnBitsSampleGreater() {

		addColumnStructure(sample).setBits(64);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnUnsignedFlagNotEqual() {

		addColumnStructure(sample).setUnsigned(true);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnMaxLengthSampleGreater() {

		addColumnStructure(sample).setMaxLength(10);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnLengthBitsSampleGreater() {

		addColumnStructure(sample).setLengthBits(10);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testWarningIfColumnCharacterSetNotEqual() {

		addColumnStructure(sample).setCharacterSet("someOtherCharacterSet");
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testWarningIfColumnCollationNotEqual() {

		addColumnStructure(sample).setCollation("someOtherCollation");
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testErrorIfColumnPrecisionNotEqual() {

		addColumnStructure(sample).setPrecision(10);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnScaleNotEqual() {

		addColumnStructure(sample).setScale(10);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnNullableFlagOnlySample() {

		addColumnStructure(sample).setNullable(true);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnAutoIncrementFlagNotEqual() {

		addColumnStructure(sample).setAutoIncrement(true);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnBaseColumnFlagNotEqual() {

		addColumnStructure(sample).setBase(true);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testWarningIfColumnDefaultValueTypeNotEqual() {

		addColumnStructure(sample).setDefaultType(DbColumnDefaultType.NORMAL);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyWarnings(1);
	}

	@Test
	public void testWarningIfColumnDefaultValueNotEqual() {

		addColumnStructure(sample).setDefaultValue("someOtherDefaultValue");
		addColumnStructure(reference);
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
