package com.softicar.platform.ajax;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.dom.DomI18n;

public interface AjaxI18n extends DomI18n {

	I18n0 AMBIGUOUS_INPUT = new I18n0("Ambiguous Input")//
		.de("Mehrdeutige Eingabe");
	I18n0 AN_EXCEPTION_OCCURRED = new I18n0("An exception occurred:")//
		.de("Ein Fehler ist aufgetreten:");
	I18n0 AN_INTERNAL_PROGRAM_ERROR_OCCURRED = new I18n0("An internal program error occurred.")//
		.de("Ein interner Programmfehler ist aufgetreten.");
	I18n0 ANOTHER_ACTION_IS_STILL_EXECUTING = new I18n0("Another action is still executing.")//
		.de("Eine andere Aktion wird noch ausgeführt.");
	I18n0 APPLICATION_IS_WORKING = new I18n0("Application is working...")//
		.de("Anwendung arbeitet...");
	I18n0 AUTO_COMPLETE_INPUT_ELEMENT = new I18n0("Auto-Complete Input Element")//
		.de("Eingabeelement mit Autovervollständigung");
	I18n0 CANCEL_REQUEST = new I18n0("Cancel request")//
		.de("Anfrage abbrechen");
	I18n0 FAILED_TO_FETCH_REQUEST_PARAMETERS = new I18n0("Failed to fetch request parameters.")//
		.de("Abruf der Anfrageparameter fehlgeschlagen.");
	I18n0 FURTHER_ENTRIES_AVAILABLE = new I18n0("further entries available")//
		.de("weitere Einträge verfügbar");
	I18n0 ILLEGAL_INPUT = new I18n0("Illegal Input")//
		.de("Ungültige Eingabe");
	I18n0 INPUT_REQUIRED = new I18n0("Input Required")//
		.de("Eingabe erforderlich");
	I18n0 INVALID_INPUT = new I18n0("Invalid Input")//
		.de("Ungültige Eingabe");
	I18n1 MISSING_DOCUMENT_PARAMETER_ARG1 = new I18n1("Missing document parameter: %s")//
		.de("Fehlender Dokumentenparameter: %s");
	I18n0 NO_RECORDS_FOUND = new I18n0("No Records Found")//
		.de("Keine Einträge gefunden");
	I18n0 PLEASE_USE_A_MODERN_BROWSER = new I18n0("Please use a modern browser.")//
		.de("Bitte verwenden Sie einen modernen Browser.");
	I18n0 PLEASE_WAIT = new I18n0("Please wait.")//
		.de("Bitte warten.");
	I18n0 THE_SERVER_HAS_NOT_RECEIVED_YOUR_REQUEST_YET = new I18n0("The server has not received your request, yet.")//
		.de("Der Server hat Ihre Anfrage noch nicht empfangen.");
	I18n0 THE_SERVER_IS_WORKING_ON_YOUR_REQUEST = new I18n0("The server is working on your request.")//
		.de("Der Server bearbeitet Ihre Anfrage.");
	I18n0 TOO_MANY_REQUESTS = new I18n0("Too many requests.")//
		.de("Zu viele Anfragen.");
	I18n0 VALID_INPUT = new I18n0("Valid Input")//
		.de("Gültige Eingabe");
	I18n0 YOUR_REQUEST_IS_TIME_CONSUMING = new I18n0("Your request is time-consuming.")//
		.de("Ihre Anfrage ist aufwendig.");
	I18n0 YOUR_SESSION_HAS_EXPIRED = new I18n0("Your session has expired.")//
		.de("Ihre Sitzung ist abgelaufen.");
	I18n1 YOUR_WEB_BROWSER_ARG1_IS_NOT_SUPPORTED = new I18n1("Your web browser '%s' is not supported.")//
		.de("Ihr Webbrowser '%s' wird nicht unterstützt.");
}
