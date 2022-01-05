package com.softicar.platform.core.module.module.system;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.event.panic.AGModulePanicReceiver;
import com.softicar.platform.core.module.module.page.ModulePageOverviewButton;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.modal.DomPopover;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.entity.table.overview.EmfTableOverviewPopup;
import com.softicar.platform.emf.management.EmfManagementPopup;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.role.EmfModuleRoleViewButton;

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
			setClickCallback(() -> new ActionPopover(cell, module).show());
		}
	}

	private class ActionPopover extends DomPopover {

		public <M extends IEmfModule<?>> ActionPopover(IEmfDataTableActionCell<M> cell, M module) {

			appendChild(
				new DomButton()//
					.setClickCallback(() -> showPanicReceiverPopup(cell, module))
					.setIcon(AGModulePanicReceiver.TABLE.getIcon())
					.setLabel(AGModulePanicReceiver.TABLE.getPluralTitle())
					.setTitle(EmfI18n.MANAGE_ARG1.toDisplay(AGModulePanicReceiver.TABLE.getPluralTitle())));
			appendChild(
				new DomButton()//
					.setIcon(CoreImages.MODULES.getResource())
					.setLabel(EmfI18n.SHOW_TABLES)
					.setClickCallback(() -> {
						hide();
						new EmfTableOverviewPopup(module).show();
					}));
			appendChild(new EmfModuleRoleViewButton(module).setCallbackBeforeShow(this::hide));
			appendChild(new ModulePageOverviewButton(module).setCallbackBeforeShow(this::hide));
		}

		private <M extends IEmfModule<?>> void showPanicReceiverPopup(IEmfDataTableActionCell<M> cell, M module) {

			hide();
			AGUuid uuid = AGUuid.getOrCreate(module.getAnnotatedUuid());
			new EmfManagementPopup<>(AGModulePanicReceiver.TABLE, uuid).setRefreshable(cell.getTableRow()).show();
		}
	}
}
