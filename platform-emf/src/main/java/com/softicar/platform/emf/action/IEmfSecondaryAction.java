package com.softicar.platform.emf.action;

import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;

/**
 * An {@link IEmfAction} that is both an {@link IEmfCommonAction} and an
 * {@link IEmfManagementAction}. It is opposed to an {@link IEmfPrimaryAction}.
 * <p>
 * TODO try to merge {@link IEmfCommonAction} and {@link IEmfManagementAction}
 *
 * @author Alexander Schmidt
 */
public interface IEmfSecondaryAction<R extends IEmfTableRow<R, ?>> extends IEmfCommonAction<R>, IEmfManagementAction<R> {

	@Override
	default void handleClick(IEmfFormBody<R> formBody) {

		handleClick(formBody.getTableRow());
	}
}
