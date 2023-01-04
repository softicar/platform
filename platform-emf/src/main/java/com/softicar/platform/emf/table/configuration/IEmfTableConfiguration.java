package com.softicar.platform.emf.table.configuration;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.transients.ITransientField;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.action.IEmfScopeAction;
import com.softicar.platform.emf.action.factory.IEmfCommonActionFactory;
import com.softicar.platform.emf.action.factory.IEmfManagementActionFactory;
import com.softicar.platform.emf.action.factory.IEmfPrimaryActionFactory;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.IEmfForeignRowAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.IEmfForeignRowAttributeFactory;
import com.softicar.platform.emf.authorizer.IEmfAttributeAuthorizer;
import com.softicar.platform.emf.authorizer.IEmfAuthorizer;
import com.softicar.platform.emf.deactivation.IEmfTableRowDeactivationStrategy;
import com.softicar.platform.emf.delete.IEmfDeleteStrategy;
import com.softicar.platform.emf.form.attribute.factory.IEmfFormAttributesDivConfiguration;
import com.softicar.platform.emf.form.factory.IEmfFormFactory;
import com.softicar.platform.emf.form.indicator.IEmfFormIndicatorConfiguration;
import com.softicar.platform.emf.form.section.IEmfFormSectionConfiguration;
import com.softicar.platform.emf.form.tab.factory.IEmfFormTabConfiguration;
import com.softicar.platform.emf.log.IEmfChangeLogger;
import com.softicar.platform.emf.management.IEmfManagementConfiguration;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.listener.IEmfCommitHook;
import com.softicar.platform.emf.table.listener.IEmfDeleteHook;
import com.softicar.platform.emf.table.listener.IEmfSaveHook;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.table.row.IEmfTableRowsFinder;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface IEmfTableConfiguration<R extends IEmfTableRow<R, P>, P, S> {

	// ------------------------------ display and input ------------------------------ //

	IResource getIcon();

	Function<R, IDomElement> getDisplayFactory();

	IEmfFormAttributesDivConfiguration<R> getFormAttributesDivConfiguration();

	IEmfFormIndicatorConfiguration<R> getFormIndicatorConfiguration();

	IEmfFormSectionConfiguration<R> getFormSectionConfiguration();

	IEmfFormTabConfiguration<R> getFormTabConfiguration();

	// ------------------------------ attributes ------------------------------ //

	List<IEmfAttribute<R, ?>> getAttributes();

	<V> IEmfAttribute<R, V> getAttribute(ISqlField<R, V> field);

	<V> IEmfAttribute<R, V> getAttribute(ITransientField<R, V> field);

	IEmfAttributeAuthorizer<R> getAttributeAuthorizer(R row, IBasicUser user);

	IEmfAttributeDefaultValueSet<R, S> getAttributeDefaultValueSet();

	// ------------------------------ actions ------------------------------ //

	Collection<IEmfScopeAction<S>> getScopeActions();

	Collection<IEmfPrimaryAction<R>> getPrimaryActions();

	Collection<IEmfCommonAction<R>> getCommonActions();

	Collection<IEmfManagementAction<R>> getManagementActions();

	Collection<IEmfPrimaryActionFactory<R>> getPrimaryActionFactories();

	Collection<IEmfCommonActionFactory<R>> getCommonActionFactories();

	Collection<IEmfManagementActionFactory<R>> getManagementActionFactories();

	Collection<IEmfPrimaryAction<R>> getPrimaryActions(R tableRow);

	Collection<IEmfCommonAction<R>> getCommonActions(R tableRow);

	Collection<IEmfManagementAction<R>> getManagementActions(R tableRow);

	// ------------------------------ miscellaneous ------------------------------ //

	Optional<ISqlForeignRowField<R, S, ?>> getScopeField();

	/**
	 * Returns the {@link IEmfForeignRowAttributeFactory} to use for
	 * {@link IEmfForeignRowAttribute}s pointing to the {@link IEmfTable} of
	 * this {@link IEmfTableConfiguration}.
	 *
	 * @return the {@link IEmfForeignRowAttributeFactory} (never null)
	 */
	IEmfForeignRowAttributeFactory<R, P> getAttributeFactory();

	Collection<IEmfCommitHook<R>> getCommitHooks();

	Collection<IEmfDeleteHook<R>> getDeleteHooks();

	Collection<IEmfSaveHook<R>> getSaveHooks();

	Collection<IEmfValidator<R>> getValidators();

	IEmfAuthorizer<R, S> getAuthorizer();

	IEmfFormFactory<R> getFormFactory();

	IEmfPredicate<S> getCreationPredicate();

	IEmfPredicate<R> getEditPredicate();

	IEmfPredicate<R> getDeactivationPredicate();

	Optional<IEmfTableRowsFinder<R>> getTableRowsFinder();

	Collection<IEmfChangeLogger<R>> getChangeLoggers();

	Collection<IEmfTable<?, ?, R>> getChildTables();

	IEmfManagementConfiguration<R> getManagementConfiguration();

	IEmfDeleteStrategy<R> getDeleteStrategy();

	/**
	 * Returns the {@link IEmfTableRowDeactivationStrategy} to be used for the
	 * table.
	 *
	 * @return the {@link IEmfTableRowDeactivationStrategy} (never null)
	 */
	IEmfTableRowDeactivationStrategy<R> getDeactivationStrategy();

	IDbKey<R> getBusinessKey();
}
