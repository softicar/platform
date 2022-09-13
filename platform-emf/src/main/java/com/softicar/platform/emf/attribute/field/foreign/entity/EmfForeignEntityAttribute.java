package com.softicar.platform.emf.attribute.field.foreign.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInputEngine;
import com.softicar.platform.emf.attribute.field.foreign.entity.scope.EmfForeignEntityScope;
import com.softicar.platform.emf.attribute.field.foreign.entity.scope.IEmfForeignEntityScope;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class EmfForeignEntityAttribute<R extends IEmfTableRow<R, ?>, F extends IEmfEntity<F, ?>> extends EmfForeignRowAttribute<R, F> {

	private final EmfForeignEntityAttributeValidator<R, F> validator;
	private Function<R, IDomAutoCompleteInputEngine<F>> inputEngineFactory;

	public EmfForeignEntityAttribute(IDbForeignRowField<R, F, ?> field) {

		super(field);

		this.validator = new EmfForeignEntityAttributeValidator<>();
		this.inputEngineFactory = this::createDefaultEngine;

		setInputFactoryByEntity(this::createDefaultInput);
	}

	@Override
	public IEmfEntityTable<F, ?, ?> getTargetTable() {

		return (IEmfEntityTable<F, ?, ?>) super.getTargetTable();
	}

	/**
	 * Sets the {@link IEmfForeignEntityScope} of the attribute values.
	 * <p>
	 * <b>Important:</b> This method has only effect if no custom input, custom
	 * input engine or custom value loader has been defined on this attribute.
	 *
	 * @param scope
	 *            the {@link IEmfForeignEntityScope} (never <i>null</i>)
	 * @return this
	 */
	public EmfForeignEntityAttribute<R, F> setScope(IEmfForeignEntityScope<R, F> scope) {

		validator.setScope(scope);
		return this;
	}

	/**
	 * Sets the scope getters of the owning {@link IEmfTableRow} and the
	 * attribute values.
	 * <p>
	 * <b>Important:</b> This method has only effect if no custom input, custom
	 * input engine or custom value loader has been defined on this attribute.
	 *
	 * @param sourceScopeGetter
	 *            the scope getter for the attribute owner (never <i>null</i>)
	 * @param targetScopeGetter
	 *            the scope getter for the attribute values (never <i>null</i>)
	 * @return this
	 */
	public <S extends IEntity> EmfForeignEntityAttribute<R, F> setScope(Function<R, S> sourceScopeGetter, Function<F, S> targetScopeGetter) {

		validator.setScope(new EmfForeignEntityScope<>(sourceScopeGetter, targetScopeGetter));
		return this;
	}

	/**
	 * Adds a filter {@link BiPredicate} for the set of allowed attribute
	 * values.
	 * <p>
	 * <b>Important:</b> This method has only effect if no custom input, custom
	 * input engine or custom value loader has been defined on this attribute.
	 *
	 * @param predicate
	 *            the filtering {@link BiPredicate} (never <i>null</i>)
	 * @return this
	 */
	public EmfForeignEntityAttribute<R, F> addFilter(BiPredicate<R, F> predicate) {

		validator.addPredicate(predicate);
		return this;
	}

	/**
	 * Adds a filter {@link Predicate} for the set of allowed attribute values.
	 * <p>
	 * <b>Important:</b> This method has only effect if no custom input, custom
	 * input engine or custom value loader has been defined on this attribute.
	 *
	 * @param predicate
	 *            the filtering {@link Predicate} (never <i>null</i>)
	 * @return this
	 */
	public EmfForeignEntityAttribute<R, F> addFilter(Predicate<F> predicate) {

		validator.addPredicate((e, t) -> predicate.test(t));
		return this;
	}

	/**
	 * Defines a custom input engine factory for the {@link EmfEntityInput}.
	 *
	 * @param inputEngineFactory
	 *            the factory (never <i>null</i>)
	 * @return this
	 */
	public final EmfForeignRowAttribute<R, F> setInputEngine(Supplier<IDomAutoCompleteInputEngine<F>> inputEngineFactory) {

		this.inputEngineFactory = dummy -> inputEngineFactory.get();
		return this;
	}

	/**
	 * Defines a custom input engine factory for the {@link EmfEntityInput}.
	 *
	 * @param inputEngineFactory
	 *            the factory (never <i>null</i>)
	 * @return this
	 */
	public final EmfForeignRowAttribute<R, F> setInputEngine(Function<R, IDomAutoCompleteInputEngine<F>> inputEngineFactory) {

		this.inputEngineFactory = inputEngineFactory;
		return this;
	}

	/**
	 * Defines a custom value loader for the {@link EmfEntityInput}.
	 *
	 * @param valueLoader
	 *            the value loader (never <i>null</i>)
	 * @return this
	 */
	public final EmfForeignRowAttribute<R, F> setValueLoader(Supplier<Collection<F>> valueLoader) {

		return setInputEngine(() -> new DomAutoCompleteDefaultInputEngine<>(valueLoader));
	}

	/**
	 * Defines a custom value loader for the {@link EmfEntityInput}.
	 *
	 * @param valueLoader
	 *            the value loader (never <i>null</i>)
	 * @return this
	 */
	public final EmfForeignRowAttribute<R, F> setValueLoader(Function<R, Collection<F>> valueLoader) {

		return setInputEngine(it -> new DomAutoCompleteDefaultInputEngine<>(() -> valueLoader.apply(it)));
	}

	private IEmfInput<F> createDefaultInput(R row) {

		var input = new EmfEntityInput<>(inputEngineFactory.apply(row));
		input.setPlaceholder(getTitle());
		return input;
	}

	private EmfEntityInputEngine<F, ?> createDefaultEngine(R row) {

		var engine = new EmfEntityInputEngine<>(getTargetTable());
		engine.addValidator(value -> validator.test(row, value));
		return engine;
	}
}
