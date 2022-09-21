package com.softicar.platform.db.runtime.utils;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

/**
 * This class is used to convert an AG object into a string.
 *
 * @author Oliver Richers
 */
public class DbObjectStringBuilder<R> implements ISqlFieldValueConsumer<R> {

	private final StringBuilder builder = new StringBuilder();

	@Override
	public <V> void consumeFieldValue(ISqlField<R, V> field, V value) {

		appendLabel(field.getName());
		appendValue(value);
	}

	private DbObjectStringBuilder<R> appendLabel(String label) {

		builder.append(builder.length() == 0? "[" : ", ").append(label).append(": ");
		return this;
	}

	private <V> void appendValue(V value) {

		if (value == null) {
			builder.append("null");
		} else if (value instanceof String) {
			builder.append("'").append((String) value).append("'");
		} else if (value instanceof byte[]) {
			builder.append(Hexadecimal.getHexStringUC((byte[]) value));
		} else if (value instanceof IBasicItem) {
			appendValue(((IBasicItem) value).getId());
		} else if (value instanceof IDbTableRow<?, ?>) {
			appendValue(((IDbTableRow<?, ?>) value).pk());
		} else {
			builder.append("" + value);
		}
	}

	public String finish() {

		return builder.append("]").toString();
	}

	@Override
	public String toString() {

		return builder.toString();
	}
}
