package com.softicar.platform.dom.event;

/**
 * Contains an incomplete list of HTML standard key names.
 * <p>
 * All key names are case-sensitive.
 * <p>
 * Key names are returned for example by {@link IDomEvent#getKey()}.
 *
 * @author Oliver Richers
 */
public interface DomKeys {

	// special keys
	String UNIDENTIFIED = "Unidentified";

	// modifier keys
	String ALT = "Alt";
	String ALT_GRAPH = "AltGraph";
	String CAPS_LOCL = "CapsLock";
	String CONTROL = "Control";
	String FN = "Fn";
	String FN_LOCK = "FnLock";
	String HYPER = "Hyper";
	String META = "Meta";
	String NUM_LOCK = "NumLock";
	String SCROLL_LOCK = "ScrollLock";
	String SHIFT = "Shift";
	String SUPER = "Super";
	String SYMBOL = "Symbol";
	String SYMBOL_LOCK = "SymbolLock";

	// whitespace keys
	String ENTER = "Enter";
	String TAB = "Tab";
	String SPACE = " ";

	// navigation keys
	String ARROW_DOWN = "ArrowDown";
	String ARROW_LEFT = "ArrowLeft";
	String ARROW_RIGHT = "ArrowRight";
	String ARROW_UP = "ArrowUp";
	String END = "End";
	String HOME = "Home";
	String PAGE_DOWN = "PageDown";
	String PAGE_UP = "PageUp";

	// editing keys
	String BACKSPACE = "Backspace";
	String CLEAR = "Clear";
	String COPY = "Copy";
	String CR_SEL = "CrSel";
	String CUT = "Cut";
	String DELETE = "Delete";
	String ERASE_EOF = "EraseEof";
	String EX_SEL = "ExSel";
	String INSERT = "Insert";
	String PASTE = "Paste";
	String REDO = "Redo";
	String UNDO = "Undo";

	// UI keys
	String ACCEPT = "Accept";
	String AGAIN = "Again";
	String ATTN = "Attn";
	String CANCEL = "Cancel";
	String CONTEXT_MENU = "ContextMenu";
	String ESCAPE = "Escape";
	String EXECUTE = "Execute";
	String FIND = "Find";
	String FINISH = "Finish";
	String HELP = "Help";
	String PAUSE = "Pause";
	String PLAY = "Play";
	String PROPS = "Props";
	String SELECT = "Select";
	String ZOOM_IN = "ZoomIn";
	String ZOOM_OUT = "ZoomOut";

	// device keys
	String BRIGHTNESS_DOWN = "BrightnessDown";
	String BRIGHTNESS_UP = "BrightnessUp";
	String EJECT = "Eject";
	String LOG_OFF = "LogOff";
	String POWER = "Power";
	String POWER_OFF = "PowerOff";
	String PRINT_SCREEN = "PrintScreen";
	String HIBERNATE = "Hibernate";
	String STAND_BY = "Standby";
	String WAKE_UP = "WakeUp";

	// function keys (incomplete)
	String F1 = "F1";
	String F2 = "F2";
	String F3 = "F3";
	String F4 = "F4";
	String F5 = "F5";
	String F6 = "F6";
	String F7 = "F7";
	String F8 = "F8";
	String F9 = "F9";
	String F10 = "F10";
	String F11 = "F11";
	String F12 = "F12";
}
