package com.softicar.platform.emf.data.table.persistence;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.map.index.IndexHashMap;
import com.softicar.platform.emf.data.table.EmfDataTableOrdering.OrderingEntry;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.IEmfDataTableController;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.title.EmfDataTableColumnTitlesHasher;
import com.softicar.platform.emf.persistence.CurrentEmfPersistenceApi;
import com.softicar.platform.emf.persistence.EmfPersistentTableConfiguration;
import com.softicar.platform.emf.persistence.EmfPersistentTableConfiguration.OrderBy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Persistently saves the current column configuration of an
 * {@link IEmfDataTable}.
 *
 * @author Alexander Schmidt
 */
public class EmfPersistentTableConfigurationSaver<R> {

	private final IEmfDataTableController<R> controller;

	/**
	 * Constructs a new {@link EmfPersistentTableConfigurationSaver} that reads
	 * data from the given {@link IEmfDataTableController}.
	 *
	 * @param controller
	 *            the {@link IEmfDataTableController} (never <i>null</i>)
	 */
	public EmfPersistentTableConfigurationSaver(IEmfDataTableController<R> controller) {

		this.controller = Objects.requireNonNull(controller);
	}

	/**
	 * Persistently saves the current column configuration, as provided by the
	 * {@link IEmfDataTableController}.
	 */
	public void save() {

		var identifier = controller.getDataTable().getIdentifier();
		if (identifier.isPresent()) {
			var configuration = createPersistentTableConfiguration();
			CurrentEmfPersistenceApi.get().savePersistentTableConfiguration(controller.getEmfDataTablePath(), configuration);
		}
	}

	private EmfPersistentTableConfiguration createPersistentTableConfiguration() {

		var configuration = new EmfPersistentTableConfiguration();
		configuration.columnTitlesHash = determineColumnTitlesHash();
		configuration.columnPositions = determineColumnPositions();
		configuration.hiddenColumnIndexes = determineHiddenColumnIndexes();
		configuration.columnOrderBys = determineColumnOrderBys();
		configuration.pageSize = determinePageSize();
		return configuration;
	}

	private String determineColumnTitlesHash() {

		return new EmfDataTableColumnTitlesHasher(controller).getHash();
	}

	private List<Integer> determineColumnPositions() {

		var indexMap = new IndexHashMap<>(controller.getColumnsInDefaultOrder());
		return controller//
			.getColumnsInCustomOrder()
			.stream()
			.map(indexMap::getIndex)
			.collect(Collectors.toList());
	}

	private Set<Integer> determineHiddenColumnIndexes() {

		var columns = controller.getColumnsInDefaultOrder();
		Set<Integer> hiddenIndexes = new TreeSet<>();
		for (int index = 0; index < columns.size(); index++) {
			if (columns.get(index).isHidden()) {
				hiddenIndexes.add(index);
			}
		}
		return hiddenIndexes;
	}

	private List<OrderBy> determineColumnOrderBys() {

		var orderBys = new ArrayList<OrderBy>();
		var dataColumns = controller.getColumnsInDefaultOrder().stream().map(IEmfDataTableColumn::getDataColumn).collect(Collectors.toList());
		var columnIndexMap = new IndexHashMap<IDataTableColumn<R, ?>>(dataColumns);
		for (OrderingEntry<R> entry: controller.getOrdering().getEntries()) {
			var orderBy = new OrderBy();
			orderBy.direction = entry.getDirection();
			orderBy.columnIndex = columnIndexMap.getIndex(entry.getColumn());
			orderBys.add(orderBy);
		}
		return orderBys;
	}

	private Integer determinePageSize() {

		return controller.getPageSize();
	}
}
