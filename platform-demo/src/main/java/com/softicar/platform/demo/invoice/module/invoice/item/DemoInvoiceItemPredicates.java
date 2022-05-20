package com.softicar.platform.demo.invoice.module.invoice.item;

import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface DemoInvoiceItemPredicates {

	IEmfPredicate<AGDemoInvoiceItem> LOCKED_ITEMS = new EmfPredicate<>(//
		DemoI18n.LOCKED_ITEMS,
		item -> item.getInvoice().isLockedItems());

	IEmfPredicate<AGDemoInvoiceItem> NOT_LOCKED_ITEMS = LOCKED_ITEMS.not();
}
