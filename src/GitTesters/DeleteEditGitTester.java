package GitTesters;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import git.Commit;
import git.index;

class DeleteEditGitTester {
	private static index index;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		AddGitTester.writeFile ("Test/foo.txt", "foo file");
		AddGitTester.writeFile ("Test/bar.txt", "bar file");
		AddGitTester.writeFile ("Test/stuff.txt", "stuff file");
		index = new index ();
		index.init();
	}
	
	@AfterAll
	static void cleanUp () {
		File objects = new File ("Test/Objects/");
		objects.mkdir();
		if (objects.exists()) {
			File[] contents = objects.listFiles();
			for (File f : contents) {
	            f.delete();
	        }
		}
		
		File foo = new File ("Test/foo.txt");
		foo.delete();
		
		File bar = new File ("Test/bar.txt");
		bar.delete();
		
		File stuff = new File ("Test/stuff.txt");
		stuff.delete();
		
		File index = new File ("Test/index");
		index.delete();
	}
	
	// should this automatically check if blob was deleted (how would we check that?!!), or can we manually check?
	@Test
	void testDelete () throws IOException {
		index.add("foo.txt");
		index.add("bar.txt");
		Commit one = new Commit ("adding foo and bar", "Audrey", null);
		
		index.edit("foo.txt");
		index.add("foo.txt");
		Commit two = new Commit ("edited foo", "Audrey", one.getCommitName());
		one.setChild(two);
		// setChild method -- is this necessary? Why do we need a private instance variable that we never use?
		
		index.add("stuff.txt");
		index.delete("bar.txt");
		Commit three = new Commit ("added stuff and deleted bar", "Audrey", two.getCommitName());
		two.setChild(three);
		
		index.edit("stuff.txt");
		index.add("stuff.txt");
		index.delete("foo.txt");
		Commit four = new Commit ("edited stuff and deleted foo", "Audrey", three.getCommitName());
		three.setChild(four);
	}
	

}
