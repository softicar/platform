package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.Set;

class ProgramDescriptionField extends AbstractTransientObjectField<AGProgram, IDisplayString> {

	public ProgramDescriptionField() {

		super(IDisplayString.class);
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.DESCRIPTION;
	}

	@Override
	protected void loadValues(Set<AGProgram> programs, IValueSetter<AGProgram, IDisplayString> setter) {

		programs.forEach(program -> setter.set(program, fetchDescription(program)));
	}

	@Override
	protected IDisplayString getDefaultValue() {

		return IDisplayString.EMPTY;
	}

	private IDisplayString fetchDescription(AGProgram program) {

		return EmfSourceCodeReferencePoints
			.getReferencePointOrThrow(program.getProgramUuid().getUuid(), IProgram.class)//
			.getDescription()
			.orElse(getDefaultValue());
	}
}
