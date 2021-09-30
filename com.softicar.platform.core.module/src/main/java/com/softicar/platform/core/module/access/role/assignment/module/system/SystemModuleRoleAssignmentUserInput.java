package com.softicar.platform.core.module.access.role.assignment.module.system;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInputEngine;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * An {@link AGUser} input element that excludes the system user.
 *
 * @author Alexander Schmidt
 */
public class SystemModuleRoleAssignmentUserInput extends EmfEntityInput<AGUser> {

	public SystemModuleRoleAssignmentUserInput() {

		setInputEngine(new InputEngine(AGUser.TABLE));
		setPlaceholder(CoreI18n.USER);
	}

	private class InputEngine extends EmfEntityInputEngine<AGUser> {

		public InputEngine(IEmfEntityTable<AGUser, ?, ?> table) {

			super(table);
		}

		@Override
		public Collection<AGUser> filterMatchingItems(String pattern, Collection<AGUser> matchingItems) {

			return super.filterMatchingItems(pattern, matchingItems)//
				.stream()
				.filter(this::isNotSystemUser)
				.collect(Collectors.toList());
		}

		@Override
		public AGUser getById(Integer id) {

			return Optional//
				.ofNullable(super.getById(id))
				.filter(this::isNotSystemUser)
				.orElse(null);
		}

		private boolean isNotSystemUser(AGUser user) {

			return getSystemUser()//
				.map(systemUser -> !systemUser.equals(user))
				.orElse(true);
		}

		private Optional<AGUser> getSystemUser() {

			return Optional.ofNullable(AGCoreModuleInstance.getInstance().getSystemUser());
		}
	}
}
