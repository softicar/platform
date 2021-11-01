package com.softicar.platform.core.module.user.activity;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.event.AGAjaxEventTypeEnum;
import com.softicar.platform.core.module.user.activity.IUserActivityQuery.IRow;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFieldset;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.styles.CssDisplay;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;

class UserActivityPageDiv extends DomDiv {

	private final DaysInput userActivityInputForm;
	private final DomDiv userActivityContainer;

	public UserActivityPageDiv() {

		this.userActivityInputForm = new DaysInput(this::refresh);
		this.userActivityContainer = new DomDiv();
		this.userActivityContainer
			.appendChild(new DomMessageDiv(DomMessageType.INFO, CoreI18n.SHOWS_USERS_THAT_HAVE_NOT_OPENED_A_PAGE_IN_THE_GIVEN_TIME_PERIOD));

		DomDiv containerDiv = new DomDiv();
		containerDiv.setStyle(CssDisplay.TABLE);
		appendChild(containerDiv);

		containerDiv.appendChild(new RecentUserActivityFieldset());
	}

	private void refresh() {

		int numDays = userActivityInputForm.getNumDays();
		DayTime threshold = Day.today().getRelative(-numDays).getBegin();
		IUserActivityQuery query = IUserActivityQuery.FACTORY.createQuery().setThreshold(threshold).setType(AGAjaxEventTypeEnum.DOCUMENT_CREATION.getRecord());
		RecentUserActivityTableDivBuilder builder = new RecentUserActivityTableDivBuilder(query);

		userActivityContainer.removeChildren();
		userActivityContainer.appendChild(builder.build());
	}

	private class RecentUserActivityFieldset extends DomFieldset {

		public RecentUserActivityFieldset() {

			if (userActivityInputForm != null) {
				appendChild(userActivityInputForm);
			}
			appendChild(new DomActionBar(new RefreshActivityButton()));
			appendChild(userActivityContainer);
		}

		private class RefreshActivityButton extends DomButton {

			public RefreshActivityButton() {

				setIcon(EmfImages.REFRESH.getResource());
				setLabel(CoreI18n.REFRESH);
				setClickCallback(this::handleClick);
			}

			public void handleClick() {

				refresh();
			}
		}
	}

	private class RecentUserActivityTableDivBuilder extends EmfDataTableDivBuilder<IRow> {

		public RecentUserActivityTableDivBuilder(IDataTable<IRow> dataTable) {

			super(dataTable);

			setColumnHandler(IUserActivityQuery.DAYS_AGO_COLUMN, new DaysAgoColumnHandler());

			setColumnTitle(IUserActivityQuery.ID_COLUMN, CoreI18n.ID);
			setColumnTitle(IUserActivityQuery.LOGIN_NAME_COLUMN, CoreI18n.LOGIN_NAME);
			setColumnTitle(IUserActivityQuery.USER_NAME_COLUMN, CoreI18n.USER_NAME);
			setColumnTitle(IUserActivityQuery.EMAIL_ADDRESS_COLUMN, CoreI18n.EMAIL_ADDRESS);
			setColumnTitle(IUserActivityQuery.LAST_PAGE_CREATION_COLUMN, CoreI18n.LAST_PAGE_CREATION);
			setColumnTitle(IUserActivityQuery.DAYS_AGO_COLUMN, CoreI18n.DAYS_PASSED);
		}

		private class DaysAgoColumnHandler extends EmfDataTableValueBasedColumnHandler<DayTime> {

			@Override
			public void buildCell(IEmfDataTableCell<?, DayTime> cell, DayTime value) {

				if (value != null) {
					cell.appendChild(Day.today().minus(value.getDay()));
				}
			}
		}
	}
}
