package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.entity.IDbEntityTable;
import com.softicar.platform.db.runtime.field.DbBaseField;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.key.DbTableBaseKey;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Default implementation of {@link IDbSubObjectTableBuilder}.
 *
 * @param <R>
 *            the table row class
 * @param <G>
 *            the generated table row base class
 * @param <B>
 *            the table row class of the base table
 * @param <P>
 *            the primary key value class of the base table
 * @author Oliver Richers
 */
public class DbSubObjectTableBuilder<R extends G, G extends IDbSubObject<R, B>, B extends IDbEntity<B, P>, P> extends DbTableBuilder<R, G, B>
		implements IDbSubObjectTableBuilder<R, B, P> {

	private IDbEntityTable<B, P> baseTable;
	private DbBaseField<R, B, P> baseField;

	public DbSubObjectTableBuilder(String database, String table, Supplier<R> rowFactory, Class<R> rowClass) {

		super(database, table, rowFactory, rowClass);
	}

	@Override
	public IDbEntityTable<B, P> getBaseTable() {

		return baseTable;
	}

	@Override
	public IDbBaseField<R, B, P> getBaseField() {

		return baseField;
	}

	public DbBaseField<R, B, P> addBaseField(String name, Function<G, B> getter, BiConsumer<G, B> setter, IDbEntityTable<B, P> baseTable) {

		this.baseTable = baseTable;
		this.baseField = new DbBaseField<>(this, name, getter, setter, baseTable);
		addField(baseField);
		setPrimaryKey(new DbTableBaseKey<>(baseField));
		return baseField;
	}
}
