package com.softicar.platform.core.module.transaction;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGTransaction for
 * database table <i>Core.Transaction</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGTransactionGenerated extends AbstractDbObject<AGTransaction> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGTransaction, AGTransactionGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "Transaction", AGTransaction::new, AGTransaction.class);
	static {
		BUILDER.setTitle(CoreI18n.TRANSACTION);
		BUILDER.setPluralTitle(CoreI18n.TRANSACTIONS);
	}

	public static final IDbIdField<AGTransaction> ID = BUILDER.addIdFieldForLong("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbDayTimeField<AGTransaction> AT = BUILDER.addDayTimeField("at", o->o.m_at, (o,v)->o.m_at=v).setTitle(CoreI18n.AT).setDefaultNow().setOnUpdateNow().setTimestamp();
	public static final IDbForeignField<AGTransaction, AGUser> BY = BUILDER.addForeignField("by", o->o.m_by, (o,v)->o.m_by=v, AGUser.ID).setTitle(CoreI18n.BY).setForeignKeyName("Transaction_ibfk_1");
	public static final IDbKey<AGTransaction> IK_BY = BUILDER.addIndexKey("by", BY);
	public static final DbObjectTable<AGTransaction> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGTransaction> createSelect() {

		return TABLE.createSelect();
	}

	public static AGTransaction get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final DayTime getAt() {

		return getValue(AT);
	}

	public final AGTransaction setAt(DayTime value) {

		return setValue(AT, value);
	}

	public final Integer getByID() {

		return getValueId(BY);
	}

	public final AGUser getBy() {

		return getValue(BY);
	}

	public final AGTransaction setBy(AGUser value) {

		return setValue(BY, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbObjectTable<AGTransaction> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private DayTime m_at;
	private AGUser m_by;
}

