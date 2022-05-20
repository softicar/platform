package com.softicar.platform.emf.data.table.configuration;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomEnumSelect;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelect;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.IEmfDataTable;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableNonSortableNonFilterableColumnHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class EmfDataTableConfigurationTableDiv<R> extends DomDiv {

	private final EmfDataTableConfigurationModel<R> model;
	private final IEmfDataTableDiv<EmfDataTableConfigurationTableRow<R>> tableDiv;

	public EmfDataTableConfigurationTableDiv(EmfDataTableConfigurationModel<R> model) {

		this.model = model;

		var selectedColumns = model.getSelectedColumns();

		var table = new EmfDataTableConfigurationTable<>(this::getConfigurationRows);
		var tableDiv = new EmfDataTableDivBuilder<>(table)//
			.setColumnHandler(table.getPositionColumn(), new PositionHandler())
			.setColumnHandler(table.getTitleColumn(), new TitleHandler())
			.setColumnHandler(table.getOrderingColumn(), new OrderHandler())
			.setRowSelectionModeMulti()
			.setHideNavigation(true)
			.setPageSize(model.getColumns().size())
			.setRowSelectionCallback(this::handleRowSelection)
			.addTableMarker(EmfDataTableConfigurationMarker.TABLE)
			.addTableDivMarker(EmfDataTableConfigurationMarker.TABLE_DIV)
			.build();
		this.tableDiv = appendChild(tableDiv);

		selectColumns(selectedColumns);
	}

	public void refreshTable() {

		var selectedColumns = model.getSelectedColumns();
		tableDiv.refresh();
		selectColumns(selectedColumns);
	}

	private void selectColumns(Collection<IDataTableColumn<R, ?>> columns) {

		this.tableDiv//
			.getDisplayedTableRows()
			.stream()
			// This is inefficient because the row-selection callback will fire for each selected row,
			// while in fact we only need it to fire once (after all desired rows were selected).
			// To improve this, we'd need to implement burst-selection of rows.
			// Yet, this probably does not matter in practice, since we won't configure tables with thousands of columns, or so.
			.forEach(row -> row.setSelected(columns.contains(row.getDataRow().getColumn()), true));
	}

	private ArrayList<EmfDataTableConfigurationTableRow<R>> getConfigurationRows() {

		var rows = new ArrayList<EmfDataTableConfigurationTableRow<R>>();
		for (int index = 0; index < model.getColumns().size(); index++) {
			var column = model.getColumns().get(index);
			rows.add(new EmfDataTableConfigurationTableRow<>(column, index));
		}
		return rows;
	}

	private void handleRowSelection(IEmfDataTable<EmfDataTableConfigurationTableRow<R>> dataTable) {

		List<IDataTableColumn<R, ?>> selectedColumns = dataTable//
			.getController()
			.getSelectedRows()
			.stream()
			.map(EmfDataTableConfigurationTableRow::getColumn)
			.collect(Collectors.toList());
		model.select(selectedColumns);
	}

	private class PositionHandler extends EmfDataTableNonSortableNonFilterableColumnHandler<EmfDataTableConfigurationTableRow<R>, Object> {

		@Override
		public void buildCell(IEmfDataTableCell<EmfDataTableConfigurationTableRow<R>, Object> cell, EmfDataTableConfigurationTableRow<R> row) {

			IDataTableColumn<R, ?> column = row.getColumn();
			DomDiv content = new DomDiv();
			content.addCssClass(EmfCssClasses.EMF_DATA_TABLE_CONFIGURATION_COLUMN_POSITION_INPUT);
			content.appendChild(new ColumnPositionSelect(column, row.getIndex() + 1));
			content.appendChild(new MoveButton(column, true));
			content.appendChild(new MoveButton(column, false));
			cell.appendChild(content);
		}

		private class ColumnPositionSelect extends DomSimpleValueSelect<Integer> {

			private final IDataTableColumn<R, ?> column;

			public ColumnPositionSelect(IDataTableColumn<R, ?> column, int position) {

				super(builder -> builder.setNilOptionAvailable(false).setValueComparator(Integer::compareTo));
				this.column = column;
				setValues(IntStream.rangeClosed(1, model.getColumns().size()).boxed().collect(Collectors.toList()));
				selectValue(position);
				setCallbackOnChange(this::handleChange);
				addMarker(EmfDataTableConfigurationMarker.POSITION_SELECT);
			}

			private void handleChange() {

				Integer targetPosition = getSelectedValue().orElseThrow();
				model.shift(column, targetPosition - 1);
				refreshTable();
			}
		}

		private class MoveButton extends DomButton {

			private final IDataTableColumn<R, ?> column;
			private final boolean down;

			public MoveButton(IDataTableColumn<R, ?> column, boolean down) {

				this.column = column;
				this.down = down;

				setClickCallback(this::handleClick);

				if (down) {
					setIcon(DomElementsImages.MOVE_DOWN.getResource());
					setTitle(EmfDataTableI18n.MOVE_DOWN);
					addMarker(EmfDataTableConfigurationMarker.MOVE_DOWN_BUTTON);
				} else {
					setIcon(DomElementsImages.MOVE_UP.getResource());
					setTitle(EmfDataTableI18n.MOVE_UP);
					addMarker(EmfDataTableConfigurationMarker.MOVE_UP_BUTTON);
				}
			}

			private void handleClick() {

				model.move(column, down);
				refreshTable();
			}
		}
	}

	private class TitleHandler extends EmfDataTableNonSortableNonFilterableColumnHandler<EmfDataTableConfigurationTableRow<R>, Object> {

		@Override
		public void buildCell(IEmfDataTableCell<EmfDataTableConfigurationTableRow<R>, Object> cell, EmfDataTableConfigurationTableRow<R> row) {

			cell.addMarker(EmfDataTableConfigurationMarker.TITLE_CELL);
			cell.appendText(row.getColumn().getTitle());
		}
	}

	private class OrderHandler extends EmfDataTableNonSortableNonFilterableColumnHandler<EmfDataTableConfigurationTableRow<R>, Object> {

		@Override
		public void buildCell(IEmfDataTableCell<EmfDataTableConfigurationTableRow<R>, Object> cell, EmfDataTableConfigurationTableRow<R> row) {

			IDataTableColumn<R, ?> column = row.getColumn();
			var orderInput = new ColumnOrderInput(column);
			orderInput.setDirection(model.getOrderDirection(column));
			orderInput.setPriority(model.getOrderPriority(column));
			cell.appendChild(orderInput);
		}

		private class ColumnOrderInput extends DomDiv {

			private final IDataTableColumn<R, ?> column;
			private final OrderDirectionSelect directionSelect;
			private final OrderPrioritySelect prioritySelect;

			public ColumnOrderInput(IDataTableColumn<R, ?> column) {

				this.column = column;
				this.directionSelect = new OrderDirectionSelect();
				this.prioritySelect = new OrderPrioritySelect();

				addCssClass(EmfCssClasses.EMF_DATA_TABLE_CONFIGURATION_COLUMN_ORDER_INPUT);
				appendChild(directionSelect);
				appendChild(prioritySelect);
			}

			public void setDirection(OrderDirection direction) {

				this.directionSelect.setSelectedValue(direction);
			}

			public void setPriority(Integer priority) {

				prioritySelect.selectValue(priority);
			}

			private Optional<OrderDirection> getDirection() {

				return Optional.ofNullable(directionSelect.getSelectedValue());
			}

			private Integer getPriority() {

				return prioritySelect.getSelectedValue().orElse(null);
			}

			private void updateModel() {

				model.setOrder(column, getDirection().orElse(null), getPriority());
			}

			private class OrderDirectionSelect extends DomEnumSelect<OrderDirection> implements IDomEventHandler {

				public OrderDirectionSelect() {

					addNilValue(IDisplayString.create("-"));
					addValue(OrderDirection.ASCENDING);
					addValue(OrderDirection.DESCENDING);
					listenToEvent(DomEventType.CHANGE);
					addMarker(EmfDataTableConfigurationMarker.ORDER_DIRECTION_SELECT);
				}

				@Override
				public void handleDOMEvent(IDomEvent event) {

					updateModel();
				}

				@Override
				protected IDisplayString getValueDisplayString(OrderDirection value) {

					return value.toDisplay();
				}
			}

			private class OrderPrioritySelect extends DomSimpleValueSelect<Integer> {

				public OrderPrioritySelect() {

					super(
						builder -> builder//
							.setNilOptionDisplayString(IDisplayString.create("-"))
							.setValueComparator(Integer::compareTo));

					setValues(IntStream.rangeClosed(1, model.getColumns().size()).boxed().collect(Collectors.toList()));
					setCallbackOnChange(this::handleChange);
					addMarker(EmfDataTableConfigurationMarker.ORDER_PRIORITY_SELECT);
				}

				private void handleChange() {

					updateModel();
				}
			}
		}
	}
}
