package com.softicar.platform.core.module.log;

import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.environment.AGLiveSystemConfiguration;
import com.softicar.platform.core.module.environment.LiveSystemRevision;
import com.softicar.platform.core.module.log.configuration.CurrentLogDbConfiguration;
import com.softicar.platform.core.module.log.entry.point.CurrentLogDbEntryPoint;
import com.softicar.platform.core.module.log.message.AGLogMessage;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import com.softicar.platform.core.module.log.process.CurrentLogProcess;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.core.connection.DbConnectionOverrideScope;
import com.softicar.platform.db.core.connection.connector.DbConnectionFailureException;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * Stores log messages into the database.
 * <p>
 * This class saves the log messages in 2 tables, {@link AGLogProcess} and
 * {@link AGLogMessage}. The former contains one entry for the program start,
 * i.e. the process, the latter contains one entry for each log message of the
 * program.
 *
 * @author Marco Pilipovic
 * @author Oliver Richers
 * @author Semsudin Mekanic
 */
public class LogDb {

	public static void setProcessClass(Class<?> processClass) {

		CurrentLogDbEntryPoint.set(processClass);
	}

	/**
	 * Writes an informative log message into the database.
	 *
	 * @param message
	 *            message to be logged
	 * @param args
	 *            message formatting arguments
	 */
	public static void info(String message, Object...args) {

		log(LogLevel.INFO, null, message, args);
	}

	/**
	 * Writes a warning message into the log database.
	 *
	 * @param message
	 *            message to be logged
	 * @param args
	 *            message formatting arguments
	 */
	public static void warning(String message, Object...args) {

		log(LogLevel.WARNING, null, message, args);
	}

	/**
	 * Writes an error message into the log database.
	 *
	 * @param message
	 *            message to be logged
	 * @param args
	 *            message formatting arguments
	 */
	public static void error(String message, Object...args) {

		log(LogLevel.ERROR, null, message, args);
	}

	/**
	 * Writes a panic message into the log database.
	 * <p>
	 * Panic messages will usually cause an e-mail to be sent to the respective
	 * panic receiver.
	 *
	 * @param message
	 *            the message to log
	 * @param args
	 *            message formatting arguments
	 */
	public static void panic(String message, Object...args) {

		log(LogLevel.PANIC, null, message, args);
	}

	/**
	 * Writes a panic message into the log database.
	 * <p>
	 * The panic message text is taken from the given {@link Throwable} and its
	 * stack-trace is appended.
	 *
	 * @param throwable
	 *            the exception that occurred
	 */
	public static void panic(Throwable throwable) {

		panic(throwable, throwable.toString());
	}

	/**
	 * Writes a panic message into the log database.
	 * <p>
	 * The stack-trace of the given {@link Throwable} is appended to the given
	 * message text.
	 *
	 * @param throwable
	 *            the exception that occurred
	 * @param message
	 *            message to be logged
	 * @param args
	 *            message formatting arguments
	 */
	public static void panic(Throwable throwable, String message, Object...args) {

		log(LogLevel.PANIC, throwable, message, args);
	}

	private static void log(LogLevel logLevel, Throwable throwable, String message) {

		if (shouldLogToDatabase(throwable)) {
			try (DbConnectionOverrideScope scope = new DbConnectionOverrideScope()) {

				if (logLevel != LogLevel.INFO) {
					message = getMessagePrefix() + "\n\n" + message + "\n\n" + getStackTrace(throwable);
				}

				AGLogProcess process = CurrentLogProcess.getOrCreateProcess();
				process.updateAndSaveWorstLogLevel(logLevel);

				AGLogMessage logMessage = new AGLogMessage();
				logMessage.setProcess(process);
				logMessage.setLogLevel(logLevel);
				logMessage.setLogText(message);
				logMessage.setLogTime(DayTime.now());
				logMessage.save();
			} catch (Exception exception) {
				String stack = StackTraceFormatting.getStackTraceAsString(exception);
				StringBuilder logString = new StringBuilder()//
					.append(new Timestamp(System.currentTimeMillis()))
					.append("\n")
					.append("The Database Logging system could not log into the database.")
					.append("\n")
					.append("Stacktrace:")
					.append("\n")
					.append(stack);
				System.out.println(logString);
			}
		} else {
			Log.log(logLevel.getPriority(), getStackTrace(throwable));
		}
	}

	private static boolean shouldLogToDatabase(Throwable throwable) {

		if (isConnectionFailureDuringDownTime(throwable)) {
			return false;
		}

		return CurrentLogDbConfiguration.get().isEnabled();
	}

	private static boolean isConnectionFailureDuringDownTime(Throwable throwable) {

		return throwable instanceof DbConnectionFailureException && isLiveSystemDownTime();
	}

	private static boolean isLiveSystemDownTime() {

		return AGLiveSystemConfiguration//
			.getInstance()
			.map(configuration -> configuration.isDbmsDownTime(DayTime.now()))
			.orElse(false);
	}

	private static void log(LogLevel logLevel, Throwable throwable, String message, Object...args) {

		if (logLevel.getPriority() <= LogLevel.INFO.getPriority()) {
			log(logLevel, throwable, args.length > 0? String.format(message, args) : message);
		}
	}

	private static String getUserNameAndEmail(AGUser currentUser) {

		if (currentUser != null) {
			return currentUser.getFirstAndLastName() + " <" + currentUser.getEmailAddress() + ">";
		} else {
			return "Unknown";
		}
	}

	private static String getUserLanguage(AGUser currentUser) {

		return Optional//
			.ofNullable(currentUser)
			.map(AGUser::getLocale)
			.map(ILocale::getLanguage)
			.map(Object::toString)
			.orElse("undefined language");
	}

	private static String getMessagePrefix() {

		String revisionId = LiveSystemRevision.getCurrentRevision().getName().orElse("");

		AGUser currentUser = CurrentUser.get();
		int userId = currentUser != null? currentUser.getId() : 0;

		return String.format("revision: %s\nuser: %s (%s [%s])", revisionId, userId, getUserNameAndEmail(currentUser), getUserLanguage(currentUser));
	}

	private static String getStackTrace(Throwable throwable) {

		if (throwable != null) {
			return StackTraceFormatting.getStackTraceAsString(throwable);
		} else {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			return StackTraceFormatting.getStackTraceAsString(stackTrace);
		}
	}
}
