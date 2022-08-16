package com.softicar.platform.core.module.file.stored.set.attribute;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.overview.StoredFileOverviewPopupButton;
import com.softicar.platform.core.module.file.stored.set.AGStoredFileSet;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.emf.EmfI18n;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StoredFileSetDisplay extends DomDiv {

	public StoredFileSetDisplay(AGStoredFileSet fileSet) {

		List<AGStoredFile> files = loadFiles(fileSet);
		if (!files.isEmpty()) {
			displayFiles(files);
		}
	}

	private void displayFiles(List<AGStoredFile> files) {

		if (files.size() > 0) {
			var button = new StoredFileOverviewPopupButton(() -> files);
			button.setLabel(EmfI18n.FILES.concat(": " + files.size()));
			appendChild(new DomActionBar()).appendChild(button);
		}
	}

	private List<AGStoredFile> loadFiles(AGStoredFileSet fileSet) {

		if (fileSet != null) {
			return fileSet//
				.getElements()
				.stream()
				.sorted(Comparator.comparing(file -> file.toDisplay().toString()))
				.collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}
}
