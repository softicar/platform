package com.softicar.platform.ajax.request.parameters;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;

/**
 * An adapter for the {@link FileItemIterator}.
 * <p>
 * This adapter isolates the client code from the dependency on
 * {@link FileItemIterator}, so that this might be replaced later if necessary.
 * <p>
 * Also, the fact that the {@link FileItemIterator} given to this class is
 * already advanced beyond the first file item, is hidden.
 *
 * @author Oliver Richers
 */
class AjaxFileUploadList implements Iterable<IDomFileUpload>, Iterator<IDomFileUpload> {

	private final FileItemIterator iterator;
	private FileItemStream current;

	/**
	 * Constructs this {@link AjaxFileUploadList} with the specified first
	 * {@link FileItemStream} and the {@link FileItemIterator}.
	 *
	 * @param first
	 *            the first file item
	 * @param iterator
	 *            the iterator to the following file items
	 */
	public AjaxFileUploadList(FileItemStream first, FileItemIterator iterator) {

		this.current = first;
		this.iterator = iterator;

		if (!isCurrentOkay()) {
			findNext();
		}
	}

	@Override
	public boolean hasNext() {

		if (current == null) {
			findNext();
		}
		return current != null;
	}

	@Override
	public IDomFileUpload next() {

		if (current == null) {
			throw new NoSuchElementException();
		}

		FileItemStream tmp = current;
		current = null;
		return new AjaxFileUpload(tmp);
	}

	@Override
	public void remove() {

		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<IDomFileUpload> iterator() {

		return this;
	}

	private boolean isCurrentOkay() {

		return current != null && !current.isFormField() && !current.getName().isEmpty();
	}

	private void findNext() {

		try {
			while (iterator.hasNext()) {
				current = iterator.next();
				if (isCurrentOkay()) {
					return;
				}
			}

			current = null;
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		} catch (FileUploadException exception) {
			throw new SofticarException(exception);
		}
	}
}
