package com.softicar.platform.ajax.request;

import com.softicar.platform.dom.event.upload.IDomFileUpload;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

/**
 * An {@link Iterable} and {@link Iterator} of {@link Part} instances from a
 * {@link HttpServletRequest}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
class AjaxFileUploadList implements Iterable<IDomFileUpload>, Iterator<IDomFileUpload> {

	private final Iterator<Part> iterator;
	private Part current;

	/**
	 * Constructs a new {@link AjaxFileUploadList} with the specified first
	 * {@link Part} and an {@link Iterator} of {@link Part} instances.
	 *
	 * @param part
	 *            the first {@link Part} (never <i>null</i>)
	 * @param iterator
	 *            the {@link Iterator} to the following {@link Part} instances
	 *            (never <i>null</i>)
	 */
	public AjaxFileUploadList(Part part, Iterator<Part> iterator) {

		this.current = Objects.requireNonNull(part);
		this.iterator = Objects.requireNonNull(iterator);

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

		Part tmp = current;
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

		return current != null && !isFormField(current) && !current.getName().isEmpty();
	}

	private boolean isFormField(Part part) {

		return part.getSubmittedFileName() == null;
	}

	private void findNext() {

		while (iterator.hasNext()) {
			current = iterator.next();
			if (isCurrentOkay()) {
				return;
			}
		}

		current = null;
	}
}
