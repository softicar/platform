package com.softicar.platform.db.structure.view;

import com.softicar.platform.common.code.QualifiedName;
import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.core.sql.parser.DbSqlScanner;
import com.softicar.platform.db.core.table.DbTableName;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses an SQL query statement into a {@link DbAliasView}.
 *
 * @author Oliver Richers
 */
public class DbAliasViewParser {

	private static final String IDENTIFIER_PATTERN = "[a-zA-Z0-9_$]+";
	private final IDbViewStructure viewStructure;
	private DbSqlScanner scanner;
	private DbAliasViewColumnMap columnMap;

	public DbAliasViewParser(IDbViewStructure viewStructure) {

		this.viewStructure = viewStructure;
	}

	public DbAliasView parse() {

		this.scanner = new DbSqlScanner(viewStructure.getViewQuery());
		this.columnMap = new DbAliasViewColumnMap();

		scanner.assertToken("SELECT");
		parseColumnMapping();
		while (true) {
			String token = scanner.assertAnyToken(",", "FROM");
			if (token.equals(",")) {
				parseColumnMapping();
			} else {
				DbTableName targetTableName = parseTableName();
				assertEndOfStatement();
				return new DbAliasView(viewStructure.getViewName(), targetTableName, columnMap);
			}
		}
	}

	private void assertEndOfStatement() {

		if (scanner.hasMoreTokens()) {
			throw new SofticarException(//
				"Expected end of statement but got '%s'. Parsed tokens: %s",
				scanner.consumeCurrentToken(),
				scanner.getScannedTokens().toString());
		}
	}

	private DbTableName parseTableName() {

		QualifiedName qualifiedName = parseQualifiedName();
		List<String> segments = qualifiedName.getSegments();
		if (segments.size() == 1) {
			return new DbTableName(viewStructure.getViewName().getDatabaseName(), segments.get(0));
		} else if (segments.size() == 2) {
			return new DbTableName(segments.get(0), segments.get(1));
		} else {
			throw new SofticarException(//
				"Illegal table name '%s', should have exactly two segments.",
				qualifiedName);
		}
	}

	private void parseColumnMapping() {

		QualifiedName qualifiedName = parseQualifiedName();
		scanner.assertToken("AS");
		String alias = parseIdentifier();
		columnMap.addColumnPair(alias, getLastSegment(qualifiedName));
	}

	private QualifiedName parseQualifiedName() {

		List<String> segments = new ArrayList<>();
		segments.add(parseIdentifier());
		while (scanner.testAndConsumeTokenIfMatched(".")) {
			segments.add(parseIdentifier());
		}
		return new QualifiedName(segments);
	}

	private String parseIdentifier() {

		if (scanner.testCurrentTokenAnyOf("`", "'")) {
			return scanner.getQuotedToken();
		} else if (scanner.peekCurrentToken().matches(IDENTIFIER_PATTERN)) {
			return scanner.consumeCurrentToken();
		} else {
			throw new SofticarException(//
				"Expected identifier but got '%s'. Parsed tokens: %s",
				scanner.consumeCurrentToken(),
				scanner.getScannedTokens());
		}
	}

	private static String getLastSegment(QualifiedName qualifiedName) {

		List<String> segments = qualifiedName.getSegments();
		return segments.get(segments.size() - 1);
	}
}
