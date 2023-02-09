package com.softicar.platform.core.module.email.composer;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.email.recipient.EmailRecipient;
import com.softicar.platform.core.module.email.recipient.type.AGEmailRecipientTypeEnum;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInputValidationMode;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EmailRecipientInput extends DomBar {

	private final EmailRecipientTypeButton recipientTypeButton;
	private final DomAutoCompleteInput<String> recipientInput;

	public EmailRecipientInput(Supplier<Collection<String>> recipientsSupplier) {

		this(recipientsSupplier, AGEmailRecipientTypeEnum.TO, "");
	}

	public EmailRecipientInput(Supplier<Collection<String>> recipientsSupplier, AGEmailRecipientTypeEnum recipientTypeEnum, String recipient) {

		this.recipientTypeButton = new EmailRecipientTypeButton(recipientTypeEnum);
		this.recipientInput = new DomAutoCompleteInput<>(//
			recipientsSupplier,
			DomAutoCompleteInputValidationMode.PERMISSIVE);

		recipientTypeButton.appendTo(this);
		recipientInput.setValue(recipient);
		recipientInput.appendTo(this);
	}

	public Optional<EmailRecipient> getRecipient() {

		var recipientTypeEnum = recipientTypeButton.getRecipientTypeEnum();
		var address = recipientInput.getValueText().trim();
		if (!address.isBlank()) {
			return Optional.of(new EmailRecipient(address, recipientTypeEnum));
		} else {
			return Optional.empty();
		}
	}

	public EmailRecipientInput appendRemoveButton(Consumer<EmailRecipientInput> removeCallback) {

		new DomButton()//
			.setIcon(CoreImages.ENTRY_REMOVE.getResource())
			.setTitle(CoreI18n.REMOVE_ENTRY)
			.setClickCallback(() -> removeCallback.accept(this))
			.appendTo(this);
		return this;
	}
}
