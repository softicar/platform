package com.softicar.platform.db.core.sql.parser;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.stream.Collectors;
import org.junit.Test;

public class DbSqlTokenizerTest extends AbstractTest {

	@Test
	public void testWithSimpleCreateTableStatement() {

		String sql = "CREATE TABLE db.foo (id INT, name VARCHAR(255));";

		assertTokens("$CREATE $TABLE $db . $foo ( $id $INT , $name $VARCHAR ( #255 ) ) ;", sql);
	}

	@Test
	public void testWithSimpleSelectStatement() {

		String sql = "SELECT COUNT(*) FROM db.foo";

		assertTokens("$SELECT $COUNT ( * ) $FROM $db . $foo", sql);
	}

	@Test
	public void testWithSimpleInsertStatement() {

		String sql = "INSERT INTO db.foo (x,y) VALUES ('a',3.14), ('b',-2.41)";

		assertTokens("$INSERT $INTO $db . $foo ( $x , $y ) $VALUES ( ' str(a) ' , #3.14 ) , ( ' str(b) ' , - #2.41 )", sql);
	}

	// ------------------------------ numbers ------------------------------ //

	@Test
	public void testWithExponentalNotation() {

		String sql = "SELECT 3.14e-12 AS x FROM foo";

		assertTokens("$SELECT #3.14e-12 $AS $x $FROM $foo", sql);
	}

	@Test
	public void testWithNegativeNumberAndExponentalNotation() {

		String sql = "SELECT -3.14e-12 AS x FROM foo";

		assertTokens("$SELECT - #3.14e-12 $AS $x $FROM $foo", sql);
	}

	@Test
	public void testWithAdditionOfNegativeNumber() {

		String sql = "SELECT a+-3 AS x FROM foo";

		assertTokens("$SELECT $a + - #3 $AS $x $FROM $foo", sql);
	}

	// ------------------------------ quoting ------------------------------ //

	@Test
	public void testWithQualifiedIdentifiers() {

		String sql = "db.foo.a";

		assertTokens("$db . $foo . $a", sql);
	}

	@Test
	public void testWithQuotedQualifiedIdentifier() {

		String sql = "`db`.`foo`.`a`";

		assertTokens("` str(db) ` . ` str(foo) ` . ` str(a) `", sql);
	}

	@Test
	public void testWithSingleQuoteLiteral() {

		String sql = "WHERE foo.x = 'hello world'";

		assertTokens("$WHERE $foo . $x = ' str(hello world) '", sql);
	}

	@Test
	public void testWithDoubleQuoteLiteral() {

		String sql = "WHERE foo.x = \"hello world\"";

		assertTokens("$WHERE $foo . $x = \" str(hello world) \"", sql);
	}

	@Test
	public void testWithEmptyString() {

		String sql = "WHERE foo.x = ''";

		assertTokens("$WHERE $foo . $x = ' str() '", sql);
	}

	@Test
	public void testWithEscapedSingleQuote() {

		String sql = "a 'foo''bar' b";

		assertTokens("$a ' str(foo'bar) ' $b", sql);
	}

	// ------------------------------ backslash ------------------------------ //

	@Test
	public void testWithBackslashSequence() {

		String sql = "'a\\n\\r\\t\\\\b'";

		assertTokens("' str(a\n\r\t\\b) '", sql);
	}

	@Test
	public void testWithIllegalBackslashSequence() {

		String sql = "'a\\xb'";

		assertTokens("' str(axb) '", sql);
	}

	// ------------------------------ illegal characters ------------------------------ //

	@Test
	public void testWithIllegalCharacters() {

		String sql = "SELECT @1";

		assertTokens("$SELECT ILLEGAL(@) #1", sql);
	}

	// ------------------------------ whitespace ------------------------------ //

	@Test
	public void testWithWhitespace() {

		String sql = "SELECT COUNT(*)\nFROM\tfoo";

		assertTokens("$SELECT ws( ) $COUNT ( * ) ws(\n) $FROM ws(\t) $foo", sql, false);
	}

	// ------------------------------ private ------------------------------ //

	private void assertTokens(String expectedTokenString, String sql) {

		assertTokens(expectedTokenString, sql, true);
	}

	private void assertTokens(String expectedTokenString, String sql, boolean skipWhitespace) {

		String actualTokenString = new DbSqlTokenizer(sql)//
			.setSkipWhiteSpace(skipWhitespace)
			.tokenize()
			.stream()
			.map(this::toString)
			.collect(Collectors.joining(" "));
		assertEquals(expectedTokenString, actualTokenString);
	}

	private String toString(DbSqlToken token) {

		return String.format(getFormatString(token.getType()), token.getText());
	}

	private String getFormatString(DbSqlTokenType type) {

		switch (type) {
		case IDENTIFIER:
			return "$%s";
		case ILLEGAL:
			return "ILLEGAL(%s)";
		case NUMBER:
			return "#%s";
		case STRING:
			return "str(%s)";
		case SYMBOL:
			return "%s";
		case WHITESPACE:
			return "ws(%s)";
		}
		throw new SofticarUnknownEnumConstantException(type);
	}
}
