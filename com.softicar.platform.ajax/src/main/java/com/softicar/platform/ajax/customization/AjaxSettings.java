package com.softicar.platform.ajax.customization;

/**
 * Default implementation of {@link IAjaxSettings}.
 *
 * @author Oliver Richers
 */
public class AjaxSettings implements IAjaxSettings {

	private int maximumExistingNodeCount = 0;
	private int maximumDocumentIdleSeconds = 15 * 60;
	private int maximumDocumentsPerSesssion = 20;
	private int maximumAgeForCachedResources = 7 * 24 * 60 * 60;

	@Override
	public int getMaximumExistingNodeCount() {

		return maximumExistingNodeCount;
	}

	@Override
	public void setMaximumExistingNodeCount(int maximumExistingNodeCount) {

		this.maximumExistingNodeCount = maximumExistingNodeCount;
	}

	@Override
	public int getMaximumDocumentIdleSeconds() {

		return maximumDocumentIdleSeconds;
	}

	@Override
	public IAjaxSettings setMaximumDocumentIdleSeconds(int maximumDocumentIdleSeconds) {

		this.maximumDocumentIdleSeconds = maximumDocumentIdleSeconds;
		return this;
	}

	@Override
	public int getMaximumDocumentsPerSession() {

		return maximumDocumentsPerSesssion;
	}

	@Override
	public IAjaxSettings setMaximumDocumentsPerSession(int maximumDocumentsPerSesssion) {

		this.maximumDocumentsPerSesssion = maximumDocumentsPerSesssion;
		return this;
	}

	@Override
	public int getMaximumAgeForCachedResources() {

		return maximumAgeForCachedResources;
	}

	@Override
	public void setMaximumAgeForCachedResources(int maximumAge) {

		this.maximumAgeForCachedResources = maximumAge;
	}
}
