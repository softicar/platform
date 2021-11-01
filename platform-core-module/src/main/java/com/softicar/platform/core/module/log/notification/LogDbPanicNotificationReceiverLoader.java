package com.softicar.platform.core.module.log.notification;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.core.module.event.panic.AGModulePanicReceiver;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.registry.IEmfModuleRegistry;
import java.util.Collection;
import java.util.Collections;

class LogDbPanicNotificationReceiverLoader {

	private final String entryPointClassName;

	public LogDbPanicNotificationReceiverLoader(String entryPointClassName) {

		this.entryPointClassName = entryPointClassName;
	}

	public Collection<String> load() {

		try {
			return loadReceivers();
		} catch (Exception exception) {
			Log.ferror(exception);
			return Collections.emptyList();
		}
	}

	private Collection<String> loadReceivers() {

		return AGModulePanicReceiver.getPanicReceiverEmailAddressesForModule(determineModule());
	}

	private IEmfModule<?> determineModule() {

		return IEmfModuleRegistry//
			.get()
			.getContainingModule(new JavaClassName(entryPointClassName).getPackageName())
			.orElseThrow(() -> new RuntimeException(String.format("Cannot determine module of entry point: %s", entryPointClassName)));
	}
}
