package com.softicar.platform.emf.form;

import com.softicar.platform.emf.form.configuration.EmfFormDefaultFactory;
import com.softicar.platform.emf.form.configuration.IEmfFormConfiguration;
import com.softicar.platform.emf.form.configuration.IEmfFormFactory;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Objects;

public class EmfFormConfiguration<R extends IEmfTableRow<R, ?>> implements IEmfFormConfiguration<R> {

	private IEmfFormFactory<R> formFactory;

	public EmfFormConfiguration() {

		this.formFactory = new EmfFormDefaultFactory<>();
	}

	@Override
	public IEmfFormFactory<R> getFormFactory() {

		return formFactory;
	}

	public EmfFormConfiguration<R> setFormFactory(IEmfFormFactory<R> formFactory) {

		this.formFactory = Objects.requireNonNull(formFactory);
		return this;
	}
}
