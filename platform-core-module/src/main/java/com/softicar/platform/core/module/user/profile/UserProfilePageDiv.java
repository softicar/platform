package com.softicar.platform.core.module.user.profile;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.password.change.UserPasswordChangeDiv;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFieldset;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.configuration.DomPopupDisplayMode;

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
		DomFieldset navigationFieldSet = navigationDiv.appendChild(new DomFieldset());
		navigationFieldSet.setLegend(CoreI18n.NAVIGATION);
		navigationFieldSet.appendChild(new UserProfileConfigureNavigationDiv());

		// FIXME remove
		appendChild(new Button(DomPopupDisplayMode.DRAGGABLE));
		appendChild(new Button(DomPopupDisplayMode.MAXIMIZED));
		appendChild(new Button(DomPopupDisplayMode.DRAGGABLE_MODAL));
	}

	private class Button extends DomButton {

		public Button(DomPopupDisplayMode displayMode) {

			setLabel(displayMode + "");
			setClickCallback(() -> new Popup(displayMode).open());
		}
	}

	private class Popup extends DomPopup {

		public Popup(DomPopupDisplayMode displayMode) {

			setCaption(IDisplayString.create(getNodeIdString()));
			configuration.setDisplayMode(displayMode);
			appendActionNode(new Button(DomPopupDisplayMode.DRAGGABLE));
			appendActionNode(new Button(DomPopupDisplayMode.MAXIMIZED));
			appendActionNode(new DomButton().setLabel("replace").setClickCallback(() -> {
				new Popup(displayMode).open();
				close();
			}));
			appendCloseButton();
		}
	}

}
