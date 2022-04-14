package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class EmfTestInvoiceGenerated extends AbstractDbObject<EmfTestInvoice> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestInvoice, EmfTestInvoiceGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "Invoice", EmfTestInvoice::new, EmfTestInvoice.class);
//	static {
//		BUILDER.setTitle(IDisplayString.create("Invoice"));
//		BUILDER.setTitle(IDisplayString.create("Invoices"));
//	}
	public static final IDbIdField<EmfTestInvoice> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestInvoice, EmfTestInvoiceModuleInstance> INVOICE_MODULE_INSTANCE = BUILDER.addForeignField("invoiceModuleInstance", o->o.invoiceModuleInstance, (o,v)->o.invoiceModuleInstance=v, EmfTestInvoiceModuleInstance.ID);
	public static final IDbForeignField<EmfTestInvoice, EmfTestBusinessPartner> BUSINESS_PARTNER = BUILDER.addForeignField("businessPartner", o->o.businessPartner, (o,v)->o.businessPartner=v, EmfTestBusinessPartner.ID);
	public static final IDbStringField<EmfTestInvoice> NUMBER = BUILDER.addStringField("number", o -> o.number, (o, v) -> o.number = v).setNullable().setDefault(null);
	public static final IDbDayField<EmfTestInvoice> DATE = BUILDER.addDayField("date", o -> o.date, (o, v) -> o.date = v).setNullable().setDefault(null);
	public static final IDbKey<EmfTestInvoice> UK_BUSINESS_MODULE_INSTANCE_BUSINESS_PARTNER_NAME = BUILDER.addUniqueKey("nameDay", INVOICE_MODULE_INSTANCE, BUSINESS_PARTNER, NUMBER);
	public static final EmfTestInvoiceTable TABLE = new EmfTestInvoiceTable(BUILDER);
	// @formatter:on

	private Integer id;
	private EmfTestInvoiceModuleInstance invoiceModuleInstance;
	private EmfTestBusinessPartner businessPartner;
	private String number;
	private Day date;

	public EmfTestInvoiceModuleInstance getInvoiceModuleInstance() {

		return getValue(INVOICE_MODULE_INSTANCE);
	}

	public EmfTestInvoice setInvoiceModuleInstance(EmfTestInvoiceModuleInstance invoiceModuleInstance) {

		return setValue(INVOICE_MODULE_INSTANCE, invoiceModuleInstance);
	}

	public EmfTestBusinessPartner getBusinessPartner() {

		return getValue(BUSINESS_PARTNER);
	}

	public EmfTestInvoice setBusinessPartner(EmfTestBusinessPartner businessPartner) {

		return setValue(BUSINESS_PARTNER, businessPartner);
	}

	public EmfTestInvoice setNumber(String name) {

		return setValue(NUMBER, name);
	}

	public String getNumber() {

		return getValue(NUMBER);
	}

	public EmfTestInvoice setDate(Day date) {

		return setValue(DATE, date);
	}

	public Day getDate() {

		return getValue(DATE);
	}

	@Override
	public EmfTestInvoiceTable table() {

		return TABLE;
	}
}
