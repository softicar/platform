package com.softicar.platform.core.module.access.role.assignment.module.system;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.AbstractSystemModule;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.engine.AbstractEmfDependentAutoCompleteInputEngine;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import java.util.Collection;
import java.util.stream.Collectors;

class SystemModuleRoleAssignmentSystemModuleInput extends EmfEntityInput<AGUuid> {

	public SystemModuleRoleAssignmentSystemModuleInput() {

		setInputEngine(new InputEngine());
		setPlaceholder(CoreI18n.MODULE);
	}

	private class InputEngine extends AbstractEmfDependentAutoCompleteInputEngine<AGUuid> {

		public InputEngine() {

			super(AGUuid.TABLE);
		}

		@Override
		protected Collection<AGUuid> loadItems() {

			return CurrentEmfModuleRegistry//
				.get()
				.getAllModules()
				.stream()
				.filter(module -> AbstractSystemModule.class.isAssignableFrom(module.getClass()))
				.map(module -> module.getAnnotatedUuid())
				.map(AGUuid::getOrCreate)
				.collect(Collectors.toList());
		}
	}
}
