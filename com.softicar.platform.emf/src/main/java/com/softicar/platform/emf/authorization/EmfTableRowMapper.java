package com.softicar.platform.emf.authorization;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Optional;
import java.util.function.Function;

class EmfTableRowMapper<S, T> implements IEmfTableRowMapper<S, T> {

	private final IDisplayString title;
	private final Function<S, Optional<T>> mapperFunction;

	public EmfTableRowMapper(IDisplayString title, Function<S, Optional<T>> mapperFunction) {

		this.title = title;
		this.mapperFunction = mapperFunction;
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public Optional<T> apply(S entity) {

		return mapperFunction.apply(entity);
	}

}
