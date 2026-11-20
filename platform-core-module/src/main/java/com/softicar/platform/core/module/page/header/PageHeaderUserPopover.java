package com.softicar.platform.core.module.page.header;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.page.navigation.modern.ModernNavigationCssClasses;
import com.softicar.platform.core.module.page.PageTheme;
import com.softicar.platform.core.module.page.variant.PageThemeVariantChooser;
import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.ajax.session.reset.AjaxSessionPage;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.service.PageServiceFactory;
import com.softicar.platform.core.module.user.profile.UserProfilePage;
import com.softicar.platform.core.module.web.service.WebServiceUrlBuilder;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.placement.strategy.DomPopupBelowEventNodePlacementStrategy;
import com.softicar.platform.dom.elements.popup.popover.DomMenuPopover;
import com.softicar.platform.emf.page.IEmfPage;
import jakarta.servlet.http.HttpSession;

class PageHeaderUserPopover extends DomMenuPopover {

	private final PageNavigationController controller;
	private PageThemeVariantChooser variantChooser;

	public PageHeaderUserPopover(PageNavigationController controller) {

		this.controller = controller;
		this.configuration.setPlacementStrategy(new DomPopupBelowEventNodePlacementStrategy());
		addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER);
		addCssClass(CoreCssClasses.PAGE_HEADER_USER_POPOVER);

		if (PageTheme.getCurrent() == PageTheme.MODERN) {
			// as in the ERP: a card with the avatar, the greeting and the e-mail address, the session and the profile,
			// then the logout set apart in red
			appendChild(new UserCard());
			appendChild(new CoreModulePageButton(AjaxSessionPage.class)).addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER_SESSION_PAGE_BUTTON);
			appendChild(new CoreModulePageButton(UserProfilePage.class)).addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER_PROFILE_PAGE_BUTTON);
			appendChild(new DomDiv()).addCssClass(ModernNavigationCssClasses.MODERN_HEADER_USER_DIVIDER);
			this.variantChooser = appendChild(PageThemeVariantChooser.createCompact());
			appendChild(new DomDiv()).addCssClass(ModernNavigationCssClasses.MODERN_HEADER_USER_DIVIDER);
			appendChild(new LogoutButton()).addCssClass(ModernNavigationCssClasses.MODERN_HEADER_USER_LOGOUT);
		} else {
			appendChild(new CoreModulePageButton(UserProfilePage.class)).addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER_PROFILE_PAGE_BUTTON);
			appendChild(new CoreModulePageButton(AjaxSessionPage.class)).addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER_SESSION_PAGE_BUTTON);
			appendChild(new LogoutButton());
		}
	}

	@Override
	public void open() {

		// the variant may have been changed in the profile since this menu was built
		if (variantChooser != null) {
			variantChooser.refresh();
		}
		super.open();
	}

	private class CoreModulePageButton extends DomButton {

		public CoreModulePageButton(Class<? extends IEmfPage<AGCoreModuleInstance>> pageClass) {

			var page = SourceCodeReferencePoints.getReferencePoint(pageClass);
			var instance = AGCoreModuleInstance.getInstance();

			setIcon(page.getIcon());
			setLabel(page.getTitle(instance));
			setClickCallback(() -> controller.openPage(page, instance.getId()));
		}
	}

	private static class UserCard extends DomDiv {

		public UserCard() {

			AGUser user = CurrentUser.get();
			setCssClass(ModernNavigationCssClasses.MODERN_HEADER_USER_CARD);
			appendChild(new DomDiv()).appendText(PageHeaderUserPopoverButton.getInitials(user.getFirstAndLastName()));
			DomDiv text = appendChild(new DomDiv());
			text.appendChild(new DomDiv()).appendText(CoreI18n.HELLO + ", " + user.getFirstName());
			text.appendChild(new DomDiv()).appendText(user.getEmailAddress());
		}
	}

	private class LogoutButton extends DomButton {

		public LogoutButton() {

			setIcon(CoreImages.LOGOUT.getResource());
			setLabel(CoreI18n.LOGOUT);
			setClickCallback(this::logout);
			setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION);
			addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER_LOGOUT_BUTTON);
		}

		public void logout() {

			getHttpSession().invalidate();
			getDomEngine().pushBrowserHistoryState(CoreI18n.LOGIN.toString(), new WebServiceUrlBuilder(PageServiceFactory.class).build().getStartingFromPath());
			getDomEngine().reloadPage();
		}
	}

	private static HttpSession getHttpSession() {

		return AjaxDocument//
			.getCurrentDocument()
			.orElseThrow()
			.getHttpSession();
	}
}
