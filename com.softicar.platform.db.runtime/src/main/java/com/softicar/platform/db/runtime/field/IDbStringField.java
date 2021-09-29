package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.sql.fields.ISqlStringField;
import java.util.Optional;

/**
 * Represents a database field of type {@link String}.
 * <p>
 * Database fields of type {@link String} can be either physically or logically
 * limited in length. The method {@link #getMaximumLength()} determines the
 * logical limit, while {@link #getLengthBits()} determines the physical limit.
 *
 * @author Oliver Richers
 */
public interface IDbStringField<R> extends IDbPrimitiveField<R, String>, ISqlStringField<R> {

	/**
	 * Returns the character set of this field.
	 *
	 * @return the optional character set
	 */
	Optional<String> getCharacterSet();

	/**
	 * Returns the collation of this field.
	 *
	 * @return the optional collation
	 */
	Optional<String> getCollation();

	/**
	 * Returns the maximum length of this field in characters.
	 * <p>
	 * If the returned maximum length is zero, then the length of this field is
	 * not logically limited. Instead, a physical limit of the length exists,
	 * which is determined by {@link #getLengthBits()}.
	 * <p>
	 * This method is guaranteed to return a positive value if and only if
	 * {@link #getLengthBits()} returns zero.
	 *
	 * @return the maximum text length (may be zero)
	 */
	int getMaximumLength();

	/**
	 * Returns the number of bits used to encode the length of this field.
	 * <p>
	 * The amount of bits determines the physical maximum length of this field.
	 * Possible values are 0, 8, 16, 24 and 32. A value of 0 implies that this
	 * field is not physically limited in length but rather logically through
	 * {@link #getMaximumLength()}.
	 * <p>
	 * This method is guaranteed to return a positive value if and only if
	 * {@link #getMaximumLength()} returns zero.
	 *
	 * @return the number of length bits (may be zero)
	 */
	int getLengthBits();
}
