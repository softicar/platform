package com.softicar.platform.emf.attribute.field.foreign.entity.input.filtered;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;
import java.util.function.Supplier;

public class EmfEntityBasedInputFilter<R extends IEmfTableRow<R, ?>, V extends IEntity> implements IEmfEntityInputFilter {

	private final IDisplayString title;
	private final Supplier<Optional<V>> supplier;

	public EmfEntityBasedInputFilter(IEmfAttribute<R, V> attribute, R tableRow) {

		this.title = attribute.getTitle();
		this.supplier = () -> Optional.ofNullable(attribute.getValue(tableRow));
	}

	public EmfEntityBasedInputFilter(IDisplayString title, Supplier<Optional<V>> supplier) {

		this.title = title;
		this.supplier = supplier;
	}

	@Override
	public boolean isActive() {

		return supplier.get().isPresent();
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public IDisplayString getValue() {

		Optional<V> value = supplier.get();
		return value.isPresent()? value.get().toDisplay() : IDisplayString.EMPTY;
	}
}
