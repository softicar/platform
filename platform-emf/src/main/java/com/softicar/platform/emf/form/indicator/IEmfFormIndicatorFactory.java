package com.softicar.platform.emf.form.indicator;

import com.softicar.platform.emf.table.row.IEmfTableRow;

@FunctionalInterface
public interface IEmfFormIndicatorFactory<R extends IEmfTableRow<R, ?>> {

	IEmfFormIndicator createIndicator(R tableRow);
}
