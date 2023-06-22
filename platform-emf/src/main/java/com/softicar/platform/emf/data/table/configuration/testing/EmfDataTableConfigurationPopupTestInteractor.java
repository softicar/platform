package com.softicar.platform.emf.data.table.configuration.testing;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.elements.testing.node.tester.IDomNodeTesterFindMethods;
import com.softicar.platform.emf.EmfTestMarker;

public class EmfDataTableConfigurationPopupTestInteractor {

	private final IDomNodeTesterFindMethods methods;

	public EmfDataTableConfigurationPopupTestInteractor(IDomNodeTesterFindMethods methods) {

		this.methods = methods;
	}

	public EmfDataTableConfigurationPopupTestInteractor openConfiguration() {

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

	public EmfDataTableConfigurationPopupTestInteractor clickPageSizeAll() {

		methods.findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_PAGE_SIZE_ALL_BUTTON).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor clickRowSelectionAll() {

		methods.findNode(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_SELECT_ALL_ON_CURRENT_PAGE).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor clickRowSelectionInvert() {

		methods.findNode(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_SELECT_INVERT_CURRENT_PAGE).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor clickRowSelectionNone() {

		methods.findNode(EmfTestMarker.DATA_TABLE_ROW_SELECTION_BUTTON_UNSELECT_ALL_ON_CURRENT_PAGE).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor toggle(String expectedTextInRow) {

		getRow(expectedTextInRow).findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_SELECTION_CHECKBOX).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor moveDown(String expectedTextInRow) {

		getRow(expectedTextInRow).findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_MOVE_DOWN_BUTTON).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor moveUp(String expectedTextInRow) {

		getRow(expectedTextInRow).findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_MOVE_UP_BUTTON).click();
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor selectPosition(String expectedTextInRow, int position) {

		getRow(expectedTextInRow).findSelect(EmfTestMarker.DATA_TABLE_CONFIGURATION_POSITION_SELECT).selectValue(position);
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor selectOrdering(String expectedTextInRow, OrderDirection direction, int priority) {

		selectOrderDirection(expectedTextInRow, direction);
		selectOrderPriority(expectedTextInRow, priority);
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor selectOrderDirection(String expectedTextInRow, OrderDirection direction) {

		getRow(expectedTextInRow).findSelect(EmfTestMarker.DATA_TABLE_CONFIGURATION_ORDER_DIRECTION_SELECT).selectValue(direction);
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor selectOrderPriority(String expectedTextInRow, int priority) {

		getRow(expectedTextInRow).findSelect(EmfTestMarker.DATA_TABLE_CONFIGURATION_ORDER_PRIORITY_SELECT).selectValue(Integer.valueOf(priority));
		return this;
	}

	public EmfDataTableConfigurationPopupTestInteractor enterPageSize(String pageSize) {

		methods.findInput(EmfTestMarker.DATA_TABLE_CONFIGURATION_PAGE_SIZE_INPUT).setInputValue(pageSize);
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
