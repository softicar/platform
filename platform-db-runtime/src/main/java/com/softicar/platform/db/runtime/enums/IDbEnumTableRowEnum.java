package com.softicar.platform.db.runtime.enums;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;
import java.util.Objects;

/**
 * Represents an enum value of an {@link IDbEnumTableRow}.
 *
 * @author Oliver Richers
 */
public interface IDbEnumTableRowEnum<E extends IDbEnumTableRowEnum<E, R>, R extends IDbEnumTableRow<R, E>> extends IStaticObject {

	/**
	 * Returns the associated {@link IDbEnumTable} containing the records.
	 *
	 * @return the {@link IDbEnumTable} (never null)
	 */
	IDbEnumTable<R, E> getTable();

	/**
	 * Returns the ID of this enum value and the corresponding
	 * {@link IDbEnumTableRow}.
	 *
	 * @return the ID (never null)
	 */
	Integer getId();

	/**
	 * Returns the name of this enum value.
	 *
	 * @return the name (never null)
	 */
	String name();

	/**
	 * Returns the {@link IDbEnumTableRow} corresponding to this enum value.
	 *
	 * @return the corresponding {@link IDbEnumTableRow} (never null)
	 */
	default R getRecord() {

		Integer id = Objects.requireNonNull(getId());
		// TODO PLAT-755 This casting magic should not be necessary.
		return CastUtils.cast(IDbTable.class.cast(getTable()).getStub(id));
	}

	/**
	 * Initializes the fields of the consumer with the field values of this enum
	 * value.
	 * <p>
	 * For each field of the {@link IDbEnumTableRow}, a value is assigned. The
	 * assigned value may be null.
	 *
	 * @param consumer
	 *            the consumer (never null)
	 */
	void setFields(ISqlFieldValueConsumer<R> consumer);
}
