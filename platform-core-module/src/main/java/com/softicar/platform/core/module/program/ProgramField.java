package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import java.util.Set;

class ProgramField extends AbstractTransientObjectField<AGProgram, String> {

	public ProgramField() {

		super(String.class);
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.PROGRAM;
	}

	@Override
	protected void loadValues(Set<AGProgram> programs, IValueSetter<AGProgram, String> setter) {

		AGProgram.TABLE//
			.createSelect()
			.where(AGProgram.ID.isIn(programs))
			.forEach(record -> setter.set(record, record.toDisplayWithoutId().toString()));
	}

	@Override
	protected String getDefaultValue() {

		return "";
	}
}
