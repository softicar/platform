package com.softicar.platform.emf.attribute.field.enums.table.row;

import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInput;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class EmfEnumTableRowInput<R extends IDbEnumTableRow<R, E>, E extends IDbEnumTableRowEnum<E, R>> extends AbstractDomValueInputDiv<R>
		implements IEmfInput<R> {

	private final EntityInput input;

	public EmfEnumTableRowInput(IDbEnumTable<R, E> targetTable) {

		this.input = new EntityInput(targetTable);
		this.input.addChangeCallback(this::executeChangeCallbacks);

		appendChild(input);
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
	public void setValue(R row) {

		input.setValue(EmfEnumTableRowEntityWrapper.wrap(row));
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		input.setDisabled(disabled);
	}

	private class EntityInput extends DomAutoCompleteEntityInput<EmfEnumTableRowEntityWrapper<R, E>> {

		public EntityInput(IDbEnumTable<R, E> targetTable) {

			super(new EmfEnumTableRowInputEngine<>(targetTable));
		}
	}
}
