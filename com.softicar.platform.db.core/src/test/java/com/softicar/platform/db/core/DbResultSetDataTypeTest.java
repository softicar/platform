package com.softicar.platform.db.core;

import java.math.BigDecimal;
import org.junit.Test;

/**
 * Test cases for {@link DbResultSet}.
 *
 * @author Oliver Richers
 */
public class DbResultSetDataTypeTest extends AbstractDbResultSetTest {

	@Test
	public void defaultsToBigDecimal() {

		assertClassAndValue(new BigDecimal("3.6"), "SELECT 3.6");
	}

	@Test
	public void defaultsToLong() {

		assertClassAndValue(8000000000L, "SELECT 8000000000");
	}

	@Test
	public void defaultsToString() {

		assertClassAndValue("1337", "SELECT '1337'");
	}
}
