package com.softicar.platform.core.module.user.password.algorithm;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGUserPasswordAlgorithmEnum implements IDbEnumTableRowEnum<AGUserPasswordAlgorithmEnum, AGUserPasswordAlgorithm>, IDisplayable {

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
	public IDisplayString toDisplay() {

		switch (this) {
		case BCRYPT:
			return CoreI18n.BCRYPT;
		case UNIX_CRYPT:
			return CoreI18n.UNIX_CRYPT;
		case APR_1:
			return CoreI18n.APR_1;
		}

		throw new IllegalArgumentException("Unknown enumerator: " + name());
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGUserPasswordAlgorithm> consumer) {

		consumer.consumeFieldValue(AGUserPasswordAlgorithm.ID, id);
		consumer.consumeFieldValue(AGUserPasswordAlgorithm.NAME, name);
	}
}

