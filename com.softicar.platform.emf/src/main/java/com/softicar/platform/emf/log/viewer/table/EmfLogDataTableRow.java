package com.softicar.platform.emf.log.viewer.table;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.emf.log.EmfLogRecordKey;
import com.softicar.platform.emf.log.viewer.item.EmfLogItem;
import com.softicar.platform.emf.table.row.IEmfTableRow;

class EmfLogDataTableRow<R extends IEmfTableRow<R, ?>> {

	private final EmfLogRecordKey key;
	private final EmfLogItem<R> logItem;

	public EmfLogDataTableRow(EmfLogItem<R> logItem) {

		this.key = new EmfLogRecordKey(logItem.getTransactionObject());
		this.logItem = logItem;
	}

	public DayTime getAt() {

		return key.getAt();
	}

	public IBasicUser getBy() {

		return key.getBy();
	}

	public R getImpermanentEntity() {

		return logItem.getImpermanentTableRow();
	}
}
