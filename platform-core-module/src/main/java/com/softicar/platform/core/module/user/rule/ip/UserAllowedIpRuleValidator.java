package com.softicar.platform.core.module.user.rule.ip;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.validation.AbstractEmfValidator;
import java.util.Collection;

public class UserAllowedIpRuleValidator extends AbstractEmfValidator<AGUserAllowedIpRule> {

	@Override
	protected void validate() {

		Collection<String> incorrectAddresses = new UserAllowedIpRuleParser(tableRow).validateAndGetIncorrectAddresses();
		if (!incorrectAddresses.isEmpty()) {
			addError(//
				AGUserAllowedIpRule.ADDRESSES,
				CoreI18n.INVALID_IP_ADDRESSES_ARG1.toDisplay(Imploder.implode(incorrectAddresses, ", ")));
		}
	}
}
