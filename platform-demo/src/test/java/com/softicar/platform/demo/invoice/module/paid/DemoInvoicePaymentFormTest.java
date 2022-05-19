package com.softicar.platform.demo.invoice.module.paid;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.demo.invoice.AbstractDemoInvoiceModuleTest;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import java.math.BigDecimal;
import org.junit.Test;

public class DemoInvoicePaymentFormTest extends AbstractDemoInvoiceModuleTest {

	private final AGDemoInvoice demoInvoice;

	public DemoInvoicePaymentFormTest() {

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoInvoice.TABLE, moduleInstance).build());
		this.demoInvoice = insertDemoInvoice("123456", Day.today());
	}

	@Test
	public void testCreateBoxShownAtCreation() {

		openTraitTab();
		findFormPopup(AGDemoInvoice.class).clickNode(EmfI18n.CONFIGURE_TRAIT);
		assertCreationInfoShown();
	}

	@Test
	public void testViewBoxShownAtView() {

		insertTestPayment();
		openTraitTab();
		assertViewInfoShown();
	}

	@Test
	public void testEditBoxShownAtEdit() {

		insertTestPayment();
		openTraitTab();
		findFormPopup(AGDemoInvoice.class).clickCommonActionEditButton();
		assertEditInfoShown();
	}

	@Test
	public void testCorrectBoxShownAfterCreation() {

		openTraitTab();
		findFormPopup(AGDemoInvoice.class).clickNode(EmfI18n.CONFIGURE_TRAIT);
		assertCreationInfoShown();
		EmfFormPopupTester formPopup = findFormPopup(AGDemoInvoice.class);
		formPopup.setInputValue(AGDemoInvoicePayment.PAID_AT, Day.today().toString());
		formPopup.setInputValue(AGDemoInvoicePayment.PAID_AMOUNT, "25");
		formPopup.clickSaveButton();
		assertViewInfoShown();
	}

	@Test
	public void testCorrectBoxShownWhenSwitchingModes() {

		insertTestPayment();
		openTraitTab();
		assertViewInfoShown();
		findFormPopup(AGDemoInvoice.class).clickCommonActionEditButton();
		assertEditInfoShown();
	}

	private void openTraitTab() {

		findManagementDiv(AGDemoInvoice.TABLE).clickShowFormButton();
		EmfFormPopupTester formPopup = findFormPopup(AGDemoInvoice.class);
		formPopup.clickTab(AGDemoInvoicePayment.TABLE.getTitle());
	}

	private void insertTestPayment() {

		insertDemoInvoicePayment(demoInvoice, Day.today(), new BigDecimal(25));
	}

	private void assertCreationInfoShown() {

		EmfFormPopupTester formPopup = findFormPopup(AGDemoInvoice.class);
		formPopup.assertContainsText(DemoI18n.CREATE_TRAIT);
		formPopup.assertDoesNotContainText(DemoI18n.VIEW_TRAIT);
		formPopup.assertDoesNotContainText(DemoI18n.EDIT_TRAIT);
	}

	private void assertViewInfoShown() {

		EmfFormPopupTester formPopup = findFormPopup(AGDemoInvoice.class);
		formPopup.assertDoesNotContainText(DemoI18n.CREATE_TRAIT);
		formPopup.assertContainsText(DemoI18n.VIEW_TRAIT);
		formPopup.assertDoesNotContainText(DemoI18n.EDIT_TRAIT);
	}

	private void assertEditInfoShown() {

		EmfFormPopupTester formPopup = findFormPopup(AGDemoInvoice.class);
		formPopup.assertDoesNotContainText(DemoI18n.CREATE_TRAIT);
		formPopup.assertDoesNotContainText(DemoI18n.VIEW_TRAIT);
		formPopup.assertContainsText(DemoI18n.EDIT_TRAIT);
	}
}
