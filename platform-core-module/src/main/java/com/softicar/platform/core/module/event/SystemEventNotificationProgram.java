package com.softicar.platform.core.module.event;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Optional;

@EmfSourceCodeReferencePointUuid("19377e46-0e79-4d83-ad69-71c81520a65f")
public class SystemEventNotificationProgram implements IProgram {

	@Override
	public void executeProgram() {

		SystemEventNotifier.notifyAboutEvents();
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 * * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.INFORMS_USERS_ABOUT_SYSTEM_EVENTS_THAT_NEED_CONFIRMATION);
	}
}
