package com.softicar.platform.demo.invoice.module.invoice;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.demo.invoice.module.AbstractDemoInvoiceModuleTest;
import com.softicar.platform.demo.invoice.module.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.emf.form.EmfFormMode;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import org.junit.Test;

/**
 * Verifies the callback for {@link EmfFormMode} changes.
 *
 * @author Daniel Klose
 */
public class DemoInvoiceFormTest extends AbstractDemoInvoiceModuleTest {

	public DemoInvoiceFormTest() {

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoInvoice.TABLE, moduleInstance).build());
	}

	@Test
	public void testCreateBoxShownAtCreation() {

		findManagementDiv(AGDemoInvoice.TABLE).clickCreateButton();
		assertCreateBoxShown();
	}

	@Test
	public void testViewBoxShownAtView() {

		insertDemoInvoice("123456", Day.today());
		findManagementDiv(AGDemoInvoice.TABLE).clickShowFormButton();
		assertViewBoxShown();
	}

	@Test
	public void testEditBoxShownAtEdit() {

		insertDemoInvoice("123456", Day.today());
		findManagementDiv(AGDemoInvoice.TABLE).clickEditButton();
		assertEditBoxShown();
	}

	@Test
	public void testCorrectBoxShownAfterCreation() {

		findManagementDiv(AGDemoInvoice.TABLE).clickCreateButton();
		assertCreateBoxShown();
		EmfFormPopupTester formPopup = findFormPopup(AGDemoInvoice.class);
		formPopup.setInputValue(AGDemoInvoice.TYPE, AGDemoInvoiceTypeEnum.INBOUND.name());
		formPopup.setInputValue(AGDemoInvoice.CREDITOR, "Foo");
		formPopup.setInputValue(AGDemoInvoice.INVOICE_NUMBER, "123465");
		formPopup.setInputValue(AGDemoInvoice.INVOICE_DATE, Day.today().toString());
		formPopup.clickSaveButton();
		assertViewBoxShown();
	}

	@Test
	public void testCorrectBoxShownWhenSwitchingModes() {

		insertDemoInvoice("123456", Day.today());
		findManagementDiv(AGDemoInvoice.TABLE).clickShowFormButton();
		assertViewBoxShown();
		findFormPopup(AGDemoInvoice.class).clickCommonActionEditButton();
		assertEditBoxShown();
	}

	private void assertCreateBoxShown() {

		EmfFormPopupTester formPopup = findFormPopup(AGDemoInvoice.class);
		formPopup.assertContainsText(DemoI18n.CREATE);
		formPopup.assertDoesNotContainText(DemoI18n.VIEW);
		formPopup.assertDoesNotContainText(DemoI18n.EDIT);
	}

	private void assertViewBoxShown() {

		EmfFormPopupTester formPopup = findFormPopup(AGDemoInvoice.class);
		formPopup.assertDoesNotContainText(DemoI18n.CREATE);
		formPopup.assertContainsText(DemoI18n.VIEW);
		formPopup.assertDoesNotContainText(DemoI18n.EDIT);
	}

	private void assertEditBoxShown() {

		EmfFormPopupTester formPopup = findFormPopup(AGDemoInvoice.class);
		formPopup.assertDoesNotContainText(DemoI18n.CREATE);
		formPopup.assertDoesNotContainText(DemoI18n.VIEW);
		formPopup.assertContainsText(DemoI18n.EDIT);
	}
}
