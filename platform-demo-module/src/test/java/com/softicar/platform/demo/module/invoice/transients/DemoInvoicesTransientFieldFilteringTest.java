package com.softicar.platform.demo.module.invoice.transients;

import com.softicar.platform.demo.module.AbstractDemoModuleTest;
import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.demo.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.module.test.fixture.DemoInvoicesTextFixture;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomPopupTester;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;
import com.softicar.platform.emf.data.table.testing.EmfDataTableTester;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.test.IEmfTestEngineMethods;
import org.junit.Rule;
import org.junit.Test;

public class DemoInvoicesTransientFieldFilteringTest extends AbstractDemoModuleTest implements IEmfTestEngineMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();

	public DemoInvoicesTransientFieldFilteringTest() {

		new DemoInvoicesTextFixture(moduleInstance).apply();

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoInvoice.TABLE, moduleInstance).build());
	}

	@Test
	public void testUnfiltered() {

		EmfDataTableTester table = findEmfDataTable(AGDemoInvoice.TABLE);
		assertEquals("00001|00002|00003", table.getTextInColumns(AGDemoInvoice.INVOICE_NUMBER));
		assertEquals("15.0000|21346.0000|97.0000", table.getTextInColumns(AGDemoInvoice.GROSS_AMOUNT_FIELD));
	}

	@Test
	public void testFilterByGrossAmount() {

		EmfDataTableTester table = findEmfDataTable(AGDemoInvoice.TABLE);

		DomPopupTester popup = table.openFilterPopup(AGDemoInvoice.GROSS_AMOUNT_FIELD);
		popup.setInputValue(EmfDataTableDivMarker.FILTER_INPUT_VALUE, "21346");
		popup.clickNode(EmfDataTableDivMarker.FILTER_EXECUTE_BUTTON);

		assertEquals("00002", table.getTextInColumns(AGDemoInvoice.INVOICE_NUMBER));
		assertEquals("21346.0000", table.getTextInColumns(AGDemoInvoice.GROSS_AMOUNT_FIELD));
	}

	@Test
	public void testFilterByGrossAmountWithDecimalPlaces() {

		EmfDataTableTester table = findEmfDataTable(AGDemoInvoice.TABLE);

		DomPopupTester popup = table.openFilterPopup(AGDemoInvoice.GROSS_AMOUNT_FIELD);
		popup.setInputValue(EmfDataTableDivMarker.FILTER_INPUT_VALUE, "15.0");
		popup.clickNode(EmfDataTableDivMarker.FILTER_EXECUTE_BUTTON);

		assertEquals("00001", table.getTextInColumns(AGDemoInvoice.INVOICE_NUMBER));
		assertEquals("15.0000", table.getTextInColumns(AGDemoInvoice.GROSS_AMOUNT_FIELD));

		// regression: ensure the filter value is displayed correctly
		assertEquals(//
			DemoI18n.GROSS_AMOUNT + "|= 15",
			table.findHeaderCell(AGDemoInvoice.GROSS_AMOUNT_FIELD).getAllTextInDocument("|"));

		// regression: ensure the filter input contains the expected value
		table//
			.openFilterPopup(AGDemoInvoice.GROSS_AMOUNT_FIELD)
			.findInput(EmfDataTableDivMarker.FILTER_INPUT_VALUE)
			.assertInputValue("15");
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}
}
