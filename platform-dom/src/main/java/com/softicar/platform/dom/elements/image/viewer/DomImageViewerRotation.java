package com.softicar.platform.dom.elements.image.viewer;

/**
 * Enumerates rotation steps (i.e. angles) for {@link DomImageViewer}.
 *
 * @author Alexander Schmidt
 */
public enum DomImageViewerRotation {

	_0,
	_90,
	_180,
	_270;

	/**
	 * Returns the next {@link DomImageViewerRotation}.
	 * <p>
	 * Wraps around.
	 *
	 * @return the next {@link DomImageViewerRotation} (never <i>null</i>)
	 */
	public DomImageViewerRotation getNext() {

		return switch (this) {
		case _0:
			yield _90;
		case _90:
			yield _180;
		case _180:
			yield _270;
		case _270:
			yield _0;
		};
	}

	/**
	 * Returns the previous {@link DomImageViewerRotation}.
	 * <p>
	 * Wraps around.
	 *
	 * @return the previous {@link DomImageViewerRotation} (never <i>null</i>)
	 */
	public DomImageViewerRotation getPrevious() {

		return switch (this) {
		case _0:
			yield _270;
		case _90:
			yield _0;
		case _180:
			yield _90;
		case _270:
			yield _180;
		};
	}
}
