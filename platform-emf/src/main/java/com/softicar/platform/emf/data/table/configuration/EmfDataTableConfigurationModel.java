package com.softicar.platform.emf.data.table.configuration;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.map.index.IndexHashMap;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.common.math.Clamping;
import com.softicar.platform.emf.data.table.EmfDataTableOrdering;
import com.softicar.platform.emf.data.table.IEmfDataTableController;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.util.ListShifter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * A data model for the column configuration of an EMF data table.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableConfigurationModel<R> {

	private final IEmfDataTableController<R> controller;
	private final List<IDataTableColumn<R, ?>> columnList;
	private final Map<IDataTableColumn<R, ?>, ColumnState> stateMap;
	private Integer pageSize;

	/**
	 * Constructs a new {@link EmfDataTableConfigurationModel}.
	 *
	 * @param controller
	 *            the {@link IEmfDataTableController} this model interacts with
	 *            (never <i>null</i>)
	 */
	public EmfDataTableConfigurationModel(IEmfDataTableController<R> controller) {

		this.controller = Objects.requireNonNull(controller);
		this.columnList = new ArrayList<>();
		this.stateMap = new HashMap<>();
		this.pageSize = null;
	}

	/**
	 * Loads this {@link EmfDataTableConfigurationModel} from the
	 * {@link IEmfDataTableController}, discarding any non-applied state
	 * changes.
	 *
	 * @return this {@link EmfDataTableConfigurationModel} (never <i>null</i>)
	 */
	public EmfDataTableConfigurationModel<R> load() {

		reloadColumnList(controller::getColumnsInCustomOrder);
		this.columnList.forEach(column -> this.stateMap.put(column, createCurrentState(column)));
		this.pageSize = controller.getPageSize();
		return this;
	}

	/**
	 * Resets this {@link EmfDataTableConfigurationModel} to default states,
	 * discarding any non-applied changes.
	 *
	 * @return this {@link EmfDataTableConfigurationModel} (never <i>null</i>)
	 */
	public EmfDataTableConfigurationModel<R> loadDefaults() {

		reloadColumnList(controller::getColumnsInOriginalOrder);
		this.columnList.forEach(column -> this.stateMap.put(column, createDefaultState()));
		this.pageSize = controller.getDefaultPageSize();
		return this;
	}

	/**
	 * Applies this {@link EmfDataTableConfigurationModel} to the
	 * {@link IEmfDataTableController}.
	 */
	public void apply() {

		for (var entry: stateMap.entrySet()) {
			var column = entry.getKey();
			var state = entry.getValue();
			controller.setHidden(controller.getEmfColumn(column), !state.isSelected());
		}

		var ordering = new EmfDataTableOrdering<R>();
		for (var orderedColumn: getOrderedColumns()) {
			ordering.add(orderedColumn.getColumn(), orderedColumn.getDirection());
		}
		controller.setOrdering(ordering);

		controller.setCustomColumnIndexMap(new IndexHashMap<>(getColumns()));
		controller.setPageSize(Math.max(1, IntegerParser.parse(pageSize + "").orElse(controller.getDefaultPageSize())));
		controller.refreshEmfDataTable();
	}

	/**
	 * Returns the currently available columns, in the current order.
	 *
	 * @return the available columns (never <i>null</i>)
	 */
	public List<IDataTableColumn<R, ?>> getColumns() {

		return columnList;
	}

	/**
	 * Returns the currently selected columns.
	 *
	 * @return the selected columns (never <i>null</i>)
	 */
	public Collection<IDataTableColumn<R, ?>> getSelectedColumns() {

		return stateMap//
			.entrySet()
			.stream()
			.filter(entry -> entry.getValue().isSelected())
			.map(Entry::getKey)
			.collect(Collectors.toList());
	}

	/**
	 * Moves the given {@link IDataTableColumn} by one step in the internal
	 * column list, according to the given direction.
	 * <p>
	 * This method does nothing if any of the following applies:
	 * <ul>
	 * <li>The given column is not contained in the internal list.</li>
	 * <li>The given column shall be moved in a direction in which there are no
	 * further columns.</li>
	 * </ul>
	 *
	 * @param column
	 *            the column to move (never <i>null</i>)
	 * @param down
	 *            <i>true</i> if the column shall be moved downwards (i.e.
	 *            towards index 0); <i>false</i> if the column shall be moved
	 *            upwards (i.e. towards the maximum index)
	 */
	public void move(IDataTableColumn<R, ?> column, boolean down) {

		Objects.requireNonNull(column);
		int currentIndex = columnList.indexOf(column);
		if (currentIndex >= 0) {
			int targetIndex = currentIndex + (down? 1 : -1);
			if (targetIndex >= 0 && targetIndex < columnList.size()) {
				ListShifter.shiftList(columnList, currentIndex, targetIndex);
			}
		}
	}

	/**
	 * Moves the given {@link IDataTableColumn} to the given index in the
	 * internal column list.
	 * <p>
	 * The given target index is clamped to the index range of the internal
	 * column list.
	 * <p>
	 * This method does nothing if the given column is not contained in the
	 * internal list.
	 *
	 * @param column
	 *            the column to move (never <i>null</i>)
	 * @param targetIndex
	 *            the index to which the column shall be moved (never
	 *            <i>null</i>)
	 */
	public void shift(IDataTableColumn<R, ?> column, int targetIndex) {

		Objects.requireNonNull(column);
		int currentIndex = columnList.indexOf(column);
		if (currentIndex >= 0) {
			targetIndex = Clamping.clamp(0, columnList.size() - 1, targetIndex);
			ListShifter.shiftList(columnList, currentIndex, targetIndex);
		}
	}

	/**
	 * Selects the given columns.
	 *
	 * @param selectedColumns
	 *            the columns to select (never <i>null</i>)
	 */
	public void select(Collection<IDataTableColumn<R, ?>> selectedColumns) {

		Objects.requireNonNull(selectedColumns);
		stateMap.values().forEach(state -> state.setSelected(false));
		selectedColumns.stream().map(stateMap::get).forEach(state -> state.setSelected(true));
	}

	/**
	 * Defines the ordering for the given column.
	 *
	 * @param column
	 *            the column (never <i>null</i>)
	 * @param orderDirection
	 *            the direction in which the given column shall be ordered (may
	 *            be <i>null</i>)
	 * @param priority
	 *            the priority for ordering by the given column (may be
	 *            <i>null</i>)
	 */
	public void setOrder(IDataTableColumn<R, ?> column, OrderDirection orderDirection, Integer priority) {

		Objects.requireNonNull(column);
		stateMap//
			.get(column)
			.setOrderDirection(orderDirection)
			.setOrderPriority(priority);
	}

	/**
	 * Returns the {@link OrderDirection} for the given column.
	 *
	 * @param column
	 *            the column (never <i>null</i>)
	 * @return the {@link OrderDirection} (may be <i>null</i>)
	 */
	public OrderDirection getOrderDirection(IDataTableColumn<R, ?> column) {

		Objects.requireNonNull(column);
		return Optional//
			.ofNullable(stateMap.get(column))
			.map(ColumnState::getOrderDirection)
			.orElse(null);
	}

	/**
	 * Returns the order priority for the given column (1-based).
	 *
	 * @param column
	 *            the column (never <i>null</i>)
	 * @return the order priority (may be <i>null</i>)
	 */
	public Integer getOrderPriority(IDataTableColumn<R, ?> column) {

		Objects.requireNonNull(column);
		return Optional//
			.ofNullable(stateMap.get(column))
			.map(ColumnState::getOrderPriority)
			.orElse(null);
	}

	public Integer getPageSize() {

		return pageSize;
	}

	public void setPageSize(Integer pageSize) {

		this.pageSize = pageSize;
	}

	private void reloadColumnList(Supplier<List<IEmfDataTableColumn<R, ?>>> columnListSupplier) {

		this.columnList.clear();
		columnListSupplier//
			.get()
			.stream()
			.filter(column -> !column.isConcealed())
			.map(IEmfDataTableColumn::getDataColumn)
			.forEach(columnList::add);
	}

	private List<OrderedColumn<R>> getOrderedColumns() {

		var indexMap = new IndexHashMap<>(getColumns());

		return stateMap
			.entrySet()
			.stream()
			.filter(entry -> entry.getValue().getOrderDirection() != null)
			.map(
				entry -> new OrderedColumn<>(//
					entry.getKey(),
					indexMap.getIndex(entry.getKey()),
					entry.getValue()))
			.sorted()
			.collect(Collectors.toList());
	}

	private ColumnState createCurrentState(IDataTableColumn<R, ?> column) {

		boolean selected = !controller.getEmfColumn(column).isHidden();
		EmfDataTableOrdering<R> ordering = controller.getOrdering();
		OrderDirection orderDirection = ordering.getOrderDirection(column).orElse(null);
		Integer orderPriority = ordering.getColumnIndex(column).map(index -> index + 1).orElse(null);
		return new ColumnState(selected, orderDirection, orderPriority);
	}

	private ColumnState createDefaultState() {

		return new ColumnState(true, null, null);
	}

	private static class ColumnState {

		private boolean selected;
		private OrderDirection orderDirection;
		private Integer orderPriority;

		public ColumnState(boolean selected, OrderDirection orderDirection, Integer orderPriority) {

			this.selected = selected;
			this.orderDirection = orderDirection;
			this.orderPriority = orderPriority;
		}

		public ColumnState setSelected(boolean selected) {

			this.selected = selected;
			return this;
		}

		public boolean isSelected() {

			return selected;
		}

		public ColumnState setOrderDirection(OrderDirection orderDirection) {

			this.orderDirection = orderDirection;
			return this;
		}

		public OrderDirection getOrderDirection() {

			return orderDirection;
		}

		public ColumnState setOrderPriority(Integer orderPriority) {

			this.orderPriority = orderPriority;
			return this;
		}

		public Integer getOrderPriority() {

			return orderPriority;
		}
	}

	private static class OrderedColumn<R> implements Comparable<OrderedColumn<R>> {

		private final IDataTableColumn<R, ?> column;
		private final int position;
		private final OrderDirection direction;
		private final Integer priority;

		public OrderedColumn(IDataTableColumn<R, ?> column, int position, ColumnState state) {

			this.column = column;
			this.position = position;
			this.direction = state.getOrderDirection();
			this.priority = Optional.ofNullable(state.getOrderPriority()).orElse(Integer.MAX_VALUE);
		}

		@Override
		public int compareTo(OrderedColumn<R> other) {

			return Comparator//
				.comparing(OrderedColumn<R>::getPriority)
				.thenComparing(OrderedColumn<R>::getPosition)
				.compare(this, other);
		}

		public IDataTableColumn<R, ?> getColumn() {

			return column;
		}

		public int getPosition() {

			return position;
		}

		public OrderDirection getDirection() {

			return direction;
		}

		public Integer getPriority() {

			return priority;
		}
	}
}
