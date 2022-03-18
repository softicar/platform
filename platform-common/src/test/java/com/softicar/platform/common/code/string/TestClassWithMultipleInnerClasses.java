package com.softicar.platform.common.code.string;

@SuppressWarnings("unused")
public class TestClassWithMultipleInnerClasses {

	private void test1() {

		String a = "a";
	}

	private class TestClassInnerClass {

		private void test1() {

			String b = "b";
		}

		private void test2() {

			String c = "c";
		}

		private void test3() {

			String d = "d";
		}

		private class TestClassDeeperClass {

			private void test1() {

				String e = "e";
			}

			private class TestClassInnerDeeperClass {

				private void test1() {

					String f = "f";
				}
			}

			private class TestClassSecondInnerDeeperClass {

				private void test1() {

					String g = "g";
				}
			}
		}

		private class TestClassEvenDeeperClass {

			private void test1() {

				String h = "h";
			}
		}
	}

	private class TestClassSecondInnerClass {

		private void test1() {

			String i = "i";
		}
	}
}
