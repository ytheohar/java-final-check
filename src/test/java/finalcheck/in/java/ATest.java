package finalcheck.in.java;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;

import org.junit.Test;

public class ATest {

	@Test
	public void redefineFinalMethodOfAclass() {
		AClass obj = new AClass();
		assertThat(obj.returnFalse(), is(false));

		ByteBuddyAgent.installOnOpenJDK();
		ClassReloadingStrategy classReloadingStrategy = ClassReloadingStrategy.fromInstalledAgent();
		new ByteBuddy().redefine(AClass.class).method(named("returnFalse"))
				.intercept(FixedValue.value(true)).make()
				.load(AClass.class.getClassLoader(), classReloadingStrategy);

		assertThat(obj.returnFalse(), is(true));
	}

	@Test(expected = NullPointerException.class)
	public void redefineMethodOfStringClass() {
		String obj = "abc";
		assertThat(obj.isEmpty(), is(false));

		ByteBuddyAgent.installOnOpenJDK();
		ClassReloadingStrategy classReloadingStrategy = ClassReloadingStrategy.fromInstalledAgent();
		new ByteBuddy().redefine(String.class).method(named("isEmpty"))
				.intercept(FixedValue.value(true)).make()
				.load(String.class.getClassLoader(), classReloadingStrategy);

		assertThat(obj.isEmpty(), is(true));
	}

}
