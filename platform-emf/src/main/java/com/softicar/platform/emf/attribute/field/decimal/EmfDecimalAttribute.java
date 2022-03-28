package com.softicar.platform.emf.attribute.field.decimal;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.number.BigDecimalMapper;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.elements.number.decimal.DomDecimalDisplay;
import com.softicar.platform.dom.elements.number.decimal.DomDecimalInput;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.attribute.field.EmfFieldAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Objects;

public class EmfDecimalAttribute<R extends IEmfTableRow<R, ?>, V extends Number> extends EmfFieldAttribute<R, V> {

	private final BigDecimalMapper<V> mapper;
	private Integer scale;

	public EmfDecimalAttribute(IDbField<R, V> field, BigDecimalMapper<V> mapper) {

		super(field);

		this.mapper = mapper;
		this.scale = null;

		setDisplayFactory(Display::new);
		setInputFactory(Input::new);
	}

	/**
	 * Defines the number of decimal places (if given) for this display.
	 * <p>
	 * See {@link DomDecimalDisplay#setScale} and
	 * {@link DomDecimalInput#setScale} for more information.
	 *
	 * @param scale
	 *            the number of decimal places; or <i>null</i> to perform no
	 *            scaling
	 * @return this
	 */
	public EmfDecimalAttribute<R, V> setScale(Integer scale) {

		this.scale = scale;
		return this;
	}

	private class Display extends DomDecimalDisplay<V> {

		public Display(V value) {

			super(mapper);

			setScale(scale);
			setValue(value);
		}
	}

	private class Input extends DomDecimalInput<V> implements IEmfInput<V>, IDomChangeEventHandler {

		private INullaryVoidFunction callback;

		public Input() {

			super(mapper);

			setScale(scale);
			this.callback = INullaryVoidFunction.NO_OPERATION;
		}

		@Override
		public void setValueAndHandleChangeCallback(V value) {

			setValue(value);
			callback.apply();
		}

		@Override
		public void setChangeCallback(INullaryVoidFunction callback) {

			this.callback = Objects.requireNonNull(callback);
		}

		@Override
		public void handleChange(IDomEvent event) {

			callback.apply();
		}
	}
}
