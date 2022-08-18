package com.softicar.platform.core.module.user.configuration.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.elements.testing.node.tester.IDomNodeTesterFindMethods;
import com.softicar.platform.emf.EmfTestMarker;

class EmfDataTableConfigurationPopupTestInteractor {

	private final IDomNodeTesterFindMethods methods;

	public EmfDataTableConfigurationPopupTestInteractor(IDomNodeTesterFindMethods methods) {

		this.methods = methods;
	}

	public EmfDataTableConfigurationPopupTestInteractor open() {

		methods.findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_TABLE_CONFIGURATION_BUTTON).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor clickApply() {

		methods.findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_APPLY_BUTTON).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor clickReset() {

		methods.findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_RESET_BUTTON).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor clickCancel() {

		methods.findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_CANCEL_BUTTON).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor toggle(String expectedTextInRow) {

		getRow(expectedTextInRow).findNode(EmfTestMarker.DATA_TABLE_ROW_SELECTION_CHECKBOX).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor moveDown(String expectedTextInRow) {

		getRow(expectedTextInRow).findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_MOVE_DOWN_BUTTON).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor select(String expectedTextInRow, OrderDirection direction) {

		getRow(expectedTextInRow).findSelect(EmfTestMarker.DATA_TABLE_CONFIGURATION_ORDER_DIRECTION_SELECT).selectValue(direction);
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor enterPriority(String expectedTextInRow, int value) {

		getRow(expectedTextInRow).findSelect(EmfTestMarker.DATA_TABLE_CONFIGURATION_ORDER_PRIORITY_SELECT).selectValue(Integer.valueOf(value));
		return this;
	}

	private DomNodeTester getRow(String expectedTextInRow) {

		return methods
			.findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_TABLE_DIV)
			.findNodes(EmfTestMarker.DATA_TABLE_BODY_ROW)
			.assertSome()
			.stream()
			.filter(it -> it.containsText(expectedTextInRow))
			.findFirst()
			.get();
	}
}
