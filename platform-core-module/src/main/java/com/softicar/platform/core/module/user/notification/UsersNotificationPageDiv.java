package com.softicar.platform.core.module.user.notification;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.buffer.BufferedEmail;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.notification.INotificationTargetUsersQuery.IRow;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsersNotificationPageDiv extends DomDiv {

	private static final int USER_BURST_SIZE = 100;
	private final DomTextInput fromInput;
	private final DomTextInput subjectInput;
	private final DomTextArea contentInput;
	private final IEmfDataTableDiv<IRow> targetUsersTable;

	public UsersNotificationPageDiv() {

		appendChild(new DomMessageDiv(DomMessageType.INFO, CoreI18n.SENDS_AN_EMAIL_WITH_THE_SPECIFIED_CONTENT_TO_ALL_SELECTED_USERS));
		appendChild(
			new DomLabelGrid()//
				.add(CoreI18n.FROM, fromInput = new DomTextInput(AGCoreModuleInstance.getInstance().getNoReplyEmailAddress()))
				.add(CoreI18n.SUBJECT, subjectInput = new DomTextInput())
				.add(CoreI18n.CONTENT, contentInput = new DomTextArea().setRowCount(5)));
		appendChild(new DomMessageDiv(DomMessageType.WARNING, CoreI18n.PLEASE_DOUBLE_CHECK_ALL_INPUTS_BEFORE_CLICKING_ARG1.toDisplay(CoreI18n.SEND)));
		appendChild(
			new DomActionBar(
				new DomButton()//
					.setClickCallback(this::send)
					.setConfirmationMessageSupplier(this::getConfirmationMessage)
					.setIcon(CoreImages.NOTIFICATION_SEND.getResource())
					.setLabel(CoreI18n.SEND)));

		INotificationTargetUsersQuery query = INotificationTargetUsersQuery.FACTORY//
			.createQuery()
			.setActive(true)
			.setSystemUser(false);
		targetUsersTable = new EmfDataTableDivBuilder<>(query)//
			.setConcealed(INotificationTargetUsersQuery.USER_COLUMN, true)
			.setRowSelectionModeMulti()
			.build();
		appendChild(targetUsersTable);
	}

	private void send() {

		validate();

		List<AGUser> users = targetUsersTable//
			.getSelectedRows()
			.stream()
			.map(row -> row.getUser())
			.collect(Collectors.toList());

		try (DbTransaction transaction = new DbTransaction()) {
			Collection<List<AGUser>> userSplices = splice(users, USER_BURST_SIZE);
			for (List<AGUser> userSplice: userSplices) {
				BufferedEmail email = new BufferedEmail();
				email.setFrom(fromInput.getInputTextTrimmed());
				email.setSubject(IDisplayString.create(subjectInput.getInputTextTrimmed()));
				email.setContent(contentInput.getInputTextTrimmed(), EmailContentType.PLAIN);
				for (AGUser user: userSplice) {
					email.addBccRecipient(user.getEmailAddress());
				}
				email.submit();
			}
			transaction.commit();
		}
		executeAlert(CoreI18n.NOTIFICATION_WAS_SENT_TO_ARG1_USERS.toDisplay(users.size()));
	}

	// TODO extract this as ListSplicer and implement a test
	private static <T> Collection<List<T>> splice(List<T> list, int spliceSize) {

		if (spliceSize < 1) {
			throw new SofticarDeveloperException("Splice size must be at least 1.");
		}
		int i = 0;
		Collection<List<T>> splices = new ArrayList<>();
		while (i < list.size()) {
			int remaining = list.size() - i;
			int nextSize = Math.min(remaining, spliceSize);
			splices.add(list.subList(i, i + nextSize));
			i += nextSize;
		}
		return splices;
	}

	private IDisplayString getConfirmationMessage() {

		validate();
		return CoreI18n.ARE_YOU_SURE_QUESTION;
	}

	private void validate() {

		if (targetUsersTable.getSelectedRows().size() == 0) {
			throw new SofticarUserException(CoreI18n.PLEASE_SELECT_RECIPIENTS);
		}
		if (fromInput.isBlank()) {
			throw new SofticarUserException(CoreI18n.PLEASE_ENTER_A_SENDER_ADDRESS);
		}
		if (subjectInput.isBlank()) {
			throw new SofticarUserException(CoreI18n.PLEASE_ENTER_A_SUBJECT);
		}
		if (contentInput.isBlank()) {
			throw new SofticarUserException(CoreI18n.PLEASE_ENTER_SOME_CONTENT);
		}
	}
}
