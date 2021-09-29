package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.io.mime.CustomMimeType;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

public class StoredFileResource implements IResource {

	private final Integer id;

	public StoredFileResource(AGStoredFile storedFile) {

		this.id = storedFile.getId();
	}

	@Override
	public InputStream getResourceAsStream() {

		return AGStoredFile.get(id).getFileContentInputStream();
	}

	@Override
	public IMimeType getMimeType() {

		return new CustomMimeType(AGStoredFile.get(id).getContentType());
	}

	@Override
	public Optional<Charset> getCharset() {

		// TODO return if specified by getContentType()
		return Optional.empty();
	}

	@Override
	public Optional<String> getFilename() {

		return Optional.ofNullable(AGStoredFile.get(id).getFileName());
	}

	@Override
	public Optional<ResourceHash> getContentHash() {

		return Optional.ofNullable(AGStoredFile.get(id).getFileHashString()).map(ResourceHash::new);
	}
}
