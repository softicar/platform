package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.core.module.email.IEmail;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;

public class BufferedEmailFactory {

	public static IEmail createNoReplyEmail() {

		String noReplyEmailAddress = AGCoreModuleInstance.getInstance().getNoReplyEmailAddress();
		IEmail email = new BufferedEmail();
		email.setFrom(noReplyEmailAddress);
		email.setSender(noReplyEmailAddress);
		return email;

	}
}
