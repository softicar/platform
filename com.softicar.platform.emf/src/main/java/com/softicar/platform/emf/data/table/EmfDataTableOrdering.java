package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class EmfDataTableOrdering<R> {

	private final List<OrderingEntry<R>> entries;
	private final Map<IDataTableColumn<R, ?>, OrderDirection> directions;
	private final Map<IDataTableColumn<R, ?>, Integer> indexes;

	public EmfDataTableOrdering() {

		this.entries = new ArrayList<>();
		this.directions = new HashMap<>();
		this.indexes = new HashMap<>();
	}

	public EmfDataTableOrdering<R> clear() {

		entries.clear();
		directions.clear();
		indexes.clear();
		return this;
	}

	public EmfDataTableOrdering<R> add(IDataTableColumn<R, ?> column, OrderDirection direction) {

		Objects.requireNonNull(column);
		Objects.requireNonNull(direction);

		if (directions.containsKey(column)) {
			remove(column);
		}

		entries.add(new OrderingEntry<>(column, direction));
		directions.put(column, direction);
		indexes.put(column, indexes.size());

		return this;
	}

	public EmfDataTableOrdering<R> remove(IDataTableColumn<R, ?> column) {

		// remove from entries
		Iterator<OrderingEntry<R>> iterator = entries.iterator();
		while (iterator.hasNext()) {
			OrderingEntry<R> entry = iterator.next();
			if (entry.getColumn() == column) {
				iterator.remove();
				break;
			}
		}

		// rebuild directions and indexes
		directions.clear();
		indexes.clear();
		for (OrderingEntry<R> entry: entries) {
			directions.put(entry.getColumn(), entry.getDirection());
			indexes.put(entry.getColumn(), indexes.size());
		}

		return this;
	}

	public Optional<OrderDirection> getOrderDirection(IDataTableColumn<R, ?> column) {

		return Optional.ofNullable(directions.get(column));
	}

	public Optional<Integer> getColumnIndex(IDataTableColumn<R, ?> column) {

		return Optional.ofNullable(indexes.get(column));
	}

	public int getColumnCount() {

		return directions.size();
	}

	public Collection<OrderingEntry<R>> getEntries() {

		return entries;
	}

	public static class OrderingEntry<R> {

		private final IDataTableColumn<R, ?> column;
		private final OrderDirection direction;

		public OrderingEntry(IDataTableColumn<R, ?> column, OrderDirection direction) {

			this.column = column;
			this.direction = direction;
		}

		public IDataTableColumn<R, ?> getColumn() {

			return column;
		}

		public OrderDirection getDirection() {

			return direction;
		}
	}
}
