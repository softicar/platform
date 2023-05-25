package com.softicar.platform.dom;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.I18n2;

public interface DomI18n extends CommonCoreI18n {

	I18n0 ACTIVE_FILTER = new I18n0("Active filter")//
		.de("Aktiver Filter")
		.bs("Aktivan filter")
		.hr("Aktivan filter")
		.sr("Aktivan filter");
	I18n0 ALL = new I18n0("All")//
		.de("Alle")
		.bs("Sve")
		.hr("Sve")
		.sr("Sve");
	I18n0 ALSO_TRIES_TO_AUTOMATICALLY_CONVERT_PURELY_TEXTUAL_VALUES_TO_NUMERICAL_VALUES =
			new I18n0("Also tries to automatically convert purely textual values to numerical values.")//
				.de("Außerdem wird versucht, rein-textuelle Werte automatisch in Zahlenwerte zu konvertieren.")
				.bs("Također pokušava automatski pretvoriti čisto tekstualne vrijednosti u numeričke vrijednosti.")
				.hr("Također pokušava automatski pretvoriti čisto tekstualne vrijednosti u numeričke vrijednosti.")
				.sr("Također pokušava automatski pretvoriti čisto tekstualne vrijednosti u numeričke vrijednosti.");
	I18n0 AMBIGUOUS_INPUT = new I18n0("Ambiguous Input")//
		.de("Mehrdeutige Eingabe")
		.bs("Dvosmislen unos")
		.hr("Dvosmislen unos")
		.sr("Dvosmislen unos");
	I18n0 AN_INTERNAL_PROGRAM_ERROR_OCCURRED = new I18n0("An internal program error occurred.")//
		.de("Ein interner Programmfehler ist aufgetreten.")
		.bs("Došlo je do interne programske greške")
		.hr("Došlo je do interne programske greške")
		.sr("Došlo je do interne programske greške");
	I18n0 AN_NONEXISTENT_OPTION_WAS_SELECTED = new I18n0("An nonexistent option was selected.")//
		.de("Eine nicht existierende Option wurde ausgewählt.")
		.bs("Odabrana je nepostojeća opcija")
		.hr("Odabrana je nepostojeća opcija")
		.sr("Odabrana je nepostojeća opcija");
	I18n0 APPEND_TIMESTAMP = new I18n0("Append Timestamp")//
		.de("Zeitstempel anhängen")
		.bs("Dodati vremensku oznaku")
		.hr("Dodati vremensku oznaku")
		.sr("Dodati vremensku oznaku");
	I18n0 APPLY = new I18n0("Apply")//
		.de("Übernehmen")
		.bs("Primijeni")
		.hr("Primijeni")
		.sr("Primeni");
	I18n0 ARE_YOU_SURE_TO_CLOSE_THIS_WINDOW_AND_ALL_SUB_WINDOWS_QUESTION = new I18n0("Are you sure to close this window and all sub windows?")//
		.de("Sollen dieses Fenster und alle Unterfenster wirklich geschlossen werden?")
		.bs("Jeste li sigurni da želite zatvoriti ovaj prozor i sve podprozore?")
		.hr("Jeste li sigurni da želite zatvoriti ovaj prozor i sve podprozore?")
		.sr("Jeste li sigurni da želite zatvoriti ovaj prozor i sve podprozore?");
	I18n0 ARE_YOU_SURE_TO_CLOSE_THIS_WINDOW_QUESTION = new I18n0("Are you sure to close this window?")//
		.de("Soll dieses Fenster wirklich geschlossen werden?")
		.bs("Jeste li sigurni da želite zatvoriti ovaj prozor?")
		.hr("Jeste li sigurni da želite zatvoriti ovaj prozor?")
		.sr("Jeste li sigurni da želite zatvoriti ovaj prozor?");
	I18n0 AT_CLICK_POSITION = new I18n0("At click position")//
		.de("Bei Klick-Position")
		.bs("Na poziciji klika")
		.hr("Na poziciji klika")
		.sr("Na poziciji klika");
	I18n0 CANCEL = new I18n0("Cancel")//
		.de("Abbrechen")
		.bs("Odgodi")
		.hr("Odgodi")
		.sr("Odgodi");
	I18n0 CENTERED = new I18n0("Centered")//
		.de("Zentriert")
		.bs("Centrirano")
		.hr("Centrirano")
		.sr("Centrirano");
	I18n0 CHOOSE_OR_DROP_FILE = new I18n0("Choose or drop file")//
		.de("Datei wählen oder ablegen")
		.bs("Odaberite ili napustite datoteku")
		.hr("Odaberite ili napustite datoteku")
		.sr("Odaberite ili napustite datoteku");
	I18n0 CLOSE = new I18n0("Close")//
		.de("Schließen")
		.bs("Zatvori")
		.hr("Zatvori")
		.sr("Zatvori");
	I18n0 CLOSE_ALL = new I18n0("Close All")//
		.de("Alle schließen")
		.bs("Zatvori sve")
		.hr("Zatvori sve")
		.sr("Zatvori sve");
	I18n0 CLOSE_ONLY_THIS = new I18n0("Close Only This")//
		.de("Nur dieses schließen")
		.bs("Zatvori samo ovo")
		.hr("Zatvori samo ovo")
		.sr("Zatvori samo ovo");
	I18n0 COLUMN_NAME = new I18n0("Column Name")//
		.de("Spaltenname")
		.bs("Ime kolone")
		.hr("Ime kolone")
		.sr("Ime kolone");
	I18n0 COMMA_SEPARATED_VALUES = new I18n0("Comma-separated values")//
		.de("Kommagetrennte Werte")
		.bs("Vrijednosti razdvojene zarezima")
		.hr("Vrijednosti razdvojene zarezima")
		.sr("Vrijednosti razdvojene zarezima");
	I18n0 COMPRESS_FILE = new I18n0("Compress File")//
		.de("Datei komprimieren")
		.bs("Kompresuj datoteku")
		.hr("Kompresuj datoteku")
		.sr("Kompresuj datoteku");
	I18n0 CONFIGURATION = new I18n0("Configuration")//
		.de("Konfiguration")
		.bs("Konfiguracija")
		.hr("Konfiguracija")
		.sr("Konfiguracija");
	I18n0 CONVERSION = new I18n0("Conversion")//
		.de("Konvertierung")
		.bs("Konverzija")
		.hr("Pretvorba")
		.sr("Pretvorba");
	I18n0 CONVERTS_ALL_CONTENTS_OF_THE_COLUMN_TO_A_PURELY_TEXTUAL_FORMAT_EXACTLY_REPRESENTING_THE_DISPLAYED_TABLE =
			new I18n0("Converts all contents of the column to a purely textual format, exactly representing the displayed table.")//
				.de("Konvertiert die Inhalte der Spalte in ein rein-textuelles Format, das exakt die angezeigte Tabelle abbildet.")
				.bs("Konvertuje sav sadržaj kolone u čisto tekstualni format, koji tačno predstavlja prikazanu tabelu.")
				.hr("Konvertuje sav sadržaj kolone u čisto tekstualni format, koji tačno predstavlja prikazanu tabelu.")
				.sr("Konvertuje sav sadržaj kolone u čisto tekstualni format, koji tačno predstavlja prikazanu tabelu.");
	I18n0 CONVERTS_ALL_CONTENTS_OF_THE_COLUMN_TO_THE_FORMAT_THAT_WAS_INTENDED_BY_THE_DEVELOPER_OF_THE_PROGRAM =
			new I18n0("Converts all contents of the column to the format that was intended by the developer of the program.")//
				.de("Konvertiert die Inhalte der Spalte in das Format, das vom Entwickler des Programms vorgesehen wurde.")
				.bs("Konvertuje sav sadržaj kolone u format koji je predvidio programer programa.")
				.hr("Konvertuje sav sadržaj kolone u format koji je predvidio programer programa.")
				.sr("Konvertuje sav sadržaj kolone u format koji je predvideo programer programa.");
	I18n0 DISPLAY_HELP = new I18n0("Display Help")//
		.de("Hilfe anzeigen")
		.bs("Prikaži pomoć")
		.hr("Prikaži pomoć")
		.sr("Prikaži pomoć");
	I18n0 EXCEL_2003_COMPATIBLE = new I18n0("Excel 2003 compatible")//
		.de("Excel 2003 - kompatibel")
		.bs("Excel 2003 kompatibilan")
		.hr("Excel 2003 kompatibilan")
		.sr("Excel 2003 kompatibilan");
	I18n0 EXPORT = new I18n0("Export")//
		.de("Exportieren")
		.bs("Izvoz")
		.hr("Izvoz")
		.sr("Izvoz");
	I18n0 EXPORT_CANNOT_BE_PERFORMED_NO_TABLE_SPECIFIED = new I18n0("Export cannot be performed: No table specified.")//
		.de("Export kann nicht durchgeführt werden: Keine Tabelle angegeben.")
		.bs("Ne može se izvesti izvoz: nije navedena tabela.")
		.hr("Ne može se izvesti izvoz: nije navedena tabela.")
		.sr("Ne može se izvesti izvoz: nije navedena tabela.");
	I18n0 EXPORT_FORMAT = new I18n0("Export Format")//
		.de("Export-Format")
		.bs("Format izvoza")
		.hr("Format izvoza")
		.sr("Format izvoza");
	I18n0 EXPORT_TABLE = new I18n0("Export Table")//
		.de("Tabelle exportieren")
		.bs("Izvoz tabele")
		.hr("Izvoz table")
		.sr("Izvoz table");
	I18n0 FILE_NAME = new I18n0("File Name")//
		.de("Dateiname")
		.bs("Ime datoteke")
		.hr("Ime datoteke")
		.sr("Ime datoteke");
	I18n0 FILTER = new I18n0("Filter")//
		.de("Filtern")
		.bs("Filter")
		.hr("Filter")
		.sr("Filter");
	I18n0 FIVE = new I18n0("Five")//
		.de("Fünf")
		.bs("Pet")
		.hr("Pet")
		.sr("Pet");
	I18n1 FOR_AN_EXPORT_IN_THE_SELECTED_FORMAT_THE_MAXIMUM_NUMBER_OF_TABLE_ROWS_MUST_NOT_EXCEED_ARG1 =//
			new I18n1("For an export in the selected format, the maximum number of table rows must not exceed %s.")//
				.de("Für einen Export im ausgewählten Format darf die Anzahl der Tabellen-Zeilen %s nicht übersteigen.")
				.bs("Za izvoz u odabranom formatu, maksimalni broj redova tabele ne smije biti veći od %s.")
				.hr("Za izvoz u odabranom formatu, maksimalni broj redova tabele ne smije biti veći od %s.")
				.sr("Za izvoz u odabranom formatu, maksimalni broj redova tabele ne sme biti veći od %s.");
	I18n0 FOUR = new I18n0("Four")//
		.de("Vier")
		.bs("Četiri")
		.hr("Četiri")
		.sr("Četiri");
	I18n0 FURTHER_ENTRIES_AVAILABLE = new I18n0("further entries available")//
		.de("weitere Einträge verfügbar")
		.bs("Dostupni su dodatni unosi")
		.hr("Dostupni su dodatni unosi")
		.sr("Dostupni su dodatni unosi");
	I18n0 HELP = new I18n0("Help")//
		.de("Hilfe")
		.bs("Pomoć")
		.hr("Pomoć")
		.sr("Pomoć");
	I18n0 IF_THIS_OPTION_IS_SELECTED_THE_EXPORTED_FILE_WILL_BE_COMPRESSED_IN_ZIP_FORMAT =//
			new I18n0("If this option is selected, the exported file will be compressed in zip format.")//
				.de("Wenn diese Option ausgewählt ist, wird die exportierte Datei im Zip-Format komprimiert.")
				.bs("Ako je ova opcija odabrana, izvezena datoteka će biti kompresovana u zip formatu.")
				.hr("Ako je ova opcija odabrana, izvezena datoteka će biti kompresovana u zip formatu.")
				.sr("Ako je ova opcija odabrana, izvezena datoteka će biti kompresovana u zip formatu.");
	I18n0 IF_THIS_OPTION_IS_SELECTED_THE_NAME_OF_THE_EXPORTED_FILE_WILL_CONTAIN_A_TIMESTAMP_INDICATING_THE_POINT_IN_TIME_AT_WHICH_THE_FILE_WAS_EXPORTED =
			new I18n0(
				"If this option is selected, the name of the exported file will contain a timestamp, indicating the point in time at which the file was exported.")//
					.de(
						"Wenn diese Option ausgewählt ist, wird der Name der exportierten Datei einen Zeitstempel beinhalten, der angibt, wann die Datei exportiert wurde.")
					.bs(
						"Ako je ova opcija odabrana, ime izvezene datoteke će sadržavati vremensku oznaku, koja označava trenutak u kojem je datoteka izvezena.")
					.hr(
						"Ako je ova opcija odabrana, ime izvezene datoteke će sadržavati vremensku oznaku, koja označava trenutak u kojem je datoteka izvezena.")
					.sr(
						"Ako je ova opcija odabrana, ime izvezene datoteke će sadržavati vremensku oznaku, koja označava trenutak u kojem je datoteka izvezena.");
	I18n0 ILLEGAL_INPUT = new I18n0("Illegal Input")//
		.de("Ungültige Eingabe")
		.bs("Pogrešan unos")
		.hr("Pogrešan unos")
		.sr("Pogrešan unos");
	I18n0 IN_CASE_NO_EXPLICIT_FORMAT_WAS_DEFINED_A_CONVERSION_TO_A_PURELY_TEXTUAL_FORMAT_IS_APPLIED =
			new I18n0("In case no explicit format was defined, a conversion to a purely textual format is applied.")//
				.de("Falls kein explizites Format definiert wurde, wird eine rein-textuelle Konvertierung vorgenommen.")
				.bs("U slučaju da eksplicitni format nije definiran, primjenjuje se konverzija u čisto tekstualni format.")
				.hr("U slučaju da eksplicitni format nije definiran, primjenjuje se konverzija u čisto tekstualni format.")
				.sr("U slučaju da eksplicitni format nije definiran, primjenjuje se konverzija u čisto tekstualni format.");
	I18n0 INVALID_DECIMAL_NUMBER = new I18n0("Invalid decimal number.")//
		.de("Ungültige Dezimalzahl.")
		.bs("Pogrešan decimalni broj")
		.hr("Pogrešan decimalni broj")
		.sr("Pogrešan decimalni broj");
	I18n0 INVALID_INTEGER = new I18n0("Invalid integer.")//
		.de("Ungültige Ganzzahl.")
		.bs("Pogrešan integer")
		.hr("pogrešan integer")
		.sr("pogrešan integer");
	I18n0 MEMORY_CONSUMPTION_IS_TOO_HIGH = new I18n0("Memory consumption is too high.")//
		.de("Speicherverbrauch ist zu hoch.")
		.bs("Potrošnja memorije je prevelika")
		.hr("Potrošnja memorije je prevelika")
		.sr("Potrošnja memorije je prevelika");
	I18n0 MISSING_INPUT_VALUE = new I18n0("Missing input value")//
		.de("Fehlender Eingabewert")
		.bs("Nedostaje vrijednost unosa")
		.hr("Nedostaje vrijednost unosa")
		.sr("Nedostaje vrednost unosa");
	I18n0 MULTIPLE_OPTIONS_WERE_SELECTED = new I18n0("Multiple options were selected.")//
		.de("Mehrere Optionen wurden ausgewählt.")
		.bs("Odabrano više opcija")
		.hr("Odabrano više opcija")
		.sr("Odabrano više opcija");
	I18n0 NEXT_PAGE = new I18n0("Next Page")//
		.de("nächste Seite")
		.bs("Sljedeća stranica")
		.hr("Sljedeća stranica")
		.sr("Sledeća stranica");
	I18n0 NO = new I18n0("No")//
		.de("Nein")
		.bs("Ne")
		.hr("Ne")
		.sr("Ne");
	I18n1 NO_MORE_THAN_ARG1_DECIMAL_PLACES_ALLOWED = new I18n1("No more than %s decimal places allowed.")//
		.de("Nicht mehr als %s Dezimalstellen erlaubt.")
		.bs("Nije dozvoljeno više od %s decimalnih mjesta")
		.hr("Nije dozvoljeno više od %s decimalnih mjesta")
		.sr("Nije dozvoljeno više od %s decimalnih mjesta");
	I18n0 NO_RECORDS_FOUND = new I18n0("No Records Found")//
		.de("Keine Einträge gefunden")
		.bs("Nema pronađenih zapisa")
		.hr("Nema pronađenih zapisa")
		.sr("Nema pronađenih zapisa");
	I18n0 NO_TITLE = new I18n0("No Title")//
		.de("kein Titel")
		.bs("Nema naslova")
		.hr("Nema naslova")
		.sr("Nema naslova");
	I18n0 NONE = new I18n0("none")//
		.de("nichts")
		.bs("Ništa")
		.hr("Ništa")
		.sr("Ništa");
	I18n0 OK = new I18n0("OK")//
		.de("OK")
		.bs("OK")
		.hr("OK")
		.sr("OK");
	I18n0 ONE = new I18n0("One")//
		.de("Eins")
		.bs("Jedan")
		.hr("Jedan")
		.sr("Jedan");
	I18n0 ONLY_ONE_OPTION_MAY_BE_SELECTED = new I18n0("Only one option may be selected.")//
		.de("Nur eine Option darf ausgewählt werden.")
		.bs("Samo jedna opcija može biti odabrana.")
		.hr("Samo jedna opcija može biti odabrana.")
		.sr("Samo jedna opcija može biti odabrana.");
	I18n0 ONLY_TEXT = new I18n0("only text")//
		.de("nur Text")
		.bs("Samo tekst")
		.hr("Samo tekst")
		.sr("Samo tekst");
	I18n0 PICK_A_DAY = new I18n0("Pick a day")//
		.de("Einen Tag auswählen")
		.bs("Odaberi dan")
		.hr("Odaberi dan")
		.sr("Odaberi dan");
	I18n0 PLEASE_CONTACT_THE_SUPPORT_TEAM = new I18n0("Please contact the support team.")//
		.de("Bitte kontaktieren Sie das Support-Team.")
		.bs("Molimo kontaktirajte podršku.")
		.hr("Molimo kontaktirajte podršku.")
		.sr("Molimo kontaktirajte podršku.");
	I18n1 PLEASE_ENTER_A_LOWER_VALUE_FOR_THE_NUMBER_OF_ROWS_PER_PAGE_MAXIMUM_ARG1 =//
			new I18n1("Please enter a lower value for the number of rows per page (maximum: %s).")//
				.de("Bitte einen kleineren Wert für die Anzahl der Zeilen pro Seite eingeben (Maximum: %s).")
				.bs("Unesite nižu vrijednost za broj redova po stranici (maksimalno: %s).")
				.hr("Unesite nižu vrijednost za broj redova po stranici (maksimalno: %s).")
				.sr("Unesite nižu vrednost za broj redova po stranici (maksimalno: %s).");
	I18n0 PLEASE_ENTER_A_TEXT = new I18n0("Please enter a text:")//
		.de("Bitte einen Text eingeben:")
		.bs("Molimo unesite tekst:")
		.hr("Molimo unesite tekst:")
		.sr("Molimo unesite tekst:");
	I18n0 PLEASE_ENTER_A_VALID_FILE_NAME = new I18n0("Please enter a valid file name.")//
		.de("Bitte einen gültigen Dateinamen eingeben.")
		.bs("Molimo unesite ispravno ime datoteke.")
		.hr("Molimo unesite ispravno ime datoteke.")
		.sr("Molimo unesite ispravno ime datoteke.");
	I18n0 PLEASE_SELECT = new I18n0("please select")//
		.de("bitte auswählen")
		.bs("Molimo odaberite")
		.hr("Molimo odaberite")
		.sr("Molimo odaberite");
	I18n0 PLEASE_SELECT_A_FORMAT = new I18n0("Please select a format.")//
		.de("Bitte ein Format auswählen.")
		.bs("Molimo odaberite format")
		.hr("Molimo odaberite format")
		.sr("Molimo odaberite format");
	I18n0 PLEASE_SELECT_A_VALID_ENTRY = new I18n0("Please select a valid entry.")//
		.de("Bitte einen gültigen Eintrag auswählen.")
		.bs("Molimo odaberite ispravan unos.")
		.hr("Molimo odaberite ispravan unos")
		.sr("Molimo odaberite ispravan unos");
	I18n0 PLEASE_SELECT_AN_EXPORT_FORMAT = new I18n0("Please select an export format.")//
		.de("Bitte ein Export-Format auswählen.")
		.bs("Molimo odaberite format izvoza.")
		.hr("Molimo odaberite format izvoza.")
		.sr("Molimo odaberite format izvoza.");
	I18n0 PLEASE_SELECT_AT_LEAST_ONE_COLUMN = new I18n0("Please select at least one column.")//
		.de("Bitte mindestens eine Spalte auswählen.")
		.bs("Molimo odaberite najmanje jednu kolonu.")
		.hr("Molimo odaberite najmanje jednu kolonu.")
		.sr("Molimo odaberite najmanje jednu kolonu.");
	I18n0 PLEASE_TRY_AGAIN = new I18n0("Please try again.")//
		.de("Bitte versuchen Sie es erneut.")
		.bs("Molimo pokušajte ponovo.")
		.hr("Molimo pokušajte ponovo.")
		.sr("Molimo pokušajte ponovo.");
	I18n0 PREVIOUS_PAGE = new I18n0("Previous Page")//
		.de("vorherige Seite")
		.bs("Prethodna stranica")
		.hr("Prethodna stranica")
		.sr("Prethodna stranica");
	I18n0 ROTATE_CLOCKWISE = new I18n0("Rotate Clockwise")//
		.de("Im Uhrzeigersinn drehen")
		.bs("Rotirajte u smjeru kazaljke na satu")
		.hr("Rotirajte u smjeru kazaljke na satu")
		.sr("Rotirajte u smeru kazaljke na satu");
	I18n0 ROTATE_COUNTER_CLOCKWISE = new I18n0("Rotate Counter-Clockwise")//
		.de("Gegen den Uhrzeigersinn drehen")
		.bs("Rotirajte u suprotnom smjeru kazaljke na satu")
		.hr("Rotirajte u suprotnom smjeru kazaljke na satu")
		.sr("Rotirajte u suprotnom smeru kazaljke na satu");
	I18n0 SELECT_COLUMNS = new I18n0("Select Columns")//
		.de("Spalten auswählen")
		.bs("Odaberite kolone")
		.hr("Odaberite kolone")
		.sr("Odaberite kolone");
	I18n2 SELECT_COLUMNS_ARG1_ARG2 = new I18n2("Select Columns (%s/%s)")//
		.de("Spalten auswählen (%s/%s)")
		.bs("Odaberite kolone (%s/%s)")
		.hr("Odaberite kolone (%s/%s)")
		.sr("Odaberite kolone (%s/%s)");
	I18n0 SHEET = new I18n0("Sheet")//
		.de("Blatt")
		.bs("List")
		.hr("List")
		.sr("List");
	I18n0 STANDARD = new I18n0("Standard")//
		.de("Standard")
		.bs("Standard")
		.hr("Standard")
		.sr("Standard");
	I18n0 STRICT = new I18n0("Strict")//
		.de("Streng")
		.bs("Strogo")
		.hr("Strogo")
		.sr("Strogo");
	I18n0 TABLE_EXPORT_FAILED = new I18n0("Table export failed.")//
		.de("Tabellenexport fehlgeschlagen.")
		.bs("Izvoz tabele neuspješan.")
		.hr("Izvoz tabele neuspješan.")
		.sr("Izvoz tabele neuspešan.");
	I18n0 TABLE_EXPORT_PRECONDITIONS_WERE_NOT_FULFILLED = new I18n0("Table export preconditions were not fulfilled.")//
		.de("Die Vorbedingungen für den Tabellen-Export waren nicht erfüllt.")
		.bs("Nisu ispunjeni preduslovi za izvoz tabele.")
		.hr("Nisu ispunjeni preduslovi za izvoz tabele.")
		.sr("Nisu ispunjeni preduslovi za izvoz tabele.");
	I18n0 THE_FOLLOWING_PROBLEM_CAN_OCCUR_WHEN_EXPORTING_THE_TABLE = new I18n0("The following problem can occur when exporting the table")//
		.de("Das folgende Problem kann beim Exportieren der Tabelle auftreten")
		.bs("Popratni problem može nastati prilikom izvoza tabele")
		.hr("Popratni problem može nastati prilikom izvoza tabele")
		.sr("Popratni problem može nastati prilikom izvoza tabele");
	I18n0 THE_FOLLOWING_PROBLEMS_CAN_OCCUR_WHEN_EXPORTING_THE_TABLE = new I18n0("The following problems can occur when exporting the table")//
		.de("Die folgenden Probleme können beim Exportieren der Tabelle auftreten")
		.bs("Popratni problemi mogu nastati prilikom izvoza tabele")
		.hr("Popratni problemi mogu nastati prilikom izvoza tabele")
		.sr("Popratni problemi mogu nastati prilikom izvoza tabele");
	I18n0 THE_FOLLOWING_SHOULD_BE_CONSIDERED_WHEN_EXPORTING_THE_TABLE = new I18n0("The following should be considered when exporting the table")//
		.de("Beim Export der Tabelle sollte Folgendes beachtet werden")
		.bs("Prilikom izvoza tabele treba uzeti u obzir sljedeće")
		.hr("Prilikom izvoza tabele treba uzeti u obzir sljedeće")
		.sr("Prilikom izvoza tabele treba uzeti u obzir sledeće");
	I18n1 THE_SELECTED_OPTION_ARG1_VANISHED = new I18n1("The selected option '%s' vanished.")//
		.de("Die ausgewählte Option '%s' is verschwunden.")
		.bs("Odabrana opcija '%s' je nestala.")
		.hr("Odabrana opcija '%s' je nestala.")
		.sr("Odabrana opcija '%s' je nestala.");
	I18n0 THE_TABLE_CANNOT_BE_EXPORTED_DUE_TO_THE_FOLLOWING_REASON = new I18n0("The table cannot be exported due to the following reason")//
		.de("Die Tabelle kann aus dem folgenden Grund nicht exportiert werden")
		.bs("Tabela se ne može izvesti iz sljedećeg razloga")
		.hr("Tabela se ne može izvesti iz sljedećeg razloga")
		.sr("Tabela se ne može izvesti iz sledećeg razloga");
	I18n0 THE_TABLE_CANNOT_BE_EXPORTED_DUE_TO_THE_FOLLOWING_REASONS = new I18n0("The table cannot be exported due to the following reasons")//
		.de("Die Tabelle kann aus den folgenden Gründen nicht exportiert werden")
		.bs("Tabela se ne može izvesti iz sljedećih razloga")
		.hr("Tabela se ne može izvesti iz sljedećih razloga")
		.sr("Tabela se ne može izvesti iz sledećih razloga");
	I18n1 THE_TABLE_TO_BE_EXPORTED_COMPRISES_ARG1_ROWS_INCLUDING_HEADER_ROWS = new I18n1("The table to be exported comprises %s rows (including header rows).")//
		.de("Die zu exportierende Tabelle umfasst %s Zeilen (inklusive Kopfzeilen).")
		.bs("Tabela koja se izvozi sadrži %s redova (uključujući redove zaglavlja).")
		.hr("Tabela koja se izvozi sadrži %s redova (uključujući redove zaglavlja).")
		.sr("Tabela koja se izvozi sadrži %s redova (uključujući redove zaglavlja).");
	I18n0 THIS_CAN_CAUSE_PROBLEMS_WHEN_TRYING_TO_SUM_UP_TEXTUAL_NUMERICAL_VALUES_IN_AN_EXCEL_FILE = //
			new I18n0("This can cause problems when trying to sum up textual, numerical values in an Excel file.")//
				.de("Dies kann Probleme verursachen, wenn versucht wird textuelle, numerische Werte in einer Excel-Datei zu summieren.")
				.bs("Ovo može uzrokovati probleme kada pokušavate da saberete tekstualne, numeričke vrijednosti u Excel datoteci.")
				.hr("Ovo može uzrokovati probleme kada pokušavate da saberete tekstualne, numeričke vrijednosti u Excel datoteci.")
				.sr("Ovo može uzrokovati probleme kada pokušavate da saberete tekstualne, numeričke vrijednosti u Excel datoteci.");
	I18n0 THREE = new I18n0("Three")//
		.de("Drei")
		.bs("Tri")
		.hr("Tri")
		.sr("Tri");
	I18n0 TIME = new I18n0("Time")//
		.de("Zeit")
		.bs("Vrijeme")
		.hr("Vrijeme")
		.sr("Vreme");
	I18n0 TIMESTAMP = new I18n0("Timestamp")//
		.de("Zeitstempel")
		.bs("Vremenska oznaka")
		.hr("Vremenska oznaka")
		.sr("Vremenska oznaka");
	I18n0 TODAY = new I18n0("Today")//
		.de("Heute")
		.bs("Danas")
		.hr("Danas")
		.sr("Danas");
	I18n0 TOGGLE_TAGS = new I18n0("Toggle Tags")//
		.de("Markierungen umschalten")
		.bs("Uključi oznake")
		.hr("Uključi oznake")
		.sr("Uključi oznake");
	I18n0 TOP_CENTERED = new I18n0("Top centered")//
		.de("Oben zentriert")
		.bs("Vrh centriran")
		.hr("Vrh centriran")
		.sr("Vrh centriran");
	I18n0 TOP_LEFT = new I18n0("Top left")//
		.de("Oben links")
		.bs("Vrh lijevo")
		.hr("Vrh lijevo")
		.sr("Vrh levo");
	I18n0 TRIED_TO_SELECT_AN_OPTION_THAT_WAS_NOT_CONTAINED_IN_THE_LIST_OF_AVAILABLE_OPTIONS =//
			new I18n0("Tried to select an option that was not contained in the list of available options.")//
				.de("Eine Option wurde gewählt, die nicht in der Liste der verfügbaren Optionen enthalten ist.")
				.bs("Pokušaj odabira opcije koja nije sadržana na listi dostupnih opcija.")
				.hr("Pokušaj odabira opcije koja nije sadržana na listi dostupnih opcija.")
				.sr("Pokušaj odabira opcije koja nije sadržana na listi dostupnih opcija.");
	I18n0 TWO = new I18n0("Two")//
		.de("Zwei")
		.bs("Dva")
		.hr("Dva")
		.sr("Dva");
	I18n0 UNTITLED_EXPORT_ENGINE = new I18n0("Untitled Export Engine")//
		.de("Unbenannte Export-Engine")
		.bs("Izvoz bez naziva")
		.hr("Izvoz bez naziva")
		.sr("Izvoz bez naziva");
	I18n0 WEEK = new I18n0("Week")//
		.de("Woche")
		.bs("Sedmica")
		.hr("Tjedan")
		.sr("Nedelja");
	I18n0 YES = new I18n0("Yes")//
		.de("Ja")
		.bs("Da")
		.hr("Da")
		.sr("Da");
	I18n0 ZOOM_IN = new I18n0("Zoom In")//
		.de("Vergrößern")
		.bs("Povećaj")
		.hr("Povećaj")
		.sr("Povećaj");
	I18n0 ZOOM_OUT = new I18n0("Zoom Out")//
		.de("Verkleinern")
		.bs("Smanji")
		.hr("Smanji")
		.sr("Smanji");
}
