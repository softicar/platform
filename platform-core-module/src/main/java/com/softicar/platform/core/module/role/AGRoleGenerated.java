package com.softicar.platform.core.module.role;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGRole for
 * database table <i>Core.Role</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGRoleGenerated extends AbstractDbObject<AGRole> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGRole, AGRoleGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "Role", AGRole::new, AGRole.class);
	static {
		BUILDER.setTitle(CoreI18n.ROLE);
		BUILDER.setPluralTitle(CoreI18n.ROLES);
	}

	public static final IDbIdField<AGRole> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbBooleanField<AGRole> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbStringField<AGRole> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final IDbKey<AGRole> UK_NAME = BUILDER.addUniqueKey("name", NAME);
	public static final AGRoleTable TABLE = new AGRoleTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGRole> createSelect() {

		return TABLE.createSelect();
	}

	public static AGRole get(Integer id) {

		return TABLE.get(id);
	}

	public static AGRole loadByName(String name) {

		return TABLE//
				.createSelect()
				.where(NAME.isEqual(name))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGRole setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGRole setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGRoleTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private Boolean m_active;
	private String m_name;
}

