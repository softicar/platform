package com.softicar.platform.common.core.java.classes.name;

import java.util.Objects;
import java.util.Optional;

/**
 * Determines {@link JavaClassName} instances.
 *
 * @author Alexander Schmidt
 */
public class JavaClassNameDeterminer {

	/**
	 * Determines a {@link JavaClassName} from a given field descriptor.
	 * <p>
	 * If the given field descriptor does not designate a class, or if it is an
	 * empty {@link String}, {@link Optional#empty()} is returned.
	 *
	 * @param fieldDescriptor
	 *            the field descriptor (never <i>null</i>)
	 * @return the {@link JavaClassName}
	 */
	public Optional<JavaClassName> fromFieldDescriptor(String fieldDescriptor) {

		Objects.requireNonNull(fieldDescriptor);
		String output = fieldDescriptor;
		if (output.endsWith(";")) {
			output = output.substring(0, output.length() - 1);
			if (output.startsWith("[")) {
				output = output.substring(1);
			}
			if (output.startsWith("L")) {
				output = output.substring(1);
				return Optional.of(new JavaClassName(output));
			}
		}
		return Optional.empty();
	}
}
