package com.softicar.platform.emf.form.tab.factory;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.emf.form.IEmfForm;
import com.softicar.platform.emf.form.tab.factory.table.EmfFormManagementTabFactory;
import com.softicar.platform.emf.form.tab.factory.trait.EmfFormTraitTabFactory;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.registry.EmfTableRegistry;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.trait.IEmfTrait;
import com.softicar.platform.emf.trait.table.IEmfTraitTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EmfFormTabConfiguration<R extends IEmfTableRow<R, ?>> implements IEmfFormTabConfiguration<R> {

	private final Collection<IEmfFormTabFactory<R>> tabFactories;

	public EmfFormTabConfiguration(IEmfTable<R, ?, ?> table) {

		this.tabFactories = new ArrayList<>();

		EmfTableRegistry//
			.getInstance()
			.getTraitTables(table)
			.stream()
			.sorted(Comparator.comparing(it -> it.getTitle().toString()))
			.forEach(this::addTraitTabFactory);
	}

	@Override
	public Collection<DomTab> createVisibleTabs(IEmfForm<R> form) {

		return tabFactories//
			.stream()
			.filter(factory -> factory.isVisible(form.getTableRow()))
			.map(factory -> factory.createTab(form))
			.collect(Collectors.toList());
	}

	@Override
	public boolean hasVisibleTabs(R tableRow) {

		return tabFactories//
			.stream()
			.anyMatch(factory -> factory.isVisible(tableRow));
	}

	public EmfFormTabConfiguration<R> addTabFactory(IEmfFormTabFactory<R> tabFactory) {

		tabFactories.add(tabFactory);
		return this;
	}

	public EmfFormTabConfiguration<R> addTab(Supplier<DomTab> supplier) {

		tabFactories.add(new EmfFormTabFactory<>(supplier));
		return this;
	}

	public EmfFormTabConfiguration<R> addTab(Supplier<DomTab> tabSupplier, IEmfPredicate<R> visiblePredicate) {

		tabFactories.add(new EmfFormTabFactory<>(tabSupplier, visiblePredicate));
		return this;
	}

	//FIXME this is a completely similar method structure like in EmfFormSectionFactories, should be improved
	public EmfFormTabConfiguration<R> addManagementTab(IEmfTable<?, ?, R> table) {

		return addManagementTab(table, Function.identity(), EmfPredicates.always());
	}

	public EmfFormTabConfiguration<R> addManagementTab(IEmfTable<?, ?, R> table, IDisplayString title) {

		return addManagementTab(table, Function.identity(), EmfPredicates.always(), title);
	}

	public EmfFormTabConfiguration<R> addManagementTab(IEmfTable<?, ?, R> table, IEmfPredicate<R> visiblePredicate) {

		return addManagementTab(table, Function.identity(), visiblePredicate);
	}

	public EmfFormTabConfiguration<R> addManagementTab(IEmfTable<?, ?, R> table, IEmfPredicate<R> visiblePredicate, IDisplayString title) {

		return addManagementTab(table, Function.identity(), visiblePredicate, title);
	}

	public <S> EmfFormTabConfiguration<R> addManagementTab(IEmfTable<?, ?, S> table, Function<R, S> scopeMapper) {

		return addManagementTab(table, scopeMapper, EmfPredicates.always());
	}

	public <S> EmfFormTabConfiguration<R> addManagementTab(IEmfTable<?, ?, S> table, Function<R, S> scopeMapper, IDisplayString title) {

		return addManagementTab(table, scopeMapper, EmfPredicates.always(), title);
	}

	public <S> EmfFormTabConfiguration<R> addManagementTab(IEmfTable<?, ?, S> table, Function<R, S> scopeMapper, IEmfPredicate<R> visiblePredicate) {

		return addManagementTab(table, scopeMapper, visiblePredicate, table.getPluralTitle());
	}

	public <S> EmfFormTabConfiguration<R> addManagementTab(IEmfTable<?, ?, S> table, Function<R, S> scopeMapper, IEmfPredicate<R> visiblePredicate,
			IDisplayString title) {

		tabFactories.add(new EmfFormManagementTabFactory<>(title, table, scopeMapper, visiblePredicate));
		return this;
	}

	private <T extends IEmfTrait<T, R>> void addTraitTabFactory(IEmfTraitTable<T, R> traitTable) {

		tabFactories.add(new EmfFormTraitTabFactory<>(traitTable));
	}
}
