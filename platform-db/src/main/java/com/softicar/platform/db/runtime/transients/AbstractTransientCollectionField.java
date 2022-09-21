package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Defines a transient field with values of type {@link List}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractTransientCollectionField<O extends IDbTableRow<O, ?>, C, E> extends AbstractTransientAccumulativeField<O, C, E> {

	private final C emptyCollection;
	private final Supplier<C> factory;
	private final BiConsumer<C, E> adder;

	public AbstractTransientCollectionField(ITransientFieldValueType<C> valueType, C emptyCollection, Supplier<C> factory, BiConsumer<C, E> adder) {

		super(valueType);

		this.emptyCollection = emptyCollection;
		this.factory = factory;
		this.adder = adder;
	}

	@Override
	protected final C getDefaultValue() {

		return emptyCollection;
	}

	@Override
	protected final C combine(C collection, E element) {

		if (collection == emptyCollection) {
			collection = factory.get();
		}
		adder.accept(collection, element);
		return collection;
	}
}
