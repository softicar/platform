package com.softicar.platform.emf.attribute.field.floats;

import com.softicar.platform.common.core.number.parser.FloatParser;
import com.softicar.platform.common.string.formatting.DoubleFormatter;
import com.softicar.platform.db.runtime.field.IDbFloatField;
import com.softicar.platform.emf.attribute.field.floating.AbstractEmfFloatingPointAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFloatAttribute<R extends IEmfTableRow<R, ?>> extends AbstractEmfFloatingPointAttribute<R, Float> {

	public EmfFloatAttribute(IDbFloatField<R> field) {

		super(field);
	}

	public EmfFloatAttribute<R> setPrecision(int precision) {

		this.precision = precision;
		return this;
	}

	@Override
	public String formatValue(Float value) {

		return DoubleFormatter.formatDouble(value, precision);
	}

	@Override
	public boolean isParseable(String text) {

		return FloatParser.isFloat(text);
	}

	@Override
	public Float parseValue(String text) {

		return FloatParser.parseFloat(text);
	}
}
