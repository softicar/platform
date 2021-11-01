package com.softicar.platform.db.structure.enums;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowBigDecimalValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowBooleanValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowDoubleValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowFloatValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowIntegerValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowLongValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowNullValue;
import com.softicar.platform.db.structure.enums.value.DbEnumTableRowStringValue;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

public class InternalDbEnumTableRowValueFromUntypedValueFactoryTest extends Assert {

	@Test
	public void testWithAllSupportedTypes() {

		assertValue(DbEnumTableRowBigDecimalValue.class, new BigDecimal("3.14"));
		assertValue(DbEnumTableRowBooleanValue.class, true);
		assertValue(DbEnumTableRowBooleanValue.class, false);
		assertValue(DbEnumTableRowDoubleValue.class, 3.14d);
		assertValue(DbEnumTableRowFloatValue.class, 3.14f);
		assertValue(DbEnumTableRowIntegerValue.class, 123);
		assertValue(DbEnumTableRowLongValue.class, 321L);
		assertValue(DbEnumTableRowStringValue.class, "foo");
	}

	@Test
	public void testWithNull() {

		assertValue(DbEnumTableRowNullValue.class, null);
	}

	@Test(expected = DbIllegalEnumTableRowValueClassException.class)
	public void testWithUnsupportedType() {

		new InternalDbEnumTableRowValueFromUntypedValueFactory(Day.today()).create();
	}

	private void assertValue(Class<?> expectedClass, Object untypedValue) {

		IDbEnumTableRowValue enumTableRowValue = new InternalDbEnumTableRowValueFromUntypedValueFactory(untypedValue).create();

		assertTrue(expectedClass.isInstance(enumTableRowValue));
		assertEquals(untypedValue + "", enumTableRowValue.toString());
	}
}
