package com.softicar.platform.db.core.connection;

import com.softicar.platform.db.core.SofticarSqlException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

class InternalStatementParameterSetter {

	private final PreparedStatement statement;

	public InternalStatementParameterSetter(PreparedStatement statement) {

		this.statement = statement;
	}

	public void setParameters(Iterable<?> parameters) {

		if (parameters != null) {
			int i = 1;
			for (Object argument: parameters) {
				if (argument instanceof Collection<?>) {
					for (Object element: (Collection<?>) argument) {
						setParameter(i++, element);
					}
				} else {
					setParameter(i++, argument);
				}
			}
		} else {
			// special case for legacy code
			setParameter(1, null);
		}
	}

	private void setParameter(int index, Object argument) {

		try {
			statement.setObject(index, argument);
		} catch (SQLException exception) {
			throw new SofticarSqlException(exception);
		}
	}
}
