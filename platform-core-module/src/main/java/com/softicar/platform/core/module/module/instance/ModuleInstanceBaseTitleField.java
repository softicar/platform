package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.util.Set;

public class ModuleInstanceBaseTitleField extends AbstractTransientObjectField<AGModuleInstanceBase, String> {

	public ModuleInstanceBaseTitleField() {

		super(String.class);
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.TITLE;
	}

	@Override
	protected void loadValues(Set<AGModuleInstanceBase> instances, IValueSetter<AGModuleInstanceBase, String> setter) {

		instances.forEach(instance -> setter.set(instance, instance.toDisplayWithoutId().toString()));
	}

	@Override
	protected String getDefaultValue() {

		return "";
	}
}
