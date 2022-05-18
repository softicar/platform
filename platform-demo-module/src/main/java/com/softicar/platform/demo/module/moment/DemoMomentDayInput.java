package com.softicar.platform.demo.module.moment;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.module.core.module.DemoI18n;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.attribute.field.day.EmfDayInput;

public class DemoMomentDayInput extends EmfDayInput {

	public DemoMomentDayInput() {

		appendChild(new SetCurrentDayButton());
	}

	private class SetCurrentDayButton extends DomButton {

		public SetCurrentDayButton() {

			setLabel(DemoI18n.TODAY);
			setClickCallback(this::setToCurrentDay);
		}

		private void setToCurrentDay() {

			setValueAndHandleChangeCallback(Day.today());
		}
	}
}
