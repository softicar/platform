package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.mime.CustomMimeType;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.file.stored.chunk.AGStoredFileChunk;
import com.softicar.platform.core.module.file.stored.content.StoredFileContentInputStreamCreator;
import com.softicar.platform.core.module.file.stored.content.StoredFileContentOutputStreamCreator;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.emf.object.IEmfObject;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Optional;

public class AGStoredFile extends AGStoredFileGenerated implements IEmfObject<AGStoredFile> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getFileName());
	}

	/**
	 * Creates a new {@link OutputStream} to upload the file content.
	 * <p>
	 * Warning: You may never forget to close the output stream.
	 *
	 * @return new output stream
	 */
	public OutputStream getFileContentOutputStream() {

		return new StoredFileContentOutputStreamCreator(this).create();
	}

	/**
	 * Creates a new {@link InputStream} to download the file content.
	 * <p>
	 * Currently there are three possible content stores: a database field,
	 * database chunks and on the file server, the correct one will be chosen
	 * automatically. Sooner or later all content will be moved to the file
	 * server.
	 *
	 * @return new input stream
	 */
	public InputStream getFileContentInputStream() {

		return new StoredFileContentInputStreamCreator(this).create();
	}

	/**
	 * Returns the content of this file.
	 *
	 * @return file content as byte array
	 */
	public byte[] getFileContentBytes() {

		return new StoredFileContentInputStreamCreator(this).getBytes();
	}

	public void uploadFileContent(InputStream inputStream) {

		new StoredFileContentOutputStreamCreator(this).upload(inputStream);
	}

	public Long getFileSize() {

		Optional<AGStoredFileSha1> sha1 = getSha1AsOptional();
		if (sha1.isPresent()) {
			return sha1.get().getSize();
		} else {
			return getSizeFromChunks();
		}
	}

	public String getFileHashString() {

		return getSha1AsOptional().map(AGStoredFileSha1::getHashString).orElse(null);
	}

	private Optional<AGStoredFileSha1> getSha1AsOptional() {

		return Optional.ofNullable(getSha1());
	}

	public void remove() {

		updateRemoveAtToNow();
	}

	public static Collection<AGStoredFile> getAll(byte[] hash) {

		return AGStoredFile//
			.createSelect()
			.join(AGStoredFile.SHA_1)
			.where(AGStoredFileSha1.HASH.isEqual(hash))
			.list();
	}

	public void updateRemoveAt(DayTime dayTime) {

		new StoredFileUpdater(this).updateRemoveAt(dayTime);
	}

	public void updateRemoveAtToNow() {

		new StoredFileUpdater(this).updateRemoveNow();
	}

	public void updateRemoveAtToNever() {

		new StoredFileUpdater(this).updateRemoveNever();
	}

	public void saveLogged() {

		new StoredFileUpdater(this).saveLogged();
	}

	private Long getSizeFromChunks() {

		return Sql//
			.from(AGStoredFileChunk.TABLE)
			.select(AGStoredFileChunk.CHUNK_SIZE.sum())
			.where(AGStoredFileChunk.FILE.isEqual(this))
			.getFirstAsOptional()
			.orElse(0)
			.longValue();
	}

	public IMimeType getMimeType() {

		return new CustomMimeType(getContentType());
	}

	/**
	 * Extracts the extension (i.e. the characters after the last {@code "."}
	 * from the file name, if any.
	 * <p>
	 * The returned {@link String} is always in lower case and never blank.
	 *
	 * @return the file name extension
	 */
	public Optional<String> getFilenameExtension() {

		String fileName = getFileName();
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex >= 0) {
			String extension = fileName.substring(dotIndex + 1);
			if (!extension.isBlank()) {
				return Optional.of(extension.toLowerCase().trim());
			}
		}
		return Optional.empty();
	}

	/**
	 * Determines whether {@link AGStoredFile#getContentType()} matches the
	 * given {@link IMimeType}.
	 * <p>
	 * The comparison is case-insensitive.
	 *
	 * @param mimeType
	 *            the suspected {@link IMimeType} (never <i>null</i>)
	 * @return <i>true</i> if this {@link AGStoredFile} has the given
	 *         {@link IMimeType}; <i>false</i> otherwise
	 */
	public boolean hasMimeType(IMimeType mimeType) {

		return getMimeType().getIdentifier().equalsIgnoreCase(mimeType.getIdentifier());
	}

	/**
	 * Determines whether the name of this {@link AGStoredFile} ends with the
	 * given file name extension.
	 * <p>
	 * The comparison is case-insensitive.
	 *
	 * @param extension
	 *            the file name extension (never <i>null</i>)
	 * @return <i>true</i> if this {@link AGStoredFile} has the given extension;
	 *         <i>false</i> otherwise
	 * @throws IllegalArgumentException
	 *             if the given extension is blank
	 */
	public boolean hasFileNameExtension(String extension) {

		if (extension.isBlank()) {
			throw new IllegalArgumentException();
		}
		return getFileName().toLowerCase().endsWith("." + extension.trim().toLowerCase());
	}

	/**
	 * Determines whether {@link AGStoredFile#getContentType()} matches the
	 * given {@link MimeType} <b>and/or</b> the name of this
	 * {@link AGStoredFile} ends with one of the file name extensions associated
	 * with the given {@link MimeType}.
	 *
	 * @param mimeType
	 *            the {@link MimeType} to check against (never <i>null</i>)
	 * @return <i>true</i> if the mime type and/or file name extension matches;
	 *         <i>false</i> otherwise
	 */
	public boolean hasMimeTypeOrExtension(MimeType mimeType) {

		return hasMimeType(mimeType) || mimeType.getFilenameSuffixes().stream().anyMatch(this::hasFileNameExtension);
	}

	/**
	 * Sets the given {@link IMimeType} as {@link AGStoredFile#CONTENT_TYPE}.
	 *
	 * @param mimeType
	 *            the {@link IMimeType} (never <i>null</i>)
	 * @return this
	 */
	public AGStoredFile setContentTypeByMimeType(IMimeType mimeType) {

		setContentType(mimeType.getIdentifier().toLowerCase());
		return this;
	}

	/**
	 * Creates a new {@link StoredFileConverter} for this {@link AGStoredFile}.
	 *
	 * @return the {@link StoredFileConverter} (never <i>null</i>)
	 */
	public StoredFileConverter convert() {

		return new StoredFileConverter(this);
	}
}
