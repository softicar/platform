package com.softicar.platform.emf.data.table.export.engine;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.engine.DomNullEngine;

/**
 * Provides functionality to stash the current {@link IDomDocument} in order to
 * temporarily replace it with another {@link IDomDocument} and to finally restore
 * (unstash) the original one.
 * <p>
 * This is particularly useful to temporarily perform operations on an
 * {@link IDomDocument} using a {@link DomNullEngine}.
 *
 * @author Alexander Schmidt
 */
class DomDocumentStasher {

	private IDomDocument oldDocument = null;

	/**
	 * Saves the current {@link IDomDocument} (as obtained via
	 * {@link CurrentDomDocument#get()}) to an internal reference and passes the
	 * given {@link IDomDocument} to {@link CurrentDomDocument#set(IDomDocument)}. Throws an
	 * Exception in case an {@link IDomDocument} was previously stashed and not yet
	 * unstashed.
	 *
	 * @param newDocument
	 */
	public void stashDomDocumentOrThrow(IDomDocument newDocument) {

		if (this.oldDocument == null) {
			if (newDocument != null) {
				IDomDocument oldDocument = CurrentDomDocument.get();

				try {
					CurrentDomDocument.set(newDocument);
					this.oldDocument = oldDocument;
				} catch (Exception e) {
					CurrentDomDocument.set(oldDocument);
					throw e;
				}
			}

			else {
				throw new SofticarDeveloperException("The given %s must not be null.", IDomDocument.class.getSimpleName());
			}
		}

		else {
			throw new SofticarDeveloperException("There already was an old %s waiting to be restored.", IDomDocument.class.getSimpleName());
		}
	}

	/**
	 * Restores the stashed {@link IDomDocument}, passing it to
	 * {@link CurrentDomDocument#set(IDomDocument)}. Throws an Exception in case no
	 * {@link IDomDocument} was previously stashed or if it was already unstashed.
	 */
	public void unstashDomDocumentOrThrow() {

		if (this.oldDocument != null) {
			CurrentDomDocument.set(this.oldDocument);
			this.oldDocument = null;
		}

		else {
			throw new SofticarDeveloperException("Tried to restore an old %s despite there was none.", IDomDocument.class.getSimpleName());
		}
	}
}
