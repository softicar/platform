package com.softicar.platform.db.sql.operations;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.db.sql.SqlBooleanExpressionBase;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

public class SqlInPkListExpression<T, F, FP> extends SqlBooleanExpressionBase<T> {

	private final Type type;
	private final ISqlForeignRowField<T, F, FP> field;
	private final Iterable<FP> pkList;

	public static enum Type {
		IS_IN(" IN "),
		IS_NOT_IN(" NOT IN ");

		public <Tx, Fx, FPx> SqlInPkListExpression<Tx, Fx, FPx> get(ISqlForeignRowField<Tx, Fx, FPx> field, Iterable<FPx> idList) {

			return new SqlInPkListExpression<>(this, field, idList);
		}

		public String getText() {

			return m_text;
		}

		private Type(String text) {

			m_text = text;
		}

		private String m_text;
	}

	private SqlInPkListExpression(Type type, ISqlForeignRowField<T, F, FP> field, Iterable<FP> pkList) {

		for (FP pk: pkList) {
			if (pk == null) {
				throw new SofticarException("Value may not be null in %s.", SqlInPkListExpression.class.getSimpleName());
			}
		}

		this.type = type;
		this.field = field;
		this.pkList = pkList;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		if (pkList.iterator().hasNext()) {
			// non-empty list
			builder.addField(field).addText(type.getText()).addParameterPack(pkList);
		} else if (type == Type.IS_IN) {
			// for empty list, IN() is always false
			builder.addText("FALSE");
		} else if (type == Type.IS_NOT_IN) {
			// for empty list, NOT IN() is always true
			builder.addText("TRUE");
		}
	}
}
