package com.softicar.platform.ajax.document.registry;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.document.IAjaxDocument;
import java.util.Comparator;

/**
 * Comparator for the access times of {@link AjaxDocument} instances.
 * <p>
 * This comparator puts {@link AjaxDocument} instances with the oldest access
 * time in front.
 *
 * @author Oliver Richers
 */
class AjaxDocumentLastAccessFirstComparator implements Comparator<IAjaxDocument> {

	private static final AjaxDocumentLastAccessFirstComparator SINGLETON = new AjaxDocumentLastAccessFirstComparator();

	private AjaxDocumentLastAccessFirstComparator() {

		// this is a singleton
	}

	public static AjaxDocumentLastAccessFirstComparator get() {

		return SINGLETON;
	}

	@Override
	public int compare(IAjaxDocument a, IAjaxDocument b) {

		int cmp = a.getLogs().getLastAccess().compareTo(b.getLogs().getLastAccess());
		return cmp != 0? cmp : a.getInstanceUuid().compareTo(b.getInstanceUuid());
	}
}
