package com.softicar.platform.emf.attribute.field;

import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * A list of editable {@link EmfFieldAttribute} references.
 *
 * @author Alexander Schmidt
 */
public class EmfFieldAttributeList<R extends IEmfTableRow<R, ?>> {

	private final List<EmfFieldAttribute<R, ?>> attributes;

	/**
	 * Constructs a new {@link EmfFieldAttributeList} from the given
	 * {@link EmfFieldAttribute} references.
	 *
	 * @param attributes
	 *            the attributes (never <i>null</i>)
	 */
	public EmfFieldAttributeList(List<EmfFieldAttribute<R, ?>> attributes) {

		this.attributes = Objects.requireNonNull(attributes);
	}

	/**
	 * Applies the given {@link Consumer} to each {@link EmfFieldAttribute} in
	 * this list.
	 *
	 * @param consumer
	 */
	public void apply(Consumer<EmfFieldAttribute<R, ?>> consumer) {

		attributes.forEach(consumer);
	}
}
