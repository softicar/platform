package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import com.softicar.platform.db.sql.fields.AbstractSqlForeignRowField;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Abstract base class of all foreign row fields.
 *
 * @param <R>
 *            the type of the source table rows
 * @param <F>
 *            the type of the target table rows
 * @param <FP>
 *            the type of the target primary key
 * @param <T>
 *            the type of the field class itself (necessary for method chaining)
 * @author Oliver Richers
 */
public abstract class AbstractDbForeignRowField<R, F extends IDbTableRow<F, FP>, FP, T> extends AbstractSqlForeignRowField<R, F, FP>
		implements IDbForeignRowField<R, F, FP> {

	private final Function<? super R, F> getter;
	private final BiConsumer<? super R, F> setter;
	private final IDbField<F, FP> targetField;
	private FP defaultValuePk;
	private boolean nullable;
	private boolean hasDefault;
	private IDisplayString title;
	private Optional<String> comment;
	private DbForeignKeyAction onDelete;
	private DbForeignKeyAction onUpdate;
	private Optional<String> foreignKeyName;

	public AbstractDbForeignRowField(IDbTableBuilder<R, ?> builder, String name, Function<? super R, F> getter, BiConsumer<? super R, F> setter,
			IDbField<F, FP> targetField) {

		super(builder::getTable, targetField, name, builder.getFieldCount());

		this.getter = getter;
		this.setter = setter;
		this.targetField = targetField;
		this.defaultValuePk = null;
		this.nullable = false;
		this.hasDefault = false;
		this.title = null;
		this.comment = Optional.empty();
		this.onDelete = DbForeignKeyAction.getDefault();
		this.onUpdate = DbForeignKeyAction.getDefault();
		this.foreignKeyName = Optional.empty();
	}

	// ------------------------------ structure ------------------------------ //

	@Override
	public SqlFieldType getPhysicalFieldType() {

		return targetField.getPhysicalFieldType();
	}

	@Override
	@SuppressWarnings("unchecked")
	public IDbTable<R, ?> getTable() {

		return (IDbTable<R, ?>) super.getTable();
	}

	@Override
	public IDbField<F, FP> getTargetField() {

		return targetField;
	}

	@Override
	@SuppressWarnings("unchecked")
	public IDbTable<F, FP> getTargetTable() {

		return (IDbTable<F, FP>) targetField.getTable();
	}

	@Override
	public Function<? super R, F> getValueGetter() {

		return getter;
	}

	@Override
	public BiConsumer<? super R, F> getValueSetter() {

		return setter;
	}

	@Override
	public F getDefault() {

		return getTargetTable().getStub(defaultValuePk);
	}

	@Override
	public FP getDefaultValuePk() {

		return defaultValuePk;
	}

	@Override
	public boolean isNullable() {

		return nullable;
	}

	@Override
	public boolean hasDefault() {

		return hasDefault;
	}

	@Override
	public IDisplayString getTitle() {

		return title != null? title : DbFields.getFallbackTitle(this);
	}

	@Override
	public Optional<String> getComment() {

		return comment;
	}

	@Override
	public DbForeignKeyAction getOnDelete() {

		return onDelete;
	}

	@Override
	public DbForeignKeyAction getOnUpdate() {

		return onUpdate;
	}

	@Override
	public Optional<String> getForeignKeyName() {

		return foreignKeyName;
	}

	// ------------------------------ prefetch ------------------------------ //

	@Override
	public Collection<F> prefetch(Iterable<? extends R> rows) {

		return prefetch(rows, ArrayList::new);
	}

	@Override
	public <C extends Collection<F>> C prefetch(Iterable<? extends R> rows, Supplier<C> collectionFactory) {

		Set<FP> pks = new HashSet<>();
		for (R row: rows) {
			F foreignRow = getValue(row);
			if (foreignRow != null) {
				FP pk = foreignRow.pk();
				if (pk != null) {
					pks.add(pk);
				}
			}
		}
		return getTargetTable().getAll(pks, collectionFactory);
	}

	@Override
	public void prefetchData(Collection<F> values) {

		targetField.getTable().unstubAll(values);
	}

	// ------------------------------ setters ------------------------------ //

	public T setDefault(FP defaultValue) {

		this.defaultValuePk = defaultValue;
		this.hasDefault = true;
		return getThis();
	}

	public T setNullable() {

		this.nullable = true;
		return getThis();
	}

	public T setTitle(IDisplayString title) {

		this.title = title;
		return getThis();
	}

	public T setComment(String comment) {

		this.comment = Optional.of(comment);
		return getThis();
	}

	public T setCascade(boolean onDeleteCascade, boolean onUpdateCascade) {

		this.onDelete = onDeleteCascade? DbForeignKeyAction.CASCADE : DbForeignKeyAction.getDefault();
		this.onUpdate = onUpdateCascade? DbForeignKeyAction.CASCADE : DbForeignKeyAction.getDefault();
		return getThis();
	}

	public T setOnDelete(DbForeignKeyAction onDelete) {

		this.onDelete = onDelete;
		return getThis();
	}

	public T setOnUpdate(DbForeignKeyAction onUpdate) {

		this.onUpdate = onUpdate;
		return getThis();
	}

	public T setForeignKeyName(String foreignKeyName) {

		this.foreignKeyName = Optional.of(foreignKeyName);
		return getThis();
	}

	// ------------------------------ abstract ------------------------------ //

	protected abstract T getThis();
}
