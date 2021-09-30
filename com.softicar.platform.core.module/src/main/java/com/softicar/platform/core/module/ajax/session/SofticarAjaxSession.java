package com.softicar.platform.core.module.ajax.session;

import com.softicar.platform.ajax.session.AjaxSessionAttributeManager;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.impersonation.AGUserImpersonationState;
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
	private Optional<DbObjectProxy<AGUserImpersonationState>> userImpersonationState;

	public SofticarAjaxSession(AGAjaxSession session, AGUser user) {

		this.session = new DbObjectProxy<>(session);
		this.user = new DbObjectProxy<>(user);
		this.userImpersonationState = Optional.empty();
	}

	public static Optional<SofticarAjaxSession> getInstance(HttpSession session) {

		return new AjaxSessionAttributeManager(session).getInstance(SofticarAjaxSession.class);
	}

	public boolean isSuperUser() {

		return user.get().isSuperUser();
	}

	public LanguageEnum getLanguage() {

		return user.get().getLanguageEnum();
	}

	public AGUser getUser() {

		return user.get();
	}

	public AGAjaxSession getSession() {

		return session.get();
	}

	public Optional<AGUserImpersonationState> getUserImpersonationState() {

		return userImpersonationState.map(DbObjectProxy::get);
	}

	public void setUserImpersonationState(AGUserImpersonationState state) {

		this.userImpersonationState = Optional.of(new DbObjectProxy<>(state));
	}
}
