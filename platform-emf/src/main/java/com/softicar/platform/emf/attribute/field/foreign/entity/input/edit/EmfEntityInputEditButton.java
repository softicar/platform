package com.softicar.platform.emf.attribute.field.foreign.entity.input.edit;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import java.util.Optional;

public class EmfEntityInputEditButton<E extends IEmfEntity<E, ?>> extends DomPopupButton {

	private final EmfEntityInput<E> input;
	private E entity;

	public EmfEntityInputEditButton(EmfEntityInput<E> input) {

		this.input = input;
		this.entity = null;
		setIcon(EmfImages.ENTITY_EDIT.getResource());
		setTitle(EmfI18n.EDIT_ENTRY);
		setDisabled(true);
	}

	public void refresh(Optional<E> entityOptional) {

		if (entityOptional.isPresent()) {
			entity = entityOptional.get();
			setPopupFactory(FormPopup::new);
			setDisabled(!isEditAllowed(entity));
		} else {
			setPopupFactory(() -> null);
			setClickCallback(INullaryVoidFunction.NO_OPERATION);
			setDisabled(true);
		}
	}

	private boolean isEditAllowed(E tableRow) {

		return tableRow//
			.table()
			.getAuthorizer()
			.getEditPermission()
			.test(tableRow, CurrentBasicUser.get());
	}

	private class FormPopup extends EmfFormPopup<E> {

		public FormPopup() {

			super(entity);
			configuration.setCallbackBeforeClose(this::refreshInput);
		}

		private void refreshInput() {

			input.setValue(entity);
		}
	}
}
