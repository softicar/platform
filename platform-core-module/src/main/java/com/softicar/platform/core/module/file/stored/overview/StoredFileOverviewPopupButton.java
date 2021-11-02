package com.softicar.platform.core.module.file.stored.overview;

import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfI18n;
import java.util.Collection;
import java.util.function.Supplier;

public class StoredFileOverviewPopupButton extends DomPopupButton {

	private final Supplier<Collection<AGStoredFile>> filesSupplier;

	public StoredFileOverviewPopupButton(Supplier<Collection<AGStoredFile>> filesSupplier) {

		this.filesSupplier = filesSupplier;
		setIcon(CoreImages.STORED_FILE.getResource());
		setTitle(EmfI18n.SHOW_FILES);
		setPopupFactory(this::createPopup);
	}

	private StoredFileOverviewPopup createPopup() {

		Collection<AGStoredFile> files = filesSupplier.get();
		AGStoredFile.TABLE.reloadAll(files);
		return new StoredFileOverviewPopup(files);
	}
}
