package com.softicar.platform.emf.delete;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfDeleteStrategy<R extends IEmfTableRow<R, ?>> implements IEmfDeleteStrategy<R> {

	private boolean deleteFromTable;
	private IDbField<R, Boolean> deletedFlag;

	public EmfDeleteStrategy() {

		this.deleteFromTable = false;
	}

	@Override
	public boolean isDeletable(R tableRow) {

		return deleteFromTable || deletedFlag != null;
	}

	@Override
	public void delete(R tableRow) {

		if (deleteFromTable) {
			tableRow.delete();
		} else {
			deletedFlag.setValue(tableRow, true);
			tableRow.save();
		}
	}

	protected void setDeleteFromTable() {

		this.deleteFromTable = true;
	}

	protected void setDeletedFlag(IDbField<R, Boolean> deletedFlag) {

		this.deletedFlag = deletedFlag;
	}

	protected void validate() {

		if (deleteFromTable && deletedFlag != null) {
			throw new SofticarDeveloperException("Cannot set both, deleted flag and delete from table.");
		}
	}
}
