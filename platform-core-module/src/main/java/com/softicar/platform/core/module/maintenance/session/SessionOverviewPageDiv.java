package com.softicar.platform.core.module.maintenance.session;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.data.table.IEmfDataTableActionColumnHandler;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import javax.servlet.http.HttpSession;

public class SessionOverviewPageDiv extends DomDiv {

	private final IEmfDataTableDiv<HttpSession> table;

	public SessionOverviewPageDiv() {

		appendChild(
			new DomActionBar(
				new DomButton()//
					.setClickCallback(new SessionManager()::invalidateAllSessions)
					.setLabel(CoreI18n.LOGOUT_ALL)
					.setIcon(CoreImages.LOGOUT.getResource())
					.setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION)));
		this.table = new EmfDataTableDivBuilder<>(new SessionOverviewTable())//
			.setActionColumnHandler(new ActionColumnHandler())
			.buildAndAppendTo(this);
	}

	private class ActionColumnHandler implements IEmfDataTableActionColumnHandler<HttpSession> {

		@Override
		public void buildCell(IEmfDataTableActionCell<HttpSession> cell, HttpSession session) {

			cell
				.appendChild(
					new DomActionBar(
						new DomButton()//
							.setClickCallback(() -> invalidateSessionAndRefresh(session))
							.setLabel(CoreI18n.LOGOUT)
							.setIcon(CoreImages.LOGOUT.getResource())
							.setConfirmationMessage(CoreI18n.ARE_YOU_SURE_QUESTION)));
		}
	}

	private void invalidateSessionAndRefresh(HttpSession session) {

		session.invalidate();
		table.refresh();
	}
}
