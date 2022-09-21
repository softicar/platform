package com.softicar.platform.db.core.connection.profiler;

import com.softicar.platform.db.core.statement.IDbStatement;
import com.softicar.platform.db.core.utils.RegexMatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;

public class DbConnectionTestProfiler implements IDbConnectionProfiler {

	private final List<IDbStatement> statements;

	public DbConnectionTestProfiler() {

		this.statements = new ArrayList<>();
	}

	@Override
	public void acceptStatementStarted(IDbStatement statement) {

		statements.add(statement);
	}

	public void assertStatement(int index, String expectedStatement) {

		Assert.assertTrue(statements.size() > index);
		Assert.assertEquals(expectedStatement, statements.get(index).getText());
	}

	public void assertStatement(int index, String expectedStatement, Object...parameters) {

		Assert.assertTrue(statements.size() > index);
		Assert.assertEquals(expectedStatement, statements.get(index).getText());
		Assert.assertEquals("wrong parameters", Arrays.asList(parameters), statements.get(index).getParameters());
	}

	public void assertStatementMatches(int index, String expectedStatementRegexp, Object...parameters) {

		assertStatementMatches(index, expectedStatementRegexp, Arrays.asList(parameters));
	}

	public void assertStatementMatches(int index, String expectedStatementRegexp, Collection<?> parameters) {

		Assert.assertTrue(statements.size() > index);
		MatcherAssert.assertThat(statements.get(index).getText(), RegexMatcher.matchesRegex(expectedStatementRegexp));
		Assert.assertEquals("wrong parameters", parameters, statements.get(index).getParameters());
	}

	public void assertStatementCount(int expectedCount) {

		String message = String
			.format(//
				"Expected %s statement but got %s:\n%s\n",
				expectedCount,
				statements.size(),
				statements.stream().map(IDbStatement::getText).collect(Collectors.joining("\n")));
		Assert.assertEquals(message, expectedCount, statements.size());
	}

	public void assertStatements(String...expectedStatements) {

		assertStatementCount(expectedStatements.length);
		for (int i = 0; i < expectedStatements.length; i++) {
			assertStatement(i, expectedStatements[i]);
		}
	}

	public void clear() {

		statements.clear();
	}
}
