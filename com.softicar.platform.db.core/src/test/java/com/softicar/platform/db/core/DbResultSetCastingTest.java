package com.softicar.platform.db.core;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test cases of casting in {@link DbResultSet}.
 *
 * @author Oliver Richers
 */
public class DbResultSetCastingTest extends AbstractDbResultSetTest {

	@Test
	public void castsToBigDecimal() {

		assertClassAndValue(new BigDecimal("1337.000"), "SELECT CAST('1337' AS DECIMAL(10,3))");
	}

	@Test
	@Ignore("TODO")
	public void castsToLong() {

		assertClassAndValue(1337L, "SELECT CAST('1337' AS SIGNED)");
		assertClassAndValue(1337L, "SELECT CAST('1337' AS SIGNED INTEGER)");
	}

	@Test
	@Ignore("TODO")
	public void castsToBigInteger() {

		assertClassAndValue(new BigInteger("1337"), "SELECT CAST('1337' AS UNSIGNED)");
		assertClassAndValue(new BigInteger("1337"), "SELECT CAST('1337' AS UNSIGNED INTEGER)");
	}

	@Test
	@Ignore("TODO")
	public void castsToSqlDate() {

		Date date = new Date(Day.fromYMD(2010, 1, 1).toDate().getTime());
		assertClassAndValue(date, "SELECT CAST('2010-01-01' AS DATE)");
		assertClassAndValue(date, "SELECT CAST('2010-01-01 13:55:20' AS DATE)");
	}

	@Test
	public void castsDayStringToTimestamp() {

		assertClassAndValue(new Timestamp(Day.fromYMD(2010, 1, 1).toMillis()), "SELECT CAST('2010-01-01' AS DATETIME)");
	}

	@Test
	public void castsDayTimeStringToTimestamp() {

		assertClassAndValue(new Timestamp(DayTime.fromYMD_HMS(2010, 1, 1, 13, 55, 20).toMillis()), "SELECT CAST('2010-01-01 13:55:20' AS DATETIME)");
	}

	@Test
	public void castsToString() {

		assertClassAndValue("1337", "SELECT CAST(1337 AS CHAR)");
	}
}
