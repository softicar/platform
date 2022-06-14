package com.softicar.platform.emf.data.table.persistence;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.map.index.IndexHashMap;
import com.softicar.platform.emf.data.table.EmfDataTableOrdering;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.IEmfDataTableController;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.title.EmfDataTableColumnTitlesHash;
import com.softicar.platform.emf.persistence.CurrentEmfPersistenceApi;
import com.softicar.platform.emf.persistence.EmfPersistentTableConfiguration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Restores a previously-saved column configuration of an {@link IEmfDataTable}.
 *
 * @author Alexander Schmidt
 */
public class EmfPersistentTableConfigurationRestorer<R> {

	private final IEmfDataTableController<R> controller;

	/**
	 * Constructs a new {@link EmfPersistentTableConfigurationRestorer} that
	 * writes data to the given {@link IEmfDataTableController}.
	 *
	 * @param controller
	 *            the {@link IEmfDataTableController} (never <i>null</i>)
	 */
	public EmfPersistentTableConfigurationRestorer(IEmfDataTableController<R> controller) {

		this.controller = Objects.requireNonNull(controller);
	}

	/**
	 * Loads a persistently-saved column configuration, and restores it by
	 * applying it to the {@link IEmfDataTableController}.
	 */
	public void restore() {

		var identifier = controller.getDataTable().getIdentifier();
		if (identifier.isPresent()) {
			CurrentEmfPersistenceApi//
				.get()
				.loadPersistentTableConfiguration(identifier, new EmfDataTableColumnTitlesHash(controller))
				.ifPresent(this::apply);
		}
	}

	private void apply(EmfPersistentTableConfiguration configuration) {

		applyColumnPositions(configuration);
		applyHiddenColumnIndexes(configuration);
		applyColumnOrderBys(configuration);
		applyPageSize(configuration);
	}

	private void applyColumnPositions(EmfPersistentTableConfiguration configuration) {

		var columns = controller.getColumnsInDefaultOrder();
		List<IDataTableColumn<R, ?>> sortedColumns = configuration.columnPositions//
			.stream()
			.map(columns::get)
			.map(IEmfDataTableColumn::getDataColumn)
			.collect(Collectors.toList());
		controller.setCustomColumnIndexMap(new IndexHashMap<>(sortedColumns));
	}

	private void applyHiddenColumnIndexes(EmfPersistentTableConfiguration configuration) {

		var columns = controller.getColumnsInDefaultOrder();
		for (Integer index: configuration.hiddenColumnIndexes) {
			columns.get(index).setHidden(true);
		}
	}

	private void applyColumnOrderBys(EmfPersistentTableConfiguration configuration) {

		var columns = controller.getColumnsInDefaultOrder();
		var ordering = new EmfDataTableOrdering<R>();
		for (var orderBy: configuration.columnOrderBys) {
			IEmfDataTableColumn<R, ?> column = columns.get(orderBy.columnIndex);
			ordering.add(column.getDataColumn(), orderBy.direction);
		}
		controller.setOrdering(ordering);
	}

	private void applyPageSize(EmfPersistentTableConfiguration configuration) {

		Optional.ofNullable(configuration.pageSize).ifPresent(pageSize -> controller.setPageSize(pageSize));
	}
}
