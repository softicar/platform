package com.softicar.platform.core.module.file.smb;

public interface ISmbApi {

	void initialize();

	ISmbFile createFile(String url, ISmbCredentials credentials);
}
