package com.softicar.platform.demo.module.invoice.stale;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.demo.module.AbstractDemoModuleTest;
import com.softicar.platform.demo.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.module.invoice.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.EmfMarker;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import org.junit.Test;

public class StaleDemoInvoiceRefreshTest extends AbstractDemoModuleTest {

	private static final String OLD = "OLD";
	private static final String NEW = "NEW";
	private final AGDemoInvoice invoice;
	private EmfFormPopupTester formPopup;
	private DomNodeTester refreshButton;

	public StaleDemoInvoiceRefreshTest() {

		this.invoice = insertDemoInvoice(moduleInstance, AGDemoInvoiceTypeEnum.INBOUND, OLD, Day.fromYMD(2020, 1, 1));

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoInvoice.TABLE, moduleInstance).build());
	}

	@Test
	public void testViewActionFromManagementDivWithStaleInvoice() {

		ensurePageIsShown();
		modifyInvoiceConcurrently();

		openInvoiceInViewMode();
		assertStaleDataRefreshButton();
		assertStaleData();
		clickRefreshButtonAndAssertRecentData();
	}

	@Test
	public void testEditActionFromManagementDivWithStaleInvoice() {

		ensurePageIsShown();
		modifyInvoiceConcurrently();

		openInvoiceInEditMode();
		assertStaleDataRefreshButton();
		assertStaleData();
		clickRefreshButtonAndAssertRecentData();
	}

	@Test
	public void testSwitchingFromViewMoveToEditModeWithStaleInvoice() {

		openInvoiceInViewMode();
		modifyInvoiceConcurrently();

		formPopup.clickCommonActionEditButton();
		assertStaleDataRefreshButton();
		assertStaleData();
		clickRefreshButtonAndAssertRecentData();
	}

	@Test
	public void testSaveActionInNormalEditModeWithStaleInvoice() {

		openInvoiceInViewMode();
		formPopup.clickCommonActionEditButton();
		modifyInvoiceConcurrently();

		formPopup.setInputValue(AGDemoInvoice.INVOICE_DATE, "2021-01-01");
		formPopup.clickSaveButton();
		assertStaleDataRefreshButton();
		clickRefreshButtonAndAssertRecentData();
	}

	@Test
	public void testSaveActionInDirectEditModeWithStaleInvoice() {

		openInvoiceInEditMode();
		modifyInvoiceConcurrently();

		formPopup.setInputValue(AGDemoInvoice.INVOICE_DATE, "2021-01-01");
		formPopup.clickSaveButton();
		assertStaleDataRefreshButton();
		clickRefreshButtonAndAssertRecentData();
	}

	private void ensurePageIsShown() {

		findBody();
	}

	private void openInvoiceInViewMode() {

		findManagementDiv(AGDemoInvoice.TABLE).clickShowFormButton();
		this.formPopup = findFormPopup(AGDemoInvoice.class);
	}

	private void openInvoiceInEditMode() {

		findManagementDiv(AGDemoInvoice.TABLE).clickEditButton();
		this.formPopup = findFormPopup(AGDemoInvoice.class);
	}

	private void assertStaleData() {

		formPopup.findNode(AGDemoInvoice.INVOICE_NUMBER).assertContainsText(OLD);
	}

	private void assertStaleDataRefreshButton() {

		this.refreshButton = formPopup.findNode(EmfMarker.INTERACTIVE_REFRESH_BUTTON);
	}

	private void clickRefreshButtonAndAssertRecentData() {

		refreshButton.click();
		formPopup.findNode(AGDemoInvoice.INVOICE_NUMBER).assertContainsText(NEW);
	}

	private void modifyInvoiceConcurrently() {

		// create new transaction
		AGTransaction transaction = new AGTransaction()//
			.setBy(CurrentUser.get())
			.setAt(DayTime.now())
			.save();

		// modify the database record without touching the invoice object
		AGDemoInvoice.TABLE//
			.createUpdate()
			.set(AGDemoInvoice.TRANSACTION, transaction)
			.set(AGDemoInvoice.INVOICE_NUMBER, NEW)
			.where(AGDemoInvoice.ID.isEqual(invoice))
			.execute();
	}
}
