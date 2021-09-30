package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.IDisplayString;
import org.junit.Assert;
import org.mockito.Mockito;

public abstract class AbstractEmfPredicateTest extends Assert {

	protected final IEmfPredicate<String> predicate1 = Mockito.mock(IEmfPredicate.class);
	protected final IEmfPredicate<String> predicate2 = Mockito.mock(IEmfPredicate.class);
	protected final IEmfPredicate<String> predicate3 = Mockito.mock(IEmfPredicate.class);
	protected IEmfPredicate<String> predicateExpression;

	public AbstractEmfPredicateTest() {

		Mockito.when(predicate1.getTitle()).thenReturn(IDisplayString.create("A"));
		Mockito.when(predicate2.getTitle()).thenReturn(IDisplayString.create("B"));
		Mockito.when(predicate3.getTitle()).thenReturn(IDisplayString.create("C"));
	}
}
