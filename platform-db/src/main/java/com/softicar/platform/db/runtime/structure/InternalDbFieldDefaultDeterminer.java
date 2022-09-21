package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.common.core.optional.OptionalListEvaluator;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import java.util.Optional;

class InternalDbFieldDefaultDeterminer {

	private final IDbField<?, ?> field;

	public InternalDbFieldDefaultDeterminer(IDbField<?, ?> field) {

		this.field = field;
	}

	public DbColumnDefaultType getDefaultType() {

		if (hasNoDefault()) {
			return DbColumnDefaultType.NONE;
		} else if (isDefaultCurrentTimestamp()) {
			return DbColumnDefaultType.CURRENT_TIMESTAMP;
		} else if (isDefaultNonNull()) {
			return DbColumnDefaultType.NORMAL;
		} else {
			return DbColumnDefaultType.NULL;
		}
	}

	public String getLiteralDefaultValue() {

		if (getDefaultType() == DbColumnDefaultType.NORMAL) {
			return getDefaultValueObject()//
				.map(Object::toString)
				.orElse(null);
		} else {
			return null;
		}
	}

	private boolean hasNoDefault() {

		return !field.hasDefault();
	}

	private boolean isDefaultCurrentTimestamp() {

		return isDefaultToday() || isDefaultNow();
	}

	private boolean isDefaultNow() {

		return field.cast().toDayTimeField().map(field -> field.isDefaultNow()).orElse(false);
	}

	private boolean isDefaultToday() {

		return field.cast().toDayField().map(field -> field.isDefaultToday()).orElse(false);
	}

	private boolean isDefaultNonNull() {

		return getDefaultValueObject().isPresent();
	}

	private Optional<Object> getDefaultValueObject() {

		return new OptionalListEvaluator<>()//
			.add(field.cast().toBooleanField().map(it -> it.getDefault()).map(this::getBooleanDefaultValueAsString))
			.add(field.cast().toForeignRowField().map(it -> it.getDefaultValuePk()))
			.addNullable(field.getDefault())
			.getFirst();
	}

	private String getBooleanDefaultValueAsString(Boolean value) {

		return value? "1" : "0";
	}
}
