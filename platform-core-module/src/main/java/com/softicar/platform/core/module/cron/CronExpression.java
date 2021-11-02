package com.softicar.platform.core.module.cron;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.cron.element.CronEmptyElement;
import com.softicar.platform.core.module.cron.element.CronIllegalElement;
import com.softicar.platform.core.module.cron.element.ICronElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CronExpression {

	private final Collection<ICronElement> minutes;
	private final Collection<ICronElement> hours;
	private final Collection<ICronElement> days;
	private final Collection<ICronElement> months;
	private final Collection<ICronElement> weekdays;
	private final Map<Part, Optional<IDisplayString>> partErrorMessageMap;
	private final Optional<String> surplusTokens;

	CronExpression(CronExpressionBuilder builder) {

		this.partErrorMessageMap = new HashMap<>();
		this.minutes = builder.getMinutes();
		this.hours = builder.getHours();
		this.days = builder.getDays();
		this.months = builder.getMonths();
		this.weekdays = builder.getWeekdays();
		this.surplusTokens = builder.getSurplusTokens();
		validate();
	}

	@Override
	public String toString() {

		return getCronExpression();
	}

	public String getCronExpression() {

		var tokens = new ArrayList<String>();
		tokens.add(createToken(minutes));
		tokens.add(createToken(hours));
		tokens.add(createToken(days));
		tokens.add(createToken(months));
		tokens.add(createToken(weekdays));
		surplusTokens.ifPresent(tokens::add);
		return Imploder.implode(tokens, " ").trim();
	}

	private String createToken(Collection<ICronElement> elements) {

		return elements//
			.stream()
			.map(ICronElement::toString)
			.collect(Collectors.joining(","));
	}

	public boolean matches(DayTime date) {

		boolean minuteMatches = anyMatch(minutes, date.getMinute());
		boolean hourMatches = anyMatch(hours, date.getHour());
		boolean dayMatches = anyMatch(days, date.getDay().getIndexWithinMonth());
		boolean monthMatches = anyMatch(months, date.getDay().getMonth().getIndexWithinYear());
		boolean weekdayMatches = anyMatch(weekdays, date.getDay().getIndexWithinWeek());

		return minuteMatches && hourMatches && dayMatches && monthMatches && weekdayMatches;
	}

	public boolean isValid() {

		return partErrorMessageMap//
			.values()
			.stream()
			.filter(Objects::nonNull)
			.allMatch(Optional::isEmpty);
	}

	public boolean isValid(Part part) {

		return getErrorMessage(part).isEmpty();
	}

	public Optional<IDisplayString> getErrorMessage(Part part) {

		return partErrorMessageMap.getOrDefault(part, Optional.empty());
	}

	private void validate() {

		validate(Part.MINUTE, minutes, 0, 59);
		validate(Part.HOUR, hours, 0, 23);
		validate(Part.DAY, days, 1, 31);
		validate(Part.MONTH, months, 1, 12);
		validate(Part.WEEKDAY, weekdays, 0, 7);
		validateSurplusTokens();
	}

	private void validate(Part part, Collection<ICronElement> elements, int min, int max) {

		var errorMessages = new ArrayList<IDisplayString>();
		if (isAllEmpty(elements)) {
			errorMessages.add(CoreI18n.MISSING_CRON_EXPRESSION_PART);
		} else {
			for (ICronElement element: elements) {
				if (element instanceof CronIllegalElement) {
					errorMessages.add(CoreI18n.ILLEGAL_CRON_ELEMENT_FORMAT_ARG1.toDisplay(element));
				} else if (!element.isInRange(min, max)) {
					errorMessages.add(CoreI18n.VALUE_OUT_OF_RANGE_ARG1_ARG2_ARG3.toDisplay(min, max, element));
				}
			}
		}
		addPartErrors(part, errorMessages);
	}

	private boolean isAllEmpty(Collection<ICronElement> elements) {

		return elements//
			.stream()
			.allMatch(CronEmptyElement.class::isInstance);
	}

	private void validateSurplusTokens() {

		if (surplusTokens.isPresent()) {
			String tokens = surplusTokens.get();
			if (!tokens.isEmpty()) {
				addPartErrors(//
					Part.SURPLUS_TOKENS,
					Collections.singletonList(CoreI18n.ARG1_SHOULD_BE_REMOVED.toDisplay(tokens)));
			}
		}
	}

	private void addPartErrors(Part part, List<IDisplayString> errorMessages) {

		if (!errorMessages.isEmpty()) {
			IDisplayString displayString = part.getDisplayString().concatColon();
			for (IDisplayString errorMessage: errorMessages) {
				displayString = displayString.concatSentence(errorMessage);
			}
			this.partErrorMessageMap.put(part, Optional.of(displayString));
		} else {
			this.partErrorMessageMap.put(part, Optional.empty());
		}
	}

	private boolean anyMatch(Collection<ICronElement> cronElements, int value) {

		return cronElements.stream().anyMatch(element -> element.test(value));
	}

	public enum Part {

		MINUTE(CoreI18n.MINUTE),
		HOUR(CoreI18n.HOUR),
		DAY(CoreI18n.DAY),
		MONTH(CoreI18n.MONTH),
		WEEKDAY(CoreI18n.WEEKDAY),
		SURPLUS_TOKENS(CoreI18n.SURPLUS_TOKENS);

		private final IDisplayString displayString;

		private Part(IDisplayString displayString) {

			this.displayString = displayString;
		}

		public IDisplayString getDisplayString() {

			return displayString;
		}
	}
}
