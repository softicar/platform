package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.field.DbIdField;
import com.softicar.platform.db.runtime.field.DbLongField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.DbTableIdKey;
import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Default implementation of {@link IDbObjectTableBuilder}.
 *
 * @param <R>
 *            the table row class
 * @param <G>
 *            the generated table row base class
 * @author Oliver Richers
 */
public class DbObjectTableBuilder<R extends G, G extends IDbObject<R>> extends DbTableBuilder<R, G, Integer> implements IDbObjectTableBuilder<R> {

	private DbIdField<R> idField;

	public DbObjectTableBuilder(String database, String table, Supplier<R> rowFactory, Class<R> rowClass) {

		super(database, table, rowFactory, rowClass);
	}

	@Override
	public IDbIdField<R> getIdField() {

		return idField;
	}

	public DbIdField<R> addIdField(String name, Function<G, Integer> getter, BiConsumer<G, Integer> setter) {

		this.idField = addField(new DbIdField<>(this, name, getter, setter));
		setPrimaryKey(new DbTableIdKey<>(idField));
		return idField;
	}

	// Please note that the name of this method is intentionally not `addLongIdField`.
	// We need to keep that name reserved for real support of 64 bit id fields.
	public DbIdField<R> addIdFieldForLong(String name, Function<G, Integer> getter, BiConsumer<G, Integer> setter) {

		return addIdField(name, getter, setter).setPhysicalFieldType(SqlFieldType.LONG).setBits(DbLongField.DEFAULT_BITS);
	}
}
