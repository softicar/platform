package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.fields.AbstractSqlField;
import com.softicar.platform.db.sql.type.SqlFieldType;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class AbstractDbPrimitiveField<R, V, T> extends AbstractSqlField<R, V> implements IDbPrimitiveField<R, V> {

	private final Function<? super R, V> getter;
	private final BiConsumer<? super R, V> setter;
	private V defaultValue = null;
	private boolean nullable = false;
	private boolean hasDefault;
	private IDisplayString title;
	private Optional<String> comment;

	public AbstractDbPrimitiveField(IDbTableBuilder<R, ?> builder, SqlFieldType type, String name, Function<? super R, V> getter,
			BiConsumer<? super R, V> setter) {

		super(builder::getTable, type, name, builder.getFieldCount());

		this.getter = getter;
		this.setter = setter;
		this.defaultValue = null;
		this.nullable = false;
		this.hasDefault = false;
		this.title = null;
		this.comment = Optional.empty();
	}

	@Override
	public IDbTable<R, ?> getTable() {

		return CastUtils.cast(super.getTable());
	}

	@Override
	public Function<? super R, V> getValueGetter() {

		return getter;
	}

	@Override
	public BiConsumer<? super R, V> getValueSetter() {

		return setter;
	}

	@Override
	public V getDefault() {

		if (hasDefault) {
			return defaultValue;
		} else {
			return null;
		}
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

	// ------------------------------ setters ------------------------------ //

	public T setDefault(V defaultValue) {

		this.defaultValue = defaultValue;
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

	// ------------------------------ abstract ------------------------------ //

	protected abstract T getThis();
}
