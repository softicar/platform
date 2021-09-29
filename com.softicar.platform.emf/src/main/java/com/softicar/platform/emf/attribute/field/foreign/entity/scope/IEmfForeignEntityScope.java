package com.softicar.platform.emf.attribute.field.foreign.entity.scope;

import com.softicar.platform.common.core.item.IBasicItem;
import java.util.Objects;

/**
 * Checks validity of a reference between two entities.
 *
 * @param <S>
 *            the source type
 * @param <T>
 *            the target type
 * @author Oliver Richers
 */
public interface IEmfForeignEntityScope<S, T> {

	boolean isVisible(S sourceEntity, T targetEntity);

	static boolean isSame(IBasicItem itemA, IBasicItem itemB) {

		// important: we cannot compare the entities for identity because
		// this code might be used inside an auto-complete handler, where
		// multiple scope instances may exist for the same ID
		return itemA != null && itemB != null && Objects.equals(itemA.getClass(), itemB.getClass()) && Objects.equals(itemA.getId(), itemB.getId());
	}
}
