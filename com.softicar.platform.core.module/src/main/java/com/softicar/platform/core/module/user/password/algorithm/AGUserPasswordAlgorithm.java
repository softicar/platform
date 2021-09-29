package com.softicar.platform.core.module.user.password.algorithm;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

/**
 * This is the automatically generated class AGUserPasswordAlgorithm for
 * database table <i>Core.UserPasswordAlgorithm</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserPasswordAlgorithm extends AbstractDbEnumTableRow<AGUserPasswordAlgorithm, AGUserPasswordAlgorithmEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUserPasswordAlgorithm, AGUserPasswordAlgorithm> BUILDER = new DbObjectTableBuilder<>("Core", "UserPasswordAlgorithm", AGUserPasswordAlgorithm::new, AGUserPasswordAlgorithm.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_PASSWORD_ALGORITHM);
		BUILDER.setPluralTitle(CoreI18n.USER_PASSWORD_ALGORITHMS);
	}

	public static final IDbIdField<AGUserPasswordAlgorithm> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGUserPasswordAlgorithm> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(CoreI18n.NAME).setMaximumLength(255);
	public static final DbEnumTable<AGUserPasswordAlgorithm, AGUserPasswordAlgorithmEnum> TABLE = new DbEnumTable<>(BUILDER, AGUserPasswordAlgorithmEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGUserPasswordAlgorithm setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGUserPasswordAlgorithm, AGUserPasswordAlgorithmEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
}

