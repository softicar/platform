package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import java.util.Objects;

class StoredFileUpdater {

	private final AGStoredFile storedFile;

	public StoredFileUpdater(AGStoredFile storedFile) {

		this.storedFile = Objects.requireNonNull(storedFile);
	}

	public void updateRemoveAt(DayTime dayTime) {

		storedFile.setRemoveAt(dayTime);
		saveLogged();
	}

	public void updateRemoveNow() {

		updateRemoveAt(DayTime.now());
	}

	public void updateRemoveNever() {

		updateRemoveAt(null);
	}

	public void saveLogged() {

		if (isSaveNecessary()) {
			try (DbTransaction transaction = new DbTransaction()) {
				storedFile.save();

				AGStoredFileLog log = new AGStoredFileLog();
				log.setFile(storedFile);
				log.setRemoveAt(storedFile.getRemoveAt());
				log.setLoggedAt(DayTime.now());
				log.setLoggedBy(CurrentUser.get());
				log.save();

				transaction.commit();
			}
		}
	}

	private boolean isSaveNecessary() {

		return storedFile.impermanent() || storedFile.dataChanged();
	}
}
