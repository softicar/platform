package com.softicar.platform.core.module.email.part.chooser;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.io.mime.MimeType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import javax.mail.MessagingException;
import javax.mail.Part;

/**
 * Chooses the {@link Part} from a {@link Collection} of {@link Part} elements
 * according to a content type preference list.
 *
 * @author Oliver Richers
 */
public class EmailAlternativePartsByTypeChooser implements IEmailAlternativePartsChooser {

	private final List<MimeType> typesPreferenceList;
	private Function<Collection<Part>, Part> fallbackChooser;

	public EmailAlternativePartsByTypeChooser() {

		this.typesPreferenceList = new ArrayList<>();
		this.fallbackChooser = new EmailAlternativePartsFirstPartChooser();
	}

	public EmailAlternativePartsByTypeChooser addType(MimeType type) {

		typesPreferenceList.add(type);
		return this;
	}

	public void setFallbackChooser(Function<Collection<Part>, Part> fallbackChooser) {

		this.fallbackChooser = fallbackChooser;
	}

	@Override
	public Part apply(Collection<Part> parts) {

		for (MimeType desiredType: typesPreferenceList) {
			var desiredPart = parts.stream().filter(part -> isMimeType(part, desiredType)).findFirst();
			if (desiredPart.isPresent()) {
				return desiredPart.get();
			} else {
				Log.finfo("none find in list: %s", parts);
			}
		}
		return fallbackChooser.apply(parts);
	}

	private boolean isMimeType(Part part, MimeType mimeType) {

		try {
			return part.isMimeType(mimeType.getIdentifier());
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}
}
