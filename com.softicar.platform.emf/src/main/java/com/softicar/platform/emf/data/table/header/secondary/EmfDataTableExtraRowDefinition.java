package com.softicar.platform.emf.data.table.header.secondary;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableRowProvider;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class EmfDataTableExtraRowDefinition<R> {

	private final EmfDataTableDivBuilder<R> divBuilder;
	private final IEmfDataTableExtraRowColumnGroupList<R> columnGroupList;
	private Optional<CssTextAlign> textAlign;

	public EmfDataTableExtraRowDefinition(EmfDataTableDivBuilder<R> divBuilder, IEmfDataTableExtraRowColumnGroupList<R> columnGroupList) {

		this.divBuilder = divBuilder;
		this.columnGroupList = columnGroupList;
		this.textAlign = Optional.empty();
	}

	public ColumnSetter addEmptyCell() {

		return addCell((IDisplayString) null);
	}

	public ColumnSetter addCell(IDisplayString label) {

		return addCell(new DisplayStringCellBuilder(label));
	}

	public ColumnSetter addCell(IEmfDataTableExtraRowCellBuilder<R> cellBuilder) {

		return new ColumnSetter(cellBuilder);
	}

	public EmfDataTableExtraRowDefinition<R> setTextAlign(CssTextAlign cssTextAlign) {

		this.textAlign = Optional.of(cssTextAlign);
		return this;
	}

	public EmfDataTableDivBuilder<R> endRow() {

		return divBuilder;
	}

	public class ColumnSetter {

		private final IEmfDataTableExtraRowCellBuilder<R> cellBuilder;

		private ColumnSetter(IEmfDataTableExtraRowCellBuilder<R> cellBuilder) {

			this.cellBuilder = new StyledCellBuilder(cellBuilder);
		}

		public EmfDataTableExtraRowDefinition<R> setColumns(IDataTableColumn<?, ?>...columns) {

			return setColumns(Arrays.asList(columns));
		}

		public EmfDataTableExtraRowDefinition<R> setColumns(Collection<? extends IDataTableColumn<?, ?>> columns) {

			columnGroupList.add(new EmfDataTableExtraRowColumnGroup<>(cellBuilder, columns));
			return EmfDataTableExtraRowDefinition.this;
		}
	}

	private class DisplayStringCellBuilder implements IEmfDataTableExtraRowCellBuilder<R> {

		private final IDisplayString label;

		public DisplayStringCellBuilder(IDisplayString label) {

			this.label = label;
		}

		@Override
		public void buildCell(IDomParentElement cell, IEmfDataTableRowProvider<R> rowProvider) {

			if (label != null) {
				cell.appendText(label);
			}
		}
	}

	private class StyledCellBuilder implements IEmfDataTableExtraRowCellBuilder<R> {

		private final IEmfDataTableExtraRowCellBuilder<R> other;

		public StyledCellBuilder(IEmfDataTableExtraRowCellBuilder<R> other) {

			this.other = Objects.requireNonNull(other);
		}

		@Override
		public void buildCell(IDomParentElement cell, IEmfDataTableRowProvider<R> rowProvider) {

			textAlign.ifPresent(cell::setStyle);
			other.buildCell(cell, rowProvider);
		}
	}
}
