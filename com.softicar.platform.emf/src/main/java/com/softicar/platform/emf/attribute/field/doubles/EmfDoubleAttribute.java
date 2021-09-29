package com.softicar.platform.emf.attribute.field.doubles;

import com.softicar.platform.common.core.number.parser.DoubleParser;
import com.softicar.platform.common.string.formatting.DoubleFormatter;
import com.softicar.platform.db.runtime.field.IDbDoubleField;
import com.softicar.platform.emf.attribute.field.floating.AbstractEmfFloatingPointAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfDoubleAttribute<R extends IEmfTableRow<R, ?>> extends AbstractEmfFloatingPointAttribute<R, Double> {

	public EmfDoubleAttribute(IDbDoubleField<R> field) {

		super(field);
	}

	public EmfDoubleAttribute<R> setPrecision(int precision) {

		this.precision = precision;
		return this;
	}

	@Override
	public String formatValue(Double value) {

		return DoubleFormatter.formatDouble(value, precision);
	}

	@Override
	public boolean isParseable(String text) {

		return DoubleParser.isDouble(text);
	}

	@Override
	public Double parseValue(String text) {

		return DoubleParser.parseDouble(text);
	}
}
