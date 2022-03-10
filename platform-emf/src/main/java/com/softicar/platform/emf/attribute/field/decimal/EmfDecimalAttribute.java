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
import java.util.Optional;

public class EmfDecimalAttribute<R extends IEmfTableRow<R, ?>, V extends Number> extends EmfFieldAttribute<R, V> {

	private final BigDecimalMapper<V> mapper;
	private Optional<Integer> scale;

	public EmfDecimalAttribute(IDbField<R, V> field, BigDecimalMapper<V> mapper) {

		super(field);

		this.mapper = mapper;
		this.scale = Optional.empty();

		setDisplayFactory(Display::new);
		setInputFactory(Input::new);
	}

	public EmfDecimalAttribute<R, V> setScale(int scale) {

		this.scale = Optional.of(scale);
		return this;
	}

	private class Display extends DomDecimalDisplay<V> {

		public Display(V value) {

			super(mapper);

			scale.ifPresent(this::setScale);
			setValue(value);
		}
	}

	private class Input extends DomDecimalInput<V> implements IEmfInput<V>, IDomChangeEventHandler {

		private INullaryVoidFunction callback;

		public Input() {

			super(mapper);

			scale.ifPresent(this::setScale);
			this.callback = INullaryVoidFunction.NO_OPERATION;
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
