package com.softicar.platform.core.module.email.composer;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.recipient.type.AGEmailRecipientTypeEnum;
import com.softicar.platform.dom.elements.button.DomButton;

public class EmailRecipientTypeButton extends DomButton {

	private AGEmailRecipientTypeEnum recipientTypeEnum;

	public EmailRecipientTypeButton(AGEmailRecipientTypeEnum recipientTypeEnum) {

		this.recipientTypeEnum = null;

		setTitle(CoreI18n.CLICK_TO_CHANGE);
		setRecipientTypeEnum(recipientTypeEnum);
		setClickCallback(this::setNextType);
	}

	public EmailRecipientTypeButton setRecipientTypeEnum(AGEmailRecipientTypeEnum recipientTypeEnum) {

		this.recipientTypeEnum = recipientTypeEnum;
		setLabel(recipientTypeEnum.toDisplay());
		return this;
	}

	public AGEmailRecipientTypeEnum getRecipientTypeEnum() {

		return recipientTypeEnum;
	}

	private void setNextType() {

		setRecipientTypeEnum(getNextType());
	}

	private AGEmailRecipientTypeEnum getNextType() {

		return switch (recipientTypeEnum) {
		case TO -> AGEmailRecipientTypeEnum.CC;
		case CC -> AGEmailRecipientTypeEnum.BCC;
		case BCC -> AGEmailRecipientTypeEnum.TO;
		};
	}
}
