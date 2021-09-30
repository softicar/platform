package com.softicar.platform.emf.action;

import com.softicar.platform.emf.form.section.IEmfFormSectionDiv;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfPrimaryAction<R extends IEmfTableRow<R, ?>> extends IEmfAction<R> {

	/**
	 * Integrates this action into the given action container.
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} (never null)
	 * @param actionContainer
	 *            the action container (never null)
	 */
	void integrate(R tableRow, IEmfFormSectionDiv<R> actionContainer);
}
