package com.softicar.platform.core.module.access.permission.assignment.module.instance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInputFilter;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.engine.AbstractEmfDependentAutoCompleteInputEngine;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

class ModuleInstancePermissionAssignmentPermissionInput extends EmfEntityInput<AGUuid> {

	public ModuleInstancePermissionAssignmentPermissionInput(AGModuleInstancePermissionAssignment assignment) {

		super(new InputEngine(assignment));
		setPlaceholder(CoreI18n.PERMISSION);
	}

	private static class InputEngine extends AbstractEmfDependentAutoCompleteInputEngine<AGUuid> {

		private final DomAutoCompleteEntityInputFilter<AGModuleInstance> filter;

		public InputEngine(AGModuleInstancePermissionAssignment assignment) {

			super(AGUuid.TABLE);
			this.filter = addFilter(() -> Optional.ofNullable(assignment.getModuleInstance())).setFilterTitle(CoreI18n.MODULE_INSTANCE);
		}

		@Override
		protected Collection<AGUuid> loadItems() {

			return filter//
				.getValue()
				.flatMap(AGModuleInstance::getModule)
				.map(CurrentEmfPermissionRegistry.get()::getStaticPermissions)
				.stream()
				.flatMap(Collection::stream)
				.filter(staticPermission -> IEmfModulePermission.class.isAssignableFrom(staticPermission.getClass()))
				.map(IEmfStaticPermission::getAnnotatedUuid)
				.map(AGUuid::getOrCreate)
				.collect(Collectors.toList());
		}
	}
}
