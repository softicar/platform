package com.softicar.platform.emf.management.importing.engine.test;

import java.util.ArrayList;
import java.util.Collection;

public class Test {

	public static class Client {

		private Strategy<?> a;

		public void setStrategy(Strategy<?> a) {

			this.a = a;
		}

		private void run() {

//			a.execute("hello world");
		}
	}

	public static void main(String[] args) {

		Client client = new Client();
		client.setStrategy(new DefaultStrategy<String>());
		client.run();

		///////////

//		Collection<Object> c = new ArrayList<String>();

		Collection<?> col = new ArrayList<String>();
//		col.add("test"); // Compilefehler
		((Collection<String>) col).add("test"); // funktioniert
		((Collection<Integer>) col).add(1); // funktioniert
		System.out.println(col);

	}
}
