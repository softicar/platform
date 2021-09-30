package com.softicar.platform.emf.authorization;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Optional;
import java.util.function.Function;

public interface IEmfTableRowMapper<S, T> {

	IDisplayString getTitle();

	Optional<T> apply(S entity);

	static <S, T> EmfTableRowMapper<S, T> optional(IDisplayString title, Function<S, Optional<T>> mapperFunction) {

		return new EmfTableRowMapper<>(title, mapperFunction);
	}

	static <S, T> EmfTableRowMapper<S, T> nonOptional(IDisplayString title, Function<S, T> mapperFunction) {

		return new EmfTableRowMapper<>(title, entity -> Optional.ofNullable(mapperFunction.apply(entity)));
	}
}
