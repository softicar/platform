package com.softicar.platform.core.module.user.login.failure.type;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGUserLoginFailureTypeEnum implements IDbEnumTableRowEnum<AGUserLoginFailureTypeEnum, AGUserLoginFailureType>, IDisplayable {

	MALFORMED_REQUEST(1, "MALFORMED_REQUEST"),
	UNKNOWN_USER(2, "UNKNOWN_USER"),
	DISABLED_USER(3, "DISABLED_USER"),
	NO_ACTIVE_PASSWORD(4, "NO_ACTIVE_PASSWORD"),
	WRONG_PASSWORD(5, "WRONG_PASSWORD"),
	TOO_MANY_FAILURES(6, "TOO_MANY_FAILURES"),
	ILLEGAL_IP(7, "ILLEGAL_IP"),
	TOO_MANY_LOGINS(8, "TOO_MANY_LOGINS"),
	MAINTENANCE_IN_PROGRESS(9, "MAINTENANCE_IN_PROGRESS"),
	//
	;

	private Integer id;
	private String name;

	private AGUserLoginFailureTypeEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<AGUserLoginFailureType, AGUserLoginFailureTypeEnum> getTable() {

		return AGUserLoginFailureType.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public IDisplayString toDisplay() {

		switch (this) {
		case MALFORMED_REQUEST:
			return CoreI18n.MALFORMED_REQUEST;
		case UNKNOWN_USER:
			return CoreI18n.UNKNOWN_USER;
		case DISABLED_USER:
			return CoreI18n.DISABLED_USER;
		case NO_ACTIVE_PASSWORD:
			return CoreI18n.NO_ACTIVE_PASSWORD;
		case WRONG_PASSWORD:
			return CoreI18n.WRONG_PASSWORD;
		case TOO_MANY_FAILURES:
			return CoreI18n.TOO_MANY_FAILURES;
		case ILLEGAL_IP:
			return CoreI18n.ILLEGAL_IP;
		case TOO_MANY_LOGINS:
			return CoreI18n.TOO_MANY_LOGINS;
		case MAINTENANCE_IN_PROGRESS:
			return CoreI18n.MAINTENANCE_IN_PROGRESS;
		}

		throw new IllegalArgumentException("Unknown enumerator: " + name());
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGUserLoginFailureType> consumer) {

		consumer.consumeFieldValue(AGUserLoginFailureType.ID, id);
		consumer.consumeFieldValue(AGUserLoginFailureType.NAME, name);
	}
}

