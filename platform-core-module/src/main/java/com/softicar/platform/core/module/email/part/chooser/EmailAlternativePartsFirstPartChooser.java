package com.softicar.platform.core.module.email.part.chooser;

import jakarta.mail.Part;
import java.util.Collection;

/**
 * Returns the first {@link Part} from a {@link Collection} of {@link Part}
 * elements.
 *
 * @author Oliver Richers
 */
public class EmailAlternativePartsFirstPartChooser implements IEmailAlternativePartsChooser {

	/**
	 * Returns the first {@link Part} or <i>null</i> if the collection is empty.
	 */
	@Override
	public Part apply(Collection<Part> parts) {

		var iterator = parts.iterator();
		return iterator.hasNext()? iterator.next() : null;
	}
}
