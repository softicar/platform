package com.softicar.platform.emf.data.table.export.spanning.element;

/**
 * Wraps elements that have both a col- and a rowspan, besides allowing the
 * storage of internal processing information.
 *
 * @param <OT>
 *            the type of the wrapped spanning element
 * @author Alexander Schmidt
 */
public interface ITableExportSpanningElement<OT> {

	OT get();

	int getColspan();

	int getRowspan();

	boolean isProcessed();

	void setProcessed(boolean processed);

	int getNegativeColspan();

	void addNegativeColspan(int colspan);

	int getEffectiveColspan();
}
