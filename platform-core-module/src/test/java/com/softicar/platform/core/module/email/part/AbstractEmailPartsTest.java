package com.softicar.platform.core.module.email.part;

import com.softicar.platform.common.testing.Asserts;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

public abstract class AbstractEmailPartsTest extends Asserts {

	protected MimeBodyPart createMimePart(String type, String content) throws MessagingException {

		var part1 = new MimeBodyPart();
		part1.setText(content);
		part1.setHeader("Content-Type", type);
		return part1;
	}

	protected MimeMessage createMimeMessage(Multipart multipart) throws MessagingException {

		var message = new MimeMessage((Session) null);
		message.setContent(multipart);
		message.setHeader("Content-Type", multipart.getContentType());
		return message;
	}
}
