package com.softicar.platform.demo.invoice.module.invoice.constraints;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.invoice.module.AbstractDemoInvoiceModuleTest;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoiceGenerated;
import com.softicar.platform.demo.invoice.module.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import org.junit.Test;

/**
 * Verifies the {@link IEmfAttribute} visibility constraints are correctly
 * updated during creation and editing.
 * <p>
 * <ul>
 * <li>For {@link AGDemoInvoiceTypeEnum#INBOUND}, the
 * {@link AGDemoInvoiceGenerated#CREDITOR} attribute is shown.</li>
 * <li>For {@link AGDemoInvoiceTypeEnum#OUTBOUND}, the
 * {@link AGDemoInvoiceGenerated#DEBTOR} attribute is shown.</li>
 * </ul>
 *
 * @author Oliver Richers
 */
public class DemoInvoiceFormAttributeConstraintUpdateTest extends AbstractDemoInvoiceModuleTest {

	private static final String CREDITOR_NAME = "MY CREDITOR";
	private static final String DEBTOR_NAME = "MY DEBTOR";
	private EmfFormPopupTester popup;

	public DemoInvoiceFormAttributeConstraintUpdateTest() {

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoInvoice.TABLE, moduleInstance).build());
	}

	@Test
	public void testWithTogglingTypeSelection() {

		// assert neither creditor nor debtor attributes are shown
		openCreatePopup();
		assertNoInputShown(AGDemoInvoice.CREDITOR);
		assertNoInputShown(AGDemoInvoice.DEBTOR);

		// change to INBOUND and enter a creditor
		setTypeInputValue(AGDemoInvoiceTypeEnum.INBOUND);
		assertInputShown(AGDemoInvoice.CREDITOR);
		assertNoInputShown(AGDemoInvoice.DEBTOR);
		setCreditorInputValue(CREDITOR_NAME);

		// change to OUTBOUND and enter debtor
		setTypeInputValue(AGDemoInvoiceTypeEnum.OUTBOUND);
		assertNoInputShown(AGDemoInvoice.CREDITOR);
		assertInputShown(AGDemoInvoice.DEBTOR);
		setDeptorInputValue(DEBTOR_NAME);

		// fill remaining inputs and save
		setRemainingInputValuesAndSave();

		// check saved invoice
		var invoice = assertOne(AGDemoInvoice.TABLE.loadAll());
		assertEquals(AGDemoInvoiceTypeEnum.OUTBOUND.getRecord(), invoice.getType());
		assertEquals("", invoice.getCreditor());
		assertEquals(DEBTOR_NAME, invoice.getDebtor());
	}

	@Test
	public void testChangingTypeOfExistingInvoice() {

		// create OUTBOUND invoice
		openCreatePopup();
		setTypeInputValue(AGDemoInvoiceTypeEnum.OUTBOUND);
		setDeptorInputValue(DEBTOR_NAME);
		setRemainingInputValuesAndSave();

		// change from OUTBOUND to INBOUND invoice
		openEditPopup();
		setTypeInputValue(AGDemoInvoiceTypeEnum.INBOUND);
		setCreditorInputValue(CREDITOR_NAME);
		popup.clickSaveAndCloseButton();

		// check saved invoice
		var invoice = assertOne(AGDemoInvoice.TABLE.loadAll());
		assertEquals(AGDemoInvoiceTypeEnum.INBOUND.getRecord(), invoice.getType());
		assertEquals(CREDITOR_NAME, invoice.getCreditor());
		assertEquals("", invoice.getDebtor());
	}

	private void openCreatePopup() {

		findManagementDiv().clickCreateButton();
		popup = findFormPopup(AGDemoInvoice.class);
	}

	private void openEditPopup() {

		findManagementDiv().clickEditButton();
		popup = findFormPopup(AGDemoInvoice.class);
	}

	private void setRemainingInputValuesAndSave() {

		popup.setInputValue(AGDemoInvoice.INVOICE_NUMBER, "123");
		popup.setInputValue(AGDemoInvoice.INVOICE_DATE, Day.today().toString());
		popup.clickSaveAndCloseButton();
	}

	private void setTypeInputValue(AGDemoInvoiceTypeEnum type) {

		String typeName = type.getRecord().toDisplay().toString();
		popup.setInputValue(AGDemoInvoice.TYPE, typeName);
	}

	private void setCreditorInputValue(String inputValue) {

		popup.setInputValue(AGDemoInvoice.CREDITOR, inputValue);
	}

	private void setDeptorInputValue(String inputValue) {

		popup.setInputValue(AGDemoInvoice.DEBTOR, inputValue);
	}

	private void assertInputShown(IStaticObject marker) {

		popup//
			.findNode(marker)
			.assertDisplayed();
	}

	private void assertNoInputShown(IStaticObject marker) {

		popup//
			.findNode(marker)
			.assertNotDisplayed();
	}
}
