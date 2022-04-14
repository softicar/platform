package com.softicar.platform.db.core.dbms.mysql;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class DbMysqlIdentifierValidatorTest extends AbstractTest {

	@Test
	public void testAssertValidDatabaseName() {

		DbMysqlIdentifierValidator.assertValidDatabaseName("b AR$123_");
	}

	@Test(expected = NullPointerException.class)
	public void testAssertValidDatabaseNameWithNull() {

		DbMysqlIdentifierValidator.assertValidDatabaseName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidDatabaseNameWithInvalidCharacter() {

		DbMysqlIdentifierValidator.assertValidDatabaseName("b AR$123_;");
	}

	@Test
	public void testAssertValidSimpleTableName() {

		DbMysqlIdentifierValidator.assertValidSimpleTableName("b AR$123_");
	}

	@Test(expected = NullPointerException.class)
	public void testAssertValidSimpleTableNameWithNull() {

		DbMysqlIdentifierValidator.assertValidSimpleTableName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidSimpleTableNameWithInvalidCharacter() {

		DbMysqlIdentifierValidator.assertValidSimpleTableName("b AR$123_;");
	}

	@Test
	public void testAssertValidVariableName() {

		DbMysqlIdentifierValidator.assertValidVariableName("b AR$123_.");
	}

	@Test(expected = NullPointerException.class)
	public void testAssertValidVariableNameWithNull() {

		DbMysqlIdentifierValidator.assertValidVariableName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidVariableNameWithInvalidCharacter() {

		DbMysqlIdentifierValidator.assertValidVariableName("b AR$123_.;");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAssertValidVariableNameWithExceededLength() {

		String variableName = "";
		for (int i = 0; i < 65; i++) {
			variableName += "x";
		}

		DbMysqlIdentifierValidator.assertValidVariableName(variableName);
	}
}
