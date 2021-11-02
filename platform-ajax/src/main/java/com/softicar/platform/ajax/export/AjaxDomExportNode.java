package com.softicar.platform.ajax.export;

import com.softicar.platform.ajax.framework.IAjaxFramework;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import com.softicar.platform.dom.elements.DomIFrame;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.styles.CssDisplay;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * A hidden in-line frame to trigger the download of an export.
 *
 * @author Oliver Richers
 */
public class AjaxDomExportNode extends DomIFrame implements IResource {

	private final IAjaxExportBuffer ajaxBuffer;
	private final String filename;
	private final IMimeType mimeType;
	private final Charset charset;
	private Closeable closeable;

	public AjaxDomExportNode(IAjaxFramework ajaxFramework, String filename, IMimeType mimeType, Charset charset) {

		this.ajaxBuffer = ajaxFramework.getAjaxStrategy().createExportBuffer();
		this.filename = filename;
		this.mimeType = mimeType;
		this.charset = charset;

		setAddress(getDomEngine().getResourceUrl(this).toString());
		setStyle(CssDisplay.NONE);
	}

	@Override
	public InputStream getResourceAsStream() {

		return ajaxBuffer.openForReading();
	}

	@Override
	public IMimeType getMimeType() {

		return mimeType;
	}

	@Override
	public Optional<Charset> getCharset() {

		return Optional.ofNullable(charset);
	}

	@Override
	public Optional<ResourceHash> getContentHash() {

		return Optional.empty();
	}

	@Override
	public Optional<String> getFilename() {

		return Optional.ofNullable(filename);
	}

	public OutputStream createOutputStream() {

		OutputStream outputStream = ajaxBuffer.openForWriting();
		this.closeable = outputStream;
		return outputStream;
	}

	public void closeOutput() {

		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException exception) {
				throw new SofticarIOException(exception);
			}
		}
	}

	public void dispose() {

		// dispose buffer
		ajaxBuffer.dispose();

		// remove this in-line frame from the document
		IDomParentElement parent = getParent();
		if (parent != null) {
			synchronized (parent) {
				parent.removeChild(this);
			}
		}
	}
}
