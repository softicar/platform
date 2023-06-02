package com.softicar.platform.dom.elements.wiki;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.I18n2;
import com.softicar.platform.common.core.i18n.I18n3;

public interface DomWikiI18n {

	I18n0 BASIC_TEXT_FORMATTING = new I18n0("Basic Text Formatting")//
		.de("Grundlegende Textformatierung")
		.bs("Osnovno formatiranje teksta")
		.sr("Osnovno formatiranje teksta")
		.hr("Osnovno formatiranje teksta");
	I18n0 BOXES = new I18n0("Boxes")//
		.de("Boxen")
		.bs("Kutije")
		.sr("Kutije")
		.hr("Kutije");
	I18n0 CELL = new I18n0("Cell")//
		.de("Zelle")
		.bs("Ćelija")
		.sr("Ćelija")
		.hr("Ćelija");
	I18n0 CURRENTLY_RESOURCES_ARE_ONLY_SUPPORTED_FOR_WIKI_ELEMENTS_THAT_ARE_DEFINED_IN_JAVA =
			new I18n0("Currently, resources are **only** supported for wiki elements that are defined in **Java**.")//
				.de("Ressourcen für Wiki Elemente werden momentan **nur** unterstützt, wenn diese in **Java** definiert sind.")
				.bs("Trenutno su resursi podržani **samo** za wiki elemente koji su definirani u **Javi**.")
				.sr("Trenutno su resursi podržani **samo** za wiki elemente koji su definirani u **Javi**.")
				.hr("Trenutno su resursi podržani **samo** za wiki elemente koji su definirani u **Javi**.");
	I18n3 DIFFERENT_TYPES_OF_BOXES_ARG1_ARG2_AND_ARG3 = new I18n3("Different types of boxes: %s, %s and %s.")//
		.de("Unterschiedliche Arten von Boxen: %s, %s und %s.")
		.bs("Različite vrste kutija: %s, %s i %s.")
		.sr("Različite vrste kutija: %s, %s i %s.")
		.hr("Različite vrste kutija: %s, %s i %s.");
	I18n0 HEADER = new I18n0("Header")//
		.de("Kopfzeile")
		.bs("Zaglavlje")
		.sr("Zaglavlje")
		.hr("Zaglavlje");
	I18n0 HEADLINE = new I18n0("Headline")//
		.de("Überschrift")
		.bs("Naslov")
		.sr("Naslov")
		.hr("Naslov");
	I18n0 HEADLINES = new I18n0("Headlines")//
		.de("Überschriften")
		.bs("Naslovi")
		.sr("Naslovi")
		.hr("Naslovi");
	I18n1 HEADLINES_ARE_CREATED_BY_USING_TWO_OR_MORE_EQUALS_SIGNS_ARG1 = new I18n1("Headlines are created by using two or more **equals signs** %s.")//
		.de("Überschriften werden erstellt, indem man zwei oder mehrere **Gleichheitszeichen** %s benutzt.")
		.bs("Naslovi se kreiraju korištenjem dva ili više **znakova jednakosti** %s.")
		.sr("Naslovi se kreiraju korištenjem dva ili više **znakova jednakosti** %s.")
		.hr("Naslovi se kreiraju korištenjem dva ili više **znakova jednakosti** %s.");
	I18n0 IF_YOU_WANT_TO_FORCE_A_NEWLINE_WITHOUT_A_PARAGRAPH_YOU_CAN_USE_TWO_BACKSLASHES_FOLLOWED_BY_A_WHITESPACE_OR_THE_END_OF_LINE = new I18n0(
		"If you want to **force a newline** without a paragraph, you can use two backslashes followed by a whitespace or the end of line.")//
			.de(
				"Wenn sie einen **Zeilenumbruch** ohne einen neuen Paragraphen **erzwingen** wollen, können sie zwei Backslashes gefolgt von einer Leerstelle oder dem Ende der Zeile verwenden.")
			.bs("Ako želite **preći u novi red** bez paragrafa, možete koristiti dvije kose crte praćene razmakom ili krajem reda.")
			.sr("Ako želite **preći u novi red** bez paragrafa, možete koristiti dvije kose crte praćene razmakom ili krajem reda.")
			.hr("Ako želite **preći u novi red** bez paragrafa, možete koristiti dvije kose crte praćene razmakom ili krajem reda.");
	I18n0 ITALIC_UNDERLINE_AND_MONOSPACED_TEXT = new I18n0("//italic//, __underline__ and ''monospaced'' text.")//
		.de("//kursiver//, __unterstrichener__ und ''dicktengleicher'' Text.")
		.bs("//italic//, __underline__ i ''monospaced'' tekst.")
		.sr("//italic//, __underline__ i ''monospaced'' tekst.")
		.hr("//italic//, __underline__ i ''monospaced'' tekst.");
	I18n0 LINKS = new I18n0("Links")//
		.de("Links")
		.bs("Linkovi")
		.sr("Linkovi")
		.hr("Linkovi");
	I18n0 LISTS = new I18n0("Lists")//
		.de("Listen")
		.bs("Liste")
		.sr("Liste")
		.hr("Liste");
	I18n0 OF_COURSE_YOU_CAN_COMBINE_ALL_THESE = new I18n0("Of course you can **__//''combine''//__** all these.")//
		.de("Sie können natürlich auch alle **__//''kombinieren''//__**.")
		.bs("Naravno da možete **__//''kombinirati''//__** sve ove.")
		.sr("Naravno da možete **__//''kombinirati''//__** sve ove.")
		.hr("Naravno da možete **__//''kombinirati''//__** sve ove.");
	I18n0 ORDERED_LIST_ITEM = new I18n0("ordered list item")//
		.de("Element einer geordneten Liste")
		.bs("stavka uređene liste")
		.sr("stavka uređene liste")
		.hr("stavka uređene liste");
	I18n0 PARAGRAPHS_ARE_CREATED_FROM_BLANK_LINES = new I18n0("**Paragraphs** are created from blank lines.")//
		.de("**Paragraphen** werden durch leere Zeilen erstellt.")
		.bs("**Paragrafi** se stvaraju iz praznih linija.")
		.sr("**Paragrafi** se stvaraju iz praznih linija.")
		.hr("**Paragrafi** se stvaraju iz praznih linija.");
	I18n0 RESOURCES = new I18n0("Resources")//
		.de("Ressourcen")
		.bs("Resursi")
		.sr("Resursi")
		.hr("Resursi");
	I18n0 SOFTICAR_WIKI_SYNTAX = new I18n0("SoftiCAR Wiki Syntax")//
		.de("SoftiCAR Wiki Syntax")
		.bs("SoftiCAR Wiki sintaksa")
		.sr("SoftiCAR Wiki sintaksa")
		.hr("SoftiCAR Wiki sintaksa");
	I18n0 TABLES = new I18n0("Tables")//
		.de("Tabellen")
		.bs("Tabele")
		.sr("Tabele")
		.hr("Tabele");
	I18n1 THE_SOFTICAR_WIKI_SYNTAX_IS_VERY_SIMILAR_TO_THE_ARG1_DOKUWIKI_SYNTAX =
			new I18n1("The SoftiCAR Wiki syntax is very similar to the [[%s|Dokuwiki syntax]].")//
				.de("Die Softicar Wiki Syntax ist sehr ähnlich zu der [[%s|Dokuwiki Syntax]].")
				.bs("SoftiCAR Wiki sintaksa vrlo je slična [[%s|Dokuwiki sintaksi]].")
				.sr("SoftiCAR Wiki sintaksa vrlo je slična [[%s|Dokuwiki sintaksi]].")
				.hr("SoftiCAR Wiki sintaksa vrlo je slična [[%s|Dokuwiki sintaksi]].");
	I18n0 THE_SOFTICAR_WIKI_SYNTAX_SUPPORTS_BOLD = new I18n0("The SoftiCAR Wiki syntax supports **bold**,")//
		.de("Die SoftiCAR Wiki Syntax unterstüzt **fettgedruckt**,")
		.bs("SoftiCAR Wiki sintaksa podržava **bold**,")
		.sr("SoftiCAR Wiki sintaksa podržava **bold**,")
		.hr("SoftiCAR Wiki sintaksa podržava **bold**,");
	I18n0 THERE_ARE_FIVE_DIFFERENT_LEVELS_OF_HEADLINES = new I18n0("There are **five** different levels of headlines.")//
		.de("Es gibt **fünf** verschiedene Stufen von Überschriften.")
		.bs("Postoji **pet** različitih nivoa naslova.")
		.sr("Postoji **pet** različitih nivoa naslova.")
		.hr("Postoji **pet** različitih nivoa naslova.");
	I18n0 THIS_IS_A_WARNING_BOX = new I18n0("This is a warning box.")//
		.de("Das ist eine Warnungs-Box.")
		.bs("Ovo je okvir upozorenja.")
		.sr("Ovo je okvir upozorenja.")
		.hr("Ovo je okvir upozorenja.");
	I18n0 THIS_IS_AN_ERROR_BOX = new I18n0("This is an error box.")//
		.de("Das ist eine Fehler-Box.")
		.bs("Ovo je okvir sa greškom.")
		.sr("Ovo je okvir sa greškom.")
		.hr("Ovo je okvir sa greškom.");
	I18n0 THIS_IS_AN_INFO_BOX_WITH_A_LIST_FOO_BAR = new I18n0("This is an info box with a list:  * foo  * bar")//
		.de("Das ist eine Info-Box mit einer Liste:  * foo  * bar")
		.bs("Ovo je informacijski okvir sa listom: * foo * bar")
		.sr("Ovo je informacijski okvir sa listom: * foo * bar")
		.hr("Ovo je informacijski okvir sa listom: * foo * bar");
	I18n0 UNORDERED_LIST_ITEM = new I18n0("unordered list item")//
		.de("Element einer ungeordneten Liste")
		.bs("stavka neuređene liste")
		.sr("stavka neuređene liste")
		.hr("stavka neuređene liste");
	I18n1 USE_ARG1_FOR_TABLE_BODY_CELLS = new I18n1("use %s for table body cells")//
		.de("%s für Zellen im Tabellenkörper nutzen")
		.bs("koristite %s za ćelije tijela tabele")
		.sr("koristite %s za ćelije tijela tabele")
		.hr("koristite %s za ćelije tijela tabele");
	I18n1 USE_ARG1_FOR_TABLE_HEADER_CELLS = new I18n1("use %s for table header cells")//
		.de("%s für Zellen in der Tabellenüberschrift nutzen")
		.bs("koristite %s za ćelije zaglavlja tabele")
		.sr("koristite %s za ćelije zaglavlja tabele")
		.hr("koristite %s za ćelije zaglavlja tabele");
	I18n0 WIKI_SYNTAX = new I18n0("Wiki Syntax")//
		.de("Wiki Syntax")
		.bs("Wiki sintaksa")
		.sr("Wiki sintaksa")
		.hr("Wiki sintaksa");
	I18n0 WITH_LABEL = new I18n0("With Label")//
		.de("Mit Beschriftung")
		.bs("S oznakom")
		.sr("S oznakom")
		.hr("S oznakom");
	I18n1 YOU_CAN_ALSO_ADD_RESOURCES_LIKE_IMAGES_ARG1 = new I18n1("You can also add resources like images %s.")//
		.de("Sie können auch Ressourcen wie Bilder hinzufügen %s.")
		.bs("Također možete dodati resurse kao što su slike %s.")
		.sr("Također možete dodati resurse kao što su slike %s.")
		.hr("Također možete dodati resurse kao što su slike %s.");
	I18n1 YOU_CAN_CREATE_ORDERED_LISTS_BY_STARTING_A_LINE_WITH_ARG1 = new I18n1("You can create **ordered** lists by starting a line with %s.")//
		.de("Sie können **geordnete** Listen erstellen indem sie die Zeile mit %s beginnen.")
		.bs("Možete kreirati **uređene** liste započinjanjem reda s %s.")
		.sr("Možete kreirati **uređene** liste započinjanjem reda s %s.")
		.hr("Možete kreirati **uređene** liste započinjanjem reda s %s.");
	I18n1 YOU_CAN_CREATE_UNORDERED_LISTS_BY_STARTING_A_LINE_WITH_ARG1 = new I18n1("You can create **unordered** lists by starting a line with %s.")//
		.de("Sie können **ungeordnete** Listen erstellen indem sie die Zeile mit %s beginnen.")
		.bs("Možete kreirati **neuređene** liste započinjanjem reda s %s.")
		.sr("Možete kreirati **neuređene** liste započinjanjem reda s %s.")
		.hr("Možete kreirati **neuređene** liste započinjanjem reda s %s.");
	I18n0 YOU_CAN_DEFINE_EXTERNAL_LINKS_WITH_AND_WITHOUT_LABEL = new I18n0("You can define external links with and without label.")//
		.de("Sie können externe Links mit oder ohne Beschriftung erstellen.")
		.bs("Vanjske linkove možete definirati sa i bez oznake.")
		.sr("Vanjske linkove možete definirati sa i bez oznake.")
		.hr("Vanjske linkove možete definirati sa i bez oznake.");
	I18n2 YOU_CAN_DEFINE_TABLES_BY_USING_ARG1_AND_ARG2 = new I18n2("You can define **tables** by using %s and %s.")//
		.de("Sie können **Tabellen** erstellen indem sie %s und %s nutzen.")
		.bs("Možete definirati **tabele** koristeći %s i %s.")
		.sr("Možete definirati **tabele** koristeći %s i %s.")
		.hr("Možete definirati **tabele** koristeći %s i %s.");
}
