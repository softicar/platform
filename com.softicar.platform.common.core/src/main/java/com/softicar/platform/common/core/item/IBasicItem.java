package com.softicar.platform.common.core.item;

/**
 * A basic item is an entity with a unique ID.
 *
 * @author Oliver Richers
 */
public interface IBasicItem extends Comparable<IBasicItem> {

	/**
	 * Returns the ID of this item.
	 *
	 * @return the item ID or null
	 */
	Integer getId();

	/**
	 * Return the ID of this item.
	 *
	 * @return the item ID or null
	 */
	default ItemId getItemId() {

		Integer id = getId();
		return id != null? new ItemId(id) : null;
	}
}
