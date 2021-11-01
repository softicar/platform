package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.data.table.DataTableValueFilterOperator;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.testing.node.iterable.IDomNodeIterable;
import com.softicar.platform.emf.data.table.filter.value.EmfDataTableValueFilterNode;

abstract class AbstractEmfDataTableFilterByValueTest extends AbstractEmfDataTableFilterTest {

	protected void selectFilterType(DataTableValueFilterOperator filterType) {

		IDomNodeIterable<EmfDataTableValueFilterNode<?, ?>> filterNodes = CastUtils.cast(findNodes(EmfDataTableValueFilterNode.class));
		filterNodes.assertOne();
		EmfDataTableValueFilterNode<?, ?> filterNode = filterNodes.iterator().next();
		filterNode.getFilterSelect().setSelectedType(filterType);
	}
}
