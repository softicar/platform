package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;

public class StoredFileBuilder {

	private final AGStoredFile storedFile;

	public StoredFileBuilder() {

		this.storedFile = new AGStoredFile();
		this.storedFile.setCreatedBy(CurrentUser.get());
		this.storedFile.setCreatedAt(DayTime.now());
		this.storedFile.setFileName(null);
		this.storedFile.setContentType((String) null);
		this.storedFile.setRemoveAt(null);
	}

	/**
	 * This sets the filename of the stored file to be created.
	 * <p>
	 * This filename will be suggested to the user when he downloads the file.
	 *
	 * @param filename
	 *            the name of the file, don't use special case like slash,
	 *            backslash, wildcards, etc.
	 * @return this file creator
	 */
	public StoredFileBuilder setFilename(String filename) {

		this.storedFile.setFileName(filename);
		return this;
	}

	/**
	 * You should specify the content type by using this method.
	 *
	 * @param contentType
	 *            some valid mime type
	 * @return this file creator
	 */
	public StoredFileBuilder setContentType(IMimeType contentType) {

		this.storedFile.setContentType(contentType.getIdentifier());
		return this;
	}

	/**
	 * Sets the user associated with this file, usually the uploader.
	 * <p>
	 * This field is already initialized to AGUser.getCurrentUser() but can be
	 * overridden with this method if necessary.
	 *
	 * @param user
	 *            the associated user
	 * @return this file creator
	 */
	public StoredFileBuilder setCreatedBy(AGUser user) {

		this.storedFile.setCreatedBy(user);
		return this;
	}

	/**
	 * Defines a date when this uploaded file shall be deleted.
	 * <p>
	 * This is useful for temporary files.
	 *
	 * @param removeAt
	 *            the date when to delete the file
	 * @return this file creator
	 */
	public StoredFileBuilder setRemoveAt(DayTime removeAt) {

		this.storedFile.setRemoveAt(removeAt);
		return this;
	}

	/**
	 * Creates and saves a file entry into the database.
	 * <p>
	 * The file content can be uploaded later.
	 *
	 * @return the newly created {@link AGStoredFile} entry
	 */
	public AGStoredFile build() {

		if (storedFile.getItemId() == null) {
			saveFile();
		}

		return storedFile;
	}

	private void saveFile() {

		if (storedFile.getFileName() == null) {
			throw new SofticarDeveloperException("You have to provide a filename.");
		}

		storedFile.saveLogged();
	}
}
