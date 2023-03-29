package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.common.string.Trim;
import java.util.Optional;
import java.util.function.Supplier;

public class WorkflowTransitionRequiredVotesParser {

	private final AGWorkflowTransition transition;

	public WorkflowTransitionRequiredVotesParser(AGWorkflowTransition transition) {

		this.transition = transition;
	}

	public int getRequiredVotes(Supplier<Integer> taskCountSupplier) {

		if (isValid()) {
			return getNumber().orElseGet(() -> getRequiredVotesFromPercentage(taskCountSupplier));
		} else {
			throw new IllegalRequiredVotesException(transition);
		}
	}

	public Optional<Integer> getNumber() {

		return IntegerParser.parse(transition.getRequiredVotes());
	}

	public Optional<Integer> getPercentage() {

		var requiredVotes = transition.getRequiredVotes();
		if (requiredVotes != null && requiredVotes.endsWith("%")) {
			String percentageString = Trim.trimSuffix(requiredVotes, "%");
			return IntegerParser.parse(percentageString);
		} else {
			return Optional.empty();
		}
	}

	public boolean isZero() {

		return getNumber()//
			.map(number -> number.equals(0))
			.orElse(false);
	}

	public boolean isOne() {

		return getNumber()//
			.map(number -> number.equals(1))
			.orElse(false);
	}

	public boolean isValid() {

		return isValidNumber() || isValidPercentage();
	}

	private boolean isValidNumber() {

		return getNumber()//
			.map(number -> number >= 0)
			.orElse(false);
	}

	private boolean isValidPercentage() {

		return getPercentage()//
			.map(percentage -> percentage > 0 && percentage <= 100)
			.orElse(false);
	}

	private int getRequiredVotesFromPercentage(Supplier<Integer> taskCountSupplier) {

		int percentage = getPercentage().orElseThrow(() -> new IllegalRequiredVotesException(transition));
		return (int) Math.ceil(taskCountSupplier.get() * percentage / 100.0);
	}
}
