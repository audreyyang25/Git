package testers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import git.Commit;

class CommitTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void initTest() throws IOException {
		Commit commit = new Commit("This is a summary","Matthew Ko",null);		
		Commit child = new Commit("This is the second summary","Steven Ko",commit.getCommitName());
		commit.setChild(child);
		Commit secondChild = new Commit("This is the third summary","Christian Bach",child.getCommitName());
		child.setChild(secondChild);
		
		File commitFile = new File("Test/Objects/9d6fe9385fb99850c0642cbe8f9b55e753ce2cdd");
		File childFile = new File("Test/Objects/163210369adabb79a4678b6e212b35fbcacd0d11");
		File secondChildFile = new File("Test/Objects/a481eea41ba06192c957ce04a96712d8b494a3f0");
		
		assertTrue(commitFile.exists());
		assertTrue(childFile.exists());
		assertTrue(secondChildFile.exists());
		
		commitFile.delete();
		childFile.delete();
		secondChildFile.delete();
		
		}

}
