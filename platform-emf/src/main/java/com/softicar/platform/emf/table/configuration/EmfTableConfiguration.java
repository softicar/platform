package com.softicar.platform.emf.table.configuration;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.transients.ITransientField;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.action.IEmfScopeAction;
import com.softicar.platform.emf.action.factory.EmfCommonActionGenerator;
import com.softicar.platform.emf.action.factory.EmfManagementActionGenerator;
import com.softicar.platform.emf.action.factory.EmfPrimaryActionGenerator;
import com.softicar.platform.emf.action.factory.IEmfCommonActionFactory;
import com.softicar.platform.emf.action.factory.IEmfManagementActionFactory;
import com.softicar.platform.emf.action.factory.IEmfPrimaryActionFactory;
import com.softicar.platform.emf.attribute.EmfAttributeList;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.dependency.EmfAttributeDependencyMap;
import com.softicar.platform.emf.attribute.field.foreign.row.IEmfForeignRowAttributeFactory;
import com.softicar.platform.emf.authorizer.EmfAttributeAuthorizer;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.authorizer.IEmfAttributeAuthorizer;
import com.softicar.platform.emf.authorizer.IEmfAuthorizer;
import com.softicar.platform.emf.deactivation.EmfTableRowDeactivationStrategy;
import com.softicar.platform.emf.deactivation.IEmfTableRowDeactivationStrategy;
import com.softicar.platform.emf.delete.EmfDeleteStrategyBuilder;
import com.softicar.platform.emf.delete.IEmfDeleteStrategy;
import com.softicar.platform.emf.form.EmfForm;
import com.softicar.platform.emf.form.factory.IEmfFormFactory;
import com.softicar.platform.emf.form.indicator.EmfFormIndicatorConfiguration;
import com.softicar.platform.emf.form.indicator.IEmfFormIndicatorConfiguration;
import com.softicar.platform.emf.form.section.EmfFormSectionConfiguration;
import com.softicar.platform.emf.form.section.IEmfFormSectionConfiguration;
import com.softicar.platform.emf.form.tab.factory.EmfFormTabConfiguration;
import com.softicar.platform.emf.form.tab.factory.IEmfFormTabConfiguration;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.log.IEmfChangeLogger;
import com.softicar.platform.emf.management.EmfManagementConfiguration;
import com.softicar.platform.emf.management.IEmfManagementConfiguration;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.listener.EmfCommitHookWrapper;
import com.softicar.platform.emf.table.listener.EmfDeleteHookWrapper;
import com.softicar.platform.emf.table.listener.EmfSaveHookWrapper;
import com.softicar.platform.emf.table.listener.IEmfCommitHook;
import com.softicar.platform.emf.table.listener.IEmfDeleteHook;
import com.softicar.platform.emf.table.listener.IEmfSaveHook;
import com.softicar.platform.emf.table.row.EmfTableRowDisplay;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.table.row.IEmfTableRowsFinder;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Default implementation of {@link IEmfTableConfiguration}.
 *
 * @author Oliver Richers
 */
public class EmfTableConfiguration<R extends IEmfTableRow<R, P>, P, S> implements IEmfTableConfiguration<R, P, S> {

