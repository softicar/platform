package com.softicar.platform.db.runtime.table;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.field.DbBigDecimalField;
import com.softicar.platform.db.runtime.field.DbBooleanField;
import com.softicar.platform.db.runtime.field.DbByteArrayField;
import com.softicar.platform.db.runtime.field.DbDayField;
import com.softicar.platform.db.runtime.field.DbDayTimeField;
import com.softicar.platform.db.runtime.field.DbDoubleField;
import com.softicar.platform.db.runtime.field.DbEnumField;
import com.softicar.platform.db.runtime.field.DbFloatField;
import com.softicar.platform.db.runtime.field.DbForeignField;
import com.softicar.platform.db.runtime.field.DbForeignRowField;
import com.softicar.platform.db.runtime.field.DbIntegerField;
import com.softicar.platform.db.runtime.field.DbLongField;
import com.softicar.platform.db.runtime.field.DbStringField;
import com.softicar.platform.db.runtime.field.DbTimeField;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbPrimitiveField;
import com.softicar.platform.db.runtime.key.DbKey;
import com.softicar.platform.db.runtime.key.DbKey1;
import com.softicar.platform.db.runtime.key.DbKey2;
import com.softicar.platform.db.runtime.key.DbKey3;
import com.softicar.platform.db.runtime.key.DbKey4;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbKey1;
import com.softicar.platform.db.runtime.key.IDbKey2;
import com.softicar.platform.db.runtime.key.IDbKey3;
import com.softicar.platform.db.runtime.key.IDbKey4;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.structure.key.DbKeyType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Default implementation of {@link IDbTableBuilder}.
 *
 * @param <R>
 *            the table row class
 * @param <G>
 *            the generated table row base class
 * @param <P>
 *            the primary key value class
 * @author Oliver Richers
 */
public class DbTableBuilder<R extends G, G, P> implements IDbTableBuilder<R, P> {

	private final DbTableName tableName;
	private final Class<R> rowClass;
	private final Supplier<R> rowFactory;
	private final List<IDbField<R, ?>> controlFields;
	private final List<IDbField<R, ?>> dataFields;
	private final List<IDbField<R, ?>> fields;
	private IDbTable<R, P> table;
	private IDbTableKey<R, P> primaryKey;
	private final Collection<IDbKey<R>> uniqueKeys;
	private final Collection<IDbKey<R>> indexKeys;
	private IDisplayString title;
	private IDisplayString pluralTitle;

	public DbTableBuilder(String database, String table, Supplier<R> rowFactory, Class<R> rowClass) {

		this.tableName = new DbTableName(database, table);
		this.rowClass = rowClass;
		this.rowFactory = rowFactory;
		this.controlFields = new ArrayList<>();
		this.dataFields = new ArrayList<>();
		this.fields = new ArrayList<>();
		this.primaryKey = null;
		this.uniqueKeys = new ArrayList<>();
		this.indexKeys = new ArrayList<>();
		this.title = null;
		this.pluralTitle = null;
	}

	// -------------------- table -------------------- //

	@Override
	public IDbTable<R, P> getTable() {

		return table;
	}

	@Override
	public void setTable(IDbTable<R, P> table) {

		this.table = table;
	}

	@Override
	public DbTableName getTableName() {

		return tableName;
	}

	// -------------------- rows -------------------- //

	@Override
	public Class<R> getRowClass() {

		return rowClass;
	}

	@Override
	public Supplier<R> getRowFactory() {

		return rowFactory;
	}

	// -------------------- title -------------------- //

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public IDisplayString getPluralTitle() {

		return pluralTitle;
	}

	public void setTitle(IDisplayString title) {

		this.title = title;
	}

	public void setPluralTitle(IDisplayString pluralTitle) {

		this.pluralTitle = pluralTitle;
	}

	// -------------------- fields -------------------- //

	@Override
	public int getFieldCount() {

		return fields.size();
	}

	@Override
	public final List<IDbField<R, ?>> getAllFields() {

		return fields;
	}

	@Override
	public final List<IDbField<R, ?>> getControlFields() {

		return controlFields;
	}

