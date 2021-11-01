package com.softicar.platform.emf.table.configuration;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class EmfAttributeDefaultValueSet<R extends IEmfTableRow<R, ?>, S> implements IEmfAttributeDefaultValueSet<R, S> {

	private final IEmfTable<R, ?, S> table;
	private final Map<IEmfAttribute<R, ?>, DefaultValue<?>> defaultValues;

	public EmfAttributeDefaultValueSet(IEmfTable<R, ?, S> table) {

		this.table = table;
		this.defaultValues = new HashMap<>();
	}

	@Override
	public void applyTo(R row, S scope) {

		defaultValues.values().forEach(value -> value.applyTo(row, scope));
	}

	public <V> EmfAttributeDefaultValueSet<R, S> setValue(IDbField<R, V> field, V value) {

		return setValue(table.getAttribute(field), value);
	}

	public <V> EmfAttributeDefaultValueSet<R, S> setValue(IEmfAttribute<R, V> attribute, V value) {

		return setSupplier(attribute, scope -> value);
	}

	public <V> EmfAttributeDefaultValueSet<R, S> setSupplier(IDbField<R, V> field, Supplier<V> valueSupplier) {

		return setSupplier(table.getAttribute(field), valueSupplier);
	}

	public <V> EmfAttributeDefaultValueSet<R, S> setSupplier(IEmfAttribute<R, V> attribute, Supplier<V> valueSupplier) {

		return setSupplier(attribute, scope -> valueSupplier.get());
	}

	public <V> EmfAttributeDefaultValueSet<R, S> setSupplier(IDbField<R, V> field, Function<S, V> valueSupplier) {

		return setSupplier(table.getAttribute(field), valueSupplier);
	}

	public <V> EmfAttributeDefaultValueSet<R, S> setSupplier(IEmfAttribute<R, V> attribute, Function<S, V> valueSupplier) {

		defaultValues.put(attribute, new DefaultValue<>(attribute, valueSupplier));
		return this;
	}

	private class DefaultValue<V> {

		private final IEmfAttribute<R, V> attribute;
		private final Function<S, V> supplier;

		public DefaultValue(IEmfAttribute<R, V> attribute, Function<S, V> supplier) {

			this.attribute = attribute;
			this.supplier = supplier;
		}

		public void applyTo(R row, S scope) {

			attribute.setValue(row, supplier.apply(scope));
		}
	}
}
