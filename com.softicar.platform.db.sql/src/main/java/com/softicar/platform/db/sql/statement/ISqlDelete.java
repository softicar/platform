package com.softicar.platform.db.sql.statement;

import com.softicar.platform.db.sql.field.ISqlField;

public interface ISqlDelete<R> extends ISqlConditionalStatement<R, ISqlDelete<R>> {

	int execute();

	int execute(int limitCount);

	ISqlDelete<R> orderBy(ISqlField<R, ?> field);

	ISqlDelete<R> orderDescendingBy(ISqlField<R, ?> field);
}
