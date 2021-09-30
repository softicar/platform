package com.softicar.platform.emf.data.table.export.popup;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.List;

class TableExportPopupConfigurationDivBuilder {

	private final List<DomButton> buttons;

	public TableExportPopupConfigurationDivBuilder() {

		this.buttons = new ArrayList<>();
	}

	public void addButton(DomButton button) {

		this.buttons.add(button);
	}

	public List<DomButton> getButtons() {

		return buttons;
	}

	public IDomNode build() {

		int count = this.buttons.size();
		var container = new DomActionBar();
		for (int i = 0; i < count; i++) {
			container.appendChild(this.buttons.get(i));

			if (i < count - 1) {
				container.appendNewChild(DomElementTag.BR);
			}
		}
		return container;
	}
}
