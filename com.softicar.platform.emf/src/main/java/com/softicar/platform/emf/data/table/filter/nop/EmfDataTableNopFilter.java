package com.softicar.platform.emf.data.table.filter.nop;

import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;

/**
 * A no-operation (NOP) pseudo filter that solely provides access to the reset
 * mechanics.
 *
 * @author Alexander Schmidt
 */
public class EmfDataTableNopFilter<R> implements IEmfDataTableFilter<R> {

	private final INullaryVoidFunction resetter;

	public EmfDataTableNopFilter() {

		this(null);
	}

	public EmfDataTableNopFilter(INullaryVoidFunction resetter) {

		this.resetter = resetter;
	}

	@Override
	public void addTo(IDataTableFilterList<R> filters) {

		// nothing to do
	}

	@Override
	public void reset() {

		if (resetter != null) {
			resetter.apply();
		}
	}

	@Override
	public IDisplayString getDisplayString() {

		return IDisplayString.EMPTY;
	}
}
