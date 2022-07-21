package com.softicar.platform.db.runtime.structure.comparison.column;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureRestrictiveComparerStrategyTest;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import java.util.Arrays;
import org.junit.Test;

/**
 * @author Daniel Klose
 */
public class DbStructureRestrictiveSampleExclusiveColumnTest extends AbstractDbStructureRestrictiveComparerStrategyTest {

	@Test
	public void testErrorIfSurplusColumn() {

		addColumnStructure(sample);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnLogicalNameNotEqual() {

		addColumnStructure(sample).setLogicalName("someOtherName");
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnTypeNotEqual() {

		addColumnStructure(sample).setFieldType(SqlFieldType.BOOLEAN);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnEnumValuesNotEqual() {

		addColumnStructure(sample).setEnumValues(Arrays.asList("first", "second"));
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnBitsNotEqual() {

		addColumnStructure(sample).setBits(5);
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
	public void testErrorIfColumnMaxLengthNotEqual() {

		addColumnStructure(sample).setMaxLength(5);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnLengthBitsNotEqual() {

		addColumnStructure(sample).setLengthBits(5);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnCharacterSetNotEqual() {

		addColumnStructure(sample).setCharacterSet("someCharacterSet");
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnCollationNotEqual() {

		addColumnStructure(sample).setCollation("someCollation");
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnPrecisionNotEqual() {

		addColumnStructure(sample).setPrecision(5);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnScaleNotEqual() {

		addColumnStructure(sample).setScale(5);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnNullableFlagNotEqual() {

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
	public void testErrorIfColumnDefaultValueTypeNotEqual() {

		addColumnStructure(sample).setDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP);
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnDefaultValueNotEqual() {

		addColumnStructure(sample).setDefaultValue("someDefaultValue");
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnCommentNotEqual() {

		addColumnStructure(sample).setComment("someComment");
		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}
}
