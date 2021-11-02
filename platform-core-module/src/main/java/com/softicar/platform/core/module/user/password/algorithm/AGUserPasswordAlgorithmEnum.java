package com.softicar.platform.core.module.user.password.algorithm;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGUserPasswordAlgorithmEnum implements IDbEnumTableRowEnum<AGUserPasswordAlgorithmEnum, AGUserPasswordAlgorithm> {

	BCRYPT(1, "BCRYPT"),
	UNIX_CRYPT(2, "UNIX_CRYPT"),
	APR_1(3, "APR1"),
	//
	;

	private Integer id;
	private String name;

	private AGUserPasswordAlgorithmEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<AGUserPasswordAlgorithm, AGUserPasswordAlgorithmEnum> getTable() {

		return AGUserPasswordAlgorithm.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGUserPasswordAlgorithm> consumer) {

		consumer.consumeFieldValue(AGUserPasswordAlgorithm.ID, id);
		consumer.consumeFieldValue(AGUserPasswordAlgorithm.NAME, name);
	}
}

