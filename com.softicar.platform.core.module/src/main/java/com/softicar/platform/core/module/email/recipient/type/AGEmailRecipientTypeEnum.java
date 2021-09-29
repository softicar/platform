package com.softicar.platform.core.module.email.recipient.type;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGEmailRecipientTypeEnum implements IDbEnumTableRowEnum<AGEmailRecipientTypeEnum, AGEmailRecipientType> {

	TO(1, "TO"),
	CC(2, "CC"),
	BCC(3, "BCC"),
	//
	;

	private Integer id;
	private String name;

	private AGEmailRecipientTypeEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<AGEmailRecipientType, AGEmailRecipientTypeEnum> getTable() {

		return AGEmailRecipientType.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGEmailRecipientType> consumer) {

		consumer.consumeFieldValue(AGEmailRecipientType.ID, id);
		consumer.consumeFieldValue(AGEmailRecipientType.NAME, name);
	}
}

