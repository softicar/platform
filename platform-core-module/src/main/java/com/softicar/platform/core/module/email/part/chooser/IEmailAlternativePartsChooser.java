package com.softicar.platform.core.module.email.part.chooser;

import java.util.Collection;
import java.util.function.Function;
import javax.mail.Part;

public interface IEmailAlternativePartsChooser extends Function<Collection<Part>, Part> {

	// nothing to add
}
