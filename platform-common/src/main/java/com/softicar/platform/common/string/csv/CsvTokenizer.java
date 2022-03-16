package com.softicar.platform.common.string.csv;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Extracts value tokens from a CSV-formatted {@link String}.
 * <p>
 * Assumes the following special characters:
 * <ul>
 * <li>commas {@code [,]} as value separators</li>
 * <li>quotes {@code ["]} as value delimiters</li>
 * <li>newlines {@code [\r or \r\n]} as row separators</li>
 * </ul>
 * <p>
 * Assumes the following CSV formatting rules:
 * <ul>
 * <li>A value is enclosed in quotes {@code ["]} if it contains at least one of
 * the following characters: {@code [,][\r][\n]["]}</li>
 * <li>Quotes {@code ["]} inside values are escaped via duplication
 * {@code ["] -> [""]}</li>
 * <li>Superfluous quotes {@code ["]} around values are tolerated</li>
 * </ul>
 * <p>
 * Retains line breaks in values, as long as the values are quoted. This way, an
 * extracted <i>logical row</i> can emerge from several <i>physical rows</i> in
 * the original CSV {@link String}.
 * <p>
 * Implementation is based upon a <a href=
 * "https://en.wikipedia.org/wiki/Deterministic_finite_automaton">Deterministic
 * Finite Automaton</a>.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class CsvTokenizer {

	private final Map<State, Collection<Transition>> transitionMap;
	private State currentState;
	private Character currentCharacter;
	private StringBuilder currentValue;
	private List<String> currentRow;
	private int currentLineNumber;
	private int currentCharacterNumber;
	private List<List<String>> resultRows;

	/**
	 * Constructs a new {@link CsvTokenizer}.
	 */
	public CsvTokenizer() {

		this.transitionMap = new TreeMap<>();

		addTransition(State.START_OF_ROW, Char.QUOTE, State.QUOTED_VALUE, INullaryVoidFunction.NO_OPERATION);
		addTransition(State.START_OF_ROW, Char.NEWLINE, State.START_OF_ROW, INullaryVoidFunction.NO_OPERATION);
		addTransition(State.START_OF_ROW, Char.COMMA, State.START_OF_VALUE, this::commitValue);
		addTransition(State.START_OF_ROW, Char.EOF, State.END, INullaryVoidFunction.NO_OPERATION);
		addTransition(State.START_OF_ROW, Char.REGULAR, State.UNQUOTED_VALUE, this::addCharToValue);

		addTransition(State.START_OF_VALUE, Char.QUOTE, State.QUOTED_VALUE, INullaryVoidFunction.NO_OPERATION);
		addTransition(State.START_OF_VALUE, Char.NEWLINE, State.START_OF_ROW, this::commitValueAndRow);
		addTransition(State.START_OF_VALUE, Char.COMMA, State.START_OF_VALUE, this::commitValue);
		addTransition(State.START_OF_VALUE, Char.EOF, State.END, this::commitValueAndRow);
		addTransition(State.START_OF_VALUE, Char.REGULAR, State.UNQUOTED_VALUE, this::addCharToValue);

		addTransition(State.UNQUOTED_VALUE, Char.QUOTE, State.ERROR, this::throwSyntaxException);
		addTransition(State.UNQUOTED_VALUE, Char.NEWLINE, State.START_OF_ROW, this::commitValueAndRow);
		addTransition(State.UNQUOTED_VALUE, Char.COMMA, State.START_OF_VALUE, this::commitValue);
		addTransition(State.UNQUOTED_VALUE, Char.EOF, State.END, this::commitValueAndRow);
		addTransition(State.UNQUOTED_VALUE, Char.REGULAR, State.UNQUOTED_VALUE, this::addCharToValue);

		addTransition(State.QUOTED_VALUE, Char.QUOTE, State.QUOTED_VALUE_QUOTE, INullaryVoidFunction.NO_OPERATION);
		addTransition(State.QUOTED_VALUE, Char.NEWLINE, State.QUOTED_VALUE, this::addCharToValue);
		addTransition(State.QUOTED_VALUE, Char.COMMA, State.QUOTED_VALUE, this::addCharToValue);
		addTransition(State.QUOTED_VALUE, Char.EOF, State.ERROR, this::throwSyntaxException);
		addTransition(State.QUOTED_VALUE, Char.REGULAR, State.QUOTED_VALUE, this::addCharToValue);

		addTransition(State.QUOTED_VALUE_QUOTE, Char.QUOTE, State.QUOTED_VALUE, this::addCharToValue);
		addTransition(State.QUOTED_VALUE_QUOTE, Char.NEWLINE, State.START_OF_ROW, this::commitValueAndRow);
		addTransition(State.QUOTED_VALUE_QUOTE, Char.COMMA, State.START_OF_VALUE, this::commitValue);
		addTransition(State.QUOTED_VALUE_QUOTE, Char.EOF, State.END, this::commitValueAndRow);
		addTransition(State.QUOTED_VALUE_QUOTE, Char.REGULAR, State.ERROR, this::throwSyntaxException);
	}

	/**
	 * Extracts values from the given CSV-formatted {@link String}.
	 * <p>
	 * In the returned result, each item in the outer {@link List} corresponds
	 * to a logical row, as extracted from one or several physical rows in the
	 * given CSV {@link String}. In the inner {@link List}, each item
	 * corresponds to a value in the respective row.
	 * <p>
	 * Empty physical rows in the given CSV {@link String} are ignored.
	 * <p>
	 * If the given CSV {@link String} is empty, an empty {@link List} is
	 * returned.
	 *
	 * @param csv
	 *            the CSV-formatted {@link String} to process (never
	 *            <i>null</i>)
	 * @return a {@link List} of logical rows of extracted tokens (never
	 *         <i>null</i>)
	 */
	public List<List<String>> tokenize(String csv) {

		Objects.requireNonNull(csv);

		this.currentState = State.START_OF_ROW;
		this.currentCharacter = null;
		this.currentValue = new StringBuilder();
		this.currentRow = new ArrayList<>();
		this.currentLineNumber = 1;
		this.currentCharacterNumber = 1;
		this.resultRows = new ArrayList<>();

		for (int i = 0; i <= csv.length(); i++) {
			this.currentCharacter = i < csv.length()? csv.charAt(i) : '\0';
			this.currentState = findTransition(currentState, currentCharacter).execute();
			if (currentCharacter == '\n') {
				this.currentLineNumber++;
				this.currentCharacterNumber = 1;
			} else {
				this.currentCharacterNumber++;
			}
		}
		return resultRows;
	}

	private void addTransition(State current, Predicate<Character> condition, State successor, INullaryVoidFunction action) {

		this.transitionMap//
			.computeIfAbsent(current, dummy -> new ArrayList<>())
			.add(new Transition(successor, condition, action));
	}

	private Transition findTransition(State current, Character c) {

		return transitionMap//
			.get(current)
			.stream()
			.filter(successor -> successor.test(c))
			.findFirst()
			.orElseThrow();
	}

	private void addCharToValue() {

		currentValue.append(currentCharacter);
	}

	private void commitValue() {

		currentRow.add(currentValue.toString());
		currentValue.setLength(0);
	}

	private void commitValueAndRow() {

		commitValue();
		resultRows.add(currentRow);
		currentRow = new ArrayList<>();
	}

	private void throwSyntaxException() {

		throw new CsvSyntaxException(currentLineNumber, currentCharacterNumber);
	}

	private static enum State {

		START_OF_ROW,
		START_OF_VALUE,
		UNQUOTED_VALUE,
		QUOTED_VALUE,
		QUOTED_VALUE_QUOTE,
		ERROR,
		END
	}

	private static class Transition {

		private final State successor;
		private final Predicate<Character> condition;
		private final INullaryVoidFunction action;

		public Transition(State successor, Predicate<Character> condition, INullaryVoidFunction action) {

			this.successor = successor;
			this.condition = condition;
			this.action = action;
		}

		public boolean test(char c) {

			return condition.test(c);
		}

		public State execute() {

			action.apply();
			return successor;
		}
	}

	private static interface Char {

		Predicate<Character> QUOTE = c -> c == '"';
		Predicate<Character> NEWLINE = c -> c == '\r' || c == '\n';
		Predicate<Character> COMMA = c -> c == ',';
		Predicate<Character> EOF = c -> c == '\0';
		Predicate<Character> REGULAR = c -> Stream.of(QUOTE, NEWLINE, COMMA, EOF).noneMatch(it -> it.test(c));
	}
}
