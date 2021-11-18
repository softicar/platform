package com.softicar.platform.demo.module.invoice.transients.sorting;

import com.softicar.platform.demo.module.AbstractDemoModuleTest;
import com.softicar.platform.demo.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.module.test.fixture.DemoInvoicesTextFixture;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.elements.testing.node.tester.DomTableTester;
import com.softicar.platform.emf.data.table.EmfDataTableDivMarker;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import org.junit.Rule;
import org.junit.Test;

public class DemoInvoicesTransientFieldSortingTest extends AbstractDemoModuleTest implements IDomTestEngineMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();

	public DemoInvoicesTransientFieldSortingTest() {

		new DemoInvoicesTextFixture(moduleInstance).apply();

		setNodeSupplier(() -> new EmfManagementDivBuilder<>(AGDemoInvoice.TABLE, moduleInstance).build());
	}

	@Test
	public void testUnordered() {

		DomTableTester table = findTable(AGDemoInvoice.TABLE);
		assertEquals("00001|00002|00003", table.getTextInColumns(AGDemoInvoice.INVOICE_NUMBER));
		assertEquals("15.0000|21346.0000|97.0000", table.getTextInColumns(AGDemoInvoice.GROSS_AMOUNT_FIELD));
	}

	@Test
	public void testOrderedAscendingly() {

		DomTableTester table = findTable(AGDemoInvoice.TABLE);

		DomNodeTester headerCell = table.findHeaderCells(AGDemoInvoice.GROSS_AMOUNT_FIELD).assertOne();
		headerCell.clickNode(EmfDataTableDivMarker.ORDER_BY_BUTTON);

		assertEquals("00001|00003|00002", table.getTextInColumns(AGDemoInvoice.INVOICE_NUMBER));
		assertEquals("15.0000|97.0000|21346.0000", table.getTextInColumns(AGDemoInvoice.GROSS_AMOUNT_FIELD));
	}

	@Test
	public void testOrderedDescendingly() {

		DomTableTester table = findTable(AGDemoInvoice.TABLE);

		DomNodeTester headerCell = table.findHeaderCells(AGDemoInvoice.GROSS_AMOUNT_FIELD).assertOne();
		headerCell.clickNode(EmfDataTableDivMarker.ORDER_BY_BUTTON);
		headerCell.clickNode(EmfDataTableDivMarker.ORDER_BY_BUTTON);

		assertEquals("00002|00003|00001", table.getTextInColumns(AGDemoInvoice.INVOICE_NUMBER));
		assertEquals("21346.0000|97.0000|15.0000", table.getTextInColumns(AGDemoInvoice.GROSS_AMOUNT_FIELD));
	}

	@Override
	public IDomTestEngine getEngine() {

		return engine;
	}
}