	@Override
	public final List<IDbField<R, ?>> getDataFields() {

		return dataFields;
	}

	protected <F extends IDbField<R, ?>> F addField(F field) {

		// add to all fields
		fields.add(field);

		// add to control or data fields
		if (primaryKey != null) {
			addToControlOrDataFields(field);
		}

		return field;
	}

	private void addToControlOrDataFields(IDbField<R, ?> field) {

		if (primaryKey.containsField(field)) {
			controlFields.add(field);
		} else {
			dataFields.add(field);
		}
	}

	public DbBigDecimalField<R> addBigDecimalField(String name, Function<G, BigDecimal> getter, BiConsumer<G, BigDecimal> setter) {

		return addField(new DbBigDecimalField<>(this, name, getter, setter));
	}

	public DbBooleanField<R> addBooleanField(String name, Function<G, Boolean> getter, BiConsumer<G, Boolean> setter) {

		return addField(new DbBooleanField<>(this, name, getter, setter));
	}

	public DbByteArrayField<R> addByteArrayField(String name, Function<G, byte[]> getter, BiConsumer<G, byte[]> setter) {

		return addField(new DbByteArrayField<>(this, name, getter, setter));
	}

	public DbDayField<R> addDayField(String name, Function<G, Day> getter, BiConsumer<G, Day> setter) {

		return addField(new DbDayField<>(this, name, getter, setter));
	}

	public DbDayTimeField<R> addDayTimeField(String name, Function<G, DayTime> getter, BiConsumer<G, DayTime> setter) {

		return addField(new DbDayTimeField<>(this, name, getter, setter));
	}

	public DbDoubleField<R> addDoubleField(String name, Function<G, Double> getter, BiConsumer<G, Double> setter) {

		return addField(new DbDoubleField<>(this, name, getter, setter));
	}

	public <E extends Enum<E>> DbEnumField<R, E> addEnumField(String name, Function<G, E> getter, BiConsumer<G, E> setter, Class<E> enumClass) {

		return addField(new DbEnumField<>(this, name, getter, setter, enumClass));
	}

	public DbFloatField<R> addFloatField(String name, Function<G, Float> getter, BiConsumer<G, Float> setter) {

		return addField(new DbFloatField<>(this, name, getter, setter));
	}

	public <F extends IDbObject<F>> DbForeignField<R, F> addForeignField(String name, Function<G, F> getter, BiConsumer<G, F> setter,
			IDbPrimitiveField<F, Integer> targetField) {

		return addField(new DbForeignField<>(this, name, getter, setter, targetField));
	}

	public <F extends IDbTableRow<F, FP>, FP> DbForeignRowField<R, F, FP> addForeignRowField(String name, Function<G, F> getter, BiConsumer<G, F> setter,
			IDbField<F, FP> targetField) {

		return addField(new DbForeignRowField<>(this, name, getter, setter, targetField));
	}

	public DbIntegerField<R> addIntegerField(String name, Function<G, Integer> getter, BiConsumer<G, Integer> setter) {

		return addField(new DbIntegerField<>(this, name, getter, setter));
	}

	public DbLongField<R> addLongField(String name, Function<G, Long> getter, BiConsumer<G, Long> setter) {

		return addField(new DbLongField<>(this, name, getter, setter));
	}

	public DbStringField<R> addStringField(String name, Function<G, String> getter, BiConsumer<G, String> setter) {

		return addField(new DbStringField<>(this, name, getter, setter));
	}

	public DbTimeField<R> addTimeField(String name, Function<G, Time> getter, BiConsumer<G, Time> setter) {

		return addField(new DbTimeField<>(this, name, getter, setter));
	}

	// -------------------- keys -------------------- //

	@Override
	public IDbTableKey<R, P> getPrimaryKey() {

		if (primaryKey == null) {
			throw new IllegalStateException("No primary key defined for database table.");
		}

		return primaryKey;
	}

	@Override
	public Collection<IDbKey<R>> getUniqueKeys() {

		return uniqueKeys;
	}

	@Override
	public Collection<IDbKey<R>> getIndexKeys() {

		return indexKeys;
	}

