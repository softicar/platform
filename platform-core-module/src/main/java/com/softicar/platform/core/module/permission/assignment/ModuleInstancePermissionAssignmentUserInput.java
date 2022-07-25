package com.softicar.platform.core.module.permission.assignment;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInputEngine;

/**
 * An {@link AGUser} input element that excludes the system user.
 *
 * @author Alexander Schmidt
 */
public class ModuleInstancePermissionAssignmentUserInput extends EmfEntityInput<AGUser> {

	public ModuleInstancePermissionAssignmentUserInput() {

		super(new EmfEntityInputEngine<>(AGUser.TABLE).addValidator(user -> !user.isSystemUser()));

		setPlaceholder(CoreI18n.USER);
	}
}
