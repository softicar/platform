package com.softicar.platform.demo.module.invoice.module.invoice.item;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.field.IDbBigDecimalField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.demo.module.core.module.DemoI18n;
import com.softicar.platform.demo.module.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.module.person.module.AGDemoPerson;
import java.math.BigDecimal;

/**
 * This is the automatically generated class AGDemoInvoiceItem for
 * database table <i>Demo.DemoInvoiceItem</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoInvoiceItemGenerated extends AbstractDbObject<AGDemoInvoiceItem> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGDemoInvoiceItem, AGDemoInvoiceItemGenerated> BUILDER = new DbObjectTableBuilder<>("Demo", "DemoInvoiceItem", AGDemoInvoiceItem::new, AGDemoInvoiceItem.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_INVOICE_ITEM);
		BUILDER.setPluralTitle(DemoI18n.DEMO_INVOICE_ITEMS);
	}

	public static final IDbIdField<AGDemoInvoiceItem> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(DemoI18n.ID);
	public static final IDbForeignField<AGDemoInvoiceItem, AGDemoInvoice> INVOICE = BUILDER.addForeignField("invoice", o->o.m_invoice, (o,v)->o.m_invoice=v, AGDemoInvoice.ID).setTitle(DemoI18n.INVOICE);
	public static final IDbStringField<AGDemoInvoiceItem> ITEM = BUILDER.addStringField("item", o->o.m_item, (o,v)->o.m_item=v).setTitle(DemoI18n.ITEM).setMaximumLength(255);
	public static final IDbIntegerField<AGDemoInvoiceItem> QUANTITY = BUILDER.addIntegerField("quantity", o->o.m_quantity, (o,v)->o.m_quantity=v).setTitle(DemoI18n.QUANTITY);
	public static final IDbBigDecimalField<AGDemoInvoiceItem> GROSS_AMOUNT = BUILDER.addBigDecimalField("grossAmount", o->o.m_grossAmount, (o,v)->o.m_grossAmount=v).setTitle(DemoI18n.GROSS_AMOUNT).setSize(20, 4);
	public static final IDbBigDecimalField<AGDemoInvoiceItem> NET_AMOUNT = BUILDER.addBigDecimalField("netAmount", o->o.m_netAmount, (o,v)->o.m_netAmount=v).setTitle(DemoI18n.NET_AMOUNT).setSize(20, 4);
	public static final IDbForeignField<AGDemoInvoiceItem, AGDemoPerson> CLERK = BUILDER.addForeignField("clerk", o->o.m_clerk, (o,v)->o.m_clerk=v, AGDemoPerson.ID).setTitle(DemoI18n.CLERK).setNullable().setDefault(null);
	public static final IDbKey<AGDemoInvoiceItem> UK_INVOICE_ITEM = BUILDER.addUniqueKey("invoiceItem", INVOICE, ITEM);
	public static final AGDemoInvoiceItemTable TABLE = new AGDemoInvoiceItemTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGDemoInvoiceItem> createSelect() {

		return TABLE.createSelect();
	}

	public static AGDemoInvoiceItem get(Integer id) {

		return TABLE.get(id);
	}

	public static AGDemoInvoiceItem loadByInvoiceAndItem(AGDemoInvoice invoice, String item) {

		return TABLE//
				.createSelect()
				.where(INVOICE.equal(invoice))
				.where(ITEM.equal(item))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getInvoiceID() {

		return getValueId(INVOICE);
	}

	public final AGDemoInvoice getInvoice() {

		return getValue(INVOICE);
	}

	public final AGDemoInvoiceItem setInvoice(AGDemoInvoice value) {

		return setValue(INVOICE, value);
	}

	public final String getItem() {

		return getValue(ITEM);
	}

	public final AGDemoInvoiceItem setItem(String value) {

		return setValue(ITEM, value);
	}

	public final Integer getQuantity() {

		return getValue(QUANTITY);
	}

	public final AGDemoInvoiceItem setQuantity(Integer value) {

		return setValue(QUANTITY, value);
	}

	public final BigDecimal getGrossAmount() {

		return getValue(GROSS_AMOUNT);
	}

	public final AGDemoInvoiceItem setGrossAmount(BigDecimal value) {

		return setValue(GROSS_AMOUNT, value);
	}

	public final BigDecimal getNetAmount() {

		return getValue(NET_AMOUNT);
	}

	public final AGDemoInvoiceItem setNetAmount(BigDecimal value) {

		return setValue(NET_AMOUNT, value);
	}

	public final Integer getClerkID() {

		return getValueId(CLERK);
	}

	public final AGDemoPerson getClerk() {

		return getValue(CLERK);
	}

	public final AGDemoInvoiceItem setClerk(AGDemoPerson value) {

		return setValue(CLERK, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoInvoiceItemTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGDemoInvoice m_invoice;
	private String m_item;
	private Integer m_quantity;
	private BigDecimal m_grossAmount;
	private BigDecimal m_netAmount;
	private AGDemoPerson m_clerk;
}

