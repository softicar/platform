package com.softicar.platform.db.core.utils;

import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.SofticarSqlException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbResultSetConverter {

	private final DbResultSet resultSet;

	public DbResultSetConverter(DbResultSet resultSet) {

		this.resultSet = resultSet;
	}

	public Optional<DbResultSetRow> toRow() {

		List<DbResultSetRow> rows = toRows();
		return rows.size() == 1? Optional.of(rows.get(0)) : Optional.empty();
	}

	public List<DbResultSetRow> toRows() {

		try {
			final int columnCount = resultSet.getMetaData().getColumnCount();
			List<DbResultSetRow> rows = new ArrayList<>();
			while (resultSet.next()) {
				rows.add(new DbResultSetRow(resultSet, columnCount));
			}
			return rows;
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		} finally {
			resultSet.close();
		}
	}
}
