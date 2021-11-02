package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.sql.fields.ISqlEnumField;
import java.util.Optional;

public interface IDbEnumField<R, E extends Enum<E>> extends IDbPrimitiveField<R, E>, ISqlEnumField<R, E> {

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
	 * Returns the {@link Enum} class of this field.
	 *
	 * @return the {@link Enum} class (never null)
	 */
	Class<E> getEnumClass();
}
