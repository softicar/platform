package com.softicar.platform.db.sql.field;

/**
 * This interface is used to extract data from a table row.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface ISqlFieldValueConsumer<R> {

	<V> void consumeFieldValue(ISqlField<R, V> field, V value);
}
