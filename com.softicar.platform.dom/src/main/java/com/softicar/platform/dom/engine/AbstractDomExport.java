package com.softicar.platform.dom.engine;

import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import java.nio.charset.Charset;
import java.util.Objects;

public abstract class AbstractDomExport implements IDomExport {

	protected String filename;
	protected IMimeType mimeType;
	protected Charset charset;

	public AbstractDomExport() {

		this.filename = "";
		this.mimeType = MimeType.APPLICATION_OCTET_STREAM;
		this.charset = null;
	}

	@Override
	public IDomExport setFilename(String filename) {

		this.filename = Objects.requireNonNull(filename);
		return this;
	}

	@Override
	public IDomExport setMimeType(IMimeType mimeType) {

		this.mimeType = Objects.requireNonNull(mimeType);
		return this;
	}

	@Override
	public IDomExport setCharset(Charset charset) {

		this.charset = charset;
		return this;
	}
}
