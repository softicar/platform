package com.softicar.platform.common.code.string;

@SuppressWarnings("unused")
public class TestClassWithInnerClass {

	private void test1() {

		String a = "a";
	}

	private class TestClassInnerClass {

		private void test1() {

			String b = "b";
			String c = "c";
		}

		private void test2() {

			String d = "d";
			String e = "e";
		}
	}
}
