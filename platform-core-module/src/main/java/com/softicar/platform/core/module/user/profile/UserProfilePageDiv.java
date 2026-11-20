package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.page.PageTheme;
import com.softicar.platform.core.module.page.variant.PageThemeVariantChooser;
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
		languageFieldSet.setLegend(CoreI18n.LOCALIZATION);
		languageFieldSet.appendChild(new UserProfileChangeLocalizationDiv());

		DomDiv navigationDiv = appendChild(new DomDiv());
		DomFieldset preferencesFieldSet = navigationDiv.appendChild(new DomFieldset());
		preferencesFieldSet.setLegend(CoreI18n.PREFERENCES);
		preferencesFieldSet.appendChild(new UserProfilePreferencesDiv());

		if (PageTheme.getCurrent() == PageTheme.MODERN) {
			// a section of its own: the variants apply at once, while the preferences above need their save button
			DomDiv appearanceDiv = appendChild(new DomDiv());
			DomFieldset appearanceFieldSet = appearanceDiv.appendChild(new DomFieldset());
			appearanceFieldSet.setLegend(CoreI18n.APPEARANCE);
			appearanceFieldSet.appendChild(new PageThemeVariantChooser());
		}
	}
}
