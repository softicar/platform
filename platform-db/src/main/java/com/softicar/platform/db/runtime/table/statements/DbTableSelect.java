package com.softicar.platform.db.runtime.table.statements;

import com.softicar.platform.common.container.iterator.LimitingIterator;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.DbResultSetIterator;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.sql.ISqlTable;
import com.softicar.platform.db.sql.statement.SqlSelectLock;
import com.softicar.platform.db.sql.statement.base.AbstractSqlSelect;
import java.util.Iterator;

public class DbTableSelect<R> extends AbstractSqlSelect<R> {

	private final ISqlTable<R> table;
	private final SqlSelectLock lock;

	public DbTableSelect(ISqlTable<R> table, SqlSelectLock lock) {

		super(table);

		this.table = table;
		this.lock = lock;
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
	public int count() {

		try (DbResultSet resultSet = new DbStatement()//
			.addTables(getBuilder().getTables())
			.addText(getBuilder().getCountText().toString())
			.addParameters(getBuilder().getParameters())
			.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DbException("Failed to count number of returned query rows.");
			}
		}
	}

	private DbResultSetIterator<R> createResultSetIterator(int offset, int limit) {

		return new DbResultSetIterator<>(() -> executeToResultSet(offset, limit), resultSet -> table.getValue(resultSet, 1));
	}

	private DbResultSet executeToResultSet(int offset, int limit) {

		// build statement text and parameters
		StringBuilder textBuilder = getBuilder().getSelectText();

		// add limit
		if (limit > 0 && offset > 0) {
			textBuilder.append(" LIMIT " + offset + ", " + limit);
		} else if (limit > 0) {
			textBuilder.append(" LIMIT " + limit);
		} else if (offset > 0) {
			throw new DbException("Offset not allowed without limit.");
		}

		// locking
		if (lock != null) {
			textBuilder.append(" " + lock.getText());
		}

		// execute query
		return new DbStatement()//
			.addTables(getBuilder().getTables())
			.addText(textBuilder.toString())
			.addParameters(getBuilder().getParameters())
			.executeQuery();
	}
}
