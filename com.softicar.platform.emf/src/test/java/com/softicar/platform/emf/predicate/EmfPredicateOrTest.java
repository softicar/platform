package com.softicar.platform.emf.predicate;

import java.util.Arrays;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfPredicateOrTest extends AbstractEmfPredicateTest {

	public EmfPredicateOrTest() {

		this.predicateExpression = new EmfPredicateOr<>(predicate1, predicate2, predicate3);
	}

	@Test
	public void testGetTitleMultiPredicates() {

		assertEquals("(A OR B OR C)", predicateExpression.getTitle().toString());
	}

	@Test
	public void testTestTrue() {

		Mockito.when(predicate1.test("ENTITY")).thenReturn(true);
		Mockito.when(predicate2.test("ENTITY")).thenReturn(true);
		Mockito.when(predicate3.test("ENTITY")).thenReturn(false);

		assertTrue(predicateExpression.test("ENTITY"));

	}

	@Test
	public void testTestFalse() {

		Mockito.when(predicate1.test("ENTITY")).thenReturn(false);
		Mockito.when(predicate2.test("ENTITY")).thenReturn(false);
		Mockito.when(predicate3.test("ENTITY")).thenReturn(false);

		assertFalse(predicateExpression.test("ENTITY"));

	}

	@Test
	public void testToString() {

		assertEquals("(A OR B OR C)", predicateExpression.toString());
	}

	@Test
	public void testAccept() {

		IEmfPredicateVisitor<String> visitor = Mockito.mock(IEmfPredicateVisitor.class);
		predicateExpression.accept(visitor);
		Mockito.verify(visitor).visitOr(Arrays.asList(predicate1, predicate2, predicate3));

	}
}
