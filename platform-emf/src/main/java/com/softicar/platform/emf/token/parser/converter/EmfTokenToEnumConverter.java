package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.runtime.field.IDbEnumField;
import com.softicar.platform.emf.EmfI18n;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class EmfTokenToEnumConverter extends AbstractEmfTokenConverter<Enum<?>, IDbEnumField<?, ?>> {

	@Override
	protected EmfTokenConverterResult<Enum<?>> convertToken(IDbEnumField<?, ?> field, String token) {

		Class<?> targetClass = field.getValueType().getValueClass();
		try {
			Enum<?> value = Enum.valueOf(CastUtils.cast(targetClass), token);
			return EmfTokenConverterResult.okay(value);
		} catch (Exception exception) {
			DevNull.swallow(exception);

			IDisplayString message = EmfI18n.ENUMERATOR_ARG1_COULD_NOT_BE_FOUND_IN_ENUMERATION_ARG2.toDisplay(token, targetClass.getSimpleName());
			List<String> enumerators = getEnumerators(targetClass);
			if (!enumerators.isEmpty()) {
				message = message//
					.concatSpace()
					.concat(EmfI18n.AVAILABLE_VALUES)
					.concatColon()
					.concatSpace()
					.concat(Imploder.implode(enumerators, ", "));
			}
			return EmfTokenConverterResult.failed(message);
		}
	}

	@Override
	public IDisplayString getTypeDescription() {

		return EmfI18n.ENUMERATOR;
	}

	private List<String> getEnumerators(Class<?> targetClass) {

		return Arrays//
			.asList(targetClass.getEnumConstants())
			.stream()
			.filter(Objects::nonNull)
			.map(it -> (Enum<?>) it)
			.map(Enum::name)
			.collect(Collectors.toList());
	}
}
