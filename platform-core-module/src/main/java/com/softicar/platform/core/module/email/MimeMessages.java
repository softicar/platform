package com.softicar.platform.core.module.email;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Supplier;

/**
 * Convenience methods for {@link MimeMessage}.
 *
 * @author Oliver Richers
 */
public abstract class MimeMessages {

	/**
	 * Reads a {@link MimeMessage} from an {@link InputStream}.
	 *
	 * @param inputStreamSupplier
	 *            the {@link Supplier} for the {@link InputStream} (never
	 *            <i>null</i>)
	 * @return the loaded {@link MimeMessage} (never <i>null</i>)
	 */
	public static MimeMessage read(Supplier<InputStream> inputStreamSupplier) {

		try (var inputStream = inputStreamSupplier.get()) {
			return new MimeMessage(null, inputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Writes a {@link MimeMessage} to an {@link OutputStream}.
	 *
	 * @param message
	 *            the {@link MimeMessage} (never <i>null</i>)
	 * @param outputStreamSupplier
	 *            the {@link Supplier} for the {@link OutputStream} (never
	 *            <i>null</i>)
	 */
	public static void write(MimeMessage message, Supplier<OutputStream> outputStreamSupplier) {

		try (var outputStream = outputStreamSupplier.get()) {
			message.writeTo(outputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}
}
