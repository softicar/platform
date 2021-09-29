package com.softicar.platform.db.runtime.enums;

import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.table.configuration.DbTableConfiguration;
import com.softicar.platform.db.runtime.table.row.getter.DbTableRowGetterLoadAllStrategy;
import com.softicar.platform.db.sql.statement.ISqlInsert;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Default implementation of {@link IDbEnumTable}.
 *
 * @author Oliver Richers
 */
public class DbEnumTable<R extends IDbEnumTableRow<R, E>, E extends IDbEnumTableRowEnum<E, R>> extends DbObjectTable<R> implements IDbEnumTable<R, E> {

	private final Map<Integer, E> enumMap;
	private final Collection<E> enums;

	public DbEnumTable(IDbObjectTableBuilder<R> builder, E[] enums) {

		super(builder);
		this.enumMap = new TreeMap<>();
		this.enums = Arrays.asList(enums);
		this.enums.forEach(value -> enumMap.put(value.getId(), value));
	}

	@Override
	public Collection<E> getEnums() {

		return enums;
	}

	@Override
	public E getEnumById(Integer id) {

		return enumMap.get(id);
	}

	@Override
	protected void customizeTableConfiguration(DbTableConfiguration<R, Integer> configuration) {

		configuration.setDataInitializer(this::initializeData);
		configuration.setTableRowGetterStrategy(new DbTableRowGetterLoadAllStrategy<>(this));
	}

	private void initializeData() {

		ISqlInsert<R> insert = createInsert();
		for (E value: getEnums()) {
			value.setFields(insert);
			insert.nextRow();
		}
		insert.executeWithoutIdGeneration();
	}
}
