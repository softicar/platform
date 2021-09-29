package com.softicar.platform.emf.form;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

/**
 * Upper part of the {@link EmfFormBody}.
 * <p>
 * The upper part contains the attributes of the entity and its common actions.
 *
 * @author Oliver Richers
 */
class EmfFormBodyUpperPart<R extends IEmfTableRow<R, ?>> extends DomDiv {

	private final IEmfFormBody<R> formBody;
	private EmfAttributesTableContainer<R> attributesContainer;
	private EmfFormCommonActionsDiv<R> commonActionsDiv;
	private boolean editMode;

	public EmfFormBodyUpperPart(IEmfFormBody<R> formBody) {

		this.formBody = formBody;
		this.attributesContainer = null;
		this.commonActionsDiv = null;
		this.editMode = false;

		setCssClass(EmfCssClasses.EMF_FORM_UPPER_BODY_PART);
	}

	public boolean isEditMode() {

		return editMode;
	}

	public void refresh() {

		Optional.ofNullable(attributesContainer).ifPresent(EmfAttributesTableContainer::refresh);
		Optional.ofNullable(commonActionsDiv).ifPresent(EmfFormCommonActionsDiv::refresh);
	}

	public void enterEditMode() {

		Optional.ofNullable(attributesContainer).ifPresent(IDomNode::disappend);
		Optional.ofNullable(commonActionsDiv).ifPresent(IDomNode::disappend);

		this.attributesContainer = new EmfAttributesTableContainer<>(formBody.getTableRow(), true);
		this.attributesContainer.addAdditionalValidators(formBody.getForm().getAdditionalValidators());
		this.commonActionsDiv = null;
		this.editMode = true;

		appendChild(attributesContainer);
	}

	public void enterViewMode() {

		Optional.ofNullable(attributesContainer).ifPresent(IDomNode::disappend);
		Optional.ofNullable(commonActionsDiv).ifPresent(IDomNode::disappend);

		this.attributesContainer = new EmfAttributesTableContainer<>(formBody.getTableRow(), false);
		this.commonActionsDiv = new EmfFormCommonActionsDiv<>(formBody);
		this.editMode = false;

		appendChild(attributesContainer);
		appendChild(commonActionsDiv);
	}

	public void enterViewModeIfNoInputChanged() {

		// TODO Check inputs for changes.
		// Currently we cannot tell if the user changed anything,
		// so we never change from edit mode to view mode.
		if (!isEditMode()) {
			enterViewMode();
		}
	}

	public EmfFormAttributesDiv<R> getAttributesDiv() {

		return attributesContainer.getAttributesDiv();
	}
}
