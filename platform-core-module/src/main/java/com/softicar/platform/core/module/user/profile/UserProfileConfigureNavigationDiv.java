package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.editor.EmfAttributesDiv;

class UserProfileConfigureNavigationDiv extends DomDiv {

	private final EmfAttributesDiv<AGUser> attributesDiv;

	public UserProfileConfigureNavigationDiv() {

		this.attributesDiv = new EmfAttributesDiv<>(CurrentUser.get(), true);
		attributesDiv.addAttribute(AGUser.AUTOMATICALLY_COLLAPSE_FOLDERS);

		appendChild(attributesDiv);
		appendChild(
			new DomActionBar(
				new DomButton()//
					.setLabel(EmfI18n.SAVE)
					.setClickCallback(attributesDiv::tryToApplyValidateAndSave)));
	}
}
