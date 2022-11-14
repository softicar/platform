package com.softicar.platform.core.module.ajax.listener;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.thread.collection.ThreadKiller;
import com.softicar.platform.common.core.utils.DevNull;
import java.lang.reflect.Field;

public class BatikCleanerThreadManager {

	private static final String BATIK_CLEANER_THREAD_CLASS = "org.apache.batik.util.CleanerThread";

	@SuppressWarnings("deprecation")
	public void shutdownThread() {

		Log.finfo("Starting shutdown of %s", BATIK_CLEANER_THREAD_CLASS);
		try {
			Class<?> batikCleanerThreadClass = Class.forName(BATIK_CLEANER_THREAD_CLASS);
			Field threadField = batikCleanerThreadClass.getDeclaredField("thread");
			threadField.setAccessible(true);
			Thread thread = (Thread) threadField.get(null);
			if (thread != null) {
				if (new ThreadKiller<>(thread).setTimeout(5000).killAll()) {
					Log.finfo("Successfull shutdown of %s", BATIK_CLEANER_THREAD_CLASS);
				} else {
					Log.finfo("Failed shutdown of %s (stupid thread does not want to die)", BATIK_CLEANER_THREAD_CLASS);
					Log.finfo("Calling Thread.stop()");
					thread.stop();
				}
			} else {
				Log.finfo("Skipping shutdown of %s (no thread started)", BATIK_CLEANER_THREAD_CLASS);
			}
		} catch (ClassNotFoundException exception) {
			DevNull.swallow(exception);
			Log.finfo("Class %s not found, so nothing to do.", BATIK_CLEANER_THREAD_CLASS);
		} catch (Exception exception) {
			Log.fwarning("Failed shutdown of %s", BATIK_CLEANER_THREAD_CLASS);
			exception.printStackTrace();
		}
	}
}
