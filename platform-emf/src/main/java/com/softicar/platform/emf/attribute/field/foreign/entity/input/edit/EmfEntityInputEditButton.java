package com.softicar.platform.emf.attribute.field.foreign.entity.input.edit;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.form.popup.EmfFormPopup;

public class EmfEntityInputEditButton<E extends IEmfEntity<E, ?>> extends DomPopupButton {

	private final EmfEntityInput<E> input;
	private E entity;

	public EmfEntityInputEditButton(EmfEntityInput<E> input) {

		this.input = input;
		this.entity = null;
		setIcon(EmfImages.ENTITY_EDIT.getResource());
		setTitle(EmfI18n.EDIT_ENTRY);
		setPopupFactory(FormPopup::new);
		setDisabled(true);
		setTabIndex(-1);
	}

	public void refresh(E entity) {

		this.entity = entity;
		setEnabled(entity != null && isEditAllowed(entity));
	}

	private boolean isEditAllowed(E tableRow) {

		return tableRow//
			.table()
			.getAuthorizer()
			.getEditPermission()
			.test(tableRow, CurrentBasicUser.get());
	}

	private class FormPopup extends EmfFormPopup<E> implements IDomRefreshBusListener {

		public FormPopup() {

			super(entity);
			setDirectEditing(true);
		}

		@Override
		public void refresh(IDomRefreshBusEvent event) {

			if (entity != null && event.isChanged(entity)) {
				input.setValue(entity);
			}
		}
	}
}
