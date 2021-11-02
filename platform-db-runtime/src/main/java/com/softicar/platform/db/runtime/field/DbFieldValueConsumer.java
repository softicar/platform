package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

public class DbFieldValueConsumer {

	public static <R> void consumeFieldValues(Iterable<? extends IDbField<R, ?>> fields, R source, ISqlFieldValueConsumer<R> consumer) {

		for (IDbField<R, ?> field: fields) {
			consumeFieldValue(field, source, consumer);
		}
	}

	private static <R, V> void consumeFieldValue(IDbField<R, V> field, R source, ISqlFieldValueConsumer<R> consumer) {

		consumer.consumeFieldValue(field, field.getValue(source));
	}

	public static <R> void consumeFieldValuesDirectly(Iterable<? extends IDbField<R, ?>> fields, R source, ISqlFieldValueConsumer<R> consumer) {

		for (IDbField<R, ?> field: fields) {
			consumeFieldValueDirectly(field, source, consumer);
		}
	}

	private static <R, V> void consumeFieldValueDirectly(IDbField<R, V> field, R source, ISqlFieldValueConsumer<R> consumer) {

		consumer.consumeFieldValue(field, field.getValueDirectly(source));
	}
}
