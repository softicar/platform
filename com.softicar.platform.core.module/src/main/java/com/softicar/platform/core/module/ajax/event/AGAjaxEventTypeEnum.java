package com.softicar.platform.core.module.ajax.event;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGAjaxEventTypeEnum implements IDbEnumTableRowEnum<AGAjaxEventTypeEnum, AGAjaxEventType> {

	ACCESS_VIOLATION(1, "ACCESS_VIOLATION"),
	DOCUMENT_CREATION(2, "DOCUMENT_CREATION"),
	DOCUMENT_DELETION(3, "DOCUMENT_DELETION"),
	//
	;

	private Integer id;
	private String name;

	private AGAjaxEventTypeEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<AGAjaxEventType, AGAjaxEventTypeEnum> getTable() {

		return AGAjaxEventType.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGAjaxEventType> consumer) {

		consumer.consumeFieldValue(AGAjaxEventType.ID, id);
		consumer.consumeFieldValue(AGAjaxEventType.NAME, name);
	}
}

