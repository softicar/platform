package com.softicar.platform.emf.transaction;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.test.user.EmfTestUser;

public class EmfTestTransaction extends AbstractDbObject<EmfTestTransaction> implements IEmfTransactionObject<EmfTestTransaction> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestTransaction, EmfTestTransaction> BUILDER = new DbObjectTableBuilder<>("Test", "Transaction", EmfTestTransaction::new, EmfTestTransaction.class);
	public static final IDbIdField<EmfTestTransaction> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbDayTimeField<EmfTestTransaction> AT = BUILDER.addDayTimeField("at", o -> o.at, (o, v) -> o.at = v).setDefault(null);
	public static final IDbForeignField<EmfTestTransaction, EmfTestUser> BY = BUILDER.addForeignField("by", o -> o.by, (o, v) -> o.by = v, EmfTestUser.ID).setDefault(null);
	public static final DbObjectTable<EmfTestTransaction> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private DayTime at;
	private EmfTestUser by;

	@Override
	public IDbObjectTable<EmfTestTransaction> table() {

		return TABLE;
	}

	@Override
	public DayTime getAt() {

		return getValue(AT);
	}

	@Override
	public EmfTestUser getBy() {

		return getValue(BY);
	}

	@Override
	public EmfTestTransaction setAt(DayTime at) {

		return setValue(AT, at);
	}

	@Override
	public EmfTestTransaction setByToCurrentUser() {

		return setBy(EmfTestUser.TABLE.get(CurrentBasicUser.get().getId()));
	}

	public EmfTestTransaction setBy(EmfTestUser user) {

		return setValue(BY, user);
	}
}
