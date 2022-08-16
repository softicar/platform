package com.softicar.platform.core.module.event;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverity;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.emf.module.IEmfModuleInstance;

public class SystemEventBuilder {

	private static final String KEY_MODULE_INSTANCE_ID = "moduleInstanceId";
	private static final String KEY_STACK_TRACE = "stackTrace";
	private final AGSystemEventSeverity severity;
	private final AGUser causedBy;
	private final DayTime causedAt;
	private final String message;
	private final JsonObject properties;

	public SystemEventBuilder(AGSystemEventSeverityEnum severityEnum, String message) {

		this.severity = severityEnum.getRecord();
		this.causedBy = CurrentUser.get();
		this.causedAt = DayTime.now();
		this.message = message;
		this.properties = new JsonObject();
	}

	@Override
	public String toString() {

		return properties.toString();
	}

	public AGSystemEvent save() {

		try (DbConnectionOverrideScope scope = new DbConnectionOverrideScope()) {
			return new AGSystemEvent()//
				.setSeverity(severity)
				.setCausedBy(causedBy)
				.setCausedAt(causedAt)
				.setMessage(message)
				.setNeedsConfirmation(severity.isNeedsConfirmation())
				.setProperties(properties.toString())
				.save();
		}
	}

	// ------------------------------ properties ------------------------------ //

	public SystemEventBuilder addProperty(String key, JsonElement element) {

		properties.add(key, element);
		return this;
	}

	public SystemEventBuilder addProperty(String key, Boolean value) {

		properties.addProperty(key, value);
		return this;
	}

	public SystemEventBuilder addProperty(String key, Number value) {

		properties.addProperty(key, value);
		return this;
	}

	public SystemEventBuilder addProperty(String key, String value) {

		properties.addProperty(key, value);
		return this;
	}

	public SystemEventBuilder addProperty(String key, IDisplayString value) {

		properties.addProperty(key, value.toString());
		return this;
	}

	public SystemEventBuilder addModuleInstance(IEmfModuleInstance<?> moduleInstance) {

		properties.addProperty(KEY_MODULE_INSTANCE_ID, moduleInstance.getItemId().toString());
		return this;
	}

	public SystemEventBuilder addStackTrace() {

		properties.addProperty(KEY_STACK_TRACE, StackTraceFormatting.getStackTraceAsString(Thread.currentThread().getStackTrace()));
		return this;
	}
}
