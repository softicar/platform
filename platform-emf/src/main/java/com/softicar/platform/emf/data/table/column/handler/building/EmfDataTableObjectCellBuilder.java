package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import java.util.Objects;

public class EmfDataTableObjectCellBuilder<T> implements IEmfDataTableCellBuilder<T> {

	@Override
	public void buildCell(IEmfDataTableCell<?, T> cell, T value) {

		String text = Objects.toString(value, "");
		cell.appendText(text);
	}
}
