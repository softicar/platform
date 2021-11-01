package com.softicar.platform.emf.action;

import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public abstract class AbstractEmfSaveAction<R extends IEmfTableRow<R, ?>> extends AbstractEmfButtonAction<R> {

	@Override
	public void handleClick(IEmfFormBody<R> formBody) {

		try (DbTransaction transaction = new DbTransaction()) {
			R tableRow = formBody.getTableRow();
			if (tableRow.isFresh()) {
				execute(tableRow);
				formBody.queueEntityForRefresh();
			} else {
				formBody.showInteractiveRefreshSectionContainer();
			}
			transaction.commit();
		}
	}

	protected abstract void execute(R tableRow);
}
