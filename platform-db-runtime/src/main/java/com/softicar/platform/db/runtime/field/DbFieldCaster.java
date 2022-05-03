package com.softicar.platform.db.runtime.field;

import java.util.Objects;
import java.util.Optional;

/**
 * Provides methods to convert an {@link IDbField} to specialized field types.
 *
 * @author Alexander Schmidt
 */
public class DbFieldCaster<R, V> {

	private final IDbField<R, V> field;

	public DbFieldCaster(IDbField<R, V> field) {

		this.field = Objects.requireNonNull(field);
	}

	public Optional<IDbBaseField<R, ?, ?>> toBaseField() {

		if (field instanceof IDbBaseField<?, ?, ?>) {
			return Optional.of((IDbBaseField<R, ?, ?>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbBigDecimalField<R>> toBigDecimalField() {

		if (field instanceof IDbBigDecimalField<?>) {
			return Optional.of((IDbBigDecimalField<R>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbBooleanField<R>> toBooleanField() {

		if (field instanceof IDbBooleanField<?>) {
			return Optional.of((IDbBooleanField<R>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbByteArrayField<R>> toByteArrayField() {

		if (field instanceof IDbByteArrayField<?>) {
			return Optional.of((IDbByteArrayField<R>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbDayField<R>> toDayField() {

		if (field instanceof IDbDayField<?>) {
			return Optional.of((IDbDayField<R>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbDayTimeField<R>> toDayTimeField() {

		if (field instanceof IDbDayTimeField<?>) {
			return Optional.of((IDbDayTimeField<R>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbDoubleField<R>> toDoubleField() {

		if (field instanceof IDbDoubleField<?>) {
			return Optional.of((IDbDoubleField<R>) field);
		} else {
			return Optional.empty();
		}
	}

	public Optional<IDbEnumField<R, ?>> toEnumField() {

		if (field instanceof IDbEnumField<?, ?>) {
			return Optional.of((IDbEnumField<R, ?>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbFloatField<R>> toFloatField() {

		if (field instanceof IDbFloatField<?>) {
			return Optional.of((IDbFloatField<R>) field);
		} else {
			return Optional.empty();
		}
	}

	public Optional<IDbForeignField<R, ?>> toForeignField() {

		if (field instanceof IDbForeignField<?, ?>) {
			return Optional.of((IDbForeignField<R, ?>) field);
		} else {
			return Optional.empty();
		}
	}

	public Optional<IDbForeignRowField<R, ?, ?>> toForeignRowField() {

		if (field instanceof IDbForeignRowField<?, ?, ?>) {
			return Optional.of((IDbForeignRowField<R, ?, ?>) field);
		} else {
			return Optional.empty();
		}
	}

	public Optional<IDbIdField<?>> toIdField() {

		if (field instanceof IDbIdField<?>) {
			return Optional.of((IDbIdField<?>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbIntegerField<R>> toIntegerField() {

		if (field instanceof IDbIntegerField<?>) {
			return Optional.of((IDbIntegerField<R>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbLongField<R>> toLongField() {

		if (field instanceof IDbLongField<?>) {
			return Optional.of((IDbLongField<R>) field);
		} else {
			return Optional.empty();
		}
	}

	public Optional<IDbPrimitiveField<R, V>> toPrimitiveField() {

		if (field instanceof IDbPrimitiveField<?, ?>) {
			return Optional.of((IDbPrimitiveField<R, V>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbStringField<R>> toStringField() {

		if (field instanceof IDbStringField<?>) {
			return Optional.of((IDbStringField<R>) field);
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public Optional<IDbTimeField<R>> toTimeField() {

		if (field instanceof IDbTimeField<?>) {
			return Optional.of((IDbTimeField<R>) field);
		} else {
			return Optional.empty();
		}
	}
}
