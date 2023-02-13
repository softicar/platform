package com.softicar.platform.core.module.email.composer;

import com.softicar.platform.common.io.mime.CustomMimeType;
import com.softicar.platform.core.module.email.buffer.attachment.BufferedEmailAttachment;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.upload.DomUploadForm;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class EmailAttachmentListDiv extends DomDiv {

	private final Collection<EmailAttachmentElement> attachmentElements;
	private final DomUploadForm uploadForm;

	public EmailAttachmentListDiv() {

		this.attachmentElements = new ArrayList<>();
		this.uploadForm = new DomUploadForm().setUploadHandler(this::handleFileUploads);

		appendChild(uploadForm);
	}

	public EmailAttachmentListDiv addAttachment(BufferedEmailAttachment attachment) {

		var attachmentElement = new EmailAttachmentElement(attachment)//
			.appendRemoveButton(this::removeAttachment);
		attachmentElements.add(attachmentElement);
		insertBefore(attachmentElement, uploadForm);
		return this;
	}

	public EmailAttachmentListDiv setAttachments(Collection<BufferedEmailAttachment> attachments) {

		clear();
		attachments.forEach(this::addAttachment);
		return this;
	}

	public Collection<BufferedEmailAttachment> getAttachments() {

		return attachmentElements//
			.stream()
			.map(element -> element.getAttachment())
			.collect(Collectors.toList());
	}

	public EmailAttachmentListDiv clear() {

		attachmentElements.forEach(IDomElement::disappend);
		attachmentElements.clear();
		return this;
	}

	private void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

		fileUploads.forEach(this::handleFileUpload);
	}

	private void handleFileUpload(IDomFileUpload fileUpload) {

		var attachment = new BufferedEmailAttachment(//
			fileUpload.getFilename(),
			new CustomMimeType(fileUpload.getContentType()),
			fileUpload.getContentAsBytes());
		addAttachment(attachment);
	}

	private void removeAttachment(EmailAttachmentElement attachmentElement) {

		attachmentElement.disappend();
		attachmentElements.remove(attachmentElement);
	}
}
