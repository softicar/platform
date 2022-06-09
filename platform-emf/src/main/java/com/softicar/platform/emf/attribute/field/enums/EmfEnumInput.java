package com.softicar.platform.emf.attribute.field.enums;

import com.softicar.platform.dom.elements.DomEnumSelect;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class EmfEnumInput<E extends Enum<E>> extends AbstractDomValueInputDiv<E> implements IEmfInput<E> {

	private final Select select;

	public EmfEnumInput(Class<E> enumClass) {

		this(enumClass.getEnumConstants());
	}

	@SafeVarargs
	public EmfEnumInput(E...enums) {

		this.select = new Select();
		this.select.addNilValue(EmfI18n.NONE.encloseInBrackets());
		this.select.addValues(enums);
		appendChild(select);
	}

	@Override
	public Optional<E> getValue() {

		return Optional.ofNullable(select.getSelectedValue());
	}

	@Override
	public void setValue(E value) {

		select.setSelectedValue(value);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		select.setDisabled(disabled);
	}

	private class Select extends DomEnumSelect<E> implements IDomChangeEventHandler {

		@Override
		public void handleChange(IDomEvent event) {

			executeChangeCallbacks();
		}
	}
}
