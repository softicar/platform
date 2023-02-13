package com.softicar.platform.core.module.email.composer;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.email.recipient.EmailRecipient;
import com.softicar.platform.core.module.email.recipient.type.AGEmailRecipientTypeEnum;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import jakarta.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EmailRecipientListDiv extends DomDiv {

	private final Collection<EmailRecipientInput> inputs;
	private final DomBar actionBar;
	private final Supplier<Collection<String>> recipientsSupplier;

	public EmailRecipientListDiv(Supplier<Collection<String>> recipientsSupplier) {

		this.inputs = new ArrayList<>();
		this.actionBar = appendChild(new DomBar());
		this.recipientsSupplier = recipientsSupplier;

		new DomButton()//
			.setIcon(CoreImages.ENTRY_ADD.getResource())
			.setTitle(CoreI18n.ADD_ENTRY)
			.setLabel(CoreI18n.ADD_ENTRY)
			.setClickCallback(() -> addToRecipient(new InternetAddress()))
			.appendTo(actionBar);
	}

	public EmailRecipientListDiv addToRecipient(InternetAddress address) {

		return addRecipient(AGEmailRecipientTypeEnum.TO, address);
	}

	public EmailRecipientListDiv addCcRecipient(InternetAddress address) {

		return addRecipient(AGEmailRecipientTypeEnum.CC, address);
	}

	public EmailRecipientListDiv addBccRecipient(InternetAddress address) {

		return addRecipient(AGEmailRecipientTypeEnum.BCC, address);
	}

	public EmailRecipientListDiv addRecipient(AGEmailRecipientTypeEnum recipientTypeEnum, InternetAddress address) {

		var input = new EmailRecipientInput(recipientsSupplier, recipientTypeEnum, address.toString())//
			.appendRemoveButton(this::removeRecipient);
		inputs.add(input);
		insertBefore(input, actionBar);
		return this;
	}

	public EmailRecipientListDiv addRecipient(EmailRecipient recipient) {

		var input = new EmailRecipientInput(recipientsSupplier, recipient.getRecipientType(), recipient.getAddress())//
			.appendRemoveButton(this::removeRecipient);
		inputs.add(input);
		insertBefore(input, actionBar);
		return this;
	}

	public EmailRecipientListDiv setRecipients(Collection<EmailRecipient> recipients) {

		clear();
		recipients.forEach(this::addRecipient);
		return this;
	}

	public Collection<EmailRecipient> getRecipients() {

		return inputs//
			.stream()
			.flatMap(input -> input.getRecipient().stream())
			.collect(Collectors.toList());
	}

	public void clear() {

		inputs.forEach(input -> input.disappend());
		inputs.clear();
	}

	private void removeRecipient(EmailRecipientInput input) {

		input.disappend();
		inputs.remove(input);
	}
}
