package com.softicar.platform.demo.module.invoice.module.type;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.demo.module.core.module.DemoI18n;

/**
 * This is the automatically generated class AGDemoInvoiceType for
 * database table <i>Demo.DemoInvoiceType</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoInvoiceTypeGenerated extends AbstractDbEnumTableRow<AGDemoInvoiceType, AGDemoInvoiceTypeEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGDemoInvoiceType, AGDemoInvoiceTypeGenerated> BUILDER = new DbObjectTableBuilder<>("Demo", "DemoInvoiceType", AGDemoInvoiceType::new, AGDemoInvoiceType.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_INVOICE_TYPE);
		BUILDER.setPluralTitle(DemoI18n.DEMO_INVOICE_TYPES);
	}

	public static final IDbIdField<AGDemoInvoiceType> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(DemoI18n.ID);
	public static final IDbStringField<AGDemoInvoiceType> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(DemoI18n.NAME).setMaximumLength(255);
	public static final IDbKey<AGDemoInvoiceType> UK_NAME = BUILDER.addUniqueKey("name", NAME);
	public static final DbEnumTable<AGDemoInvoiceType, AGDemoInvoiceTypeEnum> TABLE = new DbEnumTable<>(BUILDER, AGDemoInvoiceTypeEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGDemoInvoiceType setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGDemoInvoiceType, AGDemoInvoiceTypeEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
}

