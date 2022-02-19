package lab_1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.swing.JTextField;

public class TestReflection {

	public TestReflection() {

	}

	public static void main(String[] args) {
		try {

			Class<?> clz = Class.forName("lab_1.DlgCommand");

			System.out.println("\n��������� ������ toGenericStr�ng()");
			System.out.println(clz.toGenericString());

			System.out.println("\n��������� ������ getName()");
			System.out.println(clz.getName());

			System.out.println("\n��������� ������ getSimpleName()");
			System.out.println(clz.getSimpleName());

			System.out.println("\n��������� ������ getSuperclass()");
			System.out.println(clz.getSuperclass());

			System.out.println("\n���������� Fields()");
			java.lang.reflect.Field[] fld = clz.getFields();
			for (java.lang.reflect.Field field : fld) {
				System.out.println(field.toGenericString());
			}

			System.out.println("\n���������� getDeclaredFields()");
			java.lang.reflect.Field[] fld2 = clz.getDeclaredFields();
			for (java.lang.reflect.Field field : fld2) {
				System.out.println(field.toGenericString());
			}

			System.out.println("\nDeclared getDeclaredMethods()");
			java.lang.reflect.Method[] met = clz.getDeclaredMethods();
			for (java.lang.reflect.Method field : met) {
				System.out.println(field.toGenericString());
			}

			System.out.println("\nDeclared Methods()");
			java.lang.reflect.Method[] met2 = clz.getMethods();
			for (java.lang.reflect.Method field : met2) {
				System.out.println(field.toGenericString());
			}

	
			System.out.println("\nDeclared Constructors()");
			Constructor<?>[] con2 = clz.getConstructors();
			for (Constructor field : con2) {
				System.out.println(field.toGenericString());
			}

			System.out.println("\nDeclared getDeclaredConstructors()");
			Constructor<?>[] con = clz.getDeclaredConstructors();
			for (Constructor field : con) {
				System.out.println(field.toGenericString());
			}

			System.out.println("\n��������� ��������� ��'����");
			DlgCommand dlg = (DlgCommand) clz.newInstance();
			System.out.println(dlg);
			// ��������� ����� � ������� ���� ������
			java.lang.reflect.Field field = clz.getDeclaredField("textField_furniture");
			field.setAccessible(true);
			JTextField fieldStaff = (JTextField) field.get(dlg);
			fieldStaff.setText("5");
			
			Object a;
			Method mtGetMap;
			mtGetMap = clz.getMethod("setVisible");
			a = mtGetMap.invoke(dlg);

			System.out.println("\n��������� ������� ������ getMap()");
			
			mtGetMap = clz.getMethod("getMap");
			a = mtGetMap.invoke(dlg);
			System.out.println(a);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
