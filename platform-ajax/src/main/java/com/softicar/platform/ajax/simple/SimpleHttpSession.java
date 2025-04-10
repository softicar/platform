package com.softicar.platform.ajax.simple;

import com.softicar.platform.common.container.iterator.IteratorEnumeration;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

public class SimpleHttpSession implements HttpSession {

	private final String id;
	private final Map<String, Object> attributes;
	private final long creationTime;
	private int maxInactiveInterval;

	public SimpleHttpSession(String id) {

		this.id = id;
		this.attributes = new TreeMap<>();
		this.creationTime = System.currentTimeMillis();
		this.maxInactiveInterval = 0;
	}

	@Override
	public String getId() {

		return id;
	}

	// -------------------- attribute -------------------- //

	@Override
	public Object getAttribute(String name) {

		return attributes.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {

		attributes.put(name, value);
	}

	@Override
	public Enumeration<String> getAttributeNames() {

		return new IteratorEnumeration<>(attributes.keySet().iterator());
	}

	@Override
	public void removeAttribute(String name) {

		attributes.remove(name);
	}

	// -------------------- miscellaneous -------------------- //

	@Override
	public long getCreationTime() {

		return creationTime;
	}

	@Override
	public long getLastAccessedTime() {

		return creationTime;
	}

	@Override
	public ServletContext getServletContext() {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setMaxInactiveInterval(int interval) {

		this.maxInactiveInterval = interval;
	}

	@Override
	public int getMaxInactiveInterval() {

		return maxInactiveInterval;
	}

	@Override
	public void invalidate() {

		// nothing to do
	}

	@Override
	public boolean isNew() {

		return false;
	}
}
