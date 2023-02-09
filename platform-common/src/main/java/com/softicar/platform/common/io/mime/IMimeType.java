package com.softicar.platform.common.io.mime;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface for media type descriptions.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public interface IMimeType {

	/**
	 * Returns the standardized identifier of this media type.
	 *
	 * @return the media type identifier
	 */
	String getIdentifier();

	Collection<String> getParameters();

	Optional<String> getParameter(String parameter);

	/**
	 * Determines whether this {@link IMimeType} is equal to the given
	 * {@link IMimeType}.
	 *
	 * @param otherMimeType
	 *            the {@link IMimeType} to check against (never <i>null</i>)
	 * @return <i>true</i> if the types are the same; <i>false</i> otherwise
	 */
	default boolean is(IMimeType otherMimeType) {

		return getIdentifier().toLowerCase().equals(otherMimeType.getIdentifier().toLowerCase());
	}

	static IMimeType createCustom(String customMimeType) {

		return new CustomMimeType(customMimeType);
	}
}
