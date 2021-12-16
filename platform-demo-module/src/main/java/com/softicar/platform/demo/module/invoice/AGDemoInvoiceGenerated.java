package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.DemoI18n;

/**
 * This is the automatically generated class AGDemoInvoice for
 * database table <i>Demo.DemoInvoice</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoInvoiceGenerated extends AbstractDbObject<AGDemoInvoice> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGDemoInvoice, AGDemoInvoiceGenerated> BUILDER = new DbObjectTableBuilder<>("Demo", "DemoInvoice", AGDemoInvoice::new, AGDemoInvoice.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_INVOICE);
		BUILDER.setPluralTitle(DemoI18n.DEMO_INVOICES);
	}

	public static final IDbIdField<AGDemoInvoice> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(DemoI18n.ID);
	public static final IDbForeignField<AGDemoInvoice, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(DemoI18n.TRANSACTION);
	public static final IDbForeignRowField<AGDemoInvoice, AGDemoModuleInstance, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGDemoModuleInstance.MODULE_INSTANCE).setTitle(DemoI18n.MODULE_INSTANCE);
	public static final IDbStringField<AGDemoInvoice> INVOICE_NUMBER = BUILDER.addStringField("invoiceNumber", o->o.m_invoiceNumber, (o,v)->o.m_invoiceNumber=v).setTitle(DemoI18n.INVOICE_NUMBER).setMaximumLength(255);
	public static final IDbDayField<AGDemoInvoice> INVOICE_DATE = BUILDER.addDayField("invoiceDate", o->o.m_invoiceDate, (o,v)->o.m_invoiceDate=v).setTitle(DemoI18n.INVOICE_DATE);
	public static final IDbBooleanField<AGDemoInvoice> LOCKED_ITEMS = BUILDER.addBooleanField("lockedItems", o->o.m_lockedItems, (o,v)->o.m_lockedItems=v).setTitle(DemoI18n.LOCKED_ITEMS).setDefault(false);
	public static final AGDemoInvoiceTable TABLE = new AGDemoInvoiceTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGDemoInvoice> createSelect() {

		return TABLE.createSelect();
	}

	public static AGDemoInvoice get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGDemoInvoice setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final AGDemoModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGDemoInvoice setModuleInstance(AGDemoModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final String getInvoiceNumber() {

		return getValue(INVOICE_NUMBER);
	}

	public final AGDemoInvoice setInvoiceNumber(String value) {

		return setValue(INVOICE_NUMBER, value);
	}

	public final Day getInvoiceDate() {

		return getValue(INVOICE_DATE);
	}

	public final AGDemoInvoice setInvoiceDate(Day value) {

		return setValue(INVOICE_DATE, value);
	}

	public final Boolean isLockedItems() {

		return getValue(LOCKED_ITEMS);
	}

	public final AGDemoInvoice setLockedItems(Boolean value) {

		return setValue(LOCKED_ITEMS, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoInvoiceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private AGDemoModuleInstance m_moduleInstance;
	private String m_invoiceNumber;
	private Day m_invoiceDate;
	private Boolean m_lockedItems;
}

