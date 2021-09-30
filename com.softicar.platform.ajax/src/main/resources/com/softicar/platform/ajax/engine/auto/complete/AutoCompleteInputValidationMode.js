
/**
 * Defines the emulated enum AutoCompleteInputValidationMode.
 * <p>
 * AutoCompleteInputValidationMode combines AutoCompleteInputValidationModeEnum
 * and AutoCompleteInputValidationModeEnumValue to provide Java-like "enum with methods"
 * usability. Only AutoCompleteInputValidationMode should be used outside of this file.
 * <p>
 * Note: The entries and IDs in this emulated enum must reflect those in its Java counterpart.
 */
function AutoCompleteInputValidationModeEnum() {

	// -------------------- interface -------------------- //
	
	this.createValue = createValue;
	this.getById = getById;

	// -------------------- initialization -------------------- //
	
	var map = new Array();
	this.DEDUCTIVE = this.createValue(1);
	this.PERMISSIVE = this.createValue(2);
	this.RESTRICTIVE = this.createValue(3);
	
	// -------------------- logic -------------------- //
	
	function createValue(id) {
		
		return map[id] = new AutoCompleteInputValidationModeEnumValue();
	}
	
	function getById(id) {
		
		var modeValue = map[id];
		if(!!modeValue) {
			return modeValue;
		} else {
			console.log("WARNING: Failed to identify mode for id " + id + ". Defaulting to deductive mode.");
			return AutoCompleteInputValidationMode.DEDUCTIVE;
		}
	}
};

function AutoCompleteInputValidationModeEnumValue() {
	
	this.isDeductive = isDeductive;
	this.isPermissive = isPermissive;
	this.isRestrictive = isRestrictive;
	
	function isDeductive() {
		
		return this == AutoCompleteInputValidationMode.DEDUCTIVE;
	}
	
	function isPermissive() {
		
		return this === AutoCompleteInputValidationMode.PERMISSIVE;
	}
	
	function isRestrictive() {
		
		return this === AutoCompleteInputValidationMode.RESTRICTIVE;
	}
};

var AutoCompleteInputValidationMode = new AutoCompleteInputValidationModeEnum();
