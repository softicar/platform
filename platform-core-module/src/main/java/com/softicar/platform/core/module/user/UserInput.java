package com.softicar.platform.core.module.user;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInMemoryInputEngine;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;

/**
 * Auto complete input for active users.
 *
 * @author Viktor Nikolaus
 */
public class UserInput extends EmfEntityInput<AGUser> {

	public UserInput() {

		super(new DomAutoCompleteEntityInMemoryInputEngine<>(AGUser::getAllActive));
		setPlaceholder(CoreI18n.USER_NAME_OR_USER_ID);
	}
}
