package com.softicar.platform.emf.transaction;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.runtime.logic.IDbObject;

/**
 * Common interface of all transaction objects.
 *
 * @author Oliver Richers
 */
public interface IEmfTransactionObject<T extends IEmfTransactionObject<T>> extends IDbObject<T> {

	T setAt(DayTime at);

	DayTime getAt();

	T setByToCurrentUser();

	IBasicUser getBy();
}
