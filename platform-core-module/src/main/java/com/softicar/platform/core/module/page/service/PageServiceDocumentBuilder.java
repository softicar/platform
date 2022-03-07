package com.softicar.platform.core.module.page.service;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import com.softicar.platform.core.module.page.PageDiv;
import com.softicar.platform.core.module.page.PageResources;
import com.softicar.platform.core.module.page.navigation.PageNavigationResources;
import com.softicar.platform.core.module.page.service.login.PageServiceLoginDiv;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import java.util.Optional;

public class PageServiceDocumentBuilder {

	private final IAjaxDocument document;

	public PageServiceDocumentBuilder(IAjaxDocument document) {

		this.document = document;
	}

	public void build() {

		buildBody();
	}

	public void refreshBody() {

		clearBody();
		buildBody();
	}

	private void clearBody() {

		document.getBody().removeChildren();
	}

	private void buildBody() {

		if (authenticate()) {
			document.appendToBody(new PageDiv(document.getParameters()));
		} else {
			registerCss(PageResources.PAGE_STYLE);
			registerCss(PageNavigationResources.PAGE_NAVIGATION_STYLE);
			document.getEngine().setDocumentTitle(CoreI18n.LOGIN.toString());
			document.appendToBody(new PageServiceLoginDiv(this));
			document.getBody().focusFirst();
		}
	}

	private void registerCss(IResourceSupplier resourceSupplier) {

		document.getEngine().registerCssResourceLink(resourceSupplier.getResource());
	}

	private boolean authenticate() {

		Optional<AGUser> user = getUserFromSession();
		user.ifPresent(this::setupThreadLocals);
		return user.isPresent();
	}

	private Optional<AGUser> getUserFromSession() {

		return SofticarAjaxSession//
			.getInstance(document.getHttpSession())
			.map(SofticarAjaxSession::getUser);
	}

	private void setupThreadLocals(AGUser user) {

		CurrentUser.set(user);
		CurrentLocale.set(user.getLocale());
	}
}
