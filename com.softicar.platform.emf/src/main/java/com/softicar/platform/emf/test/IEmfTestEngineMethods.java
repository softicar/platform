package com.softicar.platform.emf.test;

import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.node.tester.AbstractDomNodeTester;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.management.EmfManagementPopup;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.test.tester.EmfFormPopupTester;
import com.softicar.platform.emf.test.tester.EmfManagementDivTester;
import com.softicar.platform.emf.test.tester.EmfManagementPopupTester;

public interface IEmfTestEngineMethods extends IDomTestEngineMethods {

	default EmfManagementDivTester findManagementDiv() {

		return findManagementDiv(findBody());
	}

	default EmfManagementDivTester findManagementDiv(IEmfTable<?, ?, ?> table) {

		return new EmfManagementDivTester(//
			getEngine(),
			findBody()//
				.findNodes(EmfManagementDiv.class)
				.filter(it -> it.getTable() == table)
				.assertOne()
				.assertType(EmfManagementDiv.class));
	}

	default EmfManagementDivTester findManagementDiv(AbstractDomNodeTester<?> scope) {

		return new EmfManagementDivTester(//
			getEngine(),
			scope.findNode(EmfManagementDiv.class).assertType(EmfManagementDiv.class));
	}

	default EmfFormPopupTester findFormPopup(Class<?> clazz) {

		return findBody()//
			.findNodes(EmfFormPopup.class)
			.filter(form -> clazz.isInstance(form.getTableRow()))
			.assertOne(node -> new EmfFormPopupTester(getEngine(), node));
	}

	default EmfManagementPopupTester findManagementPopup(IEmfTable<?, ?, ?> table) {

		return findBody()//
			.findNodes(EmfManagementPopup.class)
			.filter(popup -> popup.getEntityTable().equals(table))
			.assertOne(node -> new EmfManagementPopupTester(getEngine(), node));
	}
}
