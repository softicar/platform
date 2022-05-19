package com.softicar.platform.demo.module.invoice.item.lock;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.demo.invoice.AbstractDemoInvoiceModuleTest;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.invoice.module.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import com.softicar.platform.emf.test.tester.EmfManagementDivTester;
import java.math.BigDecimal;
import org.junit.Test;

public class DemoInvoiceItemLockTest extends AbstractDemoInvoiceModuleTest {

	public DemoInvoiceItemLockTest() {

		AGDemoInvoice invoice = insertDemoInvoice(moduleInstance, AGDemoInvoiceTypeEnum.INBOUND, "00001", Day.fromYMD(2020, 1, 1));
		insertDemoInvoiceItem(invoice, "Paper", 100, BigDecimal.valueOf(12), BigDecimal.valueOf(10));
		insertDemoInvoiceItem(invoice, "Pens", 3, BigDecimal.valueOf(3), BigDecimal.valueOf(2));

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoInvoice.TABLE, moduleInstance).build());
	}

	@Test
	public void testUnlocked() {

		// open invoice pop-up
		EmfManagementDivTester invoiceManagementDiv = findManagementDiv(AGDemoInvoice.TABLE);
		invoiceManagementDiv.clickEditButton();

		// check that invoice items can be added
		EmfFormPopupTester popup = findFormPopup(AGDemoInvoice.class);
		popup.clickTab(DemoI18n.DEMO_INVOICE_ITEMS);
		popup.findManagementDiv().assertNewEntryButton(true);
	}

	@Test
	public void testLocked() {

		// open invoice pop-up
		EmfManagementDivTester invoiceManagementDiv = findManagementDiv(AGDemoInvoice.TABLE);
		invoiceManagementDiv.clickEditButton();

		// lock the invoice items
		EmfFormPopupTester popup = findFormPopup(AGDemoInvoice.class);
		popup.clickCheckbox(AGDemoInvoice.LOCKED_ITEMS);
		popup.clickSaveButton();

		// check that invoice items cannot be added
		popup.clickTab(DemoI18n.DEMO_INVOICE_ITEMS);
		popup.findManagementDiv().assertNewEntryButton(false);
	}
}
