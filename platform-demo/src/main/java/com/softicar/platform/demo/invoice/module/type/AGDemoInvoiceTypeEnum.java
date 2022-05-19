package com.softicar.platform.demo.invoice.module.type;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;

@Generated
public enum AGDemoInvoiceTypeEnum implements IDbEnumTableRowEnum<AGDemoInvoiceTypeEnum, AGDemoInvoiceType> {

	INBOUND(1, "Inbound"),
	OUTBOUND(2, "Outbound"),
	//
	;

	private Integer id;
	private String name;

	private AGDemoInvoiceTypeEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<AGDemoInvoiceType, AGDemoInvoiceTypeEnum> getTable() {

		return AGDemoInvoiceType.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGDemoInvoiceType> consumer) {

		consumer.consumeFieldValue(AGDemoInvoiceType.ID, id);
		consumer.consumeFieldValue(AGDemoInvoiceType.NAME, name);
	}
}

