package com.softicar.platform.core.module.page.service;

import com.softicar.platform.ajax.export.IAjaxExportBuffer;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.stream.EmptyInputStream;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.log.LogDb;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.runtime.exception.DbException;
import java.io.InputStream;
import java.io.OutputStream;

class PageServiceExportBuffer implements IAjaxExportBuffer {

	private static final int EXPORT_FILE_TIME_TO_LIVE = 1 * 60 * 60; // one hour
	private AGStoredFile storedFile;

	@Override
	public OutputStream openForWriting() {

		this.storedFile = new AGStoredFile();
		this.storedFile.setCreatedBy(CurrentUser.get());
		this.storedFile.setCreatedAt(DayTime.now());
		this.storedFile.setFileName("buffer");
		this.storedFile.setContentType(MimeType.APPLICATION_OCTET_STREAM.getIdentifier());
		this.storedFile.setRemoveAt(computeRemoveAt());
		this.storedFile.saveLogged();

		return storedFile.getFileContentOutputStream();
	}

	private DayTime computeRemoveAt() {

		return DayTime.now().plusSeconds(EXPORT_FILE_TIME_TO_LIVE);
	}

	@Override
	public InputStream openForReading() {

		if (storedFile != null) {
			return storedFile.getFileContentInputStream();
		} else {
			return EmptyInputStream.get();
		}
	}

	@Override
	public void dispose() {

		if (storedFile != null && storedFile.getItemId() != null) {
			try {
				this.storedFile.delete();
				this.storedFile = null;
			} catch (DbException exception) {
				LogDb.panic(exception, "Failed to delete stored file: %s", storedFile.toString());
			}
		}
	}
}
