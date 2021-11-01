
function AutoCompleteInputIndicatorModeEnum() {
	
	// -------------------- interface -------------------- //
	
	this.createValue = createValue;
	this.getById = getById;

	// -------------------- initialization -------------------- //
	
	var map = new Array();
	this.GENERIC = this.createValue(1);
	this.VALIDATION = this.createValue(2);
	
	// -------------------- logic -------------------- //
	
	function createValue(id) {
		
		return map[id] = new AutoCompleteInputIndicatorModeEnumValue();
	}
	
	function getById(id) {
		
		var modeValue = map[id];
		if(!!modeValue) {
			return modeValue;
		} else {
			console.log("WARNING: Failed to identify mode for id " + id + ". Defaulting to generic mode.");
			return AutoCompleteInputIndicatorMode.GENERIC;
		}
	}
};

function AutoCompleteInputIndicatorModeEnumValue() {
	
	this.isGeneric = isGeneric;
	this.isValidation = isValidation;
	
	function isGeneric() {
		
		return this == AutoCompleteInputIndicatorMode.GENERIC;
	}
	
	function isValidation() {
		
		return this == AutoCompleteInputIndicatorMode.VALIDATION;
	}
};

var AutoCompleteInputIndicatorMode = new AutoCompleteInputIndicatorModeEnum();
