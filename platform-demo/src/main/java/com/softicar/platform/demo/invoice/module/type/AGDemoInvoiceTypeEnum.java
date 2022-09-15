package com.softicar.platform.demo.invoice.module.type;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;
import com.softicar.platform.demo.DemoI18n;

@Generated
public enum AGDemoInvoiceTypeEnum implements IDbEnumTableRowEnum<AGDemoInvoiceTypeEnum, AGDemoInvoiceType>, IDisplayable {

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
	public IDisplayString toDisplay() {

		switch (this) {
		case INBOUND:
			return DemoI18n.INBOUND;
		case OUTBOUND:
			return DemoI18n.OUTBOUND;
		}

		throw new IllegalArgumentException("Unknown enumerator: " + name());
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGDemoInvoiceType> consumer) {

		consumer.consumeFieldValue(AGDemoInvoiceType.ID, id);
		consumer.consumeFieldValue(AGDemoInvoiceType.NAME, name);
	}
}

