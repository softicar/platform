package com.softicar.platform.core.module.tranactional.data;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.popup.DomPopup;

public class TransactionalDataDeletionPopup extends DomPopup {

	private final TransactionalDataDeleter deleter;

	public TransactionalDataDeletionPopup() {

		this.configuration.setDisplayModeDialog();
		this.deleter = new TransactionalDataDeleter();

		appendChild(new DomMessageDiv(DomMessageType.WARNING, CoreI18n.THE_CONTENT_OF_THE_FOLLOWING_TABLES_WILL_BE_DELETED));

		deleter.getTableList().forEach(table -> {
			appendText(table.getFullName().toString());
			appendNewChild(DomElementTag.BR);
		});

		appendActionNode(
			new DomButton()
				.setLabel(CoreI18n.DELETE_ALL_TRANSACTIONAL_DATA)
				.setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION)
				.setClickCallback(this::deleteAll));
		appendCancelButton();
	}

	private void deleteAll() {

		deleter.execute();
		close();
		executeAlert(CoreI18n.ALL_TRANSACTIONAL_DATA_WAS_DELETED);
	}
}
