package com.softicar.platform.core.module.email.composer;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.buffer.BufferedEmail;
import com.softicar.platform.core.module.test.SofticarTestDatabase;
import com.softicar.platform.core.module.tinymce.TinyMceInput;
import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.emf.attribute.field.string.EmfStringInput;
import jakarta.mail.internet.InternetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class EmailComposerDiv extends DomDiv {

	private final EmfStringInput fromAddress;
	private final EmailRecipientListDiv recipientsDiv;
	private final EmfStringInput subject;
	private final EmailAttachmentListDiv attachmentsDiv;
	private final TinyMceInput content;
	private Supplier<Collection<String>> recipientsSupplier;

	public EmailComposerDiv() {

		DbCurrentDatabase.set(new SofticarTestDatabase());

		this.fromAddress = new EmfStringInput();
		this.recipientsDiv = new EmailRecipientListDiv(this::getKnownRecipients);
		this.subject = new EmfStringInput();
		this.attachmentsDiv = new EmailAttachmentListDiv();
		this.content = new TinyMceInput();
		this.recipientsSupplier = () -> Collections.emptyList();

		new DomLabelGrid()//
			.add(CoreI18n.FROM, fromAddress)
			.add(CoreI18n.RECIPIENTS, recipientsDiv)
			.add(CoreI18n.SUBJECT, subject)
			.add(CoreI18n.ATTACHMENTS, attachmentsDiv)
			.appendTo(this);
		appendChild(content);

		var email = new BufferedEmail();
		email.setFrom("oliver.richers@gmail.com");
		email.addToRecipient("oliver.richers@gmail.com");
		email.setSubject(IDisplayString.create("Eine Beispiel-E-Mail"));
		email.setContent("<p>Das ist ein Test.</p>", EmailContentType.HTML);
		load(email);
//		load(new BufferedEmail());
	}

	public EmailComposerDiv setRecipientsSupplier(Supplier<Collection<String>> recipientsSupplier) {

		this.recipientsSupplier = recipientsSupplier;
		return this;
	}

	public void load(BufferedEmail email) {

		fromAddress.setValue(email.getFrom().map(InternetAddress::toString).orElse(""));
		recipientsDiv.setRecipients(email.getRecipients());
		subject.setValue(email.getSubject());
		content.setValue(email.getContent());
		attachmentsDiv.setAttachments(email.getAttachments());
	}

	public BufferedEmail save() {

		return saveTo(new BufferedEmail());
	}

	public BufferedEmail saveTo(BufferedEmail email) {

		email.setFrom(fromAddress.getValueTextTrimmed());
		email.setRecipients(recipientsDiv.getRecipients());
		email.setSubject(IDisplayString.create(subject.getValueText()));
		email.setContent(content.getValueOrThrow(), EmailContentType.HTML);
		email.setAttachments(attachmentsDiv.getAttachments());
		return email;
	}

	private Collection<String> getKnownRecipients() {

		return recipientsSupplier.get();
	}
}
