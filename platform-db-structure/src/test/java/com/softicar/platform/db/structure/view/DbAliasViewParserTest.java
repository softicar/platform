package com.softicar.platform.db.structure.view;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.core.table.DbTableName;
import org.junit.Test;

public class DbAliasViewParserTest extends AbstractTest {

	private static final DbTableName VIEW_NAME = new DbTableName("db", "view");

	@Test
	public void testWithSimpleQuery() {

		DbViewStructure viewStructure = new DbViewStructure(VIEW_NAME, "SELECT a.b AS x, b.c AS y FROM db.foo");
		DbAliasView aliasView = new DbAliasViewParser(viewStructure).parse();

		assertEquals(VIEW_NAME, aliasView.getViewName());
		assertEquals(new DbTableName("db", "foo"), aliasView.getTableName());
		assertEquals("[x=b, y=c]", aliasView.getColumnMap().toString());
	}

	@Test
	public void testWithUnqualifiedTableName() {

		DbViewStructure viewStructure = new DbViewStructure(VIEW_NAME, "SELECT a.b AS x, b.c AS y FROM unqualified");
		DbAliasView aliasView = new DbAliasViewParser(viewStructure).parse();

		assertEquals(new DbTableName("db", "unqualified"), aliasView.getTableName());
	}

	@Test
	public void testWithQuotedIdentifiers() {

		DbViewStructure viewStructure = new DbViewStructure(VIEW_NAME, "SELECT `x`.`y` AS `a` FROM `db`.`foo`");
		DbAliasView aliasView = new DbAliasViewParser(viewStructure).parse();

		assertEquals(VIEW_NAME, aliasView.getViewName());
		assertEquals(new DbTableName("db", "foo"), aliasView.getTableName());
		assertEquals("[a=y]", aliasView.getColumnMap().toString());
	}
}
