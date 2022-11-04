package com.softicar.platform.core.module.transaction.data;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.stream.Collectors;

public class TransactionDataDeletionPopup extends DomPopup {

	private final TransactionDataDeleter deleter;

	public TransactionDataDeletionPopup() {

		this.configuration.setDisplayModeDialog();
		this.deleter = new TransactionDataDeleter();

		appendChild(new DomMessageDiv(DomMessageType.WARNING, CoreI18n.THE_CONTENT_OF_THE_FOLLOWING_TABLES_WILL_BE_DELETED));
		appendChild(getTextAreaWithStatements());

		appendActionNode(
			new DomButton()
				.setLabel(CoreI18n.DELETE_ALL_TRANSACTION_DATA)
				.setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION)
				.setClickCallback(this::deleteAll));
		appendCancelButton();
	}

	private void deleteAll() {

		deleter.execute();
		close();
		executeAlert(CoreI18n.ALL_TRANSACTION_DATA_WAS_DELETED);
	}

	private DomTextArea getTextAreaWithStatements() {

		var textArea = new DomTextArea();
		textArea.setReadonly(true);
		textArea.setValue(getStatements());
		textArea.setSize(25, 80);
		return textArea;
	}

	private String getStatements() {

		return deleter//
			.getTableList()
			.stream()
			.map(table -> "DELETE FROM %s;".formatted(table.getFullName()))
			.collect(Collectors.joining("\r\n"));
	}
}
