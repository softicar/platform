package com.softicar.platform.db.sql.selects;

import com.softicar.platform.common.container.data.table.IDataTableRowMethods;
import com.softicar.platform.common.container.iterator.LimitingIterator;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.DbResultSetIterator;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.sql.ISqlStatement;
import com.softicar.platform.db.sql.selects.SqlSelectCore.PartBuilder;
import java.util.Iterator;

/**
 * Front-end to fetch the result rows of an SQL select.
 *
 * @author Oliver Richers
 */
public abstract class AbstractSqlSelect<R> extends SqlSelectBase implements IDataTableRowMethods<R> {

	public final R getOne() {

		Iterator<R> iterator = iterator(0, 2);
		R element = iterator.hasNext()? iterator.next() : null;
		if (iterator.hasNext()) {
			throw new RuntimeException("The SQL query returned more than one row, which was not expected.");
		}
		return element;
	}

	public final R getFirst() {

		Iterator<R> iterator = iterator(0, 1);
		return iterator.hasNext()? iterator.next() : null;
	}

	@Override
	public Iterator<R> iterator(int offset, int limit) {

		if (DbConnections.getServerQuirks().isLimitAndOffsetSupported()) {
			return createResultSetIterator(offset, limit);
		} else {
			return new LimitingIterator<>(createResultSetIterator(0, 0), offset, limit);
		}
	}

	@Override
	public final int count() {

		// FIXME: this should be optimized
		int n = 0;
		try (DbResultSet resultSet = executeToResultSet(0, 0)) {
			while (resultSet.next()) {
				++n;
			}
		}
		return n;
	}

	public final ISqlStatement getStatement() {

		return getCore()._build(0, 0);
	}

	@Override
	public final String toString() {

		ISqlStatement statement = getStatement();
		return statement.getText() + " " + statement.getParameters();
	}

	protected abstract R getResultRow(DbResultSet resultSet);

	private DbResultSetIterator<R> createResultSetIterator(int offset, int limit) {

		return new DbResultSetIterator<>(() -> executeToResultSet(offset, limit), this::getResultRow);
	}

	private final DbResultSet executeToResultSet(int offset, int limit) {

		PartBuilder partBuilder = getCore()._build(offset, limit);
		return new DbStatement()//
			.addTables(core.getTables())
			.addText(partBuilder.getText())
			.addParameters(partBuilder.getParameters())
			.executeQuery();
	}
}
