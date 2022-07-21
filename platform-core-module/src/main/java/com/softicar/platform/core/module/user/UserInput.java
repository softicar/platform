package com.softicar.platform.core.module.user;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInputEngine;

/**
 * Auto complete input for active users.
 *
 * @author Viktor Nikolaus
 */
public class UserInput extends EmfEntityInput<AGUser> {

	public UserInput() {

		super(new EmfEntityInputEngine<>(AGUser.TABLE));

		setPlaceholder(CoreI18n.USER_NAME_OR_USER_ID);
	}
}