	private final IEmfTable<R, P, S> table;
	private final Supplier<EmfActionSet<R, S>> actionSetSupplier;
	private final Supplier<EmfAttributeList<R, P>> attributeListSupplier;
	private final Supplier<EmfAttributeDependencyMap<R>> attributeDependencyMapSupplier;
	private final Supplier<EmfAttributeDefaultValueSet<R, S>> attributeDefaultValueSetSupplier;
	private final Supplier<EmfFormIndicatorConfiguration<R>> indicatorConfigurationSupplier;
	private final Supplier<EmfFormSectionConfiguration<R>> sectionConfigurationSupplier;
	private final Supplier<EmfFormTabConfiguration<R>> tabConfigurationSupplier;
	private final Supplier<EmfChangeLoggerSet<R>> loggerSetSupplier;
	private final Supplier<EmfChildTableSet<R>> childTableSetSupplier;
	private final Supplier<EmfManagementConfiguration<R>> managementConfigurationSupplier;
	private final Supplier<EmfAuthorizer<R, S>> authorizerSupplier;
	private final Collection<IEmfCommitHook<R>> commitHooks;
	private final Collection<IEmfDeleteHook<R>> deleteHooks;
	private final Collection<IEmfSaveHook<R>> saveHooks;
	private final Collection<IEmfValidator<R>> validators;
	private IEmfFormFactory<R> formFactory;
	private IEmfPredicate<S> creationPredicate;
	private IEmfPredicate<R> editPredicate;
	private IEmfPredicate<R> deactivationPredicate;
	private IResourceSupplier iconResourceSupplier;
	private Function<R, IDomElement> displayFactory;
	private Optional<ISqlForeignRowField<R, S, ?>> scopeField;
	private IEmfForeignRowAttributeFactory<R, P> attributeFactory;
	private BiFunction<R, IBasicUser, IEmfAttributeAuthorizer<R>> attributeAuthorizerFactory;
	private Optional<IEmfTableRowsFinder<R>> tableRowsFinder;
	private IEmfDeleteStrategy<R> deleteStrategy;
	private IEmfTableRowDeactivationStrategy<R> deactivationStrategy;
	private IDbKey<R> businessKey;

	public EmfTableConfiguration(IEmfTable<R, P, S> table) {

		this.table = table;
		this.actionSetSupplier = new EmfLazySupplier<>(table, EmfActionSet::new, table::customizeActionSet);
		this.attributeListSupplier = new EmfLazySupplier<>(table, EmfAttributeList::new, this::customizeAttributesAndOrder);
		this.attributeDependencyMapSupplier = new EmfLazySupplier<>(table, EmfAttributeDependencyMap::new, table::customizeAttributeDependencies);
		this.attributeDefaultValueSetSupplier = new EmfLazySupplier<>(table, EmfAttributeDefaultValueSet::new, table::customizeAttributeDefaultValues);
		this.indicatorConfigurationSupplier = new EmfLazySupplier<>(EmfFormIndicatorConfiguration::new, table::customizeFormIndicators);
		this.sectionConfigurationSupplier = new EmfLazySupplier<>(EmfFormSectionConfiguration::new, table::customizeFormSections);
		this.tabConfigurationSupplier = new EmfLazySupplier<>(() -> new EmfFormTabConfiguration<>(table), table::customizeFormTabs);
		this.loggerSetSupplier = new EmfLazySupplier<>(EmfChangeLoggerSet::new, table::customizeLoggers);
		this.childTableSetSupplier = new EmfLazySupplier<>(EmfChildTableSet::new, table::customizeChildTables);
		this.managementConfigurationSupplier = new EmfLazySupplier<>(table, EmfManagementConfiguration::new, table::customizeManagementConfiguraton);
		this.authorizerSupplier = new EmfLazySupplier<>(EmfAuthorizer::new, table::customizeAuthorizer);
		this.formFactory = EmfForm::new;
		this.commitHooks = new ArrayList<>();
		this.deleteHooks = new ArrayList<>();
		this.saveHooks = new ArrayList<>();
		this.validators = new ArrayList<>();
		this.creationPredicate = EmfPredicates.always();
		this.editPredicate = EmfPredicates.always();
		this.deactivationPredicate = EmfPredicates.always();
		this.iconResourceSupplier = EmfImages.PAGE_DEFAULT;
		this.displayFactory = EmfTableRowDisplay::new;
		this.scopeField = Optional.empty();
		this.attributeFactory = table.getTableSpecialization().getDefaultAttributeFactory();
		this.attributeAuthorizerFactory = EmfAttributeAuthorizer<R>::new;
		this.tableRowsFinder = Optional.empty();
		this.deleteStrategy = new EmfDeleteStrategyBuilder<R>().build();
		this.deactivationStrategy = new EmfTableRowDeactivationStrategy<>(table);
	}

