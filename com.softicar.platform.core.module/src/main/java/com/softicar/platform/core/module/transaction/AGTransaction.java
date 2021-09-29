package com.softicar.platform.core.module.transaction;

import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.emf.attribute.field.transaction.EmfTransactionRecordManager;
import com.softicar.platform.emf.transaction.IEmfTransactionObject;

public class AGTransaction extends AGTransactionGenerated implements IEmfTransactionObject<AGTransaction> {

	public static AGTransaction getOrInsert() {

		return new EmfTransactionRecordManager<>(AGTransaction.TABLE).getOrInsertRecord();
	}

	@Override
	public AGTransaction setByToCurrentUser() {

		return setBy(CurrentUser.get());
	}

	public AGTransaction min(AGTransaction other) {

		return getAt().isBeforeOrEqual(other.getAt())? this : other;
	}
}
