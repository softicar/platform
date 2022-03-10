package com.softicar.platform.dom.elements.time.day;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.Month;
import com.softicar.platform.common.date.MonthName;
import com.softicar.platform.common.date.Week;
import com.softicar.platform.common.date.Weekday;
import com.softicar.platform.common.date.Year;
import com.softicar.platform.common.math.Range;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.AbstractDomValueSelect;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTBody;
import com.softicar.platform.dom.elements.DomTHead;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;
import java.util.function.Consumer;

/**
 * A {@link DomDiv} for choosing a day.
 *
 * @author Oliver Richers
 */
public class DomDayChooserDiv extends DomDiv {

	public static final int MINIMUM_YEAR = 1970;
	public static final int YEARS_INTO_THE_FUTURE = 20;
	private Day selectedDay;
	private final YearSelect yearSelect;
	private final MonthSelect monthSelect;
	private DayTable table;
	private Consumer<Day> dayConsumer = Consumers.noOperation();

	public DomDayChooserDiv(Day selectedDay) {

		this.selectedDay = selectedDay == null? Day.today() : selectedDay;
		this.yearSelect = new YearSelect();
		this.monthSelect = new MonthSelect();
		appendChild(new DomActionBar(yearSelect, monthSelect, new TodayButton()));
		this.table = appendChild(new DayTable());

		setCssClass(DomElementsCssClasses.DOM_DAY_CHOOSER_DIV);
	}

	public void setDay(Day day) {

		if (day != null) {
			selectedDay = day;
			updateAll();
		}
	}

	public Day getDay() {

		return selectedDay;
	}

	public DomDayChooserDiv setDayConsumer(Consumer<Day> dayConsumer) {

		this.dayConsumer = dayConsumer;
		return this;
	}

	private class DayTable extends DomTable {

		public DayTable() {

			DomTHead head = appendChild(new DomTHead());
			DomTBody body = appendChild(new DomTBody());
			// create header row
			DomRow headerRow = head.appendRow();
			headerRow.appendHeaderCell(DomI18n.WEEK);
			for (Weekday weekday: Weekday.values()) {
				headerRow.appendHeaderCell(weekday.toShortDisplay());
			}

			// compute first week
			Month month = selectedDay.getMonth();
			Day firstDay = month.getDays().getFirst();
			Week firstWeek = firstDay.getWeek();
			if (firstDay.getWeekday() == Weekday.MONDAY) {
				firstWeek = firstWeek.getPrevious();
			}

			// create table rows
			Week week = firstWeek;
			for (int i = 0; i != 6; ++i, week = week.getNext()) {
				DomRow row = body.appendRow();
				row.appendHeaderCell("" + week.getIndexWithinYear());
				for (Weekday weekday: Weekday.values()) {
					row.appendChild(new DayCell(week.getDay(weekday)));
				}
			}
		}

		private class DayCell extends DomCell implements IDomEventHandler {

			private final Day day;

			public DayCell(Day day) {

				this.day = day;
				appendText("" + day.getIndexWithinMonth());

				if (!selectedDay.getMonth().getDays().contains(day)) {
					// do nothing
				} else if (day.isSaturday() || day.isSunday()) {
					setCssClass(DomElementsCssClasses.DOM_DAY_CHOOSER_DIV_WEEKEND);
				} else {
					setCssClass(DomElementsCssClasses.DOM_DAY_CHOOSER_DIV_WORKDAY);
				}
				if (day.equals(selectedDay)) {
					addCssClass(DomCssPseudoClasses.SELECTED);
				}
				listenToEvent(DomEventType.CLICK);
			}

			@Override
			public void handleDOMEvent(IDomEvent event) {

				selectedDay = day;
				updateAll();
				dayConsumer.accept(day);
			}
		}
	}

	protected void updateDayTable() {

		table = replaceChild(new DayTable(), table);
	}

	private void updateAll() {

		yearSelect.setSelectedValue(selectedDay.getYear());
		monthSelect.setSelectedValue(selectedDay.getMonth().getName());
		updateDayTable();
	}

	private class MonthSelect extends AbstractDomValueSelect<MonthName> implements IDomEventHandler {

		public MonthSelect() {

			addValues(MonthName.values());
			setSelectedValue(selectedDay.getMonth().getName());
			listenToEvent(DomEventType.CHANGE);
		}

		@Override
		protected IDisplayString getValueDisplayString(MonthName value) {

			return value.toDisplay();
		}

		@Override
		protected Integer getValueId(MonthName value) {

			return value.ordinal();
		}

		@Override
		public void handleDOMEvent(IDomEvent event) {

			Year year = selectedDay.getYear();
			Month month = year.getMonth(getSelectedValue());
			int index = selectedDay.getIndexWithinMonth();
			if (index <= month.getDays().size()) {
				selectedDay = month.getDays().get(index);
				updateDayTable();
			} else {
				selectedDay = month.getDays().get(month.getDays().size());
				updateDayTable();
			}
		}
	}

	private class YearSelect extends AbstractDomValueSelect<Year> implements IDomEventHandler {

		private final Range<Year> range = new Range<>();

		public YearSelect() {

			setSelectedValue(selectedDay.getYear());
			listenToEvent(DomEventType.CHANGE);
		}

		@Override
		protected IDisplayString getValueDisplayString(Year value) {

			return IDisplayString.create(value.toString());
		}

		@Override
		protected Integer getValueId(Year value) {

			return value.getAbsoluteIndex();
		}

		@Override
		public void setSelectedValue(Year year) {

			updateRange(year);
			super.setSelectedValue(year);
		}

		@Override
		public void handleDOMEvent(IDomEvent event) {

			Year year = getSelectedValue();
			Month month = year.getMonth(selectedDay.getMonth().getName());
			int index = selectedDay.getIndexWithinMonth();
			if (index <= month.getDays().size()) {
				selectedDay = month.getDays().get(index);
			} else {
				selectedDay = month.getLastDayInMonth();
			}
			updateDayTable();
		}

		private void updateRange(Year year) {

			int min = Math.min(year.getAbsoluteIndex(), MINIMUM_YEAR);
			int max = Math.max(year.getAbsoluteIndex(), Year.thisYear().getAbsoluteIndex() + YEARS_INTO_THE_FUTURE);

			Year minYear = Year.get(min);
			Year maxYear = Year.get(max);

			if (!range.contains(minYear) || !range.contains(maxYear)) {
				range.add(minYear);
				range.add(maxYear);
				removeValues();
				for (Year y = range.getMin(); y.compareTo(range.getMax()) <= 0; y = y.getNext()) {
					addValue(y);
				}
			}
		}
	}

	private class TodayButton extends DomButton {

		public TodayButton() {

			setIcon(DomElementsImages.CALENDAR_TODAY.getResource());
			setLabel(DomI18n.TODAY);
			setClickCallback(this::setToCurrentDay);
		}

		private void setToCurrentDay() {

			setDay(Day.today());
			dayConsumer.accept(selectedDay);
		}
	}
}
