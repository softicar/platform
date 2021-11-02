package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.common.core.transaction.ITransaction;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.input.auto.entity.AbstractDomAutoCompleteTransactionalEntityInputEngine;
import com.softicar.platform.emf.entity.IEmfEntity;

public abstract class AbstractEmfEntityInputEngine<T extends IEmfEntity<T, ?>> extends AbstractDomAutoCompleteTransactionalEntityInputEngine<T> {

	@Override
	protected ITransaction createTransaction() {

		return new DbTransaction();
	}
}
