package com.softicar.platform.emf.management;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.data.table.IEmfDataTableRowCustomizer;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.List;
import java.util.Optional;

public interface IEmfManagementConfiguration<R extends IEmfTableRow<R, ?>> {

	List<Pair<IEmfAttribute<R, ?>, OrderDirection>> getOrderBys();

	Optional<IEmfDataTableRowCustomizer<R>> getRowCustomizer();
}
