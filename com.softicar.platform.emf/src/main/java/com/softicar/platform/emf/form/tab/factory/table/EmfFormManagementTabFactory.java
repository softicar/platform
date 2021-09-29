package com.softicar.platform.emf.form.tab.factory.table;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.tab.factory.IEmfFormTabFactory;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Function;

public class EmfFormManagementTabFactory<R extends IEmfTableRow<R, ?>, S> implements IEmfFormTabFactory<R> {

	private final IDisplayString title;
	private final IEmfTable<?, ?, S> table;
	private final Function<R, S> scopeMapper;
	private final IEmfPredicate<R> visiblePredicate;

	public EmfFormManagementTabFactory(IDisplayString title, IEmfTable<?, ?, S> table, Function<R, S> scopeMapper, IEmfPredicate<R> visiblePredicate) {

		this.title = title;
		this.table = table;
		this.scopeMapper = scopeMapper;
		this.visiblePredicate = visiblePredicate;
	}

	@Override
	public boolean isVisible(R tableRow) {

		return visiblePredicate.test(tableRow);
	}

	@Override
	public DomTab createTab(IEmfForm<R> form) {

		return new ManagementTab(form);
	}

	private class ManagementTab extends DomTab {

		public ManagementTab(IEmfForm<R> form) {

			super(title);
			appendChild(new EmfManagementDiv<>(table, scopeMapper.apply(form.getTableRow())));
		}
	}
}
