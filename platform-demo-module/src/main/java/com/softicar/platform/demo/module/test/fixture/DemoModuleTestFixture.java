package com.softicar.platform.demo.module.test.fixture;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.test.instance.registry.IStandardModuleTestFixture;
import com.softicar.platform.core.module.test.instance.registry.TestFixtureRegistry;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.invoice.AGDemoInvoice;
import com.softicar.platform.emf.table.IEmfTable;
import java.math.BigDecimal;

public class DemoModuleTestFixture implements DemoModuleTestFixtureMethods, IStandardModuleTestFixture<AGDemoModuleInstance> {

	private final TestFixtureRegistry registry;
	private AGDemoModuleInstance moduleInstance;

	public DemoModuleTestFixture(TestFixtureRegistry registry) {

		this.registry = registry;
	}

	@Override
	public AGDemoModuleInstance getInstance() {

		return moduleInstance;
	}

	@Override
	public IEmfTable<?, ?, ?> getTable() {

		return AGDemoModuleInstance.TABLE;
	}

	@Override
	public IStandardModuleTestFixture<AGDemoModuleInstance> apply() {

		this.moduleInstance = insertDemoModuleInstance();
		registry.getCoreModuleTestFixture().insertStandardRoleMemberships(moduleInstance);
		insertSomeInvoices();
		return this;
	}

	private void insertSomeInvoices() {

		AGDemoInvoice invoice1 = insertDemoInvoice(moduleInstance, "00001", Day.fromYMD(2020, 1, 1));
		insertDemoInvoiceItem(invoice1, "Paper", 100, BigDecimal.valueOf(12), BigDecimal.valueOf(10));
		insertDemoInvoiceItem(invoice1, "Pens", 3, BigDecimal.valueOf(3), BigDecimal.valueOf(2));

		AGDemoInvoice invoice2 = insertDemoInvoice(moduleInstance, "00002", Day.fromYMD(2020, 1, 12));
		insertDemoInvoiceItem(invoice2, "Car", 1, BigDecimal.valueOf(20123), BigDecimal.valueOf(18321));
		insertDemoInvoiceItem(invoice2, "Bikes", 2, BigDecimal.valueOf(1223), BigDecimal.valueOf(1001));

		AGDemoInvoice invoice3 = insertDemoInvoice(moduleInstance, "00003", Day.fromYMD(2020, 1, 13));
		insertDemoInvoiceItem(invoice3, "Water", 99, BigDecimal.valueOf(8), BigDecimal.valueOf(7));
		insertDemoInvoiceItem(invoice3, "Stones", 33, BigDecimal.valueOf(66), BigDecimal.valueOf(58));
		insertDemoInvoiceItem(invoice3, "Grass", 223, BigDecimal.valueOf(23), BigDecimal.valueOf(20));
	}
}
