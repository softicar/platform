package com.softicar.platform.emf.attribute.field.foreign.entity.scope;

import com.softicar.platform.common.core.entity.IEntity;
import java.util.function.Function;

/**
 * @param <E>
 *            the source type
 * @param <T>
 *            the target type
 * @param <S>
 *            the scope type
 */
public class EmfForeignEntityScope<E, T, S extends IEntity> implements IEmfForeignEntityScope<E, T> {

	private final Function<E, S> sourceScopeGetter;
	private final Function<T, S> targetScopeGetter;

	public EmfForeignEntityScope(Function<E, S> sourceScopeGetter, Function<T, S> targetScopeGetter) {

		this.sourceScopeGetter = sourceScopeGetter;
		this.targetScopeGetter = targetScopeGetter;
	}

	@Override
	public boolean isVisible(E sourceEntity, T targetEntity) {

		if (sourceEntity != null && targetEntity != null) {
			S sourceScope = sourceScopeGetter.apply(sourceEntity);
			S targetScope = targetScopeGetter.apply(targetEntity);
			return IEmfForeignEntityScope.isSame(sourceScope, targetScope);
		}
		return false;
	}
}
