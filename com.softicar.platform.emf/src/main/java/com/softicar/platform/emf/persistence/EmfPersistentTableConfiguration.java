package com.softicar.platform.emf.persistence;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import java.util.List;
import java.util.Set;

/**
 * Represents the persistent aspects of an {@link IEmfDataTable} configuration.
 *
 * @author Oliver Richers
 */
public class EmfPersistentTableConfiguration {

	public String columnTitlesHash;
	public Set<Integer> hiddenColumnIndexes;
	public List<Integer> columnPositions;
	public List<OrderBy> columnOrderBys;
	public Integer pageSize;

	public static class OrderBy {

		public Integer columnIndex;
		public OrderDirection direction;
	}
}
