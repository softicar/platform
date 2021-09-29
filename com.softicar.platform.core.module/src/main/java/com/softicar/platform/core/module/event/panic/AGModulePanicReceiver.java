package com.softicar.platform.core.module.event.panic;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Collection;
import java.util.stream.Collectors;

public class AGModulePanicReceiver extends AGModulePanicReceiverGenerated implements IEmfObject<AGModulePanicReceiver> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getUser().toDisplayWithoutId();
	}

	public static Collection<String> getPanicReceiverEmailAddressesForModule(IEmfModule<?> module) {

		return AGModulePanicReceiver//
			.createSelect()
			.where(AGModulePanicReceiver.ACTIVE)
			.where(AGModulePanicReceiver.MODULE_UUID.equal(AGUuid.getOrCreate(module.getAnnotatedUuid())))
			.stream()
			.map(AGModulePanicReceiver::getUser)
			.map(AGUser::getEmailAddress)
			.collect(Collectors.toList());
	}
}
