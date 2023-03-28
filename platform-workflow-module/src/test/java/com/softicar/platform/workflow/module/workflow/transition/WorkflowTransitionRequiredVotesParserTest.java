package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class WorkflowTransitionRequiredVotesParserTest extends AbstractDbTest {

	private final AGWorkflowTransition transition;
	private final WorkflowTransitionRequiredVotesParser parser;

	public WorkflowTransitionRequiredVotesParserTest() {

		this.transition = new AGWorkflowTransition().setRequiredVotes(null);
		this.parser = new WorkflowTransitionRequiredVotesParser(transition);
	}

	// ------------------------------ invalid ------------------------------ //

	@Test
	public void testWithNull() {

		transition.setRequiredVotes(null);

		assertFalse(parser.isValid());
		assertFalse(parser.isZero());
		assertFalse(parser.isOne());
		assertEmpty(parser.getNumber());
		assertEmpty(parser.getPercentage());
		assertException(SofticarUserException.class, () -> parser.getRequiredVotes(this::taskCountSupplier));
	}

	@Test
	public void testWithEmptyString() {

		transition.setRequiredVotes("");

		assertFalse(parser.isValid());
		assertFalse(parser.isZero());
		assertFalse(parser.isOne());
		assertEmpty(parser.getNumber());
		assertEmpty(parser.getPercentage());
		assertException(SofticarUserException.class, () -> parser.getRequiredVotes(this::taskCountSupplier));
	}

	// ------------------------------ number ------------------------------ //

	@Test
	public void testWithEmptyNegativeNumber() {

		transition.setRequiredVotes("-1");

		assertFalse(parser.isValid());
		assertFalse(parser.isZero());
		assertFalse(parser.isOne());
		assertEquals(-1, assertOne(parser.getNumber()));
		assertEmpty(parser.getPercentage());
		assertException(SofticarUserException.class, () -> parser.getRequiredVotes(this::taskCountSupplier));
	}

	@Test
	public void testWithZero() {

		transition.setRequiredVotes("0");

		assertTrue(parser.isValid());
		assertTrue(parser.isZero());
		assertFalse(parser.isOne());
		assertEquals(0, assertOne(parser.getNumber()));
		assertEmpty(parser.getPercentage());
		assertEquals(0, parser.getRequiredVotes(this::taskCountSupplier));
	}

	@Test
	public void testWithOne() {

		transition.setRequiredVotes("1");

		assertTrue(parser.isValid());
		assertFalse(parser.isZero());
		assertTrue(parser.isOne());
		assertEquals(1, assertOne(parser.getNumber()));
		assertEmpty(parser.getPercentage());
		assertEquals(1, parser.getRequiredVotes(this::taskCountSupplier));
	}

	@Test
	public void testWithThree() {

		transition.setRequiredVotes("3");

		assertTrue(parser.isValid());
		assertFalse(parser.isZero());
		assertFalse(parser.isOne());
		assertEquals(3, assertOne(parser.getNumber()));
		assertEmpty(parser.getPercentage());
		assertEquals(3, parser.getRequiredVotes(this::taskCountSupplier));
	}

	// ------------------------------ percentage ------------------------------ //

	@Test
	public void testWithZeroPercentage() {

		transition.setRequiredVotes("0%");

		assertFalse(parser.isValid());
		assertFalse(parser.isZero());
		assertFalse(parser.isOne());
		assertEmpty(parser.getNumber());
		assertEquals(0, assertOne(parser.getPercentage()));
		assertException(SofticarUserException.class, () -> parser.getRequiredVotes(this::taskCountSupplier));
	}

	@Test
	public void testWithTooHighPercentage() {

		transition.setRequiredVotes("130%");

		assertFalse(parser.isValid());
		assertFalse(parser.isZero());
		assertFalse(parser.isOne());
		assertEmpty(parser.getNumber());
		assertEquals(130, assertOne(parser.getPercentage()));
		assertException(SofticarUserException.class, () -> parser.getRequiredVotes(this::taskCountSupplier));
	}

	@Test
	public void testWith33Percent() {

		transition.setRequiredVotes("33%");

		assertTrue(parser.isValid());
		assertFalse(parser.isZero());
		assertFalse(parser.isOne());
		assertEmpty(parser.getNumber());
		assertEquals(33, assertOne(parser.getPercentage()));
		assertEquals(0, parser.getRequiredVotes(() -> 0));
		assertEquals(1, parser.getRequiredVotes(() -> 1));
		assertEquals(1, parser.getRequiredVotes(() -> 2));
		assertEquals(1, parser.getRequiredVotes(() -> 3));
		assertEquals(2, parser.getRequiredVotes(() -> 4));
		assertEquals(2, parser.getRequiredVotes(() -> 5));
		assertEquals(2, parser.getRequiredVotes(() -> 6));
	}

	@Test
	public void testWith50Percent() {

		transition.setRequiredVotes("50%");

		assertTrue(parser.isValid());
		assertFalse(parser.isZero());
		assertFalse(parser.isOne());
		assertEmpty(parser.getNumber());
		assertEquals(50, assertOne(parser.getPercentage()));
		assertEquals(0, parser.getRequiredVotes(() -> 0));
		assertEquals(1, parser.getRequiredVotes(() -> 1));
		assertEquals(1, parser.getRequiredVotes(() -> 2));
		assertEquals(2, parser.getRequiredVotes(() -> 3));
		assertEquals(2, parser.getRequiredVotes(() -> 4));
		assertEquals(3, parser.getRequiredVotes(() -> 5));
		assertEquals(3, parser.getRequiredVotes(() -> 6));
	}

	@Test
	public void testWith100Percent() {

		transition.setRequiredVotes("100%");

		assertTrue(parser.isValid());
		assertFalse(parser.isZero());
		assertFalse(parser.isOne());
		assertEmpty(parser.getNumber());
		assertEquals(100, assertOne(parser.getPercentage()));
		assertEquals(0, parser.getRequiredVotes(() -> 0));
		assertEquals(1, parser.getRequiredVotes(() -> 1));
		assertEquals(2, parser.getRequiredVotes(() -> 2));
		assertEquals(3, parser.getRequiredVotes(() -> 3));
	}

	private Integer taskCountSupplier() {

		throw new UnsupportedOperationException();
	}
}
