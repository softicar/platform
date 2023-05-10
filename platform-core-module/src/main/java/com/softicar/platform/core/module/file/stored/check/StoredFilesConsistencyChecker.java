package com.softicar.platform.core.module.file.stored.check;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.core.module.file.stored.content.store.IStoredFileContentStore;
import com.softicar.platform.core.module.file.stored.content.store.StoredFileContentStores;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class StoredFilesConsistencyChecker {

	private final Collection<IStoredFileContentStore> availableContentStores;
	private final ExceptionsCollector exceptionsCollector;
	private final Collection<String> errors;

	public StoredFilesConsistencyChecker() {

		this(StoredFileContentStores.getAccessibleContentStores());
	}

	public StoredFilesConsistencyChecker(Collection<IStoredFileContentStore> allContentStores) {

		this.availableContentStores = allContentStores;
		this.exceptionsCollector = new ExceptionsCollector();
		this.errors = new ArrayList<>();
	}

	public void checkAll() {

		AGStoredFileSha1.TABLE//
			.createSelect()
			.orderBy(AGStoredFileSha1.ID)
			.forEach(this::check);

		if (!errors.isEmpty()) {
			var message = "Errors found:\n" + errors.stream().collect(Collectors.joining("\n"));
			exceptionsCollector.add(new RuntimeException(message));
		}

		exceptionsCollector.throwIfNotEmpty();
	}

	public Collection<IStoredFileContentStore> getAvailableContentStores() {

		return availableContentStores;
	}

	public void addError(String error) {

		errors.add(error);
	}

	public void addException(Throwable exception) {

		exceptionsCollector.add(exception);
	}

	private void check(AGStoredFileSha1 sha1) {

		new StoredFileConsistencyChecker(this, sha1).check();
	}
}
