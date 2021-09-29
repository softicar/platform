package com.softicar.platform.db.sql;

import com.softicar.platform.db.sql.field.ISqlValueField;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;

public class SqlValueCompare<T, V> extends SqlBooleanExpressionBase<T> {

	private final Type type;
	private final ISqlValueField<T, V> field;
	private final V value;

	protected SqlValueCompare(Type type, ISqlValueField<T, V> field, V value) {

		this.type = type;
		this.field = field;
		this.value = value;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		builder.addField(field).addText(type.getText()).addParameter(value);
	}

	public static enum Type {
		EQUAL(" = "),
		NOT_EQUAL(" != "),
		LIKE(" LIKE "),
		NOT_LIKE(" NOT LIKE "),
		GREATER(" > "),
		GREATER_OR_EQUAL(" >= "),
		LESS(" < "),
		LESS_OR_EQUAL(" <= ");

		private String text;

		private Type(String text) {

			this.text = text;
		}

		public <TT, VV> SqlValueCompare<TT, VV> get(ISqlValueField<TT, VV> field, VV value) {

			return new SqlValueCompare<>(this, field, value);
		}

		public String getText() {

			return text;
		}
	}
}
