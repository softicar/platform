package com.softicar.platform.demo.module;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.workflow.module.WorkflowI18n;

public interface DemoI18n extends WorkflowI18n {

	I18n0 DEMO = new I18n0("Demo")//
		.de("Demo");
	I18n0 DEMO_INVOICE = new I18n0("Demo Invoice")//
		.de("Demo-Rechnung");
	I18n0 DEMO_INVOICE_ITEM = new I18n0("Demo Invoice Item")//
		.de("Demo-Rechnungsposition");
	I18n0 DEMO_INVOICE_ITEMS = new I18n0("Demo Invoice Items")//
		.de("Demo-Rechnungspositionen");
	I18n0 DEMO_INVOICES = new I18n0("Demo Invoices")//
		.de("Demo-Rechnungen");
	I18n0 DEMO_MODULE_INSTANCE = new I18n0("Demo Module Instance")//
		.de("Demo-Modulinstanz");
	I18n0 DEMO_MODULE_INSTANCES = new I18n0("Demo Module Instances")//
		.de("Demo-Modulinstanzen");
	I18n0 GROSS_AMOUNT = new I18n0("Gross Amount")//
		.de("Bruttobetrag");
	I18n0 INVOICE = new I18n0("Invoice")//
		.de("Rechnung");
	I18n0 INVOICE_DATE = new I18n0("Invoice Date")//
		.de("Rechnungsdatum");
	I18n0 INVOICE_NUMBER = new I18n0("Invoice Number")//
		.de("Rechnungsnummer");
	I18n0 ITEM = new I18n0("Item")//
		.de("Position");
	I18n0 LOCKED_ITEMS = new I18n0("Locked Items")//
		.de("Gesperrte Positionen.");
	I18n0 NET_AMOUNT = new I18n0("Net Amount")//
		.de("Nettobetrag");
	I18n0 QUANTITY = new I18n0("Quantity")//
		.de("Menge");
}
