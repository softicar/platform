package com.softicar.platform.core.module.role.user;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.role.AGRole;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGRoleUser for
 * database table <i>Core.RoleUser</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGRoleUserGenerated extends AbstractDbObject<AGRoleUser> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGRoleUser, AGRoleUserGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "RoleUser", AGRoleUser::new, AGRoleUser.class);
	static {
		BUILDER.setTitle(CoreI18n.ROLE_USER);
		BUILDER.setPluralTitle(CoreI18n.ROLE_USERS);
	}

	public static final IDbIdField<AGRoleUser> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGRoleUser> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGRoleUser, AGRole> ROLE = BUILDER.addForeignField("role", o->o.m_role, (o,v)->o.m_role=v, AGRole.ID).setTitle(CoreI18n.ROLE).setForeignKeyName("RoleUser_ibfk_1");
	public static final IDbForeignField<AGRoleUser, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(CoreI18n.USER).setForeignKeyName("RoleUser_ibfk_2");
	public static final IDbKey<AGRoleUser> UK_ROLE_USER = BUILDER.addUniqueKey("roleUser", ROLE, USER);
	public static final IDbKey<AGRoleUser> IK_USER = BUILDER.addIndexKey("user", USER);
	public static final AGRoleUserTable TABLE = new AGRoleUserTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGRoleUser> createSelect() {

		return TABLE.createSelect();
	}

	public static AGRoleUser get(Integer id) {

		return TABLE.get(id);
	}

	public static AGRoleUser loadByRoleAndUser(AGRole role, AGUser user) {

		return TABLE//
				.createSelect()
				.where(ROLE.equal(role))
				.where(USER.equal(user))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGRoleUser setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getRoleID() {

		return getValueId(ROLE);
	}

	public final AGRole getRole() {

		return getValue(ROLE);
	}

	public final AGRoleUser setRole(AGRole value) {

		return setValue(ROLE, value);
	}

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final AGRoleUser setUser(AGUser value) {

		return setValue(USER, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGRoleUserTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private AGRole m_role;
	private AGUser m_user;
}

