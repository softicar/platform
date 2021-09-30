package com.softicar.platform.emf.attribute.field.indirect.entity;

import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.emf.entity.IEmfEntity;

/**
 * Indirectly represents an {@link IEmfEntity} while not having
 * {@link IEmfEntity} characteristics.
 *
 * @param <E>
 *            the type of the represented {@link IEmfEntity}
 * @author Alexander Schmidt
 */
public interface IEmfIndirectEntity<E extends IEmfEntity<E, ?>> extends IDisplayable {

	/**
	 * Returns the {@link IEmfEntity} which is represented by this instance.
	 *
	 * @return the represented {@link IEmfEntity} (never <i>null</i>)
	 */
	E getRepresentedEntity();
}
