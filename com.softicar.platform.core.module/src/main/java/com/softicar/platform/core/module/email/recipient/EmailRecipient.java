package com.softicar.platform.core.module.email.recipient;

import com.softicar.platform.core.module.email.recipient.type.AGEmailRecipientTypeEnum;

public class EmailRecipient {

	private final String address;
	private final AGEmailRecipientTypeEnum recipientType;

	public EmailRecipient(String address, AGEmailRecipientTypeEnum recipientType) {

		this.address = address;
		this.recipientType = recipientType;
	}

	public String getAddress() {

		return address;
	}

	public AGEmailRecipientTypeEnum getRecipientType() {

		return recipientType;
	}
}
