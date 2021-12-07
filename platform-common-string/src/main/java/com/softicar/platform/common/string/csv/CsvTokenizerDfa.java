package com.softicar.platform.common.string.csv;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

public class CsvTokenizerDfa {

	private static final Predicate<Character> ANY = c -> true;
	private static final Predicate<Character> COMMA = c -> c == ',';
	private static final Predicate<Character> NEWLINE = c -> c == '\r' || c == '\n';
	private static final Predicate<Character> QUOTE = c -> c == '"';
	private final String input;
	private final Map<StateName, State> states;
	private List<List<String>> records;
	private List<String> currentRecord;
	private StringBuilder currentValue;
	private Character currentCharacter;
	private State state;

	public CsvTokenizerDfa(String input) {

		this.input = input;
		this.states = new TreeMap<>();

		addState(StateName.START_OF_LINE)//
			.addTransition(QUOTE, StateName.QUOTED_VALUE)
			.addTransition(NEWLINE, StateName.START_OF_LINE)
			.addTransition(COMMA, StateName.START_OF_VALUE, this::finishValue)
			.addTransition(ANY, StateName.UNQUOTED_VALUE, this::appendCharacter);

		addState(StateName.START_OF_VALUE)//
			.addTransition(QUOTE, StateName.QUOTED_VALUE)
			.addTransition(NEWLINE, StateName.START_OF_LINE, this::finishValueAndRecord)
			.addTransition(COMMA, StateName.START_OF_VALUE, this::finishValue)
			.addTransition(ANY, StateName.UNQUOTED_VALUE, this::appendCharacter)
			.setEndOfFileHandler(this::finishValueAndRecord);

		addState(StateName.UNQUOTED_VALUE)//
			.addTransition(QUOTE, StateName.ERROR, () -> throwError("Illegal quote in value."))
			.addTransition(NEWLINE, StateName.START_OF_LINE, this::finishValueAndRecord)
			.addTransition(COMMA, StateName.START_OF_VALUE, this::finishValue)
			.addTransition(ANY, StateName.UNQUOTED_VALUE, this::appendCharacter)
			.setEndOfFileHandler(this::finishValueAndRecord);

		addState(StateName.QUOTED_VALUE)//
			.addTransition(QUOTE, StateName.QUOTED_VALUE_QUOTE)
			.addTransition(NEWLINE, StateName.QUOTED_VALUE, this::appendCharacter)
			.addTransition(COMMA, StateName.QUOTED_VALUE, this::appendCharacter)
			.addTransition(ANY, StateName.QUOTED_VALUE, this::appendCharacter)
			.setEndOfFileHandler(() -> throwError("Illegal end of file."));

		addState(StateName.QUOTED_VALUE_QUOTE)//
			.addTransition(QUOTE, StateName.QUOTED_VALUE, this::appendCharacter)
			.addTransition(NEWLINE, StateName.START_OF_LINE, this::finishValueAndRecord)
			.addTransition(COMMA, StateName.START_OF_VALUE, this::finishValue)
			.addTransition(ANY, StateName.ERROR, () -> throwError("Unexpected character after quoted value."))
			.setEndOfFileHandler(this::finishValueAndRecord);
	}

	private State addState(StateName name) {

		State state = new State();
		states.put(name, state);
		return state;
	}

	public List<List<String>> tokenize() {

		this.records = new ArrayList<>();
		this.currentRecord = new ArrayList<>();
		this.currentValue = new StringBuilder();
		this.state = states.get(StateName.START_OF_LINE);
		for (int i = 0; i < input.length(); i++) {
			this.currentCharacter = input.charAt(i);
			state.handleCharacter();
		}
		state.handleEndOfFile();
		return records;
	}

	private void appendCharacter() {

		currentValue.append(currentCharacter);
	}

	private void finishValue() {

		currentRecord.add(currentValue.toString());
		currentValue.setLength(0);
	}

	private void finishValueAndRecord() {

		finishValue();
		records.add(currentRecord);
		currentRecord = new ArrayList<>();
	}

	private void throwError(String message) {

		throw new CsvFormatException(message);
	}

	private class State {

		private final List<Transition> transitions;
		private INullaryVoidFunction endOfFileHandler;

		public State() {

			this.transitions = new ArrayList<>();
			this.endOfFileHandler = INullaryVoidFunction.NO_OPERATION;
		}

		public void handleCharacter() {

			transitions.stream().filter(Transition::test).findFirst().ifPresent(Transition::execute);
		}

		public void handleEndOfFile() {

			endOfFileHandler.apply();
		}

		public State addTransition(Predicate<Character> predicate, StateName targetState) {

			return addTransition(predicate, targetState, INullaryVoidFunction.NO_OPERATION);
		}

		public State addTransition(Predicate<Character> predicate, StateName targetState, INullaryVoidFunction action) {

			transitions.add(new Transition(predicate, targetState, action));
			return this;
		}

		public void setEndOfFileHandler(INullaryVoidFunction endOfFileHandler) {

			this.endOfFileHandler = endOfFileHandler;
		}
	}

	private class Transition {

		private final Predicate<Character> predicate;
		private final INullaryVoidFunction action;
		private final StateName targetState;

		public Transition(Predicate<Character> predicate, StateName targetState, INullaryVoidFunction action) {

			this.predicate = predicate;
			this.targetState = targetState;
			this.action = action;
		}

		public boolean test() {

			return predicate.test(currentCharacter);
		}

		public void execute() {

			action.apply();
			state = states.get(targetState);
		}
	}

	private static enum StateName {
		START_OF_LINE,
		START_OF_VALUE,
		UNQUOTED_VALUE,
		QUOTED_VALUE,
		QUOTED_VALUE_QUOTE,
		ERROR
	}
}
