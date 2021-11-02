package com.softicar.platform.emf.data.table.export.precondition;

import com.softicar.platform.emf.data.table.export.popup.TableExportPopupButton;

/**
 * Implemented by preconditions to be checked upon clicking
 * {@link TableExportPopupButton}.
 *
 * @author Alexander Schmidt
 */
public interface ITableExportPrecondition {

	/**
	 * A check that is expected to either have no effect at all (in case it
	 * passes) or to throw an Exception (in case it fails).
	 */
	public void passOrThrow();
}
