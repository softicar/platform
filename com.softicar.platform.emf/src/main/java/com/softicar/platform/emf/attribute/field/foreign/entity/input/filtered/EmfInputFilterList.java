package com.softicar.platform.emf.attribute.field.foreign.entity.input.filtered;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public class EmfInputFilterList {

	private final Collection<IEmfEntityInputFilter> filters;

	public EmfInputFilterList() {

		this.filters = new ArrayList<>();
	}

	public EmfInputFilterList addFilter(IEmfEntityInputFilter filter) {

		filters.add(filter);
		return this;
	}

	public <R extends IEmfTableRow<R, ?>, V extends IEntity> EmfInputFilterList addFilter(IEmfAttribute<R, V> attribute, R tableRow) {

		return addFilter(new EmfEntityBasedInputFilter<>(attribute, tableRow));
	}

	public <V extends IEntity> EmfInputFilterList addFilter(IDisplayString title, Supplier<Optional<V>> valueSupplier) {

		return addFilter(new EmfEntityBasedInputFilter<>(title, valueSupplier));
	}

	public Collection<IEmfEntityInputFilter> getFilters() {

		return filters;
	}

}
