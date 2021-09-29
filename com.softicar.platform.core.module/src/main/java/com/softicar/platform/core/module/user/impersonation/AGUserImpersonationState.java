package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGUserImpersonationState for
 * database table <i>Core.UserImpersonationState</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGUserImpersonationState extends AbstractDbObject<AGUserImpersonationState> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGUserImpersonationState, AGUserImpersonationState> BUILDER = new DbObjectTableBuilder<>("Core", "UserImpersonationState", AGUserImpersonationState::new, AGUserImpersonationState.class);
	static {
		BUILDER.setTitle(CoreI18n.USER_IMPERSONATION_STATE);
		BUILDER.setPluralTitle(CoreI18n.USER_IMPERSONATION_STATES);
	}

	public static final IDbIdField<AGUserImpersonationState> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGUserImpersonationState, AGUser> SOURCE_USER = BUILDER.addForeignField("sourceUser", o->o.m_sourceUser, (o,v)->o.m_sourceUser=v, AGUser.ID).setTitle(CoreI18n.SOURCE_USER);
	public static final IDbForeignField<AGUserImpersonationState, AGUser> TARGET_USER = BUILDER.addForeignField("targetUser", o->o.m_targetUser, (o,v)->o.m_targetUser=v, AGUser.ID).setTitle(CoreI18n.TARGET_USER);
	public static final IDbStringField<AGUserImpersonationState> REASON = BUILDER.addStringField("reason", o->o.m_reason, (o,v)->o.m_reason=v).setTitle(CoreI18n.REASON).setLengthBits(16);
	public static final IDbDayTimeField<AGUserImpersonationState> STARTED_AT = BUILDER.addDayTimeField("startedAt", o->o.m_startedAt, (o,v)->o.m_startedAt=v).setTitle(CoreI18n.STARTED_AT);
	public static final IDbDayTimeField<AGUserImpersonationState> FINISHED_AT = BUILDER.addDayTimeField("finishedAt", o->o.m_finishedAt, (o,v)->o.m_finishedAt=v).setTitle(CoreI18n.FINISHED_AT).setNullable().setDefault(null);
	public static final IDbKey<AGUserImpersonationState> IK_SOURCE_USER = BUILDER.addIndexKey("sourceUser", SOURCE_USER);
	public static final IDbKey<AGUserImpersonationState> IK_TARGET_USER = BUILDER.addIndexKey("targetUser", TARGET_USER);
	public static final DbObjectTable<AGUserImpersonationState> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGUserImpersonationState> createSelect() {

		return TABLE.createSelect();
	}

	public static AGUserImpersonationState get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getSourceUserID() {

		return getValueId(SOURCE_USER);
	}

	public final AGUser getSourceUser() {

		return getValue(SOURCE_USER);
	}

	public final AGUserImpersonationState setSourceUser(AGUser value) {

		return setValue(SOURCE_USER, value);
	}

	public final Integer getTargetUserID() {

		return getValueId(TARGET_USER);
	}

	public final AGUser getTargetUser() {

		return getValue(TARGET_USER);
	}

	public final AGUserImpersonationState setTargetUser(AGUser value) {

		return setValue(TARGET_USER, value);
	}

	public final String getReason() {

		return getValue(REASON);
	}

	public final AGUserImpersonationState setReason(String value) {

		return setValue(REASON, value);
	}

	public final DayTime getStartedAt() {

		return getValue(STARTED_AT);
	}

	public final AGUserImpersonationState setStartedAt(DayTime value) {

		return setValue(STARTED_AT, value);
	}

	public final DayTime getFinishedAt() {

		return getValue(FINISHED_AT);
	}

	public final AGUserImpersonationState setFinishedAt(DayTime value) {

		return setValue(FINISHED_AT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbObjectTable<AGUserImpersonationState> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUser m_sourceUser;
	private AGUser m_targetUser;
	private String m_reason;
	private DayTime m_startedAt;
	private DayTime m_finishedAt;
}

