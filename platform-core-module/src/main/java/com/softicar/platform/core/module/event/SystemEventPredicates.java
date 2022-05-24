package com.softicar.platform.core.module.event;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public interface SystemEventPredicates {

	IEmfPredicate<AGSystemEvent> NEEDS_ATTENTION = new EmfPredicate<>(CoreI18n.NEEDS_ATTENTION, AGSystemEvent::isNeedsAttention);
}
