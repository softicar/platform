package com.softicar.platform.core.module.user.login.failure.type;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

/**
 * This is the automatically generated class AGUserLoginFailureType for
 * database table <i>Core.UserLoginFailureType</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserLoginFailureType extends AbstractDbEnumTableRow<AGUserLoginFailureType, AGUserLoginFailureTypeEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUserLoginFailureType, AGUserLoginFailureType> BUILDER = new DbObjectTableBuilder<>("Core", "UserLoginFailureType", AGUserLoginFailureType::new, AGUserLoginFailureType.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_LOGIN_FAILURE_TYPE);
		BUILDER.setPluralTitle(CoreI18n.USER_LOGIN_FAILURE_TYPES);
	}

	public static final IDbIdField<AGUserLoginFailureType> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGUserLoginFailureType> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final DbEnumTable<AGUserLoginFailureType, AGUserLoginFailureTypeEnum> TABLE = new DbEnumTable<>(BUILDER, AGUserLoginFailureTypeEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGUserLoginFailureType setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGUserLoginFailureType, AGUserLoginFailureTypeEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
}

