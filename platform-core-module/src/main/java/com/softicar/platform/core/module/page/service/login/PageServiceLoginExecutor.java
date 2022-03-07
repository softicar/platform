package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.ajax.request.CurrentAjaxRequest;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.session.AjaxSessionAttributeManager;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.core.module.ajax.logging.AjaxLogging;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import java.util.Optional;
import javax.servlet.http.HttpSession;

class PageServiceLoginExecutor {

	private final IAjaxRequest request;
	private final HttpSession session;
	private final String username;
	private final String password;

	public PageServiceLoginExecutor(String username, String password) {

		IDomDocument document = CurrentDomDocument.get();
		Optional<IAjaxRequest> ajaxRequest = CurrentAjaxRequest.get(document);
		this.request = ajaxRequest.get();
		this.session = request.getHttpSession();
		this.username = username;
		this.password = password;
	}

	public void executeLogin() {

		AGUser user = new PageServiceLoginAuthenticator(request, username, password).authenticate();

		new AjaxSessionAttributeManager(session).setInstance(SofticarAjaxSession.class, createSession(user));

		CurrentUser.set(user);
		CurrentLocale.set(user.getLocale());
	}

	private SofticarAjaxSession createSession(AGUser user) {

		return new SofticarAjaxSession(AjaxLogging.logSessionCreation(request, user), user);
	}
}
