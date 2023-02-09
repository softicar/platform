package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.file.FileInputStreamFactory;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.common.string.unicode.UtfNonBmpCharacterReplacer;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.IEmail;
import com.softicar.platform.core.module.email.buffer.attachment.AGBufferedEmailAttachment;
import com.softicar.platform.core.module.email.buffer.attachment.BufferedEmailAttachment;
import com.softicar.platform.core.module.email.message.EmailMessageId;
import com.softicar.platform.core.module.email.recipient.EmailRecipient;
import com.softicar.platform.core.module.email.recipient.type.AGEmailRecipientTypeEnum;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BufferedEmail implements IEmail {

	protected AGServer emailServer;
	protected InternetAddress from;
	protected InternetAddress sender;
	protected InternetAddress replyTo;
	protected final List<InternetAddress> toAddresses;
	protected final List<InternetAddress> ccAddresses;
	protected final List<InternetAddress> bccAddresses;
	protected EmailMessageId messageId;
	protected EmailMessageId inReplyTo;
	protected final Collection<EmailMessageId> references;
	protected String subject;
	protected String content;
	protected EmailContentType contentType;
	protected String autoSubmitted;
	protected final List<BufferedEmailAttachment> attachments;
	protected final UtfNonBmpCharacterReplacer characterReplacer;

	public BufferedEmail() {

		this.emailServer = null;
		this.from = null;
		this.sender = null;
		this.replyTo = null;
		this.toAddresses = new ArrayList<>();
		this.ccAddresses = new ArrayList<>();
		this.bccAddresses = new ArrayList<>();
		this.references = new ArrayList<>();
		this.subject = "";
		this.content = "";
		this.contentType = EmailContentType.PLAIN;
		this.autoSubmitted = null;
		this.attachments = new ArrayList<>();
		this.characterReplacer = new UtfNonBmpCharacterReplacer();
	}

	// ------------------------------ setter ------------------------------ //

	public BufferedEmail setEmailServer(AGServer transportServer) {

		this.emailServer = transportServer;
		return this;
	}

	@Override
	public IEmail setFrom(String from) {

		this.from = BufferedEmailAddresses.parseAddress(from);
		return this;
	}

	@Override
	public IEmail setFrom(AGUser from) {

		this.from = BufferedEmailAddresses.parseAddress(from.getEmailAddress());
		return this;
	}

	@Override
	public IEmail setSender(String sender) {

		this.sender = BufferedEmailAddresses.parseAddress(sender);
		return this;
	}

	@Override
	public IEmail setSender(AGUser sender) {

		this.sender = BufferedEmailAddresses.parseAddress(sender.getEmailAddress());
		return this;
	}

	@Override
	public IEmail setReplyTo(String replyTo) {

		this.replyTo = BufferedEmailAddresses.parseAddress(replyTo);
		return this;
	}

	@Override
	public IEmail setReplyTo(AGUser replyTo) {

		this.replyTo = BufferedEmailAddresses.parseAddress(replyTo.getEmailAddress());
		return this;
	}

	@Override
	public IEmail addToRecipient(String recipient) {

		toAddresses.add(BufferedEmailAddresses.parseAddress(recipient));
		return this;
	}

	@Override
	public IEmail addCcRecipient(String recipient) {

		ccAddresses.add(BufferedEmailAddresses.parseAddress(recipient));
		return this;
	}

	@Override
	public IEmail addCcRecipient(AGUser recipient) {

		ccAddresses.add(BufferedEmailAddresses.parseAddress(recipient.getEmailAddress()));
		return this;
	}

	@Override
	public IEmail addBccRecipient(String recipient) {

		bccAddresses.add(BufferedEmailAddresses.parseAddress(recipient));
		return this;
	}

	@Override
	public IEmail addBccRecipient(AGUser recipient) {

		bccAddresses.add(BufferedEmailAddresses.parseAddress(recipient.getEmailAddress()));
		return this;
	}

	public IEmail setRecipients(Collection<EmailRecipient> recipients) {

		toAddresses.clear();
		ccAddresses.clear();
		bccAddresses.clear();
		recipients.forEach(this::addRecipient);
		return this;
	}

	@Override
	public IEmail setMessageId(EmailMessageId messageId) {

		this.messageId = messageId;
		return this;
	}

	@Override
	public IEmail setInReplyTo(EmailMessageId inReplyTo) {

		this.inReplyTo = inReplyTo;
		return this;
	}

	@Override
	public IEmail addReference(EmailMessageId referencedMessageId) {

		this.references.add(referencedMessageId);
		return this;
	}

	@Override
	public IEmail addReferences(Collection<EmailMessageId> referencedMessageIds) {

		this.references.addAll(referencedMessageIds);
		return this;
	}

	@Override
	public IEmail setSubject(IDisplayString subject) {

		this.subject = subject.toString().trim();
		return this;
	}

	@Override
	public IEmail setContent(String content, EmailContentType contentType) {

		this.content = content;
		this.contentType = contentType;
		return this;
	}

	@Override
	public IEmail setAutoSubmitted(String autoSubmitted) {

		this.autoSubmitted = autoSubmitted;
		return this;
	}

	@Override
	public IEmail addFile(File file) {

		attachments.add(new BufferedEmailAttachment(file.getName(), MimeType.APPLICATION_OCTET_STREAM, () -> FileInputStreamFactory.create(file)));
		return this;
	}

	public IEmail setAttachments(Collection<BufferedEmailAttachment> attachments) {

		this.attachments.clear();
		this.attachments.addAll(attachments);
		return this;
	}

	@Override
	public IEmail addByteArray(byte[] data, String name, IMimeType type) {

		attachments.add(new BufferedEmailAttachment(name, type, data));
		return this;
	}

	@Override
	public IEmail addStream(InputStream inputStream, String name, IMimeType type) {

		attachments.add(new BufferedEmailAttachment(name, type, () -> inputStream));
		return this;
	}

	@Override
	public void submit() {

		validate();

		AGBufferedEmail mail = new AGBufferedEmail();
		mail.setEmailServer(emailServer != null? emailServer : AGCoreModuleInstance.getInstance().getEmailServerOrThrow());
		mail.setCreatedAt(DayTime.now());
		mail.setCreatedBy(CurrentUser.get());
		mail.setSentAt(null);
		mail.setFrom(from != null? from.toString() : "");
		mail.setSender(sender != null? sender.toString() : "");
		mail.setReplyTo(replyTo != null? replyTo.toString() : "");
		mail.setTo(BufferedEmailAddresses.implodeAddresses(toAddresses));
		mail.setCc(BufferedEmailAddresses.implodeAddresses(ccAddresses));
		mail.setBcc(BufferedEmailAddresses.implodeAddresses(bccAddresses));
		mail.setMessageId(messageId != null? messageId.getWithAngleBrackets() : null);
		mail.setInReplyTo(inReplyTo != null? inReplyTo.getWithAngleBrackets() : null);
		mail.setReferences(Imploder.implode(references, EmailMessageId::getWithAngleBrackets, " "));
		mail.setSubject(getCleanUtf16(subject));
		mail.setContent(getCleanUtf16(content));
		mail.setContentType(contentType.getContentTypeString());
		mail.setAutoSubmitted(autoSubmitted);
		mail.save();

		int index = 0;
		for (BufferedEmailAttachment attachment: attachments) {
			AGBufferedEmailAttachment storedAttachment = new AGBufferedEmailAttachment();
			storedAttachment.setIndex(index);
			storedAttachment.setEmail(mail);
			storedAttachment.setName(attachment.getName());
			storedAttachment.setType(attachment.getType().getIdentifier());
			storedAttachment.setData(attachment.getData());
			storedAttachment.save();

			index++;
		}

		Programs.enqueueExecution(BufferedEmailSendProgram.class);
	}

	@Override
	public MimeMessage toMimeMessage() {

		return new BufferedEmailToMimeMessageConverter(this).convert();
	}

	// ------------------------------ getter ------------------------------ //

	public Optional<InternetAddress> getFrom() {

		return Optional.ofNullable(from);
	}

	public Optional<InternetAddress> getSender() {

		return Optional.ofNullable(sender);
	}

	public Optional<InternetAddress> getReplyTo() {

		return Optional.ofNullable(replyTo);
	}

	public Collection<EmailRecipient> getRecipients() {

		var recipients = new ArrayList<EmailRecipient>();
		toAddresses.stream().map(address -> new EmailRecipient(address.toString(), AGEmailRecipientTypeEnum.TO)).forEach(recipients::add);
		ccAddresses.stream().map(address -> new EmailRecipient(address.toString(), AGEmailRecipientTypeEnum.CC)).forEach(recipients::add);
		bccAddresses.stream().map(address -> new EmailRecipient(address.toString(), AGEmailRecipientTypeEnum.BCC)).forEach(recipients::add);
		return recipients;
	}

	public Optional<EmailMessageId> getMessageId() {

		return Optional.ofNullable(messageId);
	}

	public Optional<EmailMessageId> getInReplyTo() {

		return Optional.ofNullable(inReplyTo);
	}

	public Collection<EmailMessageId> getReferences() {

		return Collections.unmodifiableCollection(references);
	}

	public String getSubject() {

		return subject;
	}

	public String getContent() {

		return content;
	}

	public EmailContentType getContentType() {

		return contentType;
	}

	public Optional<String> getAutoSubmitted() {

		return Optional.ofNullable(autoSubmitted);
	}

	public Collection<BufferedEmailAttachment> getAttachments() {

		return Collections.unmodifiableList(attachments);
	}

	// ------------------------------ internal ------------------------------ //

	protected String getInvalidCharacterReplacement() {

		return characterReplacer.getReplacement();
	}

	private String getCleanUtf16(String text) {

		return characterReplacer.getReplaced(text);
	}

	private void validate() {

		// check FROM
		Objects.requireNonNull(from, "FROM field of Email missing");

		// check TO, CC and BCC
		if (toAddresses.isEmpty() && ccAddresses.isEmpty() && bccAddresses.isEmpty()) {
			throw new SofticarDeveloperException("Failed to define TO, CC or BCC field of e-mail.");
		}
	}
}
