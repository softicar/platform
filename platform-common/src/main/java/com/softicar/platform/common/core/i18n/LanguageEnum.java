package com.softicar.platform.common.core.i18n;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

/**
 * Enumerates all supported languages.
 *
 * @author Oliver Richers
 */
public enum LanguageEnum implements IDisplayable {

	ABKHAZ(() -> LanguageEnumI18n.ABKHAZ, "ab"),
	AFAR(() -> LanguageEnumI18n.AFAR, "aa"),
	AFRIKAANS(() -> LanguageEnumI18n.AFRIKAANS, "af"),
	AKAN(() -> LanguageEnumI18n.AKAN, "ak"),
	ALBANIAN(() -> LanguageEnumI18n.ALBANIAN, "sq"),
	AMHARIC(() -> LanguageEnumI18n.AMHARIC, "am"),
	ARABIC(() -> LanguageEnumI18n.ARABIC, "ar"),
	ARAGONESE(() -> LanguageEnumI18n.ARAGONESE, "an"),
	ARMENIAN(() -> LanguageEnumI18n.ARMENIAN, "hy"),
	ASSAMESE(() -> LanguageEnumI18n.ASSAMESE, "as"),
	AVARIC(() -> LanguageEnumI18n.AVARIC, "av"),
	AVESTAN(() -> LanguageEnumI18n.AVESTAN, "ae"),
	AYMARA(() -> LanguageEnumI18n.AYMARA, "ay"),
	AZERBAIJANI(() -> LanguageEnumI18n.AZERBAIJANI, "az"),
	BAMBARA(() -> LanguageEnumI18n.BAMBARA, "bm"),
	BASHKIR(() -> LanguageEnumI18n.BASHKIR, "ba"),
	BASQUE(() -> LanguageEnumI18n.BASQUE, "eu"),
	BELARUSIAN(() -> LanguageEnumI18n.BELARUSIAN, "be"),
	BENGALI(() -> LanguageEnumI18n.BENGALI, "bn"),
	BIHARI(() -> LanguageEnumI18n.BIHARI, "bh"),
	BISLAMA(() -> LanguageEnumI18n.BISLAMA, "bi"),
	BOSNIAN(() -> LanguageEnumI18n.BOSNIAN, "bs", new Locale("bs", "BA")),
	BRETON(() -> LanguageEnumI18n.BRETON, "br"),
	BULGARIAN(() -> LanguageEnumI18n.BULGARIAN, "bg"),
	BURMESE(() -> LanguageEnumI18n.BURMESE, "my"),
	CATALAN(() -> LanguageEnumI18n.CATALAN, "ca"),
	CHAMORRO(() -> LanguageEnumI18n.CHAMORRO, "ch"),
	CHECHEN(() -> LanguageEnumI18n.CHECHEN, "ce"),
	CHICHEWA(() -> LanguageEnumI18n.CHICHEWA, "ny"),
	CHINESE(() -> LanguageEnumI18n.CHINESE, "zh"),
	CHUVASH(() -> LanguageEnumI18n.CHUVASH, "cv"),
	CORNISH(() -> LanguageEnumI18n.CORNISH, "kw"),
	CORSICAN(() -> LanguageEnumI18n.CORSICAN, "co"),
	CREE(() -> LanguageEnumI18n.CREE, "cr"),
	CROATIAN(() -> LanguageEnumI18n.CROATIAN, "hr"),
	CZECH(() -> LanguageEnumI18n.CZECH, "cs"),
	DANISH(() -> LanguageEnumI18n.DANISH, "da"),
	DUTCH(() -> LanguageEnumI18n.DUTCH, "nl"),
	DZONGKHA(() -> LanguageEnumI18n.DZONGKHA, "dz"),
	ENGLISH(() -> LanguageEnumI18n.ENGLISH, "en", new Locale("en", "US")),
	ESPERANTO(() -> LanguageEnumI18n.ESPERANTO, "eo"),
	ESTONIAN(() -> LanguageEnumI18n.ESTONIAN, "et"),
	EWE(() -> LanguageEnumI18n.EWE, "ee"),
	FAROESE(() -> LanguageEnumI18n.FAROESE, "fo"),
	FIJIAN(() -> LanguageEnumI18n.FIJIAN, "fj"),
	FINNISH(() -> LanguageEnumI18n.FINNISH, "fi"),
	FRENCH(() -> LanguageEnumI18n.FRENCH, "fr", new Locale("fr", "FR")),
	FULAH(() -> LanguageEnumI18n.FULAH, "ff"),
	GALICIAN(() -> LanguageEnumI18n.GALICIAN, "gl"),
	GANDA(() -> LanguageEnumI18n.GANDA, "lg"),
	GEORGIAN(() -> LanguageEnumI18n.GEORGIAN, "ka"),
	GERMAN(() -> LanguageEnumI18n.GERMAN, "de", new Locale("de", "DE")),
	GREEK(() -> LanguageEnumI18n.GREEK, "el"),
	GUARANI(() -> LanguageEnumI18n.GUARANI, "gn"),
	GUJARATI(() -> LanguageEnumI18n.GUJARATI, "gu"),
	HAITIAN(() -> LanguageEnumI18n.HAITIAN, "ht"),
	HERERO(() -> LanguageEnumI18n.HERERO, "hz"),
	HINDI(() -> LanguageEnumI18n.HINDI, "hi"),
	HIRI_MOTU(() -> LanguageEnumI18n.HIRI_MOTU, "ho"),
	HUNGARIAN(() -> LanguageEnumI18n.HUNGARIAN, "hu"),
	ICELANDIC(() -> LanguageEnumI18n.ICELANDIC, "is"),
	IDO(() -> LanguageEnumI18n.IDO, "io"),
	IGBO(() -> LanguageEnumI18n.IGBO, "ig"),
	INDONESIAN(() -> LanguageEnumI18n.INDONESIAN, "id"),
	INTERLINGUA(() -> LanguageEnumI18n.INTERLINGUA, "ia"),
	INUKTITUT(() -> LanguageEnumI18n.INUKTITUT, "iu"),
	INUPIAQ(() -> LanguageEnumI18n.INUPIAQ, "ik"),
	IRISH(() -> LanguageEnumI18n.IRISH, "ga"),
	ITALIAN(() -> LanguageEnumI18n.ITALIAN, "it"),
	JAVANESE(() -> LanguageEnumI18n.JAVANESE, "jv"),
	KALAALLISUT(() -> LanguageEnumI18n.KALAALLISUT, "kl"),
	KANNADA(() -> LanguageEnumI18n.KANNADA, "kn"),
	KANURI(() -> LanguageEnumI18n.KANURI, "kr"),
	KAZAKH(() -> LanguageEnumI18n.KAZAKH, "kk"),
	KHMER(() -> LanguageEnumI18n.KHMER, "km"),
	KIKUYU(() -> LanguageEnumI18n.KIKUYU, "ki"),
	KINYARWANDA(() -> LanguageEnumI18n.KINYARWANDA, "rw"),
	KIRUNDI(() -> LanguageEnumI18n.KIRUNDI, "rn"),
	KOMI(() -> LanguageEnumI18n.KOMI, "kv"),
	KONGO(() -> LanguageEnumI18n.KONGO, "kg"),
	KWANYAMA(() -> LanguageEnumI18n.KWANYAMA, "kj"),
	KYRGYZ(() -> LanguageEnumI18n.KYRGYZ, "ky"),
	LAO(() -> LanguageEnumI18n.LAO, "lo"),
	LATIN(() -> LanguageEnumI18n.LATIN, "la"),
	LATVIAN(() -> LanguageEnumI18n.LATVIAN, "lv"),
	LIMBURGISH(() -> LanguageEnumI18n.LIMBURGISH, "li"),
	LINGALA(() -> LanguageEnumI18n.LINGALA, "ln"),
	LITHUANIAN(() -> LanguageEnumI18n.LITHUANIAN, "lt"),
	LUXEMBOURGISH(() -> LanguageEnumI18n.LUXEMBOURGISH, "lb"),
	MACEDONIAN(() -> LanguageEnumI18n.MACEDONIAN, "mk"),
	MALAGASY(() -> LanguageEnumI18n.MALAGASY, "mg"),
	MALAYALAM(() -> LanguageEnumI18n.MALAYALAM, "ml"),
	MALTESE(() -> LanguageEnumI18n.MALTESE, "mt"),
	MANX(() -> LanguageEnumI18n.MANX, "gv"),
	MAORI(() -> LanguageEnumI18n.MAORI, "mi"),
	MARATHI(() -> LanguageEnumI18n.MARATHI, "mr"),
	MARSHALLESE(() -> LanguageEnumI18n.MARSHALLESE, "mh"),
	MONGOLIAN(() -> LanguageEnumI18n.MONGOLIAN, "mn"),
	NAURU(() -> LanguageEnumI18n.NAURU, "na"),
	NAVAJO(() -> LanguageEnumI18n.NAVAJO, "nv"),
	NDONGA(() -> LanguageEnumI18n.NDONGA, "ng"),
	NEPALI(() -> LanguageEnumI18n.NEPALI, "ne"),
	NORTHERN_NDEBELE(() -> LanguageEnumI18n.NORTHERN_NDEBELE, "nd"),
	NORTHERN_SAMI(() -> LanguageEnumI18n.NORTHERN_SAMI, "se"),
	NORWEGIAN(() -> LanguageEnumI18n.NORWEGIAN, "no"),
	NORWEGIAN_BOKMAL(() -> LanguageEnumI18n.NORWEGIAN_BOKMAL, "nb"),
	NORWEGIAN_NYNORSK(() -> LanguageEnumI18n.NORWEGIAN_NYNORSK, "nn"),
	NUOSU(() -> LanguageEnumI18n.NUOSU, "ii"),
	OCCITAN(() -> LanguageEnumI18n.OCCITAN, "oc"),
	OJIBWE(() -> LanguageEnumI18n.OJIBWE, "oj"),
	OLD_CHURCH_SLAVONIC(() -> LanguageEnumI18n.OLD_CHURCH_SLAVONIC, "cu"),
	ORIYA(() -> LanguageEnumI18n.ORIYA, "or"),
	OROMO(() -> LanguageEnumI18n.OROMO, "om"),
	OSSETIAN(() -> LanguageEnumI18n.OSSETIAN, "os"),
	PALI(() -> LanguageEnumI18n.PALI, "pi"),
	POLISH(() -> LanguageEnumI18n.POLISH, "pl"),
	PORTUGUESE(() -> LanguageEnumI18n.PORTUGUESE, "pt"),
	QUECHUA(() -> LanguageEnumI18n.QUECHUA, "qu"),
	ROMANIAN(() -> LanguageEnumI18n.ROMANIAN, "ro", new Locale("ru", "RU")),
	ROMANSH(() -> LanguageEnumI18n.ROMANSH, "rm"),
	RUSSIAN(() -> LanguageEnumI18n.RUSSIAN, "ru"),
	SAMOAN(() -> LanguageEnumI18n.SAMOAN, "sm"),
	SANGO(() -> LanguageEnumI18n.SANGO, "sg"),
	SANSKRIT(() -> LanguageEnumI18n.SANSKRIT, "sa"),
	SARDINIAN(() -> LanguageEnumI18n.SARDINIAN, "sc"),
	SCOTTISH_GAELIC(() -> LanguageEnumI18n.SCOTTISH_GAELIC, "gd"),
	SERBIAN(() -> LanguageEnumI18n.SERBIAN, "sr"),
	SHONA(() -> LanguageEnumI18n.SHONA, "sn"),
	SINHALA(() -> LanguageEnumI18n.SINHALA, "si"),
	SLOVAK(() -> LanguageEnumI18n.SLOVAK, "sk"),
	SLOVENE(() -> LanguageEnumI18n.SLOVENE, "sl", new Locale("sl", "SI")),
	SOMALI(() -> LanguageEnumI18n.SOMALI, "so"),
	SOUTHERN_NDEBELE(() -> LanguageEnumI18n.SOUTHERN_NDEBELE, "nr"),
	SOUTHERN_SOTHO(() -> LanguageEnumI18n.SOUTHERN_SOTHO, "st"),
	SPANISH(() -> LanguageEnumI18n.SPANISH, "es", new Locale("es", "ES")),
	SUNDANESE(() -> LanguageEnumI18n.SUNDANESE, "su"),
	SWAHILI(() -> LanguageEnumI18n.SWAHILI, "sw"),
	SWATI(() -> LanguageEnumI18n.SWATI, "ss"),
	SWEDISH(() -> LanguageEnumI18n.SWEDISH, "sv"),
	TAGALOG(() -> LanguageEnumI18n.TAGALOG, "tl"),
	TAHITIAN(() -> LanguageEnumI18n.TAHITIAN, "ty"),
	TAMIL(() -> LanguageEnumI18n.TAMIL, "ta"),
	TELUGU(() -> LanguageEnumI18n.TELUGU, "te"),
	THAI(() -> LanguageEnumI18n.THAI, "th"),
	TIBETAN_STANDARD(() -> LanguageEnumI18n.TIBETAN_STANDARD, "bo"),
	TIGRINYA(() -> LanguageEnumI18n.TIGRINYA, "ti"),
	TONGA(() -> LanguageEnumI18n.TONGA, "to"),
	TSONGA(() -> LanguageEnumI18n.TSONGA, "ts"),
	TSWANA(() -> LanguageEnumI18n.TSWANA, "tn"),
	TURKISH(() -> LanguageEnumI18n.TURKISH, "tr", new Locale("tr", "TR")),
	TURKMEN(() -> LanguageEnumI18n.TURKMEN, "tk"),
	TWI(() -> LanguageEnumI18n.TWI, "tw"),
	UKRAINIAN(() -> LanguageEnumI18n.UKRAINIAN, "uk"),
	VENDA(() -> LanguageEnumI18n.VENDA, "ve"),
	VIETNAMESE(() -> LanguageEnumI18n.VIETNAMESE, "vi"),
	VOLAPUK(() -> LanguageEnumI18n.VOLAPUK, "vo"),
	WALLOON(() -> LanguageEnumI18n.WALLOON, "wa"),
	WELSH(() -> LanguageEnumI18n.WELSH, "cy"),
	WESTERN_FRISIAN(() -> LanguageEnumI18n.WESTERN_FRISIAN, "fy"),
	WOLOF(() -> LanguageEnumI18n.WOLOF, "wo"),
	XHOSA(() -> LanguageEnumI18n.XHOSA, "xh"),
	YORUBA(() -> LanguageEnumI18n.YORUBA, "yo"),
	ZHUANG(() -> LanguageEnumI18n.ZHUANG, "za"),
	ZULU(() -> LanguageEnumI18n.ZULU, "zu");

