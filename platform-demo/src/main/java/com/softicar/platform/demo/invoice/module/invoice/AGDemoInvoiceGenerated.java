package com.softicar.platform.demo.invoice.module.invoice;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.set.AGStoredFileSet;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.invoice.module.type.AGDemoInvoiceType;
import com.softicar.platform.demo.person.module.AGDemoPerson;

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
	public static final IDbForeignRowField<AGDemoInvoice, AGDemoInvoiceModuleInstance, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGDemoInvoiceModuleInstance.MODULE_INSTANCE).setTitle(DemoI18n.MODULE_INSTANCE);
	public static final IDbForeignField<AGDemoInvoice, AGDemoInvoiceType> TYPE = BUILDER.addForeignField("type", o->o.m_type, (o,v)->o.m_type=v, AGDemoInvoiceType.ID).setTitle(DemoI18n.TYPE);
	public static final IDbStringField<AGDemoInvoice> CREDITOR = BUILDER.addStringField("creditor", o->o.m_creditor, (o,v)->o.m_creditor=v).setTitle(DemoI18n.CREDITOR).setDefault("").setMaximumLength(255);
	public static final IDbStringField<AGDemoInvoice> DEBTOR = BUILDER.addStringField("debtor", o->o.m_debtor, (o,v)->o.m_debtor=v).setTitle(DemoI18n.DEBTOR).setDefault("").setMaximumLength(255);
	public static final IDbStringField<AGDemoInvoice> INVOICE_NUMBER = BUILDER.addStringField("invoiceNumber", o->o.m_invoiceNumber, (o,v)->o.m_invoiceNumber=v).setTitle(DemoI18n.INVOICE_NUMBER).setMaximumLength(255);
	public static final IDbDayField<AGDemoInvoice> INVOICE_DATE = BUILDER.addDayField("invoiceDate", o->o.m_invoiceDate, (o,v)->o.m_invoiceDate=v).setTitle(DemoI18n.INVOICE_DATE);
	public static final IDbBooleanField<AGDemoInvoice> LOCKED_ITEMS = BUILDER.addBooleanField("lockedItems", o->o.m_lockedItems, (o,v)->o.m_lockedItems=v).setTitle(DemoI18n.LOCKED_ITEMS).setDefault(false);
	public static final IDbForeignField<AGDemoInvoice, AGStoredFile> DOCUMENT = BUILDER.addForeignField("document", o->o.m_document, (o,v)->o.m_document=v, AGStoredFile.ID).setTitle(DemoI18n.DOCUMENT).setNullable().setDefault(null);
	public static final IDbForeignField<AGDemoInvoice, AGStoredFileSet> ATTACHMENTS = BUILDER.addForeignField("attachments", o->o.m_attachments, (o,v)->o.m_attachments=v, AGStoredFileSet.ID).setTitle(DemoI18n.ATTACHMENTS).setNullable().setDefault(null);
	public static final IDbForeignField<AGDemoInvoice, AGDemoPerson> CONTACT = BUILDER.addForeignField("contact", o->o.m_contact, (o,v)->o.m_contact=v, AGDemoPerson.ID).setTitle(DemoI18n.CONTACT).setNullable().setDefault(null);
	public static final IDbKey<AGDemoInvoice> UK_MODULE_INSTANCE_CREDITOR_DEBTOR_INVOICE_NUMBER_INVOICE_DATE = BUILDER.addUniqueKey("moduleInstanceCreditorDebtorInvoiceNumberInvoiceDate", MODULE_INSTANCE, CREDITOR, DEBTOR, INVOICE_NUMBER, INVOICE_DATE);
	public static final AGDemoInvoiceTable TABLE = new AGDemoInvoiceTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGDemoInvoice> createSelect() {

		return TABLE.createSelect();
	}

	public static AGDemoInvoice get(Integer id) {

		return TABLE.get(id);
	}

	public static AGDemoInvoice loadByModuleInstanceAndCreditorAndDebtorAndInvoiceNumberAndInvoiceDate(AGDemoInvoiceModuleInstance moduleInstance, String creditor, String debtor, String invoiceNumber, Day invoiceDate) {

		return TABLE//
				.createSelect()
				.where(MODULE_INSTANCE.equal(moduleInstance))
				.where(CREDITOR.equal(creditor))
				.where(DEBTOR.equal(debtor))
				.where(INVOICE_NUMBER.equal(invoiceNumber))
				.where(INVOICE_DATE.equal(invoiceDate))
				.getOne();
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

	public final AGDemoInvoiceModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGDemoInvoice setModuleInstance(AGDemoInvoiceModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final Integer getTypeID() {

		return getValueId(TYPE);
	}

	public final AGDemoInvoiceType getType() {

		return getValue(TYPE);
	}

	public final AGDemoInvoice setType(AGDemoInvoiceType value) {

		return setValue(TYPE, value);
	}

	public final String getCreditor() {

		return getValue(CREDITOR);
	}

	public final AGDemoInvoice setCreditor(String value) {

		return setValue(CREDITOR, value);
	}

	public final String getDebtor() {

		return getValue(DEBTOR);
	}

	public final AGDemoInvoice setDebtor(String value) {

		return setValue(DEBTOR, value);
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

	public final Integer getDocumentID() {

		return getValueId(DOCUMENT);
	}

	public final AGStoredFile getDocument() {

		return getValue(DOCUMENT);
	}

	public final AGDemoInvoice setDocument(AGStoredFile value) {

		return setValue(DOCUMENT, value);
	}

	public final Integer getAttachmentsID() {

		return getValueId(ATTACHMENTS);
	}

	public final AGStoredFileSet getAttachments() {

		return getValue(ATTACHMENTS);
	}

	public final AGDemoInvoice setAttachments(AGStoredFileSet value) {

		return setValue(ATTACHMENTS, value);
	}

	public final Integer getContactID() {

		return getValueId(CONTACT);
	}

	public final AGDemoPerson getContact() {

		return getValue(CONTACT);
	}

	public final AGDemoInvoice setContact(AGDemoPerson value) {

		return setValue(CONTACT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoInvoiceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private AGDemoInvoiceModuleInstance m_moduleInstance;
	private AGDemoInvoiceType m_type;
	private String m_creditor;
	private String m_debtor;
	private String m_invoiceNumber;
	private Day m_invoiceDate;
	private Boolean m_lockedItems;
	private AGStoredFile m_document;
	private AGStoredFileSet m_attachments;
	private AGDemoPerson m_contact;
}

