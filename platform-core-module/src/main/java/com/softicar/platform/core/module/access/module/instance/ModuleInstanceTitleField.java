package com.softicar.platform.core.module.access.module.instance;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.util.Set;

public class ModuleInstanceTitleField extends AbstractTransientObjectField<AGModuleInstance, IDisplayString> {

	public ModuleInstanceTitleField() {

		super(IDisplayString.class);
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.TITLE;
	}

	@Override
	protected void loadValues(Set<AGModuleInstance> instances, IValueSetter<AGModuleInstance, IDisplayString> setter) {

		instances.forEach(instance -> setter.set(instance, instance.toDisplayWithoutId()));
	}

	@Override
	protected IDisplayString getDefaultValue() {

		return IDisplayString.EMPTY;
	}
}
