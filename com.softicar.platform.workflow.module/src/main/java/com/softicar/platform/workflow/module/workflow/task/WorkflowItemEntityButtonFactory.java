package com.softicar.platform.workflow.module.workflow.task;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.editor.EmfEditorPopupButton;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;

public class WorkflowItemEntityButtonFactory {

	private final AGWorkflowItem workflowItem;

	public WorkflowItemEntityButtonFactory(AGWorkflowItem workflowItem) {

		this.workflowItem = workflowItem;
	}

	public <E extends IEmfEntity<E, ?>> DomPopupButton createEditButton() {

		return new EmfEditorPopupButton<E>(getEntity());
	}

	public <E extends IEmfEntity<E, ?>> DomButton createShowButton() {

		return new DomPopupButton()//
			.setPopupFactory(() -> new EmfFormPopup<E>(getEntity()))
			.setIcon(EmfImages.ENTITY_VIEW.getResource())
			.setTitle(EmfI18n.VIEW);
	}

	private <E extends IEmfEntity<E, ?>> E getEntity() {

		// TODO this casting is ugly
		return CastUtils.cast(workflowItem.getEntityOrThrow());
	}
}
