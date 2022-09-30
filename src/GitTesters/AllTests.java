package GitTesters;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
//import org.junit.platform.commons.annotations.Testable;

// Format for JUnit 5 Jupiter Tests
// stolen from https://junit.org/junit5/docs/current/user-guide/#junit-platform-suite-engine (Links to an external site.)
// more docs found  https://www.baeldung.com/junit-5 (Links to an external site.)

@Suite
@SuiteDisplayName("Git Project Tester")
@SelectPackages("testers")
@IncludeClassNamePatterns(".*GitTester")
class AllTests {

	@Test
	public void test() {
		assertTrue(true);
	}
}
