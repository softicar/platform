package com.softicar.platform.core.module.page.header;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.ajax.session.reset.AjaxSessionPage;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.service.PageServiceFactory;
import com.softicar.platform.core.module.user.profile.UserProfilePage;
import com.softicar.platform.core.module.web.service.WebServiceUrlBuilder;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.popover.action.DomMenuPopover;
import com.softicar.platform.dom.elements.popup.position.strategy.DomPopupBelowEventNodePositionStrategy;
import com.softicar.platform.emf.page.IEmfPage;
import javax.servlet.http.HttpSession;

class PageHeaderUserPopover extends DomMenuPopover {

	private final PageNavigationController controller;

	public PageHeaderUserPopover(PageNavigationController controller) {

		this.controller = controller;
		this.configuration.setPositionStrategy(new DomPopupBelowEventNodePositionStrategy());
		addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER);

		appendChild(new CoreModulePageButton(UserProfilePage.class)).addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER_PROFILE_PAGE_BUTTON);
		appendChild(new CoreModulePageButton(AjaxSessionPage.class)).addMarker(CoreTestMarker.PAGE_HEADER_USER_POPOVER_SESSION_PAGE_BUTTON);
		appendChild(new LogoutButton());
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
