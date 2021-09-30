package com.softicar.platform.common.io.mime;

/**
 * Interface for media type descriptions.
 *
 * @author Oliver Richers
 */
public interface IMimeType {

	/**
	 * Returns the standardized identifier of this media type.
	 *
	 * @return the media type identifier
	 */
	String getIdentifier();

	static IMimeType createCustom(String customMimeType) {

		return new CustomMimeType(customMimeType);
	}
}
