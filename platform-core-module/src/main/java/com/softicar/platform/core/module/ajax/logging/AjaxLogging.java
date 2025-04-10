package com.softicar.platform.core.module.ajax.logging;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.throwable.ThrowableCauseTester;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.ajax.exception.AGAjaxException;
import com.softicar.platform.core.module.ajax.session.AGAjaxSession;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import com.softicar.platform.core.module.ajax.session.revision.AGAjaxSessionRevision;
import com.softicar.platform.core.module.environment.LiveSystemRevision;
import com.softicar.platform.core.module.log.LogDb;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.web.servlet.HttpServletRequests;
import com.softicar.platform.db.core.transaction.DbTransaction;
import java.sql.SQLException;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;

public class AjaxLogging {

	public static AGAjaxSession logSessionCreation(HttpServletRequest request, AGUser user) {

		try (DbTransaction transaction = new DbTransaction()) {
			// insert session
			AGAjaxSession session = new AGAjaxSession();
			session.setUser(user);
			session.setClientIpAddress(HttpServletRequests.getClientAddress(request));
			session.setLocalName(request.getLocalName());
			session.setLocalAddress(request.getLocalAddr());
			session.setLocalPort(request.getLocalPort());
			session.setAccessDate(DayTime.now());
			session.save();

			// insert session revision
			Optional<String> revisionString = LiveSystemRevision.getCurrentRevision().getName();
			if (revisionString.isPresent()) {
				AGAjaxSessionRevision revision = AGAjaxSessionRevision.TABLE.getOrCreate(session);
				revision.setRevision(revisionString.get());
				revision.save();
			}

			transaction.commit();
			return session;
		} catch (Exception exception) {
			Log.error("Exception while saving page access.");
			Log.error("Got exception:");
			Log.error(exception.toString());
			Log.error(StackTraceFormatting.getStackTraceAsString(exception));
			return null; // never here
		}
	}

	public static void logException(Throwable throwable, HttpServletRequest request) {

		try {
			SofticarAjaxSession//
				.getInstance(request.getSession())
				.ifPresent(session -> logException(throwable, session));
		} catch (Exception exception) {
			Log.error("Exception while saving AJAX exception.");
			Log.error("Tried to save:");
			Log.error(throwable.toString());
			Log.error(StackTraceFormatting.getStackTraceAsString(throwable));
			Log.error("Got exception:\n");
			Log.error(exception.toString());
			Log.error(StackTraceFormatting.getStackTraceAsString(exception));
		}
	}

	public static void logException(Throwable throwable, SofticarAjaxSession session) {

		if (session != null) {
			AGAjaxException ajaxException = new AGAjaxException();
			ajaxException.setSession(session.getSession());
			ajaxException.setUser(session.getUser());
			ajaxException.setExceptionDate(DayTime.now());
			ajaxException.setExceptionType(throwable.getClass().getName());
			ajaxException.setExceptionText(throwable.getMessage());
			ajaxException.setExceptionStackTrace(StackTraceFormatting.getStackTraceAsString(throwable));
			ajaxException.save();

			if (isPanicWorthy(throwable)) {
				LogDb.panic(throwable);
			}
		}
	}

	private static boolean isPanicWorthy(Throwable throwable) {

		if (throwable instanceof SofticarUserException) {
			return false;
		} else if (throwable instanceof NullPointerException || throwable instanceof SofticarDeveloperException || throwable instanceof Error) {
			return true;
		} else if (new ThrowableCauseTester(SQLException.class).test(throwable)) {
			return true;
		} else {
			return false;
		}
	}
}
