package com.softicar.platform.emf.form.indicator;

import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;

public interface IEmfFormIndicatorConfiguration<R extends IEmfTableRow<R, ?>> {

	Collection<IEmfFormIndicatorFactory<R>> getIndicatorFactories(EmfFormIndicatorAlignment alignment);
}
