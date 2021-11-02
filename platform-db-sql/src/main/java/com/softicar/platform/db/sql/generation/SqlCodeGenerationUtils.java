package com.softicar.platform.db.sql.generation;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.sql.selects.ISqlSelectCoreAdapter;
import java.util.ArrayList;
import java.util.Arrays;

public class SqlCodeGenerationUtils {

	public static SqlJavaClassName getTableChooserName(int tableCount, int expressionTableCount) {

		String rawName = String.format("SqlTableChooser%d_%d", tableCount, expressionTableCount);
		String arguments = implode("V", "S", getTableArgumentList("T", tableCount), getTableArgumentList("E", expressionTableCount));
		String selectParameter = String.format("S extends %s", ISqlSelectCoreAdapter.class.getSimpleName());
		String parameters = implode("V", selectParameter, getTableArgumentList("T", tableCount), getTableArgumentList("E", expressionTableCount));

		return new SqlJavaClassName(rawName, arguments, parameters);
	}

	public static String implode(Object...elements) {

		ArrayList<String> all = new ArrayList<>();
		String lastString = null;
		for (Object element: elements) {
			if (element instanceof String) {
				if (lastString != null && !lastString.isEmpty()) {
					all.add(lastString);
				}
				lastString = (String) element;
			} else if (element instanceof Integer) {
				if (lastString == null) {
					throw new SofticarException("Got integer %d without a previous string.", element);
				}
				int n = (Integer) element;
				int count = lastString.replaceAll("%%", "").split("%").length - 1;
				Object ns[] = null;
				if (count > 0) {
					ns = new Object[count];
				}
				for (int i = 0; i < n; ++i) {
					if (ns != null) {
						Arrays.fill(ns, i);
						all.add(String.format(lastString, ns));
					} else {
						all.add(lastString + i);
					}
				}
				lastString = null;
			} else {
				throw new SofticarException("Elements of type %s are not allowed.", element.getClass().getName());
			}
		}

		if (lastString != null && !lastString.isEmpty()) {
			all.add(lastString);
		}

		return all.isEmpty()? "" : "<" + Imploder.implode(all, ", ") + ">";
	}

	public static String getTableArgumentList(String prefix, int count) {

		ArrayList<String> list = new ArrayList<>(count);

		for (int i = 0; i < count; ++i) {
			list.add(prefix + i);
		}

		return Imploder.implode(list, ", ");
	}

	public static String getValueParameterList(String prefix, int count) {

		ArrayList<String> list = new ArrayList<>(count);

		for (int i = 0; i < count; ++i) {
			list.add(prefix + i);
		}

		return Imploder.implode(list, ", ");
	}

	private static String implode(String...elements) {

		ArrayList<String> tmp = new ArrayList<>(elements.length);
		for (String element: elements) {
			if (!element.isEmpty()) {
				tmp.add(element);
			}
		}
		return Imploder.implode(tmp, ", ");
	}
}
