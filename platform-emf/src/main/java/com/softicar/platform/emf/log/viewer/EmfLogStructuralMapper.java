package com.softicar.platform.emf.log.viewer;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.log.EmfPlainChangeLogger;
import com.softicar.platform.emf.log.IEmfChangeLogger;
import com.softicar.platform.emf.sub.entity.table.IEmfSubEntityTable;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Facilitates mapping and lookup of various structural elements related to
 * {@link IEmfTable} and {@link IEmfAttribute}.
 *
 * @author Alexander Schmidt
 */
public class EmfLogStructuralMapper<R extends IEmfTableRow<R, ?>> {

	private final Map<IDbField<?, ?>, IEmfAttribute<R, ?>> fieldAttributeMap;
	private final Collection<IEmfAttribute<R, ?>> allLoggerAttributes;

	public EmfLogStructuralMapper(IEmfTable<R, ?, ?> table) {

		Objects.requireNonNull(table);
		this.fieldAttributeMap = new IdentityHashMap<>();

		for (IEmfAttribute<R, ?> attribute: table.getAttributes()) {
			IDbField<?, ?> field = attribute.getOriginalAttribute().getField().orElse(null);
			if (field != null) {
				fieldAttributeMap.put(field, attribute);
			}
		}
		this.allLoggerAttributes = new LoggerAttributeGatherer().getAllLoggerAttributes(table);
	}

	/**
	 * Returns the {@link IEmfAttribute} that corresponds to the given
	 * {@link IDbField}.
	 * <p>
	 * Returns null if there is no corresponding {@link IEmfAttribute}, or if
	 * the given {@link IDbField} is null.
	 *
	 * @param field
	 *            the {@link IDbField} for which the corresponding
	 *            {@link IEmfAttribute} should be looked up (may be null)
	 * @return the {@link IEmfAttribute} that corresponds to the given
	 *         {@link IDbField} (may be null)
	 */
	public <V> IEmfAttribute<R, V> getAttributeOrNull(IDbField<?, V> field) {

		return getAttribute(field).orElse(null);
	}

	/**
	 * Returns the {@link IEmfAttribute} that corresponds to the given
	 * {@link IDbField}.
	 * <p>
	 * Returns {@link Optional#empty()} if there is no corresponding
	 * {@link IEmfAttribute}, or if the given {@link IDbField} is null.
	 *
	 * @param field
	 *            the {@link IDbField} for which the corresponding
	 *            {@link IEmfAttribute} should be looked up (may be null)
	 * @return the {@link IEmfAttribute} that corresponds to the given
	 *         {@link IDbField}
	 */
	public <V> Optional<IEmfAttribute<R, V>> getAttribute(IDbField<?, V> field) {

		if (field != null) {
			return Optional.ofNullable(CastUtils.cast(fieldAttributeMap.get(field)));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Determines all attributes that are logged by the loggers of the
	 * {@link IEmfTable} that was given to the constructor.
	 * <p>
	 * The lookup is transitive, so attributes of loggers of base tables (if
	 * any) are found as well.
	 * <p>
	 * Only supports {@link EmfPlainChangeLogger}s. Attributes that are logged
	 * using user kinds of loggers will be ignored.
	 *
	 * @return all attributes that are logged, as defined by the loggers
	 */
	public Collection<IEmfAttribute<R, ?>> getAllLoggerAttributes() {

		return allLoggerAttributes;
	}

	/**
	 * Converts the given {@link IEmfChangeLogger} to a
	 * {@link EmfPlainChangeLogger} if it actually is one.
	 * <p>
	 * Returns {@link Optional#empty()} if the given {@link IEmfChangeLogger} is
	 * not an {@link EmfPlainChangeLogger}, or if the given
	 * {@link IEmfChangeLogger} is null.
	 *
	 * @param logger
	 *            the {@link IEmfChangeLogger} to convert
	 * @return the given {@link IEmfChangeLogger} as an
	 *         {@link EmfPlainChangeLogger}
	 */
	public <F extends IEmfTableRow<F, ?>> Optional<EmfPlainChangeLogger<?, F, ?>> getAsPlainChangeLogger(IEmfChangeLogger<F> logger) {

		if (logger instanceof EmfPlainChangeLogger) {
			return Optional.of((EmfPlainChangeLogger<?, F, ?>) logger);
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Returns the {@link IEmfAttribute} that corresponds to the base field of
	 * the given table.
	 * <p>
	 * Returns {@link Optional#empty()} if no such attribute can be identified
	 * for the given {@link IEmfTable}, or if the given {@link IEmfTable} is
	 * null.
	 *
	 * @param table
	 *            the {@link IEmfTable} for which the base {@link IEmfAttribute}
	 *            should be looked up
	 * @return the {@link IEmfAttribute} that corresponds to the base field of
	 *         the given {@link IEmfTable}
	 */
	public Optional<? extends IEmfAttribute<R, ?>> getBaseAttribute(IEmfTable<R, ?, ?> table) {

		Optional<IEmfSubEntityTable<?, ?, ?, ?, ?>> subEntityTable = getAsSubEntityTable(table);
		if (subEntityTable.isPresent()) {
			return getAttribute(subEntityTable.get().getBaseField());
		} else {
			return Optional.empty();
		}
	}

	private Optional<IEmfTable<?, ?, ?>> getBaseTable(IEmfTable<?, ?, ?> table) {

		Optional<IEmfSubEntityTable<?, ?, ?, ?, ?>> subEntityTable = getAsSubEntityTable(table);
		if (subEntityTable.isPresent()) {
			return Optional.ofNullable(subEntityTable.get().getBaseTable());
		} else {
			return Optional.empty();
		}
	}

	private Optional<IEmfSubEntityTable<?, ?, ?, ?, ?>> getAsSubEntityTable(IEmfTable<?, ?, ?> table) {

		if (table instanceof IEmfSubEntityTable) {
			return Optional.ofNullable((IEmfSubEntityTable<?, ?, ?, ?, ?>) table);
		} else {
			return Optional.empty();
		}
	}

	private class LoggerAttributeGatherer {

		public Collection<IEmfAttribute<R, ?>> getAllLoggerAttributes(IEmfTable<?, ?, ?> table) {

			return processLevel(table);
		}

		private Collection<IEmfAttribute<R, ?>> processLevel(IEmfTable<?, ?, ?> table) {

			Collection<IEmfAttribute<R, ?>> attributes = new HashSet<>();
			attributes.addAll(getLoggerAttributes(table));
			Optional<IEmfTable<?, ?, ?>> optionalBaseTable = getBaseTable(table);
			if (optionalBaseTable.isPresent()) {
				attributes.addAll(processLevel(optionalBaseTable.get()));
			}
			return attributes;
		}

		private Collection<IEmfAttribute<R, ?>> getLoggerAttributes(IEmfTable<?, ?, ?> table) {

			Collection<IEmfAttribute<R, ?>> attributes = new HashSet<>();
			for (IEmfChangeLogger<?> changeLogger: table.getChangeLoggers()) {
				Optional<? extends EmfPlainChangeLogger<?, ?, ?>> optionalPlainChangeLogger = getAsPlainChangeLogger(changeLogger);
				if (optionalPlainChangeLogger.isPresent()) {
					optionalPlainChangeLogger//
						.get()
						.getLoggedFields()
						.stream()
						.map(field -> getAttributeOrNull(field))
						.forEach(attributes::add);
				}
			}
			return attributes;
		}
	}
}
