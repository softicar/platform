package com.softicar.platform.workflow.module;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.I18n2;
import com.softicar.platform.core.module.CoreI18n;

public interface WorkflowI18n extends CoreI18n {

	I18n0 A_NEW_WORKFLOW_TASK_REQUIRES_YOUR_ATTENTION = new I18n0("A new workflow task requires your attention.")//
		.de("Eine neue Arbeitsablauf-Aufgabe erfordert Ihre Aufmerksamkeit.");
	I18n0 A_SUBSTITUTE_MUST_BE_DEFINED = new I18n0("A substitute must be defined.")//
		.de("Eine Vertretung muss definiert sein.");
	I18n2 ACTION_ARG1_OF_WORKFLOW_NODE_ARG2_IS_MISSING = new I18n2("Action '%s' of workflow node '%s' is missing.")//
		.de("Aktion %s des Arbeitsablauf-Knotens %s fehlt.");
	I18n2 ACTION_PERMISSION_ARG1_OF_WORKFLOW_NODE_ARG2_IS_MISSING = new I18n2("Action permission '%s' of workflow node '%s' is missing.")//
		.de("Aktionberechtigung %s des Arbeitsablauf-Knotens %s fehlt.");
	I18n0 ACTIVATE = new I18n0("Activate")//
		.de("Aktivieren");
	I18n0 ADD_NEW_MESSAGE = new I18n0("Add New Message")//
		.de("Neue Nachricht hinzufügen");
	I18n0 ADD_NEW_NODE = new I18n0("Add New Node")//
		.de("Neuen Knoten hinzufügen");
	I18n0 ADD_NEW_TRANSITION = new I18n0("Add New Transition")//
		.de("Neuen Übergang hinzufügen");
	I18n1 ALL_TRANSITIONS_NEED_TO_HAVE_THE_CORRECT_SOURCE_NODE_ARG1 = new I18n1("All transitions need to have the correct source node: %s.")//
		.de("Alle Übergänge müssen den richtigen Ursprungsknoten besitzen: %s.");
	I18n0 APPROVAL_TIER = new I18n0("Approval Tier")//
		.de("Genehmigungsebene");
	I18n0 APPROVED = new I18n0("Approved")//
		.de("genehmigt");
	I18n0 APPROVER = new I18n0("Approver")//
		.de("Genehmiger");
	I18n0 AUTO_TRANSITION = new I18n0("Auto Transition")//
		.de("Automatischer Übergang");
	I18n2 AUTO_TRANSITION_ARG1_ARG2 = new I18n2("Auto transition: %s -> %s")//
		.de("Automatischer Übergang: %s -> %s");
	I18n0 AUTO_TRANSITIONS_CANNOT_NOTIFY_ANYONE = new I18n0("Auto transitions cannot notify anyone.")//
		.de("Automatische Übergänge können niemanden informieren.");
	I18n0 AUTO_TRANSITIONS_MAY_NOT_DEFINE_PERMISSIONS = new I18n0("Auto transitions may not define permissions.")//
		.de("Automatische Übergänge dürfen keine Berechtigungen definieren.");
	I18n0 AUTO_TRANSITIONS_MAY_NOT_DEFINE_REQUIRED_VOTES = new I18n0("Auto transitions may not define required votes.")//
		.de("Automatische Übergänge dürfen keine benötigten Stimmen definieren.");
	I18n0 CLOSED = new I18n0("Closed")//
		.de("Geschlossen");
	I18n1 COMMENT_ARG1 = new I18n1("Comment: %s")//
		.de("Kommentar: %s");
	I18n0 COMMENT_REQUIRED = new I18n0("Comment Required")//
		.de("Kommentar erforderlich");
	I18n0 CONFIGURE = new I18n0("Configure")//
		.de("Konfigurieren");
	I18n0 COPY = new I18n0("Copy")//
		.de("Kopieren");
	I18n0 CREATED_AT = new I18n0("Created At")//
		.de("Erstellt am");
	I18n0 CREATION_TRANSACTION = new I18n0("Creation Transaction")//
		.de("Erstellende Transaktion");
	I18n0 CURRENT_VERSION = new I18n0("Current Version")//
		.de("Aktuelle Version");
	I18n0 DELEGATE = new I18n0("Delegate")//
		.de("Delegieren");
	I18n0 DELEGATED_BY = new I18n0("Delegated By")//
		.de("Delegiert Von");
	I18n1 DELEGATED_BY_ARG1 = new I18n1("Delegated By '%s'")//
		.de("Delegiert von '%s'");
	I18n0 DELEGATION = new I18n0("Delegation")//
		.de("Delegierung");
	I18n0 DRAFT = new I18n0("Draft")//
		.de("Entwurf");
	I18n0 DRAFT_VERSION = new I18n0("Draft Version")//
		.de("Entwurfsversion");
	I18n0 EMAIL_NOTIFICATIONS_FOR_NEW_TASKS = new I18n0("Email Notifications for New Tasks")//
		.de("E-Mail Benachrichtigungen für neue Aufgaben");
	I18n0 ENTITY_TABLE = new I18n0("Entity Table")//
		.de("Entitätstabelle");
	I18n1 EXECUTED_ACTION_ARG1 = new I18n1("Executed action '%s'.")//
		.de("Ausgeführte Aktion '%s'.");
	I18n0 EXECUTES_WORKFLOW_TRANSITIONS = new I18n0("Executes workflow transitions")//
		.de("Führt Arbeitsablauf-Übergänge aus");
	I18n0 FUNCTION = new I18n0("Function")//
		.de("Funktion");
	I18n0 FURTHER_APPROVAL_IS_REQUIRED = new I18n0("Further approval is required.")//
		.de("Weitere Genehmigung ist notwendig.");
	I18n0 HAS_CURRENT_VERSION = new I18n0("Has Current Version")//
		.de("hat aktuelle Version");
	I18n0 HIDE_USERS = new I18n0("Hide Users")//
		.de("Verstecke Benutzer");
	I18n0 HTML_COLOR = new I18n0("HTML Color")//
		.de("HTML-Farbe");
	I18n0 IMPORT_SUCCESSFUL = new I18n0("Import Successful")//
		.de("Import erfolgreich");
	I18n0 IS_CURRENT_VERSION = new I18n0("Is Current Version")//
		.de("ist aktuelle Version");
	I18n0 IS_WORKFLOW_STARTED_QUESTION = new I18n0("Is Workflow Started?")//
		.de("Ist Arbeitsablauf gestartet?");
	I18n0 MANAGE_WORKFLOW = new I18n0("Manage Workflow")//
		.de("Arbeitsablauf verwalten");
	I18n2 MESSAGE_FOR_ITEM_ARG1_AT_ARG2 = new I18n2("Message for Item '%s' at '%s'")//
		.de("Nachricht für Element '%s' am '%s'");
	I18n0 MESSAGES = new I18n0("Messages")//
		.de("Nachrichten");
	I18n0 MISSING_SOURCE_CODE_REFERENCE_POINT = new I18n0("Missing source code reference point.")//
		.de("Quellcode-Referenzpunkt fehlt.");
	I18n0 MISSING_WORKFLOW_ROOT_NODE = new I18n0("Missing workflow root node.")//
		.de("Wurzel-Knoten des Arbeitsablaufs fehlt.");
	I18n0 MY_TASKS = new I18n0("My Tasks")//
		.de("Meine Aufgaben");
	I18n0 NEW_WORKFLOW_TASK = new I18n0("New Workflow Task")//
		.de("Neue Arbeitsablauf-Aufgabe");
	I18n0 NO_CURRENT_WORKFLOW_VERSION_FOUND = new I18n0("No current workflow version found.")//
		.de("Keine aktuelle Arbeitsablauf-Version gefunden.");
	I18n0 NO_FURTHER_APPROVAL_IS_REQUIRED = new I18n0("No further approval is required.")//
		.de("Keine weitere Genehmigung ist notwendig.");
	I18n0 NO_WORKFLOW_FOUND = new I18n0("No workflow found.")//
		.de("Kein Arbeitsablauf gefunden.");
	I18n1 NODE_ARG1 = new I18n1("Node: %s")//
		.de("Knoten: %s");
	I18n0 NODES = new I18n0("Nodes")//
		.de("Knoten");
	I18n0 NOTIFY = new I18n0("Notify")//
		.de("Benachrichtigen");
	I18n0 OBJECT = new I18n0("Object")//
		.de("Objekt");
	I18n0 OBSOLETE_VERSION = new I18n0("Obsolete Version")//
		.de("Veraltete Version");
	I18n0 OPEN_TASKS = new I18n0("Open Tasks")//
		.de("Offene Aufgaben");
	I18n2 PERMISSION_ARG1_OF_WORKFLOW_TRANSITION_ARG2_IS_MISSING = new I18n2("Permission '%s' of workflow transition '%s' is missing.")//
		.de("Berechtigung %s des Arbeitsablauf-Übergangs %s fehlt.");
	I18n0 PLEASE_PROVIDE_A_RATIONALE = new I18n0("Please provide a rationale.")//
		.de("Bitte geben Sie eine Begründung an.");
	I18n0 PLEASE_REFRESH_THE_INPUT_ELEMENT_OR_PRESS_F5_TO_RELOAD_THE_SCREEN = new I18n0("Please refresh the input element or press F5 to reload the screen.")//
		.de("Bitte das Eingabeelement aktualisieren oder F5 drücken, um den Bildschirm neu zu laden.");
	I18n2 PRECONDITION_FUNCTION_ARG1_OF_WORKFLOW_NODE_ARG2_IS_MISSING = new I18n2("Precondition function '%s' of workflow node '%s' is missing.")//
		.de("Vorbedingungsfunktion %s des Arbeitsablauf-Knotens %s fehlt.");
	I18n0 PRECONDITIONS = new I18n0("Preconditions")//
		.de("Vorbedingungen");
	I18n0 REQUIRED_VOTES = new I18n0("Required Votes")//
		.de("Benötigte Stimmen");
	I18n0 REQUIRED_VOTES_NOT_VALID = new I18n0("Required votes not valid.")//
		.de("Benötigte Stimmen nicht gültig.");
	I18n0 ROOT_NODE = new I18n0("Root Node")//
		.de("Wurzel-Knoten");
	I18n0 SHOW_GRAPH = new I18n0("Show Graph")//
		.de("Graph anzeigen");
	I18n0 SHOW_MORE = new I18n0("Show More")//
		.de("Zeige mehr");
	I18n0 SHOW_TASKS_DELEGATED_BY_ME = new I18n0("Show Tasks Delegated By Me")//
		.de("Von mir delegierte Aufgaben anzeigen");
	I18n0 SHOW_TRANSITIONS = new I18n0("Show Transitions")//
		.de("Übergänge anzeigen");
	I18n0 SHOW_USERS = new I18n0("Show Users")//
		.de("Zeige Benutzer");
	I18n0 SIDE_EFFECT = new I18n0("Side Effect")//
		.de("Nebeneffekt");
	I18n2 SIDE_EFFECT_ARG1_OF_WORKFLOW_TRANSITION_ARG2_IS_MISSING = new I18n2("Side effect '%s' of workflow transition '%s' is missing.")//
		.de("Nebeneffekt %s des Arbeitsablauf-Übergangs %s fehlt.");
	I18n1 SIDE_EFFECT_IS_NOT_COMPATIBLE_WITH_ARG1 = new I18n1("Side effect is not compatible with %s.")//
		.de("Nebeneffekt ist nicht kompatibel mit %s.");
	I18n0 SOURCE_CODE_REFERENCE_POINT_IS_NOT_A_VALID_SIDE_EFFECT = new I18n0("Source code reference point is not a valid side effect.")//
		.de("Quellcode-Referenzpunkt ist kein gültiger Nebeneffekt.");
	I18n0 SOURCE_NODE = new I18n0("Source Node")//
		.de("Ursprungsknoten");
	I18n0 START_WORKFLOW = new I18n0("Start Workflow")//
		.de("Arbeitsablauf starten");
	I18n0 SUBSTITUTE = new I18n0("Substitute")//
		.de("Vertretung");
	I18n0 SUBSTITUTE_DEFINED = new I18n0("Substitute defined")//
		.de("Vertretung definiert");
	I18n1 SUBSTITUTE_FOR_ARG1 = new I18n1("Substitute for %s")//
		.de("Vertretung für %s");
	I18n0 SUBSTITUTE_FROM = new I18n0("Substitute from")//
		.de("Vertretung seit");
	I18n0 SUBSTITUTE_FROM_IS_AFTER_SUBSTITUTE_TO = new I18n0("'Substitute from' is after 'Substitute to'")//
		.de("'Vertretung seit' ist nach 'Vertretung bis'");
	I18n0 SUBSTITUTE_TO = new I18n0("Substitute to")//
		.de("Vertretung bis");
	I18n0 TARGET_NODE = new I18n0("Target Node")//
		.de("Zielknoten");
	I18n0 TARGET_USER_IS_SYSTEM_USER = new I18n0("Target user is system user.")//
		.de("Ziel-Benutzer ist System-Benutzer.");
	I18n0 TASK = new I18n0("Task")//
		.de("Aufgabe");
	I18n2 TASK_ARG1_EXECUTED_TRANSITION_ARG2 = new I18n2("Task '%s' executed transition '%s'.")//
		.de("Aufgabe '%s' hat den Übergang '%s' ausgeführt.");
	I18n1 TASK_DELEGATED_TO_ARG1 = new I18n1("Task delegated to %s.")//
		.de("Aufgabe delegiert an %s.");
	I18n0 THE_ITEM_RESIDES_IN_AN_UNEXPECTED_WORKFLOW_NODE = new I18n0("The item resides in an unexpected workflow node.")//
		.de("Das Element befindet sich in einem unerwarteten Arbeitsablaufknoten.");
	I18n0 THIS_ACTION_IS_NOT_AVAILABLE_ANYMORE = new I18n0("This action is not available anymore.")//
		.de("Diese Aktion ist nicht mehr verfügbar.");
	I18n0 TRANSITION = new I18n0("Transition")//
		.de("Übergang");
	I18n2 TRANSITION_FROM_WORKFLOW_NODE_ARG1_TO_ARG2 = new I18n2("Transition from workflow node %s to %s.")//
		.de("Übergang von Arbeitsablaufknoten %s zu %s.");
	I18n0 TRANSITION_ICON = new I18n0("Transition Icon")//
		.de("Übergangs-Icon");
	I18n0 TRANSITION_PERMISSION = new I18n0("Transition Permission")//
		.de("Übergangsberechtigung");
	I18n1 TRANSITION_TO_ARG1 = new I18n1("Transition to '%s'")//
		.de("Übergang nach '%s'");
	I18n0 TRANSITIONS = new I18n0("Transitions")//
		.de("Übergänge");
	I18n0 VERSIONS = new I18n0("Versions")//
		.de("Versionen");
	I18n0 WORKFLOW = new I18n0("Workflow")//
		.de("Arbeitsablauf");
	I18n0 WORKFLOW_ACTION_AVAILABLE = new I18n0("Workflow action available")//
		.de("Arbeitsablauf-Aktion verfügbar");
	I18n0 WORKFLOW_ALREADY_STARTED = new I18n0("Workflow already started.")//
		.de("Arbeitsablauf bereits gestartet.");
	I18n0 WORKFLOW_AUTO_TRANSITION_EXECUTION = new I18n0("Workflow Auto Transition Execution")//
		.de("Ausführung eines automatischen Arbeitsablauf-Übergangs");
	I18n0 WORKFLOW_AUTO_TRANSITION_EXECUTIONS = new I18n0("Workflow Auto Transition Executions")//
		.de("Ausführungen eines automatischen Arbeitsablauf-Übergangs");
	I18n0 WORKFLOW_DEMO_OBJECT = new I18n0("Workflow Demo Object")//
		.de("Arbeitsablauf-Demo-Objekt");
	I18n0 WORKFLOW_DEMO_OBJECT_APPROVER = new I18n0("Workflow Demo Object Approver")//
		.de("Arbeitsablauf-Demo-Objekt-Genehmiger");
	I18n0 WORKFLOW_DEMO_OBJECT_APPROVER_LOG = new I18n0("Workflow Demo Object Approver Log")//
		.de("Arbeitsablauf-Demo-Objekt-Genehmiger-Log");
	I18n0 WORKFLOW_DEMO_OBJECT_APPROVER_LOGS = new I18n0("Workflow Demo Object Approver Logs")//
		.de("Arbeitsablauf-Demo-Objekt-Genehmiger-Logs");
	I18n0 WORKFLOW_DEMO_OBJECT_APPROVERS = new I18n0("Workflow Demo Object Approvers")//
		.de("Arbeitsablauf-Demo-Objekt-Genehmiger");
	I18n0 WORKFLOW_DEMO_OBJECT_LOG = new I18n0("Workflow Demo Object Log")//
		.de("Arbeitsablauf-Demo-Objekt-Log");
	I18n0 WORKFLOW_DEMO_OBJECT_LOGS = new I18n0("Workflow Demo Object Logs")//
		.de("Arbeitsablauf-Demo-Objekt-Logs");
	I18n0 WORKFLOW_DEMO_OBJECTS = new I18n0("Workflow Demo Objects")//
		.de("Arbeitsablauf-Demo-Objekte");
	I18n0 WORKFLOW_ICON = new I18n0("Workflow Icon")//
		.de("Arbeitsablauf-Icon");
	I18n0 WORKFLOW_ICON_LOG = new I18n0("Workflow Icon Log")//
		.de("Arbeitsablauf-Icon-Log");
	I18n0 WORKFLOW_ICON_LOGS = new I18n0("Workflow Icon Logs")//
		.de("Arbeitsablauf-Icon-Logs");
	I18n0 WORKFLOW_ICONS = new I18n0("Workflow Icons")//
		.de("Arbeitsablauf-Icons");
	I18n0 WORKFLOW_ITEM = new I18n0("Workflow Item")//
		.de("Arbeitsablauf-Element");
	I18n2 WORKFLOW_ITEM_ARG1_HAS_MORE_THAN_ONE_EXECUTABLE_AUTO_TRANSITION_ARG2 =
			new I18n2("Workflow item '%s' has more than one executable auto transition:\n%s")//
				.de("Arbeitsablauf-Element '%s' hat mehr als einen ausführbaren automatischen Übergang:\n%s");
	I18n0 WORKFLOW_ITEM_HAS_ALREADY_BEEN_CHANGED = new I18n0("Workflow item has already been changed.")//
		.de("Arbeitsablaufelement wurde bereits geändert.");
	I18n0 WORKFLOW_ITEM_HISTORY = new I18n0("Workflow Item History")//
		.de("Arbeitsablauf-Element-Historie");
	I18n0 WORKFLOW_ITEM_LOG = new I18n0("Workflow Item Log")//
		.de("Arbeitsablauf-Element Log");
	I18n0 WORKFLOW_ITEM_LOGS = new I18n0("Workflow Item Logs")//
		.de("Arbeitsablauf-Elemente Log");
	I18n0 WORKFLOW_ITEM_MESSAGE = new I18n0("Workflow Item Message")//
		.de("Arbeitsablauf-Element-Nachricht");
	I18n0 WORKFLOW_ITEM_MESSAGES = new I18n0("Workflow Item Messages")//
		.de("Arbeitsablauf-Element-Nachrichten");
	I18n0 WORKFLOW_ITEM_PRESENT = new I18n0("Workflow Item Present")//
		.de("Arbeitsablauf-Element vorhanden");
	I18n0 WORKFLOW_ITEMS = new I18n0("Workflow Items")//
		.de("Arbeitsablauf-Elemente");
	I18n0 WORKFLOW_LOG = new I18n0("Workflow Log")//
		.de("Arbeitsablauf-Log");
	I18n0 WORKFLOW_LOGS = new I18n0("Workflow Logs")//
		.de("Arbeitsablauf-Logs");
	I18n0 WORKFLOW_MESSAGE_SEVERITIES = new I18n0("Workflow Message Severities")//
		.de("Arbeitsablauf-Nachrichtenschweregrade");
	I18n0 WORKFLOW_MESSAGE_SEVERITY = new I18n0("Workflow Message Severity")//
		.de("Arbeitsablauf-Nachrichtenschweregrad");
	I18n0 WORKFLOW_MODULE_INSTANCE = new I18n0("Workflow Module Instance")//
		.de("Arbeitsablauf-Modulinstanz");
	I18n0 WORKFLOW_MODULE_INSTANCES = new I18n0("Workflow Module Instances")//
		.de("Arbeitsablauf-Modulinstanzen");
	I18n0 WORKFLOW_NODE = new I18n0("Workflow Node")//
		.de("Arbeitsablauf-Knoten");
	I18n0 WORKFLOW_NODE_ACTION = new I18n0("Workflow Node Action")//
		.de("Arbeitsablauf-Knoten-Aktion");
	I18n0 WORKFLOW_NODE_ACTION_LOG = new I18n0("Workflow Node Action Log")//
		.de("Arbeitsablauf-Knoten-Aktion-Log");
	I18n0 WORKFLOW_NODE_ACTION_LOGS = new I18n0("Workflow Node Action Logs")//
		.de("Arbeitsablauf-Knoten-Aktion-Logs");
	I18n0 WORKFLOW_NODE_ACTION_PERMISSION = new I18n0("Workflow Node Action Permission")//
		.de("Arbeitsablauf-Knoten-Aktionsberechtigung");
	I18n0 WORKFLOW_NODE_ACTION_PERMISSION_LOG = new I18n0("Workflow Node Action Permission Log")//
		.de("Arbeitsablauf-Knoten-Aktionsberechtigungs-Log");
	I18n0 WORKFLOW_NODE_ACTION_PERMISSION_LOGS = new I18n0("Workflow Node Action Permission Logs")//
		.de("Arbeitsablauf-Knoten-Aktionsberechtigungs-Logs");
	I18n0 WORKFLOW_NODE_ACTION_PERMISSIONS = new I18n0("Workflow Node Action Permissions")//
		.de("Arbeitsablauf-Knoten-Aktionsberechtigungen");
	I18n0 WORKFLOW_NODE_ACTIONS = new I18n0("Workflow Node Actions")//
		.de("Arbeitsablauf-Knoten-Aktionen");
	I18n0 WORKFLOW_NODE_LOG = new I18n0("Workflow Node Log")//
		.de("Arbeitsablauf-Knoten-Log");
	I18n0 WORKFLOW_NODE_LOGS = new I18n0("Workflow Node Logs")//
		.de("Arbeitsablauf-Knoten-Logs");
	I18n0 WORKFLOW_NODE_PRECONDITION = new I18n0("Workflow Node Precondition")//
		.de("Arbeitsablauf-Knoten-Vorbedingung");
	I18n0 WORKFLOW_NODE_PRECONDITION_LOG = new I18n0("Workflow Node Precondition Log")//
		.de("Arbeitsablauf-Knoten-Vorbedingung-Log");
	I18n0 WORKFLOW_NODE_PRECONDITION_LOGS = new I18n0("Workflow Node Precondition Logs")//
		.de("Arbeitsablauf-Knoten-Vorbedingung-Logs");
	I18n0 WORKFLOW_NODE_PRECONDITIONS = new I18n0("Workflow Node Preconditions")//
		.de("Arbeitsablauf-Knoten-Vorbedingungen");
	I18n0 WORKFLOW_NODES = new I18n0("Workflow Nodes")//
		.de("Arbeitsablauf-Knoten");
	I18n0 WORKFLOW_STARTED = new I18n0("Workflow started.")//
		.de("Arbeitsablauf gestartet.");
	I18n0 WORKFLOW_STATUS = new I18n0("Workflow Status")//
		.de("Arbeitsablauf-Status");
	I18n0 WORKFLOW_TASK = new I18n0("Workflow Task")//
		.de("Arbeitsablauf-Aufgabe");
	I18n0 WORKFLOW_TASK_DELEGATION = new I18n0("Workflow Task Delegation")//
		.de("Arbeitsablauf-Aufgabe-Delegierung");
	I18n0 WORKFLOW_TASK_DELEGATION_LOG = new I18n0("Workflow Task Delegation Log")//
		.de("Arbeitsablauf-Aufgabe-Delegierung-Log");
	I18n0 WORKFLOW_TASK_DELEGATION_LOGS = new I18n0("Workflow Task Delegation Logs")//
		.de("Arbeitsablauf-Aufgabe-Delegierung-Logs");
	I18n0 WORKFLOW_TASK_DELEGATIONS = new I18n0("Workflow Task Delegations")//
		.de("Arbeitsablauf-Aufgabe-Delegierungen");
	I18n0 WORKFLOW_TASK_LOG = new I18n0("Workflow Task Log")//
		.de("Arbeitsablauf-Aufgabe-Log");
	I18n0 WORKFLOW_TASK_LOGS = new I18n0("Workflow Task Logs")//
		.de("Arbeitsablauf-Aufgabe-Logs");
	I18n0 WORKFLOW_TASKS = new I18n0("Workflow Tasks")//
		.de("Arbeitsablauf-Aufgaben");
	I18n0 WORKFLOW_TRANSITION = new I18n0("Workflow Transition")//
		.de("Arbeitsablauf-Übergang");
	I18n0 WORKFLOW_TRANSITION_ACTION_PERMISSION = new I18n0("Workflow Transition Action Permission")//
		.de("Arbeitsablauf-Übergangs-Aktionenberechtigung");
	I18n0 WORKFLOW_TRANSITION_EXECUTION = new I18n0("Workflow Transition Execution")//
		.de("Ausführung eines Arbeitsablauf-Übergangs");
	I18n0 WORKFLOW_TRANSITION_EXECUTIONS = new I18n0("Workflow Transition Executions")//
		.de("Ausführungen eines Arbeitsablauf-Übergangs");
	I18n0 WORKFLOW_TRANSITION_LOG = new I18n0("Workflow Transition Log")//
		.de("Arbeitsablauf-Übergang-Log");
	I18n0 WORKFLOW_TRANSITION_LOGS = new I18n0("Workflow Transition Logs")//
		.de("Arbeitsablauf-Übergang-Logs");
	I18n0 WORKFLOW_TRANSITION_PERMISSION = new I18n0("Workflow Transition Permission")//
		.de("Arbeitsablauf-Übergangsberechtigung");
	I18n0 WORKFLOW_TRANSITION_PERMISSION_LOG = new I18n0("Workflow Transition Permission Log")//
		.de("Arbeitsablauf-Übergangsberechtigungs-Log");
	I18n0 WORKFLOW_TRANSITION_PERMISSION_LOGS = new I18n0("Workflow Transition Permission Logs")//
		.de("Arbeitsablauf-Übergangsberechtigungs-Logs");
	I18n0 WORKFLOW_TRANSITION_PERMISSIONS = new I18n0("Workflow Transition Permissions")//
		.de("Arbeitsablauf-Übergangsberechtigungen");
	I18n0 WORKFLOW_TRANSITIONS = new I18n0("Workflow Transitions")//
		.de("Arbeitsablauf-Übergänge");
	I18n0 WORKFLOW_USER_CONFIGURATION = new I18n0("Workflow User Configuration")//
		.de("Arbeitsablauf-Benutzer-Konfiguration");
	I18n0 WORKFLOW_USER_CONFIGURATION_LOG = new I18n0("Workflow User Configuration Log")//
		.de("Arbeitsablauf-Benutzer-Konfigurations-Log");
	I18n0 WORKFLOW_USER_CONFIGURATION_LOGS = new I18n0("Workflow User Configuration Logs")//
		.de("Arbeitsablauf-Benutzer-Konfigurations-Logs");
	I18n0 WORKFLOW_USER_CONFIGURATIONS = new I18n0("Workflow User Configurations")//
		.de("Arbeitsablauf-Benutzer-Konfigurationen");
	I18n0 WORKFLOW_USERS = new I18n0("Workflow Users")//
		.de("Arbeitsablauf-Benutzer");
	I18n0 WORKFLOW_VERSION = new I18n0("Workflow Version")//
		.de("Arbeitsablauf-Version");
	I18n0 WORKFLOW_VERSION_FINALIZED = new I18n0("Workflow Version Finalized")//
		.de("Arbeitsablauf-Version finalisiert");
	I18n0 WORKFLOW_VERSION_LOG = new I18n0("Workflow Version Log")//
		.de("Arbeitsablauf-Versions-Log");
	I18n0 WORKFLOW_VERSION_LOGS = new I18n0("Workflow Version Logs")//
		.de("Arbeitsablauf-Versions-Logs");
	I18n0 WORKFLOW_VERSION_PRESENT = new I18n0("Workflow Version Present")//
		.de("Arbeitsablauf-Version vorhanden");
	I18n0 WORKFLOW_VERSIONS = new I18n0("Workflow Versions")//
		.de("Arbeitsablauf-Versionen");
	I18n0 WORKFLOWS = new I18n0("Workflows")//
		.de("Arbeitsabläufe");
	I18n0 X_COORDINATE = new I18n0("X Coordinate")//
		.de("X-Koordinate");
	I18n0 Y_COORDINATE = new I18n0("Y Coordinate")//
		.de("Y-Koordinate");
	I18n1 YOU_ARE_SUBSTITUTE_FOR_ARG1 = new I18n1("You are substitute for: %s")//
		.de("Sie verteten: %s");
	I18n0 YOU_CAN_NOT_SUBSTITUTE_YOURSELF = new I18n0("You can not substitute yourself")//
		.de("Sie können sich nicht selbst vertreten");
}
