package com.softicar.platform.core.module.role.user;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.role.AGRole;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGRoleUserLog for
 * database table <i>Core.RoleUserLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGRoleUserLog extends AbstractDbRecord<AGRoleUserLog, Tuple2<AGRoleUser, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGRoleUserLog, AGRoleUserLog, Tuple2<AGRoleUser, AGTransaction>> BUILDER = new DbTableBuilder<>("Core", "RoleUserLog", AGRoleUserLog::new, AGRoleUserLog.class);
	static {
		BUILDER.setTitle(CoreI18n.ROLE_USER_LOG);
		BUILDER.setPluralTitle(CoreI18n.ROLE_USER_LOGS);
	}

	public static final IDbForeignField<AGRoleUserLog, AGRoleUser> ROLE_USER = BUILDER.addForeignField("roleUser", o->o.m_roleUser, (o,v)->o.m_roleUser=v, AGRoleUser.ID).setTitle(CoreI18n.ROLE_USER).setForeignKeyName("RoleUserLog_ibfk_1");
	public static final IDbForeignField<AGRoleUserLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setForeignKeyName("RoleUserLog_ibfk_2");
	public static final IDbBooleanField<AGRoleUserLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGRoleUserLog, AGRole> ROLE = BUILDER.addForeignField("role", o->o.m_role, (o,v)->o.m_role=v, AGRole.ID).setTitle(CoreI18n.ROLE).setNullable().setDefault(null).setForeignKeyName("RoleUserLog_ibfk_3");
	public static final IDbForeignField<AGRoleUserLog, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER).setNullable().setDefault(null).setForeignKeyName("RoleUserLog_ibfk_4");
	public static final IDbTableKey<AGRoleUserLog, Tuple2<AGRoleUser, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(ROLE_USER, TRANSACTION));
	public static final IDbKey<AGRoleUserLog> IK_TRANSACTION = BUILDER.addIndexKey("transaction", TRANSACTION);
	public static final IDbKey<AGRoleUserLog> IK_ROLE = BUILDER.addIndexKey("role", ROLE);
	public static final IDbKey<AGRoleUserLog> IK_USER = BUILDER.addIndexKey("user", USER);
	public static final DbRecordTable<AGRoleUserLog, Tuple2<AGRoleUser, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGRoleUserLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getRoleUserID() {

		return getValueId(ROLE_USER);
	}

	public final AGRoleUser getRoleUser() {

		return getValue(ROLE_USER);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGRoleUserLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getRoleID() {

		return getValueId(ROLE);
	}

	public final AGRole getRole() {

		return getValue(ROLE);
	}

	public final AGRoleUserLog setRole(AGRole value) {

		return setValue(ROLE, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGRoleUserLog setUser(AGUser value) {

		return setValue(USER, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGRoleUserLog, Tuple2<AGRoleUser, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGRoleUser m_roleUser;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGRole m_role;
	private AGUser m_user;
}

