package com.softicar.platform.db.runtime.test.utils;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.sql.statement.ISqlInsert;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class can be used in unit tests to insert new table rows.
 *
 * @author Oliver Richers
 */
public class DbTestTableRowInserter<R extends IDbTableRow<R, P>, P> {

	private final IDbTable<R, P> table;
	private final Map<IDbField<R, ?>, Object> values;

	public DbTestTableRowInserter(IDbTable<R, P> table) {

		this.table = table;
		this.values = new HashMap<>();
	}

	public <V> DbTestTableRowInserter<R, P> set(IDbField<R, V> field, V value) {

		values.put(field, value);
		return this;
	}

	public R insert() {

		// create insert
		ISqlInsert<R> insert = table.createInsert();
		for (Entry<IDbField<R, ?>, Object> entry: values.entrySet()) {
			set(insert, entry.getKey(), entry.getValue());
		}

		// ensure that at least one field is defined
		if (values.isEmpty()) {
			insert.set(table.getPrimaryKey().getFields().get(0), null);
		}

		// load and return inserted row from table
		IDbTableKey<R, P> primaryKey = table.getPrimaryKey();
		if (primaryKey.isGenerated()) {
			int id = insert.execute();
			return table//
				.createSelect()
				.where(primaryKey.getIdField().isEqual(id))
				.getOne();
		} else {
			insert.executeWithoutIdGeneration();
			ISqlSelect<R> select = table.createSelect();
			for (IDbField<R, ?> field: primaryKey.getFields()) {
				addWhere(select, field);
			}
			return select.getOne();
		}
	}

	private <V> void set(ISqlInsert<R> insert, IDbField<R, V> field, Object value) {

		insert.set(field, castValue(field, value));
	}

	private <V> void addWhere(ISqlSelect<R> select, IDbField<R, V> field) {

		Object value = values.get(field);
		select.where(field.isEqual(castValue(field, value)));
	}

	private <V> V castValue(IDbField<R, V> field, Object value) {

		return field.getValueType().getValueClass().cast(value);
	}
}
