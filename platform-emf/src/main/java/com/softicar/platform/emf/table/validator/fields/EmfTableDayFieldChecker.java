package com.softicar.platform.emf.table.validator.fields;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmfTableDayFieldChecker<R extends IEmfTableRow<R, ?>> {

	// TODO Those and similar EMF-related constants should be gathered in a central place.
	public static final Day EXPECTED_VALID_FROM_DEFAULT_VALUE = Day.get1970();
	public static final Day EXPECTED_VALID_TO_DEFAULT_VALUE = Day.fromYMD(9999, 12, 31);
	public static final String VALID_FROM_FIELD_NAME = "validFrom";
	public static final String VALID_TO_FIELD_NAME = "validTo";

	private final Optional<? extends IDbField<R, Day>> validFromField;
	private final Optional<? extends IDbField<R, Day>> validToField;

	public EmfTableDayFieldChecker(Collection<? extends IDbField<R, ?>> fields) {

		Collection<? extends IDbField<R, Day>> dayFields = filterDayFields(fields);
		this.validFromField = getNamedField(dayFields, VALID_FROM_FIELD_NAME);
		this.validToField = getNamedField(dayFields, VALID_TO_FIELD_NAME);
	}

	public boolean hasValidFromField() {

		return validFromField.isPresent();
	}

	public boolean hasValidToField() {

		return validToField.isPresent();
	}

	public boolean hasSoleValidFromField() {

		return hasValidFromField() && !hasValidToField();
	}

	public boolean hasSoleValidToField() {

		return !hasValidFromField() && hasValidToField();
	}

	public boolean hasValidFromFieldWithInvalidNullability() {

		return validFromField.map(IDbField::isNullable).orElse(false);
	}

	public boolean hasValidToFieldWithInvalidNullability() {

		return validToField.map(IDbField::isNullable).orElse(false);
	}

	public boolean hasValidFromFieldWithIllegalDefaultValue() {

		return validFromField//
			.map(IDbField::getDefault)
			.filter(Objects::nonNull)
			.filter(this::isIllegalValidFromDay)
			.isPresent();
	}

	public boolean hasValidToFieldWithIllegalDefaultValue() {

		return validToField//
			.map(IDbField::getDefault)
			.filter(Objects::nonNull)
			.filter(this::isIllegalValidToDay)
			.isPresent();
	}

	private List<IDbField<R, Day>> filterDayFields(Collection<? extends IDbField<R, ?>> fields) {

		return fields//
			.stream()
			.filter(field -> field.getValueType().getValueClass().equals(Day.class))
			.map(CastUtils::<IDbField<R, Day>> cast)
			.collect(Collectors.toList());
	}

	private Optional<? extends IDbField<R, Day>> getNamedField(Collection<? extends IDbField<R, Day>> fields, String fieldName) {

		return fields.stream().filter(field -> field.getName().equals(fieldName)).findAny();
	}

	private boolean isIllegalValidFromDay(Day day) {

		return !day.equals(EXPECTED_VALID_FROM_DEFAULT_VALUE);
	}

	private boolean isIllegalValidToDay(Day day) {

		return !day.equals(EXPECTED_VALID_TO_DEFAULT_VALUE);
	}
}
