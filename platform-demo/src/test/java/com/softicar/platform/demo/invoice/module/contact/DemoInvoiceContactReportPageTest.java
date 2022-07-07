package com.softicar.platform.demo.invoice.module.contact;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.invoice.module.AbstractDemoInvoiceModuleTest;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.person.module.AGDemoPerson;
import com.softicar.platform.dom.elements.testing.node.tester.DomAutoCompleteTester;
import com.softicar.platform.dom.elements.testing.node.tester.DomPopupTester;
import com.softicar.platform.emf.data.table.testing.EmfDataTableTester;
import org.junit.Test;

public class DemoInvoiceContactReportPageTest extends AbstractDemoInvoiceModuleTest {

	private final AGDemoPerson person;

	public DemoInvoiceContactReportPageTest() {

		this.person = insertDemoPerson(moduleInstance.getDemoPersonModuleInstance(), "Jens", "Maul", 0, Day.today());

		insertDemoInvoice("aaa", Day.today())//
			.setContact(person)
			.save();
		insertDemoInvoice("bbb", Day.today());

		setNodeSupplier(() -> SourceCodeReferencePoints.getReferencePoint(DemoInvoiceContactReportPage.class).createContentNode(moduleInstance));
	}

	@Test
	public void test() {

		EmfDataTableTester table = findEmfDataTable(AGDemoInvoice.TABLE);
		DomPopupTester popup = table.openFilterPopup(IDemoInvoiceContactReportQuery.CONTACT_COLUMN);
		DomAutoCompleteTester<AGDemoPerson> input = popup.findAutoCompleteInput(IDemoInvoiceContactReportQuery.CONTACT_COLUMN);
		input.selectValue(person);
		assertSame(person, input.getNode().getValueOrNull());
	}
}
