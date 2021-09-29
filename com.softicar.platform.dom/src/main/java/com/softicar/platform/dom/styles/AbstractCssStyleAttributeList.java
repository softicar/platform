package com.softicar.platform.dom.styles;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.dom.style.ICssStyleAttribute;
import java.util.List;

/**
 * A list of {@link ICssStyleAttribute} elements.
 *
 * @author Oliver Richers
 */
public abstract class AbstractCssStyleAttributeList<T extends ICssStyleAttribute> implements ICssStyleAttribute {

	private final List<T> attributes;
	private final String separator;

	public AbstractCssStyleAttributeList(List<T> attributes, String separator) {

		this.attributes = attributes;
		this.separator = separator;
	}

	@Override
	public String getValue() {

		return Imploder.implode(attributes, separator);
	}
}
