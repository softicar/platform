package com.softicar.platform.core.module.maintenance.session;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.data.table.IEmfDataTableActionColumnHandler;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import javax.servlet.http.HttpSession;

public class SessionsPageDiv extends DomDiv {

	private final IEmfDataTableDiv<HttpSession> table;

	public SessionsPageDiv() {

		var actionBar = appendChild(new DomActionBar());
		this.table = new EmfDataTableDivBuilder<>(new SessionsTable())//
			.setActionColumnHandler(new ActionColumnHandler())
			.buildAndAppendTo(this);

		actionBar
			.appendChild(
				new DomButton()//
					.setClickCallback(table::refresh)
					.setIcon(EmfImages.REFRESH.getResource())
					.setLabel(EmfI18n.REFRESH));

		actionBar
			.appendChild(
				new DomButton()//
					.setClickCallback(new SessionManager()::invalidateAllSessions)
					.setIcon(CoreImages.TERMINATE.getResource())
					.setLabel(CoreI18n.TERMINATE_ALL_SESSIONS)
					.setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION));
	}

	private class ActionColumnHandler implements IEmfDataTableActionColumnHandler<HttpSession> {

		@Override
		public void buildCell(IEmfDataTableActionCell<HttpSession> cell, HttpSession session) {

			cell
				.appendChild(
					new DomActionBar(
						new DomButton()//
							.setClickCallback(() -> invalidateSessionAndRefresh(session))
							.setTitle(CoreI18n.TERMINATE_SESSION)
							.setIcon(CoreImages.TERMINATE.getResource())
							.setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION)));
		}
	}

	private void invalidateSessionAndRefresh(HttpSession session) {

		session.invalidate();
		table.refresh();
	}
}
