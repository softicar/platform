package com.softicar.platform.core.module.access.role.assignment.module.system;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.access.role.EmfSystemModuleRole;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.engine.AbstractEmfDependentAutoCompleteInputEngine;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import java.util.Collection;
import java.util.stream.Collectors;

class SystemModuleRoleAssignmentRoleInput extends EmfEntityInput<AGUuid> {

	public SystemModuleRoleAssignmentRoleInput() {

		super(new InputEngine());
		setPlaceholder(CoreI18n.ROLE);
	}

	private static class InputEngine extends AbstractEmfDependentAutoCompleteInputEngine<AGUuid> {

		public InputEngine() {

			super(AGUuid.TABLE);
		}

		@Override
		protected Collection<AGUuid> loadItems() {

			return CurrentEmfRoleRegistry
				.get()
				.getStaticRoles(CurrentEmfModuleRegistry.get().getModule(CoreModule.class))
				.stream()
				.filter(role -> EmfSystemModuleRole.class.isAssignableFrom(role.getClass()))
				.map(IEmfStaticRole::getAnnotatedUuid)
				.map(AGUuid::getOrCreate)
				.collect(Collectors.toList());
		}
	}
}