	@Override
	public Collection<IDbKey<R>> getAllKeys() {

		Collection<IDbKey<R>> allkeys = new ArrayList<>();
		Optional.of(primaryKey).ifPresent(allkeys::add);
		allkeys.addAll(uniqueKeys);
		allkeys.addAll(indexKeys);
		return allkeys;
	}

	// -------------------- primary keys -------------------- //

	public IDbTableKey<R, P> setPrimaryKey(IDbTableKey<R, P> primaryKey) {

		if (this.primaryKey != null) {
			throw new IllegalStateException(String.format("Cannot register more that one primary key for database table."));
		}

		this.primaryKey = primaryKey;

		// reorganize control and data fields
		controlFields.clear();
		dataFields.clear();
		for (IDbField<R, ?> field: fields) {
			addToControlOrDataFields(field);
		}

		return primaryKey;
	}

	// -------------------- unique keys -------------------- //

	public <V> IDbKey1<R, V> addUniqueKey(String name, IDbField<R, V> field) {

		return addKey(new DbKey1<>(DbKeyType.UNIQUE, name, field));
	}

	public <V0, V1> IDbKey2<R, V0, V1> addUniqueKey(String name, IDbField<R, V0> field0, IDbField<R, V1> field1) {

		return addKey(new DbKey2<>(DbKeyType.UNIQUE, name, field0, field1));
	}

	public <V0, V1, V2> IDbKey3<R, V0, V1, V2> addUniqueKey(String name, IDbField<R, V0> field0, IDbField<R, V1> field1, IDbField<R, V2> field2) {

		return addKey(new DbKey3<>(DbKeyType.UNIQUE, name, field0, field1, field2));
	}

	public <V0, V1, V2, V3> IDbKey4<R, V0, V1, V2, V3> addUniqueKey(String name, IDbField<R, V0> field0, IDbField<R, V1> field1, IDbField<R, V2> field2,
			IDbField<R, V3> field3) {

		return addKey(new DbKey4<>(DbKeyType.UNIQUE, name, field0, field1, field2, field3));
	}

	@SafeVarargs
	public final IDbKey<R> addUniqueKey(String name, IDbField<R, ?>...fields) {

		return addKey(new DbKey<>(DbKeyType.UNIQUE, name, Arrays.asList(fields)));
	}

	// -------------------- index keys -------------------- //

	public <V> IDbKey1<R, V> addIndexKey(String name, IDbField<R, V> field) {

		return addKey(new DbKey1<>(DbKeyType.INDEX, name, field));
	}

	public <V0, V1> IDbKey2<R, V0, V1> addIndexKey(String name, IDbField<R, V0> field0, IDbField<R, V1> field1) {

		return addKey(new DbKey2<>(DbKeyType.INDEX, name, field0, field1));
	}

	public <V0, V1, V2> IDbKey3<R, V0, V1, V2> addIndexKey(String name, IDbField<R, V0> field0, IDbField<R, V1> field1, IDbField<R, V2> field2) {

		return addKey(new DbKey3<>(DbKeyType.INDEX, name, field0, field1, field2));
	}

	public <V0, V1, V2, V3> IDbKey4<R, V0, V1, V2, V3> addIndexKey(String name, IDbField<R, V0> field0, IDbField<R, V1> field1, IDbField<R, V2> field2,
			IDbField<R, V3> field3) {

		return addKey(new DbKey4<>(DbKeyType.INDEX, name, field0, field1, field2, field3));
	}

	@SafeVarargs
	public final IDbKey<R> addIndexKey(String name, IDbField<R, ?>...fields) {

		return addKey(new DbKey<>(DbKeyType.INDEX, name, Arrays.asList(fields)));
	}

	// -------------------- private -------------------- //

	private <K extends IDbKey<R>> K addKey(K key) {

		switch (key.getType()) {
		case PRIMARY:
			throw new UnsupportedOperationException("Not allowed for primary keys.");
		case UNIQUE:
			uniqueKeys.add(key);
			break;
		case INDEX:
			indexKeys.add(key);
			break;
		}
		return key;
	}
}
