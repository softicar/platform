package com.softicar.platform.emf.attribute.field.foreign.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInputEngine;
import com.softicar.platform.emf.attribute.field.foreign.entity.scope.EmfForeignEntityScope;
import com.softicar.platform.emf.attribute.field.foreign.entity.scope.IEmfForeignEntityScope;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class EmfForeignEntityAttribute<R extends IEmfTableRow<R, ?>, F extends IEmfEntity<F, ?>> extends EmfForeignRowAttribute<R, F> {

	private final EmfForeignEntityAttributeValidator<R, F> validator;

	public EmfForeignEntityAttribute(IDbForeignRowField<R, F, ?> field) {

		super(field);

		this.validator = new EmfForeignEntityAttributeValidator<>();

		setInputFactoryByEntity(this::createForeignInput);
	}

	public EmfForeignEntityAttribute<R, F> setScope(IEmfForeignEntityScope<R, F> scope) {

		validator.setScope(scope);
		return this;
	}

	public <S extends IEntity> EmfForeignEntityAttribute<R, F> setScope(Function<R, S> sourceScopeGetter, Function<F, S> targetScopeGetter) {

		validator.setScope(new EmfForeignEntityScope<>(sourceScopeGetter, targetScopeGetter));
		return this;
	}

	public EmfForeignEntityAttribute<R, F> addValidatorCondition(BiPredicate<R, F> validation) {

		validator.addPredicate(validation);
		return this;
	}

	public EmfForeignEntityAttribute<R, F> addValidatorCondition(Predicate<F> validation) {

		validator.addPredicate((e, t) -> validation.test(t));
		return this;
	}

	@Override
	public IEmfEntityTable<F, ?, ?> getTargetTable() {

		return (IEmfEntityTable<F, ?, ?>) super.getTargetTable();
	}

	private IEmfInput<F> createForeignInput(R tableRow) {

		EmfEntityInput<F> input = new EmfEntityInput<>(new EmfEntityInputEngine<>(getTargetTable()).addValidator(value -> validator.test(tableRow, value)));
		input.setPlaceholder(getTitle());
		return input;
	}
}
