package com.softicar.platform.emf.form.factory;

import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.IEmfFormFrame;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * This factory is used to define custom implementations of {@link IEmfForm}
 * that can be used in an {@link IEmfTable}.
 *
 * @author Daniel Klose
 */
@FunctionalInterface
public interface IEmfFormFactory<R extends IEmfTableRow<R, ?>> {

	IEmfForm<R> createForm(IEmfFormFrame<R> frame, R tableRow);
}
