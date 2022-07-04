package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomPre;
import com.softicar.platform.dom.elements.tables.DomDataTable;

public class CronSyntaxDiv extends DomDiv {

	public CronSyntaxDiv() {

		appendNewChild(DomElementTag.H4).appendText(CoreI18n.GENERAL_STRUCTURE);
		appendChild(new StructureBox());
		appendNewChild(DomElementTag.H4).appendText(CoreI18n.ALLOWED_VALUES);
		appendChild(new AllowedValuesTable());
		appendNewChild(DomElementTag.H4).appendText(CoreI18n.EXAMPLES);
		appendChild(new ExamplesTable());
	}

	private static class StructureBox extends DomPre {

		public StructureBox() {

			appendText("┌───────────── " + CoreI18n.MINUTE + " (0 - 59)\n");
			appendText("│ ┌───────────── " + CoreI18n.HOUR + " (0 - 23)\n");
			appendText("│ │ ┌───────────── " + CoreI18n.DAY_OF_MONTH + " (1 - 31)\n");
			appendText("│ │ │ ┌───────────── " + CoreI18n.MONTH + " (1 - 12)\n");
			appendText("│ │ │ │ ┌───────────── " + CoreI18n.WEEKDAY + " (0 - 7)\n");
			appendText("│ │ │ │ │ \n");
			appendText("* * * * * ");
		}
	}

	private static class AllowedValuesTable extends DomDataTable {

		public AllowedValuesTable() {

			getHead().appendRow().appendHeaderCells(CoreI18n.FIELD, CoreI18n.ALLOWED_VALUES, CoreI18n.ALLOWED_CHARACTERS);
			appendCronCharacterRow(CoreI18n.MINUTE, "0-59", " * , - /");
			appendCronCharacterRow(CoreI18n.HOUR, "0-23", " * , - /");
			appendCronCharacterRow(CoreI18n.DAY_OF_MONTH, "1-31", " * , - /");
			appendCronCharacterRow(CoreI18n.MONTH, "1-12 / JAN-DEC", " * , - /");
			appendCronCharacterRow(CoreI18n.WEEKDAY, "0-7 / MON-SUN", " * , - /");
		}

		private void appendCronCharacterRow(IDisplayString field, String allowedValues, String allowedCharacters) {

			getBody()
				.appendRow()
				.appendCells(//
					field,
					IDisplayString.create(allowedValues),
					IDisplayString.create(allowedCharacters));
		}
	}

	private static class ExamplesTable extends DomDataTable {

		public ExamplesTable() {

			getHead()
				.appendRow()
				.appendHeaderCells(CoreI18n.MINUTE, CoreI18n.HOUR, CoreI18n.DAY_OF_MONTH, CoreI18n.MONTH, CoreI18n.WEEKDAY, CoreI18n.DESCRIPTION);
			appendCronExampleRow("5-59/20", "*", "*", "*", "*", CoreI18n.FIVE_TWENTY_FIVE_AND_FORTY_FIVE_MINUTES_AFTER_EVERY_FULL_HOUR);
			appendCronExampleRow("*/5", "*", "*", "*", "*", CoreI18n.EVERY_FIVE_MINUTES);
			appendCronExampleRow("5", "*", "*", "*", "*", CoreI18n.FIVE_MINUTES_PAST_EVERY_HOUR);
			appendCronExampleRow("*", "*", "*", "*", "*", CoreI18n.EVERY_MINUTE_AROUND_THE_CLOCK_SEVEN_DAYS_A_WEEK);
			appendCronExampleRow("59", "23", "*", "*", "0", CoreI18n.EVERY_SUNDAY_AT_11_59_PM);
			appendCronExampleRow("20,30", "1", "*", "*", "1-5", CoreI18n.FROM_MONDAY_TO_FRIDAY_AT_1_20_AND_1_30_AM);
			appendCronExampleRow("0", "0", "*", "*", "*", CoreI18n.EVERY_DAY_AT_MIDNIGHT);

		}

		private void appendCronExampleRow(String minute, String hour, String dayOfMonth, String month, String weekday, IDisplayString description) {

			getBody()
				.appendRow()
				.appendCells(
					IDisplayString.create(minute),
					IDisplayString.create(hour),
					IDisplayString.create(dayOfMonth),
					IDisplayString.create(month),
					IDisplayString.create(weekday),
					description);
		}
	}
}
