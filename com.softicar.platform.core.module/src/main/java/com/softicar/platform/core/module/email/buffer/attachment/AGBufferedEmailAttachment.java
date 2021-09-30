package com.softicar.platform.core.module.email.buffer.attachment;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class AGBufferedEmailAttachment extends AGBufferedEmailAttachmentGenerated implements IEmfObject<AGBufferedEmailAttachment> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString
			.format(//
				"%s: %s (%s)",
				getThis().getIndex(),
				getThis().getName(),
				getThis().getType());
	}
}