	// ------------------------------ setters ------------------------------ //

	public final void setIcon(IResourceSupplier iconResourceSupplier) {

		this.iconResourceSupplier = iconResourceSupplier;
	}

	public final void setDisplayFactory(Function<R, IDomElement> displayFactory) {

		this.displayFactory = displayFactory;
	}

	public final void setScopeField(ISqlForeignRowField<R, S, ?> scopeField) {

		this.scopeField = Optional.of(scopeField);
	}

	public void setAttributeFactory(IEmfForeignRowAttributeFactory<R, P> attributeFactory) {

		this.attributeFactory = Objects.requireNonNull(attributeFactory);
	}

	/**
	 * Adds an {@link IEmfCommitHook} to this {@link EmfTableConfiguration}.
	 * <p>
	 * The given factory will be used to create a thread-local instance of the
	 * {@link IEmfCommitHook}, so data can be safely transfered between calls to
	 * {@link IEmfCommitHook#beforeCommit} and
	 * {@link IEmfCommitHook#afterCommit}.
	 *
	 * @param commitHookFactory
	 *            the {@link IEmfCommitHook} factory (never <i>null</i>)
	 */
	public final void addCommitHook(Supplier<IEmfCommitHook<R>> commitHookFactory) {

		commitHooks.add(new EmfCommitHookWrapper<>(commitHookFactory));
	}

	/**
	 * Adds an {@link IEmfDeleteHook} to this {@link EmfTableConfiguration}.
	 * <p>
	 * The given factory will be used to create a thread-local instance of the
	 * {@link IEmfDeleteHook}, so data can be safely transfered between calls to
	 * {@link IEmfDeleteHook#beforeDelete} and
	 * {@link IEmfDeleteHook#afterDelete}.
	 *
	 * @param deleteHookFactory
	 *            the {@link IEmfDeleteHook} factory (never <i>null</i>)
	 */
	public final void addDeleteHook(Supplier<IEmfDeleteHook<R>> deleteHookFactory) {

		deleteHooks.add(new EmfDeleteHookWrapper<>(deleteHookFactory));
	}

	/**
	 * Adds an {@link IEmfSaveHook} to this {@link EmfTableConfiguration}.
	 * <p>
	 * The given factory will be used to create a thread-local instance of the
	 * {@link IEmfSaveHook}, so data can be safely transfered between calls to
	 * {@link IEmfSaveHook#beforeSave} and {@link IEmfSaveHook#afterSave}.
	 *
	 * @param saveHookFactory
	 *            the {@link IEmfSaveHook} factory (never <i>null</i>)
	 */
	public final void addSaveHook(Supplier<IEmfSaveHook<R>> saveHookFactory) {

		saveHooks.add(new EmfSaveHookWrapper<>(saveHookFactory));
	}

	public final void addValidator(Supplier<? extends IEmfValidator<R>> validatorFactory) {

		validators.add(new EmfValidatorWrapper<>(validatorFactory));
	}

	public final void setAttributeAuthorizerFactory(BiFunction<R, IBasicUser, IEmfAttributeAuthorizer<R>> attributeAuthorizerFactory) {

		this.attributeAuthorizerFactory = attributeAuthorizerFactory;
	}

	public final void setFormFactory(IEmfFormFactory<R> formFactory) {

		this.formFactory = formFactory;
	}

	public final void setCreationPredicate(IEmfPredicate<S> creationPredicate) {

		this.creationPredicate = creationPredicate;
	}

	public final void setEditPredicate(IEmfPredicate<R> editPredicate) {

		this.editPredicate = editPredicate;
	}

	public final void setDeactivationPredicate(IEmfPredicate<R> deactivationPredicate) {

		this.deactivationPredicate = deactivationPredicate;
	}

