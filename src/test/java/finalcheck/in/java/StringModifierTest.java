package finalcheck.in.java;

import java.lang.reflect.Method;

import not.asfinalas.you.think.StringModifier;

import org.junit.Test;

public class StringModifierTest {

	@Test(expected = NoSuchMethodException.class)
	public void javaCanNotSeeAddedMethod() throws NoSuchMethodException, SecurityException {
		String methodName = "myMethod";

		StringModifier m = new StringModifier();
		m.addMethod(methodName);

		Method method = String.class.getDeclaredMethod(methodName);
	}

}
