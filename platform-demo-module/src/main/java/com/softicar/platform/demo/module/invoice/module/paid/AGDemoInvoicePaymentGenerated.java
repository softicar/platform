package com.softicar.platform.demo.module.invoice.module.paid;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbBigDecimalField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.demo.module.core.module.DemoI18n;
import com.softicar.platform.demo.module.invoice.module.invoice.AGDemoInvoice;
import java.math.BigDecimal;

/**
 * This is the automatically generated class AGDemoInvoicePayment for
 * database table <i>Demo.DemoInvoicePayment</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoInvoicePaymentGenerated extends AbstractDbRecord<AGDemoInvoicePayment, AGDemoInvoice> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGDemoInvoicePayment, AGDemoInvoicePaymentGenerated, AGDemoInvoice> BUILDER = new DbTableBuilder<>("Demo", "DemoInvoicePayment", AGDemoInvoicePayment::new, AGDemoInvoicePayment.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_INVOICE_PAYMENT);
		BUILDER.setPluralTitle(DemoI18n.DEMO_INVOICE_PAYMENTS);
	}

	public static final IDbForeignField<AGDemoInvoicePayment, AGDemoInvoice> DEMO_INVOICE = BUILDER.addForeignField("demoInvoice", o->o.m_demoInvoice, (o,v)->o.m_demoInvoice=v, AGDemoInvoice.ID).setTitle(DemoI18n.DEMO_INVOICE);
	public static final IDbDayField<AGDemoInvoicePayment> PAID_AT = BUILDER.addDayField("paidAt", o->o.m_paidAt, (o,v)->o.m_paidAt=v).setTitle(DemoI18n.PAID_AT);
	public static final IDbBigDecimalField<AGDemoInvoicePayment> PAID_AMOUNT = BUILDER.addBigDecimalField("paidAmount", o->o.m_paidAmount, (o,v)->o.m_paidAmount=v).setTitle(DemoI18n.PAID_AMOUNT).setSize(6, 2);
	public static final IDbTableKey<AGDemoInvoicePayment, AGDemoInvoice> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(DEMO_INVOICE));
	public static final AGDemoInvoicePaymentTable TABLE = new AGDemoInvoicePaymentTable(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGDemoInvoicePaymentGenerated() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getDemoInvoiceID() {

		return getValueId(DEMO_INVOICE);
	}

	public final AGDemoInvoice getDemoInvoice() {

		return getValue(DEMO_INVOICE);
	}

	public final Day getPaidAt() {

		return getValue(PAID_AT);
	}

	public final AGDemoInvoicePayment setPaidAt(Day value) {

		return setValue(PAID_AT, value);
	}

	public final BigDecimal getPaidAmount() {

		return getValue(PAID_AMOUNT);
	}

	public final AGDemoInvoicePayment setPaidAmount(BigDecimal value) {

		return setValue(PAID_AMOUNT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoInvoicePaymentTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGDemoInvoice m_demoInvoice;
	private Day m_paidAt;
	private BigDecimal m_paidAmount;
}

