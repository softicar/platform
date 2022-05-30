package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInput;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;
import java.util.Optional;

public class EmfEnumTableRowInput<R extends IDbEnumTableRow<R, E>, E extends IDbEnumTableRowEnum<E, R>> extends AbstractEmfInputDiv<R> {

	private final EntityInput input;

	public EmfEnumTableRowInput(IDbEnumTable<R, E> targetTable) {

		this.input = appendChild(new EntityInput(targetTable));
	}

	@Override
	public void refreshInputConstraints() {

		input.refreshFilters();
	}

	@Override
	public Optional<R> getValue() {

		return input.getValue().map(EmfEnumTableRowEntityWrapper::getRow);
	}

	@Override
	public void setMandatory(boolean mandatory) {

		input.getConfiguration().setMandatory(mandatory);
	}

	@Override
	public void setValueAndHandleChangeCallback(R value) {

		setValue(value);
		input.applyChangeCallback();
	}

	@Override
	public void setValue(R row) {

		input.setValue(EmfEnumTableRowEntityWrapper.wrap(row));
	}

	@Override
	public void setChangeCallback(INullaryVoidFunction callback) {

		input.setChangeCallback(callback);
	}

	@Override
	public EmfEnumTableRowInput<R, E> setDisabled(boolean disabled) {

		input.setDisabled(disabled);
		return this;
	}

	@Override
	public boolean isDisabled() {

		return input.isDisabled();
	}

	private class EntityInput extends DomAutoCompleteEntityInput<EmfEnumTableRowEntityWrapper<R, E>> {

		public EntityInput(IDbEnumTable<R, E> targetTable) {

			super(new EmfEnumTableRowInputEngine<>(targetTable));
		}
	}
}
