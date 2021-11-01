package com.softicar.platform.core.module.cron;

import com.softicar.platform.core.module.cron.CronExpression.Part;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class CronParserTest extends Assert {

	private interface Expression {

		String SIMPLE = "0 * * * *";
		String ADVANCED = "0,30 6-18/2 * * 7";
		String LETTERS = "* * * FEB,JUL-OCT TUE-THU,SUN";
		String MISSING_ARGUMENTS = "* * *";
		String INVALID_ARGUMENTS = "A * B * *";
		String SURPLUS_ARGUMENTS = SIMPLE + " * * *";
		String ARGUMENTS_OUT_OF_RANGE = "0-60 24 * * *";
	}

	@Test
	public void testParseWithSimpleExpression() {

		var expression = CronParser.parse(Expression.SIMPLE);

		new Asserter(expression)//
			.assertValid()
			.assertNoErrors()
			.assertString(Expression.SIMPLE);
	}

	@Test
	public void testParseWithAdvancedExpression() {

		var expression = CronParser.parse(Expression.ADVANCED);

		new Asserter(expression)//
			.assertValid()
			.assertNoErrors()
			.assertString(Expression.ADVANCED);
	}

	@Test
	public void testParseWithLetterBasedExpression() {

		var expression = CronParser.parse(Expression.LETTERS);

		new Asserter(expression)//
			.assertValid()
			.assertNoErrors()
			.assertString("* * * 2,7-10 2-4,7");
	}

	@Test
	public void testParseWithSundayIndex() {

		var expression = CronParser.parse("* * * * 0");

		new Asserter(expression)//
			.assertValid()
			.assertNoErrors()
			.assertString("* * * * 7");
	}

	@Test
	public void testParseWithSundayIndexMapped() {

		var expression = CronParser.parse("* * * * 7");

		new Asserter(expression)//
			.assertValid()
			.assertNoErrors()
			.assertString("* * * * 7");
	}

	@Test
	public void testParseWithSurplusArguments() {

		var expression = CronParser.parse(Expression.SURPLUS_ARGUMENTS);

		new Asserter(expression)//
			.assertInvalid()
			.assertNoError(Part.MINUTE)
			.assertNoError(Part.HOUR)
			.assertNoError(Part.DAY)
			.assertNoError(Part.MONTH)
			.assertNoError(Part.WEEKDAY)
			.assertError(Part.SURPLUS_TOKENS)
			.assertString(Expression.SURPLUS_ARGUMENTS);
	}

	@Test
	public void testParseWithInsufficientArguments() {

		var expression = CronParser.parse(Expression.MISSING_ARGUMENTS);

		new Asserter(expression)//
			.assertInvalid()
			.assertNoError(Part.MINUTE)
			.assertNoError(Part.HOUR)
			.assertNoError(Part.DAY)
			.assertError(Part.MONTH)
			.assertError(Part.WEEKDAY)
			.assertNoError(Part.SURPLUS_TOKENS)
			.assertString(Expression.MISSING_ARGUMENTS);
	}

	@Test
	public void testParseWithInvalidArguments() {

		var expression = CronParser.parse(Expression.INVALID_ARGUMENTS);

		new Asserter(expression)//
			.assertInvalid()
			.assertError(Part.MINUTE)
			.assertNoError(Part.HOUR)
			.assertError(Part.DAY)
			.assertNoError(Part.MONTH)
			.assertNoError(Part.WEEKDAY)
			.assertNoError(Part.SURPLUS_TOKENS)
			.assertString(Expression.INVALID_ARGUMENTS);
	}

	@Test
	public void testParseWithArgumentsOutOfRange() {

		var expression = CronParser.parse(Expression.ARGUMENTS_OUT_OF_RANGE);

		new Asserter(expression)//
			.assertInvalid()
			.assertError(Part.MINUTE)
			.assertError(Part.HOUR)
			.assertNoError(Part.DAY)
			.assertNoError(Part.MONTH)
			.assertNoError(Part.WEEKDAY)
			.assertNoError(Part.SURPLUS_TOKENS)
			.assertString(Expression.ARGUMENTS_OUT_OF_RANGE);
	}

	private static class Asserter {

		private final CronExpression expression;

		public Asserter(CronExpression expression) {

			this.expression = expression;
		}

		public Asserter assertValid() {

			assertTrue(expression.isValid());
			return this;
		}

		public Asserter assertInvalid() {

			assertFalse(expression.isValid());
			return this;
		}

		public Asserter assertError(Part part) {

			assertTrue(isErrorMessagePresent(part));
			return this;
		}

		public Asserter assertNoError(Part part) {

			assertFalse(isErrorMessagePresent(part));
			return this;
		}

		public Asserter assertNoErrors() {

			Arrays.asList(Part.values()).forEach(this::assertNoError);
			return this;
		}

		public Asserter assertString(String expected) {

			assertEquals(expected, expression.toString());
			return this;
		}

		private boolean isErrorMessagePresent(Part part) {

			return expression.getErrorMessage(part).isPresent();
		}
	}
}
