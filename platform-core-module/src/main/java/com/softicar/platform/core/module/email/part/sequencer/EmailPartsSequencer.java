package com.softicar.platform.core.module.email.part.sequencer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;

/**
 * Flattens the hierarchical structure of a {@link MimeMessage} into a sequence
 * of {@link Part} objects.
 *
 * @author Oliver Richers
 */
public class EmailPartsSequencer {

	private final Part root;
	private final List<Part> parts;
	private Function<Collection<Part>, Part> alternativeChooser;

	/**
	 * Constructs this {@link EmailPartsSequencer} with the given root
	 * {@link Part}.
	 *
	 * @param root
	 *            the root {@link Part} of the hierarchy, usually the
	 *            {@link MimeMessage} (never <i>null</i>)
	 */
	public EmailPartsSequencer(Part root) {

		this.root = Objects.requireNonNull(root);
		this.parts = new ArrayList<>();
		this.alternativeChooser = parts -> parts.iterator().next();
	}

	/**
	 * Defines the logic to choose between a {@link Collection} of alternative
	 * {@link Part} objects.
	 *
	 * @param alternativeChooser
	 *            the chooser logic (never <i>null</i>)
	 * @return this
	 */
	public EmailPartsSequencer setAlternativeChooser(Function<Collection<Part>, Part> alternativeChooser) {

		this.alternativeChooser = Objects.requireNonNull(alternativeChooser);
		return this;
	}

	public List<Part> getParts() {

		parts.clear();
		collectParts(root);
		return parts;
	}

	private void collectParts(Part part) {

		try {
			if (part.isMimeType("multipart/*")) {
				var parts = getParts((Multipart) part.getContent());
				if (part.isMimeType("multipart/alternative")) {
					if (!parts.isEmpty()) {
						collectParts(alternativeChooser.apply(parts));
					}
				} else {
					parts.forEach(this::collectParts);
				}
			} else {
				parts.add(part);
			}
		} catch (MessagingException | IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private static List<Part> getParts(Multipart multiPart) {

		try {
			var parts = new ArrayList<Part>();
			for (int i = 0; i < multiPart.getCount(); i++) {
				parts.add(multiPart.getBodyPart(i));
			}
			return parts;
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}
}
