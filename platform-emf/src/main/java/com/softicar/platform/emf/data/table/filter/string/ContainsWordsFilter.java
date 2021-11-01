package com.softicar.platform.emf.data.table.filter.string;

import com.softicar.platform.common.container.data.table.DataTableFilterListOperator;
import com.softicar.platform.common.container.data.table.DataTableStringFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import java.util.Arrays;
import java.util.List;

class ContainsWordsFilter<R> implements IEmfDataTableFilter<R> {

	private final IDataTableColumn<R, String> column;
	private final List<String> words;
	private final INullaryVoidFunction resetter;

	public ContainsWordsFilter(IDataTableColumn<R, String> column, String wordsInput, INullaryVoidFunction resetter) {

		this.column = column;
		this.words = Arrays.asList(wordsInput.trim().split("\\s"));
		this.resetter = resetter;
	}

	@Override
	public void reset() {

		resetter.apply();
	}

	@Override
	public void addTo(IDataTableFilterList<R> filters) {

		IDataTableFilterList<R> filterList = filters.addFilterList(DataTableFilterListOperator.AND);
		for (String word: words) {
			filterList.addStringFilter(column, DataTableStringFilterOperator.LIKE, "%" + word + "%");
		}
	}

	@Override
	public IDisplayString getDisplayString() {

		return EmfDataTableI18n.CONTAINS.concat("(").concat(Imploder.implode(words, "+")).concat(")");
	}
}
