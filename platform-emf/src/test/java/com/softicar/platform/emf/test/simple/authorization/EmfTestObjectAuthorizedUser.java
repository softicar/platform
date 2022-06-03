package com.softicar.platform.emf.test.simple.authorization;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.object.IEmfObject;

public class EmfTestObjectAuthorizedUser extends EmfTestObjectAuthorizedUserGenerated implements IEmfObject<EmfTestObjectAuthorizedUser> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getObject().toDisplayWithoutId().concatSentence(getUser().toDisplayWithoutId());
	}
}
