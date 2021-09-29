package com.softicar.platform.core.module.user.login.failure.type;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGUserLoginFailureTypeEnum implements IDbEnumTableRowEnum<AGUserLoginFailureTypeEnum, AGUserLoginFailureType> {

	MALFORMED_REQUEST(1, "MALFORMED_REQUEST"),
	UNKOWN_USER(2, "UNKOWN_USER"),
	DISABLED_USER(3, "DISABLED_USER"),
	NO_ACTIVE_PASSWORD(4, "NO_ACTIVE_PASSWORD"),
	WRONG_PASSWORD(5, "WRONG_PASSWORD"),
	TOO_MANY_FAILURES(6, "TOO_MANY_FAILURES"),
	ILLEGAL_IP(7, "ILLEGAL_IP"),
	TOO_MANY_LOGINS(8, "TOO_MANY_LOGINS"),
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
	public void setFields(ISqlFieldValueConsumer<AGUserLoginFailureType> consumer) {

		consumer.consumeFieldValue(AGUserLoginFailureType.ID, id);
		consumer.consumeFieldValue(AGUserLoginFailureType.NAME, name);
	}
}

