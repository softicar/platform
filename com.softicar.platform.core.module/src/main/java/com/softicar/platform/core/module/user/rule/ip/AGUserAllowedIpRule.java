package com.softicar.platform.core.module.user.rule.ip;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.network.address.IllegalIpv4Address;
import com.softicar.platform.common.network.address.IllegalIpv4NetworkAddress;
import com.softicar.platform.common.network.address.Ipv4Address;
import com.softicar.platform.core.module.log.LogDb;
import com.softicar.platform.emf.object.IEmfObject;

public class AGUserAllowedIpRule extends AGUserAllowedIpRuleGenerated implements IEmfObject<AGUserAllowedIpRule> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getName());
	}

	public boolean matches(String clientAddressString) {

		try {
			Ipv4Address clientAddress = new Ipv4Address(clientAddressString);
			return new UserAllowedIpRuleParser(this)//
				.getMatchers()
				.stream()
				.anyMatch(matcher -> matcher.test(clientAddress));
		} catch (IllegalIpv4Address | IllegalIpv4NetworkAddress exception) {
			LogDb.panic(exception, "Problem with ip rule %s.", getId());
			return false;
		}
	}
}
