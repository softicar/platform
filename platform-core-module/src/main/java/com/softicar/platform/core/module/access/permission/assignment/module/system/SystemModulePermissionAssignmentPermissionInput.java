package com.softicar.platform.core.module.access.permission.assignment.module.system;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.access.permission.EmfSystemModulePermission;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.engine.AbstractEmfDependentAutoCompleteInputEngine;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import java.util.Collection;
import java.util.stream.Collectors;

class SystemModulePermissionAssignmentPermissionInput extends EmfEntityInput<AGUuid> {

	public SystemModulePermissionAssignmentPermissionInput() {

		super(new InputEngine());
		setPlaceholder(CoreI18n.PERMISSION);
	}

	private static class InputEngine extends AbstractEmfDependentAutoCompleteInputEngine<AGUuid> {

		public InputEngine() {

			super(AGUuid.TABLE);
		}

		@Override
		protected Collection<AGUuid> loadItems() {

			return CurrentEmfPermissionRegistry
				.get()
				.getStaticPermissions(CurrentEmfModuleRegistry.get().getModule(CoreModule.class))
				.stream()
				.filter(permission -> EmfSystemModulePermission.class.isAssignableFrom(permission.getClass()))
				.map(IEmfStaticPermission::getAnnotatedUuid)
				.map(AGUuid::getOrCreate)
				.collect(Collectors.toList());
		}
	}
}
