package com.softicar.platform.core.module.file.stored.overview;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.emf.EmfI18n;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StoredFileOverviewPopup extends DomPopup {

	public StoredFileOverviewPopup(Collection<AGStoredFile> files) {

		setCaption(EmfI18n.FILES);
		List<StoredFileOverviewRow> rows = loadRows(files);
		StoredFileOverviewDataTable dataTable = new StoredFileOverviewDataTable(rows);
		new StoredFileOverviewDataTableDivBuilder(dataTable).buildAndAppendTo(this);
		appendCloseButton();
	}

	private List<StoredFileOverviewRow> loadRows(Collection<AGStoredFile> files) {

		Map<AGStoredFile, AGStoredFileSha1> hashes = AGStoredFileSha1.loadAll(files);
		return files//
			.stream()
			.map(file -> new StoredFileOverviewRow(file, hashes.get(file)))
			.collect(Collectors.toList());
	}
}
