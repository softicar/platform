package com.softicar.platform.core.module.email.composer;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.email.buffer.attachment.BufferedEmailAttachment;
import com.softicar.platform.dom.elements.bar.DomBar;
import com.softicar.platform.dom.elements.button.DomButton;
import java.util.function.Consumer;

public class EmailAttachmentElement extends DomBar {

	private final BufferedEmailAttachment attachment;

	public EmailAttachmentElement(BufferedEmailAttachment attachment) {

		this.attachment = attachment;

		new DomButton()//
			.setLabel(attachment.getName())
			.setTitle(CoreI18n.CLICK_TO_DOWNLOAD_THE_FILE_ARG1.toDisplay(attachment.getName()))
			.setClickCallback(this::downloadAttachment)
			.appendTo(this);
	}

	public EmailAttachmentElement appendRemoveButton(Consumer<EmailAttachmentElement> removeCallback) {

		new DomButton()//
			.setIcon(CoreImages.ENTRY_REMOVE.getResource())
			.setTitle(CoreI18n.REMOVE_ENTRY)
			.setClickCallback(() -> removeCallback.accept(this))
			.appendTo(this);
		return this;
	}

	public BufferedEmailAttachment getAttachment() {

		return attachment;
	}

	private void downloadAttachment() {

		getDomEngine()//
			.createExport()
			.setFilename(attachment.getName())
			.setMimeType(attachment.getType())
			.write(attachment.getData());
	}
}
