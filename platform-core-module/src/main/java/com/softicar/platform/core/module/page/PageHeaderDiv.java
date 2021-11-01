package com.softicar.platform.core.module.page;

import com.softicar.platform.ajax.event.AjaxDomEvent;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.page.navigation.PageNavigationToggleButton;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.text.DomTextNode;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;

class PageHeaderDiv<I extends IEmfModuleInstance<I>> extends DomDiv {

	private final IEmfPage<I> page;
	private final I moduleInstance;
	private final IEmfModule<I> module;

	public PageHeaderDiv(PageNavigationLink<I> link, INullaryVoidFunction navigationToggleFunction) {

		this.page = link.getPage();
		this.moduleInstance = link.getModuleInstance();
		this.module = CastUtils.cast(moduleInstance.getModuleOrThrow());
		setCssClass(PageCssClasses.PAGE_HEADER_DIV);
		appendChild(new PageNavigationToggleButton(navigationToggleFunction));
		appendChild(new PagePathDiv());
		appendChild(new UserDisplay());
		appendChild(new DomDiv());
		appendChild(new LogoutButton());
	}

	private class PagePathDiv extends DomDiv {

		public PagePathDiv() {

			setCssClass(PageCssClasses.PAGE_HEADER_PATH_DIV);

			EmfPagePath pagePath = page.getPagePath(module.getDefaultPagePath(moduleInstance));
			for (String segment: pagePath.getSegments()) {
				appendChild(DomTextNode.create(segment));
				appendChild(new Separator());
			}
			appendChild(new PageName(page.getTitle().toString()));
		}
	}

	private class Separator extends DomDiv {

		public Separator() {

			setCssClass(PageCssClasses.PAGE_HEADER_SEPARATOR);
			appendText(">>");
		}
	}

	private class PageName extends DomDiv {

		public PageName(String name) {

			setCssClass(PageCssClasses.PAGE_HEADER_PAGE_NAME);
			appendText(name);
		}
	}

	private class UserDisplay extends DomDiv {

		public UserDisplay() {

			setCssClass(PageCssClasses.PAGE_HEADER_USER_DISPLAY);
			appendText(CurrentUser.get().toDisplayWithoutId());
		}
	}

	private class LogoutButton extends DomButton {

		public LogoutButton() {

			setIcon(CoreImages.LOGOUT.getResource());
			setTitle(CoreI18n.LOGOUT);
			setClickCallback(this::logout);
			setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION);
		}

		public void logout() {

			getAjaxRequest().getHttpSession().invalidate();
			getDomEngine().reloadPage();
		}
	}

	private static IAjaxRequest getAjaxRequest() {

		IDomEvent event = CurrentDomDocument.get().getCurrentEvent();
		return ((AjaxDomEvent) event).getAjaxRequest();
	}
}
