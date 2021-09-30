package com.softicar.platform.emf.form.section;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.factory.EmfFormSectionFactory;
import com.softicar.platform.emf.form.section.factory.IEmfFormSectionFactory;
import com.softicar.platform.emf.management.section.EmfManagementSectionDiv;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmfFormSectionConfiguration<R extends IEmfTableRow<R, ?>> implements IEmfFormSectionConfiguration<R> {

	private final Collection<EmfFormSectionFactory<R>> sectionFactories;

	public EmfFormSectionConfiguration() {

		this.sectionFactories = new ArrayList<>();
	}

	@Override
	public Collection<EmfFormSectionDiv<R>> createAvailableSections(IEmfFormBody<R> body) {

		return sectionFactories//
			.stream()
			.filter(sectionFactory -> sectionFactory.isAvailable(body.getTableRow()))
			.map(sectionFactory -> sectionFactory.createSection(body))
			.collect(Collectors.toList());
	}

	public EmfFormSectionFactory<R> addSection(IEmfFormSectionFactory<R> sectionFactory) {

		return addSection(sectionFactory, EmfPredicates.always());
	}

	public EmfFormSectionFactory<R> addSection(IEmfFormSectionFactory<R> sectionFactory, IEmfPredicate<R> predicate) {

		EmfFormSectionFactory<R> factory = new EmfFormSectionFactory<>(sectionFactory, predicate);
		sectionFactories.add(factory);
		return factory;
	}

	//FIXME this is a completely similar method structure like in EmfFormTabFactoryList, should be improved
	public EmfFormSectionFactory<R> addManagementSection(IEmfTable<?, ?, R> table) {

		return addManagementSection(table, Function.identity(), EmfPredicates.always());
	}

	public EmfFormSectionFactory<R> addManagementSection(IEmfTable<?, ?, R> table, IDisplayString title) {

		return addManagementSection(table, Function.identity(), EmfPredicates.always(), title);
	}

	public EmfFormSectionFactory<R> addManagementSection(IEmfTable<?, ?, R> table, IEmfPredicate<R> predicate) {

		return addManagementSection(table, Function.identity(), predicate);
	}

	public EmfFormSectionFactory<R> addManagementSection(IEmfTable<?, ?, R> table, IEmfPredicate<R> predicate, IDisplayString title) {

		return addManagementSection(table, Function.identity(), predicate, title);
	}

	public <S> EmfFormSectionFactory<R> addManagementSection(IEmfTable<?, ?, S> table, Function<R, S> scopeMapper) {

		return addManagementSection(table, scopeMapper, EmfPredicates.always());
	}

	public <S> EmfFormSectionFactory<R> addManagementSection(IEmfTable<?, ?, S> table, Function<R, S> scopeMapper, IDisplayString title) {

		return addManagementSection(table, scopeMapper, EmfPredicates.always(), title);
	}

	public <S> EmfFormSectionFactory<R> addManagementSection(IEmfTable<?, ?, S> table, Function<R, S> scopeMapper, IEmfPredicate<R> predicate) {

		return addManagementSection(table, scopeMapper, predicate, table.getPluralTitle());
	}

	public <S> EmfFormSectionFactory<R> addManagementSection(IEmfTable<?, ?, S> table, Function<R, S> scopeMapper, IEmfPredicate<R> predicate,
			IDisplayString title) {

		EmfFormSectionFactory<R> factory =
				new EmfFormSectionFactory<>(formBody -> new EmfManagementSectionDiv<>(formBody, table, scopeMapper, title), predicate);
		sectionFactories.add(factory);
		return factory;
	}
}
