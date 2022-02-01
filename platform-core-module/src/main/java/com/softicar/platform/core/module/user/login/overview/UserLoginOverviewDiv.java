package com.softicar.platform.core.module.user.login.overview;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.login.overview.IUserLoginOverviewQuery.IRow;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

class UserLoginOverviewDiv extends DomDiv {

	private final DomDiv container;

	public UserLoginOverviewDiv() {

		this.container = new DomDiv();
		appendChild(
			new DomActionBar(
				new DomButton()//
					.setIcon(EmfImages.REFRESH.getResource())
					.setLabel(CoreI18n.REFRESH)
					.setClickCallback(this::refresh)));
		appendChild(container);
		refresh();
	}

	private void refresh() {

		container.removeChildren();
		container.appendChild(new UserLoginOverviewTableDivBuilder().build());
	}

	private class UserLoginOverviewTableDivBuilder extends EmfDataTableDivBuilder<IRow> {

		public UserLoginOverviewTableDivBuilder() {

			super(IUserLoginOverviewQuery.FACTORY.createQuery());

			setColumnHandler(IUserLoginOverviewQuery.DAYS_PASSED_COLUMN, new DaysPassedColumnHandler());
			setColumnTitle(IUserLoginOverviewQuery.ID_COLUMN, CoreI18n.ID);
			setColumnTitle(IUserLoginOverviewQuery.LOGIN_NAME_COLUMN, CoreI18n.LOGIN_NAME);
			setColumnTitle(IUserLoginOverviewQuery.USER_NAME_COLUMN, CoreI18n.USER_NAME);
			setColumnTitle(IUserLoginOverviewQuery.EMAIL_ADDRESS_COLUMN, CoreI18n.EMAIL_ADDRESS);
			setColumnTitle(IUserLoginOverviewQuery.LAST_LOGIN_AT_COLUMN, CoreI18n.LAST_LOGIN_AT);
			setColumnTitle(IUserLoginOverviewQuery.DAYS_PASSED_COLUMN, CoreI18n.DAYS_PASSED);
		}

		private class DaysPassedColumnHandler extends EmfDataTableValueBasedColumnHandler<DayTime> {

			@Override
			public void buildCell(IEmfDataTableCell<?, DayTime> cell, DayTime value) {

				if (value != null) {
					cell.appendChild(Day.today().minus(value.getDay()));
				}
			}
		}
	}
}
