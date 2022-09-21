package com.softicar.platform.db.runtime.query;

import com.softicar.platform.common.code.title.TitleFromIdentifierDeterminer;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.function.Function;

/**
 * Basic implementation of {@link IDbQueryColumn}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDbQueryColumn<R extends IDbQueryRow<R>, V> implements IDbQueryColumn<R, V> {

	private final Function<R, V> valueGetter;
	private final String name;
	private final IDisplayString title;

	/**
	 * Constructs this query column with the given parameters.
	 * <p>
	 * If no title is specified (which is usual for queries used only
	 * internally) a fallback title is generated from the column name. That
	 * title will not be translated into the language of the user.
	 *
	 * @param valueGetter
	 *            the getter to read the value from the {@link IDbQueryRow}
	 *            (never null)
	 * @param name
	 *            the name of the column (never null)
	 * @param title
	 *            the title of the column (may be null)
	 */
	public AbstractDbQueryColumn(Function<R, V> valueGetter, String name, IDisplayString title) {

		this.valueGetter = valueGetter;
		this.name = name;
		this.title = title != null? title : getFallbackTitle(name);
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public V getValue(R queryRow) {

		return valueGetter.apply(queryRow);
	}

	@Override
	public boolean isTable() {

		return false;
	}

	@Override
	public boolean isStub() {

		return false;
	}

	@Override
	public IDbTable<V, ?> getTable() {

		throw new UnsupportedOperationException("Not a table column.");
	}

	private static IDisplayString getFallbackTitle(String name) {

		return IDisplayString.create(new TitleFromIdentifierDeterminer(name).getTitle());
	}
}
