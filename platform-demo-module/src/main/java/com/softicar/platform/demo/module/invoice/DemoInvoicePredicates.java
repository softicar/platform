package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface DemoInvoicePredicates {

	IEmfPredicate<AGDemoInvoice> LOCKED_ITEMS = new EmfPredicate<>(//
		DemoI18n.LOCKED_ITEMS,
		AGDemoInvoice::isLockedItems);

	IEmfPredicate<AGDemoInvoice> NOT_LOCKED_ITEMS = LOCKED_ITEMS.not();
}
