package com.softicar.platform.dom.elements.input.auto.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.transaction.ITransaction;
import java.util.Collection;
import java.util.Collections;

public class DomAutoCompleteEntityDummyInputEngine<T extends IEntity> extends AbstractDomAutoCompleteTransactionalEntityInputEngine<T> {

	@Override
	public T getById(Integer id) {

		return null;
	}

	@Override
	public Collection<T> findMatchingItems(String pattern, int fetchOffset, int fetchSize) {

		return Collections.emptyList();
	}

	@Override
	protected ITransaction createTransaction() {

		return ITransaction.noOperation();
	}
}
