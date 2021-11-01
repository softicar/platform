package com.softicar.platform.core.module.email.buffer.attachment;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.emf.action.section.counted.EmfCountedContentSectionDiv;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.header.EmfFormSectionHeader;

public class BufferedEmailAttachmentSectionDiv extends EmfCountedContentSectionDiv<AGBufferedEmail> {

	private static final EmfFormSectionHeader SECTION_HEADER = new EmfFormSectionHeader()//
		.setIcon(CoreImages.EMAIL_ATTACHMENT.getResource())
		.setCaption(CoreI18n.ATTACHMENTS);

	public BufferedEmailAttachmentSectionDiv(IEmfFormBody<AGBufferedEmail> formBody) {

		super(new BufferedEmailAttachmentSectionDivEngine(formBody), SECTION_HEADER);
	}
}
