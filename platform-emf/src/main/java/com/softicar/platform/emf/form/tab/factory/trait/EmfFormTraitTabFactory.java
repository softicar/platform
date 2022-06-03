package com.softicar.platform.emf.form.tab.factory.trait;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.tab.factory.IEmfFormTabFactory;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.trait.EmfTraitTab;
import com.softicar.platform.emf.trait.IEmfTrait;
import com.softicar.platform.emf.trait.table.IEmfTraitTable;

public class EmfFormTraitTabFactory<T extends IEmfTrait<T, R>, R extends IEmfTableRow<R, ?>> implements IEmfFormTabFactory<R> {

	private final IEmfTraitTable<T, R> traitTable;

	public EmfFormTraitTabFactory(IEmfTraitTable<T, R> traitTable) {

		this.traitTable = traitTable;
	}

	@Override
	public boolean isVisible(R tableRow) {

		if (tableRow.impermanent()) {
			return false;
		} else {
			T trait = traitTable.load(tableRow);
			if (trait != null) {
				return traitTable.getAuthorizer().getViewRole().test(trait, CurrentBasicUser.get());
			} else {
				return traitTable.isCreationPossible(tableRow) && traitTable.isCreationAllowed(tableRow);
			}
		}
	}

	@Override
	public DomTab createTab(IEmfForm<R> form) {

		return new EmfTraitTab<>(form, traitTable);
	}
}
