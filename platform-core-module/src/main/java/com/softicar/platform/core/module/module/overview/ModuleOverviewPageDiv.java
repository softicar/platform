package com.softicar.platform.core.module.module.overview;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.event.panic.AGModulePanicReceiver;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.entity.table.overview.EmfTableOverviewPopup;
import com.softicar.platform.emf.management.EmfManagementButton;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.permission.EmfModulePermissionViewButton;

public class ModuleOverviewPageDiv extends DomDiv {

	public ModuleOverviewPageDiv() {

		ModuleOverviewTable table = new ModuleOverviewTable();
		appendChild(
			new EmfDataTableDivBuilder<>(table)//
				.setActionColumnHandler(this::buildActionCell)
				.setColumnHandler(table.getPanicReceiversColumn(), new PanicReceiversColumnHandler())
				.setOrderBy(table.getNameColumn(), OrderDirection.ASCENDING)
				.build());
	}

	private <M extends IEmfModule<?>> void buildActionCell(IEmfDataTableActionCell<M> cell, M module) {

		cell.appendChild(new ShowActionPopoverButton(cell, module));
	}

	private class ShowActionPopoverButton extends DomButton {

		public <M extends IEmfModule<?>> ShowActionPopoverButton(IEmfDataTableActionCell<M> cell, M module) {

			addCssClass(EmfCssClasses.EMF_MANAGEMENT_ACTIONS_BUTTON);
			setIcon(EmfImages.MANAGEMENT_ACTIONS.getResource());
			setClickCallback(() -> new ActionPopover(cell, module).open());
		}
	}

	private class ActionPopover extends DomPopover {

		public <M extends IEmfModule<?>> ActionPopover(IEmfDataTableActionCell<M> cell, M module) {

			addCssClass(EmfCssClasses.EMF_MANAGEMENT_ACTIONS_POPOVER);
			AGUuid uuid = AGUuid.getOrCreate(module.getAnnotatedUuid());
			appendChild(
				new EmfManagementButton<>(AGModulePanicReceiver.TABLE, uuid)//
					.setRefreshable(cell.getTableRow())
					.setCallbackBeforeOpen(this::close)
					.setLabel(AGModulePanicReceiver.TABLE.getPluralTitle()));
			appendChild(
				new DomPopupButton()//
					.setPopupFactory(() -> new EmfTableOverviewPopup(module))
					.setCallbackBeforeOpen(this::close)
					.setIcon(CoreImages.MODULES.getResource())
					.setLabel(EmfI18n.SHOW_TABLES));
			appendChild(
				new EmfModulePermissionViewButton(module)//
					.setCallbackBeforeOpen(this::close));
			appendChild(
				new ModulePageOverviewButton(module)//
					.setCallbackBeforeOpen(this::close));
		}
	}
}
