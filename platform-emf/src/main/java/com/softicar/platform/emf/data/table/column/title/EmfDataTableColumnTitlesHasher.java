package com.softicar.platform.emf.data.table.column.title;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.data.table.IEmfDataTableController;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmfDataTableColumnTitlesHasher {

	private final String hash;

	public EmfDataTableColumnTitlesHasher(IEmfDataTableController<?> controller) {

		Objects.requireNonNull(controller);
		this.hash = new EmfDataTableColumnTitlesHashFactory().createHash(getColumnTitles(controller));
	}

	@Override
	public String toString() {

		return getHash();
	}

	public String getHash() {

		return hash;
	}

	private <R> List<IDisplayString> getColumnTitles(IEmfDataTableController<R> controller) {

		return controller//
			.getColumnsInDefaultOrder()
			.stream()
			.map(IEmfDataTableColumn::getDataColumn)
			.filter(column -> !controller.getColumnSettings(column).isConcealed())
			.map(IDataTableColumn::getTitle)
			.collect(Collectors.toList());
	}
}
