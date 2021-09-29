package com.softicar.platform.emf.attribute.field.enums;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfEnumAttribute<R extends IEmfTableRow<R, ?>, N extends Enum<N>> extends EmfFieldAttribute<R, N> {

	public EmfEnumAttribute(IDbField<R, N> field) {

		super(field);

		setDisplayFactory(EmfEnumDisplay::new);
		setInputFactory(() -> new EmfEnumInput<>(field.getValueType().getValueClass()));
	}

	@SuppressWarnings("unchecked")
	public static <R extends IEmfTableRow<R, ?>, E extends Enum<E>> EmfEnumAttribute<R, E> create(IDbField<R, ?> field) {

		if (field.getFieldType() != SqlFieldType.ENUM) {
			throw new SofticarDeveloperException("Field %s of %s is not an enum field.", field.getName(), field.getTable().getFullName());
		}

		return new EmfEnumAttribute<>((IDbField<R, E>) field);
	}
}
