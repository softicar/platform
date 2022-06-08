package com.softicar.platform.core.module.access.permission.assignment.module.system;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.engine.AbstractEmfDependentAutoCompleteInputEngine;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import java.util.Collection;
import java.util.stream.Collectors;

class SystemModulePermissionAssignmentSystemModuleInput extends EmfEntityInput<AGUuid> {

	public SystemModulePermissionAssignmentSystemModuleInput() {

		super(new InputEngine());
		setPlaceholder(CoreI18n.MODULE);
	}

	private static class InputEngine extends AbstractEmfDependentAutoCompleteInputEngine<AGUuid> {

		public InputEngine() {

			super(AGUuid.TABLE);
		}

		@Override
		protected Collection<AGUuid> loadItems() {

			return CurrentEmfModuleRegistry//
				.get()
				.getAllModules()
				.stream()
				.filter(module -> CoreModule.class.isInstance(module))
				.map(module -> module.getAnnotatedUuid())
				.map(AGUuid::getOrCreate)
				.collect(Collectors.toList());
		}
	}
}
