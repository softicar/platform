package com.softicar.platform.emf.attribute.field.integer;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.field.AbstractEmfParsedInput;
import com.softicar.platform.emf.attribute.input.IEmfStringInputNode;

public class EmfIntegerInput extends AbstractEmfParsedInput<Integer> {

	public EmfIntegerInput() {

		super(//
			ChangeListeningIntegerInput::new,
			IntegerParser::parseInteger,
			EmfI18n.INTEGER);
	}

	private static class ChangeListeningIntegerInput extends DomIntegerInput implements IEmfStringInputNode {

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
