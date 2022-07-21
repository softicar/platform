package com.softicar.platform.db.runtime.structure.comparison.column;

import com.softicar.platform.db.runtime.structure.comparison.strategy.AbstractDbStructureRestrictiveComparerStrategyTest;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import java.util.Arrays;
import org.junit.Test;

/**
 * @author Daniel Klose
 */
public class DbStructureRestrictiveReferenceExclusiveColumnTest extends AbstractDbStructureRestrictiveComparerStrategyTest {

	@Test
	public void testErrorIfSurplusColumn() {

		addColumnStructure(reference);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnLogicalNameNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setLogicalName("someOtherName");
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnTypeNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setFieldType(SqlFieldType.BOOLEAN);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnEnumValuesNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setEnumValues(Arrays.asList("first", "second"));
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnBitsNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setBits(5);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnUnsignedFlagNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setUnsigned(true);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnMaxLengthNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setMaxLength(5);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnLengthBitsNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setLengthBits(5);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnCharacterSetNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setCharacterSet("someCharacterSet");
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnCollationNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setCollation("someCollation");
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnPrecisionNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setPrecision(5);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnScaleNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setScale(5);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnNullableFlagNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setNullable(true);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
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

		addColumnStructure(sample);
		addColumnStructure(reference).setBase(true);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnDefaultValueTypeNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setDefaultType(DbColumnDefaultType.CURRENT_TIMESTAMP);
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnDefaultValueNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setDefaultValue("someDefaultValue");
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}

	@Test
	public void testErrorIfColumnCommentNotEqual() {

		addColumnStructure(sample);
		addColumnStructure(reference).setComment("someComment");
		executeColumnComparison();
		new Asserter().assertOnlyErrors(1);
	}
}
