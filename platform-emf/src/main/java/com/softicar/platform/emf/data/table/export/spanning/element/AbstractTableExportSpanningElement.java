package com.softicar.platform.emf.data.table.export.spanning.element;

/**
 * Represents an element which has colspan and rowspan attributes.
 * <p>
 * Provides misc calculation and flag setting methods for processing purposes.
 *
 * @param <OT>
 *            The type of the wrapped spanning element
 * @author Alexander Schmidt
 */
public abstract class AbstractTableExportSpanningElement<OT> implements ITableExportSpanningElement<OT> {

	private final OT element;
	private final int colspan;
	private final int rowspan;
	private boolean processed = false;
	private int negativeColspan = 0;

	public AbstractTableExportSpanningElement(OT element, int colspan, int rowspan) {

		this.element = element;
		this.colspan = colspan;
		this.rowspan = rowspan;
	}

	@Override
	public OT get() {

		return element;
	}

	@Override
	public int getColspan() {

		return colspan;
	}

	@Override
	public int getRowspan() {

		return rowspan;
	}

	@Override
	public boolean isProcessed() {

		return processed;
	}

	@Override
	public void setProcessed(boolean processed) {

		this.processed = processed;
	}

	@Override
	public int getNegativeColspan() {

		return this.negativeColspan;
	}

	@Override
	public void addNegativeColspan(int negativeColspan) {

		this.negativeColspan += negativeColspan;
	}

	@Override
	public int getEffectiveColspan() {

		return Math.max(getColspan() - getNegativeColspan(), 0);
	}
}
