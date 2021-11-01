
function ValueNodeMap() {

	var nodes = {};              // id (integer) --> node object
	var approvedValues = {};     // id (integer) --> approved value
	var unapprovedValues = null; // id (integer) --> non-approved value

	// add node and assume its value to be approved
	this.addValueNode = function(nodeId, node) {

		nodes[nodeId] = node;
		approvedValues[nodeId] = getCurrentValue(node);
	};

	// removes all information about the node with the specified nodeId
	this.removeValueNode = function(nodeId) {

		delete nodes[nodeId];
		delete approvedValues[nodeId];
		delete unapprovedValues[nodeId];
	};

	// approves all un-approved values
	this.approveNodeValues = function() {

		for(var nodeId in unapprovedValues) {
			approvedValues[nodeId] = unapprovedValues[nodeId];
		}
		unapprovedValues = null;
	};

	// transfers all changed values of value nodes to the specified data structure;
	// the specified data structure can then be sent to the ajax server
	this.copyNodeValues = function(data) {

		unapprovedValues = {};
		for(var nodeId in nodes) {
			var currentValue = getCurrentValue(nodes[nodeId]);
			var approvedValue = approvedValues[nodeId];
			if(approvedValue == null || currentValue != approvedValue) {
				data['V'+nodeId] = currentValue;
				unapprovedValues[nodeId] = currentValue;
			}
		}
	};

	this.isValueChanged = function(node) {

		var currentValue = getCurrentValue(node);
		var approvedValue = getApprovedValue(node);
		return approvedValue === undefined || currentValue != approvedValue;
	};

	this.setValue = function(node, value) {

		node.value = value;
		approveValue(node);

		AUTO_COMPLETE_ENGINE.setCommittedValue(node, value);
	};

	this.setChecked = function(node, checked) {

		node.checked = checked;
		approveValue(node);
	};

	this.setSelectedOptions = function(node, optionIDs) {

		var type = node.type;
		if(type == "select-one") {
			GLOBAL.getNode(optionIDs[0]).selected = true;
		} else if(type == "select-multiple") {
			for(var i in node.options) {
				node.options[i].selected = false;
			}
			for(var i in optionIDs) {
				GLOBAL.getNode(optionIDs[i]).selected = true;
			}
		} else {
			alert("Unknown select type " + type + ".");
		}

		approveValue(node);
	};

	function getCurrentValue(node) {

		switch(node.tagName.toUpperCase()) {
		case 'INPUT':
			if(node.type.toUpperCase() == 'CHECKBOX' || node.type.toUpperCase() == 'RADIO')
				return ''+node.checked;
			else
				return ''+node.value;
		case 'TEXTAREA':
			return node.value;
		case 'SELECT':
			var options = [];
			for(var j = 0; j != node.options.length; ++j)
				if(node.options[j].selected)
					options.push(node.options.item(j).id);
			return options.join(',');
		}
	}

	function getApprovedValue(node) {

		var nodeId = getNodeId(node);
		return approvedValues[nodeId];
	}

	function approveValue(node) {

		var nodeId = getNodeId(node);
		approvedValues[nodeId] = getCurrentValue(node);
	}

	function getNodeId(node) {

		return node.id.substr(1);
	}
}
