package com.softicar.platform.emf.data.table.configuration.testing;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.supplier.LazySupplier;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.elements.testing.node.tester.IDomNodeTesterFindMethods;
import com.softicar.platform.emf.EmfTestMarker;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;

public class EmfDataTableConfigurationPopupAsserter {

	private final IDomNodeTesterFindMethods methods;
	private final LazySupplier<List<DomNodeTester>> rows;
	private int index;
	private DomNodeTester row;

	public EmfDataTableConfigurationPopupAsserter(IDomNodeTesterFindMethods methods) {

		this.methods = methods;
		this.rows = new LazySupplier<>(this::findRows);
		this.index = 0;
		this.row = null;
	}

	public EmfDataTableConfigurationPopupAsserter assertPageSize(String expected) {

		methods.findInput(EmfTestMarker.DATA_TABLE_CONFIGURATION_PAGE_SIZE_INPUT).assertInputValue(expected);
		return this;
	}

	public EmfDataTableConfigurationPopupAsserter assertRow(boolean checked, String title) {

		return assertRow(checked, title, null, null);
	}

	public EmfDataTableConfigurationPopupAsserter assertRow(boolean checked, String title, OrderDirection direction, Integer priority) {

		this.row = rows.get().get(index);
		row//
			.findCheckbox(EmfTestMarker.DATA_TABLE_CONFIGURATION_SELECTION_CHECKBOX)
			.assertChecked(checked);
		row//
			.findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_TITLE_CELL)
			.assertContainsText(title);
		row//
			.findSelect(EmfTestMarker.DATA_TABLE_CONFIGURATION_ORDER_DIRECTION_SELECT)
			.assertSelected(Optional.ofNullable(direction).map(OrderDirection::toDisplay).map(IDisplayString::toString).orElse("-"));
		row//
			.findSelect(EmfTestMarker.DATA_TABLE_CONFIGURATION_ORDER_PRIORITY_SELECT)
			.assertSelected(Optional.ofNullable(priority).map(String::valueOf).orElse("-"));
		this.index++;
		return this;
	}

	public void assertNoMoreRows() {

		Assert.assertEquals(index, rows.get().size());
	}

	private List<DomNodeTester> findRows() {

		return methods
			.findNode(EmfTestMarker.DATA_TABLE_CONFIGURATION_TABLE_DIV)//
			.findNodes(EmfTestMarker.DATA_TABLE_BODY_ROW)
			.assertSome();
	}
}
