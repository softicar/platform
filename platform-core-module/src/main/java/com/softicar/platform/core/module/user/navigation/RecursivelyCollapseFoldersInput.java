package com.softicar.platform.core.module.user.navigation;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanInput;

public class RecursivelyCollapseFoldersInput extends EmfBooleanInput {

	private final AGUser user;

	public RecursivelyCollapseFoldersInput(AGUser user) {

		super(user.isRecursivelyCollapseFolders());
		this.user = user;
		refreshInputConstraints();
	}

	@Override
	public void refreshInputConstraints() {

		if (user.isAutomaticallyCollapseFolders()) {
			setValue(true);
			setEnabled(false);
		} else {
			setEnabled(true);
		}
	}
}
