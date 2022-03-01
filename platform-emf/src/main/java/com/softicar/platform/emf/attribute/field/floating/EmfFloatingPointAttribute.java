package com.softicar.platform.emf.attribute.field.floating;

import com.softicar.platform.common.string.formatting.DoubleFormatter;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.input.DomFloatingPointInput;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Function;

public class EmfFloatingPointAttribute<R extends IEmfTableRow<R, ?>, V extends Number> extends EmfFieldAttribute<R, V> {

	private int precision;

	public EmfFloatingPointAttribute(IDbField<R, V> field, Function<String, V> parser) {

		super(field);
		this.precision = -1;

		setDisplayFactory(Display::new);
		setInputFactory(() -> new Input(parser));
	}

	public EmfFloatingPointAttribute<R, V> setPrecision(int precision) {

		this.precision = precision;
		return this;
	}

	protected class Display extends DomDiv {

		public Display(V value) {

			if (value != null) {
				appendText(formatValue(value));
			}
		}

		private String formatValue(V value) {

			if (precision >= 0) {
				return DoubleFormatter.formatDouble(value.doubleValue(), precision);
			} else {
				return value.toString();
			}
		}
	}

	protected class Input extends DomFloatingPointInput<V> implements IEmfInput<V> {

		public Input(Function<String, V> parser) {

			super(parser);
		}
	}
}
