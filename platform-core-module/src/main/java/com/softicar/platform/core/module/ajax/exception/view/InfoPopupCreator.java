package com.softicar.platform.core.module.ajax.exception.view;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.session.AGAjaxSession;
import com.softicar.platform.core.module.ajax.session.revision.AGAjaxSessionRevision;
import com.softicar.platform.dom.elements.DomPre;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.popup.DomPopup;

class InfoPopupCreator {

	public static DomPopup createForSession(AGAjaxSession session) {

		AGAjaxSessionRevision revision = AGAjaxSessionRevision.TABLE.createSelect().where(AGAjaxSessionRevision.SESSION.equal(session)).getFirst();
		DomPopup popup = new DomPopup();
		popup.setCaption(CoreI18n.SESSION_INFORMATION);
		popup//
			.appendChild(new DomLabelGrid())
			.add(CoreI18n.SESSION_ID, session.getId().toString())
			.add(CoreI18n.USER, session.getUser().toDisplay())
			.add(CoreI18n.REMOTE_ADDRESS, session.getClientIpAddress())
			.add(CoreI18n.LOCAL_NAME, session.getLocalName())
			.add(CoreI18n.LOCAL_ADDRESS, session.getLocalAddress())
			.add(CoreI18n.LOCAL_PORT, session.getLocalPort().toString())
			.add(CoreI18n.ACCESS_DATE, session.getAccessDate().toGermanString())
			.add(CoreI18n.REVISION, revision == null? "" : revision.getRevision());
		popup.appendCloseButton();
		return popup;
	}

	public static DomPopup createForStackTrace(String stackTrace) {

		DomPopup popup = new DomPopup();
		popup.setCaption(CoreI18n.STACK_TRACE);
		DomPre domPre = popup.appendChild(new DomPre());
		domPre.appendText(stackTrace);
		popup.appendCloseButton();
		return popup;

	}
}
