package com.softicar.platform.common.date;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

/**
 * Base class of all date items, including {@link Year}, {@link Month},
 * {@link Week} and {@link Day}.
 * <p>
 * One common property of date items is that they all have an absolute index,
 * which enables a list of common operations, like iteration or comparison.
 *
 * @param <T>
 *            the actual type of the date item
 * @author Oliver Richers
 */
public abstract class DateItem<T extends DateItem<T>> implements Comparable<DateItem<T>> {

	/**
	 * Returns an absolute index for this date item.
	 *
	 * @return an absolute index
	 */
	public abstract int getAbsoluteIndex();

	/**
	 * Returns the date item following this date item.
	 *
	 * @return the next date item
	 */
	public T getNext() {

		return getRelative(1);
	}

	/**
	 * Returns the date item before this date item.
	 *
	 * @return the previous date item
	 */
	public T getPrevious() {

		return getRelative(-1);
	}

	/**
	 * Returns a date item relative to this item.
	 *
	 * @param index
	 *            the index of the other date item counted from this date item
	 * @return the other date item
	 */
	public abstract T getRelative(int index);

	/**
	 * Returns the distance from this item to the other item.
	 *
	 * @param other
	 *            the other item
	 * @return the distance to the other item
	 */
	public int getDistance(DateItem<T> other) {

		return other.getAbsoluteIndex() - getAbsoluteIndex();
	}

	/**
	 * Returns a range over the neighboring items.
	 *
	 * @param size
	 *            the size of the range in each direction
	 * @return a range with 2*size+1 items
	 */
	public DateItemRange<T> getRange(int size) {

		return new DateItemRange<>(getRelative(-size), getRelative(size));
	}

	/**
	 * Returns a range over the neighboring items. NOTE: fromIndex must be less
	 * or equal to toIndex
	 *
	 * @param fromIndex
	 *            index of the first item relative to this item
	 * @param toIndex
	 *            index of the last item relative to this item
	 * @return new DateItemRange<T>(getRelative(fromIndex),
	 *         getRelative(toIndex));
	 */
	public DateItemRange<T> getRange(int fromIndex, int toIndex) {

		return new DateItemRange<>(getRelative(fromIndex), getRelative(toIndex));
	}

	/**
	 * Computes the distance from the specified other {@link DateItem} to this
	 * item.
	 * <p>
	 * This is simply a subtraction of the absolute index of the other item from
	 * the absolute index of this item, and it will return a positive value if
	 * this item is later than the given item, zero if they are equal and a
	 * negative value otherwise.
	 *
	 * @param other
	 *            the other date item
	 * @return this.getAbsoluteIndex() - other.getAbsoluteIndex()
	 */
	public int minus(DateItem<T> other) {

		return getAbsoluteIndex() - other.getAbsoluteIndex();
	}

	/**
	 * Returns the absolute distance between this date item and the other.
	 *
	 * @return the absolute distance, always positive
	 */
	public int getAbsoluteDistance(DateItem<T> other) {

		return Math.abs(minus(other));
	}

	/**
	 * Compares this date item to an other date item.
	 * <p>
	 * This returns a positive value if this {@link DateItem} is later than the
	 * given {@link DateItem}, zero if equal, and otherwise a negative value.
	 *
	 * @return this.minus(other)
	 */
	@Override
	public int compareTo(DateItem<T> other) {

		return minus(other);
	}

	/**
	 * Compares this date item to another date item.
	 */
	public boolean equals(DateItem<?> other) {

		if (other == null) {
			return false;
		}
		return getAbsoluteIndex() == other.getAbsoluteIndex();
	}

	@Override
	public boolean equals(Object other) {

		if (other == null) {
			return false;
		}
		return other.getClass() == getClass() && equals((DateItem<?>) other);
	}

	@Override
	public int hashCode() {

		return getAbsoluteIndex();
	}

	public boolean isAfter(DateItem<T> other) {

		return compareTo(other) > 0;
	}

	public boolean isAfterOrEqual(DateItem<T> other) {

		return compareTo(other) >= 0;
	}

	public boolean isBefore(DateItem<T> other) {

		return compareTo(other) < 0;
	}

	public boolean isBeforeOrEqual(DateItem<T> other) {

		return compareTo(other) <= 0;
	}

	/**
	 * Determines whether this {@link DateItem} lies in the time period which is
	 * spanned by the given {@link DateItem} instances (inclusive).
	 * <p>
	 * Returns <i>false</i> if the second given {@link DateItem} is before the
	 * first given {@link DateItem}.
	 *
	 * @param first
	 *            the first {@link DateItem} of the period (never null)
	 * @param last
	 *            the last {@link DateItem} of the period (never null)
	 * @return <i>true</i> this {@link DateItem} is in the given period;
	 *         <i>false</i> otherwise
	 */
	public boolean isBetween(DateItem<T> first, DateItem<T> last) {

		return isAfterOrEqual(first) && isBeforeOrEqual(last);
	}

	/**
	 * Identifies and returns the {@link DateItem} which represents the latest
	 * point in time, among the given {@link DateItem} instances.
	 * <p>
	 * Returns <i>null</i> if the given array is empty.
	 *
	 * @param dateItems
	 *            the {@link DateItem} instances among which the latest one
	 *            shall be identified (never null)
	 * @return the latest given {@link DateItem} (may be null)
	 */
	@SafeVarargs
	public static <T extends DateItem<T>> T max(T...dateItems) {

		return max(Arrays.asList(dateItems));
	}

	/**
	 * Identifies and returns the {@link DateItem} which represents the latest
	 * point in time, among the given {@link DateItem} instances.
	 * <p>
	 * Returns <i>null</i> if the given {@link Collection} is empty.
	 *
	 * @param dateItems
	 *            the {@link DateItem} instances among which the latest one
	 *            shall be identified (never null)
	 * @return the latest given {@link DateItem} (may be null)
	 */
	public static <T extends DateItem<T>> T max(Collection<T> dateItems) {

		return dateItems.stream().filter(Objects::nonNull).sorted(Comparator.reverseOrder()).findFirst().orElse(null);
	}

	/**
	 * Identifies and returns the {@link DateItem} which represents the earliest
	 * point in time, among the given {@link DateItem} instances.
	 * <p>
	 * Returns <i>null</i> if the given array is empty.
	 *
	 * @param dateItems
	 *            the {@link DateItem} instances among which the earliest one
	 *            shall be identified (never null)
	 * @return the earliest given {@link DateItem} (may be null)
	 */
	@SafeVarargs
	public static <T extends DateItem<T>> T min(T...dateItems) {

		return min(Arrays.asList(dateItems));
	}

	/**
	 * Identifies and returns the {@link DateItem} which represents the earliest
	 * point in time, among the given {@link DateItem} instances.
	 * <p>
	 * Returns <i>null</i> if the given {@link Collection} is empty.
	 *
	 * @param dateItems
	 *            the {@link DateItem} instances among which the earliest one
	 *            shall be identified (never null)
	 * @return the earliest given {@link DateItem} (may be null)
	 */
	public static <T extends DateItem<T>> T min(Collection<T> dateItems) {

		return dateItems.stream().filter(Objects::nonNull).sorted().findFirst().orElse(null);
	}
}
