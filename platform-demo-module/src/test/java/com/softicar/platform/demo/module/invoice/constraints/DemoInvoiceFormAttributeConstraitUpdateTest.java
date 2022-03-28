package com.softicar.platform.demo.module.invoice.constraints;

import com.softicar.platform.demo.module.AbstractDemoModuleTest;
import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.demo.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.module.invoice.AGDemoInvoiceGenerated;
import com.softicar.platform.demo.module.invoice.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import org.junit.Ignore;
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
public class DemoInvoiceFormAttributeConstraitUpdateTest extends AbstractDemoModuleTest {

	public DemoInvoiceFormAttributeConstraitUpdateTest() {

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoInvoice.TABLE, moduleInstance).build());
	}

	// FIXME activate the test
	@Test
	@Ignore
	public void testWithChangingTypeSelection() {

		findManagementDiv().clickCreateButton();
		var popup = findFormPopup(AGDemoInvoice.class);

		popup//
			.assertDoesNotContainText(DemoI18n.CREDITOR)
			.assertDoesNotContainText(DemoI18n.DEBTOR);

		popup//
			.setInputValue(AGDemoInvoice.TYPE, AGDemoInvoiceTypeEnum.INBOUND.getRecord().toDisplay().toString())
			.assertDoesNotContainText(DemoI18n.CREDITOR);
	}
}
