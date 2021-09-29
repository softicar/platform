package com.softicar.platform.core.module.email.buffer.attachment;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.action.section.counted.IEmfCountedContentSectionDivEngine;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.management.EmfManagementDiv;

public class BufferedEmailAttachmentSectionDivEngine implements IEmfCountedContentSectionDivEngine<AGBufferedEmail> {

	private final IEmfFormBody<AGBufferedEmail> formBody;

	public BufferedEmailAttachmentSectionDivEngine(IEmfFormBody<AGBufferedEmail> formBody) {

		this.formBody = formBody;
	}

	@Override
	public IEmfFormBody<AGBufferedEmail> getFormBody() {

		return formBody;
	}

	@Override
	public IDomElement createContentElement() {

		return new EmfManagementDiv<>(AGBufferedEmailAttachment.TABLE, formBody.getTableRow());
	}

	@Override
	public int getContentCounter() {

		return AGBufferedEmailAttachment//
			.createSelect()
			.where(AGBufferedEmailAttachment.EMAIL.equal(formBody.getTableRow()))
			.count();
	}

	@Override
	public void setCountChangedCallback(INullaryVoidFunction countChangedCallback) {

		// nothing to do because this UI is read-only
	}
}
