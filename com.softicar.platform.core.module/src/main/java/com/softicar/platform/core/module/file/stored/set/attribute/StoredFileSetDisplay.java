package com.softicar.platform.core.module.file.stored.set.attribute;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.overview.StoredFileOverviewPopupButton;
import com.softicar.platform.core.module.file.stored.set.AGStoredFileSet;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.set.AbstractEmfForeignEntitySetDisplay;
import java.util.List;

public class StoredFileSetDisplay extends AbstractEmfForeignEntitySetDisplay<AGStoredFile> {

	public StoredFileSetDisplay(AGStoredFileSet entitySet) {

		super(entitySet);
	}

	@Override
	protected void displayElements(List<AGStoredFile> elements) {

		if (elements.size() > 0) {
			var button = new StoredFileOverviewPopupButton(() -> elements);
			button.setLabel(EmfI18n.FILES.concat(": " + elements.size()));
			appendChild(new DomActionBar()).appendChild(button);
		}
	}
}
