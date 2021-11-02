package com.softicar.platform.emf.attribute.field.indirect.entity.foreign;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.thread.utils.ThreadSafeLazySupplier;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInMemoryInputEngine;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.attribute.field.indirect.entity.IEmfIndirectEntity;
import com.softicar.platform.emf.attribute.field.string.EmfStringDisplay;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EmfForeignIndirectEntityAttribute<R extends IEmfTableRow<R, ?>, F extends IEmfEntity<F, ?>> extends EmfForeignRowAttribute<R, F> {

	public EmfForeignIndirectEntityAttribute(IDbForeignRowField<R, F, ?> field) {

		super(field);
	}

	/**
	 * Defines a loader that provides the entities which are valid values of
	 * this attribute.
	 *
	 * @param <I>
	 *            the {@link IEmfIndirectEntity} type
	 * @param loader
	 *            a {@link Supplier} that provides the entities (never
	 *            <i>null</i>)
	 * @return this {@link EmfForeignIndirectEntityAttribute}
	 */
	public <I extends IEmfIndirectEntity<F>> EmfForeignIndirectEntityAttribute<R, F> setEntityLoader(Supplier<Collection<I>> loader) {

		var factory = new UiElementFactory<>(loader, this);
		setDisplayFactory(factory::createDisplay);
		setInputFactory(factory::createInput);
		return this;
	}

	/**
	 * Provides factory methods to create display and input elements.
	 * <p>
	 * Implemented using {@link ThreadSafeLazySupplier} because:
	 * <ul>
	 * <li>The {@link IEmfIndirectEntity} loader may be invoked from several
	 * threads.</li>
	 * <li>Repeated invocations of the {@link IEmfIndirectEntity} loader shall
	 * be avoided for the sake of performance.</li>
	 * </ul>
	 * <p>
	 * TODO Thread synchronization should not be necessary here.
	 */
	private static class UiElementFactory<R extends IEmfTableRow<R, ?>, F extends IEmfEntity<F, ?>, I extends IEmfIndirectEntity<F>> {

		private final ThreadSafeLazySupplier<Map<ItemId, IEmfIndirectEntity<F>>> lazyLoader;
		private final IEmfAttribute<?, ?> attribute;

		public UiElementFactory(Supplier<Collection<I>> loader, IEmfAttribute<?, ?> attribute) {

			this.lazyLoader = new ThreadSafeLazySupplier<>(() -> createMap(loader));
			this.attribute = attribute;
		}

		public IDomElement createDisplay(F entity) {

			return new EmfStringDisplay(getDisplayString(entity));
		}

		public IEmfInput<F> createInput() {

			EmfEntityInput<F> input = new EmfEntityInput<>(new InputEngine());
			input.setPlaceholder(attribute.getTitle());
			return input;
		}

		private IDisplayString getDisplayString(F entity) {

			if (entity != null) {
				return Optional//
					.ofNullable(entity.getItemId())
					.map(lazyLoader.get()::get)
					.map(IEmfIndirectEntity::toDisplay)
					.orElse(entity.toDisplay());
			} else {
				return IDisplayString.EMPTY;
			}
		}

		private Map<ItemId, IEmfIndirectEntity<F>> createMap(Supplier<Collection<I>> loader) {

			return loader//
				.get()
				.stream()
				.filter(Objects::nonNull)
				.filter(this::hasItemId)
				.collect(Collectors.toMap(this::getItemId, Function.identity()));
		}

		private boolean hasItemId(I indirectEntity) {

			return getItemId(indirectEntity) != null;
		}

		private ItemId getItemId(I indirectEntity) {

			return indirectEntity.getRepresentedEntity().getItemId();
		}

		private class InputEngine extends DomAutoCompleteEntityInMemoryInputEngine<F> {

			public InputEngine() {

				setItems(loadItems());
			}

			@Override
			public IDisplayString getDisplayString(F entity) {

				return UiElementFactory.this.getDisplayString(entity);
			}

			private List<F> loadItems() {

				return lazyLoader//
					.get()
					.values()
					.stream()
					.map(IEmfIndirectEntity::getRepresentedEntity)
					.sorted(Comparator.comparing(this::getDisplayString))
					.collect(Collectors.toList());
			}
		}
	}
}