	public final void setTableRowsFinder(IEmfTableRowsFinder<R> entitiesFinder) {

		this.tableRowsFinder = Optional.of(entitiesFinder);
	}

	public final void setDeleteStrategy(IEmfDeleteStrategy<R> deleteStrategy) {

		this.deleteStrategy = deleteStrategy;
	}

	public final void setDeactivationStrategy(IEmfTableRowDeactivationStrategy<R> deactivationStrategy) {

		this.deactivationStrategy = deactivationStrategy;
	}

	// ------------------------------ display and input ------------------------------ //

	@Override
	public IResource getIcon() {

		return iconResourceSupplier.getResource();
	}

	@Override
	public final Function<R, IDomElement> getDisplayFactory() {

		return displayFactory;
	}

	@Override
	public final IEmfFormIndicatorConfiguration<R> getFormIndicatorConfiguration() {

		return indicatorConfigurationSupplier.get();
	}

	@Override
	public final IEmfFormSectionConfiguration<R> getFormSectionConfiguration() {

		return sectionConfigurationSupplier.get();
	}

	@Override
	public final IEmfFormTabConfiguration<R> getFormTabConfiguration() {

		return tabConfigurationSupplier.get();
	}

	// ------------------------------ attributes ------------------------------ //

	@Override
	public final List<IEmfAttribute<R, ?>> getAttributes() {

		return attributeListSupplier.get().getAttributes();
	}

	@Override
	public final <V> IEmfAttribute<R, V> getAttribute(ISqlField<R, V> field) {

		return attributeListSupplier.get().getAttribute(field);
	}

	@Override
	public final <V> IEmfAttribute<R, V> getAttribute(ITransientField<R, V> field) {

		return attributeListSupplier.get().getAttribute(field);
	}

	@Override
	public final IEmfAttributeAuthorizer<R> getAttributeAuthorizer(R row, IBasicUser user) {

		return attributeAuthorizerFactory.apply(row, user);
	}

	@Override
	public final IEmfAttributeDefaultValueSet<R, S> getAttributeDefaultValueSet() {

		return attributeDefaultValueSetSupplier.get();
	}

	@Override
	public final EmfAttributeDependencyMap<R> getAttributeDependencies() {

		return attributeDependencyMapSupplier.get();
	}

	// ------------------------------ actions ------------------------------ //

	@Override
	public Collection<IEmfScopeAction<S>> getScopeActions() {

		return actionSetSupplier.get().getScopeActions();
	}

	@Override
	public final Collection<IEmfPrimaryAction<R>> getPrimaryActions() {

		return actionSetSupplier.get().getPrimaryActions();
	}

	@Override
	public final Collection<IEmfCommonAction<R>> getCommonActions() {

		return actionSetSupplier.get().getCommonActions();
	}

	@Override
	public final Collection<IEmfManagementAction<R>> getManagementActions() {

		return actionSetSupplier.get().getManagementActions();
	}

	@Override
	public Collection<IEmfPrimaryActionFactory<R>> getPrimaryActionFactories() {

		return actionSetSupplier.get().getPrimaryActionFactories();
	}

	@Override
	public Collection<IEmfCommonActionFactory<R>> getCommonActionFactories() {

		return actionSetSupplier.get().getCommonActionFactories();
	}

	@Override
	public Collection<IEmfManagementActionFactory<R>> getManagementActionFactories() {

		return actionSetSupplier.get().getManagementActionFactories();
	}

	@Override
	public Collection<IEmfPrimaryAction<R>> getPrimaryActions(R tableRow) {

		var actions = new ArrayList<IEmfPrimaryAction<R>>();
		actions.addAll(tableRow.table().getPrimaryActions());
		actions.addAll(new EmfPrimaryActionGenerator<R>().generateActions(tableRow));
		return actions;
	}