	private static final Map<String, LanguageEnum> ISO6391_MAP = new TreeMap<>();
	private Supplier<IDisplayString> nameSupplier;
	private String iso6391;
	private Locale locale;

	private LanguageEnum(Supplier<IDisplayString> nameSupplier, String iso6391) {

		this(nameSupplier, iso6391, null);
	}

	private LanguageEnum(Supplier<IDisplayString> nameSupplier, String iso6391, Locale local) {

		this.nameSupplier = nameSupplier;
		this.iso6391 = iso6391;
		this.locale = local;
	}

	public String getIso6391() {

		return iso6391;
	}

	public Locale getLocale() {

		return locale;
	}

	@Override
	public String toString() {

		return super.toString().toLowerCase();
	}

	@Override
	public IDisplayString toDisplay() {

		return nameSupplier.get();
	}

	/**
	 * Returns the language enum matching the given name.
	 * <p>
	 * This method is case-insensitive. The given name is converted to
	 * upper-case before a matching enum is searched.
	 *
	 * @param name
	 *            the name of the enum constant
	 * @return the matching enum constant, never null
	 * @throws IllegalArgumentException
	 *             if no matching enum constant can be found
	 */
	public static LanguageEnum getByName(String name) {

		return valueOf(name.toUpperCase());
	}

	public static LanguageEnum getByIso6391(String iso6391) {

		return ISO6391_MAP.get(iso6391);
	}

	static {
		for (LanguageEnum languageEnum: values()) {
			ISO6391_MAP.put(languageEnum.getIso6391(), languageEnum);
		}
	}
}
