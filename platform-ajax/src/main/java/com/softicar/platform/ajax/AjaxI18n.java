package com.softicar.platform.ajax;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.dom.DomI18n;

public interface AjaxI18n extends DomI18n {

	I18n0 AN_EXCEPTION_OCCURRED = new I18n0("An exception occurred:")//
		.de("Ein Fehler ist aufgetreten:")
		.bs("Desila se greška:")
		.hr("Dogodila se iznimka:")
		.sr("Desio se izuzetak:");
	I18n0 ANOTHER_ACTION_IS_STILL_EXECUTING = new I18n0("Another action is still executing.")//
		.de("Eine andere Aktion wird noch ausgeführt.")
		.bs("Druga akcija se još uvijek izvršava.")
		.hr("Druga radnja je u toku.")
		.sr("Druga akcija se još uvek izvršava.");
	I18n0 APPLICATION_IS_WORKING = new I18n0("Application is working...")//
		.de("Anwendung arbeitet...")
		.bs("Aplikacija radi...")
		.hr("Aplikacija radi...")
		.sr("Aplikacija radi...");
	I18n0 CANCEL_REQUEST = new I18n0("Cancel request")//
		.de("Anfrage abbrechen")
		.bs("Prekini zahtjev")
		.hr("Prekini zahtjev")
		.sr("Prekini zahtev");
	I18n0 FAILED_TO_FETCH_REQUEST_PARAMETERS = new I18n0("Failed to fetch request parameters.")//
		.de("Abruf der Anfrageparameter fehlgeschlagen.")
		.bs("Preuzimanje parametara zahtjeva nije uspjelo.")
		.hr("Preuzimanje parametara zahtjeva nije uspjelo.")
		.sr("Preuzimanje parametara zahteva nije uspelo.");
	I18n1 MISSING_DOCUMENT_PARAMETER_ARG1 = new I18n1("Missing document parameter: %s")//
		.de("Fehlender Dokumentenparameter: %s")
		.bs("U dokumentu nedostaje parametar: %s")
		.hr("U dokumentu nedostaje parametar: %s")
		.sr("U dokumentu nedostaje parametar: %s");
	I18n0 PLEASE_USE_A_MODERN_BROWSER = new I18n0("Please use a modern browser.")//
		.de("Bitte verwenden Sie einen modernen Browser.")
		.bs("Molimo koristite noviji pretraživač.")
		.hr("Molimo koristite noviji pretraživač.")
		.sr("Molimo koristite noviji pretraživač.");
	I18n0 PLEASE_WAIT = new I18n0("Please wait.")//
		.de("Bitte warten.")
		.bs("Molimo sačekajte.")
		.hr("Molimo sačekajte.")
		.sr("Molimo sačekajte.");
	I18n0 THE_SERVER_HAS_NOT_RECEIVED_YOUR_REQUEST_YET = new I18n0("The server has not received your request, yet.")//
		.de("Der Server hat Ihre Anfrage noch nicht empfangen.")
		.bs("Server još uvijek nije primio zahtjev.")
		.hr("Server još uvijek nije primio zahtjev.")
		.sr("Server još uvek nije primio zahtev.");
	I18n0 THE_SERVER_IS_WORKING_ON_YOUR_REQUEST = new I18n0("The server is working on your request.")//
		.de("Der Server bearbeitet Ihre Anfrage.")
		.bs("Server radi na obradi zahtjeva.")
		.hr("Server radi na obradi zahtjeva.")
		.sr("Server radi na obradi zahteva.");
	I18n0 TOO_MANY_REQUESTS = new I18n0("Too many requests.")//
		.de("Zu viele Anfragen.")
		.bs("Prevelik broj zahtjeva.")
		.hr("Prevelik broj zahtjeva.")
		.sr("Prevelik broj zahteva.");
	I18n0 YOUR_REQUEST_IS_TIME_CONSUMING = new I18n0("Your request is time-consuming.")//
		.de("Ihre Anfrage ist aufwendig.")
		.bs("Izvršavanje zahtjeva traje predugo.")
		.hr("Izvršavanje zahtjeva traje predugo.")
		.sr("Izvršavanje zahteva traje predugo.");
	I18n0 YOUR_SESSION_HAS_EXPIRED = new I18n0("Your session has expired.")//
		.de("Ihre Sitzung ist abgelaufen.")
		.bs("Sesija je istekla.")
		.hr("Sesija je istekla.")
		.sr("Sesija je istekla.");
	I18n1 YOUR_WEB_BROWSER_ARG1_IS_NOT_SUPPORTED = new I18n1("Your web browser '%s' is not supported.")//
		.de("Ihr Webbrowser '%s' wird nicht unterstützt.")
		.bs("Pretraživač '%s' nije podržan.")
		.hr("Pretraživač '%s' nije podržan.")
		.sr("Pretraživač '%s' nije podržan.");
}
