package com.softicar.platform.db.runtime.query;

class MapToStringBuilder {

	private final StringBuilder builder;
	private boolean first;
	private boolean closed;

	public MapToStringBuilder() {

		this.builder = new StringBuilder("[");
		this.first = true;
		this.closed = false;
	}

	public void append(String key, Object value) {

		// check state
		if (closed) {
			throw new IllegalStateException("Tried to append key-value pair to a closed string builder.");
		}

		// prepend comma
		if (!first) {
			builder.append(", ");
		}

		// append key and value
		builder.append(key).append(": ").append(value == null? "NULL" : value.toString());
		first = false;
	}

	@Override
	public String toString() {

		if (!closed) {
			builder.append("]");
			closed = true;
		}

		return builder.toString();
	}
}
