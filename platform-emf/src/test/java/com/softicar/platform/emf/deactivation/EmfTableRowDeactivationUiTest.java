package com.softicar.platform.emf.deactivation;

import com.softicar.platform.dom.elements.DomHeaderCell;
import com.softicar.platform.dom.elements.DomTHead;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.IEmfDataTableRow;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.List;
import org.junit.Test;

public class EmfTableRowDeactivationUiTest extends AbstractEmfTest {

	private static final String A = "A";
	private static final String B = "B";

	@Test
	public void testWithActiveField() {

		insertBaseEntity(A, true);
		insertBaseEntity(B, false);
		setNodeSupplier(() -> new EmfManagementDiv<>(EmfTestObject.TABLE, moduleInstance));

		assertTableColumns("Active", 0);
		assertTableRows(A);

		findToggleCheckbox().click();

		assertTableColumns("Active", 1);
		assertTableRows(A, B);
	}

	@Test
	public void testWithInheritedActiveField() {

		insertTestEntity(A, true);
		insertTestEntity(B, false);
		setNodeSupplier(() -> new EmfManagementDiv<>(EmfTestSubObject.TABLE, moduleInstance));

		assertTableColumns("Active", 0);
		assertTableRows(A);

		findToggleCheckbox().click();

		assertTableColumns("Active", 1);
		assertTableRows(A, B);
	}

	private DomNodeTester findToggleCheckbox() {

		DomNodeTester checkbox = findBody().findNode(DomCheckbox.class);
		checkbox.assertContainsText(EmfI18n.SHOW_INACTIVE);
		return checkbox;
	}

	private void assertTableColumns(String name, int count) {

		findBody()//
			.findNode(EmfManagementDiv.class)
			.findNode(IEmfDataTable.class)
			.findNode(DomTHead.class)
			.findNodes(DomHeaderCell.class)
			.withText(name)
			.assertSize(count);
	}

	private void assertTableRows(String...names) {

		List<DomNodeTester> rows = findBody()//
			.findNode(EmfManagementDiv.class)
			.findNode(IEmfDataTable.class)
			.findNodes(IEmfDataTableRow.class)
			.assertSize(names.length);
		for (int i = 0; i < names.length; i++) {
			rows.get(i).assertContainsText(names[i]);
		}
	}

	private EmfTestSubObject insertTestEntity(String name, boolean active) {

		return EmfTestSubObject.TABLE//
			.createObject(insertBaseEntity(name, active))
			.setName(name)
			.setNotNullableValue(420)
			.save();
	}

	private EmfTestObject insertBaseEntity(String name, boolean active) {

		EmfTestObject testObject = new EmfTestObject()//
			.setName(name)
			.setActive(active)
			.save();
		testObject.addAuthorizedUser(user);
		return testObject;
	}
}
