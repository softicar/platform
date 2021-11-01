package com.softicar.platform.common.io.xml.parser;

import com.softicar.platform.common.core.number.parser.IntegerParser;
import java.util.Objects;

/**
 * Allows for index-based addressing of XML nodes with recurring tags.
 * <p>
 * Indexes are zero-based.
 * <p>
 * Example:
 *
 * <pre>
 * {@code
 * <OUTER>
 *     <A>foo</A>        // tag A, index 0
 *     <A>bar</A>        // tag A, index 1
 *     <B>baz</B>        // tag B, index 0
 *     <A>xxx</A>        // tag A, index 2
 *     <C>42</C>         // tag C, index 0
 *     <C>yyy</C>        // tag C, index 1
 * </OUTER>
 * }
 * </pre>
 *
 * @author Alexander Schmidt
 */
public class XmlTag implements IXmlTag {

	private final String name;
	private final int index;

	/**
	 * Creates a new {@link XmlTag} with its name and zero-based index derived
	 * from the given identifier.
	 * <p>
	 * Name and index are derived as follows:
	 *
	 * <pre>
	 * identifier="foo"     =>  name="foo", index=0
	 * identifier="bar:0"   =>  name="bar", index=0
	 * identifier="baz:22"  =>  name="baz", index=22
	 * </pre>
	 *
	 * @param identifier
	 *            the tag name, optionally accompanied by an index (never
	 *            <i>null</i>)
	 * @return the new {@link XmlTag} (never <i>null</i>)
	 * @throws IllegalArgumentException
	 *             if the given name is empty, or if it contains several colons
	 * @throws IllegalArgumentException
	 *             if the encoded index is negative, not an integer, or missing
	 *             despite the presence of a colon
	 */
	public static XmlTag create(String identifier) {

		String[] tokens = identifier.split(":");

		if (tokens.length == 1 || tokens.length == 2) {
			String name = tokens[0];
			int index = 0;

			if (tokens.length == 2) {
				index = IntegerParser//
					.parse(tokens[1])
					.orElseThrow(IllegalArgumentException::new);
			}

			return create(name, index);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Creates a new {@link XmlTag} with the given name and index.
	 *
	 * @param name
	 *            the tag name (never <i>null</i>)
	 * @param index
	 *            the index of the tag, in a list of equally-named tags
	 * @return the new {@link XmlTag} (never <i>null</i>)
	 * @throws IllegalArgumentException
	 *             if the given name is empty, or if the given index is negative
	 */
	public static XmlTag create(String name, int index) {

		return new XmlTag(name, index);
	}

	private XmlTag(String name, int index) {

		this.name = validateName(name);
		this.index = validateIndex(index);
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public int getIndex() {

		return index;
	}

	private String validateName(String name) {

		Objects.requireNonNull(name);
		if (name.isBlank()) {
			throw new IllegalArgumentException();
		}
		return name;
	}

	private int validateIndex(int index) {

		if (index < 0) {
			throw new IllegalArgumentException();
		}
		return index;
	}
}
