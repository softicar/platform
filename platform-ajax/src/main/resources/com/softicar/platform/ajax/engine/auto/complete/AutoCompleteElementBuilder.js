
function DomElementBuilder(tag) {

	var self = this;
	var element = document.createElement(tag);

	this.setId = function(id) {

		element.id = id;
		return self;
	};

	this.setClassName = function(className) {

		element.className = className;
		return self;
	};

	this.appendText = function(text) {

		element.appendChild(document.createTextNode(text));
		return self;
	};

	this.appendChild = function(child) {

		element.appendChild(child);
		return self;
	};

	this.appendNewChild = function(tag) {

		var builder = new DomElementBuilder(tag);
		element.appendChild(builder.getElement());
		return builder;
	};

	this.appendTo = function(parent) {

		parent.appendChild(element);
		return self;
	};

	this.getElement = function() {

		return element;
	};

	this.build = function() {

		return element;
	};
}
