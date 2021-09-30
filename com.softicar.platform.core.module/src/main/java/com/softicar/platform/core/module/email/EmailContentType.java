package com.softicar.platform.core.module.email;

public enum EmailContentType {

	PLAIN("text/plain;charset=utf-8"),
	HTML("text/html;charset=utf-8"),
	//
	;

	private String contentTypeString;

	private EmailContentType(String contentTypeString) {

		this.contentTypeString = contentTypeString;
	}

	public String getContentTypeString() {

		return contentTypeString;
	}
}
