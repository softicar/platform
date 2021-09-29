package com.softicar.platform.emf.form.popup;

import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Configuration for {@link EmfFormPopup}.
 *
 * @author Daniel Klose
 */
public class EmfFormPopupConfiguration<R extends IEmfTableRow<R, ?>> implements IEmfFormPopupConfiguration<R> {

	private final Collection<IEmfFormPopupContentFactory<R>> additionalContentFactories;

	public EmfFormPopupConfiguration() {

		this.additionalContentFactories = new ArrayList<>();
	}

	@Override
	public Collection<IDomElement> createAdditionalContent(R tableRow) {

		return additionalContentFactories//
			.stream()
			.map(factory -> factory.create(tableRow))
			.collect(Collectors.toList());
	}

	public EmfFormPopupConfiguration<R> addAdditionalContent(IEmfFormPopupContentFactory<R> contentFactory) {

		this.additionalContentFactories.add(Objects.requireNonNull(contentFactory));
		return this;
	}
}
