package com.softicar.platform.common.string.imploder;

@FunctionalInterface
public interface IObjectImploder<T> {

	public String implode(T obj);
}
