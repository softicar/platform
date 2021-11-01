package com.softicar.platform.core.module.language;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AGCoreLanguage extends AGCoreLanguageGenerated implements IEntity {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return getDisplayString(getEnum());
	}

	public IResource getLangageFlag() {

		switch (getEnum()) {
		case ENGLISH:
			return CoreImages.FLAG_UNITED_KINGDOM.getResource();
		case GERMAN:
			return CoreImages.FLAG_GERMANY.getResource();
		}
		return CoreImages.FLAG_UNITED_KINGDOM.getResource();
	}

	public Optional<LanguageEnum> getLanguageEnum() {

		return Optional.ofNullable(LanguageEnum.getByIso6391(getThis().getIso6391()));
	}

	/**
	 * Returns a mixed-language {@link IDisplayString} that describes the given
	 * {@link LanguageEnum}.
	 * <p>
	 * The returned {@link IDisplayString} comprises the designation of the
	 * given language in the current user's preferred language, and in English,
	 * as well as the corresponding ISO 6391 code.
	 * <p>
	 * Returns a placeholder if no {@link AGCoreLanguage} can be identified for
	 * the given {@link LanguageEnum}.
	 * <p>
	 * Examples:
	 * <ul>
	 * <li>If the user prefers English: {@link LanguageEnum#ENGLISH} is
	 * displayed as <tt>"English / English (en)"</tt></li>
	 * <li>If the user prefers German: {@link LanguageEnum#ENGLISH} is displayed
	 * as <tt>"Englisch / English (en)"</tt></li>
	 * <li>If the user prefers English: {@link LanguageEnum#GERMAN} is displayed
	 * as <tt>"German / German (de)"</tt></li>
	 * <li>If the user prefers German: {@link LanguageEnum#GERMAN} is displayed
	 * as <tt>"Deutsch / German (de)"</tt></li>
	 * </ul>
	 *
	 * @return a mixed-language {@link IDisplayString} that describes the given
	 *         {@link LanguageEnum} (never null)
	 */
	public IDisplayString getMixedLanguageDisplayString() {

		return IDisplayString.format("%s / %s (%s)", toDisplayWithoutId(), getName(), getIso6391());
	}

	public static Collection<AGCoreLanguage> getAll() {

		return streamAll().collect(Collectors.toList());
	}

	public static Optional<AGCoreLanguage> getByLanguageEnum(LanguageEnum languageEnum) {

		return streamAll()//
			.filter(coreLanguage -> coreLanguage.getIso6391().equals(languageEnum.getIso6391()))
			.findFirst();
	}

	private static Stream<AGCoreLanguage> streamAll() {

		return Arrays//
			.asList(AGCoreLanguageEnum.values())
			.stream()
			.map(AGCoreLanguageEnum::getRecord);
	}

	private static IDisplayString getDisplayString(AGCoreLanguageEnum coreLanguageEnum) {

		switch (coreLanguageEnum) {
		case ENGLISH:
			return CoreI18n.ENGLISH;
		case GERMAN:
			return CoreI18n.GERMAN;
		}

		throw new SofticarUnknownEnumConstantException(coreLanguageEnum);
	}
}
