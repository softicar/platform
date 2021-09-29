package com.softicar.platform.core.module.access.role.assignment.module.instance;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInputFilter;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.engine.AbstractEmfDependentAutoCompleteInputEngine;
import com.softicar.platform.emf.authorization.role.CurrentEmfRoleRegistry;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

class ModuleInstanceRoleAssignmentRoleInput extends EmfEntityInput<AGUuid> {

	public ModuleInstanceRoleAssignmentRoleInput(AGModuleInstanceRoleAssignment assignment) {

		setInputEngine(new InputEngine(assignment));
		setPlaceholder(CoreI18n.ROLE);
	}

	private class InputEngine extends AbstractEmfDependentAutoCompleteInputEngine<AGUuid> {

		private final DomAutoCompleteEntityInputFilter<AGModuleInstance> filter;

		public InputEngine(AGModuleInstanceRoleAssignment assignment) {

			super(AGUuid.TABLE);
			this.filter = addFilter(() -> Optional.ofNullable(assignment.getModuleInstance())).setFilterTitle(CoreI18n.MODULE_INSTANCE);
		}

		@Override
		protected Collection<AGUuid> loadItems() {

			return filter//
				.getValue()
				.flatMap(AGModuleInstance::getModule)
				.map(CurrentEmfRoleRegistry.get()::getStaticRoles)
				.stream()
				.flatMap(Collection::stream)
				.filter(staticRole -> IEmfModuleRole.class.isAssignableFrom(staticRole.getClass()))
				.map(IEmfStaticRole::getAnnotatedUuid)
				.map(AGUuid::getOrCreate)
				.collect(Collectors.toList());
		}
	}
}
