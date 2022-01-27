package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.DbBigDecimalField;
import com.softicar.platform.db.runtime.field.DbBooleanField;
import com.softicar.platform.db.runtime.field.DbByteArrayField;
import com.softicar.platform.db.runtime.field.DbDayField;
import com.softicar.platform.db.runtime.field.DbDayTimeField;
import com.softicar.platform.db.runtime.field.DbDoubleField;
import com.softicar.platform.db.runtime.field.DbEnumField;
import com.softicar.platform.db.runtime.field.DbFloatField;
import com.softicar.platform.db.runtime.field.DbForeignField;
import com.softicar.platform.db.runtime.field.DbIdField;
import com.softicar.platform.db.runtime.field.DbIntegerField;
import com.softicar.platform.db.runtime.field.DbLongField;
import com.softicar.platform.db.runtime.field.DbStringField;
import com.softicar.platform.db.runtime.field.DbTimeField;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.EmfI18n;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EmfTokenConverters {

	private final Map<Class<?>, IEmfTokenConverter<?, ? extends IDbField<?, ?>>> converters;

	public EmfTokenConverters() {

		this.converters = new HashMap<>();
		this.converters.put(DbBigDecimalField.class, new EmfTokenToBigDecimalConverter());
		this.converters.put(DbBooleanField.class, new EmfTokenToBooleanConverter());
		this.converters.put(DbByteArrayField.class, new EmfTokenToByteArrayConverter());
		this.converters.put(DbDayField.class, new EmfTokenToDayConverter());
		this.converters.put(DbDayTimeField.class, new EmfTokenToDayTimeConverter());
		this.converters.put(DbDoubleField.class, new EmfTokenToDoubleConverter());
		this.converters.put(DbEnumField.class, new EmfTokenToEnumConverter());
		this.converters.put(DbFloatField.class, new EmfTokenToFloatConverter());
		this.converters.put(DbForeignField.class, new EmfTokenToForeignConverter());
		this.converters.put(DbIdField.class, new EmfTokenToIdConverter());
		this.converters.put(DbIntegerField.class, new EmfTokenToIntegerConverter());
		this.converters.put(DbLongField.class, new EmfTokenToLongConverter());
		this.converters.put(DbStringField.class, new EmfTokenToStringConverter());
		this.converters.put(DbTimeField.class, new EmfTokenToTimeConverter());
	}

	public EmfTokenConverterResult<?> convert(IDbField<?, ?> targetField, String token) {

		Objects.requireNonNull(token);
		var converter = converters.get(targetField.getClass());
		if (converter != null) {
			return converter.convert(CastUtils.cast(targetField), token);
		} else {
			return EmfTokenConverterResult.failed(EmfI18n.THERE_IS_NO_CONVERTER_FOR_FIELD_TYPE_ARG1.toDisplay(targetField.getClass().getSimpleName()));
		}
	}
}
