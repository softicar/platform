package com.softicar.platform.core.module.file.stored.access.log;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.emf.EmfImages;

/**
 * A button to open {@link StoredFileAccessLogPopup}.
 * <p>
 * The button has a title but no label.
 *
 * @author Oliver Richers
 */
public class StoredFileAccessLogButton extends DomPopupButton {

	public StoredFileAccessLogButton(AGStoredFile file) {

		setPopupFactory(() -> new StoredFileAccessLogPopup(file));
		setIcon(EmfImages.ENTITY_LOG.getResource());
		setTitle(CoreI18n.STORED_FILE_ACCESS_LOGS);
	}
}
