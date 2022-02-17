package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.demo.module.AbstractDemoModuleTest;
import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import org.junit.Test;

public class DemoInvoiceCreationPopupTest extends AbstractDemoModuleTest {

	public DemoInvoiceCreationPopupTest() {

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoInvoice.TABLE, moduleInstance).build());
	}

	@Test
	public void testDemoInfoBoxIsShown() {

		findManagementDiv(AGDemoInvoice.TABLE).clickCreateButton();
		findFormPopup(AGDemoInvoice.class).assertContainsText(DemoI18n.THIS_IS_A_DEMO);
	}
}
