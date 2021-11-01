package com.softicar.platform.dom.engine;

/**
 * Represents a library with script code which can be loaded via
 * {@link IDomEngine#loadScriptLibrary(IDomScriptLibrary)}.
 * 
 * @author Oliver Richers
 */
public interface IDomScriptLibrary {

	/**
	 * Returns the source code of this script library.
	 * 
	 * @return the library source code (never null)
	 */
	String getCode();

	/**
	 * Returns the hash of the source code returned by {@link #getCode()}.
	 * <p>
	 * This hash is used to decide if the script library was already loaded. Of
	 * course, this hash could also be generated automatically, but this method
	 * adds the opportunity to cache the source code hash. So, the hash returned
	 * by this method must always be the same.
	 * 
	 * @return the hash of the source code (never null)
	 */
	String getCodeHash();
}
