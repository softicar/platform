package com.softicar.platform.emf.management.prefilter;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.management.EmfManagementDivBuilder;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Set;
import java.util.function.Supplier;

public class EmfPrefilteredManagementDiv<R extends IEmfTableRow<R, P>, P, S> extends DomDiv {

	private final DomDiv tableContainer;
	private final IEmfTable<R, P, S> table;
	private final S scope;

	public EmfPrefilteredManagementDiv(IEmfTable<R, P, S> table, S scope, Supplier<EmfPrefilterPopup<R>> prefilterPopupSupplier) {

		this.table = table;
		this.scope = scope;
		EmfPrefilterPopup<R> prefilterPopup = prefilterPopupSupplier.get();
		prefilterPopup.setPrefilterCallback(this::rebuild);
		appendChild(
			new DomActionBar(
				new DomPopupButton()//
					.setPopupFactory(() -> prefilterPopup)
					.setIcon(DomElementsImages.FILTER.getResource())
					.setLabel(EmfI18n.PREFILTER)));
		this.tableContainer = appendChild(new DomDiv());
		rebuildWithoutPrefilter();
	}

	protected void rebuild(Set<R> prefilteredEntities) {

		EmfManagementDiv<R, P, S> managementDiv = createManagementDiv(prefilteredEntities);
		tableContainer.removeChildren();
		tableContainer.appendChild(managementDiv);
	}

	private void rebuildWithoutPrefilter() {

		rebuild(null);
	}

	private EmfManagementDiv<R, P, S> createManagementDiv(Set<R> prefilteredEntities) {

		return new EmfManagementDivBuilder<>(table, scope).setPrefilteredEntities(prefilteredEntities).build();
	}
}
