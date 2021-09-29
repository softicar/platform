package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class TestTableRowSupplier implements Supplier<List<TestTableRow>> {

	private final List<TestTableRow> rows;

	public TestTableRowSupplier() {

		//@formatter:off
		rows = new ArrayList<>();
		rows.add(new TestTableRow(2, 22l,  222d, new BigDecimal("22.22"), "bbb", false, Day.today().getRelative(-2), DayTime.now().minusDays(2), TestEnum.BAZ, new TestEntity(2)));
		rows.add(new TestTableRow(1, 11l,  111d, new BigDecimal("11.11"), "aaa", true,  Day.today().getRelative(-1), DayTime.now().minusDays(1), TestEnum.BAR, new TestEntity(1)));
		rows.add(new TestTableRow(3, 33l,  333d, new BigDecimal("33.33"), "ccc", true,  Day.today().getRelative(-3), DayTime.now().minusDays(3), TestEnum.FOO, new TestEntity(3)));
		rows.add(new TestTableRow(4, 44l,  444d, new BigDecimal("44.44"), "ddd", false, Day.today().getRelative(-4), DayTime.now().minusDays(4), TestEnum.BAZ, new TestEntity(4)));
		rows.add(new TestTableRow(5, 55l,  555d, new BigDecimal("55.55"), "",    true,  Day.today().getRelative(-5), DayTime.now().minusDays(5), null,         new TestEntity(5)));
		rows.add(new TestTableRow(6, null, null, null,                    null,  null,  null,                        null,                       null,         null));
		//@formatter:on

		requireDistinctIntegerValues();
	}

	@Override
	public List<TestTableRow> get() {

		return rows;
	}

	private void requireDistinctIntegerValues() {

		int numDistinctIntegerValues = rows.stream().map(row -> row.getIntegerValue()).collect(Collectors.toSet()).size();
		if (numDistinctIntegerValues != rows.size()) {
			throw new IllegalArgumentException("Expected distinct integer values but encountered at least one redundant value.");
		}
	}
}
