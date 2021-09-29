package com.softicar.platform.emf.attribute.field.bigdecimal;

import com.softicar.platform.db.runtime.field.IDbBigDecimalField;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.math.BigDecimal;

public class EmfBigDecimalAttribute<R extends IEmfTableRow<R, ?>> extends EmfFieldAttribute<R, BigDecimal> {

	private final IDbBigDecimalField<R> bigDecimalField;

	public EmfBigDecimalAttribute(IDbBigDecimalField<R> field) {

		super(field);
		this.bigDecimalField = field;

		setDisplayFactory(EmfBigDecimalDisplay::new);
		setInputFactory(() -> new EmfBigDecimalInput(getScale()));
	}

	public int getScale() {

		return bigDecimalField.getScale();
	}
}
