package com.softicar.platform.core.module.ajax.session;

import com.softicar.platform.ajax.session.AjaxSessionAttributeManager;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.object.DbObjectProxy;
import java.util.Optional;
import javax.servlet.http.HttpSession;

/**
 * An instance of this class is saved as an attribute of the
 * {@link HttpSession}.
 *
 * @author Oliver Richers
 */
public final class SofticarAjaxSession {

	private final DbObjectProxy<AGAjaxSession> session;
	private final DbObjectProxy<AGUser> user;

	public SofticarAjaxSession(AGAjaxSession session, AGUser user) {

		this.session = new DbObjectProxy<>(session);
		this.user = new DbObjectProxy<>(user);
	}

	public static Optional<SofticarAjaxSession> getInstance(HttpSession session) {

		return new AjaxSessionAttributeManager(session).getInstance(SofticarAjaxSession.class);
	}

	public LanguageEnum getLanguage() {

		return user.get().getLocale().getLanguage();
	}

	public AGUser getUser() {

		return user.get();
	}

	public AGAjaxSession getSession() {

		return session.get();
	}
}
