package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.io.mime.CustomMimeType;
import com.softicar.platform.common.io.mime.IMimeType;
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

	public boolean hasMimeType(IMimeType mimeType) {

		return getMimeType().getIdentifier().equalsIgnoreCase(mimeType.getIdentifier());
	}

	public boolean hasFileNameExtension(String extension) {

		return getFileName().toLowerCase().endsWith("." + extension.toLowerCase());
	}
}
