package com.softicar.platform.emf.transaction;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.emf.EmfI18n;
import java.util.Optional;

public class EmfTransactionObjectInfoDiv extends DomDiv {

	public EmfTransactionObjectInfoDiv(IEmfTransactionObject<?> transactionObject) {

		appendText(getTimeString(transactionObject) + getUserNameString(transactionObject));
		setTitle(EmfI18n.TRANSACTION.concat(": " + transactionObject.getId()));
	}

	private String getTimeString(IEmfTransactionObject<?> transactionObject) {

		return transactionObject.getAt().toString();
	}

	private String getUserNameString(IEmfTransactionObject<?> transactionObject) {

		return Optional//
			.ofNullable(transactionObject.getBy())
			.map(it -> " - " + it.toDisplay())
			.orElse("");
	}
}
