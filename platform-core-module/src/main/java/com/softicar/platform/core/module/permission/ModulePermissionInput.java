package com.softicar.platform.core.module.permission;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.role.permission.AGRolePermission;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInputFilter;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.engine.AbstractEmfDependentAutoCompleteInputEngine;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.permission.CurrentEmfPermissionRegistry;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModulePermissionInput extends EmfEntityInput<AGUuid> {

	public ModulePermissionInput(Supplier<Optional<AGModuleInstance>> moduleInstanceSupplier) {

		super(new InputEngine(moduleInstanceSupplier));
		setPlaceholder(CoreI18n.PERMISSION);
	}

	public ModulePermissionInput(AGModuleInstancePermissionAssignment assignment) {

		this(() -> Optional.ofNullable(assignment.getModuleInstance()));
	}

	public ModulePermissionInput(AGRolePermission rolePermission) {

		this(() -> Optional.ofNullable(rolePermission.getModuleInstance()));
	}

	private static class InputEngine extends AbstractEmfDependentAutoCompleteInputEngine<AGUuid> {

		private final DomAutoCompleteEntityInputFilter<AGModuleInstance> filter;

		public InputEngine(Supplier<Optional<AGModuleInstance>> moduleInstanceSupplier) {

			super(AGUuid.TABLE);
			this.filter = addFilter(() -> moduleInstanceSupplier.get()).setFilterTitle(CoreI18n.MODULE_INSTANCE);
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
