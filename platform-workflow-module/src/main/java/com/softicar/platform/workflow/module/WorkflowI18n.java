package com.softicar.platform.workflow.module;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.I18n2;
import com.softicar.platform.common.core.i18n.I18n4;
import com.softicar.platform.core.module.CoreI18n;

public interface WorkflowI18n extends CoreI18n {

	I18n0 A_NEW_WORKFLOW_TASK_REQUIRES_YOUR_ATTENTION = new I18n0("A new workflow task requires your attention.")//
		.de("Eine neue Arbeitsablauf-Aufgabe erfordert Ihre Aufmerksamkeit.")
		.bs("Novi zadatak toka posla zahtijeva vašu pažnju.")
		.hr("Novi zadatak tijeka posla zahtijeva vašu pažnju.")
		.sr("Novi zadatak procesa posla zahtijeva vašu pažnju.");
	I18n0 A_SUBSTITUTE_MUST_BE_DEFINED = new I18n0("A substitute must be defined.")//
		.de("Eine Vertretung muss definiert sein.")
		.bs("Podnaslov mora biti definisan.")
		.hr("Podnaslov mora biti definiran.")
		.sr("Podnaslov mora biti definiran.");
	I18n2 ACTION_ARG1_OF_WORKFLOW_NODE_ARG2_IS_MISSING = new I18n2("Action '%s' of workflow node '%s' is missing.")//
		.de("Aktion %s des Arbeitsablauf-Knotens %s fehlt.")
		.bs("Akcija '%s' radnog toka čvora '%s' nedostaje.")
		.hr("Akcija '%s' radnog tijeka čvora '%s' nedostaje.")
		.sr("Akcija '%s' radnog toka čvora '%s' nedostaje.");
	I18n2 ACTION_PERMISSION_ARG1_OF_WORKFLOW_NODE_ARG2_IS_MISSING = new I18n2("Action permission '%s' of workflow node '%s' is missing.")//
		.de("Aktionberechtigung %s des Arbeitsablauf-Knotens %s fehlt.")
		.bs("Nedostaje dozvola za akciju '%s' radnog toka čvora '%s'..")
		.hr("Nedostaje dozvola za akciju '%s' radnog tijeka čvora '%s'..")
		.sr("Nedostaje dozvola za akciju '%s' radnog toka čvora '%s'..");
	I18n0 ACTIVATE = new I18n0("Activate")//
		.de("Aktivieren")
		.bs("Aktiviraj")
		.hr("Aktiviraj")
		.sr("Aktiviraj");
	I18n0 ADD_NEW_MESSAGE = new I18n0("Add New Message")//
		.de("Neue Nachricht hinzufügen")
		.bs("Dodaj novu poruku")
		.hr("Dodaj novu poruku")
		.sr("Dodaj novu poruku");
	I18n0 ADD_NEW_NODE = new I18n0("Add New Node")//
		.de("Neuen Knoten hinzufügen")
		.bs("Dodaj novi čvor")
		.hr("Dodaj novi čvor")
		.sr("Dodaj novi čvor");
	I18n0 ADD_NEW_TRANSITION = new I18n0("Add New Transition")//
		.de("Neuen Übergang hinzufügen")
		.bs("Dodaj novi prijelaz")
		.hr("Dodaj novi prijelaz")
		.sr("Dodaj novi prelaz");
	I18n1 ALL_TRANSITIONS_NEED_TO_HAVE_THE_CORRECT_SOURCE_NODE_ARG1 = new I18n1("All transitions need to have the correct source node: %s.")//
		.de("Alle Übergänge müssen den richtigen Ursprungsknoten besitzen: %s.")
		.bs("Svi prijelazi moraju imati ispravan izvorni čvor: %s.")
		.hr("Svi prijelazi moraju imati ispravan izvorni čvor: %s.")
		.sr("Svi prelazi moraju imati ispravan izvorni čvor: %s.");
	I18n0 APPROVAL_TIER = new I18n0("Approval Tier")//
		.de("Genehmigungsebene")
		.bs("Nivo odobrenja")
		.hr("Nivo odobrenja")
		.sr("Nivo odobrenja");
	I18n0 APPROVED = new I18n0("Approved")//
		.de("genehmigt")
		.bs("Odobreno")
		.hr("Odobreno")
		.sr("Odobreno");
	I18n0 APPROVER = new I18n0("Approver")//
		.de("Genehmiger")
		.bs("Odobravač")
		.hr("Odobravatelj")
		.sr("Odobravač");
	I18n2 ARG1_ITEM_S_OF_SOURCE_WORKFLOW_NODE_ARG2 = new I18n2("%s item(s) of source workflow node '%s'")//
		.de("%s Element(e) aus Ursprungs-Arbeitsablauf-Knoten '%s'")
		.bs("%s stavke(e) izvornog čvora toka rada '%s'")
		.hr("%s stavke(e) izvornog čvora tijeka rada '%s'")
		.sr("%s stavke(e) izvornog čvora toka rada '%s'");
	I18n0 AUTO_TRANSITION = new I18n0("Auto Transition")//
		.de("Automatischer Übergang")
		.bs("Automatski prijelaz")
		.hr("Automatski prijelaz")
		.sr("Automatski prelaz");
	I18n2 AUTO_TRANSITION_ARG1_ARG2 = new I18n2("Auto transition: %s -> %s")//
		.de("Automatischer Übergang: %s -> %s")
		.bs("Automatski prijelaz: %s -> %s")
		.hr("Automatski prijelaz: %s -> %s")
		.sr("Automatski prelaz: %s -> %s");
	I18n0 AUTO_TRANSITIONS_CANNOT_NOTIFY_ANYONE = new I18n0("Auto transitions cannot notify anyone.")//
		.de("Automatische Übergänge können niemanden informieren.")
		.bs("Automatski prijelazi više ne obavještavaju.")
		.hr("Automatski prijelazi više ne obavještavaju.")
		.sr("Automatski prelazi više ne obaveštavaju.");
	I18n0 AUTO_TRANSITIONS_MAY_NOT_DEFINE_PERMISSIONS = new I18n0("Auto transitions may not define permissions.")//
		.de("Automatische Übergänge dürfen keine Berechtigungen definieren.")
		.bs("Automatski prijelazi možda neće definisati dozvole.")
		.hr("Automatski prijelazi možda neće definirati dozvole.")
		.sr("Automatski prelazi možda neće definisati dozvole.");
	I18n0 AUTO_TRANSITIONS_MAY_NOT_DEFINE_REQUIRED_VOTES = new I18n0("Auto transitions may not define required votes.")//
		.de("Automatische Übergänge dürfen keine benötigten Stimmen definieren.")
		.bs("Automatski prijelazi možda neće definisati potrebne glasove.")
		.hr("Automatski prijelazi možda neće definirati potrebne glasove.")
		.sr("Automatski prelazi možda neće definisati potrebne glasove.");
	I18n0 CLOSE_SELECTED_TASKS = new I18n0("Close Selected Tasks")//
		.de("Selektierte Aufgaben schließen")
		.bs("Zatvori odabrane zadatke")
		.hr("Zatvori odabrane zadatke")
		.sr("Zatvori odabrane zadatke");
	I18n0 CLOSED = new I18n0("Closed")//
		.de("Geschlossen")
		.bs("Zatvoreno")
		.hr("Zatvoreno")
		.sr("Zatvoreno");
	I18n1 COMMENT_ARG1 = new I18n1("Comment: %s")//
		.de("Kommentar: %s")
		.bs("Komentar: %s")
		.hr("Komentar: %s")
		.sr("Komentar: %s");
	I18n0 COMMENT_REQUIRED = new I18n0("Comment Required")//
		.de("Kommentar erforderlich")
		.bs("Potreban komentar")
		.hr("Potreban komentar")
		.sr("Potreban komentar");
	I18n0 CONFIGURE = new I18n0("Configure")//
		.de("Konfigurieren")
		.bs("Konfiguriši")
		.hr("Konfiguriši")
		.sr("Konfiguriši");
	I18n0 COPY = new I18n0("Copy")//
		.de("Kopieren")
		.bs("Kopiraj")
		.hr("Kopiraj")
		.sr("Kopiraj");
	I18n0 CREATE_WORKFLOW_TASKS = new I18n0("Create Workflow Tasks")//
		.de("Erstelle Arbeitsaufgaben");
	I18n0 CREATED_AT = new I18n0("Created At")//
		.de("Erstellt am")
		.bs("Kreirano")
		.hr("Kreirano")
		.sr("Kreirano");
	I18n0 CREATION_TRANSACTION = new I18n0("Creation Transaction")//
		.de("Erstellende Transaktion")
		.bs("Transakcija kreiranja")
		.hr("Transakcija kreiranja")
		.sr("Transakcija kreiranja");
	I18n0 CURRENT_VERSION = new I18n0("Current Version")//
		.de("Aktuelle Version")
		.bs("Trenutna verzija")
		.hr("Trenutna verzija")
		.sr("Trenutna verzija");
	I18n0 DELEGATE = new I18n0("Delegate")//
		.de("Delegieren")
		.bs("Delegat")
		.hr("Delegat")
		.sr("Delegat");
	I18n0 DELEGATED_BY = new I18n0("Delegated By")//
		.de("Delegiert Von")
		.bs("Delegirao")
		.hr("Delegirao")
		.sr("Delegirao");
	I18n1 DELEGATED_BY_ARG1 = new I18n1("Delegated By '%s'")//
		.de("Delegiert von '%s'")
		.bs("Delegirao '%s'")
		.hr("Delegirao '%s'")
		.sr("Delegirao '%s'");
	I18n0 DELEGATION = new I18n0("Delegation")//
		.de("Delegierung")
		.bs("Delegacija")
		.hr("Delegacija")
		.sr("Delegacija");
	I18n0 DRAFT = new I18n0("Draft")//
		.de("Entwurf")
		.bs("Nacrt")
		.hr("Nacrt")
		.sr("Nacrt");
	I18n0 DRAFT_VERSION = new I18n0("Draft Version")//
		.de("Entwurfsversion")
		.bs("Verzija nacrta")
		.hr("Verzija nacrta")
		.sr("Verzija nacrta");
	I18n0 EMAIL_NOTIFICATIONS_FOR_NEW_TASKS = new I18n0("Email Notifications for New Tasks")//
		.de("E-Mail Benachrichtigungen für neue Aufgaben")
		.bs("Email obavijesti za novi zadatak")
		.hr("Email obavijesti za novi zadatak")
		.sr("Email obavesti za novi zadatak");
	I18n0 ENTITY_TABLE = new I18n0("Entity Table")//
		.de("Entitätstabelle")
		.bs("Tabela entiteta")
		.hr("Tabela entiteta")
		.sr("Tabela entiteta");
	I18n1 EXECUTED_ACTION_ARG1 = new I18n1("Executed action '%s'.")//
		.de("Ausgeführte Aktion '%s'.")
		.bs("Izvršena akcija '%s'.")
		.hr("Izvršena akcija '%s'.")
		.sr("Izvršena akcija '%s'.");
	I18n0 EXECUTES_WORKFLOW_TRANSITIONS = new I18n0("Executes workflow transitions")//
		.de("Führt Arbeitsablauf-Übergänge aus")
		.bs("Izvršava prijelaze toka rada")
		.hr("Izvršava prijelaze tijeka rada")
		.sr("Izvršava prijelaze toka rada");
	I18n0 FUNCTION = new I18n0("Function")//
		.de("Funktion")
		.bs("Funkcija")
		.hr("Funkcija")
		.sr("Funkcija");
	I18n0 FURTHER_APPROVAL_IS_REQUIRED = new I18n0("Further approval is required.")//
		.de("Weitere Genehmigung ist notwendig.")
		.bs("Potrebno je dodatno odobrenje")
		.hr("Potrebno je dodatno odobrenje")
		.sr("Potrebno je dodatno odobrenje");
	I18n0 HAS_CURRENT_VERSION = new I18n0("Has Current Version")//
		.de("hat aktuelle Version")
		.bs("Ima trenutnu verziju")
		.hr("Ima trenutnu verziju")
		.sr("Ima trenutnu verziju");
	I18n0 HIDE_USERS = new I18n0("Hide Users")//
		.de("Verstecke Benutzer")
		.bs("Sakrij korisnike")
		.hr("Sakrij korisnike")
		.sr("Sakrij korisnike");
	I18n0 HTML_COLOR = new I18n0("HTML Color")//
		.de("HTML-Farbe")
		.bs("HTML boja")
		.hr("HTML boja")
		.sr("HTML boja");
	I18n0 IMPORT_SUCCESSFUL = new I18n0("Import Successful")//
		.de("Import erfolgreich")
		.bs("Uspješan uvoz")
		.hr("Uspješan uvoz")
		.sr("Uspešan uvoz");
	I18n0 IS_CURRENT_VERSION = new I18n0("Is Current Version")//
		.de("ist aktuelle Version")
		.bs("Da li je trenutna verzija")
		.hr("Da li je trenutna verzija")
		.sr("Da li je trenutna verzija");
	I18n0 IS_WORKFLOW_STARTED_QUESTION = new I18n0("Is Workflow Started?")//
		.de("Ist Arbeitsablauf gestartet?")
		.bs("Da li je tok rada pokrenut?")
		.hr("Da li je tijek rada pokrenut?")
		.sr("Da li je tok rada pokrenut?");
	I18n4 ITEM_WAS_MOVED_FROM_NODE_ARG1_OF_VERSION_ARG2_TO_ARG3_OF_VERSION_ARG4 =
			new I18n4("Item was moved from node '%s' of version '%s' to '%s' of version '%s'.")//
				.de("Element wurde von Knoten '%s' aus Version '%s' in Knoten '%s' von Version '%s' verschoben.")
				.bs("Stavka je premještena iz čvora '%s' verzije '%s' u '%s' verzije '%s'.")
				.hr("Stavka je premještena iz čvora '%s' verzije '%s' u '%s' verzije '%s'.")
				.sr("Stavka je premeštena iz čvora '%s' verzije '%s' u '%s' verzije '%s'.");
	I18n0 ITEMS = new I18n0("Items")//
		.de("Elemente")
		.bs("Stavke")
		.hr("Stavke")
		.sr("Stavke");
	I18n0 ITEMS_IN_INACTIVE_VERSIONS = new I18n0("Items in Inactive Versions")//
		.de("Elemente in inaktiven Versionen")
		.bs("Stavke u neaktivnoj verziji")
		.hr("Stavke u neaktivnoj verziji")
		.sr("Stavke u neaktivnoj verziji");
	I18n0 MANAGE_WORKFLOW = new I18n0("Manage Workflow")//
		.de("Arbeitsablauf verwalten")
		.bs("Upravljanje radnim tijekom")
		.hr("Upravljanje radnim tokom")
		.sr("Upravljanje radnim tokom");
	I18n2 MESSAGE_FOR_ITEM_ARG1_AT_ARG2 = new I18n2("Message for Item '%s' at '%s'")//
		.de("Nachricht für Element '%s' am '%s'")
		.bs("Poruka za stavku '%s' na '%s'")
		.hr("Poruka za stavku '%s' na '%s'")
		.sr("Poruka za stavku '%s' na '%s'");
	I18n0 MESSAGES = new I18n0("Messages")//
		.de("Nachrichten")
		.bs("Poruke")
		.hr("Poruke")
		.sr("Poruke");
	I18n0 MISSING_SOURCE_CODE_REFERENCE_POINT = new I18n0("Missing source code reference point.")//
		.de("Quellcode-Referenzpunkt fehlt.")
		.bs("Nedostaje referentna tačka izvornog koda")
		.hr("Nedostaje referentna točka izvornog koda")
		.sr("Nedostaje referentna tačka izvornog koda");
	I18n0 MISSING_WORKFLOW_ROOT_NODE = new I18n0("Missing workflow root node.")//
		.de("Wurzel-Knoten des Arbeitsablaufs fehlt.")
		.bs("Nedostaje korijenski čvor toka rada")
		.hr("Nedostaje korijenski čvor tijeka rada")
		.sr("Nedostaje korijenski čvor toka rada");
	I18n0 MOVE_WORKFLOW_ITEMS_TO_ANOTHER_WORKFLOW_NODE = new I18n0("Move Workflow Items to Another Workflow Node")//
		.de("Arbeitsablauf-Elemente in anderen Arbeitsablauf-Knoten verschieben")
		.bs("Premjestite stavke toka rada u drugi čvor toka rada")
		.hr("Premjestite stavke tijeka rada u drugi čvor tijeka rada")
		.sr("Premjestite stavke toka rada u drugi čvor toka rada");
	I18n0 MY_TASKS = new I18n0("My Tasks")//
		.de("Meine Aufgaben")
		.bs("Moji zadaci")
		.hr("Moji zadaci")
		.sr("Moji zadaci");
	I18n0 NEW_WORKFLOW_TASK = new I18n0("New Workflow Task")//
		.de("Neue Arbeitsablauf-Aufgabe")
		.bs("Novi zadatak toka rada")
		.hr("Novi zadatak tijeka rada")
		.sr("Novi zadatak toka rada");
	I18n0 NO_CURRENT_WORKFLOW_VERSION_FOUND = new I18n0("No current workflow version found.")//
		.de("Keine aktuelle Arbeitsablauf-Version gefunden.")
		.bs("Nije pronađena trenutna verzija toka rada")
		.hr("Nije pronađena trenutna verzija tijeka rada")
		.sr("Nije pronađena trenutna verzija toka rada");
	I18n0 NO_FURTHER_APPROVAL_IS_REQUIRED = new I18n0("No further approval is required.")//
		.de("Keine weitere Genehmigung ist notwendig.")
		.bs("Nije potrebno dodatno odobrenje")
		.hr("Nije potrebno dodatno odobrenje")
		.sr("Nije potrebno dodatno odobrenje");
	I18n0 NO_WORKFLOW_FOUND = new I18n0("No workflow found.")//
		.de("Kein Arbeitsablauf gefunden.")
		.bs("Nema pronađenog toka rada")
		.hr("Nema pronađenog tijeka rada")
		.sr("Nema pronađenog toka rada");
	I18n0 NODE = new I18n0("Node")//
		.de("Knoten")
		.bs("Čvor")
		.hr("Čvor")
		.sr("Čvor");
	I18n1 NODE_ARG1 = new I18n1("Node: %s")//
		.de("Knoten: %s")
		.bs("Čvor: %s")
		.hr("Čvor: %s")
		.sr("Čvor: %s");
	I18n0 NODES = new I18n0("Nodes")//
		.de("Knoten")
		.bs("Čvorovi")
		.hr("Čvorovi")
		.sr("Čvorovi");
	I18n0 NOTIFY = new I18n0("Notify")//
		.de("Benachrichtigen")
		.bs("Obavijesti")
		.hr("Obavijesti")
		.sr("Ovavesti");
	I18n0 OBJECT = new I18n0("Object")//
		.de("Objekt")
		.bs("Objekat")
		.hr("Objekat")
		.sr("Objekat");
	I18n0 OBSOLETE_VERSION = new I18n0("Obsolete Version")//
		.de("Veraltete Version")
		.bs("Zastarjela verzija")
		.hr("Zastarjela verzija")
		.sr("Zastarela verzija");
	I18n0 OPEN_TASKS = new I18n0("Open Tasks")//
		.de("Offene Aufgaben")
		.bs("Otvoreni zadaci")
		.hr("Otvoreni zadaci")
		.sr("Otvoreni zadaci");
	I18n2 PERMISSION_ARG1_OF_WORKFLOW_TRANSITION_ARG2_IS_MISSING = new I18n2("Permission '%s' of workflow transition '%s' is missing.")//
		.de("Berechtigung %s des Arbeitsablauf-Übergangs %s fehlt.")
		.bs("Nedostaje dozvola '%s' prijelaza toka rada '%s'.")
		.hr("Nedostaje dozvola '%s' prijelaza tijeka rada '%s'.")
		.sr("Nedostaje dozvola '%s' prelaza tijeka rada '%s'.");
	I18n0 PLEASE_PROVIDE_A_RATIONALE = new I18n0("Please provide a rationale.")//
		.de("Bitte geben Sie eine Begründung an.")
		.bs("Navedite obrazloženje")
		.hr("Navedite obrazloženje")
		.sr("Navedite obrazloženje");
	I18n0 PLEASE_REFRESH_THE_INPUT_ELEMENT_OR_PRESS_F5_TO_RELOAD_THE_SCREEN = new I18n0("Please refresh the input element or press F5 to reload the screen.")//
		.de("Bitte das Eingabeelement aktualisieren oder F5 drücken, um den Bildschirm neu zu laden.")
		.bs("Osvježite element unosa ili pritisnite F5 za ponovno učitavanje zaslona.")
		.hr("Osvježite element unosa ili pritisnite F5 za ponovno učitavanje zaslona.")
		.sr("Osvježite element unosa ili pritisnite F5 za ponovno učitavanje zaslona.");
	I18n2 PRECONDITION_FUNCTION_ARG1_OF_WORKFLOW_NODE_ARG2_IS_MISSING = new I18n2("Precondition function '%s' of workflow node '%s' is missing.")//
		.de("Vorbedingungsfunktion %s des Arbeitsablauf-Knotens %s fehlt.")
		.bs("Nedostaje funkcija preduvjeta '%s' čvora toka rada '%s'.")
		.hr("Nedostaje funkcija preduvjeta '%s' čvora tijeka rada '%s'.")
		.sr("Nedostaje funkcija preduveta '%s' čvora toka rada '%s'.");
	I18n0 PRECONDITIONS = new I18n0("Preconditions")//
		.de("Vorbedingungen")
		.bs("Preduvjeti")
		.hr("Preduvjeti")
		.sr("Preduveti");
	I18n0 REQUIRED_VOTES = new I18n0("Required Votes")//
		.de("Benötigte Stimmen")
		.bs("Potrebni glasovi")
		.hr("Potrebni glasovi")
		.sr("Potrebni glasovi");
	I18n0 REQUIRED_VOTES_NOT_VALID = new I18n0("Required votes not valid.")//
		.de("Benötigte Stimmen nicht gültig.")
		.bs("Potrebni glasovi nisu odgovarajući")
		.hr("Potrebni glasovi nisu odgovarajući")
		.sr("Potrebni glasovi nisu odgovarajući");
	I18n0 ROOT_NODE = new I18n0("Root Node")//
		.de("Wurzel-Knoten")
		.bs("Korijenski čvor")
		.hr("Korijenski čvor")
		.sr("Korenski čvor");
	I18n0 SHOW_GRAPH = new I18n0("Show Graph")//
		.de("Graph anzeigen")
		.bs("Prikaži grafikon")
		.hr("Prikaži grafikon")
		.sr("Prikaži grafikon");
	I18n0 SHOW_MORE = new I18n0("Show More")//
		.de("Zeige mehr")
		.bs("Prikaži više")
		.hr("Prikaži više")
		.sr("Prikaži više");
	I18n0 SHOW_ONLY_EXCLUSIVE_TASKS = new I18n0("Show Only Exclusive Tasks")//
		.de("Nur exklusive Aufgaben anzeigen")
		.bs("Prikaži samo ekskluzivne zadatke")
		.hr("Prikaži samo ekskluzivne zadatke")
		.sr("Prikaži samo ekskluzivne zadatke");
	I18n0 SHOW_TASKS_DELEGATED_BY_ME = new I18n0("Show Tasks Delegated By Me")//
		.de("Von mir delegierte Aufgaben anzeigen")
		.bs("Prikaži zadatke koje sam dodijelio")
		.hr("Prikaži zadatke koje sam delegirao")
		.sr("Prikaži zadatke koje sam delegirao");
	I18n0 SHOW_TRANSITIONS = new I18n0("Show Transitions")//
		.de("Übergänge anzeigen")
		.bs("Prikaži prijelaze")
		.hr("Prikaži prijelaze")
		.sr("Prikaži prelaze");
	I18n0 SHOW_USERS = new I18n0("Show Users")//
		.de("Zeige Benutzer")
		.bs("Prikaži korisnike")
		.hr("Prikaži korisnike")
		.sr("Prikaži korisnike");
	I18n0 SIDE_EFFECT = new I18n0("Side Effect")//
		.de("Nebeneffekt")
		.bs("Sporedni efekat")
		.hr("Sporedni učinak")
		.sr("Sporedni efekat");
	I18n2 SIDE_EFFECT_ARG1_OF_WORKFLOW_TRANSITION_ARG2_IS_MISSING = new I18n2("Side effect '%s' of workflow transition '%s' is missing.")//
		.de("Nebeneffekt %s des Arbeitsablauf-Übergangs %s fehlt.")
		.bs("Nedostaje sporedni efekat '%s' za prelaz toka rada '%s'.")
		.hr("Nedostaje sporedni efekt '%s' čvora tijeka rada '%s'.")
		.sr("Nedostaje sporedni efekat '%s' prelaza toka rada '%s'.");
	I18n1 SIDE_EFFECT_IS_NOT_COMPATIBLE_WITH_ARG1 = new I18n1("Side effect is not compatible with %s.")//
		.de("Nebeneffekt ist nicht kompatibel mit %s.")
		.bs("Sporedni efekat nije kompatibilan sa %s.")
		.hr("Sporedni učinak nije kompatibilan s %s.")
		.sr("Sporedni efekat nije kompatibilan sa %s.");
	I18n0 SOURCE_CODE_REFERENCE_POINT_IS_NOT_A_VALID_SIDE_EFFECT = new I18n0("Source code reference point is not a valid side effect.")//
		.de("Quellcode-Referenzpunkt ist kein gültiger Nebeneffekt.")
		.bs("Izvor referentne tačke u kodu nije validan sporedni efekat.")
		.hr("Izvor referentne točke u kodu nije valjani sporedni učinak.")
		.sr("Izvor referentne tačke u kodu nije validan sporedni efekat.");
	I18n0 SOURCE_NODE = new I18n0("Source Node")//
		.de("Ursprungsknoten")
		.bs("Izvorni čvor")
		.hr("Izvorni čvor")
		.sr("Izvorni čvor");
	I18n0 START_WORKFLOW = new I18n0("Start Workflow")//
		.de("Arbeitsablauf starten")
		.bs("Pokreni tok rada")
		.hr("Pokreni tijek rada")
		.sr("Pokreni tok rada");
	I18n0 SUBSCRIBED = new I18n0("Subscribed")//
		.de("Abonniert");
	I18n0 SUBSTITUTE = new I18n0("Substitute")//
		.de("Vertretung")
		.bs("Zamjena")
		.hr("Zamjena")
		.sr("Zamena");
	I18n0 SUBSTITUTE_DEFINED = new I18n0("Substitute defined")//
		.de("Vertretung definiert")
		.bs("Zamjena definisana")
		.hr("Zamjena definirana")
		.sr("Zamena definisana");
	I18n1 SUBSTITUTE_FOR_ARG1 = new I18n1("Substitute for %s")//
		.de("Vertretung für %s")
		.bs("Zamjena za %s")
		.hr("Zamjena za %s")
		.sr("Zamena za %s");
	I18n0 SUBSTITUTE_FROM = new I18n0("Substitute from")//
		.de("Vertretung seit")
		.bs("Zamjena od")
		.hr("Zamjena od")
		.sr("Zamena od");
	I18n0 SUBSTITUTE_FROM_IS_AFTER_SUBSTITUTE_TO = new I18n0("'Substitute from' is after 'Substitute to'")//
		.de("'Vertretung seit' ist nach 'Vertretung bis'")
		.bs("'Zamjena od' je poslije 'Zamjena do'")
		.hr("'Zamjena od' je nakon 'Zamjena do'")
		.sr("'Zamena od' je posle 'Zamena do'");
	I18n0 SUBSTITUTE_TO = new I18n0("Substitute to")//
		.de("Vertretung bis")
		.bs("Zamjena do")
		.hr("Zamjena do")
		.sr("Zamena do");
	I18n0 TARGET_NODE = new I18n0("Target Node")//
		.de("Zielknoten")
		.bs("Ciljni čvor")
		.hr("Ciljni čvor")
		.sr("Ciljni čvor");
	I18n0 TARGET_NODE_MUST_BE_ACTIVE = new I18n0("Target node must be active.")//
		.de("Zielknoten muss aktiv sein.")
		.bs("Ciljni čvor mora biti aktivan.")
		.hr("Ciljni čvor mora biti aktivan.")
		.sr("Ciljni čvor mora biti aktivan.");
	I18n0 TARGET_NODE_MUST_BE_DIFFERENT_THAN_SOURCE_NODE = new I18n0("Target node must be different than source node.")//
		.de("Zielknoten muss sich von dem Ausgangsknoten unterscheiden.")
		.bs("Ciljni čvor mora biti različit od izvornog čvora.")
		.hr("Ciljni čvor mora biti različit od izvornog čvora.")
		.sr("Ciljni čvor mora biti različit od izvornog čvora.");
	I18n0 TARGET_USER_IS_SYSTEM_USER = new I18n0("Target user is system user.")//
		.de("Ziel-Benutzer ist System-Benutzer.")
		.bs("Ciljni korisnik je sistemski korisnik.")
		.hr("Ciljni korisnik je sistemski korisnik.")
		.sr("Ciljni korisnik je sistemski korisnik.");
	I18n0 TARGET_WORKFLOW_NODE = new I18n0("Target Workflow Node")//
		.de("Ziel-Arbeitsablauf-Knoten")
		.bs("Ciljni čvor toka rada")
		.hr("Ciljni čvor tijeka rada")
		.sr("Ciljni čvor toka rada");
	I18n0 TARGET_WORKFLOW_VERSION = new I18n0("Target Workflow Version")//
		.de("Ziel-Arbeitsablauf-Version")
		.bs("Ciljna verzija toka rada")
		.hr("Ciljna verzija tijeka rada")
		.sr("Ciljna verzija toka rada");
	I18n0 TASK = new I18n0("Task")//
		.de("Aufgabe")
		.bs("Zadatak")
		.hr("Zadatak")
		.sr("Zadatak");
	I18n2 TASK_ARG1_EXECUTED_TRANSITION_ARG2 = new I18n2("Task '%s' executed transition '%s'.")//
		.de("Aufgabe '%s' hat den Übergang '%s' ausgeführt.")
		.bs("Zadatak '%s' izvršio je prijelaz '%s'.")
		.hr("Zadatak '%s' izvršio je prijelaz '%s'.")
		.sr("Zadatak '%s' izvršio je prelaz '%s'.");
	I18n1 TASK_DELEGATED_TO_ARG1 = new I18n1("Task delegated to %s.")//
		.de("Aufgabe delegiert an %s.")
		.bs("Zadatak delegiran korisniku %s.")
		.hr("Zadatak delegiran korisniku %s.")
		.sr("Zadatak delegiran korisniku %s.");
	I18n0 THE_ITEM_RESIDES_IN_AN_UNEXPECTED_WORKFLOW_NODE = new I18n0("The item resides in an unexpected workflow node.")//
		.de("Das Element befindet sich in einem unerwarteten Arbeitsablaufknoten.")
		.bs("Stavka se nalazi u neočekivanom čvoru toka rada.")
		.hr("Stavka se nalazi u neočekivanom čvoru tijeka rada.")
		.sr("Stavka se nalazi u neočekivanom čvoru toka rada.");
	I18n0 THIS_ACTION_IS_NOT_AVAILABLE_ANYMORE = new I18n0("This action is not available anymore.")//
		.de("Diese Aktion ist nicht mehr verfügbar.")
		.bs("Ova radnja više nije dostupna.")
		.hr("Ova radnja više nije dostupna.")
		.sr("Ova radnja više nije dostupna.");
	I18n0 TRANSITION = new I18n0("Transition")//
		.de("Übergang")
		.bs("Prijelaz")
		.hr("Prijelaz")
		.sr("Prelaz");
	I18n2 TRANSITION_FROM_WORKFLOW_NODE_ARG1_TO_ARG2 = new I18n2("Transition from workflow node %s to %s.")//
		.de("Übergang von Arbeitsablaufknoten %s zu %s.")
		.bs("Prijelaz iz čvora toka rada %s u %s.")
		.hr("Prijelaz iz čvora tijeka rada %s u %s.")
		.sr("Prelaz iz čvora toka rada %s u %s.");
	I18n0 TRANSITION_ICON = new I18n0("Transition Icon")//
		.de("Übergangs-Icon")
		.bs("Ikona prijelaza")
		.hr("Ikona prijelaza")
		.sr("Ikona prelaza");
	I18n0 TRANSITION_PERMISSION = new I18n0("Transition Permission")//
		.de("Übergangsberechtigung")
		.bs("Dozvola za prijelaz")
		.hr("Dozvola za prijelaz")
		.sr("Dozvola za prelaz");
	I18n1 TRANSITION_TO_ARG1 = new I18n1("Transition to '%s'")//
		.de("Übergang nach '%s'")
		.bs("Prijelaz na '%s'")
		.hr("Prijelaz na '%s'")
		.sr("Prelaz na '%s'");
	I18n0 TRANSITIONS = new I18n0("Transitions")//
		.de("Übergänge")
		.bs("Prijelazi")
		.hr("Prijelazi")
		.sr("Prelazi");
	I18n0 VERSIONS = new I18n0("Versions")//
		.de("Versionen")
		.bs("Verzije")
		.hr("Verzije")
		.sr("Verzije");
	I18n0 WORKFLOW = new I18n0("Workflow")//
		.de("Arbeitsablauf")
		.bs("Tok rada")
		.hr("Tijek rada")
		.sr("Tok rada");
	I18n0 WORKFLOW_ACTION_AVAILABLE = new I18n0("Workflow action available")//
		.de("Arbeitsablauf-Aktion verfügbar")
		.bs("Radnja toka rada dostupna")
		.hr("Radnja tijeka rada dostupna")
		.sr("Radnja toka rada dostupna");
	I18n0 WORKFLOW_ALREADY_STARTED = new I18n0("Workflow already started.")//
		.de("Arbeitsablauf bereits gestartet.")
		.bs("Tok rada već je započeo.")
		.hr("Tijek rada već je započeo.")
		.sr("Tok rada već je započeo.");
	I18n0 WORKFLOW_AUTO_TRANSITION_EXECUTION = new I18n0("Workflow Auto Transition Execution")//
		.de("Ausführung eines automatischen Arbeitsablauf-Übergangs")
		.bs("Izvršavanje automatskog prijelaza toka rada")
		.hr("Izvršavanje automatskog prijelaza tijeka rada")
		.sr("Izvršavanje automatskog prelaza toka rada");
	I18n0 WORKFLOW_AUTO_TRANSITION_EXECUTIONS = new I18n0("Workflow Auto Transition Executions")//
		.de("Ausführungen eines automatischen Arbeitsablauf-Übergangs")
		.bs("Izvršavanja automatskih prijelaza toka rada")
		.hr("Izvršavanja automatskih prijelaza tijeka rada")
		.sr("Izvršavanja automatskih prelaza toka rada");
	I18n0 WORKFLOW_DEMO_OBJECT = new I18n0("Workflow Demo Object")//
		.de("Arbeitsablauf-Demo-Objekt")
		.bs("Demo objekat toka rada")
		.hr("Demo objekt tijeka rada")
		.sr("Demo objekat toka rada");
	I18n0 WORKFLOW_DEMO_OBJECT_APPROVER = new I18n0("Workflow Demo Object Approver")//
		.de("Arbeitsablauf-Demo-Objekt-Genehmiger")
		.bs("Odobravač demo objekta toka rada")
		.hr("Odobravač demo objekta tijeka rada")
		.sr("Odobravač demo objekta toka rada");
	I18n0 WORKFLOW_DEMO_OBJECT_APPROVER_LOG = new I18n0("Workflow Demo Object Approver Log")//
		.de("Arbeitsablauf-Demo-Objekt-Genehmiger-Log")
		.bs("Zapisnik odobravača demo objekta toka rada")
		.hr("Zapisnik odobravača demo objekta tijeka rada")
		.sr("Zapisnik odobravača demo objekta toka rada");
	I18n0 WORKFLOW_DEMO_OBJECT_APPROVER_LOGS = new I18n0("Workflow Demo Object Approver Logs")//
		.de("Arbeitsablauf-Demo-Objekt-Genehmiger-Logs")
		.bs("Zapisnici odobravača demo objekta toka rada")
		.hr("Zapisnici odobravača demo objekta tijeka rada")
		.sr("Zapisnici odobravača demo objekta toka rada");
	I18n0 WORKFLOW_DEMO_OBJECT_APPROVERS = new I18n0("Workflow Demo Object Approvers")//
		.de("Arbeitsablauf-Demo-Objekt-Genehmiger")
		.bs("Odobravači demo objekta toka rada")
		.hr("Odobravači demo objekta tijeka rada")
		.sr("Odobravači demo objekta toka rada");
	I18n0 WORKFLOW_DEMO_OBJECT_LOG = new I18n0("Workflow Demo Object Log")//
		.de("Arbeitsablauf-Demo-Objekt-Log")
		.bs("Zapisnik demo objekta toka rada")
		.hr("Zapisnik demo objekta tijeka rada")
		.sr("Zapisnik demo objekta toka rada");
	I18n0 WORKFLOW_DEMO_OBJECT_LOGS = new I18n0("Workflow Demo Object Logs")//
		.de("Arbeitsablauf-Demo-Objekt-Logs")
		.bs("Dnevnici objekata demo verzije radnog toka")
		.hr("Zapisnici objekata demo radnog tijeka")
		.sr("Evidencija objekata demo radnog toka");
	I18n0 WORKFLOW_DEMO_OBJECTS = new I18n0("Workflow Demo Objects")//
		.de("Arbeitsablauf-Demo-Objekte")
		.bs("Objekti demo verzije radnog toka")
		.hr("Objekti demo radnog tijeka")
		.sr("Objekti demo radnog toka");
	I18n0 WORKFLOW_ICON = new I18n0("Workflow Icon")//
		.de("Arbeitsablauf-Icon")
		.bs("Ikona radnog toka")
		.hr("Ikona radnog tijeka")
		.sr("Ikona radnog toka");
	I18n0 WORKFLOW_ICON_LOG = new I18n0("Workflow Icon Log")//
		.de("Arbeitsablauf-Icon-Log")
		.bs("Zapisnik ikone radnog toka")
		.hr("Zapisnik ikone radnog tijeka")
		.sr("Zapisnik ikone radnog toka");
	I18n0 WORKFLOW_ICON_LOGS = new I18n0("Workflow Icon Logs")//
		.de("Arbeitsablauf-Icon-Logs")
		.bs("Zapisnik ikona radnog toka")
		.hr("Zapisnik ikona radnog tijeka")
		.sr("Zapisnik ikona radnog toka");
	I18n0 WORKFLOW_ICONS = new I18n0("Workflow Icons")//
		.de("Arbeitsablauf-Icons")
		.bs("Ikone radnog toka")
		.hr("Ikone radnog tijeka")
		.sr("Ikone radnog toka");
	I18n0 WORKFLOW_ITEM = new I18n0("Workflow Item")//
		.de("Arbeitsablauf-Element")
		.bs("Stavka radnog toka")
		.hr("Stavka radnog tijeka")
		.sr("Stavka radnog toka");
	I18n2 WORKFLOW_ITEM_ARG1_HAS_MORE_THAN_ONE_EXECUTABLE_TRANSITION_ARG2 = new I18n2("Workflow item '%s' has more than one executable transition:\n%s")//
		.de("Arbeitsablauf-Element '%s' hat mehr als einen ausführbaren Übergang:\n%s")
		.bs("Stavka radnog toka '%s' ima više od jednog izvršnog prijelaza:\n%s")
		.hr("Stavka radnog tijeka '%s' ima više od jednog izvršnog prijelaza:\n%s")
		.sr("Stavka radnog toka '%s' ima više od jednog izvršnog prelaza:\n%s");
	I18n0 WORKFLOW_ITEM_HAS_ALREADY_BEEN_CHANGED = new I18n0("Workflow item has already been changed.")//
		.de("Arbeitsablaufelement wurde bereits geändert.")
		.bs("Stavka radnog toka je već promijenjena.")
		.hr("Stavka radnog tijeka već je promijenjena.")
		.sr("Stavka radnog toka već je promenjena.");
	I18n0 WORKFLOW_ITEM_HISTORY = new I18n0("Workflow Item History")//
		.de("Arbeitsablauf-Element-Historie")
		.bs("Povijest stavke radnog toka")
		.hr("Povijest stavke radnog tijeka")
		.sr("Istorija stavke radnog toka");
	I18n0 WORKFLOW_ITEM_LOG = new I18n0("Workflow Item Log")//
		.de("Arbeitsablauf-Element Log")
		.bs("Zapisnik stavke radnog toka")
		.hr("Zapisnik stavke radnog tijeka")
		.sr("Zapisnik stavke radnog toka");
	I18n0 WORKFLOW_ITEM_LOGS = new I18n0("Workflow Item Logs")//
		.de("Arbeitsablauf-Elemente Log")
		.bs("Zapisnik stavki radnog toka")
		.hr("Zapisnik stavki radnog tijeka")
		.sr("Zapisnik stavki radnog toka");
	I18n0 WORKFLOW_ITEM_MESSAGE = new I18n0("Workflow Item Message")//
		.de("Arbeitsablauf-Element-Nachricht")
		.bs("Poruka stavke radnog toka")
		.hr("Poruka stavke radnog tijeka")
		.sr("Poruka stavke radnog toka");
	I18n0 WORKFLOW_ITEM_MESSAGES = new I18n0("Workflow Item Messages")//
		.de("Arbeitsablauf-Element-Nachrichten")
		.bs("Poruke stavki radnog toka")
		.hr("Poruke stavki radnog tijeka")
		.sr("Poruke stavki radnog toka");
	I18n0 WORKFLOW_ITEM_PRESENT = new I18n0("Workflow Item Present")//
		.de("Arbeitsablauf-Element vorhanden")
		.bs("Stavka radnog toka prisutna")
		.hr("Stavka radnog tijeka prisutna")
		.sr("Stavka radnog toka prisutna");
	I18n0 WORKFLOW_ITEMS = new I18n0("Workflow Items")//
		.de("Arbeitsablauf-Elemente")
		.bs("Stavke radnog toka")
		.hr("Stavke radnog tijeka")
		.sr("Stavke radnog toka");
	I18n0 WORKFLOW_LOG = new I18n0("Workflow Log")//
		.de("Arbeitsablauf-Log")
		.bs("Zapisnik radnog toka")
		.hr("Zapisnik radnog tijeka")
		.sr("Zapisnik radnog toka");
	I18n0 WORKFLOW_LOGS = new I18n0("Workflow Logs")//
		.de("Arbeitsablauf-Logs")
		.bs("Zapisnik radnog toka")
		.hr("Zapisnik radnog tijeka")
		.sr("Zapisnik radnog toka");
	I18n0 WORKFLOW_MESSAGE_SEVERITIES = new I18n0("Workflow Message Severities")//
		.de("Arbeitsablauf-Nachrichtenschweregrade")
		.bs("Težine poruka radnog toka")
		.hr("Težine poruka radnog tijeka")
		.sr("Težine poruka radnog toka");
	I18n0 WORKFLOW_MESSAGE_SEVERITY = new I18n0("Workflow Message Severity")//
		.de("Arbeitsablauf-Nachrichtenschweregrad")
		.bs("Težina poruke radnog toka")
		.hr("Težina poruke radnog tijeka")
		.sr("Težina poruke radnog toka");
	I18n0 WORKFLOW_MODULE_INSTANCE = new I18n0("Workflow Module Instance")//
		.de("Arbeitsablauf-Modulinstanz")
		.bs("Instanca modula radnog toka")
		.hr("Instanca modula radnog tijeka")
		.sr("Instanca modula radnog toka");
	I18n0 WORKFLOW_MODULE_INSTANCES = new I18n0("Workflow Module Instances")//
		.de("Arbeitsablauf-Modulinstanzen")
		.bs("Instance modula radnog toka")
		.hr("Instance modula radnog tijeka")
		.sr("Instance modula radnog toka");
	I18n0 WORKFLOW_NODE = new I18n0("Workflow Node")//
		.de("Arbeitsablauf-Knoten")
		.bs("Čvor radnog toka")
		.hr("Čvor radnog tijeka")
		.sr("Čvor radnog toka");
	I18n0 WORKFLOW_NODE_ACTION = new I18n0("Workflow Node Action")//
		.de("Arbeitsablauf-Knoten-Aktion")
		.bs("Akcija čvora radnog toka")
		.hr("Akcija čvora radnog tijeka")
		.sr("Akcija čvora radnog toka");
	I18n0 WORKFLOW_NODE_ACTION_LOG = new I18n0("Workflow Node Action Log")//
		.de("Arbeitsablauf-Knoten-Aktion-Log")
		.bs("Zapisnik akcije čvora radnog toka")
		.hr("Zapisnik akcije čvora radnog tijeka")
		.sr("Zapisnik akcije čvora radnog toka");
	I18n0 WORKFLOW_NODE_ACTION_LOGS = new I18n0("Workflow Node Action Logs")//
		.de("Arbeitsablauf-Knoten-Aktion-Logs")
		.bs("Zapisnik akcija čvora radnog toka")
		.hr("Zapisnik akcija čvora radnog tijeka")
		.sr("Zapisnik akcija čvora radnog toka");
	I18n0 WORKFLOW_NODE_ACTION_PERMISSION = new I18n0("Workflow Node Action Permission")//
		.de("Arbeitsablauf-Knoten-Aktionsberechtigung")
		.bs("Dozvola za radnju čvora radnog toka")
		.hr("Dozvola za akciju čvora radnog tijeka")
		.sr("Dozvola za akciju čvora radnog toka");
	I18n0 WORKFLOW_NODE_ACTION_PERMISSION_LOG = new I18n0("Workflow Node Action Permission Log")//
		.de("Arbeitsablauf-Knoten-Aktionsberechtigungs-Log")
		.bs("Zapisnik dozvole za akciju čvora radnog toka")
		.hr("Zapisnik dozvole za akciju čvora radnog tijeka")
		.sr("Zapisnik dozvole za akciju čvora radnog toka");
	I18n0 WORKFLOW_NODE_ACTION_PERMISSION_LOGS = new I18n0("Workflow Node Action Permission Logs")//
		.de("Arbeitsablauf-Knoten-Aktionsberechtigungs-Logs")
		.bs("Zapisnik dozvole za akciju čvora radnog toka")
		.hr("Zapisnik dozvole za akciju čvora radnog tijeka")
		.sr("Zapisnik dozvole za akciju čvora radnog toka");
	I18n0 WORKFLOW_NODE_ACTION_PERMISSIONS = new I18n0("Workflow Node Action Permissions")//
		.de("Arbeitsablauf-Knoten-Aktionsberechtigungen")
		.bs("Dozvole za akcije čvora radnog toka")
		.hr("Dozvole za akcije čvora radnog tijeka")
		.sr("Dozvole za akcije čvora radnog toka");
	I18n0 WORKFLOW_NODE_ACTIONS = new I18n0("Workflow Node Actions")//
		.de("Arbeitsablauf-Knoten-Aktionen")
		.bs("Akcije čvora radnog toka")
		.hr("Akcije čvora radnog tijeka")
		.sr("Akcije čvora radnog toka");
	I18n0 WORKFLOW_NODE_CONTAINS_ONE_OR_MORE_ITEMS = new I18n0("Workflow Node Contains One or More Items")//
		.de("Arbeitsablauf-Knoten enthält ein oder mehrere Elemente")
		.bs("Čvor radnog toka sadrži jednu ili više stavki")
		.hr("Čvor radnog tijeka sadrži jednu ili više stavki")
		.sr("Čvor radnog toka sadrži jednu ili više stavki");
	I18n0 WORKFLOW_NODE_LOG = new I18n0("Workflow Node Log")//
		.de("Arbeitsablauf-Knoten-Log")
		.bs("Dnevnik čvora radnog toka")
		.hr("Zapisnik čvora radnog tijeka")
		.sr("Dnevnik čvora radnog toka");
	I18n0 WORKFLOW_NODE_LOGS = new I18n0("Workflow Node Logs")//
		.de("Arbeitsablauf-Knoten-Logs")
		.bs("Zapisnik čvora radnog toka")
		.hr("Zapisnik čvora radnog tijeka")
		.sr("Zapisnik čvora radnog toka");
	I18n0 WORKFLOW_NODE_PRECONDITION = new I18n0("Workflow Node Precondition")//
		.de("Arbeitsablauf-Knoten-Vorbedingung")
		.bs("Preduvjet čvora radnog toka")
		.hr("Preduvjet čvora radnog tijeka")
		.sr("Preduslov čvora radnog toka");
	I18n0 WORKFLOW_NODE_PRECONDITION_LOG = new I18n0("Workflow Node Precondition Log")//
		.de("Arbeitsablauf-Knoten-Vorbedingung-Log")
		.bs("Zapisnik preduvjeta čvora radnog toka")
		.hr("Zapisnik preduvjeta čvora radnog tijeka")
		.sr("Zapisnik preduslova čvora radnog toka");
	I18n0 WORKFLOW_NODE_PRECONDITION_LOGS = new I18n0("Workflow Node Precondition Logs")//
		.de("Arbeitsablauf-Knoten-Vorbedingung-Logs")
		.bs("Zapisnik preduvjeta čvora radnog toka")
		.hr("Zapisnik preduvjeta čvora radnog tijeka")
		.sr("Zapisnik preduslova čvora radnog toka");
	I18n0 WORKFLOW_NODE_PRECONDITIONS = new I18n0("Workflow Node Preconditions")//
		.de("Arbeitsablauf-Knoten-Vorbedingungen")
		.bs("Preduvjeti čvora radnog toka")
		.hr("Preduvjeti čvora radnog tijeka")
		.sr("Preduslovi čvora radnog toka");
	I18n0 WORKFLOW_NODES = new I18n0("Workflow Nodes")//
		.de("Arbeitsablauf-Knoten")
		.bs("Čvorovi radnog toka")
		.hr("Čvorovi radnog tijeka")
		.sr("Čvorovi radnog toka");
	I18n0 WORKFLOW_SPECIFIC_USER_CONFIGURATION = new I18n0("Workflow Specific User Configuration")//
		.de("Arbeitsablaufspezifische Benutzerkonfiguration");
	I18n0 WORKFLOW_SPECIFIC_USER_CONFIGURATION_LOG = new I18n0("Workflow Specific User Configuration Log")//
		.de("Arbeitsablaufspezifische Benutzerkonfigurationlog");
	I18n0 WORKFLOW_SPECIFIC_USER_CONFIGURATION_LOGS = new I18n0("Workflow Specific User Configuration Logs")//
		.de("Arbeitsablaufspezifische Benutzerkonfigurationlogs");
	I18n0 WORKFLOW_SPECIFIC_USER_CONFIGURATIONS = new I18n0("Workflow Specific User Configurations")//
		.de("Arbeitsablaufspezifische Benutzerkonfigurationen");
	I18n0 WORKFLOW_STARTED = new I18n0("Workflow started.")//
		.de("Arbeitsablauf gestartet.")
		.bs("Radni tok započet.")
		.hr("Radni tijek započeo.")
		.sr("Radni tok započet.");
	I18n0 WORKFLOW_STATUS = new I18n0("Workflow Status")//
		.de("Arbeitsablauf-Status")
		.bs("Status radnog toka")
		.hr("Status radnog tijeka")
		.sr("Status radnog toka");
	I18n0 WORKFLOW_TASK = new I18n0("Workflow Task")//
		.de("Arbeitsablauf-Aufgabe")
		.bs("Zadatak radnog toka")
		.hr("Zadatak radnog tijeka")
		.sr("Zadatak radnog toka");
	I18n0 WORKFLOW_TASK_DELEGATION = new I18n0("Workflow Task Delegation")//
		.de("Arbeitsablauf-Aufgabe-Delegierung")
		.bs("Delegacija zadatka radnog toka")
		.hr("Delegacija zadatka radnog tijeka")
		.sr("Delegacija zadatka radnog toka");
	I18n0 WORKFLOW_TASK_DELEGATION_LOG = new I18n0("Workflow Task Delegation Log")//
		.de("Arbeitsablauf-Aufgabe-Delegierung-Log")
		.bs("Zapisnik delegacije zadatka radnog toka")
		.hr("Zapisnik delegacije zadatka radnog tijeka")
		.sr("Zapisnik delegacije zadatka radnog toka");
	I18n0 WORKFLOW_TASK_DELEGATION_LOGS = new I18n0("Workflow Task Delegation Logs")//
		.de("Arbeitsablauf-Aufgabe-Delegierung-Logs")
		.bs("Zapisnik delegacije zadatka radnog toka")
		.hr("Zapisnik delegacije zadatka radnog tijeka")
		.sr("Zapisnik delegacije zadatka radnog toka");
	I18n0 WORKFLOW_TASK_DELEGATIONS = new I18n0("Workflow Task Delegations")//
		.de("Arbeitsablauf-Aufgabe-Delegierungen")
		.bs("Delegacije zadataka radnog toka")
		.hr("Delegacije zadataka radnog tijeka")
		.sr("Delegacije zadataka radnog toka");
	I18n0 WORKFLOW_TASK_EXECUTION = new I18n0("Workflow Task Execution")//
		.de("Arbeitsablauf-Aufgabenausführung");
	I18n0 WORKFLOW_TASK_EXECUTIONS = new I18n0("Workflow Task Executions")//
		.de("Arbeitsablauf-Aufgabenausführungen");
	I18n0 WORKFLOW_TASK_LOG = new I18n0("Workflow Task Log")//
		.de("Arbeitsablauf-Aufgabe-Log")
		.bs("Zapisnik zadatka radnog toka")
		.hr("Zapisnik zadatka radnog tijeka")
		.sr("Zapisnik zadatka radnog toka");
	I18n0 WORKFLOW_TASK_LOGS = new I18n0("Workflow Task Logs")//
		.de("Arbeitsablauf-Aufgabe-Logs")
		.bs("Zapisnik zadataka radnog toka")
		.hr("Zapisnik zadataka radnog tijeka")
		.sr("Zapisnik zadataka radnog toka");
	I18n0 WORKFLOW_TASKS = new I18n0("Workflow Tasks")//
		.de("Arbeitsablauf-Aufgaben")
		.bs("Zadaci radnog toka")
		.hr("Zadaci radnog tijeka")
		.sr("Zadaci radnog toka");
	I18n0 WORKFLOW_TRANSITION = new I18n0("Workflow Transition")//
		.de("Arbeitsablauf-Übergang")
		.bs("Prijelaz radnog toka")
		.hr("Prijelaz radnog tijeka")
		.sr("Prelaz radnog toka");
	I18n0 WORKFLOW_TRANSITION_ACTION_PERMISSION = new I18n0("Workflow Transition Action Permission")//
		.de("Arbeitsablauf-Übergangs-Aktionenberechtigung")
		.bs("Dozvola akcije prijelaza radnog toka")
		.hr("Dozvola akcije prijelaza radnog tijeka")
		.sr("Dozvola akcije prelaza radnog toka");
	I18n0 WORKFLOW_TRANSITION_EXECUTION = new I18n0("Workflow Transition Execution")//
		.de("Ausführung eines Arbeitsablauf-Übergangs")
		.bs("Izvršenje prijelaza radnog toka")
		.hr("Izvršenje prijelaza radnog tijeka")
		.sr("Izvršavanje prelaza radnog toka");
	I18n0 WORKFLOW_TRANSITION_EXECUTIONS = new I18n0("Workflow Transition Executions")//
		.de("Ausführungen eines Arbeitsablauf-Übergangs")
		.bs("Izvršenja prijelaza radnog toka")
		.hr("Izvršenja prijelaza radnog tijeka")
		.sr("Izvršavanja prelaza radnog toka");
	I18n0 WORKFLOW_TRANSITION_LOG = new I18n0("Workflow Transition Log")//
		.de("Arbeitsablauf-Übergang-Log")
		.bs("Zapisnik prijelaza radnog toka")
		.hr("Zapisnik prijelaza radnog tijeka")
		.sr("Zapisnik prelaza radnog toka");
	I18n0 WORKFLOW_TRANSITION_LOGS = new I18n0("Workflow Transition Logs")//
		.de("Arbeitsablauf-Übergang-Logs")
		.bs("Zapisnik prijelaza radnog toka")
		.hr("Zapisnik prijelaza radnog tijeka")
		.sr("Zapisnik prelaza radnog toka");
	I18n0 WORKFLOW_TRANSITION_PERMISSION = new I18n0("Workflow Transition Permission")//
		.de("Arbeitsablauf-Übergangsberechtigung")
		.bs("Dozvola prijelaza radnog toka")
		.hr("Dozvola prijelaza radnog tijeka")
		.sr("Dozvola prelaza radnog toka");
	I18n0 WORKFLOW_TRANSITION_PERMISSION_LOG = new I18n0("Workflow Transition Permission Log")//
		.de("Arbeitsablauf-Übergangsberechtigungs-Log")
		.bs("Zapisnik dozvole prijelaza radnog toka")
		.hr("Zapisnik dozvole prijelaza radnog tijeka")
		.sr("Zapisnik dozvole prelaza radnog toka");
	I18n0 WORKFLOW_TRANSITION_PERMISSION_LOGS = new I18n0("Workflow Transition Permission Logs")//
		.de("Arbeitsablauf-Übergangsberechtigungs-Logs")
		.bs("Zapisnik dozvole prijelaza radnog toka")
		.hr("Zapisnik dozvole prijelaza radnog tijeka")
		.sr("Zapisnik dozvole prelaza radnog toka");
	I18n0 WORKFLOW_TRANSITION_PERMISSIONS = new I18n0("Workflow Transition Permissions")//
		.de("Arbeitsablauf-Übergangsberechtigungen")
		.bs("Dozvole prijelaza radnog toka")
		.hr("Dozvole prijelaza radnog tijeka")
		.sr("Dozvole prelaza radnog toka");
	I18n0 WORKFLOW_TRANSITIONS = new I18n0("Workflow Transitions")//
		.de("Arbeitsablauf-Übergänge")
		.bs("Prijelazi radnog toka")
		.hr("Prijelazi radnog tijeka")
		.sr("Prelazi radnog toka");
	I18n0 WORKFLOW_USER_CONFIGURATION = new I18n0("Workflow User Configuration")//
		.de("Arbeitsablauf-Benutzer-Konfiguration")
		.bs("Konfiguracija korisnika radnog toka")
		.hr("Konfiguracija korisnika radnog tijeka")
		.sr("Konfiguracija korisnika radnog toka");
	I18n0 WORKFLOW_USER_CONFIGURATION_LOG = new I18n0("Workflow User Configuration Log")//
		.de("Arbeitsablauf-Benutzer-Konfigurations-Log")
		.bs("Zapisnik konfiguracije korisnika radnog toka")
		.hr("Zapisnik konfiguracije korisnika radnog tijeka")
		.sr("Zapisnik konfiguracije korisnika radnog toka");
	I18n0 WORKFLOW_USER_CONFIGURATION_LOGS = new I18n0("Workflow User Configuration Logs")//
		.de("Arbeitsablauf-Benutzer-Konfigurations-Logs")
		.bs("Zapisnik konfiguracije korisnika radnog toka")
		.hr("Zapisnik konfiguracije korisnika radnog tijeka")
		.sr("Zapisnik konfiguracije korisnika radnog toka");
	I18n0 WORKFLOW_USER_CONFIGURATIONS = new I18n0("Workflow User Configurations")//
		.de("Arbeitsablauf-Benutzer-Konfigurationen")
		.bs("Konfiguracije korisnika radnog toka")
		.hr("Konfiguracije korisnika radnog tijeka")
		.sr("Konfiguracije korisnika radnog toka");
	I18n0 WORKFLOW_USERS = new I18n0("Workflow Users")//
		.de("Arbeitsablauf-Benutzer")
		.bs("Korisnici radnog toka")
		.hr("Korisnici radnog tijeka")
		.sr("Korisnici radnog toka");
	I18n0 WORKFLOW_VERSION = new I18n0("Workflow Version")//
		.de("Arbeitsablauf-Version")
		.bs("Verzija radnog toka")
		.hr("Verzija radnog tijeka")
		.sr("Verzija radnog toka");
	I18n0 WORKFLOW_VERSION_LOG = new I18n0("Workflow Version Log")//
		.de("Arbeitsablauf-Versions-Log")
		.bs("Zapisnik verzije radnog toka")
		.hr("Zapisnik verzije radnog tijeka")
		.sr("Zapisnik verzije radnog toka");
	I18n0 WORKFLOW_VERSION_LOGS = new I18n0("Workflow Version Logs")//
		.de("Arbeitsablauf-Versions-Logs")
		.bs("Zapisnik verzija radnog toka")
		.hr("Zapisnik verzija radnog tijeka")
		.sr("Zapisnik verzija radnog toka");
	I18n0 WORKFLOW_VERSION_PRESENT = new I18n0("Workflow Version Present")//
		.de("Arbeitsablauf-Version vorhanden")
		.bs("Verzija radnog toka prisutna")
		.hr("Verzija radnog tijeka prisutna")
		.sr("Verzija radnog toka prisutna");
	I18n0 WORKFLOW_VERSIONS = new I18n0("Workflow Versions")//
		.de("Arbeitsablauf-Versionen")
		.bs("Verzije radnog toka")
		.hr("Verzije radnog tijeka")
		.sr("Verzije radnog toka");
	I18n0 WORKFLOWS = new I18n0("Workflows")//
		.de("Arbeitsabläufe")
		.bs("Radni tokovi")
		.hr("Radni tijekovi")
		.sr("Radni tokovi");
	I18n0 X_COORDINATE = new I18n0("X Coordinate")//
		.de("X-Koordinate")
		.bs("X Koordinata")
		.hr("X koordinata")
		.sr("X koordinata");
	I18n0 Y_COORDINATE = new I18n0("Y Coordinate")//
		.de("Y-Koordinate")
		.bs("Y Koordinata")
		.hr("Y koordinata")
		.sr("Y koordinata");
	I18n1 YOU_ARE_SUBSTITUTE_FOR_ARG1 = new I18n1("You are substitute for: %s")//
		.de("Sie vertreten: %s")
		.bs("Vi ste zamjena za: %s")
		.hr("Vi ste zamjena za: %s")
		.sr("Vi ste zamena za: %s");
	I18n0 YOU_CAN_NOT_SUBSTITUTE_YOURSELF = new I18n0("You can not substitute yourself")//
		.de("Sie können sich nicht selbst vertreten")
		.bs("Ne možete zamijeniti sebe")
		.hr("Ne možete zamijeniti sami sebe")
		.sr("Ne možete sebe zameniti");
}
