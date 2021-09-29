package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import java.math.BigDecimal;

public class TestTableRow {

	private Integer integerValue = null;
	private Long longValue = null;
	private Double doubleValue = null;
	private BigDecimal bigDecimalValue = null;
	private String stringValue = null;
	private Boolean booleanValue = null;
	private Day dayValue = null;
	private DayTime dayTimeValue = null;
	private TestEnum testEnumValue = null;
	private IEntity entityValue = null;

	public TestTableRow() {

		// nothing to do
	}

	public TestTableRow(Integer integerValue, Long longValue, Double doubleValue, BigDecimal bigDecimalValue, String stringValue, Boolean booleanValue,
			Day dayValue, DayTime dayTimeValue, TestEnum testEnumValue, IEntity entityValue) {

		this.integerValue = integerValue;
		this.longValue = longValue;
		this.doubleValue = doubleValue;
		this.bigDecimalValue = bigDecimalValue;
		this.stringValue = stringValue;
		this.booleanValue = booleanValue;
		this.dayValue = dayValue;
		this.dayTimeValue = dayTimeValue;
		this.testEnumValue = testEnumValue;
		this.entityValue = entityValue;
	}

	public Integer getIntegerValue() {

		return integerValue;
	}

	public Long getLongValue() {

		return longValue;
	}

	public Double getDoubleValue() {

		return doubleValue;
	}

	public BigDecimal getBigDecimalValue() {

		return bigDecimalValue;
	}

	public String getStringValue() {

		return stringValue;
	}

	public Boolean getBooleanValue() {

		return booleanValue;
	}

	public Day getDayValue() {

		return dayValue;
	}

	public DayTime getDayTimeValue() {

		return dayTimeValue;
	}

	public TestEnum getTestEnumValue() {

		return testEnumValue;
	}

	public IEntity getEntityValue() {

		return entityValue;
	}

	public TestTableRow setIntegerValue(Integer integerValue) {

		this.integerValue = integerValue;
		return this;
	}

	public TestTableRow setLongValue(Long longValue) {

		this.longValue = longValue;
		return this;
	}

	public TestTableRow setDoubleValue(Double doubleValue) {

		this.doubleValue = doubleValue;
		return this;
	}

	public TestTableRow setBigDecimalValue(BigDecimal bigDecimalValue) {

		this.bigDecimalValue = bigDecimalValue;
		return this;
	}

	public TestTableRow setStringValue(String stringValue) {

		this.stringValue = stringValue;
		return this;
	}

	public TestTableRow setBooleanValue(Boolean booleanValue) {

		this.booleanValue = booleanValue;
		return this;
	}

	public TestTableRow setDayValue(Day dayValue) {

		this.dayValue = dayValue;
		return this;
	}

	public TestTableRow setDayTimeValue(DayTime dayTimeValue) {

		this.dayTimeValue = dayTimeValue;
		return this;
	}

	public TestTableRow setTestEnumValue(TestEnum testEnumValue) {

		this.testEnumValue = testEnumValue;
		return this;
	}

	public TestTableRow setEntityValue(IEntity entityValue) {

		this.entityValue = entityValue;
		return this;
	}
}
