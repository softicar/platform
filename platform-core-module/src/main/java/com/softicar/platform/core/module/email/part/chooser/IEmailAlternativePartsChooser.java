package com.softicar.platform.core.module.email.part.chooser;

import jakarta.mail.Part;
import java.util.Collection;
import java.util.function.Function;

public interface IEmailAlternativePartsChooser extends Function<Collection<Part>, Part> {

	// nothing to add
}
