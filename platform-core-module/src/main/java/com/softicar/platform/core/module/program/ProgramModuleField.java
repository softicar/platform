package com.softicar.platform.core.module.program;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.transients.AbstractTransientObjectField;
import com.softicar.platform.db.runtime.transients.IValueSetter;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.Set;

class ProgramModuleField extends AbstractTransientObjectField<AGProgram, String> {

	public ProgramModuleField() {

		super(String.class);
	}

	@Override
	public IDisplayString getTitle() {

		return CoreI18n.MODULE;
	}

	@Override
	protected void loadValues(Set<AGProgram> programs, IValueSetter<AGProgram, String> setter) {

		programs.forEach(program -> setter.set(program, determineModuleName(program)));
	}

	@Override
	protected String getDefaultValue() {

		return "";
	}

	private String determineModuleName(AGProgram program) {

		try {
			Class<?> programClass = EmfSourceCodeReferencePoints//
				.getReferencePointOrThrow(program.getProgramUuid().getUuid(), IProgram.class)
				.getClass();
			return CurrentEmfModuleRegistry//
				.get()
				.getContainingModule(new JavaPackageName(programClass))
				.map(module -> module.toDisplay().toString())
				.orElse("");
		} catch (Exception exception) {
			DevNull.swallow(exception);
			return "";
		}
	}
}
