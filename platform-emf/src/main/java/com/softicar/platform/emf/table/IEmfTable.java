package com.softicar.platform.emf.table;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.db.runtime.table.DbTable;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.transients.ITransientField;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.action.IEmfScopeAction;
import com.softicar.platform.emf.action.factory.IEmfCommonActionFactory;
import com.softicar.platform.emf.action.factory.IEmfManagementActionFactory;
import com.softicar.platform.emf.action.factory.IEmfPrimaryActionFactory;
import com.softicar.platform.emf.attribute.EmfAttributeReorderer;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.dependency.EmfAttributeDependencyMap;
import com.softicar.platform.emf.attribute.field.transaction.EmfTransactionAttribute;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.authorizer.IEmfAttributeAuthorizer;
import com.softicar.platform.emf.authorizer.IEmfAuthorizer;
import com.softicar.platform.emf.delete.IEmfDeleteStrategy;
import com.softicar.platform.emf.form.indicator.EmfFormIndicatorAlignment;
import com.softicar.platform.emf.form.indicator.EmfFormIndicatorConfiguration;
import com.softicar.platform.emf.form.indicator.IEmfFormIndicatorFactory;
import com.softicar.platform.emf.form.popup.EmfFormPopupConfiguration;
import com.softicar.platform.emf.form.section.EmfFormSectionConfiguration;
import com.softicar.platform.emf.form.section.IEmfFormSectionConfiguration;
import com.softicar.platform.emf.form.tab.factory.EmfFormTabConfiguration;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.log.IEmfChangeLogger;
import com.softicar.platform.emf.management.EmfManagementConfiguration;
import com.softicar.platform.emf.management.IEmfManagementConfiguration;
import com.softicar.platform.emf.sub.object.IEmfSubObject;
import com.softicar.platform.emf.sub.object.table.IEmfSubObjectTable;
import com.softicar.platform.emf.table.configuration.EmfAttributeDefaultValueSet;
import com.softicar.platform.emf.table.configuration.EmfChildTableSet;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.table.configuration.IEmfAttributeDefaultValueSet;
import com.softicar.platform.emf.table.configuration.IEmfTableConfiguration;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.table.specialization.IEmfTableSpecialization;
import com.softicar.platform.emf.table.validator.EmfTableValidator;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Interface for all {@link DbTable} classes for {@link IEmfTableRow}.
 *
 * @param <R>
 *            the type of the {@link IEmfTableRow}
 * @param <P>
 *            the type of the primary key of the {@link IDbTable}
 * @param <S>
 *            the type of the scope
 * @author Oliver Richers
 */
public interface IEmfTable<R extends IEmfTableRow<R, P>, P, S> extends IDbTable<R, P>, IDisplayable {

	@Override
	default IDisplayString toDisplay() {

		return getTitle();
	}

	// ------------------------------ convenience methods ------------------------------ //

	/**
	 * Validates the {@link IEmfTableConfiguration} of this table.
	 * <p>
	 * If the configuration is invalid, an exception will be thrown.
	 */
	@Override
	default void assertValidConfigurationOrThrow() {

		new EmfTableValidator<>(this).validate();
	}

	/**
	 * Returns the optional scope of the given {@link IEmfTableRow}.
	 *
	 * @param row
	 *            the {@link IEmfTableRow} (never null)
	 * @return the optional scope (never null)
	 */
	default Optional<S> getScope(R row) {

		return getScopeField().map(field -> field.getValue(row));
	}

	/**
	 * Validates the given {@link IEmfTableRow}.
	 *
	 * @param row
	 *            the {@link IEmfTableRow} to validate (never null)
	 * @param result
	 *            the {@link IEmfValidationResult} object (never null)
	 */
	default void validateTableRow(R row, IEmfValidationResult result) {

		getAttributes().forEach(attribute -> attribute.validate(row, result));
		getEmfTableConfiguration().getValidators().forEach(validator -> validator.validate(row, result));
	}

