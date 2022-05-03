package com.softicar.platform.dom.elements.dialog;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.configuration.DomPopupChildClosingMode;
import com.softicar.platform.dom.input.IDomFocusable;
import com.softicar.platform.dom.node.IDomNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A builder for {@link DomModalDialogPopup} elements with a message and
 * fully-customizable option buttons.
 *
 * @author Alexander Schmidt
 */
public class DomModalDialogBuilder {

	private final List<Option> options;
	private IDisplayString message;
	private INullaryVoidFunction callbackBeforeOpen;
	private final Set<IStaticObject> markers;

	/**
	 * Constructs a new {@link DomModalDialogBuilder}.
	 */
	public DomModalDialogBuilder() {

		this.options = new ArrayList<>();
		this.message = IDisplayString.EMPTY;
		this.callbackBeforeOpen = INullaryVoidFunction.NO_OPERATION;
		this.markers = new HashSet<>();
	}

	/**
	 * Defines the message to be displayed in the {@link DomModalDialogPopup} to
	 * build.
	 *
	 * @param message
	 *            the message (never <i>null</i>)
	 * @return this {@link DomModalDialogBuilder}
	 */
	public DomModalDialogBuilder setMessage(IDisplayString message) {

		this.message = Objects.requireNonNull(message);
		return this;
	}

	/**
	 * Defines the callback to be executed when (i.e. immediately before) the
	 * {@link DomModalDialogPopup} to build is opened.
	 *
	 * @param callbackBeforeOpen
	 *            the callback (never <i>null</i>)
	 * @return this {@link DomModalDialogBuilder}
	 */
	public DomModalDialogBuilder setCallbackBeforeOpen(INullaryVoidFunction callbackBeforeOpen) {

		this.callbackBeforeOpen = Objects.requireNonNull(callbackBeforeOpen);
		return this;
	}

	/**
	 * Adds an option button to the {@link DomModalDialogPopup} to build.
	 *
	 * @param icon
	 *            the icon of the button (never <i>null</i>)
	 * @param label
	 *            the label of the button (never <i>null</i>)
	 * @param callback
	 *            the callback of the button (never <i>null</i>)
	 * @return this {@link DomModalDialogBuilder}
	 */
	public DomModalDialogBuilder addOption(IResource icon, IDisplayString label, INullaryVoidFunction callback) {

		return addOption(icon, label, callback, new IStaticObject[0]);
	}

	/**
	 * Adds an option button to the {@link DomModalDialogPopup} to build.
	 *
	 * @param icon
	 *            the icon of the button (never <i>null</i>)
	 * @param label
	 *            the label of the button (never <i>null</i>)
	 * @param callback
	 *            the callback of the button (never <i>null</i>)
	 * @param markers
	 *            the markers of the button (never <i>null</i>)
	 * @return this {@link DomModalDialogBuilder}
	 */
	public DomModalDialogBuilder addOption(IResource icon, IDisplayString label, INullaryVoidFunction callback, IStaticObject...markers) {

		this.options.add(new Option(icon, label, callback, markers));
		return this;
	}

	/**
	 * Defines markers for the {@link DomModalDialogPopup} to build.
	 *
	 * @param markers
	 *            the markers (never <i>null</i>)
	 * @return this {@link DomModalDialogBuilder}
	 */
	public DomModalDialogBuilder addMarkers(IStaticObject...markers) {

		Objects.requireNonNull(markers);
		Arrays.asList(markers).forEach(this.markers::add);
		return this;
	}

	/**
	 * Builds the {@link DomModalDialogPopup}.
	 * <p>
	 * To display it, use {@link DomModalDialogPopup#open()}.
	 *
	 * @return the {@link DomModalDialogPopup} (never <i>null</i>)
	 */
	public DomModalDialogPopup build() {

		return new DialogPopup();
	}

	private class DialogPopup extends DomModalDialogPopup {

		public DialogPopup() {

			addCssClass(DomElementsCssClasses.DOM_MODAL_DIALOG_POPUP_WRAPPED);
			configuration.setChildClosingMode(DomPopupChildClosingMode.AUTOMATIC_NONE);
			configuration.setCallbackBeforeOpen(callbackBeforeOpen);
			getContent().appendText(message);
			options.stream().map(this::createOptionButton).forEach(this::appendActionNode);
			addMarkers(markers);
		}

		@Override
		public void open() {

			super.open();
			IDomFocusable.focusFirst(DomButton.class, this);
		}

		private IDomNode createOptionButton(Option option) {

			return new DomButton()//
				.setLabel(option.getLabel())
				.setIcon(option.getIcon())
				.setClickCallback(() -> {
					option.getCallback().apply();
					close();
				})
				.addMarkers(option.getMarkers());
		}
	}

	private static class Option {

		private final IResource icon;
		private final IDisplayString label;
		private final INullaryVoidFunction callback;
		private final IStaticObject[] markers;

		public Option(IResource icon, IDisplayString label, INullaryVoidFunction callback, IStaticObject[] markers) {

			this.icon = Objects.requireNonNull(icon);
			this.label = Objects.requireNonNull(label);
			this.callback = Objects.requireNonNull(callback);
			this.markers = Objects.requireNonNull(markers);
		}

		public IResource getIcon() {

			return icon;
		}

		public IDisplayString getLabel() {

			return label;
		}

		public INullaryVoidFunction getCallback() {

			return callback;
		}

		public Collection<IStaticObject> getMarkers() {

			return Arrays.asList(markers);
		}
	}
}
