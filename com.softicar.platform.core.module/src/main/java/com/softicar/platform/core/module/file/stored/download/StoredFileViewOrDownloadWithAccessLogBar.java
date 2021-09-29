package com.softicar.platform.core.module.file.stored.download;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.access.log.AGStoredFileAccessLog;
import com.softicar.platform.core.module.file.stored.access.log.StoredFileAccessLogButton;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.elements.bar.DomBar;

public class StoredFileViewOrDownloadWithAccessLogBar extends DomBar {

	private final AGStoredFile file;

	public StoredFileViewOrDownloadWithAccessLogBar(AGStoredFile file) {

		this.file = file;

		appendChild(new StoredFileViewOrDownloadButton(file).setPreClickCallback(this::logAccess));
		appendChild(new StoredFileAccessLogButton(file));
	}

	private void logAccess() {

		new AGStoredFileAccessLog()//
			.setFile(file)
			.setAccessedBy(CurrentUser.get())
			.setAccessedAt(DayTime.now())
			.save();
	}
}
