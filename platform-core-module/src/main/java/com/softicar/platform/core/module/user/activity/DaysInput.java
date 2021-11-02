package com.softicar.platform.core.module.user.activity;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomEventHandler;

class DaysInput extends DomDiv {

	private static final int DEFAULT_NUMBER_OF_DAYS = 30;
	private final INullaryVoidFunction callback;
	private final DomIntegerInput numDaysInput;

	public DaysInput(INullaryVoidFunction callback) {

		this.callback = callback;
		this.numDaysInput = new ListeningTextInput(DEFAULT_NUMBER_OF_DAYS);

		appendChild(new DomLabelGrid().add(CoreI18n.NUMBER_OF_DAYS, this.numDaysInput));
	}

	public int getNumDays() {

		Integer numDays = this.numDaysInput.getInteger();

		if (numDays != null && numDays >= 0) {
			return numDays;
		} else {
			throw new SofticarUserException(CoreI18n.PLEASE_SPECIFY_A_NON_NEGATIVE_NUMBER_OF_DAYS);
		}
	}

	private class ListeningTextInput extends DomIntegerInput implements IDomEventHandler {

		public ListeningTextInput(int value) {

			super(value);
			listenToEvent(DomEventType.ENTER);
		}

		@Override
		public void handleDOMEvent(IDomEvent event) {

			callback.apply();
		}
	}
}
