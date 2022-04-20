package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbBigDecimalField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import java.math.BigDecimal;

public class EmfTestInvoiceItemGenerated extends AbstractDbObject<EmfTestInvoiceItem> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestInvoiceItem, EmfTestInvoiceItemGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "InvoiceItem", EmfTestInvoiceItem::new, EmfTestInvoiceItem.class);
//	static {
//		BUILDER.setTitle(IDisplayString.create("InvoiceItem"));
//		BUILDER.setTitle(IDisplayString.create("InvoiceItems"));
//	}
	public static final IDbIdField<EmfTestInvoiceItem> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestInvoiceItem, EmfTestInvoice> INVOICE = BUILDER.addForeignField("invoice", o->o.invoice, (o,v)->o.invoice=v, EmfTestInvoice.ID);
	public static final IDbIntegerField<EmfTestInvoiceItem> POSITION = BUILDER.addIntegerField("position", o -> o.position, (o, v) -> o.position = v);
	public static final IDbStringField<EmfTestInvoiceItem> DESCRIPTION = BUILDER.addStringField("description", o -> o.description, (o, v) -> o.description = v).setNullable().setDefault(null);
	public static final IDbBigDecimalField<EmfTestInvoiceItem> QUANTITY = BUILDER.addBigDecimalField("quantity", o -> o.quantity, (o, v) -> o.quantity = v).setNullable().setDefault(null);
	public static final IDbKey<EmfTestInvoiceItem> UK_INVOICE_POSITION = BUILDER.addUniqueKey("invoicePosition", INVOICE, POSITION);
	public static final EmfTestInvoiceItemTable TABLE = new EmfTestInvoiceItemTable(BUILDER);
	// @formatter:on

	private Integer id;
	private EmfTestInvoice invoice;
	private Integer position;
	private String description;
	private BigDecimal quantity;

	public EmfTestInvoice getInvoice() {

		return getValue(INVOICE);
	}

	public EmfTestInvoiceItem setInvoice(EmfTestInvoice invoice) {

		return setValue(INVOICE, invoice);
	}

	public EmfTestInvoiceItem setPosition(Integer position) {

		return setValue(POSITION, position);
	}

	public Integer getPosition() {

		return getValue(POSITION);
	}

	public EmfTestInvoiceItem setDescription(String description) {

		return setValue(DESCRIPTION, description);
	}

	public String getDescription() {

		return getValue(DESCRIPTION);
	}

	public EmfTestInvoiceItem setDate(BigDecimal quantity) {

		return setValue(QUANTITY, quantity);
	}

	public BigDecimal getDate() {

		return getValue(QUANTITY);
	}

	@Override
	public EmfTestInvoiceItemTable table() {

		return TABLE;
	}
}
