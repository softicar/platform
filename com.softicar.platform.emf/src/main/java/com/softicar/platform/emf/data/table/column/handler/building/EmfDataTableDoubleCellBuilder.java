package com.softicar.platform.emf.data.table.column.handler.building;

import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

class EmfDataTableDoubleCellBuilder implements IEmfDataTableCellBuilder<Double> {

	@Override
	public void buildCell(IEmfDataTableCell<?, Double> cell, Double value) {

		if (value != null) {
			cell.appendText(formatFloatingPoint(value));
		}
	}

	public static String formatFloatingPoint(double value) {

		NumberFormat format = NumberFormat.getInstance(Locale.US);
		format.setMaximumFractionDigits(4);
		format.setMinimumFractionDigits(0);
		format.setGroupingUsed(false);
		format.setRoundingMode(RoundingMode.HALF_UP);
		return format.format(value);
	}
}
