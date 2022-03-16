package com.softicar.platform.common.core.utils.equals;

import com.softicar.platform.common.core.utils.CastUtils;
import java.io.Serializable;
import java.util.function.Function;

abstract class EqualsComparerBase<T> implements IEqualsComparer<T>, Serializable {

	@Override
	public boolean compareToObject(T thisObject, Object otherObject) {

		if (thisObject == otherObject) {
			return true;
		} else if (thisObject != null && otherObject != null) {
			Class<? extends T> thisClass = CastUtils.cast(thisObject.getClass());
			if (thisClass.isInstance(otherObject)) {
				return compare(thisObject, thisClass.cast(otherObject));
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public <K> IEqualsComparer<T> comparing(Function<? super T, K> keyExtractor) {

		return comparing(new EqualsKeyExtractor<>(keyExtractor));
	}

	@Override
	public IEqualsComparer<T> comparing(IEqualsComparer<? super T> otherComparator) {

		return new EqualsComposer<>(this, otherComparator);
	}
}
