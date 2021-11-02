package com.softicar.platform.common.core.item;

/**
 * Represents the ID of an {@link IBasicItem}.
 * <p>
 * It is intentional that this class provides no conversion to {@link Integer},
 * to {@link Long} or any other {@link Number}. The rationale is that working
 * with IDs is an error prone task and should be avoided. Basing any business
 * logic on untyped IDs is not a good idea, use actual business objects instead.
 * Usually, the only valid reason to work with IDs is to display them to the
 * user.
 *
 * @author Oliver Richers
 */
public class ItemId implements Comparable<ItemId> {

	private final long id;

	/**
	 * Constructs this ID from the given {@link Long} value.
	 *
	 * @param id
	 *            the long value representing this ID
	 */
	public ItemId(long id) {

		this.id = id;
	}

	@Override
	public int compareTo(ItemId other) {

		return Long.compare(id, other.id);
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof ItemId) {
			return id == ((ItemId) other).id;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Long.hashCode(id);
	}

	@Override
	public String toString() {

		return "" + id;
	}
}
