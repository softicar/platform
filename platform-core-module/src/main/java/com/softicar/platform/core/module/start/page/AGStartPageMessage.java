package com.softicar.platform.core.module.start.page;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.List;

public class AGStartPageMessage extends AGStartPageMessageGenerated implements IEmfObject<AGStartPageMessage> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getMessage());
	}

	public static List<AGStartPageMessage> getLatestMessages(int limit) {

		return AGStartPageMessage.TABLE//
			.createSelect()
			.where(AGStartPageMessage.ACTIVE)
			.orderDescendingBy(MESSAGE_DATE)
			.list(limit);
	}
}
