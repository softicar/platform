package com.softicar.platform.core.module.ajax.listener;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.thread.collection.ThreadKiller;
import java.lang.reflect.Field;

public class BatikCleanerThreadManager {

	private static final String BATIK_CLEANER_THREAD_CLASS = "org.apache.batik.util.CleanerThread";

	@SuppressWarnings("deprecation")
	public void shutdownThread() {

		Log.finfo("starting shutdown of %s", BATIK_CLEANER_THREAD_CLASS);
		try {
			Class<?> batikCleanerThreadClass = Class.forName(BATIK_CLEANER_THREAD_CLASS);
			Field threadField = batikCleanerThreadClass.getDeclaredField("thread");
			threadField.setAccessible(true);
			Thread thread = (Thread) threadField.get(null);
			if (thread != null) {
				if (new ThreadKiller<>(thread).setTimeout(5000).killAll()) {
					Log.finfo("successfull shutdown of %s", BATIK_CLEANER_THREAD_CLASS);
				} else {
					Log.finfo("failed shutdown of %s (stupid thread does not want to die)", BATIK_CLEANER_THREAD_CLASS);
					Log.finfo("calling Thread.stop()");
					thread.stop();
				}
			} else {
				Log.finfo("skipping shutdown of %s (no thread started)", BATIK_CLEANER_THREAD_CLASS);
			}
		} catch (Exception exception) {
			Log.fwarning("failed shutdown of %s", BATIK_CLEANER_THREAD_CLASS);
			exception.printStackTrace();
		}
	}
}
