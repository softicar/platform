package com.softicar.platform.common.io.xml.parser;

/**
 * Represents an XML tag with a name and an index.
 *
 * @author Alexander Schmidt
 */
@FunctionalInterface
public interface IXmlTag {

	/**
	 * The name of the tag.
	 *
	 * @return the tag name (never <i>null</i>)
	 */
	public String getName();

	/**
	 * The zero-based index of the tag, relative to equally-named tags under the
	 * same parent node.
	 * <p>
	 * Zero by default.
	 *
	 * @return the zero-based tag index
	 */
	default int getIndex() {

		return 0;
	}
}