	/**
	 * Checks if the given {@link IEmfAttribute} is a scope attribute.
	 *
	 * @param attribute
	 *            the {@link IEmfAttribute} to test
	 * @return true if the given {@link IEmfAttribute} is a scope attribute;
	 *         false otherwise
	 */
	default boolean isScopeAttribute(IEmfAttribute<R, ?> attribute) {

		Optional<ISqlForeignRowField<R, S, ?>> scopeField = getScopeField();
		if (scopeField.isPresent()) {
			Optional<?> field = attribute.getField();
			if (field.isPresent()) {
				return field.get() == scopeField.get();
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns the optional {@link EmfTransactionAttribute} of this table.
	 *
	 * @return the {@link EmfTransactionAttribute} attribute (never null)
	 */
	default Optional<EmfTransactionAttribute<R, ?>> getTransactionAttribute() {

		// TODO make this O(1)
		for (IEmfAttribute<R, ?> attribute: getAttributes()) {
			if (attribute instanceof EmfTransactionAttribute) {
				return Optional.of((EmfTransactionAttribute<R, ?>) attribute);
			}
		}
		return Optional.empty();
	}

	/**
	 * Returns the attribute owners of this {@link IEmfTable}, always including
	 * the given {@link IEmfTableRow}.
	 * <p>
	 * In case of an {@link IEmfSubObjectTable}, this returns all bases of the
	 * given {@link IEmfSubObject} in ascending order, that is, the
	 * {@link IEmfSubObject} itself is the first element, its base the second,
	 * etc.
	 * <p>
	 * If this table is <b>not</b> an {@link IEmfSubObjectTable}, a singleton
	 * list with the given {@link IEmfTableRow} is returned.
	 *
	 * @param row
	 *            the {@link IEmfTableRow} whose attribute owners will be
	 *            determined
	 * @return all attribute owners of the given {@link IEmfTableRow} (never
	 *         null)
	 */
	default Collection<IEmfTableRow<?, ?>> getAttributeOwners(R row) {

		return Collections.singletonList(row);
	}

	// ------------------------------ configuration methods ------------------------------ //

	default IResource getIcon() {

		return getEmfTableConfiguration().getIcon();
	}

	default IDisplayString getTitle() {

		return getConfiguration().getTitle();
	}

	default IDisplayString getPluralTitle() {

		return getConfiguration().getPluralTitle();
	}

	default Function<R, IDomElement> getDisplayFactory() {

		return getEmfTableConfiguration().getDisplayFactory();
	}

	default Collection<IEmfFormIndicatorFactory<R>> getIndicatorFactories(EmfFormIndicatorAlignment alignment) {

		return getEmfTableConfiguration().getFormIndicatorConfiguration().getIndicatorFactories(alignment);
	}

	default IEmfFormSectionConfiguration<R> getFormSectionConfiguration() {

		return getEmfTableConfiguration().getFormSectionConfiguration();
	}

	default List<IEmfAttribute<R, ?>> getAttributes() {

		return getEmfTableConfiguration().getAttributes();
	}

	default <V> IEmfAttribute<R, V> getAttribute(ISqlField<R, V> field) {

		return getEmfTableConfiguration().getAttribute(field);
	}

	default <V> IEmfAttribute<R, V> getAttribute(ITransientField<R, V> field) {

		return getEmfTableConfiguration().getAttribute(field);
	}

	default IEmfAttributeAuthorizer<R> getAttributeAuthorizer(R row, IBasicUser user) {

		return getEmfTableConfiguration().getAttributeAuthorizer(row, user);
	}

	default IEmfAttributeDefaultValueSet<R, S> getAttributeDefaultValueSet() {

		return getEmfTableConfiguration().getAttributeDefaultValueSet();
	}

	default EmfAttributeDependencyMap<R> getAttributeDependencies() {

		return getEmfTableConfiguration().getAttributeDependencies();
	}

	default Collection<IEmfScopeAction<S>> getScopeActions() {

		return getEmfTableConfiguration().getScopeActions();
	}

	default Collection<IEmfPrimaryAction<R>> getPrimaryActions() {

		return getEmfTableConfiguration().getPrimaryActions();
	}

	default Collection<IEmfCommonAction<R>> getCommonActions() {

		return getEmfTableConfiguration().getCommonActions();
	}

	default Collection<IEmfManagementAction<R>> getManagementActions() {

		return getEmfTableConfiguration().getManagementActions();
	}

	default Collection<IEmfPrimaryActionFactory<R>> getPrimaryActionFactories() {

		return getEmfTableConfiguration().getPrimaryActionFactories();
	}

	default Collection<IEmfCommonActionFactory<R>> getCommonActionFactories() {

		return getEmfTableConfiguration().getCommonActionFactories();
	}

	default Collection<IEmfManagementActionFactory<R>> getManagementActionFactories() {

		return getEmfTableConfiguration().getManagementActionFactories();
	}

	default Collection<IEmfPrimaryAction<R>> getPrimaryActions(R tableRow) {

		return getEmfTableConfiguration().getPrimaryActions(tableRow);
	}

	default Collection<IEmfCommonAction<R>> getCommonActions(R tableRow) {

		return getEmfTableConfiguration().getCommonActions(tableRow);
	}

	default Collection<IEmfManagementAction<R>> getManagementActions(R tableRow) {

		return getEmfTableConfiguration().getManagementActions(tableRow);
	}

	default Optional<ISqlForeignRowField<R, S, ?>> getScopeField() {

		return getEmfTableConfiguration().getScopeField();
	}

	default IEmfAuthorizer<R, S> getAuthorizer() {

		return getEmfTableConfiguration().getAuthorizer();
	}

	default Collection<IEmfChangeLogger<R>> getChangeLoggers() {

		return getEmfTableConfiguration().getChangeLoggers();
	}

	default Collection<IEmfTable<?, ?, R>> getChildTables() {

		return getEmfTableConfiguration().getChildTables();
	}

	default IEmfManagementConfiguration<R> getManagementConfiguration() {

		return getEmfTableConfiguration().getManagementConfiguration();
	}

	default IEmfDeleteStrategy<R> getDeleteStrategy() {

		return getEmfTableConfiguration().getDeleteStrategy();
	}

	default Boolean isCreationAllowed(S scopeEntity) {

		return getAuthorizer().getCreationRole().test(scopeEntity, CurrentBasicUser.get());
	}

	default Boolean isCreationPossible(S scopeEntity) {

		return getEmfTableConfiguration().getCreationPredicate().test(scopeEntity);
	}

	IEmfTableConfiguration<R, P, S> getEmfTableConfiguration();

	/**
	 * Returns the {@link IEmfTableSpecialization} for this table.
	 *
	 * @return the {@link IEmfTableSpecialization} (never null)
	 */
	IEmfTableSpecialization<R, P, S> getTableSpecialization();

	// ------------------------------ customization methods ------------------------------ //

	default void customizeEmfTableConfiguration(EmfTableConfiguration<R, P, S> configuration) {

		DevNull.swallow(configuration);
	}

	default void customizeFormIndicators(EmfFormIndicatorConfiguration<R> indicatorConfiguration) {

		DevNull.swallow(indicatorConfiguration);
	}

	default void customizeFormSections(EmfFormSectionConfiguration<R> sectionConfiguration) {

		DevNull.swallow(sectionConfiguration);
	}

	default void customizeFormTabs(EmfFormTabConfiguration<R> tabConfiguration) {

		DevNull.swallow(tabConfiguration);
	}

	default void customizeFormPopup(EmfFormPopupConfiguration<R> popupConfiguration) {

		DevNull.swallow(popupConfiguration);
	}

	default void customizeAttributeProperties(IEmfAttributeList<R> attributes) {

		DevNull.swallow(attributes);
	}

	default void customizeAttributeOrdering(EmfAttributeReorderer<R> reorderer) {

		DevNull.swallow(reorderer);
	}

	default void customizeAttributeDefaultValues(EmfAttributeDefaultValueSet<R, S> defaultValueSet) {

		DevNull.swallow(defaultValueSet);
	}

	default void customizeAttributeDependencies(EmfAttributeDependencyMap<R> dependencyMap) {

		DevNull.swallow(dependencyMap);
	}

	default void customizeActionSet(EmfActionSet<R, S> actionSet) {

		DevNull.swallow(actionSet);
	}

	default void customizeAuthorizer(EmfAuthorizer<R, S> authorizer) {

		DevNull.swallow(authorizer);
	}

	default void customizeLoggers(EmfChangeLoggerSet<R> loggerSet) {

		DevNull.swallow(loggerSet);
	}

	default void customizeChildTables(EmfChildTableSet<R> childTableSet) {

		DevNull.swallow(childTableSet);
	}

	default void customizeManagementConfiguraton(EmfManagementConfiguration<R> configuration) {

		DevNull.swallow(configuration);
	}
}
