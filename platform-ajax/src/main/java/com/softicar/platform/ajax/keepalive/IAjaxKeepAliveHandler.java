package com.softicar.platform.ajax.keepalive;

/**
 * Usually, this handler does nothing.
 * <p>
 * It's purpose is to keep the user session alive.
 * 
 * @author Oliver Richers
 */
public interface IAjaxKeepAliveHandler {

	public void handleKeepAlive();
}
