package com.softicar.platform.db.runtime.table.row;

import com.softicar.platform.db.runtime.field.DbFieldValueConsumer;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.table.logic.DbTableRowDeleter;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagReader;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagWriter;
import com.softicar.platform.db.runtime.table.logic.DbTableRowFlagsFactory;
import com.softicar.platform.db.runtime.table.logic.DbTableRowInvalidator;
import com.softicar.platform.db.runtime.table.logic.DbTableRowLoader;
import com.softicar.platform.db.runtime.table.logic.save.DbTableRowSaver;
import com.softicar.platform.db.runtime.utils.DbObjectStringBuilder;
import com.softicar.platform.db.sql.statement.SqlSelectLock;

public abstract class AbstractDbTableRow<R extends AbstractDbTableRow<R, P>, P> implements IDbTableRow<R, P> {

	private byte[] flags;

	protected AbstractDbTableRow() {

		this.flags = null;
	}

	// ------------------------------ initialize ------------------------------ //

	@Override
	public IDbTableRowInitializer<R, P> initializer() {

		return new DbTableRowInitializer<>(getThis());
	}

	@Override
	public void initializeLazily() {

		if (flags == null) {
			initializer().initializeFlagsAndSetAllFieldsToDefault(DbTableRowFlag.IMPERMANENT);
		}
	}

	// ------------------------------ value access ------------------------------ //

	protected <V> V getValue(IDbField<R, V> field) {

		return field.getValue(getThis());
	}

	protected Integer getValueId(IDbForeignField<R, ?> field) {

		return field.getValueId(getThis());
	}

	protected <V> R setValue(IDbField<R, V> field, V value) {

		field.setValue(getThis(), value);
		return getThis();
	}

	// ------------------------------ flags ------------------------------ //

	protected void initializeFlags(DbTableRowFlag...flagsToEnable) {

		this.flags = DbTableRowFlagsFactory.createFlags(table());
		new DbTableRowFlagWriter(flags).setFlags(flagsToEnable);
	}

	@Override
	public final byte[] flags() {

		initializeLazily();
		return flags;
	}

	@Override
	public boolean impermanent() {

		return new DbTableRowFlagReader(this).isImpermanent();
	}

	@Override
	public final boolean dataChanged() {

		return new DbTableRowFlagReader(this).isDataChanged();
	}

	@Override
	public final boolean dataChanged(IDbField<R, ?> field) {

		return new DbTableRowFlagReader(this).isDataChanged(field);
	}

	@Override
	public final boolean dirty() {

		return new DbTableRowFlagReader(this).isDirty();
	}

	@Override
	public final boolean invalidated() {

		return new DbTableRowFlagReader(this).isInvalidated();
	}

	@Override
	public final boolean stub() {

		return new DbTableRowFlagReader(this).isStub();
	}

	@Override
	public final void invalidate() {

		new DbTableRowInvalidator<>(getThis()).invalidate();
	}

	// ------------------------------ save and delete ------------------------------ //

	@Override
	public boolean saveIfNecessary() {

		return new DbTableRowSaver<>(table())//
			.setLazyMode(true)
			.addRow(getThis())
			.save() > 0;
	}

	@Override
	public final R save() {

		table().save(getThis());
		return getThis();
	}

	@Override
	public final R delete() {

		new DbTableRowDeleter<>(table()).addRow(getThis()).delete();
		return getThis();
	}

	// ------------------------------ loading ------------------------------ //

	@Override
	public final boolean reload() {

		return new DbTableRowLoader<>(table()).addRow(getThis()).reload();
	}

	@Override
	public final boolean reload(SqlSelectLock lock) {

		return new DbTableRowLoader<>(table()).addRow(getThis()).setLock(lock).reload();
	}

	@Override
	public final boolean reloadForUpdate() {

		return new DbTableRowLoader<>(table()).addRow(getThis()).setLock(SqlSelectLock.FOR_UPDATE).reload();
	}

	// ------------------------------ field operations ------------------------------ //

	@Override
	public final void copyDataFieldsTo(R target) {

		for (IDbField<R, ?> field: table().getDataFields()) {
			copyValue(field, getThis(), target);
		}
	}

	private <V> void copyValue(IDbField<R, V> field, R source, R target) {

		V value = field.getValue(source);
		field.setValue(target, field.copyValue(value));
	}

	// ------------------------------ basics ------------------------------ //

	@Override
	public boolean is(R other) {

		if (other == null) {
			return false;
		} else if (other == this) {
			return true;
		} else {
			P thisPk = this.pk();
			P otherPk = other.pk();
			if (thisPk != null && otherPk != null) {
				return table().getPrimaryKey().compare(thisPk, otherPk) == 0;
			} else {
				return false;
			}
		}
	}

	@Override
	public boolean equals(Object other) {

		return this == other;
	}

	@Override
	public final int hashCode() {

		return System.identityHashCode(this);
	}

	@Override
	public final String toString() {

		DbObjectStringBuilder<R> builder = new DbObjectStringBuilder<>();
		DbFieldValueConsumer.consumeFieldValues(table().getAllFields(), getThis(), builder);
		return builder.finish();
	}

	// ------------------------------ miscellaneous ------------------------------ //

	@Override
	public final P pk() {

		initializeLazily();
		return table().getPrimaryKey().getFromRow(getThis());
	}

	@Override
	@SuppressWarnings("unchecked")
	public R getThis() {

		return (R) this;
	}
}