	@Override
	public Collection<IEmfCommonAction<R>> getCommonActions(R tableRow) {

		var actions = new ArrayList<IEmfCommonAction<R>>();
		actions.addAll(tableRow.table().getCommonActions());
		actions.addAll(new EmfCommonActionGenerator<R>().generateActions(tableRow));
		return actions;
	}

	@Override
	public Collection<IEmfManagementAction<R>> getManagementActions(R tableRow) {

		var actions = new ArrayList<IEmfManagementAction<R>>();
		actions.addAll(tableRow.table().getManagementActions());
		actions.addAll(new EmfManagementActionGenerator<R>().generateActions(tableRow));
		return actions;
	}

	// ------------------------------ miscellaneous ------------------------------ //

	@Override
	public final Optional<ISqlForeignRowField<R, S, ?>> getScopeField() {

		return scopeField;
	}

	@Override
	public IEmfForeignRowAttributeFactory<R, P> getAttributeFactory() {

		return attributeFactory;
	}

	@Override
	public final Collection<IEmfCommitHook<R>> getCommitHooks() {

		return commitHooks;
	}

	@Override
	public final Collection<IEmfDeleteHook<R>> getDeleteHooks() {

		return deleteHooks;
	}

	@Override
	public final Collection<IEmfSaveHook<R>> getSaveHooks() {

		return saveHooks;
	}

	@Override
	public final Collection<IEmfValidator<R>> getValidators() {

		return Collections.unmodifiableCollection(validators);
	}

	@Override
	public final IEmfAuthorizer<R, S> getAuthorizer() {

		return authorizerSupplier.get();
	}

	@Override
	public IEmfFormFactory<R> getFormFactory() {

		return formFactory;
	}

	@Override
	public IEmfPredicate<S> getCreationPredicate() {

		return creationPredicate;
	}

	@Override
	public IEmfPredicate<R> getEditPredicate() {

		return editPredicate;
	}

	@Override
	public IEmfPredicate<R> getDeactivationPredicate() {

		return deactivationPredicate;
	}

	@Override
	public final Optional<IEmfTableRowsFinder<R>> getTableRowsFinder() {

		return tableRowsFinder;
	}

	@Override
	public final Collection<IEmfChangeLogger<R>> getChangeLoggers() {

		return loggerSetSupplier.get().getLoggers();
	}

	@Override
	public final Collection<IEmfTable<?, ?, R>> getChildTables() {

		return childTableSetSupplier.get().getChildTables();
	}

	@Override
	public final IEmfManagementConfiguration<R> getManagementConfiguration() {

		return managementConfigurationSupplier.get();
	}

	@Override
	public final IEmfDeleteStrategy<R> getDeleteStrategy() {

		return deleteStrategy;
	}

	@Override
	public IEmfTableRowDeactivationStrategy<R> getDeactivationStrategy() {

		return deactivationStrategy;
	}

	private void customizeAttributesAndOrder(EmfAttributeList<R, P> attributes) {

		table.customizeAttributeProperties(attributes);
		table.customizeAttributeOrdering(attributes.createReorderer());
	}

	public void setBusinessKey(IDbKey<R> businessKey) {

		checkBusinessKey(businessKey);
		this.businessKey = businessKey;
	}

	private void checkBusinessKey(IDbKey<R> businessKey) {

		if (!businessKey.isUniqueKey()) {
			throw new SofticarDeveloperException(
				"The business key (%s) of table %s must be a unique key.",
				Imploder.implode(businessKey.getFields(), " & "),
				table);
		}

		if (table.getPrimaryKey().isGenerated() && !Collections.disjoint(businessKey.getFields(), table.getPrimaryKey().getFields())) {
			throw new SofticarDeveloperException(
				"The business key (%s) of table %s must not contain a column of the generated primary key.",
				Imploder.implode(businessKey.getFields(), " & "),
				table);
		}
	}

	@Override
	public IDbKey<R> getBusinessKey() {

		return businessKey != null? businessKey : table.getPrimaryKey();
	}
}
