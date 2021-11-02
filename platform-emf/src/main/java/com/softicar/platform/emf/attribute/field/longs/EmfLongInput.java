package com.softicar.platform.emf.attribute.field.longs;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.number.parser.LongParser;
import com.softicar.platform.dom.elements.input.DomLongInput;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.field.AbstractEmfParsedInput;
import com.softicar.platform.emf.attribute.input.IEmfStringInputNode;

public class EmfLongInput extends AbstractEmfParsedInput<Long> {

	public EmfLongInput() {

		super(//
			ChangeListeningLongInput::new,
			LongParser::parseLong,
			EmfI18n.LONG_INTEGER);
	}

	private static class ChangeListeningLongInput extends DomLongInput implements IEmfStringInputNode {

		private INullaryVoidFunction callback;

		@Override
		public void setChangeCallback(INullaryVoidFunction callback) {

			this.callback = callback;
		}

		@Override
		public void handleChange(IDomEvent event) {

			if (callback != null) {
				callback.apply();
			}
		}
	}
}
