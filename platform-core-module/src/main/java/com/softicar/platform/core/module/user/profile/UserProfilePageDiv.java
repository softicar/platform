package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.password.change.UserPasswordChangeDiv;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFieldset;

public class UserProfilePageDiv extends DomDiv {

	public UserProfilePageDiv() {

		DomDiv passwordDiv = appendChild(new DomDiv());
		DomFieldset passwordFieldSet = passwordDiv.appendChild(new DomFieldset());
		passwordFieldSet.setLegend(CoreI18n.PASSWORD);
		passwordFieldSet.appendChild(new UserPasswordChangeDiv(SofticarPasswordPolicy.get()));

		DomDiv languageDiv = appendChild(new DomDiv());
		DomFieldset languageFieldSet = languageDiv.appendChild(new DomFieldset());
		languageFieldSet.setLegend(CoreI18n.LANGUAGE);
		languageFieldSet.appendChild(new UserProfileChangeLocalizationDiv());
	}
}
