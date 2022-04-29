package com.softicar.platform.emf.table.row;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.form.popup.EmfFormPopup;

public class EmfTableRowDisplay<R extends IEmfTableRow<R, ?>> extends DomDiv {

	public EmfTableRowDisplay(R row) {

		setCssClass(EmfCssClasses.EMF_TABLE_ROW_DISPLAY);

		if (row != null) {
			appendChild(new ViewButton(row));
		}
	}

	private class ViewButton extends DomButton {

		public ViewButton(R row) {

			IDisplayString label = row.toDisplay();
			setLabel(label);
			setTitle(EmfI18n.VIEW.concatColon().concatSpace().concat(label));
			setClickCallback(() -> showEntityFormPopup(row));
		}

		private void showEntityFormPopup(R row) {

			DomPopupManager//
				.getInstance()
				.getPopup(row, EmfFormPopup.class, EmfFormPopup::new)
				.open();
		}
	}
}
