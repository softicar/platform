package com.softicar.platform.emf.data.table.configuration.testing;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.testing.node.tester.IDomNodeTesterFindMethods;
import com.softicar.platform.emf.EmfTestMarker;
import com.softicar.platform.emf.data.table.EmfDataTableOrdering;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.Assert;

public class EmfDataTableConfigurationTableAsserter {

	private final IDomNodeTesterFindMethods methods;
	private final IStaticObject tableMarker;
	private int orderingIndex;

	public EmfDataTableConfigurationTableAsserter(IDomNodeTesterFindMethods methods, IStaticObject tableMarker) {

		this.methods = methods;
		this.tableMarker = tableMarker;
		this.orderingIndex = 0;
	}

	public EmfDataTableConfigurationTableAsserter assertRows(int expected) {

		methods//
			.findNode(EmfTestMarker.DATA_TABLE, tableMarker)
			.findNodes(EmfTestMarker.DATA_TABLE_BODY_ROW)
			.assertSize(expected);
		return this;
	}

	public EmfDataTableConfigurationTableAsserter assertDisplayedColumns(String...columnNames) {

		String expectedHeaderText = Arrays.asList(columnNames).stream().collect(Collectors.joining("|"));

		String actualHeaderText = getEmfDataTable()
			.getController()
			.getColumnsInCustomOrder()
			.stream()
			.filter(column -> !column.isConcealed())
			.filter(column -> !column.isHidden())
			.map(column -> column.getTitle().toString())
			.collect(Collectors.joining("|"));

		Assert.assertEquals(expectedHeaderText, actualHeaderText);
		return this;
	}

	public EmfDataTableConfigurationTableAsserter assertNextOrdering(IDataTableColumn<?, ?> column, OrderDirection direction) {

		Assert.assertEquals(direction, getOrderDirection(column));
		Assert.assertEquals(orderingIndex, getOrderIndex(column));
		this.orderingIndex++;
		return this;
	}

	public EmfDataTableConfigurationTableAsserter assertNoMoreOrdering() {

		Assert.assertEquals(orderingIndex, getOrdering().getEntries().size());
		return this;
	}

	private OrderDirection getOrderDirection(IDataTableColumn<?, ?> column) {

		return getOrdering()//
			.getOrderDirection(CastUtils.cast(column))
			.get();
	}

	private int getOrderIndex(IDataTableColumn<?, ?> column) {

		return getOrdering()//
			.getColumnIndex(CastUtils.cast(column))
			.get();
	}

	private EmfDataTableOrdering<?> getOrdering() {

		return getEmfDataTable().getController().getOrdering();
	}

	private IEmfDataTable<?> getEmfDataTable() {

		return methods//
			.findNode(EmfTestMarker.DATA_TABLE, tableMarker)
			.getNode(IEmfDataTable.class);
	}
}
